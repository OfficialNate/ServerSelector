package com.smartloser.Events;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.smartloser.Handlers.ItemsHandler;
import com.smartloser.Managers.ConfigManager;
import com.smartloser.Managers.ServerManager;
import com.smartloser.maze.Selector;

public class EventsHandler implements Listener
{
	public Selector plugin;
   private ItemsHandler ih;
   private ConfigManager gm;
   private ServerManager sm;
	public EventsHandler(Selector i)
	{
		this.plugin = i;
		ih = ItemsHandler.getHandler();
		gm = ConfigManager.getManager(i);
		sm = ServerManager.getManager();
	}
  
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent evt)
	{
		Player p = evt.getPlayer();
		if (!p.getInventory().contains(Material.getMaterial(Integer.parseInt(gm.getValue("SelectorItemID").toString()))))
		{
			ItemStack item = new ItemStack(Material.getMaterial(Integer.parseInt(gm.getValue("SelectorItemID").toString())));
			ItemMeta im = item.getItemMeta();
			String s = ChatColor.translateAlternateColorCodes('&', gm.getValue("SelectorName").toString());
			im.setDisplayName(s);
			item.setItemMeta(im);
			p.getInventory().addItem(new ItemStack[] { item });
		}

	}
	
	@EventHandler
	public void click(InventoryClickEvent e)
	{
		Player p = (Player)e.getWhoClicked();
	    if (!e.getInventory().getName().equalsIgnoreCase(Selector.getInventory().getName()))
	    {
	    	return;
	    }
	    Inventory iv = e.getInventory();
	    ItemStack it = e.getCurrentItem();
	    String server = sm.getServerSpecific(ChatColor.stripColor(it.getItemMeta().getDisplayName()));
	    if (server != "")
	    {
	    	p.closeInventory();
	    	Transfer(p, server);
	    	
	    }
		
	}
	
	@EventHandler
	public void InteractEvent(PlayerInteractEvent e)
	{
		if (e.getPlayer().getItemInHand().getTypeId() == Integer.parseInt(gm.getValue("SelectorItemID").toString()) && e.getAction() == Action.RIGHT_CLICK_AIR)
				{
			      e.getPlayer().openInventory(Selector.getInventory());
				}
	}
	
	
	public static boolean isOnline(String ip)
	{
		String[] addr = ip.split(":");
		try
		{
			Socket s = new Socket();
			s.connect(new InetSocketAddress(addr[0], Integer.parseInt(addr[1])));
		//	Bukkit.getServer().broadcastMessage(addr[0] + ":" + addr[1]);
			s.close();
			return true;
		}
		catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void sendCount(String server_name)
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
			out.writeUTF(server_name);
			Player player = Bukkit.getOnlinePlayers()[0];

			player
					.sendPluginMessage(Selector.getPlugin(), "BungeeCord", o
							.toByteArray());
		}
		catch (Exception e)
		{

		}

	}
	
	  public void Transfer(Player p, String server)
	  {
		  ByteArrayOutputStream b = new ByteArrayOutputStream();
		  DataOutputStream out = new DataOutputStream(b);
		   
		  try {
		      out.writeUTF("Connect");
		      
		      out.writeUTF(server); // Target Server
		  } catch (IOException e) {
		      // Can never happen
		  }
		  p.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray());
	  }
}
