package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.MessageHandler;

import com.juiced.juicedWebsocketServer.Logic.WebsocketLogic;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.MessageConverter;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.UserCollection;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.User;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Users;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MessageHandler {
    private ExecutorService executorService = Executors.newFixedThreadPool(100);
    WebsocketLogic websocketLogic = new WebsocketLogic();

    public void handleMessage(Session session, JSONObject message) throws IOException, InterruptedException {
        switch (message.getString("Action")) {
            case "CREATE":
                System.out.println("create called.");
                break;
            case "LEAVELOBBY":
                //Doe wat leuks
                break;
            case "REGISTER":
                User user = (User) MessageConverter.FromGsonToObject(new User(), message.getJSONObject("User").toString());
                user.setSession(session);

                String allConnectedUsers = "";
                List<User> correctConnectedUsers = UserCollection.getConnectedUsers();
                for (User currentUser: correctConnectedUsers){
                    allConnectedUsers = allConnectedUsers + currentUser.getUsername();
                }

                Users users = new Users();
                users.setUsers(UserCollection.getConnectedUsers());

                for (User u : UserCollection.getConnectedUsers()) {
                    System.out.println(u.getUsername());
                    u.getSession().getRemote().sendString("---------players:--------");
                }

                MessageSender.UpdatePlayers();

                for (User u : UserCollection.getConnectedUsers()) {
                    System.out.println(u.getUsername());
                    u.getSession().getRemote().sendString("-----------------");
                }

                if (UserCollection.CheckNotRegistered(user)) {
                    UserCollection.getConnectedUsers().add(user);

                    List<User> connectedUsers = UserCollection.getConnectedUsers();

                    System.out.println("A new user joined. The current connected players are:");
                    for (User currentUser : connectedUsers) {
                        System.out.println(currentUser.getUsername());
                    }

                    System.out.println("-------------");
                    break;
                } else{
                    System.out.println("Player " + user.getUsername() + " reconnected.");
                }
        }
    }
}
