package com.example.ApiBotDiscord.Repositories;

import com.example.ApiBotDiscord.Model.RSSFeed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.net.URL;
import java.util.Optional;

public interface RSSFeedRepository extends MongoRepository<RSSFeed,String> {
    Optional<RSSFeed>findByLink(URL link);
    Optional<RSSFeed>findByRssName(String rssName);
    long deleteByRssName(String rssName);
}
