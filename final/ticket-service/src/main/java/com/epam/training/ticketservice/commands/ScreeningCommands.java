package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Movie;
import com.epam.training.ticketservice.models.Room;
import com.epam.training.ticketservice.models.Screening;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ShellComponent
public class ScreeningCommands {

    private final ScreeningRepository screeningRepository;

    @Autowired
    public ScreeningCommands(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @ShellMethod(key = "create screening", value = "Create a screening")
    public void createScreening(String movieTitle, String roomName, String startDateTime) {
        if (UserCommands.isAdminLoggedIn()) {
            Movie movie = MovieCommands.getMovieRepository().findByMovieName(movieTitle);
            Room room = RoomCommands.getRoomRepository().findByRoomName(roomName);

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
        if (UserCommands.isAdminLoggedIn()) {
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
                MovieCommands.getMovieRepository().findByMovieName(screening.getMovieName()).getType(),
                MovieCommands.getMovieRepository().findByMovieName(screening.getMovieName()).getDuration(),
                screening.getRoomName(),
                screening.getStartDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
