package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Logic;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.MessageConverter;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Battle.BattleModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Character.CharacterModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Fight.CharacterFightModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.User;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.WsReturnMessage;

import org.eclipse.jetty.websocket.api.Session;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class handleMessageLogic {

    RestTemplate restTemplate = new RestTemplate();

    public void sendBattleInformation(Session session, User user) throws IOException {
        //Sends back player information to new player
        CharacterModel characterModel = restTemplate.getForObject("http://localhost:8550/get/characterbyname/" + user.getUsername(), CharacterModel.class);
        CharacterFightModel characterFightModel = new CharacterFightModel(characterModel);
        BattleModel battleModel = restTemplate.getForObject("http://localhost:8550/get/battletimebycharacterid/" + characterFightModel.getId(), BattleModel.class);
        characterFightModel.setTimeToKill(battleModel.getTimeToKill());
        characterFightModel.setMonsterToAttackName(battleModel.getMonster());
        characterFightModel.setFighting(true);

        WsReturnMessage wsReturnMessage = new WsReturnMessage();
        wsReturnMessage.setAction("GETPLAYERINFORMATION");
        wsReturnMessage.setContent(characterFightModel);
        session.getRemote().sendString(MessageConverter.FromObjectToString(wsReturnMessage));
    }

    public void addGold(User user, int gold){
        CharacterModel character = restTemplate.getForObject("http://localhost:8550/get/characterbyname/" + user.getUsername(), CharacterModel.class);
        character.setGold(character.getGold() + gold);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(character, headers);

        restTemplate.exchange("http://localhost:8550/get/updatecharacter", HttpMethod.POST, entity, CharacterModel.class);
    }
}
