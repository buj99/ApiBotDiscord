package DiscordBOT.Events;

import DiscordBOT.Commands.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CommandMessage extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        List<String> messageSplit= new LinkedList<>();
        messageSplit= Arrays.asList(event.getMessage().getContentRaw().split(
                " "));
        if(messageSplit.get(0).equalsIgnoreCase("!bobo")){
            Command command = null;
            if(messageSplit.size()==1){
                command= new HelpCommand(event);
            }
            else{
                switch (messageSplit.get(1)){
                    case "join": command=new JoinCommand(event); break;
                    case "feeds":command=new FeedsCommand(event);break;
                    case "add":command=new AddFeedCommand(event);break;
                    case "subscriptions" : command=
                            new SubscriptionsCommand(event);break;
                    case "subscribe":
                        command = new SubscribeCommand(event);break;
                    case "unsubscribe":command =new UnsubscribeCommand(event);break;
                    default:command = new CommandNotValid(event); break;
                }
                command.execute(messageSplit.subList(2, messageSplit.size()));
                return ;
            }
            command.execute(new LinkedList<String>());
        }


    }
}
