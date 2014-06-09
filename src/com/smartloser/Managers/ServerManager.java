package com.smartloser.Managers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServerManager
{
   private Map<String, Integer> servers = new HashMap<String, Integer>();
   private static ServerManager sm;
   
   public ServerManager()
   {
	   
   }
   
   public static ServerManager getManager()
   {
	   if (sm == null) 
		   sm = new ServerManager();
	   
	   return sm;
   }
   
   public void registerServer(String s)
   {
	  this.servers.put(s, -1);
   }
   
   public int getPlayers(String s)
   {
	   if (!s.contains(s))
		   return -1;
	   
	   return this.servers.get(s);
   }
   
   public void savePlayers(String s, int i)
   {
	   this.servers.put(s, i);
   }
   
   public String[] getServers()
   {
	   return Arrays.asList(this.servers.keySet().toArray()).toArray(new String[this.servers.keySet().toArray().length]);
   }
   
   public String getServerSpecific(String s)
   {
	   for (String p : this.servers.keySet())
	   {
		   if (p.equalsIgnoreCase(s))
			   return p;
	   }
	   return "";
   }
   
   public void resetMap()
   {
	   this.servers.clear();
   }
  
}
