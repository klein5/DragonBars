package de.klein5.DragonBarMySQL.Commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.klein5.Aktu.Main;
import de.klein5.DragonBarMySQL.MySQL.MySQL;

public class DragonBar_Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if(sender.hasPermission("ALL")){
		
		if(cmd.getName().equalsIgnoreCase("addMessage")){
			
			if(args.length == 0){
				
				
			}else{
				if(args.length >= 1){
					
					String msg = "";
					
					for(String me : args){
						
						msg = msg+" "+me;
						
					}
					MySQL.Update("INSERT INTO DragonBar_Messages (Messages) VALUES ('"+msg+"')"); 
					sender.sendMessage("§cDu hast die Message '"+msg+"' eingespeichert.");
					
				}
			}
			
		}
		if(cmd.getName().equalsIgnoreCase("removeMessage")){
			
			if(args.length == 0){
				
				sender.sendMessage("/removeMessage <id>");
				
				
			}else{
				if(args.length == 1){
				
					String remove = "";
					
					int i = Integer.parseInt(args[0]);
					
					List<String> list = new ArrayList<String>();
					
					int is = 0;
					
					sender.sendMessage("§eNachicht gelöscht.");
					
					ResultSet rs = MySQL.Query("SELECT Messages FROM DragonBar_Messages");
					
					try {
						while(rs.next()){
							is++;
							
							if(is == i){
								
								remove = rs.getString(1);
								
								MySQL.Update("DELETE FROM DragonBar_Messages WHERE Messages='"+remove+"'");
								
							}
							
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			
		}
		if(cmd.getName().equalsIgnoreCase("listMessages")){
			
			sender.sendMessage("§aAlle Messages : ");
			int i = 0;
			
			for(String s : Main.messages){
				i++;
				sender.sendMessage("§5"+i+". "+s);
				
			}
			sender.sendMessage(" §a INFO | §eColorCodes sind nicht umgewandelt.");
			
		}
		if(cmd.getName().equalsIgnoreCase("setSekchange")){
			if(args.length == 0){
				sender.sendMessage("/setSekChange");
			}else{
				if(args.length >= 1){
					int i = 0;
					try{
					
					i = Integer.parseInt(args[0]);
					
					}catch(Exception e){
						sender.sendMessage("§eDas ist keine Zahl.");
					}
					
					MySQL.Update("INSERT INTO DragonBar_sek SET sek='"+i+"'");
					
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("reloadDragonBar")){
			
			sender.sendMessage("§eReload wird Aktiviert.");
			MySQL.Update("UPDATE DragonBar_willReload SET reload='1'");
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					MySQL.Update("UPDATE DragonBar_willReload SET reload='0'");
					
				}
			}, 10*20L);
		}
		}else{
			sender.sendMessage("§cKEINE RECHTE");
		}
		return true;
	}
	
	
	

}
