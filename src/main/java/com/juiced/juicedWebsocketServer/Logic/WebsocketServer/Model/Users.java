package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model;

import java.util.ArrayList;
import java.util.List;

public class Users {
    private List<User> users;

    public Users(){
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
