package Main;



import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        try {
            InputStreamReader isReader = new InputStreamReader(System.in);
            BufferedReader bufReader = new BufferedReader(isReader);
            String inputStr = "";
            StringBuilder sb = new StringBuilder();
            while (inputStr != null) {
                inputStr = bufReader.readLine();
                if (inputStr != null) {
                    sb.append(inputStr);
                    System.out.println(inputStr);
                }
            }
            System.out.println("reading done");
            String jsonString = sb.toString();
            System.out.println(jsonString);
            JSONObject inputObj = convertToJsonObj(jsonString);
            JSONObject flattenedObj = flattenJsonObj(inputObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // REQUIRES: a valid json string
    // EFFECTS: returns a json object from the json string
    public static JSONObject convertToJsonObj(String jsonString) {
        return new JSONObject(jsonString);
    }

    public static JSONObject flattenJsonObj(JSONObject obj) {
        JSONObject result = new JSONObject();

        flattenJsonObjHelper(obj, result, "");

        return result;

    }

    public static JSONObject flattenJsonObjHelper(JSONObject currObj, JSONObject newObj, String prevKeyName) {
        Iterator<?> keys = currObj.keys();
        String key = (String) keys.next();
        while (keys.hasNext()) {
            // value is a json object
            if (currObj.get(key) instanceof JSONObject) {
                System.out.println("value is obj");
                if (prevKeyName == null || prevKeyName.equals("")) {
                    return flattenJsonObjHelper((JSONObject) currObj.get(key), newObj, key);
                } else {
                    return flattenJsonObjHelper((JSONObject) currObj.get(key), newObj, prevKeyName + '.' + key);
                }
            // value is not a json object
            } else {
                System.out.println("value is not obj");
                if (prevKeyName == null || prevKeyName.equals("")) {
                    return newObj.put(key, currObj.get(key));
                } else {
                    return newObj.put(prevKeyName + "." + key, currObj.get(key));
                }
            }
        }
        return null;
    }
}
