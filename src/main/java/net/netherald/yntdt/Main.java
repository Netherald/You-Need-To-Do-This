package net.netherald.yntdt;

import net.netherald.yntdt.command.SlashCommandHandler;
import net.netherald.yntdt.command.TextCommandHandler;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class Main {

    public static Logger LOGGER = Logger.getLogger("YNTDT");
    private static DiscordApi api;

    public static void main(String[] args) {
        String token = "";
        api = new DiscordApiBuilder().setToken(token).login().join();
        new SlashCommandHandler(api).init();
        new TextCommandHandler(api).init();
        inputConsole();
    }

    private static void inputConsole() {
        new Thread(() -> {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            while (true) {
                try {
                    String line = reader.readLine();
                    api.getServerById(796384846238842881L).get().getChannelById(796384846704279555L).get().asTextChannel().get().sendMessage(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).run();

    }
}
