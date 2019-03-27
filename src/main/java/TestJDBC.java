import java.sql.*;

public class TestJDBC {
    static Connection connection = null;
    static java.sql.Statement Statement = null;

    public static void main(String[] args) {
        initDB();

    }

    public static void initDB() {
        String URL = "jdbc:mysql://127.0.0.1:3306";
        String user = "root";
        String password = "123456";
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL+"?user="+user+"&password="+password+"&useSSL=false");
            Statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("show databases");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            System.out.println("Initial success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDB() {
        String a = "create database test";

    }

}
