package net.Indyuce.mmoitems.stat;

import org.bukkit.attribute.Attribute;

import net.Indyuce.mmoitems.api.item.build.MMOItemBuilder;
import net.Indyuce.mmoitems.api.util.StatFormat;
import net.Indyuce.mmoitems.stat.data.StatData;
import net.Indyuce.mmoitems.stat.type.AttributeStat;
import net.mmogroup.mmolib.MMOLib;
import net.mmogroup.mmolib.api.item.ItemTag;
import net.mmogroup.mmolib.version.VersionMaterial;

public class Attack_Speed extends AttributeStat {
	public Attack_Speed() {
		super("ATTACK_SPEED", VersionMaterial.LIGHT_GRAY_DYE.toItem(), "Attack Speed", new String[] { "The speed at which your weapon strikes.", "In attacks/sec." }, Attribute.GENERIC_ATTACK_SPEED, MMOLib.plugin.getVersion().isBelowOrEqual(1, 12) ? 1.6 : 4);
	}

	@Override
	public boolean whenApplied(MMOItemBuilder item, StatData data) {
		double value = ((DoubleData) data).generateNewValue();
		item.addItemTag(new ItemTag("MMOITEMS_ATTACK_SPEED", value));
		item.getLore().insert("attack-speed", format(value, "#", new StatFormat("##").format(value)));
		return true;
	}
}
