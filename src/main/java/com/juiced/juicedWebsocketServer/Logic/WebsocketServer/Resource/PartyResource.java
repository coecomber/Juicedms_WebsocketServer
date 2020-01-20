package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Resource;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.ErrorHandler.RestTemplateErrorHandler;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Character.CharacterModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.PartyModel.PartyModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.PartyModel.PartyModelForClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/party/")
public class PartyResource {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("partymodelbycharacterid/{characterId}")
    public PartyModelForClient partyModel(@PathVariable("characterId") int characterId){
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        PartyModelForClient partyModelForClient = restTemplate.getForObject("http://party-service/api/public/party/get/" + characterId, PartyModelForClient.class);

        RestTemplate localRestTemplate = new RestTemplate();
        if(characterId == partyModelForClient.getCharacterIdOne()){
            CharacterModel characterModel = localRestTemplate.getForObject("http://localhost:8550/character/characterbyid/" + partyModelForClient.getCharacterIdTwo(), CharacterModel.class);
            partyModelForClient.setOtherCharacterName(characterModel.getName());
        } else{
            CharacterModel characterModel = localRestTemplate.getForObject("http://localhost:8550/character/characterbyid/" + partyModelForClient.getCharacterIdOne(), CharacterModel.class);
            partyModelForClient.setOtherCharacterName(characterModel.getName());
        }

        return partyModelForClient;
    }

    @DeleteMapping("leavepartybycharacterid/{characterId}")
    public void removeParty(@PathVariable("characterId") int characterId){
        restTemplate.delete("http://party-service/api/public/party/delete/" + characterId);
    }

    @PostMapping("createpartybypartymodel")
    public void createParty(@RequestBody PartyModel partyModel){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(partyModel, headers);

        restTemplate.exchange("http://party-service/api/party/create/", HttpMethod.POST, entity, PartyModel.class);
    }
}
