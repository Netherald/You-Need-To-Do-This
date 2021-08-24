package net.netherald.yntdt.command;

import org.javacord.api.DiscordApi;

public abstract class CommandHandler {
    public final DiscordApi api;
    public CommandHandler(DiscordApi api) {
        this.api = api;
    }
    public abstract void init();
}
