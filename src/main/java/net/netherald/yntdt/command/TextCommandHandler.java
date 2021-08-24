package net.netherald.yntdt.command;

import org.javacord.api.DiscordApi;

public class TextCommandHandler extends CommandHandler {

    public TextCommandHandler(DiscordApi api) {
        super(api);
    }

    @Override
    public void init() {
        api.addMessageCreateListener(event -> {
            var split = event.getMessageContent().split(" ");
            if (split[0].trim().equals("!todo")) {
                var text = commandFormattedString(split,2);
                if (split[1].trim().equals("create")) {
                    event.getChannel().sendMessage("creating : "+text);

                }
            }
        });
    }

    private String commandFormattedString(String[] content, int num) {
        var string = "";
        for (var i=0;i<content.length;i++) {
            if (i < num) {
                continue;
            }
            string += content[i];
            if (i != content.length-1) {
                string += " ";
            }
        }
        return string;
    }
}
