package Main;
import java.util.ArrayList;
public class Main {

    public static void printGrid(int[][] grid)
    {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++)
                System.out.print(" " + grid[i][j] + " ");
            System.out.println("");
        }
    }

    public static void printEstimations(int[][][] grid)
    {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 5; k++) {
                    if (grid[i][j][k] != 0)
                        System.out.println(
                                " estimations[" + i + "]" + "[" + j + "]" + "[" + k + "] => " + grid[i][j][k] + " ");
                }
            }
        }
    }
    
    public static int[][][] getEstimations()
    {
        int[][][] estimations = new int[6][6][5];
        
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                for (int k = 1; k < 5; k++)
                    estimations[i][j][k] = 1;
            }
        }
        return estimations;
    }

    public static int[][] getGrid(String str)
    {
        int[][] grid    = new int[6][6];
        String[] strArr = str.split(" ");
        int k           = 0;
        for (int j = 0; j < 6; j += 5) {
            for (int i = 1; i < 5; i++) {
                grid[j][i] = Integer.parseInt(strArr[k++]);
            }
        }

        for (int j = 0; j < 6; j += 5) {
            for (int i = 1; i < 5; i++) {
                grid[i][j] = Integer.parseInt(strArr[k++]);
            }
        }
        return grid;
    }

    public static void TakeEasyShots(int[][] grid, int[][][] estimations)
    {
        for (int x = 0; x < 16; x++) {
            int row   = x / 4 + 1;
            int col   = x % 4 + 1;
            int up    = grid[0][col];
            int down  = grid[5][col];
            int left  = grid[row][0];
            int right = grid[row][5];

            for (int i = 1; i < 5; i++) {
                if (up == 4 && down == 1 && row == 1)
                    fillCell(grid, estimations, i, col, i);
                if (up == 1 && down == 4 && row == 4)
                    fillCell(grid, estimations, i, col, 5 - i);
                if (left == 4 && right == 1 && col == 1) 
                    fillCell(grid, estimations, row, i, i);
                if (left == 1 && right == 4 && col == 4) 
                    fillCell(grid, estimations, row, 5 - i, i);
            }

            if (up == 1 && row == 1 && (down == 2 || down == 3)) {
                fillCell(grid, estimations, 1, col, 4);
                fillCell(grid, estimations, 4, col, 5-down);
            }

            if (right == 1 && ((left == 2 && col == 1) || (left == 3 && col == 4))) {
                fillCell(grid, estimations, row, 4, 4);
                fillCell(grid, estimations, row, 1, 5 - left);
            }

            if (left == 1 && ((right == 2 && col == 4) || (right == 3 && col == 1))) {
                fillCell(grid, estimations, row, 1, 4);
                fillCell(grid, estimations, row, 4, 5 - right);
            }

            if (down == 1 && ((up == 2 &&  row == 4) || (up == 3 && row == 1) )) {
                fillCell(grid, estimations, 4, col, up + row -2);
                fillCell(grid, estimations, 1, col, up + 1 );
            }

            if (up    == 3 && (row == 2 || row == 1))
                estimations[row][col][row + 2] = 0;

            if (down  == 3 && (row == 3 || row == 4))
                estimations[row][col][7 - row] = 0;

            if (left  == 3 && (col == 2 || col == 1 ))
                estimations[row][col][col + 2] = 0;

            if (right == 3 && (col == 3 || col == 4))
                estimations[row][col][7 - col] = 0;

            if (left == 3) 
            {
                estimations[row][1][3] = 0;
                estimations[row][1][4] = 0;
                estimations[row][2][4] = 0;
            }
            if (right == 3) 
            {
                estimations[row][4][3] = 0;
                estimations[row][4][4] = 0;
                estimations[row][3][4] = 0;
            }
            if (up == 3) 
            {
                estimations[1][col][3] = 0;
                estimations[1][col][4] = 0;
                estimations[2][col][4] = 0;
            }
            if (down == 3) 
            {
                estimations[4][col][3] = 0;
                estimations[4][col][4] = 0;
                estimations[3][col][4] = 0;
            }

            if (left == 2) {
                estimations[row][1][4] = 0;
                estimations[row][2][3] = 0;
            }
            if (right == 2) {
                estimations[row][4][4] = 0;
                estimations[row][3][3] = 0;
            }
            if (up == 2)
            {
                estimations[1][col][4] = 0;
                estimations[2][col][3] = 0;
            }
            if (down == 2)
            {
                estimations[4][col][4] = 0;
                estimations[3][col][3] = 0;
            }
        }
    }

    public static void  fillCell(int[][] grid, int[][][] estimations, int row, int col, int res)
    {
        grid[row][col] = res;
        for (int i = 1; i < 5; i++) {
            if (i != res && estimations[row][col][i] != 2)
                estimations[row][col][i] = 0;
            else
                estimations[row][col][i] = 2;
            if (i != col && estimations[row][i][res] != 2)
                estimations[row][i][res] = 0;
            else
                estimations[row][i][res] = 2;
            if (i != row && estimations[i][col][res] != 2)
                estimations[i][col][res] = 0;
            else
                estimations[i][col][res] = 2;
        }

    }

    public static boolean CheckVector(int[] rowVector)
    {
        System.out.print("   RowVector->");
        for (int i = 0; i < rowVector.length; i++)
            System.out.print(rowVector[i]);
        int threshold = rowVector[5];
        int howManySeen = 1;
        int rightest = rowVector[4];
        for (int c = 3; c > 0; c--) {
            if (rowVector[c] > rightest) {
                howManySeen++;
                rightest = rowVector[c];
            }
        }
        System.out.println("   threshold :" + threshold + ",but " + howManySeen + " seen.");
        if (threshold == howManySeen) {
            return true;
        }
        return false;
    }

    public static ArrayList<Integer> getEstimations(int[][][] estimations, int row, int col) {
        ArrayList<Integer> values = new ArrayList<>();

        for (int j = 1; j < 5; j++) {
            if (estimations[row][col][j] > 0)
                values.add(j);
        }
        return values;
    }

    public static void FillBlanks(int[][] grid, int n, int[][][] estimations)
    {
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < 16; x++) {
                int row = x / 4 + 1;
                int col = x % 4 + 1;
                if (grid[row][col] == 0) {
                    ArrayList<Integer> _estimations = getEstimations(estimations, row, col);
                    if (_estimations.size() == 1)
                        fillCell(grid, estimations, row, col, _estimations.get(0));
                }
            }
        }
        
    }
        
        
    public static void main(String[] args) {
        ArrayList<String> skyscrappers = new ArrayList<>();
        /*       4 3 2 1 
         *     4 o o o o 1 
         *     3 o o o o 2
         *     2 o o o o 2
         *     1 o o o o 2
         *       1 2 2 2 
         */
        skyscrappers.add("4 3 2 1 1 2 2 2 4 3 2 1 1 2 2 2");
        skyscrappers.add("2 2 3 1 1 3 2 3 2 2 3 1 1 2 2 3");
        skyscrappers.add("1 2 2 2 4 3 1 2 1 2 3 3 3 3 1 2");
        skyscrappers.add("4 2 1 2 1 2 3 3 3 3 2 1 2 1 2 4");
        skyscrappers.add("2 4 2 1 2 1 2 4 3 3 1 2 1 2 3 3");
        skyscrappers.add("4 1 2 2 1 4 2 2 2 3 2 1 2 1 2 3");

        for (int i = 0; i < skyscrappers.size(); i++) {

            int[][] grid = getGrid(skyscrappers.get(i));
            int[][][] estimations = getEstimations();

            TakeEasyShots(grid, estimations);
            FillBlanks(grid, 2, estimations);
            TakeEasyShots(grid, estimations);
            FillBlanks(grid, 2, estimations);
            printGrid(grid);
            System.out.println();
            //printEstimations(estimations);
        }
    }
}
