package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.PartyModel;

public class PartyModelForClient {
    private int characterIdOne;
    private int characterIdTwo;
    private String otherCharacterName;

    public PartyModelForClient() {
    }

    public PartyModelForClient(int characterIdOne, int characterIdTwo, String otherCharacterName) {
        this.characterIdOne = characterIdOne;
        this.characterIdTwo = characterIdTwo;
        this.otherCharacterName = otherCharacterName;
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

    public String getOtherCharacterName() {
        return otherCharacterName;
    }

    public void setOtherCharacterName(String otherCharacterName) {
        this.otherCharacterName = otherCharacterName;
    }
}
