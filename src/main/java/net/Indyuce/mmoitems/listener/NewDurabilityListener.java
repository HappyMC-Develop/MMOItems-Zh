package net.Indyuce.mmoitems.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

import net.Indyuce.mmoitems.api.interaction.util.DurabilityItem;

public class NewDurabilityListener implements Listener {

	@EventHandler(ignoreCancelled = true)
	public void a(PlayerItemDamageEvent event) {
		DurabilityItem item = new DurabilityItem(event.getPlayer(), event.getItem());
		if (item.isValid()) {

			/*
			 * calculate item durability loss
			 */
			item.decreaseDurability(event.getDamage());

			/*
			 * if the item is broken and if it is meant to be lost when broken,
			 * do NOT cancel the event and make sure the item is destroyed
			 */
			if (item.isBroken() && item.isLostWhenBroken()) {
				event.setDamage(999);
				return;
			}

			event.setCancelled(true);
			event.getItem().setItemMeta(item.toItem().getItemMeta());
		}
	}
}
