package pl.moresteck.uberbukkit.protocol;

public class Protocol6 extends Protocol7 {

	@Override
	public boolean canReceiveBlockItem(int id) {
	    return super.canReceiveBlockItem(id);
	}

	@Override
	public boolean canReceivePacket(int id) {
		switch (id) {
			case 100: // inventory stuff
				return false;
			case 101: // inventory stuff
				return false;
			case 102: // inventory stuff
				return false;
			case 103: // inventory stuff
                return false;
			case 104: // inventory stuff
                return false;
			case 105: // inventory stuff
                return false;
			case 106: // inventory stuff
                return false;
			case 130: // sign edit
                return false;
			default:
				return super.canReceivePacket(id);
		}
	}

	@Override
	public boolean canSeeMob(Class<?> claz) {
	    return super.canSeeMob(claz);
	}
}
