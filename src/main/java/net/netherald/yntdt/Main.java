package net.netherald.yntdt;

import net.netherald.yntdt.command.SlashCommandHandler;
import net.netherald.yntdt.data.TodoStatus;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.interaction.MessageComponentInteraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Main {
    public static final String MESSAGE_ID = "879717347500376126";
    public static Logger LOGGER = Logger.getLogger("YNTDT");
    public static Map<String, TodoStatus> todoList = new HashMap<>();
    private static DiscordApi api;

    public static void main(String[] args) {
        String token = "";
        api = new DiscordApiBuilder().setToken(token).login().join();
        new SlashCommandHandler(api).init();

        inputConsole();

        api.addMessageComponentCreateListener(event -> {
            MessageComponentInteraction interaction = event.getMessageComponentInteraction();
            String customId = interaction.getCustomId();

            switch (customId) {
                case "preparing" -> {
                    updateMessage("p");
                    interaction.createImmediateResponder().respond();
                }
                case "working" -> {
                    updateMessage("w");
                    interaction.createImmediateResponder().respond();
                }
                case "complete" -> {
                    updateMessage("c");
                    interaction.createImmediateResponder().respond();
                }
            }
        });
    }

    private static void inputConsole() {
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            while (true) {
                try {
                    String line = reader.readLine();
                    api.getServerById(796384846238842881L).orElseThrow().getChannelById(796384846704279555L).orElseThrow().asTextChannel().orElseThrow().sendMessage(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void updateMessage(String id) {
        Message message = api.getServerById(796384846238842881L).orElseThrow()
                .getTextChannelById(856008843586568252L).orElseThrow()
                .getMessageById(MESSAGE_ID).join();

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Netherald To-Do");
        List<String> preparing = new ArrayList<>(), working = new ArrayList<>(), completion = new ArrayList<>();
        todoList.forEach((s, todoStatus) -> {
            if (todoStatus.equals(TodoStatus.Preparing)) preparing.add(s);
            if (todoStatus.equals(TodoStatus.Working)) working.add(s);
            if (todoStatus.equals(TodoStatus.Completion)) completion.add(s);
        });
        StringBuilder strBuilder = new StringBuilder();
        if (id.equals("p")) {
            strBuilder.append("Preparing:\n");
            preparing.forEach(s -> {
                strBuilder.append(s).append("\n");
            });
        }
        if (id.equals("w")) {
            strBuilder.append("Working:\n");
            working.forEach(s -> {
                strBuilder.append(s).append("\n");
            });
        }
        if (id.equals("c")) {
            strBuilder.append("Completion:\n");
            completion.forEach(s -> {
                strBuilder.append(s).append("\n");
            });
        }

        builder.setTitle(strBuilder.toString());

        message.edit("**Netherald To-Do**", builder);
    }
}
