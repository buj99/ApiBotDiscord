package com.example.ApiBotDiscord.RSS;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

//https://slipstick.com/feed
public class Main {
    public static void main(String []args){
        URL feedUrl = null;
        try {
            feedUrl = new URL("https://css-tricks.com/feed  ");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        SyndFeedInput input = new SyndFeedInput();
        try {
            SyndFeed feed = input.build(new XmlReader(feedUrl));
            List entries  = feed.getEntries();
            System.out.println(feed.getDescription());
            Iterator itEntries =  entries.iterator();
            while(itEntries.hasNext()){
                SyndEntry entry = (SyndEntry) itEntries.next();
                System.out.println("Title ==> " + entry.getTitle());
                System.out.println("Link ==> " + entry.getLink());
                System.out.println("Link ==> " + entry.getAuthor());

            }

        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
