package bomber;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInputHandler extends MouseAdapter {

	public boolean collisions[] = new boolean[8];

	private Point Pos;

	public void clear() {
		for (int i = 0; i < 8; i++)
			collisions[i] = false;
	}

	public void SetPos(Point p) {
		Pos = p;
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int PosX = MouseInfo.getPointerInfo().getLocation().x - Pos.x;
			int PosY = MouseInfo.getPointerInfo().getLocation().y - Pos.y;

			if (PosX >= 625 && PosX <= 980 && PosY >= 75 && PosY <= 125)// Let's
																		// bomb
			{
				// System.out.println("Let's bomb");
				collisions[0] = true;
			} else if (PosX >= 25 && PosX <= 115 && PosY >= 670 && PosY <= 725) // Mute
			{
				// System.out.println("Mute");
				collisions[1] = true;
			}

			else if (PosX >= 115 && PosX <= 370 && PosY >= 620 && PosY <= 670)// Credits
			{
				// System.out.println("Credits");
				collisions[2] = true;
			} else if (PosX >= 845 && PosX <= 980 && PosY >= 690 && PosY <= 745) // exit
			{
				// System.out.println("Exit");
				collisions[3] = true;
			} else if (PosX >= 71 && PosX <= 71 + 236 && PosY >= 89
					&& PosY <= 89 + 44) // exit
			{
				// System.out.println("Exit");
				collisions[5] = true;
			} else if (PosX >= 71 && PosX <= 71 + 276 && PosY >= 186
					&& PosY <= 186 + 44) // exit
			{
				// System.out.println("Exit");
				collisions[6] = true;
			} else if (PosX >= 71 && PosX <= 71 + 315 && PosY >= 292
					&& PosY <= 292 + 44) // exit
			{
				// System.out.println("Exit");
				collisions[7] = true;
			}

			collisions[4] = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		clear();
	}

}
