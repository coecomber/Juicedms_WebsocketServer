package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Setup;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.UserCollection;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.MessageHandler.MessageHandler;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service("WebSocketService")
public class WebSocketService
{
    private MessageHandler messageHandler = new MessageHandler();
    private Set<Session> listenerSessions = new CopyOnWriteArraySet<>();

    public void removeSession(Session session) throws IOException {
        UserCollection userCollection = new UserCollection();
        userCollection.removeBySessionError(session);
        session.close();
        listenerSessions.remove(session);
    }

    public void addSession(Session session){
        listenerSessions.add(session);
    }

    public void sendMessage(Session session, String message) throws IOException, InterruptedException {
        JSONObject jsonObject = new JSONObject(message);
        messageHandler.handleMessage(session,  jsonObject);
    }

    @PostConstruct
    private void init(){
        Runnable runnable = () -> {
            while (true){

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void sendRandomName(Session s, String message) {
        try {
            s.getRemote().sendString(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}