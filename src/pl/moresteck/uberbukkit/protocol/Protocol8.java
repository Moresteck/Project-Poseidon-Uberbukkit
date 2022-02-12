package pl.moresteck.uberbukkit.protocol;

public class Protocol8 extends Protocol9 {

	@Override
	public boolean canReceiveBlockItem(int id) {
		switch (id) {
			case 355: // bed
				return false;
			case 356: // redstone repeater
				return false;
			default:
				return super.canReceiveBlockItem(id);
		}
	}

	@Override
	public boolean canReceivePacket(int id) {
		switch (id) {
			case 17: // bed error
				return false;
			case 27: // sneak
				return false;
			default:
				return super.canReceivePacket(id);
		}
	}
}
