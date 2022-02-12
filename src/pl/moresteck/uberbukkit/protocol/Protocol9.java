package pl.moresteck.uberbukkit.protocol;

import org.bukkit.entity.Wolf;

public class Protocol9 extends Protocol10 {

	@Override
	public boolean canReceiveBlockItem(int id) {
		switch (id) {
			case 95: // locked chest
				return false;
			case 357: // cookie
				return false;
			default:
				return super.canReceiveBlockItem(id);
		}
	}

	@Override
	public boolean canReceivePacket(int id) {
		switch (id) {
			case 70: // bed
				return false;
			default:
				return super.canReceivePacket(id);
		}
	}

	@Override
	public boolean canSeeMob(Class<?> claz) {
		if (Wolf.class.isAssignableFrom(claz)) {
			return false;
		}
		return super.canSeeMob(claz);
	}
}
