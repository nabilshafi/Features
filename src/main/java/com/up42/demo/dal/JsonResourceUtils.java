package com.up42.demo.dal;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class JsonResourceUtils {


    public JsonResourceUtils() {
    }


    public static JSONObject getJsonObjFromConfiguration(){
        String FILENAME = "source-data.json";
        return getJsonObjFromResource(FILENAME);
    }

    public static JSONObject getJsonObjFromResource(String filename){
        JSONObject json = null;
        JSONArray featureList = null;

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
                featureList = JSON.parseArray(sb.toString());
                //json = JSON.parseObject(sb.toString());
            }else{
            }

        }catch (Exception e){
        }

        parseJsonArray((JSONObject) featureList.get(0),"features");
        return json;
    }

    public static void parseJsonArray(JSONObject json,String arg){

        JSONArray jsonArray = (JSONArray)json.get(arg);
        JSONObject feature = (JSONObject) jsonArray.get(0);
        JSONObject properties = (JSONObject) feature.get("properties");
        System.out.println(properties.get("id"));

    }
}
