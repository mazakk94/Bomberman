package bomber;

import java.util.Random;

public class Player extends GameObject {

	private int currBombs; // currBombs ready to drop
	protected int FPS;

	private int speed;
	private int bombs; // max of bombs
	private boolean alive; // is alive

	Random randomizer = new Random();

	public int Speed() {
		return speed;
	}

	public int Bombs() {
		return currBombs;
	}

	public Player(int x, int y, int id) {
		alive = true;
		range = 1;
		bombs = 1;
		currBombs = 1;
		pos_x = x;
		pos_y = y;
		speed = 10;
		FPS = speed;
	}

	public void DropBomb() // droping bomb
	{
		currBombs--;
	}

	public boolean CurrBombs() {
		if (currBombs != 0)
			return true;
		else
			return false;
	}

	public void AddBomb() {
		currBombs++;
		// bombs++;
	}

	public void Die() {
		alive = false;
	}

	public boolean Alive() {
		return alive;
	}

	public void MoveLeft() {
		pos_x--;
		FPS = speed;
	}

	public void MoveRight() {
		pos_x++;
		FPS = speed;
	}

	public void MoveUp() {
		pos_y--;
		FPS = speed;
	}

	public void MoveDown() {
		pos_y++;
		FPS = speed;
	}

	public void GetBonus() {
		int flag = randomizer.nextInt(3);
		switch (flag) {
		case 0: {
			if (bombs < 5) {
				currBombs++;
				bombs++;
			}
		}
			break;
		case 1: {
			if (speed > 10)
				speed--;
			break;
		}
		case 2: {
			if (range < 8)
				range++;
			break;
		}
		}
	}

	public boolean isMoving() {
		if (FPS == 0)
			return true;
		else {
			FPS--;
			return false;
		}

	}

	public int MakeDecision() {
		return 0;
	}

	public void getMatrix(Matrix myMatrix) {
	}

}
