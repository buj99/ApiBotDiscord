package DiscordBOT.Commands;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
//!bobo help
public class HelpCommand extends Command{
    public HelpCommand(GuildMessageReceivedEvent event) {
        super(event);
    }
    @Override
    public void execute(List<String> params) {
        StringBuilder helpMessage =  new StringBuilder();
        helpMessage.append("!bobo join -> register\n\n")
                .append("!bobo add <category> <URL> -> add a new feed to the " +
                        "rss feed list [Only for Admins] \n\n")
                .append("!bobo feeds -> displays all available feeds\n\n")
                .append("!bobo subscribe <RSS feed name> ->add rss feed to " +
                        "your subscribe list\n\n")
                .append("!bobo unsubscribe <RSS feed name> -> remove rss feed" +
                        " from your subscriptions list\n\n")
                .append("!bobo subscriptions -> gives you all rss feeds from " +
                        "your subscription list\n\n");
        event.getChannel().sendMessage(helpMessage.toString()).queue();
    }
}
