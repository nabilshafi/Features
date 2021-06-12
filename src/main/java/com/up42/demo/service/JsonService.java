package com.up42.demo.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.up42.demo.model.FeatureDTO;
import com.up42.demo.model.FeatureImageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * This class pre loads data from file
 * parsed data and map to POJO
 * Save data into hashmap for future use
 */
@Service
public class JsonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonService.class);

    private  HashMap<String, FeatureDTO> featuresMap = new HashMap<>();
    private  HashMap<String, FeatureImageDTO> featuresImg = new HashMap<>();


    public JsonService(){}

    @PostConstruct
    public void init() {
        getJsonObjFromConfiguration();
    }


    /**
     * This function store and pass the file name
     */
    private  void getJsonObjFromConfiguration(){
        String FILENAME = "source-data.json";
        getJsonObjFromResource(FILENAME);
    }


    /**
     * This function Read data from json file
     * @param filename
     * pass data as argument to loadJsonToMemory() function
     */
    private void getJsonObjFromResource(String filename){

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
                loadJsonToMemory(sb.toString());
            }else{
                LOGGER.info("[{}] file not exist",filename);
            }

        }catch (Exception e){
            LOGGER.error("read file to string error",e);
        }

    }


    /**
     * This function Map and parsed jsonString to required data
     * @param jsonString
     */
    public void loadJsonToMemory(String jsonString){

        try{

            JSONArray featureList = JSON.parseArray(jsonString);
            JSONArray jsonArray;
            JSONObject propertiesObject;
            JSONObject acquisitionObject;

            for(int i = 0; i < featureList.size(); i++){

                jsonArray = parseJsonArray((JSONObject) featureList.get(i),"features");
                propertiesObject = parseJsonObject(jsonArray.getJSONObject(0),"properties");
                acquisitionObject = parseJsonObject(propertiesObject,"acquisition");

                String id = (String) propertiesObject.get("id");
                long timestamp = (long) propertiesObject.get("timestamp");
                long beginViewingDate = (long) acquisitionObject.get("beginViewingDate");
                long endViewingDate = (long) acquisitionObject.get("endViewingDate");
                String missionName = (String) acquisitionObject.get("missionName");
                String quickLook = (String) propertiesObject.get("quicklook");

                featuresMap.put(id,new FeatureDTO(id,timestamp,beginViewingDate,endViewingDate,missionName));
                featuresImg.put(id, new FeatureImageDTO(id,quickLook));
            }
        }
        catch (Exception e){
            LOGGER.error("Error when parsing ", e);
        }
    }


    public JSONArray parseJsonArray(JSONObject json, String arg){

        return (JSONArray)json.get(arg);

    }

    private JSONObject parseJsonObject(JSONObject json, String arg){

        return (JSONObject) json.get(arg);

    }


    /**
     * This function return the list of features
     * @return List<FeatureDTO>
     */
    public List<FeatureDTO> getFeatures()  {

        if(featuresMap.isEmpty()){
            return null;
        }
        List<FeatureDTO> result = featuresMap.values().stream()
                .collect(Collectors.toList());

        return result;
    }

    /**
     * This function return feature according to id
     * @param id
     * @return FeatureDTO
     */
    public FeatureDTO getFeatureById(String id) {

        if(featuresMap.isEmpty()){
            return null;
        }
        else if(!featuresMap.containsKey(id) ){
            return null;
        }

      return featuresMap.get(id);

    }


    /**
     * This method decode the 64bit string to image associated with the repective id
     * @param id
     * @return decoded image
     */
    public byte[] getImage(String id) {

        if(featuresImg.isEmpty()){
            return null;
        }
        else if(!featuresImg.containsKey(id) ){
            return null;
        }
        else if(featuresImg.get(id) == null || featuresImg.get(id).getImage() == null){
            return null;
        }

        FeatureImageDTO img = featuresImg.get(id);
        return Base64.getDecoder().decode(img.getImage());

    }

    public HashMap<String, FeatureDTO> getMap() {
        return featuresMap;
    }

}
