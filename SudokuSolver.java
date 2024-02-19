public class SudokuSolver {
    private static final int gridsize = 9; //Grid size will always be
    public static void main(String[] args) {

        int[][] board = { //2D array, 0's are empty
                {7, 0, 2, 0, 5, 0, 6, 0, 0},
                {0, 0, 0, 0, 0, 3, 0, 0, 0},
                {1, 0, 0, 0, 0, 9, 5, 0, 0},
                {8, 0, 0, 0, 0, 0, 0, 9, 0},
                {0, 4, 3, 0, 0, 0, 7, 5, 0},
                {0, 9, 0, 0, 0, 0, 0, 0, 8},
                {0, 0, 9, 7, 0, 0, 0, 0, 5},
                {0, 0, 0, 2, 0, 0, 0, 0, 0},
                {0, 0, 7, 0, 4, 0, 2, 0, 3}
        };
        if (solveBoard(board)){
            System.out.println("Solved it!");
        }
        else {
            System.out.println("Unsolvable board!");
        }
        printBoard(board);

    }
    private static void printBoard(int[][] board){
        for (int row = 0; row<gridsize; row++){
            if (row % 3 ==0 && row!=0){
                System.out.println("-----------");
            }
            for (int column = 0; column< gridsize; column++){
                if (column % 3 ==0 && column!=0){
                    System.out.print("|");
                }
                System.out.print(board[row][column]);
            }
            System.out.println();
        }
    }
    private static boolean IsNumberInRow(int[][] board, int number, int row){
        for(int i = 0; i< gridsize; i++){ //Going through the board with loop
            if (board[row][i] == number){ //Checks if the row number (that gets passed in) and the column thats being iterated through [0-9] has a number. EX: row: 0, column i [0-9]
                return true;
            }
        } return false;
    }

    private static boolean IsNumberInColumn(int[][] board, int number, int column){
        for(int i = 0; i< gridsize; i++){ //Going through the board with loop
            if (board[i][column] == number){ //Checks if the column number (that gets passed in) and the row that's being iterated through [0-9] has a number. EX: row: i [0-9], column 0
                return true;
            }
        } return false;
    }

    private static boolean IsNumberInBox(int[][] board, int number, int row, int column){
        int localBoxRow = row - row%3; //Tricky way to find top left corner for row in local boxes
        int localBoxColumn = column - column%3; //Tricky way find top left corner for column in local boxes

        for(int i = localBoxRow; i<localBoxRow + 3; i++){ //Nested loop to cover individual 3 x 3 boxes
            for (int j = localBoxColumn; j<localBoxColumn +3; j++){
                if (board[i][j] == number){
                    return true;
                }
            }
        }
        return false;
    }
    private static boolean IsValidPlacement(int[][] board, int number, int row, int column){
        //Combining all 3 methods to check if a number can be placed in any row, any column, any box
        return !IsNumberInRow(board, number, row) &&
                !IsNumberInColumn(board, number, column) &&
                !IsNumberInBox(board, number, row, column);
        //If they all return false, its valid placement
    }
    private static boolean solveBoard(int[][] board){ //Creating the algorithm to solve the board
        for (int row = 0; row< gridsize; row++){
            for (int column = 0; column < gridsize; column++){
                if(board[row][column] == 0){

                    for (int numberToTry = 1; numberToTry <= gridsize; numberToTry++){ //Looping to find if number that's being tried can be placed
                        if (IsValidPlacement(board, numberToTry, row, column)){
                            board[row][column] = numberToTry; //Places the number if valid, if not valid it tried again with different number

                            if (solveBoard(board)){//Calls the method to recursivley repeat the whole process
                                return true; //Returns true when the rest of the numbers on the board have valid placements because of the first initially placed number
                            } else {
                                board[row][column] = 0; //Sets first number back to 0 so it can try to solve it again but with a different number.
                            }
                        }
                    }
                    return false; //Board just cant be solved
                }
            }
        }
        return true; //Was able to solve the board completly
    }
}