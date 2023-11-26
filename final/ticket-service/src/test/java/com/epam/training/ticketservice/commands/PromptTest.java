package com.epam.training.ticketservice.commands;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.jline.utils.AttributedString;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomPromptTest {

    @InjectMocks
    private Prompt customPrompt;

    public CustomPromptTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPrompt_ShouldReturnExpectedPrompt() {

        String expectedPrompt = "Ticket service>";

        AttributedString result = customPrompt.getPrompt();

        assertEquals(expectedPrompt, result.toString());
    }
}