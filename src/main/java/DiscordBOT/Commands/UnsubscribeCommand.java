package DiscordBOT.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//!bobo unsubscribe
public class UnsubscribeCommand extends Command{
    private final static String getRssUrl = "http://localhost:3000" +
            "/users/{discordId}/@{rssName}";
    private static RestTemplate restTemplate=  new RestTemplate();
    public UnsubscribeCommand(GuildMessageReceivedEvent event) {
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
                    HttpMethod.DELETE,null,
                    new ParameterizedTypeReference<String>(){},args
            );
            event.getChannel().sendMessage("You unsubscribed from "+params.get(0)).queue();
        } catch (RestClientException e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }
}
