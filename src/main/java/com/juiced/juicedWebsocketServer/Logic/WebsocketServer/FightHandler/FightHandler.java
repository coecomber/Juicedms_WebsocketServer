package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.FightHandler;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.User;

import java.util.concurrent.TimeUnit;

public class FightHandler {

    public void checkBattleProgress(User user) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(200);
    }
}
