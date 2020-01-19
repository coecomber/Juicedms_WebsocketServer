package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Battle;

public class BattleModel {
    private float timeToKill;
    private String monster;

    public BattleModel() {
    }

    public float getTimeToKill() {
        return timeToKill;
    }

    public void setTimeToKill(float timeToKill) {
        this.timeToKill = timeToKill;
    }

    public String getMonster() {
        return monster;
    }

    public void setMonster(String monster) {
        this.monster = monster;
    }
}
