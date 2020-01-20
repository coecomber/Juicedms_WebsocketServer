package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.MessageHandler;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.MessageConverter;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.UserCollection;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Character.CharacterModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.PartyModel.PartyModelForClient;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.User;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.WsReturnMessage;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class MessageSender {



    //Sends the correct list of logged in characters to everyone.
    public static void UpdateConnectedPlayers() throws IOException {
        String users = "";
        for (User u : UserCollection.getConnectedUsers()) {
            users = users + u.getUsername() + ", ";
        }

        WsReturnMessage wsReturnMessage = new WsReturnMessage();
        wsReturnMessage.setAction("UPDATEPLAYERS");
        wsReturnMessage.setContent(users);

        for (User u : UserCollection.getConnectedUsers()) {
            System.out.println(u.getUsername());
            u.getSession().getRemote().sendString(MessageConverter.FromObjectToString(wsReturnMessage));
        }
    }

    public static void UpdateParties() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        WsReturnMessage wsReturnMessage = new WsReturnMessage();
        wsReturnMessage.setAction("UPDATEPARTY");

        for (User u : UserCollection.getConnectedUsers()) {
            //Check if current user is in a party
            CharacterModel character = restTemplate.getForObject("http://localhost:8550/character/characterbyname/" + u.getUsername(), CharacterModel.class);
            PartyModelForClient partyModelForClient = restTemplate.getForObject("http://localhost:8550/party/partymodelbycharacterid/" + character.getId(), PartyModelForClient.class);

            wsReturnMessage.setContent(partyModelForClient);
            Session session = u.getSession();
            session.getRemote().sendString(MessageConverter.FromObjectToString(wsReturnMessage));
        }
    }

    public static void SendPlayerInformation(){
    }
}
