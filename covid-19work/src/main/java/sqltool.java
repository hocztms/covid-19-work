import java.sql.*;

public class sqltool {
    public static void sqlmeau() throws SQLException {
        System.out.println("---------欢迎使用数据库");
        String jdbc = "jdbc:mysql://localhost:3306/covid-19test?useSSL=false&characterEncoding=utf8";
        String user = "root";
        String password = "password";
        Connection connection = DriverManager.getConnection(jdbc, user, password);
        System.out.println("1.查询\n2.更新\n3.删除");
        find(connection);
        update(connection);
        delete(connection);
    }

    public static void find(Connection connection) throws SQLException {
        System.out.println("本次测试案例查询国家表所有数据");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM nation")) {
            ResultSet resultSet = ps.executeQuery();
            int n = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= n; i++) {
                    System.out.print(resultSet.getString(i) + "  ");
                    if (i % 13 == 0) System.out.println();
                }
            }

        }
        System.out.println("-------------------------------------------------------------------------------------------------------");
    }

    public static void update(Connection connection) throws SQLException {
        System.out.println("本次测试修改国家表中US国家地区改为UK");
        try (PreparedStatement ps = connection.prepareStatement("UPDATE nation SET continent='NA' WHERE country='US'")) {
            int n = ps.executeUpdate();
            find(connection);
            if (n > 0)  System.out.println("修改成功");
            else System.out.println("修改失败");
        }
    }

    public static void delete(Connection connection) throws SQLException {
        System.out.println("本次测试删除国家表中修改后的NK记录");
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM nation WHERE continent='NA'")) {
            int n = ps.executeUpdate();
            find(connection);
            if (n > 0) System.out.println("删除成功");
            else System.out.println("删除失败");
        }
    }
}
