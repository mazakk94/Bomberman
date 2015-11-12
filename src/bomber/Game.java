package bomber;

import java.util.Date;
import java.util.Random;
import java.util.Vector;

import bomber.KeyInputHandler.Val;

class Game {
	public static Vector<Explode> explosions = new Vector<Explode>();
	public static Vector<Bomb> bombs = new Vector<Bomb>();
	public static Player playerOne;
	public static Player playerTwo;
	public static Matrix matrix = new Matrix();
	static int FPS;
	public static boolean Done;
	static final int box = 2;
	static final int wall = 3;
	static final int floor = 0;
	static final int randomBonus = 1;
	static final int bonusChance = 27;
	static Random randomizer = new Random();
	long begin;
	boolean firstBomb = true;
	boolean firstBomb2 = true;
	int fpsAI = 0;
	int AIfps = 0;
	int afterBomb = 0;
	int bombAfter = 0;
	long end;
	static Date data = new Date();
	static KeyInputHandler Keys = new KeyInputHandler();

	static boolean FirstPlayerAI = false;
	static boolean SecondPlayerAI = false;

	public Matrix ReturnMatrix() {
		return matrix;
	}

	public Game() {
		ResetGame(true, true);
	}

	public boolean isBombAt(int playerX, int playerY) {
		for (int i = 0; i < bombs.size(); i++) {
			if (bombs.get(i).getX() == playerX
					&& bombs.get(i).getY() == playerY) {
				return true;
			}
		}
		return false;
	}

	public void ResetGame(boolean One, boolean Two)// constructor
	{
		matrix = new Matrix();
		playerOne = new Player(1, 1, 1);
		playerTwo = new Player(15, 15, 2);
		explosions.clear();
		bombs.clear();
		FPS = 0;
		Done = false;
		KeyInputHandler.DisableOne = One;
		KeyInputHandler.DisableTwo = Two;

		FirstPlayerAI = One;
		SecondPlayerAI = Two;
		if (FirstPlayerAI)
			playerOne = new AiPlayer(1, 1, 1);
		else
			playerOne = new Player(1, 1, 1);
		if (SecondPlayerAI)
			playerTwo = new AiPlayer(15, 15, 2);
		else
			playerTwo = new Player(15, 15, 2);
	}

	public void GameLoop() {
		if (!Done) {
			begin = System.nanoTime();
			if (FPS == 60)
				FPS = 0;
			FPS++;

			if (FirstPlayerAI) {
				playerOne.getMatrix(matrix);
				switch (playerOne.MakeDecision()) {
				case 1:
					KeyInputHandler.Keys[Val.P1Up.ordinal()] = true;
					break;
				case 2:
					KeyInputHandler.Keys[Val.P1Right.ordinal()] = true;
					break;
				case 3:
					KeyInputHandler.Keys[Val.P1Down.ordinal()] = true;
					break;
				case 4:
					KeyInputHandler.Keys[Val.P1Left.ordinal()] = true;
					break;
				case 5:
					KeyInputHandler.Keys[Val.P1Bomb.ordinal()] = true;
					break;
				}
			}

			if (SecondPlayerAI) {
				playerTwo.getMatrix(matrix);
				switch (playerTwo.MakeDecision()) {
				case 1:
					KeyInputHandler.Keys[Val.P2Up.ordinal()] = true;
					break;
				case 2:
					KeyInputHandler.Keys[Val.P2Right.ordinal()] = true;
					break;
				case 3:
					KeyInputHandler.Keys[Val.P2Down.ordinal()] = true;
					break;
				case 4:
					KeyInputHandler.Keys[Val.P2Left.ordinal()] = true;
					break;
				case 5:
					KeyInputHandler.Keys[Val.P2Bomb.ordinal()] = true;
					break;
				}
			}

			// obsluga klawiszy
			if (playerOne.isMoving()) {// robimy chodzenie
				if (KeyInputHandler.Keys[Val.P1Left.ordinal()]) {

					KeyInputHandler.Keys[Val.P1Left.ordinal()] = false;
					if (matrix.getBlockID(playerOne.getX() - 1,
							playerOne.getY()) == floor
							&& !(playerTwo.getX() == playerOne.getX() - 1 && playerOne
									.getY() == playerTwo.getY())
							&& !(isBombAt(playerOne.getX() - 1,
									playerOne.getY())))
						playerOne.MoveLeft();
					else if (matrix.getBlockID(playerOne.getX() - 1,
							playerOne.getY()) == randomBonus) {
						playerOne.GetBonus();
						matrix.updateMatrix(playerOne.getX() - 1,
								playerOne.getY(), 0);
						playerOne.MoveLeft();
					}
				}

				else if (KeyInputHandler.Keys[Val.P1Right.ordinal()]) {

					KeyInputHandler.Keys[Val.P1Right.ordinal()] = false;
					if (matrix.getBlockID(playerOne.getX() + 1,
							playerOne.getY()) == floor
							&& !(playerTwo.getX() == playerOne.getX() + 1 && playerOne
									.getY() == playerTwo.getY())
							&& !(isBombAt(playerOne.getX() + 1,
									playerOne.getY())))
						playerOne.MoveRight();

					else if (matrix.getBlockID(playerOne.getX() + 1,
							playerOne.getY()) == randomBonus) {
						playerOne.GetBonus();
						matrix.updateMatrix(playerOne.getX() + 1,
								playerOne.getY(), 0);
						playerOne.MoveRight();
					}
				}

				else if (KeyInputHandler.Keys[Val.P1Up.ordinal()]) {
					KeyInputHandler.Keys[Val.P1Up.ordinal()] = false;

					if (matrix.getBlockID(playerOne.getX(),
							playerOne.getY() - 1) == floor
							&& !(playerTwo.getX() == playerOne.getX() && playerTwo
									.getY() == playerOne.getY() - 1)
							&& !(isBombAt(playerOne.getX(),
									playerOne.getY() - 1)))
						playerOne.MoveUp();

					else if (matrix.getBlockID(playerOne.getX(),
							playerOne.getY() - 1) == randomBonus) {
						playerOne.GetBonus();
						matrix.updateMatrix(playerOne.getX(),
								playerOne.getY() - 1, 0);
						playerOne.MoveUp();
					}
				}

				else if (KeyInputHandler.Keys[Val.P1Down.ordinal()]) {

					KeyInputHandler.Keys[Val.P1Down.ordinal()] = false;
					if (matrix.getBlockID(playerOne.getX(),
							playerOne.getY() + 1) == floor
							&& !(playerTwo.getX() == playerOne.getX() && playerOne
									.getY() + 1 == playerTwo.getY())
							&& !(isBombAt(playerOne.getX(),
									playerOne.getY() + 1)))
						playerOne.MoveDown();

					else if (matrix.getBlockID(playerOne.getX(),
							playerOne.getY() + 1) == randomBonus) {
						playerOne.GetBonus();
						matrix.updateMatrix(playerOne.getX(),
								playerOne.getY() + 1, 0);
						playerOne.MoveDown();
					}
				}

				if (KeyInputHandler.Keys[Val.P1Bomb.ordinal()]) {
					KeyInputHandler.Keys[Val.P1Bomb.ordinal()] = false;
					if (playerOne.CurrBombs()) {
						if (!isBombAt(playerOne.getX(), playerOne.getY())) {
							fpsAI = 130;
							if (firstBomb) {
								afterBomb = 2;
								firstBomb = false;
							} else
								afterBomb = 3;
							bombs.add(new Bomb(playerOne.getX(), playerOne
									.getY(), playerOne.getRange(), 0));
							playerOne.DropBomb();
						}
					}
				}
			} // konczymy chodzenie
			if (playerTwo.isMoving()) {
				if (KeyInputHandler.Keys[Val.P2Left.ordinal()]) {
					KeyInputHandler.Keys[Val.P2Left.ordinal()] = false;
					if (matrix.getBlockID(playerTwo.getX() - 1,
							playerTwo.getY()) == floor
							&& !(playerTwo.getX() - 1 == playerOne.getX() && playerOne
									.getY() == playerTwo.getY())
							&& !(isBombAt(playerTwo.getX() - 1,
									playerTwo.getY())))

						playerTwo.MoveLeft();

					else if (matrix.getBlockID(playerTwo.getX() - 1,
							playerTwo.getY()) == randomBonus) {
						playerTwo.GetBonus();
						matrix.updateMatrix(playerTwo.getX() - 1,
								playerTwo.getY(), 0);
						playerTwo.MoveLeft();
					}
				}

				else if (KeyInputHandler.Keys[Val.P2Right.ordinal()]) {

					KeyInputHandler.Keys[Val.P2Right.ordinal()] = false;
					if (matrix.getBlockID(playerTwo.getX() + 1,
							playerTwo.getY()) == floor
							&& !(playerTwo.getX() + 1 == playerOne.getX() && playerOne
									.getY() == playerTwo.getY())
							&& !(isBombAt(playerTwo.getX() + 1,
									playerTwo.getY())))
						playerTwo.MoveRight();

					else if (matrix.getBlockID(playerTwo.getX() + 1,
							playerTwo.getY()) == randomBonus) {
						playerTwo.GetBonus();
						matrix.updateMatrix(playerTwo.getX() + 1,
								playerTwo.getY(), 0);
						playerTwo.MoveRight();
					}
				}

				else if (KeyInputHandler.Keys[Val.P2Up.ordinal()]) {
					KeyInputHandler.Keys[Val.P2Up.ordinal()] = false;
					if (matrix.getBlockID(playerTwo.getX(),
							playerTwo.getY() - 1) == floor
							&& !(playerTwo.getX() == playerOne.getX() && playerOne
									.getY() == playerTwo.getY() - 1)
							&& !(isBombAt(playerTwo.getX(),
									playerTwo.getY() - 1)))
						playerTwo.MoveUp();

					else if (matrix.getBlockID(playerTwo.getX(),
							playerTwo.getY() - 1) == randomBonus) {
						playerTwo.GetBonus();
						matrix.updateMatrix(playerTwo.getX(),
								playerTwo.getY() - 1, 0);
						playerTwo.MoveUp();
					}
				}

				else if (KeyInputHandler.Keys[Val.P2Down.ordinal()]) {

					KeyInputHandler.Keys[Val.P2Down.ordinal()] = false;
					if (matrix.getBlockID(playerTwo.getX(),
							playerTwo.getY() + 1) == floor
							&& !(playerTwo.getX() == playerOne.getX() && playerOne
									.getY() == playerTwo.getY() + 1)
							&& !(isBombAt(playerTwo.getX(),
									playerTwo.getY() + 1)))

						playerTwo.MoveDown();

					else if (matrix.getBlockID(playerTwo.getX(),
							playerTwo.getY() + 1) == randomBonus) {
						playerTwo.GetBonus();
						matrix.updateMatrix(playerTwo.getX(),
								playerTwo.getY() + 1, 0);
						playerTwo.MoveDown();
					}
				}
			}// konczymy chodzenie

			if (KeyInputHandler.Keys[Val.P2Bomb.ordinal()]) {

				KeyInputHandler.Keys[Val.P2Bomb.ordinal()] = false;
				if (playerTwo.CurrBombs()) {

					if (!isBombAt(playerTwo.getX(), playerTwo.getY())) {
						AIfps = 130;
						if (firstBomb2) {
							bombAfter = 2;
							firstBomb2 = false;
						} else
							bombAfter = 3;
						bombs.add(new Bomb(playerTwo.getX(), playerTwo.getY(),
								playerTwo.getRange(), 1));
						playerTwo.DropBomb();

					}
				}
			}

			for (int i = 0; i < bombs.size(); i++) {
				if (bombs.get(i).isBlown()) {
					if (bombs.get(i).getPlayerID() == 0)
						playerOne.AddBomb();
					else
						playerTwo.AddBomb();

					explosions.add(new Explode(bombs.get(i).getX(), bombs
							.get(i).getY(), bombs.get(i).getRange()));
					bombs.remove(i);
					// tutaj sprawdzamy czy bomba cos zniszczyla i aktualizujemy
					// matrix
					int explosionX = explosions.get(explosions.size() - 1)
							.getX();
					int explosionY = explosions.get(explosions.size() - 1)
							.getY();

					if (explosionX == playerOne.getX()
							&& explosionY == playerOne.getY()) {
						playerOne.Die();
						Done = true;
					}

					if (explosionX == playerTwo.getX()
							&& explosionY == playerTwo.getY()) {
						playerTwo.Die();
						Done = true;
					}
					// start exp
					for (int j = explosionY - 1; j > explosionY
							- explosions.get(explosions.size() - 1).getRange()
							- 1; j--) // north
					{
						boolean loopEnd = false;

						int matrixID = matrix.getBlockID(explosionX, j);
						if (matrixID != 0) {
							switch (matrixID) {
							case box:
								loopEnd = true;
								int roulette = randomizer.nextInt(100);
								if (roulette <= bonusChance)
									matrix.updateMatrix(explosionX, j,
											randomBonus);
								else
									matrix.updateMatrix(explosionX, j, floor);

								break;
							case randomBonus:
								matrix.updateMatrix(explosionX, j, floor);
								break;
							case wall:
								loopEnd = true;
								break;
							}
						} else {
							if (explosionX == playerOne.getX()
									&& j == playerOne.getY()) {
								{
									playerOne.Die();
									Done = true;
								}
							} else if (explosionX == playerTwo.getX()
									&& j == playerTwo.getY()) {
								{
									playerTwo.Die();
									Done = true;
								}
							}
						}
						if (loopEnd) // koniec petli jak trafimy na sciane lub
										// boksa
							break;
					}

					for (int j = explosionY + 1; j < explosionY
							+ explosions.get(explosions.size() - 1).getRange()
							+ 1; j++) // south
					{

						System.out.println(j);
						boolean loopEnd = false;

						int matrixID = matrix.getBlockID(explosionX, j);
						if (matrixID != 0) {
							switch (matrixID) {
							case box:
								loopEnd = true;
								int roulette = randomizer.nextInt(100);
								if (roulette <= bonusChance)
									matrix.updateMatrix(explosionX, j,
											randomBonus);
								else
									matrix.updateMatrix(explosionX, j, floor);

								break;
							case randomBonus:
								matrix.updateMatrix(explosionX, j, floor);
								break;
							case wall:
								loopEnd = true;
								break;
							}
						} else {
							if (explosionX == playerOne.getX()
									&& j == playerOne.getY()) {
								{
									playerOne.Die();
									Done = true;
								}
							} else if (explosionX == playerTwo.getX()
									&& j == playerTwo.getY()) {
								{
									playerTwo.Die();
									Done = true;
								}
							}
						}
						if (loopEnd) // koniec petli jak trafimy na sciane lub
										// boksa
							break;
					}

					for (int j = explosionX + 1; j < explosionX
							+ explosions.get(explosions.size() - 1).getRange()
							+ 1; j++) // east
					{
						boolean loopEnd = false;
						System.out.println(j);
						int matrixID = matrix.getBlockID(j, explosionY);
						if (matrixID != 0) {
							switch (matrixID) {
							case box:
								loopEnd = true;
								int roulette = randomizer.nextInt(100);
								if (roulette <= bonusChance)
									matrix.updateMatrix(j, explosionY,
											randomBonus);
								else
									matrix.updateMatrix(j, explosionY, floor);

								break;
							case randomBonus:
								matrix.updateMatrix(j, explosionY, floor);
								break;
							case wall:
								loopEnd = true;
								break;
							}
						} else {
							if (j == playerOne.getX()
									&& explosionY == playerOne.getY()) {
								playerOne.Die();
								Done = true;
							} else if (j == playerTwo.getX()
									&& explosionY == playerTwo.getY()) {
								playerTwo.Die();
								Done = true;
							}
						}
						if (loopEnd) // koniec petli jak trafimy na sciane lub
										// boksa
							break;
					}

					for (int j = explosionX - 1; j > explosionX
							- explosions.get(explosions.size() - 1).getRange()
							- 1; j--) // west
					{
						boolean loopEnd = false;

						int matrixID = matrix.getBlockID(j, explosionY);
						if (matrixID != 0) {
							switch (matrixID) {
							case box:
								loopEnd = true;
								int roulette = randomizer.nextInt(100);
								if (roulette <= bonusChance)
									matrix.updateMatrix(j, explosionY,
											randomBonus);
								else
									matrix.updateMatrix(j, explosionY, floor);

								break;
							case randomBonus:
								matrix.updateMatrix(j, explosionY, floor);
								break;
							case wall:
								loopEnd = true;
								break;
							}
						} else {
							if (j == playerOne.getX()
									&& explosionY == playerOne.getY()) {
								playerOne.Die();
								Done = true;
							} else if (j == playerTwo.getX()
									&& explosionY == playerTwo.getY()) {
								playerTwo.Die();
								Done = true;
							}
						}
						if (loopEnd) // koniec petli jak trafimy na sciane lub
										// boksa
							break;
					}

				}
			}
			for (int i1 = 0; i1 < explosions.size(); i1++) {
				if (explosions.get(i1).EndOfExplode()) {
					explosions.remove(i1);
				}
			}

			end = System.nanoTime() - begin;
			long timeLeft = (1000000000L / 60L - end) / 1000000L;
			if (timeLeft < 10)
				timeLeft = 10;
			try {
				Thread.sleep(timeLeft);
			} catch (Exception e) {
			}

		} // end of game loop
	}
}