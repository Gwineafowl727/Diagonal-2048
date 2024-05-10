import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A class that represents a modified version of the game 2048
 * @author Tennyson Engquist
 */
public class SlideGame extends Application {

    // an instance of the Random class to create random integers
    Random r = new Random();

    // number of rows to play with
    private int mainRows = 4;

    // number of columns to play with
    private int mainColumns = 4;

    // 2D array that will contain values that the buttons will always be displaying
    int[][] mainGameBoard;

    /**
     * Returns the private field mainRows
     * @return the number of rows of the game board
     */
    public int getMainRows(){
        return this.mainRows;
    }

    /**
     * Replaces the original number of rows with a new amount
     * @param a the new number of rows the user wants to pass
     */
    public void setMainRows(int a){
        this.mainRows = a;
    }

    /**
     * Returns the private field mainColumns
     * @return the number of columns of the game board
     */
    public int getMainColumns(){
        return this.mainColumns;
    }

    /**
     * Replaces the original number of columns with a new amount
     * @param a the new number of columns the user wants to pass
     */
    public void setMainColumns(int a){
        this.mainColumns = a;
    }

    // array of buttons
    Button[][] buttonArray;

    /**
     * Produces an int that tells how many blank spaces there are
     * @param totalSpaces the number of elements to be checked
     * @param gameBoard 2D array representing the game board
     * @return the amount of spaces that are blank
     */
    public int getTotalViable(int totalSpaces, int[][] gameBoard){

        // tracks the total number of blank spaces
        int count = 0;

        // go through every index of the gameBoard
        for (int i = 0; i < gameBoard.length; i++){

            // check each element in the row
            for (int j = 0; j < gameBoard[i].length; j++){ 

                if (gameBoard[i][j] == 0)
                    count++;
            }
        }

        return count;
    }

    /**
     * Gets the index pair to identify which space in gameBoard we need to spawn the 1 in
     * @param selector the randomized number that represents which space we spawn the 1 in
     * @param gameBoard 2D array representing the game board
     * @return an array with 2 elements: a column index and a row index
     */
    public int[] getSpawnLocation(int selector, int[][] gameBoard){

        // as we iterate through viableSpaces, this tracks how many viable spots we pass
        int truesPassed = 0;

        // this loop iterates through gameBoard. Whatever num is, that's the amount of viable spaces
        // we want to iterate through before settling on a space
        // for each loop, check if that space is viable, and increment truesPassed if true
        // if we have passed the designated number of viable spaces, spawn a 1 on that space
        for (int i = 0; i < gameBoard.length; i++){
            for (int j = 0; j < gameBoard[i].length; j++){

                if (gameBoard[i][j] == 0)
                    truesPassed++;
                
                if (truesPassed == selector){
                    int[] coords = {i, j};
                    return coords;
                }

            }
        }

    return null;
    
    }

    /**
     * Generates a new 1 to a random blank ("viable") space on gameBoard
     */
    public void spawnRandomOne() {

        // total amount of spaces we need to check the viability of
        int totalSpaces = getMainColumns() * getMainRows();

        // an int that tells how many blank spaces there are
        int totalViable = getTotalViable(totalSpaces, mainGameBoard);

        // a random number that will be used to determine which space we place 1 in
        // selector is between 1 and totalViable
        int selector = r.nextInt(totalViable) + 1;

        // step 4: select column and row to place our 1 in
        
        // an array with the indexes to identify which column and which row to place our 1 in

        int[] spawnCoords = getSpawnLocation(selector, mainGameBoard);

        mainGameBoard[spawnCoords[0]][spawnCoords[1]] = 1;

    }

    /**
     * Moves a single row left
     * @param row A single row on the gameBoard
     * @return The new row after moving left
     */
    public int[] moveRowLeft(int[] row){

        // the updated row that will be returned at the end
        int[] newRow = new int[row.length];

        // for tracking which index we are adding nums to newRow
        int newRowIndex = 0;

        newRow[0] = row[0];

        // for tracking if the previous index in newRow is the formation of two numbers
        boolean previousIsCombination = false;
        
        for (int i = 1; i < row.length; i++){

            if (row[i] != 0){

                if ((! previousIsCombination) & (newRow[newRowIndex] == row[i])){
                    newRow[newRowIndex] = row[i] * 2;
                    previousIsCombination = true;
                    newRowIndex++;
                }

                else if ((newRow[newRowIndex] != 0)){
                    newRowIndex++;
                    newRow[newRowIndex] = row[i];
                    previousIsCombination = false;

                }

                else {

                    newRow[newRowIndex] = row[i];
                    previousIsCombination = false;

                }

            }

        }

        return newRow;

    }

    /**
     * shifts each row of an entire gameBoard or jaggedBoard left
     * @param gameBoard 2D array representing the game board
     * @return the board which each of its rows shifted left
     */
    public int[][] getMoveLeft(int[][] gameBoard){

        int [][] newBoard = new int[gameBoard.length][gameBoard[0].length];

        // shift each row left one by one
        for (int i = 0; i < gameBoard.length; i++){
            newBoard[i] = moveRowLeft(gameBoard[i]);
        }

        return newBoard;
    }

    /**
     * Performs a top left movement of the gameBoard.
     * @param gameBoard 2D array representing the game board
     * @return 2D array of same dimensions with the movement performed
     */
    public int[][] getMoveTopLeft(int[][] gameBoard){

        // first rotate board 45 degrees counterclockwise to make jagged array

        // number of rows in gameBoard
        int numRows = gameBoard.length;
        
        // number of columns in gameBoard
        int numColumns = gameBoard[0].length;

        // the actual result of moving everything top left
        int[][] newBoard = new int[numRows][numColumns];

        // the number of rows in the jaggedBoard that have the biggest length
        int numMiddleRows = java.lang.Math.abs(numRows - numColumns) + 1;

        // the number of rows in the beginning and end of jaggedBoard that aren't the middle long rows
        int numNonMiddleRows = (numRows + numColumns - 1 - numMiddleRows);

        int numBeginningRows = numNonMiddleRows / 2;

        int MiddleRowLength = numRows;
        if (numRows > numColumns)
             MiddleRowLength = numColumns;

        int topRowIndex = numColumns - 1;

        // do beginning jagged rows, shift them left, and place results in newBoard
        for (int i = 0; i < numBeginningRows; i++){

            // array that represents a "diagonal row" of numbers going from top left to bottom right
            int[] jaggedRow = new int[i + 1];

            // fill in jaggedRow with corresponding elements from gameBoard
            for (int j = 0; j < jaggedRow.length; j++){
                jaggedRow[j] = gameBoard[j][topRowIndex + j];
            }

            // shift the elements of jaggedBoard
            jaggedRow = moveRowLeft(jaggedRow);

            // place the elements of jaggedRow into newBoard
            for (int j = 0; j < jaggedRow.length; j++){
                newBoard[j][topRowIndex + j] = jaggedRow[j];
            }

            topRowIndex--;

        }

        // for indexing down the rows once the beginning of our diagonal rows no longer start at top row
        int rowLevel = 1;

        // do middle jagged rows, shift them left, and place results in newBoard
        for (int i = 0; i < numMiddleRows; i++){

            // array that represents a "diagonal row" of numbers going from top left to bottom right
            int[] jaggedRow = new int[MiddleRowLength];

            if (topRowIndex < 0){ // executes if the beginning of our diagonal rows are starting from the left of gameBoard

                // fill in jaggedRow with corresponding elements from gameBoard
                for (int j = 0; j < jaggedRow.length; j++){
                    jaggedRow[j] = gameBoard[i + j][j];
                }

                // shift the elements of jaggedBoard
                jaggedRow = moveRowLeft(jaggedRow);
                
                // place the elements of jaggedRow into newBoard
                for (int j = 0; j < jaggedRow.length; j++){
                newBoard[i + j][j] = jaggedRow[j];
                }

                rowLevel++;

            }
            
            else { // executes if our diagonal rows are still starting from the top row of gameBoard

                // fill in jaggedRow with corresponding elements from gameBoard
                for (int j = 0; j < jaggedRow.length; j++){
                    jaggedRow[j] = gameBoard[j][topRowIndex + j];
                }

                // shift the elements of jaggedBoard
                jaggedRow = moveRowLeft(jaggedRow);

                // place the elements of jaggedRow into newBoard
                for (int j = 0; j < jaggedRow.length; j++){
                    newBoard[j][topRowIndex + j] = jaggedRow[j];
                }

                topRowIndex--;

            }
        }

        int shrinkingLength = MiddleRowLength - 1;

        // do end jagged rows, shift them left, and add them to newBoard
        for (int i = rowLevel; i < numRows; i++){

            // array that represents a "diagonal row" of numbers going from top left to bottom right
            int[] jaggedRow = new int[shrinkingLength];

            // fill in jaggedRow with corresponding elements from gameBoard
            for (int j = 0; j < jaggedRow.length; j++){
                jaggedRow[j] = gameBoard[i + j][j];
            }

            // shift the elements of jaggedBoard
            jaggedRow = moveRowLeft(jaggedRow);
            
            // place the elements of jaggedRow into newBoard
            for (int j = 0; j < jaggedRow.length; j++){
                newBoard[i + j][j] = jaggedRow[j];
            }

            shrinkingLength--;

        }

        return newBoard;

    }

    /**
     * Rotates the gameBoard 90 degrees clockwise
     * @param gameBoard 2D array representing the game board
     * @return int[][] that is the rotated gameBoard
     */
    public int[][] getRotatedBoard(int[][] gameBoard){

        // number of rows of unrotated gameBoard
        int gbNumRows = gameBoard.length;

        // number of rows of unrotated gameBoard
        int gbNumColumns = gameBoard[0].length;

        // our new rotated board is going to have the original board's values swapped
        int[][] rotatedBoard = new int[gbNumColumns][gbNumRows];

        // creates the contents of the rotatedBoard row by row
        for (int i = 0; i < rotatedBoard.length; i++){

            // adds element to each index corresponding with gameBoard
            for (int j = 0; j < rotatedBoard[i].length; j++){

                rotatedBoard[i][j] = gameBoard[gbNumRows - j - 1][i];

            }
        }

        return rotatedBoard;

    }

    /**
     * Moves elements in gameBoard up
     * @param gameBoard
     * @return a new board with elements moved up
     */
    public int[][] getMoveUp(int[][] gameBoard){

        // the board that will be the result of moving elements up
        int[][] newBoard = getRotatedBoard(gameBoard);

        newBoard = getRotatedBoard(newBoard);
        newBoard = getRotatedBoard(newBoard);
        newBoard = getMoveLeft(newBoard);
        newBoard = getRotatedBoard(newBoard);
        return newBoard;
        
    }

    /**
     * Moves elements in gameBoard right
     * @param gameBoard
     * @return a new board with elements moved right
     */
    public int[][] getMoveRight(int[][] gameBoard){

        // the board that will be the result of moving elements right
        int[][] newBoard = getRotatedBoard(gameBoard);

        newBoard = getRotatedBoard(newBoard);
        newBoard = getMoveLeft(newBoard);
        newBoard = getRotatedBoard(newBoard);
        newBoard = getRotatedBoard(newBoard);
        return newBoard;
        
    }

    /**
     * Moves elements in gameBoard down
     * @param gameBoard
     * @return a new board with elements moved down
     */
    public int[][] getMoveDown(int[][] gameBoard){

        // the board that will be the result of moving elements down
        int[][] newBoard = getRotatedBoard(gameBoard);

        newBoard = getMoveLeft(newBoard);
        newBoard = getRotatedBoard(newBoard);
        newBoard = getRotatedBoard(newBoard);
        newBoard = getRotatedBoard(newBoard);
        return newBoard;
        
    }

    /**
     * Moves elements in gameBoard top right
     * @param gameBoard
     * @return a new board with elements moved top right
     */
    public int[][] getMoveTopRight(int[][] gameBoard){

        // the board that will be the result of moving elements top right
        int[][] newBoard = getRotatedBoard(gameBoard);

        newBoard = getRotatedBoard(newBoard);
        newBoard = getRotatedBoard(newBoard);
        newBoard = getMoveTopLeft(newBoard);
        newBoard = getRotatedBoard(newBoard);
        return newBoard;
        
    }

    /**
     * Moves elements in gameBoard bottom right
     * @param gameBoard
     * @return a new board with elements moved bottom right
     */
    public int[][] getMoveBottomRight(int[][] gameBoard){

        // the board that will be the result of moving elements bottom right
        int[][] newBoard = getRotatedBoard(gameBoard);

        newBoard = getRotatedBoard(newBoard);
        newBoard = getMoveTopLeft(newBoard);
        newBoard = getRotatedBoard(newBoard);
        newBoard = getRotatedBoard(newBoard);
        return newBoard;
        
    }

    /**
     * Moves elements in gameBoard down
     * @param gameBoard
     * @return a new board with elements moved down
     */
    public int[][] getMoveBottomLeft(int[][] gameBoard){

        // the board that will be the result of moving elements bottom left
        int[][] newBoard = getRotatedBoard(gameBoard);

        newBoard = getMoveTopLeft(newBoard);
        newBoard = getRotatedBoard(newBoard);
        newBoard = getRotatedBoard(newBoard);
        newBoard = getRotatedBoard(newBoard);
        return newBoard;
        
    }

    public class ButtonAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e){

            int [][] unmovedBoard = mainGameBoard;

            // top left
            if (e.getSource().equals(buttonArray[0][0]))
                moveTopLeft();

            // top right
            if (e.getSource().equals(buttonArray[0][getMainColumns() - 1]))
                moveTopRight();

            // bottom right
            if (e.getSource().equals(buttonArray[getMainRows() - 1][getMainColumns() - 1]))
                moveBottomRight();

            // bottom left
            if (e.getSource().equals(buttonArray[getMainRows() - 1][0]))
                moveBottomLeft();

            // check top row
            for (int i = 1; i < getMainColumns() - 1; i++){
                if (e.getSource().equals(buttonArray[0][i]))
                    moveUp();
            }

            // check left
            for (int i = 1; i < getMainRows() - 1; i++){
                if (e.getSource().equals(buttonArray[i][0]))
                    moveLeft();
            }

            // check right
            for (int i = 1; i < getMainRows() - 1; i++){
                if (e.getSource().equals(buttonArray[i][getMainColumns() - 1]))
                    moveRight();
            }

            // check bottom
            for (int i = 1; i < getMainColumns() - 1; i++){
                if (e.getSource().equals(buttonArray[getMainRows() - 1][i]))
                    moveDown();
            }

        boolean movementHappened = false;
        for (int i = 0; i < getMainRows(); i++){
            for (int j = 0; j < getMainColumns(); j++){
                if (unmovedBoard[i][j] != mainGameBoard[i][j])
                    movementHappened = true;
            }
        }

        if (movementHappened){
            spawnRandomOne();
            update();
            }
        
        else
            System.out.println("No movement occurs, so did not spawn new 1\n");

        }

    }

    /**
     * A method that shifts the numbers left and adds them accordingly.
     */
    public void moveLeft(){
        System.out.println("\nmoving left");
        mainGameBoard = getMoveLeft(mainGameBoard);
    }

    /**
     * A method that shifts the numbers up and adds them accordingly.
     */
    public void moveUp(){
        System.out.println("\nmoving up");
        mainGameBoard = getMoveUp(mainGameBoard);
    }

    /**
     * A method that shifts the numbers right and adds them accordingly.
     */
    public void moveRight(){
        System.out.println("\nmoving right");
        mainGameBoard = getMoveRight(mainGameBoard);
    }

    /**
     * A method that shifts the numbers down and adds them accordingly.
     */
    public void moveDown(){
        System.out.println("\nmoving down");
        mainGameBoard = getMoveDown(mainGameBoard);
    }

    /**
     * A method that shifts the numbers down and adds them accordingly.
     */
    public void moveTopLeft(){
        System.out.println("\nmoving top left");
        mainGameBoard = getMoveTopLeft(mainGameBoard);
    }

    /**
     * A method that shifts the numbers down and adds them accordingly.
     */
    public void moveTopRight(){
        System.out.println("\nmoving top right");
        mainGameBoard = getMoveTopRight(mainGameBoard);
    }

    /**
     * A method that shifts the numbers down and adds them accordingly.
     */
    public void moveBottomRight(){
        System.out.println("\nmoving bottom right");
        mainGameBoard = getMoveBottomRight(mainGameBoard);
    }

    /**
     * A method that shifts the numbers down and adds them accordingly.
     */
    public void moveBottomLeft(){
        System.out.println("\nmoving bottom left");
        mainGameBoard = getMoveBottomLeft(mainGameBoard);
    }

    /**
     * A method that updates the buttons to display the correct numbers
     */
    public void update(){

        for (int i = 0; i < buttonArray.length; i++){
            for (int j = 0; j < buttonArray[0].length; j++){

                if (mainGameBoard[i][j] != 0)
                buttonArray[i][j].setText(Integer.toString(mainGameBoard[i][j]));
                else
                    buttonArray[i][j].setText(" ");

            }
        }
    }

    @Override
    public void start(Stage primaryStage){

        // contains the parameters passed when starting this program in terminal
        List<String> args = getParameters().getRaw();

        if ((! args.isEmpty()) && (args.size() == 2)){

            // argument for rows passed in terminal
            int r = Integer.parseInt(args.get(0));

            // argument for columns passed in terminal
            int c = Integer.parseInt(args.get(1));
            
            // if either argument is less than 2, set dimensions to default 4
            // since game is unplayable with 2 rows or 2 columns
            if ((r <= 2) || (c <= 2)){
                c = 4;
                r = 4;
            }
                
            setMainRows(r);
            setMainColumns(c);
            
        }

        // GridPane to house buttons in grid
        GridPane layout = new GridPane();

        // 2D array that contains values that the buttons will always be displaying
        mainGameBoard = new int[getMainRows()][getMainColumns()];

        // button array matches mainGameBoard so we can match each button pressed to the intended method
        buttonArray = new Button[getMainRows()][getMainColumns()];

        // put each button in buttonArray, set ButtonAction() to each, and
        // add the button to the GridPane
        for (int i = 0; i < buttonArray.length; i++){
            for (int j = 0; j < buttonArray[0].length; j++){

                buttonArray[i][j] = new Button();
                buttonArray[i][j].setOnAction(new ButtonAction());
                buttonArray[i][j].setPrefSize(45, 45);
                layout.add(buttonArray[i][j], j, i);

            }
        }
        
        spawnRandomOne();
        update();

        // scene for primaryStage
        Scene scene = new Scene(layout);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
