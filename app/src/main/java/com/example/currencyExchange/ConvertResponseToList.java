package com.example.currencyExchange;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Map;

public class ConvertResponseToList {

    public ArrayList<Valute> convert(String body) {
        JsonElement root = new JsonParser().parse(body);
        JsonObject object = root.getAsJsonObject().get("Valute").getAsJsonObject();
        Gson gson = new Gson();
        ArrayList<Valute> valuateList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            Valute valute = gson.fromJson(entry.getValue(), Valute.class);
            valuateList.add(valute);
        }
        return valuateList;
    }
}
