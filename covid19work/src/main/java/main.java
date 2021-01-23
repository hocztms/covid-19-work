import com.alibaba.fastjson.JSONObject;

public class main {
    public static void main(String[] args)throws Exception {
        //设置中国（China）、美国(US)、英国(United Kingdom)、日本(Japen) url 并获取数据
        String c_url = "https://covid-api.mmediagroup.fr/v1/cases?country=China";
        String us_url = "https://covid-api.mmediagroup.fr/v1/cases?country=US";
        String uk_url = "https://covid-api.mmediagroup.fr/v1/cases?country=United%20Kingdom";
        String j_url = "https://covid-api.mmediagroup.fr/v1/cases?country=Japan";

        //获取数据
        JSONObject c_json = Geturlinformationtool.getJsonFromUrl(c_url);
        JSONObject us_json = Geturlinformationtool.getJsonFromUrl(us_url);
        JSONObject uk_json = Geturlinformationtool.getJsonFromUrl(uk_url);
        JSONObject j_json = Geturlinformationtool.getJsonFromUrl(j_url);
        System.out.println("测试四国例子");
        System.out.println(c_json);
        System.out.println(us_json);
        System.out.println(uk_json);
        System.out.println(j_json);
        System.out.println("测试成功");

        //jdbc连接
        jdbcstore.store(c_json);
        jdbcstore.store(us_json);
        jdbcstore.store(uk_json);
        jdbcstore.store(j_json);

        //测试数据库中增删改函数
        sqltool.sqlmeau();
    }}
