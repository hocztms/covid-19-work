import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Set;

public class jdbcstore {
    public static void store(JSONObject jsonobject) throws SQLException {
        String jdbc = "jdbc:mysql://localhost:3306/covid-19test?useSSL=false&characterEncoding=utf8";
        String user = "root";
        String password = "password";
        int error=0;
        try (Connection connection = DriverManager.getConnection(jdbc, user, password)) {
            int i = 0;
            Set<String> jsonSet = jsonobject.keySet();
            for (String outjson : jsonSet) {
                JSONObject injson = jsonobject.getJSONObject(outjson);
                if (i == 0) {
                    try (PreparedStatement ps = connection.prepareStatement("INSERT INTO nation (continent, country, iso, capital_city, life_expectancy, abbreviation, confirmed, population, sq_km_area, recovered, elevation_in_meters, location, deaths) " +
                            "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                        ps.setObject(1, injson.getString("continent"));
                        ps.setObject(2, injson.getString("country"));
                        ps.setObject(3, injson.getIntValue("iso"));
                        ps.setObject(4, injson.getString("capital_city"));
                        ps.setObject(5, injson.getFloat("life_expectancy"));
                        ps.setObject(6, injson.getString("abbreviation"));
                        ps.setObject(7, injson.getIntValue("confirmed"));
                        ps.setObject(8, injson.getIntValue("population"));
                        ps.setObject(9, injson.getIntValue("sq_km_area"));
                        ps.setObject(10, injson.getIntValue("recovered"));
                        ps.setObject(11, injson.getIntValue("elevation_in_meters"));
                        ps.setObject(12, injson.getString("location"));
                        ps.setObject(13, injson.getIntValue("deaths"));
                        int n = ps.executeUpdate();
                        if (n<=0) error++;
                    }

                } else {
                    try (PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO area (name, recovered, confirmed, updated, lat, date_long, deaths) VALUES (?,?,?,?,?,?,?)",
                            Statement.RETURN_GENERATED_KEYS)) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss+00");
                        String datetime = injson.getString("updated");
                        LocalDateTime update = LocalDateTime.parse(datetime, formatter);
                        ps.setObject(1, outjson); // 注意：索引从1开始// grade
                        ps.setObject(2, injson.getIntValue("recovered"));
                        ps.setObject(3, injson.getIntValue("confirmed"));
                        ps.setObject(4, update);
                        ps.setObject(5, injson.getFloat("lat"));
                        ps.setObject(6, injson.getFloat("long"));
                        ps.setObject(7, injson.getIntValue("deaths"));
                        int n = ps.executeUpdate();
                        if (n<=0) error++;
                    }
                }
                i++;
            }
        }
        if (error==0) System.out.println("储存成功");
        else System.out.println("存储失败");
    }
}
