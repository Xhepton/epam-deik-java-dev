package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Movie;
import com.epam.training.ticketservice.models.Room;
import com.epam.training.ticketservice.models.Screening;
import com.epam.training.ticketservice.repositories.MovieRepository;
import com.epam.training.ticketservice.repositories.RoomRepository;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import com.epam.training.ticketservice.services.AdminService;
import org.jline.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public Commands(MovieRepository movieRepository, RoomRepository roomRepository, ScreeningRepository screeningRepository, AdminService adminService) {
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.screeningRepository = screeningRepository;
        this.adminService = adminService;
    }
    @ShellMethod(key = "sign in privileged", value = "Admin login")
    public String signInPrivileged(String username, String password) {
        if (adminService.authenticate(username, password)) {
            adminLoggedIn = true;
            loggedInUsername = username;
            return "Admin login successful. You now have access to privileged commands.";
        } else {
            return "Login failed due to incorrect credentials.";
        }
    }
    @ShellMethod(key = "sign out", value = "Admin logout")
    public String signOut() {
        adminLoggedIn = false;
        return "Admin logout successful. You no longer have access to privileged commands.";
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
    public String createMovie(String movieName, String type, int length) {
        if (adminLoggedIn) {
            Movie movie = new Movie();
            movie.setMovie_name(movieName);
            movie.setType(type);
            movie.setDuration(length);

            movieRepository.save(movie);

            return "Film mentve az adatbázisba: " + movie.getMovie_name();
        }
        else {
            return "You are not signed in";
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
                output.append(movie.getMovie_name())
                        .append(" (")
                        .append(movie.getType())
                        .append(", ")
                        .append(movie.getDuration())
                        .append(" minutes)\n");
            }
            return output.toString();
        }
    }

    @ShellMethod(key = "create room", value = "Create a room")
    public String createRoom(String roomName, int rows, int columns) {
        if (adminLoggedIn) {
            // Check if a room with the same name already exists
            if (roomRepository.existsByRoomName(roomName)) {
                return "Room with name '" + roomName + "' already exists.";
            }

            // Create a new room
            Room newRoom = new Room();
            newRoom.setRoom_name(roomName);
            newRoom.setRows(rows);
            newRoom.setColumns(columns);

            // Save the new room to the database
            roomRepository.save(newRoom);

            return "Room '" + roomName + "' created successfully.";
        } else {
            return "You must be logged in as admin to execute this command.";
        }
    }
    @ShellMethod(key = "update room", value = "Update a room")
    public String updateRoom(String roomName, int rows, int columns) {
        if (adminLoggedIn) {
            Room existingRoom = roomRepository.findByRoomName(roomName);

            if (existingRoom != null) {
                // Update the room properties
                existingRoom.setRows(rows);
                existingRoom.setColumns(columns);

                // Save the updated room
                roomRepository.save(existingRoom);

                return "Room Updated: " + existingRoom.getRoom_name();
            } else {
                return "Cannot find room with the given room name: " + roomName;
            }
        } else {
            return "You are not signed in";
        }
    }
    @ShellMethod(key = "delete room", value = "Delete a room")
    public String deleteRoom(String roomName) {
        if (adminLoggedIn) {
            Room existingRoom = roomRepository.findByRoomName(roomName);

            if (existingRoom != null) {
                // Delete the room from the database
                roomRepository.delete(existingRoom);

                return "Room deleted: " + existingRoom.getRoom_name();
            } else {
                return "Cannot find room with the given room name: " + roomName;
            }
        } else {
            return "You are not signed in";
        }
    }
    @ShellMethod(key = "list rooms", value = "List rooms")
    public String listRooms() {
        List<Room> rooms = roomRepository.findAll();
        List<String> result = new ArrayList<>();

        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            for (Room room : rooms) {
                result.add(String.format("Room %s with %d seats, %d rows, and %d columns",
                        room.getRoom_name(),
                        room.getColumns()*room.getRows(),
                        room.getRows(),
                        room.getColumns()
                ));
            }
            return result.toString();
        }
    }
    @ShellMethod(key = "create screening", value = "Create a screening")
    public String createScreening(String movieTitle, String roomName, String startDateTime) {
        if (adminLoggedIn) {
            Movie movie = movieRepository.findByMovieName(movieTitle);
            Room room = roomRepository.findByRoomName(roomName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            //        LocalDateTime screeningStartDateTime = LocalDateTime.parse(startDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
            try {
                // Parse the string using the formatter
                LocalDateTime screeningStartDateTime = LocalDateTime.parse(startDateTime, formatter);
                Log.info("Parsed LocalDateTime: " + screeningStartDateTime);

                // Check if there is an overlapping screening
                LocalDateTime screeningEndTime = screeningStartDateTime.plusMinutes(movie.getDuration());
                if (screeningRepository.existsByRoomAndStartTimeBetween(room.getRoom_name(), screeningStartDateTime, screeningEndTime)) {
                    return "There is an overlapping screening";
                }

                // Check if the screening starts in the break period after another screening
                if (screeningRepository.existsByRoomAndStartTimeAfter(room.getRoom_name(), screeningStartDateTime)) {
                    return "This would start in the break period after another screening in this room";
                }

                // Create and save the screening (using the constructor
                Screening screening = new Screening(movie.getMovie_name(), room.getRoom_name(), screeningStartDateTime, movie.getDuration());
                //            screening.setMovie_name(movie.getMovie_name());
                //            screening.setRoom_name(room.getRoom_name());
                //            screening.setStartDateTime(screeningStartDateTime);
                //            screening.setEndDateTime(screeningEndTime);
                screeningRepository.save(screening);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "Screening created successfully";
        } else {
            return "You are not signed in";
        }
    }
}
