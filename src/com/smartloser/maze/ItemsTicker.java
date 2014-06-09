package com.smartloser.maze;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.smartloser.Handlers.ItemsHandler;
import com.smartloser.Managers.ConfigManager;
import com.smartloser.Managers.ServerManager;

public class ItemsTicker extends BukkitRunnable
{
	
	ItemsHandler ih = ItemsHandler.getHandler();
	
	public void run()
	{
		//Bukkit.getServer().broadcastMessage("TEst");
      for (int i = 0; i < ih.getAmount(); i++)
      {
    	  ItemStack is = Selector.getInventory().getItem(i);
    	  String name = ChatColor.stripColor(is.getItemMeta().getDisplayName());
    	  sendCount(ServerManager.getManager().getServerSpecific(name));
    	  int count = ServerManager.getManager().getPlayers(name);
    	  List<String> lore = new ArrayList<String>();
    	  lore.add("");
    	  lore.add(ChatColor.translateAlternateColorCodes('&',(ConfigManager.getManager(Selector.getPlugin()).getValue("Lore Message").toString().replaceAll("!players", "" + count))));
    	  lore.add("");
    	  ItemMeta im = is.getItemMeta();
    	  im.setLore(lore);
    	  is.setItemMeta(im);
      }
	}
	
	public void sendCount(String server)
	{
		if (Bukkit.getOnlinePlayers().length == 0)
		{
			return;
		}
		try
		{
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(o);
			out.writeUTF("PlayerCount");
			out.writeUTF(server);
			Player p = Bukkit.getOnlinePlayers()[0];

			p.sendPluginMessage(Selector.getPlugin(), "BungeeCord", o.toByteArray());
		}
		catch (Exception e)
		{

		}

	}
}
