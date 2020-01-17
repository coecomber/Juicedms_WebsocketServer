package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Setup;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.UserCollection;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;

import java.io.IOException;

@WebSocket
public class WebSocketJetty
{
    @OnWebSocketConnect
    public void onConnect(Session session) {
        if(session.isOpen()){
            ServiceBean.getService().addSession(session);
        }
    }

    @OnWebSocketClose
    public void onClose(Session session, int _closeCode, String _closeReason) throws IOException {
        ServiceBean.getService().removeSession(session);
        UserCollection.getConnectedUsers().remove(UserCollection.getUserBySession(session));
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String text) throws IOException, InterruptedException {
        ServiceBean.getService().sendMessage(session, text);
    }

    @OnWebSocketError
    public void error(Session session, Throwable t){
        System.out.println("On error called.");
        System.out.println(session.toString());
        System.out.println(session);
        System.out.println(t.toString());
    }
}
