package DiscordBOT.Commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public abstract class Command {
    GuildMessageReceivedEvent event =  null;

    public Command(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    public void execute(List<String> params) {

    }
}