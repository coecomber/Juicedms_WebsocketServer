package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Logic;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.MessageConverter;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection.UserCollection;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Battle.BattleModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Character.CharacterModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Fight.CharacterFightModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.PartyModel.PartyModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.PartyModel.PartyModelForClient;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.User;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.WsReturnMessage;

import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;
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
        CharacterModel characterModel = restTemplate.getForObject("http://localhost:8550/character/characterbyname/" + user.getUsername(), CharacterModel.class);
        CharacterFightModel characterFightModel = new CharacterFightModel(characterModel);
        BattleModel battleModel = restTemplate.getForObject("http://localhost:8550/battle/battletimebycharacterid/" + characterFightModel.getId(), BattleModel.class);
        characterFightModel.setTimeToKill(battleModel.getTimeToKill());
        characterFightModel.setMonsterToAttackName(battleModel.getMonster());
        characterFightModel.setFighting(true);

        WsReturnMessage wsReturnMessage = new WsReturnMessage();
        wsReturnMessage.setAction("GETPLAYERINFORMATION");
        wsReturnMessage.setContent(characterFightModel);
        session.getRemote().sendString(MessageConverter.FromObjectToString(wsReturnMessage));
    }

    public void addGold(User user, int gold){
        CharacterModel character = restTemplate.getForObject("http://localhost:8550/character/characterbyname/" + user.getUsername(), CharacterModel.class);
        character.setGold(character.getGold() + gold);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(character, headers);

        restTemplate.exchange("http://localhost:8550/character/updatecharacter", HttpMethod.POST, entity, CharacterModel.class);
    }

    public User checkUser(JSONObject message, Session session, User userFromJSON){
        //Checks if player has session already. If so, change the user object to the new one.
        User user = (User) MessageConverter.FromGsonToObject(new User(), message.getJSONObject("User").toString());
        user.setSession(session);
        for(User currentUser : UserCollection.getConnectedUsers()){
            if(session == currentUser.getSession()){
                System.out.println("Already have session");
                user = UserCollection.getUserBySession(session);
                user.setUsername(userFromJSON.getUsername());
            }
        }
        return user;
    }

    public boolean createParty(String characterOne, String characterTwo){
        CharacterModel character1 = restTemplate.getForObject("http://localhost:8550/character/characterbyname/" + characterOne, CharacterModel.class);
        CharacterModel character2 = restTemplate.getForObject("http://localhost:8550/character/characterbyname/" + characterTwo, CharacterModel.class);

        PartyModelForClient PartyModel = new PartyModelForClient();
        PartyModel.setCharacterIdOne(character1.getId());
        PartyModel.setCharacterIdTwo(character2.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(PartyModel, headers);
        restTemplate.exchange("http://localhost:8550/party/createpartybypartymodel", HttpMethod.POST, entity, PartyModel.class);
        return false;
    }

    public boolean leaveParty(User user){
        CharacterModel character = restTemplate.getForObject("http://localhost:8550/character/characterbyname/" + user.getUsername(), CharacterModel.class);
        restTemplate.delete("http://localhost:8550/party/leavepartybycharacterid/" + character.getId());
        return true;
    }
}
