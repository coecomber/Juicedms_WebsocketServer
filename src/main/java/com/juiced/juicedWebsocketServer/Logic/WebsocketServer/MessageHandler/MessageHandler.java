package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.MessageHandler;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.MessageConverter;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.UserCollection;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Logic.handleMessageLogic;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.User;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

public class MessageHandler {

    RestTemplate restTemplate = new RestTemplate();
    handleMessageLogic handleMessageLogic = new handleMessageLogic();

    public void handleMessage(Session session, JSONObject message) throws IOException {
        User userFromJSON = (User) MessageConverter.FromGsonToObject(new User(), message.getJSONObject("User").toString());

        switch (message.getString("Action")) {
            case "STARTBATTLE":
                System.out.println("STARTBATTLE called.");
                break;
            case "REGISTER":
                //Checks if player has session already. If so, change the user object to the new one.
                User user = (User) MessageConverter.FromGsonToObject(new User(), message.getJSONObject("User").toString());
                user.setSession(session);
                for(User currentUser : UserCollection.getConnectedUsers()){
                    if(session == currentUser.getSession()){
                        System.out.println("Already have session");
                        user = UserCollection.getUserBySession(session);
                        user.setUsername(userFromJSON.getUsername());
                    }
                }

                handleMessageLogic.sendBattleInformation(session, user);

                if (UserCollection.CheckNotRegistered(user.getUsername())) {
                    UserCollection.getConnectedUsers().add(user);
                } else{
                    System.out.println("Player " + user.getUsername() + " reconnected.");
                }

                MessageSender.UpdatePlayers();
                break;
            case "NEWFIGHT":
                handleMessageLogic.addGold(userFromJSON, message.getInt("GoldToAdd"));
                handleMessageLogic.sendBattleInformation(session, userFromJSON);
        }
    }
}
