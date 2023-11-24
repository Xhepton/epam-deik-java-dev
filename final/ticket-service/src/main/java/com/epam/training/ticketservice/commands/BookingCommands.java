package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Booking;
import com.epam.training.ticketservice.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@ShellComponent
public class BookingCommands {

    private static BookingRepository bookingRepository;

    @Autowired
    public BookingCommands(BookingRepository bookingRepository) {
        BookingCommands.bookingRepository = bookingRepository;
    }

    @ShellMethod(key = "book", value = "booking tickets")
    public String bookTicket(String movieName, String roomName, String startDateTimeS, String seats) {
        if (UserCommands.getLoggedInUsername() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeS, formatter);

            List<String> bookedSeats = Arrays.asList(seats.split(" "));

            String seatsToPrint = "";

            for (String seatToken : bookedSeats) {
                String[] seatInfo = seatToken.split(",");

                int row = Integer.parseInt(seatInfo[0]);
                int column = Integer.parseInt(seatInfo[1]);

                if (bookingRepository.existsByRowAndColumn(row, column)) {
                    return String.format("Seat (%d,%d) is already taken", row, column);
                } else {
                    Booking booking = new Booking(movieName, roomName, startDateTime, row, column, UserCommands.getLoggedInUsername());
                    bookingRepository.save(booking);

                    if (seatToken.equals(bookedSeats.get(bookedSeats.size() - 1))) {
                        seatsToPrint = seatsToPrint + String.format("(%d,%d);", row, column);
                    } else {
                        seatsToPrint = seatsToPrint + String.format("(%d,%d), ", row, column);
                    }
                }
            }
            return String.format("Seats booked: %s the price for this booking is %d HUF", seatsToPrint, bookedSeats.size() * 1500);
        }
        else {
            return "";
        }
    }
}
