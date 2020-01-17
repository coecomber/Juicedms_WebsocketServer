package com.juiced.juicedWebsocketServer.Logic.WebsocketServer.Collection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageConverter {


    public static Object FromGsonToObject(Object object, String gsonString){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();

        object = gson.fromJson(gsonString, object.getClass());

        return object;
    }

    public static String FromObjectToString(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.create();

        return gson.toJson(object);
    }
}