package net.netherald.yntdt.command;

import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;

import java.util.Arrays;

public class SlashCommandHandler extends CommandHandler {

    public SlashCommandHandler(DiscordApi api) {
        super(api);
    }

    @Override
    public void init() {
        SlashCommand.with("todo","TODO", Arrays.asList(
                SlashCommandOption.createWithOptions(SlashCommandOptionType.SUB_COMMAND,"create","create new todo",
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.STRING,"content","todo content",true)
                        ))
        )).createGlobal(api).thenRun(()-> System.out.println("asdf"));



        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction interaction = event.getSlashCommandInteraction();
            String content = interaction.getOptions().get(0).getStringValue().orElseThrow();
            String commandName = interaction.getCommandName();
            if (commandName.equals("todo")) {
                interaction.createImmediateResponder()
                        .setContent(content)
                        .respond();
            }
        });
    }
}
