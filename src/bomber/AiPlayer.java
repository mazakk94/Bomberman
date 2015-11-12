package bomber;

import java.util.Random;

public class AiPlayer extends Player {

	private int bombFPS;
	private boolean isNotSafe;
	private int bombRange; // to, ¿e sprawdzamy czy ucieklismy od bomby
	private int direction; // kierunek w którym sie poruszamy
	private int decision;
	private Matrix matrix = new Matrix();
	private Random randomizer = new Random();
	private int randomValue;

	// NASZ W£ASNY ENUM
	private final int Up = 1;
	private final int Right = 2;
	private final int Down = 3;
	private final int Left = 4;

	// KONIEC W£ASNEGO ENUMA

	public AiPlayer(int x, int y, int id) {
		super(x, y, id); // odpalamy konstruktor zwyk³ego playera
		isNotSafe = false;
		bombFPS = 0;
		bombRange = 0;
		direction = Right;
		decision = 0; // 0 - czekanie, 5 - stawianie bomby, 1 - góra, 2 - prawo,
						// 3 - dó³, 4 - lewo

		// AiStart(Keys); // rozpoczynamy AI

	}

	public boolean isMovingFlag() {
		if (FPS > 0)
			return true;
		else
			return false;
	}

	public void getMatrix(Matrix myMatrix) {
		matrix = myMatrix;
	}

	public int MakeDecision() {
		if (bombFPS > 0)
			bombFPS--;
		if (isMovingFlag() == true || (isNotSafe == false && bombFPS > 0)) { // jak
																				// czkeamy,
																				// lub
																				// ruszamy
																				// siê
																				// to
																				// czekaj...
			return 0;
		}
		if (isNotSafe == true) { // jak trzeba uciekaæ to uciekamy
			System.out.println("dir=" + direction);
			if (bombRange == range + 1) {
				bombRange--;
				return direction;// cofa sie o krok po po³o¿eniu bomby
			}
			if (bombRange > 0) {
				return RunAwayToBeSafeFromTheBomb();
			} else {
				isNotSafe = false;
				return 0;
			}
		}
		decision = LookingForAPlaceToDropTheBombOrRandomBonusToGet(1);// szukamy
																		// bonusu
		if (decision != 0) {
			direction = decision;
			return direction;
		}

		decision = LookingForAPlaceToDropTheBombOrRandomBonusToGet(2);
		if (decision != 0) {
			direction = (direction + 2) % 4;
			if (direction == 0)
				direction = 4; // uciekamy w przeciwnym kierunku do po³o¿enia
								// bomby
			isNotSafe = true;
			bombFPS = 130;
			bombRange = range + 1;
			return 5; // stawianie bomby
		}
		randomValue = randomizer.nextInt(100);
		if (randomValue > 20) {
			if (CheckIfICanKeepGoingSameDirectionAsIDid() == true)// chodzenie
				return direction;
			else {
				direction = ICantKeepGoingInSameDirectionSoIHaveToChangeItOrIWantToChangeMyDirectionInCaseOfRandomValue();// FUNKCJA
																															// I
																															// CAN'T
																															// KEEP
																															// GOING
																															// SAME
																															// DIRECTION
																															// SO
																															// I
																															// HAVE
																															// TO
																															// CHANGE
																															// IT
				return direction;
			}
		} else {
			decision = ICantKeepGoingInSameDirectionSoIHaveToChangeItOrIWantToChangeMyDirectionInCaseOfRandomValue();
			if (Math.abs(decision - direction) == 2)
				return direction;
			else {
				direction = decision;
				return direction;
			}
		}
	}

	private int ICantKeepGoingInSameDirectionSoIHaveToChangeItOrIWantToChangeMyDirectionInCaseOfRandomValue() {
		switch (direction) {
		case Up:
			if (matrix.getBlockID(pos_x + 1, pos_y) == 0) // prawo
				return Right;
			if (matrix.getBlockID(pos_x - 1, pos_y) == 0) // lewo
				return Left;
			return Up;
		case Right:
			if (matrix.getBlockID(pos_x, pos_y + 1) == 0) // dó³
				return Down;
			if (matrix.getBlockID(pos_x, pos_y - 1) == 0) // na górze jest box
				return Up;
			return Left;
		case Down:
			if (matrix.getBlockID(pos_x - 1, pos_y) == 0) // lewo
				return Left;
			if (matrix.getBlockID(pos_x + 1, pos_y) == 0) // prawo
				return Right;
			return Up;
		case Left:
			if (matrix.getBlockID(pos_x, pos_y - 1) == 0) // na górze jest box
				return Up;
			if (matrix.getBlockID(pos_x, pos_y + 1) == 0) // dó³
				return Down;
			return Right;
		default:
			return direction;
		}
	}

	private boolean CheckIfICanKeepGoingSameDirectionAsIDid() {
		switch (direction) {
		case Up:
			if (matrix.getBlockID(pos_x, pos_y - 1) == 0)
				return true;

			else
				return false;

		case Right:
			if (matrix.getBlockID(pos_x + 1, pos_y) == 0)
				return true;
			else
				return false;

		case Down:
			if (matrix.getBlockID(pos_x, pos_y + 1) == 0)
				return true;
			else
				return false;

		case Left:
			if (matrix.getBlockID(pos_x - 1, pos_y) == 0)
				return true;
			else
				return false;

		default:
			return false;
		}
	}

	private int LookingForAPlaceToDropTheBombOrRandomBonusToGet(
			int whatImLookingFor) { // box = 2, bonus = 1
		switch (direction) { // jak jest 5 to dajemy komunikat ¿eby k³ad³ bombê
		case Up:
			if (matrix.getBlockID(pos_x - 1, pos_y) == whatImLookingFor) // lewo
				return Left;
			if (matrix.getBlockID(pos_x + 1, pos_y) == whatImLookingFor) // prawo
				return Right;
			if (matrix.getBlockID(pos_x, pos_y - 1) == whatImLookingFor) // na
																			// górze
																			// jest
																			// box
				return Up;
			return 0;
		case Right:
			if (matrix.getBlockID(pos_x, pos_y + 1) == whatImLookingFor) // dó³
				return Down;
			if (matrix.getBlockID(pos_x + 1, pos_y) == whatImLookingFor) // prawo
				return Right;
			if (matrix.getBlockID(pos_x, pos_y - 1) == whatImLookingFor) // na
																			// górze
																			// jest
																			// box
				return Up;
			return 0;
		case Down:
			if (matrix.getBlockID(pos_x, pos_y + 1) == whatImLookingFor) // dó³
				return Down;
			if (matrix.getBlockID(pos_x + 1, pos_y) == whatImLookingFor) // prawo
				return Right;
			if (matrix.getBlockID(pos_x - 1, pos_y) == whatImLookingFor) // lewo
				return Left;
			return 0;
		case Left:
			if (matrix.getBlockID(pos_x, pos_y - 1) == whatImLookingFor) // na
																			// górze
																			// jest
																			// box
				return Up;
			if (matrix.getBlockID(pos_x, pos_y + 1) == whatImLookingFor) // dó³
				return Down;
			if (matrix.getBlockID(pos_x - 1, pos_y) == whatImLookingFor) // lewo
				return Left;
			return 0;
		default:
			return 0;
		}
	}

	private int RunAwayToBeSafeFromTheBomb() {
		switch (direction) {
		case Up: // je¿eli uciekamy w góre
			if (matrix.getBlockID(pos_x - 1, pos_y) == 0
					|| matrix.getBlockID(pos_x - 1, pos_y) == 1) { // floor lub
																	// randombonus
				isNotSafe = false;
				bombRange = 0;
				direction = 4; // idziemy w lewo
			} else if (matrix.getBlockID(pos_x + 1, pos_y) == 0
					|| matrix.getBlockID(pos_x + 1, pos_y) == 1) {
				isNotSafe = false;
				bombRange = 0;
				direction = 2; // idziemy w prawo
			} else
				bombRange--;
			return direction; // jak nie spelnilismy warunkow to idziemy w góre
		case Right: // uciekamy w prawo
			if (matrix.getBlockID(pos_x, pos_y - 1) == 0
					|| matrix.getBlockID(pos_x, pos_y - 1) == 1) { // floor lub
																	// randombonus
				isNotSafe = false;
				bombRange = 0;
				direction = 1; // idziemy w górê
			} else if (matrix.getBlockID(pos_x, pos_y + 1) == 0
					|| matrix.getBlockID(pos_x, pos_y + 1) == 1) {
				isNotSafe = false;
				bombRange = 0;
				direction = 3; // idziemy w dó³
			} else
				bombRange--;
			return direction; // jak nie pyk³o to w prawo
		case Down: // je¿eli uciekamy w dó³
			if (matrix.getBlockID(pos_x + 1, pos_y) == 0
					|| matrix.getBlockID(pos_x + 1, pos_y) == 1) {
				isNotSafe = false;
				bombRange = 0;
				direction = 2; // idziemy w prawo
			} else if (matrix.getBlockID(pos_x - 1, pos_y) == 0
					|| matrix.getBlockID(pos_x - 1, pos_y) == 1) { // floor lub
																	// randombonus
				isNotSafe = false;
				bombRange = 0;
				direction = 4; // idziemy w lewo
			} else
				bombRange--;
			return direction; // jak nie spelnilismy warunkow to idziemy w dó³
		case Left:
			if (matrix.getBlockID(pos_x, pos_y + 1) == 0
					|| matrix.getBlockID(pos_x, pos_y + 1) == 1) {
				isNotSafe = false;
				bombRange = 0;
				direction = 3; // idziemy w dó³
			} else if (matrix.getBlockID(pos_x, pos_y - 1) == 0
					|| matrix.getBlockID(pos_x, pos_y - 1) == 1) { // floor lub
																	// randombonus
				isNotSafe = false;
				bombRange = 0;
				direction = 1; // idziemy w górê
			} else
				bombRange--;
			return direction; // jak nie pyk³o to w lewo
		default:
			return 0;
		}
	}

}