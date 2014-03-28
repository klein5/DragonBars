package de.klein5.DragonBarMySQL.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.klein5.Aktu.Main;
 
public class MySQL {
public static Connection con;
       
public static Connection openConnection() throws Exception
{
	Class.forName("com.mysql.jdbc.Driver");
	Connection con = DriverManager.getConnection("jdbc:mysql://" + Main.MySQL_host + ":" + Main.MySQL_Port + "/" + Main.MySQL_db + "?user=" + Main.MySQL_user + "&password=" + Main.MySQL_pass + "&autoReconnect=true");
	MySQL.con = con;
	System.out.println("[DungendDefenders] MySQL Connected");
	return con;
}
       
        public static void close(){
               
                if(con != null){
                        try {
                                con.close();
                        } catch (SQLException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }
                }
               
        }
       
        public static void Update(String qry){
               
               
                try {
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(qry);
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
               
        }
       
        public static ResultSet Query(String qry){
                ResultSet rs = null;
               
                try {
                        Statement stmt = con.createStatement();
                        rs = stmt.executeQuery(qry);
                } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
				return rs;
       
        }
       
}