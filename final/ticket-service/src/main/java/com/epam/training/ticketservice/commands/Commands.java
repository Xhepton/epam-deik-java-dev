package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Movie;
import com.epam.training.ticketservice.models.Room;
import com.epam.training.ticketservice.models.Screening;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import com.epam.training.ticketservice.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ShellComponent
public class Commands {

    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final ScreeningRepository screeningRepository;
    private final AdminService adminService;
    private boolean adminLoggedIn = false;
    private String loggedInUsername;

    @Autowired
    public Commands(MovieRepository movieRepository,
                    RoomRepository roomRepository,
                    ScreeningRepository screeningRepository,
                    AdminService adminService) {
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
        this.adminService = adminService;
    }

    @ShellMethod(key = "sign in privileged", value = "Admin login")
    public void signInPrivileged(String username, String password) {
        if (adminService.authenticate(username, password)) {
            adminLoggedIn = true;
            loggedInUsername = username;
        } else {
            System.out.println("Login failed due to incorrect credentials");
        }
    }

    @ShellMethod(key = "sign out", value = "Admin logout")
    public void signOut() {
        adminLoggedIn = false;
    }

    @ShellMethod(key = "describe account", value = "Describe the current account")
    public String describeAccount() {
        if (adminLoggedIn) {
            return "Signed in with privileged account '" + loggedInUsername + "'";
        } else {
            return "You are not signed in";
        }
    }

    @ShellMethod(key = "create movie", value = "Létrehoz egy filmet és menti az adatbázisba")
    public void createMovie(String movieName, String type, int length) {
        if (adminLoggedIn) {
            Movie movie = new Movie(movieName, type, length);

            movieRepository.save(movie);

            System.out.println("Film mentve az adatbázisba: " + movie.getMovieName());
        } else {
            System.out.println("You are not signed in");
        }
    }

    @ShellMethod(key = "update movie", value = "Update movie details")
    public String updateMovie(String movieTitle, String genre, int duration) {
        if (adminLoggedIn) {
            // Retrieve the existing movie from the database
            Movie existingMovie = movieRepository.findByMovieName(movieTitle);

            if (existingMovie != null) {
                // Update the properties of the existing movie
                existingMovie.setType(genre);
                existingMovie.setDuration(duration);

                // Save the updated movie back to the database
                movieRepository.save(existingMovie);

                return "Movie '" + movieTitle + "' updated successfully.";
            } else {
                return "Movie with title '" + movieTitle + "' not found.";
            }
        } else {
            return "You are not signed in";
        }
    }

    @ShellMethod(key = "delete movie", value = "Delete a movie")
    public String deleteMovie(String movieTitle) {
        if (adminLoggedIn) {
            // Retrieve the existing movie from the database
            Movie existingMovie = movieRepository.findByMovieName(movieTitle);

            if (existingMovie != null) {
                // Delete the movie from the database
                movieRepository.delete(existingMovie);

                return "Movie '" + movieTitle + "' deleted successfully.";
            } else {
                return "Movie with title '" + movieTitle + "' not found.";
            }
        } else {
            return "You are not signed in";
        }
    }

    @ShellMethod(key = "list movies", value = "List all movies")
    public String listMovies() {
        List<Movie> movies = movieRepository.findAll();

        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder output = new StringBuilder();
            for (Movie movie : movies) {
                output.append(movie.getMovieName())
                        .append(" (")
                        .append(movie.getType())
                        .append(", ")
                        .append(movie.getDuration())
                        .append(" minutes)");
            }
            return output.toString();
        }
    }

    @ShellMethod(key = "create room", value = "Create a room")
    public void createRoom(String roomName, int rows, int columns) {
        if (adminLoggedIn) {
            // Check if a room with the same name already exists
            if (roomRepository.existsByRoomName(roomName)) {
                System.out.println("Room with name '" + roomName + "' already exists.");
            } else {
                // Create a new room
                Room newRoom = new Room(roomName, rows, columns);

                // Save the new room to the database
                roomRepository.save(newRoom);
            }

        } else {
            System.out.println("You must be logged in as admin to execute this command.");
        }
    }

    @ShellMethod(key = "update room", value = "Update a room")
    public void updateRoom(String roomName, int rows, int columns) {
        if (adminLoggedIn) {
            Room existingRoom = roomRepository.findByRoomName(roomName);

            if (existingRoom != null) {
                // Update the room properties
                existingRoom.setRows(rows);
                existingRoom.setColumns(columns);

                // Save the updated room
                roomRepository.save(existingRoom);
            } else {
                System.out.println("Cannot find room with the given room name: " + roomName);
            }
        } else {
            System.out.println("You are not signed in");
        }
    }

    @ShellMethod(key = "delete room", value = "Delete a room")
    public void deleteRoom(String roomName) {
        if (adminLoggedIn) {
            Room existingRoom = roomRepository.findByRoomName(roomName);

            if (existingRoom != null) {
                // Delete the room from the database
                roomRepository.delete(existingRoom);

            } else {
                System.out.println("Cannot find room with the given room name: " + roomName);
            }
        } else {
            System.out.println("You are not signed in");
        }
    }

    @ShellMethod(key = "list rooms", value = "List rooms")
    public String listRooms() {
        List<Room> rooms = roomRepository.findAll();
        StringBuilder result = new StringBuilder();

        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            for (Room room : rooms) {
                result.append(String.format("Room %s with %d seats, %d rows and %d columns",
                        room.getRoomName(),
                        room.getColumns() * room.getRows(),
                        room.getRows(),
                        room.getColumns()
                ));
            }
            return result.toString();
        }
    }

    @ShellMethod(key = "create screening", value = "Create a screening")
    public void createScreening(String movieTitle, String roomName, String startDateTime) {
        if (adminLoggedIn) {
            Movie movie = movieRepository.findByMovieName(movieTitle);
            Room room = roomRepository.findByRoomName(roomName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            // Parse the string using the formatter
            LocalDateTime screeningStartDateTime = LocalDateTime.parse(startDateTime, formatter);

            // Check if there is an overlapping screening
            LocalDateTime screeningEndTime = screeningStartDateTime.plusMinutes(movie.getDuration());
            if (screeningRepository.existsByRoomAndStartTimeBetween(room.getRoomName(),
                                                                    screeningStartDateTime,
                                                                    screeningEndTime)) {
                System.out.println("There is an overlapping screening");
            } else if (screeningRepository.existsByRoomAndStartTimeAfter(room.getRoomName(), screeningStartDateTime)) {
                System.out.println("This would start in the break period after another screening in this room");
            } else {
                Screening screening = new Screening(movie.getMovieName(),
                                                    room.getRoomName(),
                                                    screeningStartDateTime,
                                                    movie.getDuration());
                screeningRepository.save(screening);
            }
        } else {
            System.out.println("You are not signed in");
        }
    }

    @ShellMethod(key = "delete screening", value = "Delete a screening")
    public String deleteScreening(String movieTitle, String roomName, String startDateTime) {
        if (adminLoggedIn) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime screeningStartDateTime = LocalDateTime.parse(startDateTime, formatter);

                // Find the screening to delete
                Screening screeningToDelete = screeningRepository.findByMovieNameAndRoomNameAndStartTime(
                        movieTitle, roomName, screeningStartDateTime);

                if (screeningToDelete != null) {
                    // Delete the screening
                    screeningRepository.delete(screeningToDelete);
                    return "Screening deleted successfully";
                } else {
                    return "Screening not found";
                }
            } catch (Exception e) {
                return "Error deleting screening";
            }
        } else {
            return "You are not signed in";
        }
    }

    @ShellMethod(key = "list screenings", value = "List all screenings")
    public String listScreenings() {
        List<Screening> screenings = screeningRepository.findAll();

        if (screenings.isEmpty()) {
            return "There are no screenings";
        }

        StringBuilder result = new StringBuilder();
        for (Screening screening : screenings) {
            result.append(formatScreeningInfo(screening)).append("\n");
        }

        return result.toString();
    }

    private String formatScreeningInfo(Screening screening) {
        return String.format("%s (%s, %d minutes), screened in room %s, at %s",
                screening.getMovieName(),
                movieRepository.findByMovieName(screening.getMovieName()).getType(),
                movieRepository.findByMovieName(screening.getMovieName()).getDuration(),
                screening.getRoomName(),
                screening.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
