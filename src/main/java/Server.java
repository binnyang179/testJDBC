import com.mysql.jdbc.Driver;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
GUI
    create database ...;
    create table ...;
    insert into ...;
    update ...;
    delete ...;
    select ...;
    drop table ...;
    drop database ...;
 */
public class Server {

    private static final String URL = "jdbc:mysql:///?user=root&password=system&useSSL=false";
    private static Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private static SimpleDateFormat simpleDateFormat;

    public Server() {
        getConnection();
        simpleDateFormat = new SimpleDateFormat("[YYYY-MM-dd HH:mm:ss] ");
    }

    public Connection getConnection() { // singleton 单例模式
        if (connection == null) {
            try {
                new Driver();
                connection = DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * DDL: create alter drop truncate
     * DML: insert update delete
     *
     * @param sql statement
     */
    String update(String sql) {
        String output;
        try {
            long start = System.currentTimeMillis();
            preparedStatement = connection.prepareStatement(sql);
            int rowAffected = preparedStatement.executeUpdate();
            long end = System.currentTimeMillis();

            output = simpleDateFormat.format(new Date(end));
            output += rowAffected + " row affected in " + (end - start) + " ms";
        } catch (SQLException e) {
            output = "SQL Error: " + e.getMessage();
        }
        return output;
    }

    /**
     * DQL: select
     *
     * @param sql statement
     */
    String query(String sql) {
        String output;
        try {
            long start = System.currentTimeMillis();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            long end = System.currentTimeMillis();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); // 元数据
            int columnCount = resultSetMetaData.getColumnCount();

            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = resultSetMetaData.getColumnLabel(i + 1);
            }

            int rowCount = 0; // 记录的条数
            if (resultSet.last()) { // 移动到最后一条记录
                rowCount = resultSet.getRow(); // 获取记录的条数
                resultSet.beforeFirst(); // 移动到第一条之前
            }

            String[][] data = new String[rowCount][columnCount];

            for (int row = 0; row < rowCount; row++) {
                resultSet.next();
                for (int col = 0; col < columnCount; col++) {
                    data[row][col] = resultSet.getString(col + 1);
                }
            }

            Client.defaultTableModel = new DefaultTableModel(data, columnNames);

            output = simpleDateFormat.format(new Date(end));
            output += (rowCount + " rows retrieved in " + (end - start) + " ms");
        } catch (SQLException e) {
            output = "SQL Error: " + e.getMessage();
        }
        return output;
    }

    String dispatch(String sql) {
        if (sql == null) {
            return null;
        } else {
            if (sql.toLowerCase().trim().startsWith("select")) {
                return query(sql);
            } else {
                return update(sql);
            }
        }

    }
}