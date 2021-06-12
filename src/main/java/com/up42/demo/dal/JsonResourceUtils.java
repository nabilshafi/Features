package com.up42.demo.dal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.up42.demo.model.FeatureDTO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class JsonResourceUtils {


    private static HashMap<String, FeatureDTO> map = new HashMap<>();

    public JsonResourceUtils() {
    }


    public static JSONObject getJsonObjFromConfiguration(){
        String FILENAME = "source-data.json";
        return getJsonObjFromResource(FILENAME);
    }

    public static JSONObject getJsonObjFromResource(String filename){
        JSONObject json = null;


        if (!filename.contains(".json")){
            filename += ".json";
        }

        try{
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (inputStream != null){
                StringBuilder sb = new StringBuilder();
                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bfReader = new BufferedReader(reader);
                String content = null;
                while((content = bfReader.readLine()) != null){
                    sb.append(content);
                }
                bfReader.close();
                parseStringtoJson(sb.toString());
                //json = JSON.parseObject(sb.toString());
            }else{
            }

        }catch (Exception e){
        }



        return json;
    }

    public static void parseStringtoJson(String jsonString){

        JSONArray featureList = JSON.parseArray(jsonString);

        for(int i = 0; i < featureList.size(); i++){

            JSONArray jsonArray = parseJsonArray((JSONObject) featureList.get(i),"features");
            JSONObject propertiesObject = parseJsonObject(jsonArray.getJSONObject(0),"properties");
            JSONObject acquisitionObject = parseJsonObject(propertiesObject,"acquisition");

            String id = (String) propertiesObject.get("id");
            long timestamp = (long) propertiesObject.get("timestamp");
            long beginViewingDate = (long) acquisitionObject.get("beginViewingDate");
            long endViewingDate = (long) acquisitionObject.get("endViewingDate");
            String missionName = (String) acquisitionObject.get("missionName");

            map.put(id,new FeatureDTO(id,timestamp,beginViewingDate,endViewingDate,missionName));

        }

    }


    public static JSONArray parseJsonArray(JSONObject json, String arg){

        return (JSONArray)json.get(arg);

    }

    public static JSONObject parseJsonObject(JSONObject json, String arg){

        return (JSONObject) json.get(arg);

    }
}
