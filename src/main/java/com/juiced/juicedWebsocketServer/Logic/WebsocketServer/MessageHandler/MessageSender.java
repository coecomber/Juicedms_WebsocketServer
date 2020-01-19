package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.MessageHandler;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.MessageConverter;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.UserCollection;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.User;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.WsReturnMessage;

import java.io.IOException;

public class MessageSender {

    //Sends the correct list of logged in characters to everyone.
    public static void UpdatePlayers() throws IOException {
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

    public static void SendPlayerInformation(){

    }
}
