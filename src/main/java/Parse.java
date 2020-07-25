import java.io.*;
import java.sql.*;

public class Parse {
    private static Connection connection;
    private static Statement stmt;

    public static void main(String[] args) {
        try {
            connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        try {
            createDB("Bobs");
//            deleteDB("Bobs");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        readTestTxt();
        disconnect();
    }
    public static void readTestTxt(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("src/test.txt")));
            String line = reader.readLine();
            String[] tokens;
            while (line != null) {
                System.out.println(line);
                tokens = line.split(" ");
                int Score = 0;
                try {
                    Score = Integer.parseInt(tokens[2]);
                } catch (NumberFormatException e) {
                    System.err.println("Неправильный формат строки!");

                }
                add(tokens[1],Score);
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main2.db");
        stmt = connection.createStatement();
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createDB(String dbName) throws SQLException {
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " Name TEXT NOT NULL, " +
                " Score INTEGER NOT NULL )", dbName);
        stmt.executeUpdate(sql);
    }

    public static void add(String Name, int Score) throws SQLException {
        String sql = "INSERT INTO Bobs (Name, Score) VALUES (?,?);";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setString(1, Name);
        pStmt.setInt(2, Score);
        pStmt.executeUpdate();
    }

    public static void deleteDB(String dbName) throws SQLException {
        String sql = String.format("DROP TABLE IF EXISTS %s", dbName);
        stmt.execute(sql);
    }
}