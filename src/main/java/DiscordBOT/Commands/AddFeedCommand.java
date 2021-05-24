package DiscordBOT.Commands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//!bobo add <category> <URL>
public class AddFeedCommand extends Command{
    private static final  String url  =  "http://localhost:3000/rss/{category}";
    private static RestTemplate restTemplate=  new RestTemplate();
    public AddFeedCommand(GuildMessageReceivedEvent event) {
        super(event);
    }

    @Override
    public void execute(List<String> params) {
        if(event.getMember().getUser().isBot())return;
        //check if user is Admin

        Role adminRole = event.getMember().getRoles()
                .stream()
                .filter(role -> role.getName().equals("Bobo Admin"))
                .findFirst()
                .orElse(null);
        if(adminRole==null) {
            event.getChannel().sendMessage("You need to be an admin to add " +
                    "more RSS feeds").queue();
        }
        //execute command
        if(params.size()!=2){
            event.getChannel().sendMessage("The correct form of the command " +
                    "is !bobo add <rssLink>");
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<String>(String.format("{\"link\":\"%s\"}",
                        params.get(1)),
                        headers);
        Map<String,String> args = new HashMap<>();
        args.put("category",params.get(0));
        ResponseEntity<String> response =
                restTemplate.postForEntity(url,request,String.class,args);
        if (response.getStatusCode()== HttpStatus.CREATED){
            event.getChannel().sendMessage("RSS feed added").queue();
            return;
        }
        event.getChannel().sendMessage("RSS feed wasn't added, someting went " +
                "rong").queue();
    }
}
