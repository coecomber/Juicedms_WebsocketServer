package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Resource;

import com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Model.Battle.BattleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/battle/")
public class BattleResource {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("battletimebycharacterid/{characterId}")
    public BattleModel battleTime(@PathVariable("characterId") int characterId){
        return restTemplate.getForObject("http://battle-service/api/public/battle/get/" + characterId, BattleModel.class);
    }
}
