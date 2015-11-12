package bomber;

public abstract class GameObject {

	protected int pos_x;
	protected int pos_y;
	protected int FPS;
	protected int range;

	public int getRange() {
		return range;
	}

	public int getX() {
		return pos_x;
	}

	public int getY() {
		return pos_y;
	}

}
