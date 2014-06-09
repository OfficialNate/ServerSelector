package com.smartloser.maze;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.Material;

import com.smartloser.Events.EventsHandler;
import com.smartloser.Handlers.ItemsHandler;
import com.smartloser.Managers.ConfigManager;
import com.smartloser.Managers.ServerManager;
public class Selector extends JavaPlugin implements PluginMessageListener
{
	 public static final Logger logger = Logger.getLogger("Minecraft");
	 private static Selector plugin;
	 public ItemsTicker ticker;
	 public ConfigManager gm;
	 public ServerManager sm;
	 private static Inventory i;
	 @Override
	   public void onDisable()
	   {
	   	PluginDescriptionFile pdfFile = getDescription();
	   	this.logger.info(pdfFile.getName() + " Has been disabled!"); // disable
	   }

	   @Override
	   public void onEnable()
	   { // enable
		   plugin = this;
			if (!(new File(this.getDataFolder(), "config.yml").exists()))
			{
				saveDefaultConfig();
			}
			else
			{
				getConfig().options().copyDefaults(true);
				saveConfig();
			}
		   gm = ConfigManager.getManager(this);
		   sm = ServerManager.getManager();
		   gm.setupConfig();
		   PluginDescriptionFile pdfFile = getDescription();
		   PluginManager pm = Bukkit.getServer().getPluginManager();
	       this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	       this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
			this.logger.info(pdfFile.getName() + " Has been enabled!");
			i = Bukkit.getServer().createInventory(null, 18, "tEST");
			ItemsHandler ih = ItemsHandler.getHandler();
			for (int x = 0; x < ih.getAmount(); x++)
			{
				ItemStack is = new ItemStack(Material.getMaterial(ih.getItemID(x)));
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(ChatColor.translateAlternateColorCodes('&',ih.getItemName(x)));
				is.setItemMeta(im);
				i.setItem(x, is);
				sm.registerServer(ih.getBungeeServer(x));
			}
			pm.registerEvents(new EventsHandler(this), this);
			ticker = new ItemsTicker();
			ticker.runTaskTimer(this, 0L, 60L);
			
	   }
	   @EventHandler
	   public void onPluginMessageReceived(String channel, Player player,
				byte[] data)
		{
			if (channel.equalsIgnoreCase("BungeeCord"))
			{
				DataInputStream input = new DataInputStream(
						new ByteArrayInputStream(data));
				try
				{
					String subchannel = input.readUTF();
					if (subchannel.equalsIgnoreCase("PlayerCount"))
					{
						String server = input.readUTF();
						int count = input.readInt();
						for (String s : sm.getServers())
						{
						
							if (s.equalsIgnoreCase(server))
							{
								sm.savePlayers(s, count);
								return;
							}
						}
					}
				}
				catch(IOException e)
				{
					
				}
			}
		}
	   
		public boolean onCommand(CommandSender sender, Command cmd,
				String commandLabel, String[] args)
		{
			if (!(sender instanceof Player))
			{
				return false;
			}
			Player p = (Player)sender;
			if (commandLabel.equalsIgnoreCase("setselectoritem"))
			{
				if (args.length  < 2)
					{
					 p.sendMessage(ChatColor.RED + "Format: /setselectoritem <ItemName> <ServerName> <ItemID>");
					return false;
					}

				ItemsHandler h = ItemsHandler.getHandler();
				h.SaveItem(args[0], args[1], Integer.parseInt(args[2]));
				p.sendMessage("Saved");
				
			}
			else if (commandLabel.equalsIgnoreCase("reloadselector"))
			{
				reloadConfig();
				saveConfig();
				gm.setupConfig();
				sm.resetMap();
				getInventory().clear();
				ItemsHandler ih = ItemsHandler.getHandler();
				for (int x = 0; x < ih.getAmount(); x++)
				{
					ItemStack is = new ItemStack(Material.getMaterial(ih.getItemID(x)));
					ItemMeta im = is.getItemMeta();
					im.setDisplayName(ChatColor.translateAlternateColorCodes('&',ih.getItemName(x)));
					is.setItemMeta(im);
					i.setItem(x, is);
					sm.registerServer(ih.getBungeeServer(x));
				}
				p.sendMessage(ChatColor.GOLD + "Reloaded Selector");
			}
			return false;
		}
		
		public static Inventory getInventory()
		{
			return i;
		}
		
		public static Selector getPlugin()
		{
			return plugin;
		}
		
}
