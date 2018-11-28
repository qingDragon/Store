package com.big.yanzhuang.store;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Tools {
    public static JsonObject str2Json(String string){
        JsonParser parser = new JsonParser();
        JsonObject jObject = new JsonObject();
        jObject = (JsonObject) parser.parse(string);
        return jObject;
    }
}
