package com.example.ApiBotDiscord.Model;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@Document
public class User {
    @Id
    private String id;
    @Field
    private String userName;
    @Field
    private String idDiscord;
    @Field
    private List<String> subscriptions = new LinkedList<>();
    public User(){}
    public User(String userName ,String idDiscord) {
        this.userName = userName;
        this.idDiscord=idDiscord;
    }

    public String getIdDiscord() {
        return idDiscord;
    }

    public void setIdDiscord(String idDiscord) {
        this.idDiscord = idDiscord;
    }

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addSubscription(String rssName){
        this.subscriptions.add(rssName);
    }
    public void removeSubscription(String rssName){
        this.subscriptions.remove(rssName);
    }

    @Override
    public String toString() {
        return String.format("User[id='%s',userName='%s',idDiscord='%s'," +
                        "subscriptions='%s',]"
                , id
                , userName,idDiscord,subscriptions);
    }
    public String toJSON(){
        return String.format("{\"userName\":\"%s\"," +
                        "\"idDiscord\":\"%s\"," +
                        "\"subscriptions\":%s}",
                userName,idDiscord,subscriptions);
    }
}
