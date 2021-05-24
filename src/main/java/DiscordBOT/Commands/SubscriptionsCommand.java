package DiscordBOT.Commands;

import com.example.ApiBotDiscord.Model.RSSFeed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//!bobo subscriptions
public class SubscriptionsCommand extends Command{
    private static final String url = "http://localhost:3000/rss/{discordId}" +
            "/subscriptions";
    private static RestTemplate restTemplate = new RestTemplate();
    public SubscriptionsCommand(GuildMessageReceivedEvent event) {
        super(event);
    }

    @Override
    public void execute(List<String> params) {
        if(event.getMember().getUser().isBot())return;
        if(params.size()!=0) {
            event.getChannel().sendMessage("This command doesn't need params").queue();
            return;
        }
        Map <String,String> args = new HashMap<>();
        args.put("discordId",event.getMember().getUser().getId());
        try{
            ResponseEntity<String> response=  restTemplate.exchange(
                    url, HttpMethod.GET,
                    null, new ParameterizedTypeReference<String>(){},args
            );
            event.getChannel().sendMessage(response.getBody()).queue();
        } catch (RestClientException e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }


    }
}
