package com.epam.training.ticketservice.commands;

import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;
import org.jline.utils.AttributedString;


@Configuration
public class Prompt implements PromptProvider {

    @Override
    public final AttributedString getPrompt() {
        // Customize the prompt as needed
        return new AttributedString("Ticket service>");
    }

}
