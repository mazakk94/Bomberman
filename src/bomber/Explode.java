package bomber;

public class Explode extends GameObject {

	Explode(int x, int y, int rang) {
		pos_x = x;
		pos_y = y;
		range = rang;
		FPS = 30;
	}

	public boolean EndOfExplode() {
		if (FPS == 0)
			return true;

		else {
			FPS--;
			return false;
		}
	}

}
