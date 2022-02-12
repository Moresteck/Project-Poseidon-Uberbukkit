package pl.moresteck.uberbukkit.protocol;

import org.bukkit.entity.Squid;

public class Protocol7 extends Protocol8 {

	@Override
	public boolean canReceiveBlockItem(int id) {
		switch (id) {
			case 354: // cake
				return false;
			case 353: // sugar
				return false;
			case 352: // bone
				return false;
			case 351: // dye
				return false;
			case 21: // lapis ore
				return false;
			case 22: // lapis block
				return false;
			case 23: // dispenser
				return false;
			case 24: // sandstone
				return false;
			case 25: // noteblock
				return false;
			default:
				return super.canReceiveBlockItem(id);
		}
	}

	@Override
	public boolean canReceivePacket(int id) {
		switch (id) {
			case 19: // entity action
				return false;
			case 25: // painting
				return false;
			case 40: // entity metadata
				return false;
			case 54: // play noteblock
				return false;
			default:
				return super.canReceivePacket(id);
		}
	}

	@Override
	public boolean canSeeMob(Class<?> claz) {
		if (Squid.class.isAssignableFrom(claz)) {
			return false;
		}
		return super.canSeeMob(claz);
	}
}
