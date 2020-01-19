package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Resource;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Battle.BattleModel;
import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Character.CharacterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/get/")
public class CharacterResource {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("characterbyname/{characterName}")
    public CharacterModel characterModel(@PathVariable("characterName") String characterName){
        return restTemplate.getForObject("http://character-service/api/public/character/getbyname/" + characterName, CharacterModel.class);
    }

    @GetMapping("battletimebycharacterid/{characterId}")
    public BattleModel battleTime(@PathVariable("characterId") int characterId){
        return restTemplate.getForObject("http://battle-service/api/public/battle/get/" + characterId, BattleModel.class);
    }

    @PostMapping("updatecharacter")
    public void updateCharacter(@RequestBody CharacterModel characterModel){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity entity = new HttpEntity(characterModel, headers);

        restTemplate.exchange("http://character-service/api/public/updateCharacter", HttpMethod.POST, entity, CharacterModel.class);
    }
}
