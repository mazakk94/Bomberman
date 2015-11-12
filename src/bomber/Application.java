package bomber;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import bomber.KeyInputHandler.Val;

public class Application extends Canvas {

	private static final long serialVersionUID = 1L;
	private static int ScreenHeight = 1028;
	private static int ScreenWidth = 768;

	private static boolean Mute = false;
	private static boolean TutPage = true;
	private static boolean AppIsRunning = true;

	static final private int Game = 0;
	static final private int Credits = 1;
	static final private int Menu = 2;
	static final private int Tutorial = 3;
	static final private int Victory = 4;
	private static int GameState = Menu;

	public static BufferedImage[] map = new BufferedImage[4];
	public static Matrix matrix = new Matrix();

	private static BufferStrategy strategy;

	static KeyInputHandler Keys = new KeyInputHandler();
	static MouseInputHandler Mouse = new MouseInputHandler();

	public Application() {
		initUI();
		addKeyListener(Keys);
		addMouseListener(Mouse);
		requestFocus();
		this.createBufferStrategy(2);
		strategy = getBufferStrategy();
	}

	private Point RetPos() {
		return this.getLocationOnScreen();
	}

	public Application(int x, int y) {
		ScreenWidth = x;
		ScreenHeight = y;
		initUI();
		addKeyListener(Keys);
		addMouseListener(Mouse);
		requestFocus();
		this.createBufferStrategy(2);
		strategy = getBufferStrategy();
	}

	private void initUI() {
		JFrame container = new JFrame("Bomberriino!");
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(ScreenHeight, ScreenWidth));
		panel.setLayout(null);
		setBounds(0, 0, ScreenHeight, ScreenWidth);
		panel.add(this);
		setIgnoreRepaint(true);
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setBackground(Color.lightGray);
	}

	public static void main(String[] args) throws IOException,
			InterruptedException, FontFormatException {

		boolean AI = true;
		boolean Alive1 = true;
		boolean Alive2 = true;
		map[0] = ImageIO.read(new File("./gfx/Obszar/floor.png"));
		map[1] = ImageIO.read(new File("./gfx/Bonus/skrzynkaclose.png"));
		map[2] = ImageIO.read(new File("./gfx/Obszar/box.png"));
		map[3] = ImageIO.read(new File("./gfx/Obszar/wall.png"));
		BufferedImage UnMutBcg = ImageIO.read(new File(
				"./gfx/Menu/menu_unmute.jpg")); // when unmuted
		BufferedImage MutBcg = ImageIO
				.read(new File("./gfx/Menu/menu_mute.jpg")); // when muted
		BufferedImage Tut1 = ImageIO.read(new File("./gfx/Tutorial/tut1.png"));
		BufferedImage Tut2 = ImageIO.read(new File("./gfx/Tutorial/tut2.png"));
		BufferedImage credits = ImageIO.read(new File("./gfx/Credits.png"));
		BufferedImage Play1Won = ImageIO.read(new File(
				"./gfx/Koniec/player1_win.png"));
		BufferedImage Play2Won = ImageIO.read(new File(
				"./gfx/Koniec/player2_win.png"));
		BufferedImage Play1 = ImageIO.read(new File("./gfx/Pamp1/bomb1.png"));
		BufferedImage Play2 = ImageIO.read(new File("./gfx/Pamp2/bomb3.png"));
		BufferedImage Bomb = ImageIO.read(new File(
				"./gfx/Bomba/etap0/bomb_biggest.png"));
		BufferedImage explosion = ImageIO.read(new File(
				"./gfx/Bomba/etap1/krzyz1.png"));
		BufferedImage backg = ImageIO.read(new File("./gfx/backg.png"));
		BufferedImage AIChoose = ImageIO.read(new File("./gfx/AI.png"));
		Application MyApp = new Application();

		Game OurGame = new Game();
		Graphics2D g = null;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		Font MyFont = Font.createFont(Font.TRUETYPE_FONT,
				new FileInputStream(new File("./font/HoboStd.otf")))
				.deriveFont(Font.PLAIN, 24);

		while (AppIsRunning) // dopoki ma dzialac aplikacja
		{
			g = (Graphics2D) strategy.getDrawGraphics();

			Mouse.SetPos(MyApp.RetPos()); // zaktualizuj myszce polozenie tego
											// okna

			switch (GameState) // sprawdzaj jaki masz stan gry
			{

			case Menu: {
				if (Mouse.collisions[0])
					GameState = Tutorial;
				if (Mouse.collisions[1])
					Mute = !Mute;
				if (Mouse.collisions[2])
					GameState = Credits;
				if (Mouse.collisions[3])
					AppIsRunning = false; // zmieniaj stany gry
				Mouse.clear();
				if (Mute)// w zaleznosci od muta rysuj dany ekran
					g.drawImage(MutBcg, 0, 0, null);
				else
					g.drawImage(UnMutBcg, 0, 0, null);

				g.dispose();
				strategy.show();

				try {
					Thread.sleep(15);
				} catch (Exception e) {
				}
				break;
			}

			case Credits: {
				if (Keys.AnyKey())
					GameState = Menu;

				g.drawImage(credits, 0, 0, null);
				g.dispose();
				strategy.show();

				try {
					Thread.sleep(15);
				} catch (Exception e) {
				}
				break;
			}

			case Tutorial: {

				if (AI) {
					g.drawImage(AIChoose, 0, 0, null);
					if (Mouse.collisions[5]) {
						Alive1 = true;
						Alive2 = true;
						AI = false;
					}
					if (Mouse.collisions[6]) {
						Alive1 = false;
						Alive2 = true;
						AI = false;
					}
					if (Mouse.collisions[7]) {
						Alive1 = false;
						Alive2 = false;
						AI = false;
					}
				} else if (!Alive1 || !Alive2) {
					if (KeyInputHandler.Keys[Val.Space.ordinal()])
						TutPage = !TutPage;
					if (KeyInputHandler.Keys[Val.Enter.ordinal()])
						GameState = Game;

					if (TutPage)
						g.drawImage(Tut1, 0, 0, null);
					else
						g.drawImage(Tut2, 0, 0, null);
				} else
					GameState = Game;
				g.dispose();
				strategy.show();

				try {
					Thread.sleep(100);
				} catch (Exception e) {
				}
				break;

			}
			case Game: {
				OurGame.ResetGame(Alive1, Alive2);
				AI = true;
				while (!bomber.Game.Done) {
					
					matrix = OurGame.ReturnMatrix(); // zaktualizuj matrix z gry
					g = (Graphics2D) strategy.getDrawGraphics();
					g.setRenderingHints(rh);
					g.setFont(MyFont);

					OurGame.GameLoop();
					g.drawImage(backg, 0, 0, null);
					// rysowanie
					for (int i = 0; i < 17; i++)
						for (int j = 0; j < 17; j++)
							g.drawImage(map[matrix.getBlockID(i, j)], 40 * i,
									40 * j, null);
					if (bomber.Game.playerOne.Alive())
						g.drawImage(Play1, bomber.Game.playerOne.getX() * 40,
								bomber.Game.playerOne.getY() * 40, null);

					if (bomber.Game.playerTwo.Alive())
						g.drawImage(Play2, bomber.Game.playerTwo.getX() * 40,
								bomber.Game.playerTwo.getY() * 40, null);

					for (int i = 0; i < bomber.Game.bombs.size(); i++)
						g.drawImage(Bomb, bomber.Game.bombs.get(i).getX() * 40,
								bomber.Game.bombs.get(i).getY() * 40, null);
					for (int i = 0; i < bomber.Game.explosions.size(); i++) {

						int explosionX = bomber.Game.explosions.get(i).getX();
						int explosionY = bomber.Game.explosions.get(i).getY();
						g.drawImage(explosion, explosionX * 40,
								explosionY * 40, null);

						for (int j = explosionY - 1; j > explosionY
								- bomber.Game.explosions.get(i).getRange() - 1; j--)
							// north
							if (matrix.getBlockID(explosionX, j) == 0)
								g.drawImage(explosion, explosionX * 40, j * 40,
										null);
							else
								break;

						for (int j = explosionY + 1; j < explosionY
								+ bomber.Game.explosions.get(i).getRange() + 1; j++)
							// south
							if (matrix.getBlockID(explosionX, j) == 0)
								g.drawImage(explosion, explosionX * 40, j * 40,
										null);
							else
								break;
						for (int j = explosionX + 1; j < explosionX
								+ bomber.Game.explosions.get(i).getRange() + 1; j++)
							// east
							if (matrix.getBlockID(j, explosionY) == 0)
								g.drawImage(explosion, j * 40, explosionY * 40,
										null);
							else
								break;

						for (int j = explosionX - 1; j > explosionX
								- bomber.Game.explosions.get(i).getRange() - 1; j--)
							// west
							if (matrix.getBlockID(j, explosionY) == 0)
								g.drawImage(explosion, j * 40, explosionY * 40,
										null);
							else
								break;

					}

					g.drawString(
							Integer.toString(30 - bomber.Game.playerOne.Speed()),
							863, 129 + 15);
					g.drawString(
							Integer.toString(bomber.Game.playerOne.getRange()),
							893, 181 + 15);
					g.drawString(Integer.toString(bomber.Game.playerOne.Bombs()),
							868, 233 + 15);
					g.drawString(
							Integer.toString(30 - bomber.Game.playerTwo.Speed()),
							863, 330 + 15);
					g.drawString(
							Integer.toString(bomber.Game.playerTwo.getRange()),
							893, 384 + 15);
					g.drawString(Integer.toString(bomber.Game.playerTwo.Bombs()),
							868, 434 + 15);

					g.dispose();
					strategy.show();
					// koniec rysowania
				}
				GameState = Victory; // ustaw kto wygral
				break;
			}
			case Victory: {

				if (Keys.AnyKey())
					GameState = Menu;
				if (bomber.Game.playerOne.Alive()) // sprawdz kto wygral
					g.drawImage(Play1Won, 0, 0, null);
				else
					g.drawImage(Play2Won, 0, 0, null);

				g.dispose();
				strategy.show();
				try {
					Thread.sleep(15);
				} catch (Exception e) {
				}
				break;
			}
			}
		}
		System.exit(0);
	}
}