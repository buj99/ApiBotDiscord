package DiscordBOT;

import DiscordBOT.Events.CommandMessage;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class BOT {
    public static void main(String []args){
        JDA jda;
        try {
            jda= JDABuilder.createDefault("ODQ0NDUyMDA0NzY3NTk2NTQ0.YKSnNw.w6nVsbDlt6_Qbxrz6n5oqw5GfVM").build();
            jda.addEventListener(new CommandMessage());
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }
}
