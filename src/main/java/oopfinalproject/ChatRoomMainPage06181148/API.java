package oopfinalproject.ChatRoomMainPage06181148;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
public class API {
    //你的Token 用來安全連線
    private static String token = null;
    //你的id
    private static String id = null;
    //如果get/post 回傳的是Object，會存到這裡
    private static JSONObject result_object = null;
    //如果get 回傳的是Array，會存到這裡
    private static JSONArray result_array = null;

    //這兩個public method重要，你會用來取得data
    public static JSONObject getResultObject() {
        return result_object;
    }
    public static JSONArray getResultArray() {
        return result_array;
    }

    public static void main(String[] args){
        //Demo用，你會在你的其他class 呼叫這些
        // Example：登入後取得自己的chatrooms
        int resCode = API.login("bob", "87654321"); //有這個帳號
//        int resCode = API.login("randomDude", "43457423905"); //沒這個帳號
//        Websocket.connectionWebsocket(); //詳見Websocket.java
        if(resCode == 200){
            //登入成功
        }else{
            //失敗，取得錯誤訊息 （或是你可以直接用resCode判斷）
            String errorMessage = API.getResultObject().getString("error");
            System.out.println(errorMessage);
        }
        API.viewChatHistoryByFriend("alice");
//        Websocket.disconnectWebsocket();
        API.logout();
    }

    //筆記：
    //API都寫好了
    //所有POST預設的成功都會回傳：
    //        200 {}


    //200 登入成功 (response已經處理好)
    //409 {"error": "user exist"}
    //415 {"error": "format error"}
    public static int login(String id, String password){
        String url = "/account/login";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("password", password);
        int resCode = post(url, false, params);
        if(resCode == 200){
            setToken(API.getResultObject().getString("token"));
            setId(API.getResultObject().getString("id"));
        }
        return resCode;

    }
    //200 註冊成功
//    409 {"error": "user exist"}
//    415 {"error": "format error"} 限制： 密碼長度>=6，Email格式 *@*.*
    public static int signup(String id, String password, String name, String email){
        String url = "/account/signup";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
        int resCode = post(url, false, params);
        return resCode;

    }
    public static int logout(){
        String url = "/account/logout/"+getId();
        Map<String, Object> params = new HashMap<>();
        int resCode = post(url, true, params);
        return resCode;

    }
    //200 {"id":"bob","name":"bob","password":"87654321","email":"bob@gmail.com","apikey":"someRandomKey"}
    public static int getInfo(){
        String url = "/account/info/"+getId();
        Map<String, Object> params = new HashMap<>();
        int resCode = getObject(url, true, params);
        return resCode;
    }

    public static int addFriend(String friendID){
        String url = "/friend/add/"+getId();
        Map<String, Object> params = new HashMap<>();
        params.put("id", friendID);
        int resCode = post(url, true, params);
        return resCode;
    }
    //200 [
    //  {"id":"alice","name":"alice"}
    // , ...]
    
    public static int searchFriend(String friendID){
        String url = "/friend/search/"+getId();
        Map<String, Object> params = new HashMap<>();
        params.put("friendId", friendID);
        int resCode = getObject(url, true, params);
        return resCode;
    }//加入API.java
    
    public static int viewFriend(){
        String url = "/friend/view/"+getId();
        Map<String, Object> params = new HashMap<>();
        int resCode = getArray(url, true, params);
        return resCode;

    }

    public static int messageFriend(String friendID, String content){
        String url = "/friend/message/"+getId();
        Map<String, Object> params = new HashMap<>();
        params.put("friendId", friendID);
        params.put("content", content);
        int resCode = post(url, true, params);
        return resCode;
    }

    public static int messageRoom(int roomId, String content){
        String url = "/room/message/"+getId();
        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);
        params.put("content", content);
        int resCode = post(url, true, params);
        return resCode;
    }
    //200 [
    //   {"id":1,"content":"hi","time":"2023-06-14T20:00:25","sender":{"id":"bob","name":"bob"}},
    //   {"id":2,"content":"hello?","time":"2023-06-14T20:22:56","sender":{"id":"bob","name":"bob"}}
    // ,...]
    public static int viewChatHistory(int roomId){
        String url = "/room/messages/"+getId();
        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);
        int resCode = getArray(url, true, params);
        return resCode;
    }

    public static int viewChatHistoryByFriend(String friendId){
        String url = "/friend/messages/"+getId();
        Map<String, Object> params = new HashMap<>();
        params.put("friendId", friendId);
        int resCode = getArray(url, true, params);
        return resCode;
    }

    // 200 [
    // {"id":1,"roomName":"alice","participant1":{"id":"alice","name":"alice"},"participant2":{"id":"bob","name":"bob"},"group":false}
    // , ...]
    //如果是group， participant都會是null 要用下面的方法取得 (目前沒有group就是了)
    public static int viewAllChatroom(){
        String url = "/room/all/"+getId();
        Map<String, Object> params = new HashMap<>();
        int resCode = getArray(url, true, params);
        return resCode;
    }
    //200 [
    // {"id":"alice","name":"alice"},
    // {"id":"bob","name":"bob"},
    // (if it's a group, there would be more.)
    // ]
    public static int viewParticipants(int roomId){
        String url = "/room/participants/"+getId();
        Map<String, Object> params = new HashMap<>();
        params.put("roomId", roomId);
        int resCode = getArray(url, true, params);
        return resCode;
    }

    public static int setAPIKey(String key){
        String url = "/account/key/"+getId();
        Map<String, Object> params = new HashMap<>();
        params.put("key", key);
        int resCode = post(url, true, params);
        return resCode;
    }



    ////////////////////////////////下面的code已經寫好，盡量不要動到/////////////////////////////////

    public static String getId(){
        return  id;
    }
    private static void setId(String value){
        id = value;
    }

    private static void setToken(String value) {
        token = value;
    }

    public static String getToken() {
        return token;
    }

    private static void setResult(JSONObject value) {
        result_object = value;
    }

    private static void setResult(JSONArray value) {
        result_array = value;
    }


    public static int getObject(String url, boolean requireToken, Map<String, Object> params) {
        String fullUrl = "http://localhost:8080" + url;
        if (params != null && !params.isEmpty()) {
            StringBuilder queryString = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (queryString.length() > 0) {
                    queryString.append("&");
                }
                queryString.append(entry.getKey()).append("=").append(entry.getValue());
            }
            fullUrl += "?" + queryString.toString();
        }

        if (requireToken && token == null) {
            System.out.println("Cannot send request: no login token");
            return 400;
        }

        try {
            URL apiUrl = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            if (requireToken) {
                connection.setRequestProperty("Authorization", getToken());
            }

            int responseCode = connection.getResponseCode();
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String resString = response.toString();
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + resString);
            setResult(new JSONObject(resString));
            connection.disconnect();
            return responseCode;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Did not receive proper JSON response");
            e.printStackTrace(); // Add this line to print the stack trace for the JSONException
        }
        return 400;
    }

    public static int getArray(String url, boolean requireToken, Map<String, Object> params) {
        String fullUrl = "http://localhost:8080" + url;
        if (params != null && !params.isEmpty()) {
            StringBuilder queryString = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (queryString.length() > 0) {
                    queryString.append("&");
                }
                queryString.append(entry.getKey()).append("=").append(entry.getValue());
            }
            fullUrl += "?" + queryString.toString();
        }

        if (requireToken && token == null) {
            System.out.println("Cannot send request: no login token");
            return 400;
        }

        try {
            URL apiUrl = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            if (requireToken) {
                connection.setRequestProperty("Authorization", getToken());
            }

            int responseCode = connection.getResponseCode();
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String resString = response.toString();
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + resString);
            if(responseCode == 200){
                setResult(new JSONArray(resString));
            }else{
                setResult(new JSONObject(resString));
            }
            connection.disconnect();
            return responseCode;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Did not receive proper JSON response");
            e.printStackTrace(); // Add this line to print the stack trace for the JSONException
        }
        return 400;
    }

    private static String mapToJsonString(Map<String, Object> params) throws JSONException {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toString();
    }

    public static int post(String url, boolean requireToken, Map<String, Object> params) {
        String fullUrl = "http://localhost:8080" + url;
        String requestBody = mapToJsonString(params);

        if (requireToken && token == null) {
            System.out.println("Cannot send request: no login token");
            return 400;
        }


        try {
            URL apiUrl = new URL(fullUrl);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            if (requireToken) {
                connection.setRequestProperty("Authorization", getToken());
            }
            connection.setDoOutput(true);
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();
            int responseCode = connection.getResponseCode();
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String resString = response.toString();
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + resString);
            setResult(new JSONObject(resString));
            connection.disconnect();
            return responseCode;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("Did not receive proper JSON response");
            e.printStackTrace(); // Add this line to print the stack trace for the JSONException
        }
        return 400;
    }

    //上面都些好了，不用動，其他API都可以照著下面implement


}

