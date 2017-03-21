package game.view;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DBConnection {

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/TicTacToe", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new RuntimeException("Error Connecting To The Database", e);
        }
    }

    public Vector<String> selectOnlinePlayers() {
        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL getOnline()}";
        ResultSet rs;
        Vector<String> myVector = new Vector<String>();
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            rs = sqlStatement.executeQuery();
            System.out.println("Online Players Selected ");
            while (rs.next()) {
                String str1 = rs.getString(1);
                myVector.add(str1);
            }
            testConnection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myVector;
    }

    public int getLastUserId() {
        int id = 0;
        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL getLastUserID()}";
        ResultSet rs;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            rs = sqlStatement.executeQuery();

            while (rs.next()) {
                id = rs.getInt(1);
            }
            testConnection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return id + 1;
    }

    public boolean isInserted(String playerName, int online, int host, int play, String playerIp) {
        boolean flag = false;
        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL insertPlayer(?,?,?,?,?,?)}";
        ResultSet myResultSet;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            sqlStatement.setInt(1, getLastUserId());
            sqlStatement.setString(2, playerName);
            sqlStatement.setInt(3, online);
            sqlStatement.setInt(4, host);
            sqlStatement.setInt(5, play);
            sqlStatement.setString(6, playerIp);
            myResultSet = sqlStatement.executeQuery();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            flag = true;
        } finally {
            try {
                testConnection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return flag;
    }

    public void setUserOnLIne(int id) {
        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL setUserOnline()}";
        ResultSet rs;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            rs = sqlStatement.executeQuery();

            testConnection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getUserId(String s) {

        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL getUserId(?)}";
        ResultSet rs;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            sqlStatement.setString(1, s);
            rs = sqlStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            testConnection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public boolean isExist(String s) {

        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL isExist(?)}";
        ResultSet rs;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            sqlStatement.setString(1, s);
            rs = sqlStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
            testConnection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    public boolean isIpExist(String s, String n) {

        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL isIpExist(?,?)}";
        ResultSet rs;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            sqlStatement.setString(1, s);
            sqlStatement.setString(2, n);
            rs = sqlStatement.executeQuery();
            if (rs.next()) {
                return true;
            }
            testConnection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    public void isGameDataInserted(String matchSequence, int player2, int playerId, int matchStatus, String name) {

        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL insertGame(?,?,?,?,?)}";
        ResultSet myResultSet;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            sqlStatement.setString(1, matchSequence);
            sqlStatement.setInt(2, player2);
            sqlStatement.setInt(3, playerId);
            sqlStatement.setInt(4, matchStatus);
            sqlStatement.setString(5, name);

            myResultSet = sqlStatement.executeQuery();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                testConnection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    public void  updateSeq(String s, int n) {

        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL updateSeq(?,?)}";
        ResultSet rs;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            sqlStatement.setString(1, s);
            sqlStatement.setInt(2, n);
            rs = sqlStatement.executeQuery();
            testConnection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public int getLastGameId() {

        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL getLastGameId()}";
        ResultSet rs;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {
            rs = sqlStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            testConnection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public String getMatchSeq(int id, String name) {
        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL getMatchSeq(?,?)}";
        ResultSet myResultSet;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {

            sqlStatement.setInt(1, id);
            sqlStatement.setString(2, name);

            myResultSet = sqlStatement.executeQuery();
            if (myResultSet.next()) {
                return myResultSet.getString(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                testConnection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return "";
    }
    
    
    
        public boolean haveAGame(int id, String name) {
        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL haveAGame(?,?)}";
        ResultSet myResultSet;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {

            sqlStatement.setInt(1, id);
            sqlStatement.setString(2, name);

            myResultSet = sqlStatement.executeQuery();
            if (myResultSet.next()) {
                return true ;
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                testConnection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return false;
    }

        
        public int getUnCompletedMatchId(int id, String name) {
        com.mysql.jdbc.Connection testConnection = (com.mysql.jdbc.Connection) DBConnection.getConnection();
        String storedProcedureName = "{CALL haveAGame(?,?)}";
        ResultSet myResultSet;
        try (CallableStatement sqlStatement = testConnection.prepareCall(storedProcedureName)) {

            sqlStatement.setInt(1, id);
            sqlStatement.setString(2, name);

            myResultSet = sqlStatement.executeQuery();
            if (myResultSet.next()) {
                return myResultSet.getInt(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        } finally {
            try {
                testConnection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return 0;
    }
        
        
}
