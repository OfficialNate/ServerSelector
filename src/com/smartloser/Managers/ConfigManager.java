package com.smartloser.Managers;

import java.util.HashMap;
import java.util.Map;

import com.smartloser.maze.Selector;

public class ConfigManager
{
	private Map<String, Object> configstuff = new HashMap<String, Object>();

	private static ConfigManager gm;

	private Selector i;

	public ConfigManager(Selector i)
	{
		this.i = i;
	}

	public static ConfigManager getManager(Selector i)
	{
		if (gm == null)
		{
			gm = new ConfigManager(i);
		}

		return gm;
	}
	public void setupConfig()
	{
		configstuff.put("SelectorItemID", this.i.getConfig().getInt(
				"SelectorItemID"));
		configstuff.put("InventoryName", this.i.getConfig().getString(
				"InventoryName"));
		configstuff.put("SelectorName", this.i.getConfig().getString(
				"SelectorName"));
		configstuff.put("Lore Message", this.i.getConfig().getString(
				"Lore Message"));
	}

	public Object getValue(String value)
	{
		if (this.configstuff.containsKey(value))
		{
			return this.configstuff.get(value);
		}
		return "Not Found";
	}
}
