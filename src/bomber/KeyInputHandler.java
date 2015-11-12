package bomber;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputHandler extends KeyAdapter {
	public enum Val {
		P1Left, P1Right, P1Up, P1Down, P1Bomb, P2Left, P2Right, P2Up, P2Down, P2Bomb, Space, Enter, size;
	}

	public boolean AnyKey() {
		for (int i = 0; i < Val.size.ordinal(); i++)
			if (Keys[i])
				return true;
		return false;
	}

	public void ClearKeys() {
		for (int i = 0; i < Val.size.ordinal(); i++)
			Keys[i] = false;
	}

	public static boolean DisableOne = false;
	public static boolean DisableTwo = false;

	public static boolean[] Keys = new boolean[Val.size.ordinal()];

	public void keyPressed(KeyEvent e) {

		if (!DisableOne) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				Keys[Val.P1Left.ordinal()] = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Keys[Val.P1Right.ordinal()] = true;
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				Keys[Val.P1Up.ordinal()] = true;
			}

			else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				Keys[Val.P1Down.ordinal()] = true;
			}

			else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				Keys[Val.P1Bomb.ordinal()] = true;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			Keys[Val.Space.ordinal()] = true;

		if (!DisableTwo) {
			if (e.getKeyCode() == KeyEvent.VK_A) {
				Keys[Val.P2Left.ordinal()] = true;
			} else if (e.getKeyCode() == KeyEvent.VK_D) {
				Keys[Val.P2Right.ordinal()] = true;
			} else if (e.getKeyCode() == KeyEvent.VK_W) {
				Keys[Val.P2Up.ordinal()] = true;
			}

			else if (e.getKeyCode() == KeyEvent.VK_S) {
				Keys[Val.P2Down.ordinal()] = true;
			}

			else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				Keys[Val.P2Bomb.ordinal()] = true;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Keys[Val.Enter.ordinal()] = true;
		}

	}

	// else if(GameState==Credits || GameState==Victory)
	// GameState=Menu;

	public void keyReleased(KeyEvent e) {
		ClearKeys();
	}
}