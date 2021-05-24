package DiscordBOT.Commands;

import com.example.ApiBotDiscord.Model.RSSFeed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class FeedsCommand extends Command{
    private static final String url = "http://localhost:3000/rss";
    private static RestTemplate restTemplate = new RestTemplate();
    public FeedsCommand(GuildMessageReceivedEvent event) {
        super(event);
    }
//!bobo feeds
    @Override
    public void execute(List<String> params) {
        if(event.getMember().getUser().isBot())return;
        if(params.size()!=0) {
            event.getChannel().sendMessage("This command doesn't need params").queue();
            return;
        }
        ResponseEntity<List<RSSFeed>> response=  restTemplate.exchange(
                url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<RSSFeed>>(){}
        );
        List<RSSFeed> result = response.getBody();
        result.forEach(rssFeed ->{
            String message =  String.format("Category : %s \n Name : %s \n " +
                    "Link : %s \n",rssFeed.getCategory(),rssFeed.getRssName()
                    ,rssFeed.getLink());
            event.getChannel().sendMessage(message).queue();
        });

    }
}
