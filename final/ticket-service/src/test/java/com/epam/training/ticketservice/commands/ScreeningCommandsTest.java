package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.models.Screening;
import com.epam.training.ticketservice.repositories.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ScreeningCommandsTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @InjectMocks
    private ScreeningCommands screeningService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteScreeningSuccess() {

        String movieTitle = "TestMovie";
        String roomName = "TestRoom";
        String startDateTime = "2023-01-01 12:00";

        UserCommands.setAdminLoggedIn(true);

        Screening screeningToDelete = new Screening();
        when(screeningRepository.findByMovieNameAndRoomNameAndStartTime(Mockito.any(),
                Mockito.any(), Mockito.any()))
                .thenReturn(screeningToDelete);

        String result = screeningService.deleteScreening(movieTitle, roomName, startDateTime);

        assertEquals("Screening deleted successfully", result);
    }
}