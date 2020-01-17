package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Setup;

import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class WebSocketServlet extends org.eclipse.jetty.websocket.servlet.WebSocketServlet
{
    @Override
    public void configure(WebSocketServletFactory webSocketServletFactory) {
        //webSocketServletFactory.getPolicy().setIdleTimeout(10000);
        webSocketServletFactory.register(WebSocketJetty.class);
    }
}
