package net.netherald.yntdt.command;

import net.netherald.yntdt.Main;
import net.netherald.yntdt.data.TodoStatus;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class SlashCommandHandler extends CommandHandler {

    public SlashCommandHandler(DiscordApi api) {
        super(api);
    }

    @Override
    public void init() {
        SlashCommand.with("create", "Create Todo",
                        List.of(
                                SlashCommandOption.create(SlashCommandOptionType.STRING, "content", "todo content", true)
                        )).
                createForServer(api.getServerById(796384846238842881L).orElseThrow());
        
        SlashCommand.with("status", "Update Todo Status",
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.STRING, "name", "todo name", true),
                                SlashCommandOption.createWithChoices(SlashCommandOptionType.STRING, "status", "todo status", true,
                                        Arrays.asList(SlashCommandOptionChoice.create("Preparing", "Preparing"),
                                                SlashCommandOptionChoice.create("Working", "Working"),
                                                SlashCommandOptionChoice.create("Completion", "Completion")))
                        )).
                createForServer(api.getServerById(796384846238842881L).orElseThrow());


        api.addSlashCommandCreateListener(event -> {
            SlashCommandInteraction interaction = event.getSlashCommandInteraction();
            if (interaction.getCommandName().equals("create")) {
                String content = interaction.getOptions().get(0).getStringValue().orElseThrow();
                interaction.createImmediateResponder().addEmbed(new EmbedBuilder()
                        .setTitle("Todo Created!")
                        .setDescription("Title: " + content)
                        .setColor(Color.cyan)).respond();
                Main.todoList.put(content, TodoStatus.Preparing);
            } else if (interaction.getCommandName().equals("status")) {
                String content = interaction.getOptions().get(0).getStringValue().orElseThrow();
                String status = interaction.getOptions().get(1).getStringValue().orElseThrow();
                if (Main.todoList.containsKey(content)) {
                    interaction.createImmediateResponder().addEmbed(new EmbedBuilder()
                            .setTitle("Todo Updated!")
                            .setDescription("Title: " + content + "\nStatus: " + status)
                            .setColor(Color.cyan)).respond();
                    Main.todoList.put(content, TodoStatus.valueOf(status));
                } else {
                    interaction.createImmediateResponder().addEmbed(new EmbedBuilder()
                            .setTitle("Todo Updated Failure")
                            .setDescription("Error: todo not found")
                            .setColor(Color.red)).respond();
                }
            }
        });
    }
}
