import java.util.*;
class backtrackingMRV {

    static int ROWS = 4;
    static int COLUMNS = 10;
    static int consistency;
    static int assignments;
    static double totalCons;
    static double totalAssign;
    static double divide;

    static boolean backtrackMRV(int grid[][], int row, int col) {
        if (row == ROWS - 1 && col == COLUMNS)
            return true;

        if (col == COLUMNS) {
            row++;
            col = 0;
        }

        if (grid[row][col] != -1)
            return backtrackMRV(grid, row, col + 1);

        // Find the cell with the smallest number of legal values
        int minRemainingValues = 10000;
        int minRow = -1, minCol = -1;
        for (int i = 0; i < ROWS - 1; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (grid[i][j] == -1) {
                    int remainingValues = getRemainingValues(grid, i, j);
                    if (remainingValues < minRemainingValues) {
                        minRemainingValues = remainingValues;
                        minRow = i;
                        minCol = j;
                    }
                }
            }
        }


        // Try each legal value for the selected cell
        for (int num = 0; num < 10; num++) {
            if (isSafe(grid, minRow, minCol, num)) {
                grid[minRow][minCol] = num;
                assignments++;
                if (backtrackMRV(grid, 0, 0))
                    return true;
            }
            grid[minRow][minCol] = -1;
            assignments++;
        }
        return false;
    }

    // Helper function to find the number of legal values for a cell
    static int getRemainingValues(int[][] grid, int row, int col) {
        int remainingValues = 0;
        for (int num = 0; num < 10; num++) {
            if (isSafe(grid, row, col, num))
                remainingValues++;
        }
        return remainingValues;
    }


    /* A function to print grid */
    static void print(int[][] grid) {
        System.out.println("\n ---------Final State---------");
        for (int i = 0; i < ROWS; i++) {
            System.out.print("|");
            for (int j = 0; j < COLUMNS; j++)
                System.out.printf("%2d ", grid[i][j]);
            System.out.println("|");
        }
        System.out.println(" ------------------------------");
    }

    static void printIntialState(int[][] grid) {
        System.out.println("\n --------Initial State---------");
        for (int i = 0; i < ROWS; i++) {
            System.out.print("|");
            for (int j = 0; j < COLUMNS; j++)
                if (grid[i][j] != -1)
                    System.out.printf("%2d ", grid[i][j]);
                else
                    System.out.printf("%2s ", " ");
            System.out.println("|");
        }
        System.out.println(" ------------------------------");
    }

    // Check whether it will be legal
    // to assign num to the
    // given row, col
    static boolean isSafe(int[][] grid, int row, int col, int num) {

        // Check if we find the same num
        // in the similar row , we
        // return false
        for (int x = 0; x < COLUMNS; x++) {
            consistency++;
            if (grid[row][x] == num) {
                return false;
            }
        }

        try {
            consistency++;
            if (grid[row - 1][col] == num) {
                return false;
            }
        } catch (Exception e) {consistency--;}
        try {
            consistency++;
            if (grid[row + 1][col] == num) {
                return false;
            }
        } catch (Exception e) {consistency--;}
        try {
            consistency++;
            if (grid[row][col - 1] == num) {
                return false;
            }
        } catch (Exception e) {consistency--;}
        try {
            consistency++;
            if (grid[row][col + 1] == num) {
                return false;
            }
        } catch (Exception e) {consistency--;}
        try {
            consistency++;
            if (grid[row + 1][col + 1] == num) {
                return false;
            }
        } catch (Exception e) {consistency--;}

        try {
            consistency++;
            if (grid[row - 1][col - 1] == num) {
                return false;
            }
        } catch (Exception e) {consistency--;}
        try {
            consistency++;
            if (grid[row + 1][col - 1] == num) {
                return false;
            }
        } catch (Exception e) {consistency--;}
        try {
            consistency++;
            if (grid[row - 1][col + 1] == num) {
                return false;
            }
        } catch (Exception e) {consistency--;}

        int sum = 0;
        for (int i = 0; i < ROWS - 1; i++) {
            if (grid[i][col] != -1)
                sum += grid[i][col];
        }
        sum += num;
        consistency++;
        if (sum > grid[ROWS - 1][col])
            return false;

        return true;


    }

    static void resetCounters(){
        divide++;
        totalCons += consistency;
        totalAssign += assignments;
        consistency = 0;
        assignments = 0;
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int grid[][] = null;
        divide = 0;

        boolean flag = true;
        while (flag) {

            System.out.print("Which grid would you like to solve 1, 2, 3, 4 or 5? Type 0 if you would like to exit: ");
            int x = cin.nextInt();

            switch (x) {
                case 1:
                    int f[][] = {
                            {-1, 6, 2, 0, -1, -1, -1, 8, 5, 7},
                            {-1, 0, 1, 7, 8, -1, -1, -1, 9, -1},
                            {-1, 4, -1, -1, 2, -1, 3, 7, -1, 8},
                            {13, 10, 8, 7, 19, 16, 11, 19, 15, 17}};

                    grid = f;
                    printIntialState(grid);
                    if (backtrackMRV(grid, 0, 0))
                        print(grid);
                    else
                        System.out.println("No Solution exists");
                    break;

                case 2:
                    int s[][] = {
                            {-1, -1, 5, 3, -1, -1, 6, -1, -1, -1},
                            {0, 7, -1, 4, 6, 5, -1, -1, 1, 3},
                            {-1, 2, 3, 7, -1, 4, -1, 6, 5, -1},
                            {10, 13, 17, 14, 8, 16, 14, 17, 14, 12}};

                    grid = s;
                    printIntialState(grid);
                    if (backtrackMRV(grid, 0, 0))
                        print(grid);
                    else
                        System.out.println("No Solution exists");
                    break;

                case 3:
                    int t[][] = {
                            {4, -1, -1, -1, 2, 0, -1, 1, -1, 9},
                            {7, -1, -1, 5, -1, -1, -1, 2, -1, 6},
                            {4, -1, -1, 9, 7, -1, -1, -1, -1, 3},
                            {15, 11, 10, 20, 17, 4, 23, 3, 14, 18}};

                    grid = t;
                    printIntialState(grid);
                    if (backtrackMRV(grid, 0, 0))
                        print(grid);
                    else
                        System.out.println("No Solution exists");
                    break;

                case 4:
                    int fo[][] = {
                            {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                            {-1, 4, -1, -1, -1, 9, -1, -1, 2, -1},
                            {-1, 1, 9, 5, 8, 4, -1, 7, 6, 3},
                            {13, 14, 20, 13, 13, 18, 10, 14, 12, 8}};

                    grid = fo;
                    printIntialState(grid);
                    if (backtrackMRV(grid, 0, 0))
                        print(grid);
                    else
                        System.out.println("No Solution exists");
                    break;

                case 5:
                    int fi[][] = {
                            {5, 7, -1, 0, -1, 1, -1, -1, -1, -1},
                            {1, -1, -1, 6, 7, -1, -1, 5, 4, -1},
                            {-1, -1, -1, -1, -1, -1, 1, 7, 2, -1},
                            {14, 12, 17, 15, 19, 10, 14, 18, 9, 7}};

                    grid = fi;
                    printIntialState(grid);
                    if (backtrackMRV(grid, 0, 0))
                        print(grid);
                    else
                        System.out.println("No Solution exists");
                    break;
                default:
                    flag = false;

            }
            if ( flag ) {
                System.out.println("consistency: " + consistency);
                System.out.println("assignments: " + assignments);
                resetCounters();
            }
        }
        System.out.println("average consistency: " + (totalCons / divide));
        System.out.println("average assignments: " + (totalAssign / divide));
    }
}
