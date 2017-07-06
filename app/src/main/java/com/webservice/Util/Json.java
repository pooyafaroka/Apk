package com.webservice.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by Pooya on 7/6/2017.
 */

public class Json {

    public String getByName(String jsonData, String keyword, int index) {
        String result = "";
        JSONTokener tokener = new JSONTokener("[" + jsonData + "]");
        JSONArray finalResult = null;
        try {
            finalResult = new JSONArray(tokener);
            JSONObject obj = finalResult.getJSONObject(index);
            result = obj.getString(keyword);
        } catch (JSONException e) {
            tokener = new JSONTokener(jsonData);
            finalResult = null;
            try {
                finalResult = new JSONArray(tokener);
                JSONObject obj = finalResult.getJSONObject(index);
                result = obj.getString(keyword);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    public int getArraySize(String input) {
        int res = 0;
        JSONTokener tokener = new JSONTokener(input);
        JSONArray finalResult = null;
        try {
            finalResult = new JSONArray(tokener);
            res = finalResult.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return res;
    }
}
