package net.Indyuce.mmoitems.ability;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.api.ability.Ability;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ability.VectorAbilityResult;
import net.Indyuce.mmoitems.api.player.PlayerStats.CachedStats;
import net.Indyuce.mmoitems.stat.data.AbilityData;

public class TNT_Throw extends Ability implements Listener {
	public TNT_Throw() {
		super(CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK);

		addModifier("cooldown", 10);
		addModifier("force", 1);
		addModifier("mana", 0);
		addModifier("stamina", 0);
	}

	@Override
	public AbilityResult whenRan(CachedStats stats, LivingEntity target, AbilityData ability, ItemAttackResult result) {
		return new VectorAbilityResult(ability, stats.getPlayer(), target);
	}

	@Override
	public void whenCast(CachedStats stats, AbilityResult ability, ItemAttackResult result) {
		Vector vec = ((VectorAbilityResult) ability).getTarget().multiply(2 * ability.getModifier("force"));
		TNTPrimed tnt = (TNTPrimed) stats.getPlayer().getWorld().spawnEntity(stats.getPlayer().getLocation().add(0, 1, 0), EntityType.PRIMED_TNT);
		tnt.setFuseTicks(80);
		tnt.setVelocity(vec);
		MMOItems.plugin.getEntities().registerCustomEntity(tnt);
		stats.getPlayer().getWorld().playSound(stats.getPlayer().getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1, 0);
		stats.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, stats.getPlayer().getLocation().add(0, 1, 0), 12, 0, 0, 0, .1);
	}


	@EventHandler
	public void a(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (damager instanceof TNTPrimed)
			if (MMOItems.plugin.getEntities().isCustomEntity(damager))
				if (!MMOUtils.canDamage(event.getEntity()))
					event.setCancelled(true);
	}
}
