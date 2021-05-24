package DiscordBOT.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.web.client.RestTemplate;

import java.util.List;
//!bobo join
public class CommandNotValid extends  Command{


    public CommandNotValid(GuildMessageReceivedEvent event) {
        super(event);
    }

    @Override
    public void execute(List<String> params) {
        event.getChannel().sendMessage("Command not recongized").queue();
    }
}
