import java.util.*;
/** This class creates a Connect Four game with two players,
 *  first prompting users to enter names and then the desired number of 
 *  spaces "tokens" in a row to win. Based on these input the "playing grid" is 
 *  created. From there players will take turns placing peices until a winner 
 *  is decided based on the correct number in a row, or will end if no more spaces.
 *  @author John Eakes
 */
public class ConnectFour {

    /** The Scanner that is passed into multiple methods */ 
    static Scanner scnr = new Scanner(System.in);


    /**
     * Main method to run the connect four game. It initializes the game setup,
     * prompts the user for input, and manages the turn system until there is a winner 
     * or the game ends in a draw.
     * @param args command line arguements (not used)
     * @throws IllegalArgumentException if the user value entered is less than or equal to 2;
     */
    public static void main(String[] args) {
        // Player setup and input
        String playerOne;
        String playerTwo;
        char playerOnePiece = 'X';
        char playerTwoPiece = 'O';
        int numToWin;
        int rows;
        int cols;
        char[][] grid;

        System.out.print("Enter name for player one(X): ");
        playerOne = scnr.next();
        
        System.out.print("Enter name for player two(O): ");
        playerTwo = scnr.next();
        
        System.out.print("Enter a number 3-6 indicating how many pieces in a row it takes to win: ");
        numToWin = scnr.nextInt();
        if(numToWin <= 2){
            throw new IllegalArgumentException("Please enter a value bigger than 2");
        }
        rows = numToWin * 2;
        cols = numToWin * 2;
        System.out.println();
        System.out.println();
        grid = new char[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid[i][j] = '_';
            }
        }

        System.out.println("Welcome to Connect four! Each user will go back and forth entering the number of the column they want to place their piece in.");
        System.out.println("Once one player gets " + numToWin + " pieces in a row, they win!");
        System.out.println("This is your empty, starting board:\n");
        printArray(grid);

        // Gameplay loop
        boolean gameWon = false;
        for(int turn = 0; turn < rows * cols; turn++){
            if(turn % 2 == 0){
                getUserInput(grid, playerOne, playerOnePiece);
                gameWon = checkWin(grid, playerOnePiece, numToWin);
                printArray(grid);
                if (gameWon){
                    System.out.println(playerOne + " wins!!!");
                    break;
                }

            }
            else{
                getUserInput(grid, playerTwo, playerTwoPiece);
                gameWon = checkWin(grid, playerTwoPiece, numToWin);
                printArray(grid);
                if(gameWon){
                    System.out.println(playerTwo + " wins!!!");
                    break;
                }

            }
        }

        if(!gameWon){
            System.out.println("Game Over. It's a draw.");
        }
        



        
            
    }

    /**
     * The printArray method prints out the 
     * @param arr the 2d array representing the game grid. 
     */
    public static void printArray(char[][] arr) {
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[0].length; j++){
                System.out.print(arr[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
        for(int i = 0; i < arr[0].length; i++){
            System.out.print(i + "  ");
        }
        System.out.println();
        System.out.println();
    }
    
    /**
     * Prompts the current player to select the column to place thier piece.
     * Handles user input validation and makes sure the peice is placed in the lowest
     * available row of the chosen column.
     * 
     * @param grid the 2d array representing the game grid. 
     * @param player player the name of the current player.
     * @param playerPiece playerPiece the piece ('X' or 'O') of the current player.
     */
    public static void getUserInput(char[][] grid, String player, char playerPiece){
        int input = -1;
        boolean valid = false;
        boolean placed = false;
        while(!valid){
            System.out.print(player + ", which column do you want to place your piece: ");
            input = scnr.nextInt();
            System.out.println();
            
            if (input < 0 || input >= grid[0].length) {
                System.out.println("Invalid column! Try again.");  
                placed = true;          
            } else {
                for(int i = grid.length - 1; i >= 0; i--){
                    if(grid[i][input] == '_'){
                        grid[i][input] = playerPiece;
                        placed = true;
                        valid = true;
                        break;
                    }
                }
            }
            if(!placed) {
                System.out.println("Column is full! Try again.");
            }
        }
    }

    /**
     * This method checks the grid after the player input
     * to see if the player has won based on the criteria of horizontal, diagonal, or
     * vertical consecutive.
     * @param grid the 2d array representing the game grid.
     * @param playerPiece playerPiece the piece ('X' or 'O') of the current player.
     * @param numToWin the required number of consecutive pieces to win.
     * @return true if the player has won, otherwise false.
     */
    public static boolean checkWin(char[][] grid, char playerPiece, int numToWin){
        //Check horizontal
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] == playerPiece){
                    //Horizontal
                    if (j + numToWin - 1 < grid[0].length) {
                        boolean horizontalWin = true;
                        for(int a = 0; a < numToWin; a++){
                            if(grid[i][j + a] != playerPiece){
                                horizontalWin = false;
                                break;
                            }
                        }
                        if (horizontalWin) return true;
                    }

                    //Vertical
                    if(i + numToWin - 1 < grid.length){
                        boolean verticalWin = true;
                        for(int a = 0; a < numToWin; a++){
                            if(grid[i + a][j] != playerPiece){
                                verticalWin = false;
                                break;
                            }
                        }
                        if (verticalWin) return true;
                    }

                    //Diagonal right
                    if(i + numToWin - 1 < grid.length && j + numToWin - 1 < grid[0].length){
                        boolean rightDiagonalWin = true;
                        for(int a = 0; a < numToWin; a++){
                            if(grid[i + a][j + a] != playerPiece){
                                rightDiagonalWin = false;
                                break;
                            }
                        }
                        if (rightDiagonalWin) return true;
                    }

                    //Diagonal left
                    if(i - numToWin + 1 >= 0 && j + numToWin - 1 < grid[0].length){
                        boolean leftDiagonalWin = true;
                        for(int a = 0; a < numToWin; a++){
                            if(grid[i - a][j + a] != playerPiece){
                                leftDiagonalWin = false;
                                break;
                            }
                        }
                        if (leftDiagonalWin) return true;
                    }
                }
            }
        }
        return false;
    }
}