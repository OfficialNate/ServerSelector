package com.smartloser.Handlers;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.smartloser.maze.Selector;

public class ItemsHandler
{
	 private static ItemsHandler h;
		File si = new File(Selector.getPlugin().getDataFolder(),"ItemsInfo.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(si);
	   public ItemsHandler()
	   {
		   if (!new File(Selector.getPlugin().getDataFolder(), "ItemsInfo.yml").exists())
		   {
			   try
			{
				new File(Selector.getPlugin().getDataFolder(), "ItemsInfo.yml").createNewFile();
				config.set("ItemsAmount", 0);
				//config.set("World", "world");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	   }
	   
	   public static ItemsHandler getHandler()
	   {
		   if (h == null)
		   {
			   h = new ItemsHandler();
		   }
		   return h;
	   }
	   public int getAmount()
	   {
		   return config.getInt("ItemsAmount");
	   }
	   
	   public void SaveItem(String servername, String bungeeserver, int id)
	   {
				//config.set("Item" + config.getInt("ItemsAmount") + ".Spot", spot);
				config.set("Item" + config.getInt("ItemsAmount") + ".Slot", config.getInt("ItemsAmount"));
				config.set("Item" + config.getInt("ItemsAmount") + ".Name", servername);
				config.set("Item" + config.getInt("ItemsAmount") + ".ItemID", id);
				config.set("Item" + config.getInt("ItemsAmount") + ".BungeeServer", bungeeserver);
				config.set("ItemsAmount", config.getInt("ItemsAmount") + 1);
				try
				{
					config.save(si);
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		   
	   }
	   
		public int getItemID(String s)
		{
			for (int i = 0; i < getAmount(); i++)
			{
				if (config.getString("Item" + i + ".BungeeServer").equalsIgnoreCase(s))
				{
				return config.getInt("Item" + i + ".ID");
				}
			}
			return 0;
		}
		
		public int getItemID(int x)
		{
			if (x < getAmount())
			{
				return config.getInt("Item" + x + ".ItemID");
			}
			return 0;
		}
		
		public String getItemName(int x)
		{
			if (x < getAmount())
			{
				return config.getString("Item" + x + ".Name");
			}
			return "";
		}
		
		public int getItemSpot(String s)
		{
			for (int i = 0; i < getAmount(); i++)
			{
				if (config.getString("Item" + i + ".BungeeServer").equalsIgnoreCase(s))
				{
				return config.getInt("Item" + i + ".Spot");
				}
			}
			return 46;
		}
		public String getBungeeServer(String name)
		{
			for (int i = 0; i < getAmount(); i++)
			{
				if (config.getString("Item" + i + ".Name").equalsIgnoreCase(name))
				{
				return config.getString("Item" + i + ".BungeeServer");
				}
			}
			return "";
		}
		
		public String getBungeeServer(int x)
		{
			if (x < getAmount())
			{
				return config.getString("Item" + x + ".BungeeServer");
			}
			return "";
		}
		/*
		 * Future Update.
		 */
		public String getIP(String s)
		{
			for (int i = 0; i < getAmount(); i++)
			{
				if (config.getString("Item" + i + ".BungeeServer").equalsIgnoreCase(s))
				{
				return config.getString("Item" + i + ".IP");
				}
			}
			return "0.0.0.0:25565";

		}
		public String getName(String s)
		{
			for (int i = 0; i < getAmount(); i++)
			{
				if (config.getString("Item" + i + ".BungeeServer").equalsIgnoreCase(s))
				{
				return config.getString("Item" + i + ".Name");
				}
			}
			
			return "";
		}
	   
}
