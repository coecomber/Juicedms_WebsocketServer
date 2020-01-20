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
                User user = handleMessageLogic.checkUser(message, session, userFromJSON);
                handleMessageLogic.sendBattleInformation(session, user);
                if (UserCollection.CheckNotRegistered(user.getUsername())) {
                    UserCollection.getConnectedUsers().add(user);
                }
                MessageSender.UpdateConnectedPlayers();
                MessageSender.UpdateParties();
                break;
            case "NEWFIGHT":
                handleMessageLogic.addGold(userFromJSON, message.getInt("GoldToAdd"));
                handleMessageLogic.sendBattleInformation(session, userFromJSON);
                MessageSender.UpdateParties();
                break;
            case "CREATEPARTY":
                System.out.println(userFromJSON.getUsername() + " wants to make a party with " + message.getString("partyToJoin"));
                handleMessageLogic.createParty(userFromJSON.getUsername(), message.getString("partyToJoin"));
                break;
            case "LEAVEPARTY":
                System.out.println("Leave party called for player: " + userFromJSON.getUsername());
                handleMessageLogic.leaveParty(userFromJSON);
                MessageSender.UpdateParties();
                break;
        }
    }
}
