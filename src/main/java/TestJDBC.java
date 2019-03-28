import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.stream.Stream;

public class TestJDBC {
    private static Connection connection = null;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private static Scanner scanner;
    private static SimpleDateFormat simpleDateFormat;
    private String sqlToExecute;

    static String URL = "jdbc:mysql://127.0.0.1:3306";
    static String user = "root";
    static String password = "123456";

    public TestJDBC() {
        scanner = new Scanner(System.in);
        initDB();
        getSQL();
        dispatch(sqlToExecute);
        simpleDateFormat = new SimpleDateFormat("[YYYY-MM-dd HH:mm:ss] ");
    }

    public static void initDB() {
        try {
//            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL+"?user="+user+"&password="+password+"&useSSL=false");
            System.out.println("Initial success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getSQL() {
        System.out.print("mysql> ");
        String line = scanner.nextLine();
        StringBuilder sql = new StringBuilder(line);
        while (!line.endsWith(";")) {
            System.out.print("    -> ");
            line = scanner.nextLine();
            sql.append(line);
        }
        sqlToExecute =  sql.toString();
    }

    public void dispatch(String sql) {
        String[] sqlMethodGetReturn = {"select", "show"};
        String[] sqlMethodNonReturn = {"insert", "alter","drop","update","delete"};
        if ((Stream.of(sqlMethodGetReturn).anyMatch(s -> sql.toLowerCase().trim().startsWith(s)))) {
            querySQL(sql);
        } else if ((Stream.of(sqlMethodNonReturn).anyMatch(s -> sql.toLowerCase().trim().startsWith(s)))) {
            executeSQL(sql);
        } else {

        }
    }
    public void querySQL(String sql) {
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); // 元数据
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                System.out.print(resultSetMetaData.getColumnLabel(i + 1) + " ");
            }
            System.out.println("\n----------------------");
            while (resultSet.next()) {
                for (int i = 0; i < columnCount; i++) {
                    System.out.print(resultSet.getString(i + 1) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void executeSQL(String sql) {
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        new TestJDBC();
    }

}
