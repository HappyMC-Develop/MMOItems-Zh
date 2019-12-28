package net.Indyuce.mmoitems.ability;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.MMOUtils;
import net.Indyuce.mmoitems.api.ItemAttackResult;
import net.Indyuce.mmoitems.api.ability.Ability;
import net.Indyuce.mmoitems.api.ability.AbilityResult;
import net.Indyuce.mmoitems.api.ability.SimpleAbilityResult;
import net.Indyuce.mmoitems.api.player.PlayerStats.CachedStats;
import net.Indyuce.mmoitems.stat.data.AbilityData;

public class Frozen_Aura extends Ability implements Listener {
	public Frozen_Aura() {
		super(CastingMode.ON_HIT, CastingMode.WHEN_HIT, CastingMode.LEFT_CLICK, CastingMode.RIGHT_CLICK, CastingMode.SHIFT_LEFT_CLICK, CastingMode.SHIFT_RIGHT_CLICK);

		addModifier("duration", 6);
		addModifier("amplifier", 1);
		addModifier("radius", 10);
		addModifier("cooldown", 10);
		addModifier("mana", 0);
		addModifier("stamina", 0);
	}

	@Override
	public AbilityResult whenRan(CachedStats stats, LivingEntity target, AbilityData ability, ItemAttackResult result) {
		return new SimpleAbilityResult(ability);
	}

	@Override
	public void whenCast(CachedStats stats, AbilityResult ability, ItemAttackResult result) {
		double duration = ability.getModifier("duration") * 20;
		double radiusSquared = Math.pow(ability.getModifier("radius"), 2);
		double amplifier = ability.getModifier("amplifier") - 1;

		new BukkitRunnable() {
			double j = 0;
			int ti = 0;

			public void run() {
				if (ti++ > duration)
					cancel();

				j += Math.PI / 60;
				for (double k = 0; k < Math.PI * 2; k += Math.PI / 2)
					stats.getPlayer().getWorld().spawnParticle(Particle.SPELL_INSTANT, stats.getPlayer().getLocation().add(Math.cos(k + j) * 2, 1 + Math.sin(k + j * 7) / 3, Math.sin(k + j) * 2), 0);

				if (ti % 2 == 0)
					stats.getPlayer().getWorld().playSound(stats.getPlayer().getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);

				if (ti % 7 == 0)
					for (Entity entity : MMOUtils.getNearbyChunkEntities(stats.getPlayer().getLocation()))
						if (entity.getLocation().distanceSquared(stats.getPlayer().getLocation()) < radiusSquared && MMOUtils.canDamage(stats.getPlayer(), entity)) {
							((LivingEntity) entity).removePotionEffect(PotionEffectType.SLOW);
							((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, (int) amplifier));
						}
			}
		}.runTaskTimer(MMOItems.plugin, 0, 1);
	}
}
