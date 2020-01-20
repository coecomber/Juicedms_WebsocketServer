package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Logic;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Character.CharacterModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.web.client.RestTemplate;
import static com.google.common.truth.Truth.assertThat;

class handleMessageLogicTest {

    User user;
    RestTemplate restTemplate;

    @BeforeAll
    public void setValues(){
        user = new User("Juiced", "joostvherwaarden@gmail.com");
        restTemplate = new RestTemplate();
    }

    @Test
    void sendBattleInformation() {
        CharacterModel characterModel = restTemplate.getForObject("http://localhost:8550/character/characterbyname/" + user.getUsername(), CharacterModel.class);
        assertThat(characterModel.getId() == 1);
    }

    @Test
    void addGold() {
        CharacterModel character = restTemplate.getForObject("http://localhost:8550/character/characterbyname/" + user.getUsername(), CharacterModel.class);
        int expectedGold = character.getGold() + 10;
        assertThat(expectedGold == character.getGold() + 10);
    }

    @Test
    void checkUser() {
    }

    @Test
    void createParty() {
    }

    @Test
    void leaveParty() {
    }
}