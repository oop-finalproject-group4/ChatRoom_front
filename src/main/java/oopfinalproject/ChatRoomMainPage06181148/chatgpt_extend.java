package oopfinalproject.ChatRoomMainPage06181148;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class chatgpt_extend {
	public chatgpt_extend() {};
	
	
    public String getChat(String input_messeage,String key) throws Exception {
        // API endpoint
        String endpoint = "https://api.openai.com/v1/chat/completions";
        JSONArray messages = new JSONArray();
        // Create the user message
        JSONObject userMessage1 = new JSONObject();
        userMessage1.put("role", "user");
        userMessage1.put("content", "This is messeage in chatroom: \n"+input_messeage+"base on gived messeage in json, can you give me a recommand reply and reply only in sentences, not json?");
        messages.put(userMessage1);
        //將資料以及預計問的問題傳入chatgpt中，以其規定的標準形式傳入，並下達詢問指令


        // Create the JSON object and add messages array
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messages", messages);

        jsonObject.put("max_tokens",1000);

        jsonObject.put("temperature",0.8);
        
        // Print the JSON object
        System.out.println(jsonObject.toString());
        // Request parameters
        String prompt = jsonObject.toString();
        String apiKey = key;
        String model = "gpt-3.5-turbo-0613";
        // 設定Model
        // Create the request payload
        jsonObject.put("model",model);
        String jsonBody = jsonObject.toString();
        
        
        
        // Create the HTTP connection
        // 設定基本參數
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setDoOutput(true);

        // Send the request payload
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(jsonBody.getBytes());
        outputStream.flush();
        outputStream.close();
        // 將資料讀出

        // Get the response
        int responseCode = connection.getResponseCode();

        BufferedReader reader;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }
        //獲得http的回應資訊
        // Read the response
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        // 將http文本傳送回來的資料讀成string，爾後透過json讀取的方式將其資料轉為標準json格式
        String msg = "No message.";
        // Parse the response JSON
//        System.out.println(jsonBody);
        try {
        String jsonResponse = response.toString();
        JSONObject content = new JSONObject(jsonResponse);
        msg = content.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
        // 抓取預計得到的資料內容所在的區塊
        System.out.println(jsonResponse);
        System.out.println(content.toString());
        System.out.println(msg);
        }catch(Exception e) {
        	System.out.println(e.toString());
        }
        return msg;
    }
}
