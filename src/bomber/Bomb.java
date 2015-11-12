package bomber;

public class Bomb extends GameObject {

	private int idOfPlayer;

	public Bomb(int x, int y, int rg, int IdOfPlayer) // constructor for player
	{
		idOfPlayer = IdOfPlayer;
		pos_x = x;
		pos_y = y;
		range = rg;
		FPS = 120;
	}

	public boolean isBlown() {
		/*
		 * if (date.getTime() - seconds >= 2000) return true; else return false;
		 */
		if (FPS == 0)
			return true;
		else {
			FPS--;
			return false;
		}
	}

	public int getPlayerID() {
		return idOfPlayer;
	}

}