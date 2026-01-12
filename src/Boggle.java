import java.util.ArrayList;
import java.util.Arrays;

public class Boggle {
    private static ArrayList<String> goodWords = new ArrayList<String>();
    private static boolean visited[][];
    private static TST tst;

    public static void dfs(int row, int col, String word, char[][] board){
        // Check if in bounds
        if (row < 0 || col < 0 || row >= visited.length || col >= visited[0].length) return;

        // Check if visited
        if (visited[row][col]){
            return;
        }

        // Update word
        word += board[row][col];

        // Only run lookup once; otherwise on a search hit it would return false the second time because I edit the TST during lookup()
        int lookup = tst.lookup(word);

        // Two possibilities for search miss: either no node exists, or there is a node but it's not the end of a word. We want only the no node exists case.
        if (lookup == TST.NONODE) {
            return;
        }

        // Search hit, add word to goodWords. Don't have to worry about duplicates because TST gets edited by lookup()
        if (lookup > 0) {
            goodWords.add(word);
        }

        // Mark as visited
        visited[row][col] = true;

        // DFS to other spots
        dfs(row + 1, col, word, board);
        dfs(row-1, col, word, board);
        dfs(row, col - 1, word, board);
        dfs(row, col + 1, word, board);

        // Unmark as visited
        visited[row][col] = false;
    }



    public static String[] findWords(char[][] board, String[] dictionary) {

        // Global variables need reset because otherwise subsequent tests run with pre-used variables
        visited = new boolean[board.length][board[0].length];
        tst = new TST();
        goodWords.clear();

        // Initialize tst with dictionary
        for (String word : dictionary) {
            tst.insert(word, 1);
        }

        // DFS on every square in the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(i, j, "", board);
            }
        }

        // Convert the list into a sorted array of strings, then return the array.
        String[] sol = new String[goodWords.size()];
        goodWords.toArray(sol);
        Arrays.sort(sol);
        return sol;
    }
}
