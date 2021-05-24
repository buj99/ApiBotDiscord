package com.example.ApiBotDiscord.Model;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.IOException;
import java.net.URL;

public class RSSFeed {
    @Id
    private String id;
    @Field
    private String rssName;
    @Field
    private URL link;
    @Field
    private String category="none";
    @Field
    private int subscribersCount=0;

    public RSSFeed(){}

    public RSSFeed(URL link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSubscribersCount() {
        return subscribersCount;
    }

    public void setSubscribersCount(int subscribersCount) {
        this.subscribersCount = subscribersCount;
    }

    public String getRssName() {
        return rssName;
    }

    public void setRssName(String rssName) {
        this.rssName = rssName;
    }

    public void incrementSubscriberCount(){this.subscribersCount++;};
    public void decrementSubscriberCount(){this.subscribersCount--;};


    @Override
    public String toString() {
        return String.format("RSSFeed[id='%s',rssName='%s',link='%s'," +
                "category='%s'," +
                "subscribersCount='%s',]", id,rssName,link,category,
                subscribersCount);
    }

    public String toJSON(){
        return String.format("{\"id\":\"%s\",\"rssName\":\"%s\"," +
                        "\"link\":\"%s\"," +
                "\"category\":\"%s\",\"subscribersCount\":\"%s\",}",id,rssName,link,
                category,subscribersCount);
    }
}
