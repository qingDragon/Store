package com.big.yanzhuang.store;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class Tools {
    public static JsonObject str2Json(String string){

        /**
         *@author Bibi
         *@date 2019/4/9 16:06
         *@name str2Json
         *@returnType com.google.gson.JsonObject
         *@decribe TODO 字符串转json
        **/
        JsonParser parser = new JsonParser();
        JsonObject jObject = new JsonObject();
        return parser.parse(string).getAsJsonObject();
    }
    public static ArrayList<Item> first_list(ArrayList<Item> list){
        ArrayList<Item> first_list = new ArrayList<>();
        if(list.isEmpty()){

        } else {
            for(int i=0;i<list.size();i++){
                if(list.get(i).getParent_id().equals("0")){
                    first_list.add(list.get(i));
                } else {
                    System.out.println("服务器无数据");
                }
            }
        }
        return first_list;
    }
}
