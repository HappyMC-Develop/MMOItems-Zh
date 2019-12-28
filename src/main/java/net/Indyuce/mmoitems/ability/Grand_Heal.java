package net.Indyuce.mmoitems.ability;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.api.ability.Ability;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.player.PlayerStats.CachedStats;
import net.Indyuce.mmoitems.stat.data.AbilityData;

public class Grand_Heal extends Ability {
	public Grand_Heal() {
		super(CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK);

		addModifier("heal", 5);
		addModifier("radius", 5);
		addModifier("cooldown", 15);
		addModifier("mana", 0);
		addModifier("stamina", 0);
	}

	@Override
	public AbilityResult whenRan(CachedStats stats, LivingEntity target, AbilityData ability, ItemAttackResult result) {
		return new SimpleAbilityResult(ability);
	}

	@Override
	public void whenCast(CachedStats stats, AbilityResult ability, ItemAttackResult result) {
		double heal = ability.getModifier("heal");
		double radius = ability.getModifier("radius");

		MMOUtils.heal(stats.getPlayer(), heal);
		stats.getPlayer().getWorld().playSound(stats.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
		stats.getPlayer().getWorld().spawnParticle(Particle.HEART, stats.getPlayer().getLocation().add(0, .75, 0), 16, 1, 1, 1, 0);
		stats.getPlayer().getWorld().spawnParticle(Particle.VILLAGER_HAPPY, stats.getPlayer().getLocation().add(0, .75, 0), 16, 1, 1, 1, 0);
		for (Entity entity : stats.getPlayer().getNearbyEntities(radius, radius, radius))
			if (entity instanceof Player)
				MMOUtils.heal((Player) entity, heal);
	}
}
