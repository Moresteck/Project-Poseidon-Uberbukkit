package pl.moresteck.uberbukkit.protocol;

public class Protocol10 extends Protocol11 {

	@Override
	public boolean canReceiveBlockItem(int id) {
		switch (id) {
			case 30: // cobweb
				return false;
			case 27: // powered rails
				return false;
			case 28: // detector rails
				return false;
			default:
				return super.canReceiveBlockItem(id);
		}
	}

	@Override
	public boolean canReceivePacket(int id) {
		switch (id) {
			case 200: // statistics
				return false;
			case 71: // weather
				return false;
			default:
				return super.canReceivePacket(id);
		}
	}
}
