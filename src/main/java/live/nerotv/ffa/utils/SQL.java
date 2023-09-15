package live.nerotv.ffa.utils;

import live.nerotv.ffa.Main;
import live.nerotv.ffa.apis.ConfigAPI;
import java.sql.*;

public class SQL {

    public static boolean SQLite = !ConfigAPI.CFG.getBoolean("Core.Settings.MySQL.enable");
    public static String host = ConfigAPI.CFG.getString("Core.Settings.MySQL.host");
    public static String port = ConfigAPI.CFG.getString("Core.Settings.MySQL.port");
    public static String database = ConfigAPI.CFG.getString("Core.Settings.MySQL.database");
    public static String username = ConfigAPI.CFG.getString("Core.Settings.MySQL.username");
    public static String password = ConfigAPI.CFG.getString("Core.Settings.MySQL.password");
    public static boolean isConnected() {
        return (con != null);
    }
    public static Connection getConnection() {
        return con;
    }
    public static Connection con;

    public static void connect() {
        if (!isConnected()) {
            try {
                if(SQLite) {
                    con = DriverManager.getConnection("jdbc:sqlite:" + Main.getInstance().getDataFolder() + "/sql.db");
                } else {
                    con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con = null;
    }

    public static boolean is(String table, String check) {
        boolean is;
        try {
            PreparedStatement ps = SQL.getConnection().prepareStatement("SELECT VALUE FROM "+table+" WHERE UUID = ?");
            ps.setString(1, check);
            ResultSet rs = ps.executeQuery();
            is = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            is = false;
        }
        return is;
    }
}