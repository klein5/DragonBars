package de.klein5.Aktu;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.klein5.DragonBarMySQL.Commands.DragonBar_Commands;
import de.klein5.DragonBarMySQL.MySQL.MySQL;

public class Main extends JavaPlugin {
	
	public static String MySQL_host = "";
    public static String MySQL_user = "";
    public static String MySQL_pass = "";
    public static String MySQL_db = "";
    public static int MySQL_Port = 0;
	
    private static Main m;
    
    public static boolean s = false;
    
    public static List<String> messages = new ArrayList<String>();    
	@Override
	public void onEnable(){
		
		m = this;
		
		
		loadConfig();
		sql();
		MySQL.Update("CREATE TABLE IF NOT EXISTS DragonBar_Messages (Messages text(30))");
		MySQL.Update("CREATE TABLE IF NOT EXISTS DragonBar_willReload (reload int(30))");
		MySQL.Update("CREATE TABLE IF NOT EXISTS DragonBar_sek (sek int(30))");
		
		ResultSet rs = MySQL.Query("SELECT reload FROM DragonBar_willReload");
		
		try {
			if(rs.next()){
				
				if(rs != null){
				
				}else{
					MySQL.Update("INSERT INTO DragonBar_Messages SET Messages='&aTest das ist ein Test'");
					MySQL.Update("INSERT INTO DragonBar_Messages SET Messages='&aTest2 das ist ein Test2'");
					MySQL.Update("INSERT INTO DragonBar_willReload SET reload='0'");
					MySQL.Update("INSERT INTO DragonBar_sek SET sek='5'");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		loadMessages();
		
		this.getCommand("reloadDragonBar").setExecutor(new DragonBar_Commands());
		this.getCommand("removeMessage").setExecutor(new DragonBar_Commands());
		this.getCommand("listMessages").setExecutor(new DragonBar_Commands());
		this.getCommand("addMessage").setExecutor(new DragonBar_Commands());
		this.getCommand("setSekchange").setExecutor(new DragonBar_Commands());
		
		
		change();
		
		
	}
	@Override
	public void onDisable(){
		
	}
	public void change(){
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				
				if(s == true){
				
				Random ran = new Random();
				
				int gn = 0;
				
				try{
				
				gn = ran.nextInt(Main.messages.size());
				

				}catch(Exception e){
					for(Player all : Bukkit.getOnlinePlayers()){
					BossHealth.displayText(all, "§eFüge eine Message hinzu mit /addMessage <message>", 1);
					}
				}
				
				for(Player all : Bukkit.getOnlinePlayers()){
				
					
					
					try{
					
						String s = Main.messages.get(gn);
						
						s.replace("%p%", all.getName());		
						s.replace("%onlineplayer%", Bukkit.getOnlinePlayers().length+"");		
						s.replace("%maxplayer%", Bukkit.getMaxPlayers()+"");	
						
						BossHealth.displayText(all, ChatColor.translateAlternateColorCodes('&', s)+"", 1);
				
					}catch(Exception e){
						BossHealth.displayText(all, "§eFüge eine Message hinzu mit /addMessage <message>", 1);
					}
				
				}
				}else{
					for(Player all : Bukkit.getOnlinePlayers()){
					BossHealth.displayText(all, "§cMySQL Verbinden !", 1);
					}
					
				}
			}
		}, 1L, getSek()*20L);
		
	}
	public int getSek(){
		
	ResultSet rs = MySQL.Query("SELECT sek FROM DragonBar_sek");
		
		int i = 0;
		
		try {
			if(rs.next()){
				
			i = rs.getInt(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
		
	}
	public static Main getInstance(){
		return m;
	}
	
	public int getZahl(){
		ResultSet rs = MySQL.Query("SELECT reload FROM DragonBar_willReload");
		
		int i = 0;
		
		try {
			if(rs.next()){
				
			i = rs.getInt(1);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	
	
	public void loadMessages(){
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
			
			@Override
			public void run() {
		
		List<String> list = new ArrayList<String>();
		
		ResultSet rs = MySQL.Query("SELECT Messages FROM DragonBar_Messages");
		
		try {
			while(rs.next()){
				
				list.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Main.messages = list;
		
		
		
		}
	}, 0L, getSek()*20L);
		
	}
	
private static void loadConfig() {
		
		File file = new File("plugins/DragonBar","Datas.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		cfg.addDefault("Config.Host", "localhost");
		cfg.addDefault("Config.User", "user");
		cfg.addDefault("Config.Password", "pass");
		cfg.addDefault("Config.DB", "database");
		cfg.addDefault("Config.Port", 3306);


		cfg.options().copyDefaults(true);
		try {
			cfg.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	private static void sql(){
		File file = new File("plugins/DragonBar","Datas.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
		
		MySQL_host = cfg.getString("Config.Host");
        MySQL_user = cfg.getString("Config.User");
        MySQL_pass = cfg.getString("Config.Password");
        MySQL_db = cfg.getString("Config.DB");
        MySQL_Port = cfg.getInt("Config.Port");
        try {
			MySQL.openConnection();
			s = true;
		} catch (Exception e) {
			System.err.println("MySQL not working");
			s = false;
		}
	}
}
