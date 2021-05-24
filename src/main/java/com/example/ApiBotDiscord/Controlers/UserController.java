package com.example.ApiBotDiscord.Controlers;

import com.example.ApiBotDiscord.Model.RSSFeed;
import com.example.ApiBotDiscord.Model.User;
import com.example.ApiBotDiscord.Repositories.RSSFeedRepository;
import com.example.ApiBotDiscord.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final RSSFeedRepository rssFeedRepository;
    @Autowired
    public UserController(UserRepository userRepository, RSSFeedRepository rssFeedRepository) {
        this.userRepository = userRepository;
        this.rssFeedRepository = rssFeedRepository;
    }

    //GET All users of the bot
    @GetMapping
    public List<User> getAllUsers(){return userRepository.findAll();}

    //GET user by server and username
    @GetMapping("/{discordId}")
    public String getUserByServerAndUserName (
            @PathVariable("discordId") String IdDiscord){
        Optional<User> _user=
                userRepository.findByIdDiscord(IdDiscord);
        if(_user.isPresent())return _user.get().toJSON();
        return null;
    }

    //Create new user
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createUser(@RequestBody User newUser){
        Optional<User> user =
                userRepository.findByIdDiscord(newUser.getIdDiscord());
        if (user.isPresent())return new ResponseEntity<>("User already exists",
                HttpStatus.IM_USED);
        userRepository.save(newUser);
        return new ResponseEntity<>("User Added", HttpStatus.CREATED);
    }

    //updating username
    @PutMapping("/{discordId}/{newUserName}")
    public ResponseEntity<String> updateUsername(
            @PathVariable("discordId") String idDiscord ,
            @PathVariable("newUserName") String newUserName){
        Optional<User> user  = userRepository.findByIdDiscord(idDiscord);
        if(user.isPresent()){
            User _user =  user.get();
            _user.setUserName(newUserName);
            userRepository.save(_user);
            return new ResponseEntity<String>( HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<String>("User doesn't exist" ,
                    HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{discordId}/@{rssName}")
    public  ResponseEntity<String> addRssToSubscribe(@PathVariable("discordId")String idDiscord,
                                                     @PathVariable("rssName")String rssName){
        Optional<User> _user = userRepository.findByIdDiscord(idDiscord);
        Optional<RSSFeed>_rssFeed=  rssFeedRepository.findByRssName(rssName);
        if(!_user.isPresent()) return new ResponseEntity<String>("This user " +
                "doesn't exist", HttpStatus.NOT_FOUND);
        if(!_rssFeed.isPresent())return new ResponseEntity<String>("This RSS " +
                "feed doesn't exist", HttpStatus.NOT_FOUND);
        _user.get().addSubscription(rssName);
        _rssFeed.get().incrementSubscriberCount();
        userRepository.save(_user.get());
        rssFeedRepository.save(_rssFeed.get());
        return new ResponseEntity<String>(HttpStatus.CREATED);
    }

    //delete user
    @DeleteMapping("/{discordId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("discordId")String idDiscord){
        Long nrOfDeletes = userRepository.deleteUserByIdDiscord(idDiscord);
        if(nrOfDeletes==0) return new ResponseEntity<String>("User doesn't " +
                "exist",HttpStatus.NOT_FOUND);
        return new ResponseEntity<String>( HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{discordId}/@{rssName}")
    public  ResponseEntity<String> removeRssFromSubscribeList(
            @PathVariable("discordId")String idDiscord,
            @PathVariable("rssName")String rssName){

        Optional<User> _user = userRepository.findByIdDiscord(idDiscord);
        Optional<RSSFeed>_rssFeed=  rssFeedRepository.findByRssName(rssName);
        if(!_user.isPresent()) return new ResponseEntity<String>("This user " +
                "doesn't exist", HttpStatus.NOT_FOUND);
        if(!_rssFeed.isPresent())return new ResponseEntity<String>("This RSS " +
                "feed doesn't exist", HttpStatus.NOT_FOUND);
        _user.get().getSubscriptions().remove(rssName);
        _rssFeed.get().decrementSubscriberCount();
        userRepository.save(_user.get());
        rssFeedRepository.save(_rssFeed.get());
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }
}
