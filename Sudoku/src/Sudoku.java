import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sudoku {
    public static char[][] input() {
        char[][] board = new char[9][9];
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            for (int i = 0; i < 9; i++)
                board[i] = bf.readLine().toCharArray();
            bf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(" Input sudoku puzzle:");
        print(board);
        return board;
    }

    public static void print(char[][] arr) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(arr[i][j]+" ");
                if (j == 2 || j == 5)
                    System.out.print("| ");
            }
            System.out.println();
            if (i == 2 || i == 5)
                System.out.println("—————————————————————");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        char[][] board = input();
        if (solve(board)) {
            System.out.println(" Solved sudoku puzzle:");
            print(board);
        }
    }

    public static boolean solve(char[][] board) {
        int[] currPos = findEmpty(board);

        if (currPos == null)
            return true;

        for (int i = 1; i <= board.length; i++) {
            char n = (char) (i+48);
            if (validate(n, currPos, board)) {
                board[currPos[0]][currPos[1]] = n;

                if (solve(board))
                        return true;

                board[currPos[0]][currPos[1]] = '.';
            }
        }

        return false;
    }

    public static boolean validate(char num, int[] currPos, char[][] board) {
        int     row = currPos[0],
                col = currPos[1],
                boxSize = (int) Math.sqrt(board.length);

        for (int i = 0; i < board.length; i++)
            if (board[i][col] == num
                    && i != row)
                return false;

        for (int i = 0; i < board.length; i++)
            if (board[row][i] == num
                    && i != col)
                return false;

        int boxRow = (int) (Math.floor(row / boxSize) * boxSize);
        int boxCol = (int) (Math.floor(col / boxSize) * boxSize);

        for (int i = boxRow; i < boxRow + boxSize; i++)
            for (int j = boxCol; j < boxCol + boxSize; j++)
                if (board[i][j] == num && i != row && j != col)
                    return false;

        return true;
    }

    public static int[] findEmpty(char[][] board) {
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board.length; c++)
                if (board[r][c] == '.')
                    return new int[]{r, c};

        return null;
    }
}
