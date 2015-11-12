package bomber;

public class Matrix {
	static final int MatrixSize = 17;
	public static int[][] GameMatrix = new int[MatrixSize][MatrixSize];

	void ZeroMatrix() {
		for (int i = 0; i < MatrixSize; i++) {
			for (int j = 0; j < MatrixSize; j++) {
				GameMatrix[i][j] = 2;
			}
		}
	}

	void BuildEdges() {
		for (int i = 0; i < MatrixSize; i++) {
			for (int j = 0; j < MatrixSize; j++) {
				// Build edges
				if (i == 0) {
					GameMatrix[i][j] = 3;
				}
				if (j == 0) {
					GameMatrix[i][j] = 3;
				}
				if (i == (MatrixSize - 1)) {
					GameMatrix[i][j] = 3;
				}
				if (j == (MatrixSize - 1)) {
					GameMatrix[i][j] = 3;
				}
			}
		}

	}

	void PrintMatrix(int x, int y) {
		System.out.print(GameMatrix[x][y]);
	}

	void PrintMatrix() {
		for (int i = 0; i < MatrixSize; i++) {
			for (int j = 0; j < MatrixSize; j++) {
				PrintMatrix(i, j);
			}
			System.out.println();
		}
	}

	void BuildWalls() {
		for (int i = 0; i < MatrixSize; i++) {
			for (int j = 0; j < MatrixSize; j++) {
				if ((i % 2 == 0) && (j % 2 == 0)) {
					GameMatrix[i][j] = 3;
				}
				if ((i % 2 == 1) && (j % 2 == 1)) {
					GameMatrix[i][j] = 0;
				}

			}
		}
	}

	Matrix() {
		ZeroMatrix();
		BuildEdges();
		BuildWalls();
		GameMatrix[1][1] = 0;
		GameMatrix[1][2] = 0;
		GameMatrix[2][1] = 0;
		GameMatrix[15][14] = 0;
		GameMatrix[15][15] = 0;
		GameMatrix[14][15] = 0;
		GameMatrix[5][10] = 0;
		GameMatrix[7][10] = 0;
		GameMatrix[9][10] = 0;
	}

	void updateMatrix(int x, int y, int id) {
		GameMatrix[x][y] = id;
	}

	public int getBlockID(int x, int y) {
		return GameMatrix[x][y];
	}

}