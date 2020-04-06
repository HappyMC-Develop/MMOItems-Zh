package net.Indyuce.mmoitems.stat;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import net.Indyuce.mmoitems.stat.type.DisableStat;

public class Disable_AdvancedEnchantments extends DisableStat {
	public Disable_AdvancedEnchantments() {
		super("ADVANCED_ENCHANTS", Material.ENCHANTED_BOOK, "Disable Advanced Enchants", new String[] { "all" }, "When toggled on, prevents players", "from applying AE onto this item.");

		if (Bukkit.getPluginManager().getPlugin("AdvancedEnchantments") == null)
			disable();
	}
}
