package DiscordBOT.Commands;

import com.example.ApiBotDiscord.Model.RSSFeed;
import com.example.ApiBotDiscord.Model.User;
import com.example.ApiBotDiscord.Repositories.UserRepository;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscribeCommand extends Command{
    private final static String getRssUrl = "http://localhost:3000" +
            "/users/{discordId}/@{rssName}";
    private static RestTemplate restTemplate=  new RestTemplate();
    private UserRepository userRepository;
    public SubscribeCommand(GuildMessageReceivedEvent event) {
        super(event);
    }

    @Override
    public void execute(List<String> params) {
        if (event.getMember().getUser().isBot())return;
        if(params.size()!=1) return;

        Map<String,String> args = new HashMap<>();
        args.put("rssName",params.get(0));
        args.put("discordId",event.getMember().getUser().getId());
        ResponseEntity<String> response;
        try{
            response= restTemplate.exchange(getRssUrl,
                    HttpMethod.PUT,null,
                    new ParameterizedTypeReference<String>(){},args
            );
            event.getChannel().sendMessage("You subscribed to "+params.get(0)).queue();
        } catch (RestClientException e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
