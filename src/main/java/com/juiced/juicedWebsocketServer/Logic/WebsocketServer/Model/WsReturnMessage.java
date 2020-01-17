package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model;

public class WsReturnMessage {
    public WsReturnMessage() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    String action;
    Object content;
}
