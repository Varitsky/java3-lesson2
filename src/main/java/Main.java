

import java.sql.*;

public class Main {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pStmt;

    public static void main(String[] args) {
        try {
            connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        try {
//            createMain("main");
//            fillMain();
//            add("turnerBob", 9);
//            selectBob("turnerBob");
//            delete(2);
            deleteDB("main");
//            selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnect();
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createMain(String dbName) throws SQLException {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " Bob TEXT NOT NULL, " +
                " NumberOfFingers INTEGER)", dbName);
        stmt.executeUpdate(sql);
    }

    public static void fillMain() throws SQLException {
        String sql = "INSERT INTO main (Bob, NumberOfFingers) VALUES (?,?);";
        pStmt = connection.prepareStatement(sql);
        for (int i = 1; i < 6; i++) {
            pStmt.setString(1, "Bob" + i);
            pStmt.setInt(2, 10);
            pStmt.addBatch();
        }
        pStmt.executeBatch();
    }

    public static void add(String newBob, int fingers) throws SQLException {
        String sql = "INSERT INTO main (Bob, NumberOfFingers) VALUES (?,?);";
        pStmt = connection.prepareStatement(sql);
        pStmt.setString(1, newBob);
        pStmt.setInt(2, fingers);

        pStmt.executeUpdate();
    }

    public static void delete(int id) throws SQLException {
        String sql = String.format("DELETE FROM main WHERE id = %s", id);
        stmt.execute(sql);
    }

    public static void deleteDB(String dbName) throws SQLException {
        String sql = String.format("DROP TABLE IF EXISTS %s", dbName);
        stmt.execute(sql);
    }

    public static void selectBob(String name) throws SQLException {
        String sql = "SELECT Bob, NumberOfFingers FROM main where Bob = \"" + name + "\"";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) + ": " + rs.getInt(2));
        }
    }

    public static void selectAll() throws SQLException {
        String sql = "SELECT Bob, NumberOfFingers FROM main ";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) + ": " + rs.getInt(2));
        }
    }
}