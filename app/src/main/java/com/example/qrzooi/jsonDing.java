package com.example.qrzooi;

import org.json.JSONException;
import org.json.JSONObject;

public class jsonDing {

    public String dingetje(String geweldigeText){//idek.
        try {
            JSONObject jsObj = new JSONObject();
            jsObj.put("Random string:", geweldigeText);
            return jsObj.toString();

        } catch(JSONException jx){
            jx.printStackTrace();
        }
        return "";
    }
}
