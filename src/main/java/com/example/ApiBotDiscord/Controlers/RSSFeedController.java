package com.example.ApiBotDiscord.Controlers;

import com.example.ApiBotDiscord.Model.RSSFeed;
import com.example.ApiBotDiscord.Model.User;
import com.example.ApiBotDiscord.Repositories.RSSFeedRepository;
import com.example.ApiBotDiscord.Repositories.UserRepository;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rss")
public class RSSFeedController {
    private final UserRepository userRepository;
    private final RSSFeedRepository rssFeedRepository;
    @Autowired
    public RSSFeedController(UserRepository userRepository,
                             RSSFeedRepository rssFeedRepository) {
        this.userRepository = userRepository;
        this.rssFeedRepository = rssFeedRepository;
    }

    //GET all rss feeds
    @GetMapping
    public List<RSSFeed> getAllRssFeeds(){return rssFeedRepository.findAll();}

    @GetMapping("/{rssName}")
    public ResponseEntity<String> getRssFeedByLink(@PathVariable("rssName")String rssName) {
            Optional<RSSFeed> rssFeed =
                    rssFeedRepository.findByRssName(rssName);
            if(rssFeed.isPresent()){
                URL feedUrl = rssFeed.get().getLink();

                SyndFeedInput input = new SyndFeedInput();
                try {
                    SyndFeed feed = input.build(new XmlReader(feedUrl));
                    List entries  = feed.getEntries();
                    Iterator itEntries =  entries.iterator();
                    String post = new String();
                    StringBuilder message = new StringBuilder();
                    while(itEntries.hasNext()){
                        SyndEntry entry = (SyndEntry) itEntries.next();
                        post = String.format("Title : %s\nAuthor : %s\nLink :" +
                                " %s\n\n",entry.getTitle(),entry.getAuthor(),
                                entry.getLink());
                        message.append(post);
                    }
                    return new ResponseEntity<String>(message.toString(),
                            HttpStatus.OK);

                } catch (FeedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{discordId}/subscriptions")
    public ResponseEntity<String> getAllFeedsFromSubscriptions(@PathVariable(
            "discordId")String idDiscord){
        Optional<User> _user= userRepository.findByIdDiscord(idDiscord);
        if(!_user.isPresent()) return new ResponseEntity<String>("User " +
                "doesn't exist.", HttpStatus.NOT_FOUND);
        StringBuilder message = new StringBuilder();
        _user.get().getSubscriptions().forEach(subscription->{
            Optional<RSSFeed> rssFeed =
                    rssFeedRepository.findByRssName(subscription);
            if(rssFeed.isPresent()){
                message.append(subscription+" :\n\n");
                URL feedUrl = rssFeed.get().getLink();

                SyndFeedInput input = new SyndFeedInput();
                try {
                    SyndFeed feed = input.build(new XmlReader(feedUrl));
                    List entries  = feed.getEntries();
                    Iterator itEntries =  entries.iterator();
                    String post = new String();
                    while(itEntries.hasNext()){
                        SyndEntry entry = (SyndEntry) itEntries.next();
                        post = String.format("Title : %s\nAuthor : %s\nLink :" +
                                        " %s\n\n",entry.getTitle(),entry.getAuthor(),
                                entry.getLink());
                        message.append(post);
                    }

                } catch (FeedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }

        });
        return new ResponseEntity<String>(message.toString(),HttpStatus.OK);
    }

    //Create new RSS feed
    @PostMapping(path="/{category}",consumes = "application/json")
    public ResponseEntity<String>createRssFeed(@RequestBody RSSFeed rssFeed ,
                                               @PathVariable("category")String category){

            Optional<RSSFeed> _rssFeed =
                    rssFeedRepository.findByLink(rssFeed.getLink());
            if(_rssFeed.isPresent()){
                return new ResponseEntity<String>("This rss feed already " +
                        "exists in DB.",HttpStatus.IM_USED);
            }
        SyndFeedInput input = new SyndFeedInput();
        try {
            SyndFeed feed= input.build(new XmlReader(rssFeed.getLink()));
            rssFeed.setRssName(feed.getTitle());
            rssFeed.setCategory(category);
        } catch (FeedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            rssFeedRepository.save(rssFeed);
            return new ResponseEntity<String>("RSS feed added succesfuly",
                    HttpStatus.CREATED);
    }

    @PutMapping(path="/{rssName}", consumes = "application/json")
    public ResponseEntity<String> changeRssLink (@PathVariable("rssName")String rssName,
                                                 @RequestBody RSSFeed rssFeed){
            Optional<RSSFeed> _rssFeed =
                    rssFeedRepository.findByRssName(rssName);
            if(_rssFeed.isPresent()){
                RSSFeed newFeed = _rssFeed.get();
                newFeed.setLink(rssFeed.getLink());
                SyndFeedInput input = new SyndFeedInput();
                try {
                    SyndFeed feed= input.build(new XmlReader(rssFeed.getLink()));
                    newFeed.setRssName(feed.getTitle());
                    rssFeedRepository.save(newFeed);
                    return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
                } catch (FeedException e) {
                    e.printStackTrace();
                    return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
                } catch (IOException e) {
                    e.printStackTrace();
                    return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{rssName}")
    public ResponseEntity<String> deleteRssFeed(@PathVariable("rssName")String rssName){
        long nrOfDeletes = rssFeedRepository.deleteByRssName(rssName);
        if(nrOfDeletes==0)return new ResponseEntity<String>("This rss feed " +
                "doesnt exist .",HttpStatus.NOT_FOUND);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }

}
