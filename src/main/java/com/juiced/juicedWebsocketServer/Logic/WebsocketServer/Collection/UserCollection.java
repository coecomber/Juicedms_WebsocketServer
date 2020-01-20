package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.MessageHandler.MessageSender;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.User;
import org.aspectj.bridge.Message;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserCollection {
    private static List<User> ConnectedUsers = new ArrayList<>();

    public static List<User> getConnectedUsers() {
        List<User> userListToReturn = new ArrayList<>();

        return ConnectedUsers;
    }

    public static void setConnectedUsers(List<User> connectedUsers) {
        ConnectedUsers = connectedUsers;
    }

    public static boolean CheckNotRegistered(String username){
        for (User u : ConnectedUsers){
            if(u.getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

    public static User getUserBySession(Session session) {
        for (User u : ConnectedUsers) {
            if (u.getSession() == session) {
                return u;
            }
        }
        return null;
    }

    public boolean removeBySessionError(Session session) throws IOException {
        User userToRemove = new User();

        for (User u : ConnectedUsers){
            if(u.getSession().getLocalAddress() == session.getLocalAddress()) {
                System.out.println("Need to remove user " + u.getUsername());
                userToRemove = u;
                if(userToRemove != null){
                    UserCollection.getConnectedUsers().remove(userToRemove);
                    session.close();
                    System.out.println(userToRemove.getUsername() + " removed.");
                    MessageSender.UpdateConnectedPlayers();
                    userToRemove = null;
                }
            }
        }
        return true;
    }
}
