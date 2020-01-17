package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model;

import org.eclipse.jetty.websocket.api.Session;

public class User {
    private String username;
    private String email;
    protected Session session;

    public User()
    {
    }

    public User(String username, String email)
    {
        this.username = username;
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Session getSession()
    {
        return session;
    }

    public void setSession(Session session)
    {
        this.session = session;
    }
}
