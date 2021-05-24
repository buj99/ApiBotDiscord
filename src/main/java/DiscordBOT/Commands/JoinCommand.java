package DiscordBOT.Commands;

import com.example.ApiBotDiscord.Model.User;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JoinCommand extends Command{

    private static final  String url  =  "http://localhost:3000/users";
    private static RestTemplate restTemplate=  new RestTemplate();

    public JoinCommand(GuildMessageReceivedEvent event) {
        super(event);
    }

    @Override
    public void execute(List<String> params ) {
        if(params.size()!=0) return;
        if(event.getMember().getUser().isBot())return ;
        User newUser =  new User(event.getMember().getUser().getName(),
                event.getMember().getUser().getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<String>(newUser.toJSON(),headers);

        ResponseEntity<String> response =  restTemplate.postForEntity(url,
                request,String.class);
        if(response.getStatusCode().equals(HttpStatus.CREATED)){
            String name= event.getMember().getUser().getName();
            event.getChannel().sendMessage("Hope my RSS feeds are interesting for " +
                    "you"+name+" ! ").queue();
            return;
        }else if(response.getStatusCode().equals(HttpStatus.IM_USED)){
            event.getChannel().sendMessage("You have already joined :D ").queue();
            return;
        }
        event.getChannel().sendMessage("A problem has occured while registering " +
                "your acount , please leave the server and rejoin").queue();

    }
}
