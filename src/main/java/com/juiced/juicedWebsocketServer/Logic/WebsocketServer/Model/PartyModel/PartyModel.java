package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.PartyModel;

public class PartyModel {
    private int characterIdOne;
    private int characterIdTwo;


    public PartyModel() {
    }

    public PartyModel(int characterIdOne, int characterIdTwo) {
        this.characterIdOne = characterIdOne;
        this.characterIdTwo = characterIdTwo;
    }

    public int getCharacterIdOne() {
        return characterIdOne;
    }

    public void setCharacterIdOne(int characterIdOne) {
        this.characterIdOne = characterIdOne;
    }

    public int getCharacterIdTwo() {
        return characterIdTwo;
    }

    public void setCharacterIdTwo(int characterIdTwo) {
        this.characterIdTwo = characterIdTwo;
    }
}
