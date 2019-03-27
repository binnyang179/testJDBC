import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class TestJDBC {
    private static Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private static Scanner scanner;
    private static SimpleDateFormat simpleDateFormat;
    private String sqlToExecute;


    public static void initDB() {
        String URL = "jdbc:mysql://127.0.0.1:3306";
        String user = "root";
        String password = "123456";
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL+"?user="+user+"&password="+password+"&useSSL=false");
            System.out.println("Initial success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getSQL() {

    }


    public static void main(String[] args) {
        initDB();

    }



}
