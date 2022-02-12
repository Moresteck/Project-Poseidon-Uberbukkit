package pl.moresteck.uberbukkit.protocol;

public class Protocol13 extends Protocol14 {

	@Override
	public boolean canReceiveBlockItem(int id) {
		switch (id) {
			case 29: // piston
				return false;
			case 33: // sticky piston
				return false;
			case 34: // piston extension
				return false;
			case 36: // piston moving piece
				return false;
			case 359: // shears
				return false;
			default:
				return super.canReceiveBlockItem(id);
		}
	}
}
