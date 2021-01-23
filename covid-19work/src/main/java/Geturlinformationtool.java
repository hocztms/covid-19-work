import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//接口读取信息工具
public class Geturlinformationtool {
    public static JSONObject getJsonFromUrl(String strurl) throws Exception {
        URL url = new URL(strurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.connect();
            String line;
            StringBuffer sbuffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                sbuffer.append(line);
            }
            return JSON.parseObject(String.valueOf(sbuffer));
        } finally {
            connection.disconnect();
        }
    }
}
