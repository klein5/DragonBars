package de.klein5.Aktu;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.minecraft.server.v1_7_R1.DataWatcher;
import net.minecraft.server.v1_7_R1.EntityPlayer;
import net.minecraft.server.v1_7_R1.Packet;
import net.minecraft.server.v1_7_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_7_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R1.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R1.PacketPlayOutSpawnEntityLiving;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


public abstract class BossHealth {

	private static final int ENTITY_ID = 1234;
	private static final ArrayList<Player> hasHealthBar = new ArrayList<>();

	private static void sendPacket(Player player, Packet packet){
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		entityPlayer.playerConnection.sendPacket(packet);
	}
	
	//Accessing packets
	@SuppressWarnings("deprecation")
	private static PacketPlayOutSpawnEntityLiving getMobPacket(String text, Location loc){
		PacketPlayOutSpawnEntityLiving mobPacket = new PacketPlayOutSpawnEntityLiving();
		
	
		
		try {
			
			Field a = mobPacket.getClass().getDeclaredField("a");
			Field b = mobPacket.getClass().getDeclaredField("b");
			Field c = mobPacket.getClass().getDeclaredField("c");
			Field d = mobPacket.getClass().getDeclaredField("d");
			Field e = mobPacket.getClass().getDeclaredField("e");
			Field f = mobPacket.getClass().getDeclaredField("f");
			Field g = mobPacket.getClass().getDeclaredField("g");
			Field h = mobPacket.getClass().getDeclaredField("h");
			Field i = mobPacket.getClass().getDeclaredField("i");
			Field j = mobPacket.getClass().getDeclaredField("j");
			Field k = mobPacket.getClass().getDeclaredField("k");
			
			a.setAccessible(true);
			b.setAccessible(true);
			c.setAccessible(true);
			d.setAccessible(true);
			e.setAccessible(true);
			f.setAccessible(true);
			g.setAccessible(true);
			h.setAccessible(true);
			i.setAccessible(true);
			j.setAccessible(true);
			k.setAccessible(true);
			
		    try {
				    a.set(mobPacket,(int) ENTITY_ID);
				    b.set(mobPacket,(byte) EntityType.ENDER_DRAGON.getTypeId());
				    c.set(mobPacket,(int) Math.floor(loc.getBlockX() * 32.0D));
				    d.set(mobPacket,(int) Math.floor(100 * 150.0D));
				    e.set(mobPacket,(int) Math.floor(loc.getBlockZ() * 32.0D));
				    f.set(mobPacket,(byte) 0);
				    g.set(mobPacket,(byte) 0);
				    h.set(mobPacket,(byte) 0);
				    i.set(mobPacket,(byte) 0);
				    j.set(mobPacket,(byte) 0);
				    k.set(mobPacket,(byte) 0);
				    
			} catch (IllegalArgumentException er) {
				er.printStackTrace();
			} catch (IllegalAccessException er) {
				er.printStackTrace();
			}
		  
		    
		} catch (SecurityException | NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	    
		DataWatcher watcher = getWatcher(text, 200);
		

		try{ 
			Field t = PacketPlayOutSpawnEntityLiving.class.getDeclaredField("l");
			t.setAccessible(true);
			t.set(mobPacket, watcher);
		} catch(Exception e){
			e.printStackTrace();
		}
	
		return mobPacket;
	}
	
	private static PacketPlayOutEntityDestroy getDestroyEntityPacket(){
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy();
		try {
			Field a = packet.getClass().getDeclaredField("a");
			a.setAccessible(true);
			a.set(packet,new int[]{ENTITY_ID});
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return packet;
	}
	
	private static PacketPlayOutEntityMetadata getMetadataPacket(DataWatcher watcher){
		PacketPlayOutEntityMetadata metaPacket = new PacketPlayOutEntityMetadata();
			try {
			 Field a = metaPacket.getClass().getDeclaredField("a");
				a.setAccessible(true);
				a.set(metaPacket,(int) ENTITY_ID);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		try{
			Field b = PacketPlayOutEntityMetadata.class.getDeclaredField("b");
			b.setAccessible(true);
			b.set(metaPacket, watcher.c());
		} catch(Exception e){
			e.printStackTrace();
		}
		return metaPacket;
	}
	
	@SuppressWarnings("unused")
	private static PacketPlayInClientCommand getRespawnPacket(){
		PacketPlayInClientCommand packet = new PacketPlayInClientCommand();
		try {
			Field a = packet.getClass().getDeclaredField("a");
			a.setAccessible(true);
			a.set(packet,(int) 1);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return packet;
	}
	
	private static DataWatcher getWatcher(String text, int health){
		DataWatcher watcher = new DataWatcher(null);//ENTITY VIA ID ODER NAME ERMITTELN
		watcher.a(0, (Byte) (byte) 0x20); //Flags, 0x20 = invisible
		watcher.a(6, (Float) (float) health);
		watcher.a(10, (String) text); //Entity name
		watcher.a(11, (Byte) (byte) 1); //Show name, 1 = show, 0 = don't show
		//watcher.a(16, (Integer) (int) health); //Wither health, 300 = full health
		return watcher;
	}

	public static void displayText(final Player player, final String text, final double percent) {
		if (!hasHealthBar.contains(player)) {
			PacketPlayOutSpawnEntityLiving mobPacket = getMobPacket(text, player.getLocation());
			sendPacket(player, mobPacket);
			hasHealthBar.add(player);
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				DataWatcher watcher = getWatcher(text, (int) (percent * 200.d));
				PacketPlayOutEntityMetadata metaPacket = getMetadataPacket(watcher);
				
				sendPacket(player, metaPacket);	
			}
		});
	}

	public static void removeText(Player player) {
		PacketPlayOutEntityDestroy destroyEntityPacket = getDestroyEntityPacket();
		sendPacket(player, destroyEntityPacket);
		if (hasHealthBar.contains(player)) {
			hasHealthBar.remove(player);
		}
	}
}