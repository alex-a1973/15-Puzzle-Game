import java.util.ArrayList;

/**
 * This class represents the state of the puzzle through a 'Board' object and
 * has various information associated with each state as well as behaviors as
 * to manipulate the state
 * @author Alex Amado
 */
public class Board {
    /**
     * String representation of state/board
     */
    private String state;
    /**
     * Indicates the depth of this 'Board' object in this 'tree' structure
     */
    private int depth;
    /**
     * Represents the 'board' in the form of a 2D array
     */
    private char[][] board;
    /**
     * Integer array which contains the row and column of the blank space found
     * in the 2D array 'board'. Ex: [Row, Col]
     */
    private int[] blankSpacePosition;
    /**
     * Represents the goal state for goal state number 1
     */
    private char[][] goalState1;
    /**
     * Represents the goal state for goal state number 2
     */
    private char[][] goalState2;
    /**
     * Heuristic one (number of tiles that are misplaced) for this 'Board'
     * object on 'goalState1'
     */
    private int heuristicOneGoalOne;
    /**
     * Heuristic one (number of tiles that are misplaced) for this 'Board'
     * object on 'goalState2'
     */
    private int heuristicOneGoalTwo;
    /**
     * Heuristic two (sum of Manhattan distances) for this 'Board' object
     * on 'goalState1'
     */
    private int heuristicTwoGoalOne;
    /**
     * Heuristic two (sum of Manhattan distances) for this 'Board' object
     * on 'goalState2'
     */
    private int heuristicTwoGoalTwo;

    /**
     * User provided constructor which will allow the user to provide an 'initialState'
     * in the form of a String which will represent our 'Board' object which stores
     * a 2D array under the hood
     * @param initialState String representation of the 2D array 'Board' object provided
     *                     by the user
     */
    public Board(String initialState) {
        // Initialize string representation of the board
        state = initialState;
        // Initialize depth
        depth = 0;
        // Initialize 'board'
        board = new char[4][4];
        int row = 0;
        int col = 0;
        // Initialize array for blank space
        blankSpacePosition = new int[2];
        for (int i = 0; i < initialState.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                row++;
                col = 0;
            }
            board[row][col] = initialState.charAt(i);
            if (initialState.charAt(i) == ' ') {
                blankSpacePosition[0] = row;
                blankSpacePosition[1] = col;
            }
            col++;
        }
        // Initialize goal state boards
        initializeGoalStates();
        // Initialize heuristics
        initializeHeuristicOneGoalOne();
        initializeHeuristicOneGoalTwo();
        initializeHeuristicTwoGoalOne();
        initializeHeuristicTwoGoalTwo();
    }

    /**
     * Constructs a 'Board' object given a 2D array. This primary use is for in-class work
     * such as calling 'deepCopy' with the 2D array contents of this current 'board'
     * @param theBoard a deep copied 2D array taken from another 'Board' object to initialize 'this'
     *                 one
     */
    private Board(char[][] theBoard) {
        // Decode 2D array to get string representation
        state = "";
        // Primarily used to create a new object given a 2D array board
        board = theBoard;
        // Initialize array for blank space
        blankSpacePosition = new int[2];
        // Find blank space position in 'theBoard'
        for (int i = 0; i < theBoard.length; i++) {
            for (int j = 0; j < theBoard[i].length; j++) {
                if (theBoard[i][j] == ' ') {
                    blankSpacePosition[0] = i;
                    blankSpacePosition[1] = j;
                }
                state += theBoard[i][j];
            }
        }
        // Initialize goal state boards
        initializeGoalStates();
        // Initialize heuristics
        initializeHeuristicOneGoalOne();
        initializeHeuristicOneGoalTwo();
        initializeHeuristicTwoGoalOne();
        initializeHeuristicTwoGoalTwo();
    }

    /**
     * Returns successor nodes of this current 'board' for only BFS, A*, and
     * Greedy search because they use different data structure (queue) so they
     * expand in correct order (right, down, left, and up)
     * @return returns an ArrayList of type Board in order of expansion for
     * searches BFS, A*, and Greedy (right, down, left, and up)
     */
    public ArrayList<Board> getSuccessorNodesBFSAStarGBFS() {
        ArrayList<Board> successorNodes = new ArrayList<Board>();
        int row = blankSpacePosition[0];
        int col = blankSpacePosition[1];
        Board rightBoard;
        Board downBoard;
        Board leftBoard;
        Board upBoard;
        // Blank space in (0, 0) position, 2 moves
        if (row == 0 && col == 0) {
            // Move blank space right
            rightBoard = deepCopy();
            // Swap blank position righwards
            rightBoard.swapBlankDirectionRight(row, col);
            // Move blank space down
            downBoard = deepCopy();
            // Swap blank position downwards
            downBoard.swapBlankDirectionDown(row, col);
            // Add new states to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(downBoard);
        }
        // Blank space in (0, 1) position, 3 moves
        else if (row == 0 && col == 1) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(downBoard);
            successorNodes.add(leftBoard);
        }
        // Blank space in (0, 2) position, 3 moves
        else if (row == 0 && col == 2) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(downBoard);
            successorNodes.add(leftBoard);
        }
        // Blank space in (0, 3) position, 2 moves
        else if (row == 0 && col == 3) {
            // Perform deep copies and all possible moves for blank space
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(downBoard);
            successorNodes.add(leftBoard);
        }
        // Blank space in (1, 0) position, 3 moves
        else if (row == 1 && col == 0) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(downBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (1, 1) position, 4 moves
        else if (row == 1 && col == 1) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(downBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (1, 2) position, 4 moves
        else if (row == 1 && col == 2) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(downBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (1, 3) position, 3 moves
        else if (row == 1 && col == 3) {
            // Perform deep copies and all possible moves for blank space
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(downBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (2, 0) position, 3 moves
        else if (row == 2 && col == 0) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(downBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (2, 1) position, 4 moves
        else if (row == 2 && col == 1) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(downBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (2, 2) position, 4 moves
        else if (row == 2 && col == 2) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(downBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (2, 3) position, 3 moves
        else if (row == 2 && col == 3) {
            // Perform deep copies and all possible moves for blank space
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(downBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (3, 0) position, 2 moves
        else if (row == 3 && col == 0) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (3, 1) position, 3 moves
        else if (row == 3 && col == 1) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (3, 2) position, 3 moves
        else if (row == 3 && col == 2) {
            // Perform deep copies and all possible moves for blank space
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(rightBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(upBoard);
        }
        // Blank space in (3, 3) position, 2 moves
        else if (row == 3 && col == 3) {
            // Perform deep copies and all possible moves for blank space
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(leftBoard);
            successorNodes.add(upBoard);
        }
        return successorNodes;
    }

    /**
     * Returns successor nodes of this current 'board' for only DFS and DLS
     * search because they use different data structure (stack) so they expand
     * in opposite order for (up, left, down, and right due to stack LIFO)
     * @return
     */
    public ArrayList<Board> getSuccessorNodesDFSDLS() {
       ArrayList<Board> successorNodes = new ArrayList<Board>();
        int row = blankSpacePosition[0];
        int col = blankSpacePosition[1];
        Board upBoard;
        Board leftBoard;
        Board downBoard;
        Board rightBoard;
        // Blank space in (0, 0) position, 2 moves
        if (row == 0 && col == 0) {
            // Perform deep copies and all possible moves for blank space
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(downBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (0, 1) position, 3 moves
        else if (row == 0 && col == 1) {
            // Perform deep copies and all possible moves for blank space
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(leftBoard);
            successorNodes.add(downBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (0, 2) position, 3 moves
        else if (row == 0 && col == 2) {
            // Perform deep copies and all possible moves for blank space
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(leftBoard);
            successorNodes.add(downBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (0, 3) position, 2 moves
        else if (row == 0 && col == 3) {
            // Perform deep copies and all possible moves for blank space
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(leftBoard);
            successorNodes.add(downBoard);
        }
        // Blank space in (1, 0) position, 3 moves
        else if (row == 1 && col == 0) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(downBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (1, 1) position, 4 moves
        else if (row == 1 && col == 1) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(downBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (1, 2) position, 4 moves
        else if (row == 1 && col == 2) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(downBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (1, 3) position, 3 moves
        else if (row == 1 && col == 3) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(downBoard);
        }
        // Blank space in (2, 0) position, 3 moves
        else if (row == 2 && col == 0) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(downBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (2, 1) position, 4 moves
        else if (row == 2 && col == 1) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(downBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (2, 2) position, 4 moves
        else if (row == 2 && col == 2) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(downBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (2, 3) position, 3 moves
        else if (row == 2 && col == 3) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            downBoard = deepCopy();
            downBoard.swapBlankDirectionDown(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(downBoard);
        }
        // Blank space in (3, 0) position, 2 moves
        else if (row == 3 && col == 0) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (3, 1) position, 3 moves
        else if (row == 3 && col == 1) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (3, 2) position, 3 moves
        else if (row == 3 && col == 2) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            rightBoard = deepCopy();
            rightBoard.swapBlankDirectionRight(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(leftBoard);
            successorNodes.add(rightBoard);
        }
        // Blank space in (3, 3) position, 2 moves
        else if (row == 3 && col == 3) {
            // Perform deep copies and all possible moves for blank space
            upBoard = deepCopy();
            upBoard.swapBlankDirectionUp(row, col);
            leftBoard = deepCopy();
            leftBoard.swapBlankDirectionLeft(row, col);
            // Add each of the board states above to 'successorNodes' list
            successorNodes.add(upBoard);
            successorNodes.add(leftBoard);
        }
       return successorNodes;
    }

    /**
     * Copies this 'Board's internal board data structure by duplicating
     * it to another 2D character array
     * @return a 2D character array which is a copy of 'board'
     */
    public Board deepCopy() {
        char[][] copyBoard = new char[4][4];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                copyBoard[i][j] = board[i][j];
            }
        }
        Board newBoard = new Board(copyBoard);
        return newBoard;
    }

    /**
     * Allows the user to mutate the 'depth' field
     * @param theDepth the user passed argument which 'depth' field will be set too
     */
    public void setDepth(int theDepth) {
        depth = theDepth;
    }

    /**
     * Returns the current 'board' state to the user in String format
     * @return returns a String representing the 'board'
     */
    public String getState() {
        return state;
    }

    /**
     * Return the board's current depth
     * @return the 'Board' objects current depth in hypothetical tree structure
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Returns the 2D array/'board' of the puzzle
     * @return a 2D array which resembles the puzzle
     */
    public char[][] getBoard() {
        return board;
    }

    /**
     * Return the integer array which contains the blank space position
     * @return row and column of blank space position in a integer array
     */
    public int[] getBlankSpacePosition() {
        return blankSpacePosition;
    }

    /**
     * Returns the 2D array goal state, the first one of the two
     * @return a 2D array which resembles the goal state of a board
     */
    public char[][] getGoalState1() {
        return goalState1;
    }

    /**
     * Returns the 2D array goal state, the second one of the two
     * @return a 2D array which resembles the goal state of a board
     */
    public char[][] getGoalState2() {
        return goalState2;
    }

    /**
     * Return the heuristic one measured on the 'goalState1'
     * @return heuristic one measured on 'goalState1'
     */
    public int getHeuristicOneGoalOne() {
        return heuristicOneGoalOne;
    }

    /**
     * Return the heuristic one measured on the 'goalState2'
     * @return heuristic one measured on 'goalState2'
     */
    public int getHeuristicOneGoalTwo() {
        return heuristicOneGoalTwo;
    }

    /**
     * Return the heuristic two measured on the 'goalState1'
     * @return heuristic two measured on 'goalState1'
     */
    public int getHeuristicTwoGoalOne() {
        return heuristicTwoGoalOne;
    }

    /**
     * Return the heuristic two measured on the 'goalState2'
     * @return heuristic two measured on 'goalState2'
     */
    public int getHeuristicTwoGoalTwo() {
        return heuristicTwoGoalTwo;
    }

    /**
     * Checks if current 'board' state is a goal state or not
     * @return a boolean indicating this 'board' is a goal state or not
     */
    public boolean isGoalState() {
        boolean checkGoalState1 = true;
        boolean checkGoalState2 = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != goalState1[i][j] && checkGoalState1) {
                    checkGoalState1 = false;
                }

                if (board[i][j] != goalState2[i][j] && checkGoalState2) {
                    checkGoalState2 = false;
                }
            }
            if (!checkGoalState1 && !checkGoalState2) {
                break;
            }
        }
        return checkGoalState1 || checkGoalState2;
    }

    /**
     * Sets up the heuristic one on initialization of 'this' object (i.e.,
     * constructor is called) or alteration of 'board on 'goalState1'
     */
    private void initializeHeuristicOneGoalOne() {
        if (state.charAt(0) != '1') heuristicOneGoalOne++;
        if (state.charAt(1) != '2') heuristicOneGoalOne++;
        if (state.charAt(2) != '3') heuristicOneGoalOne++;
        if (state.charAt(3) != '4') heuristicOneGoalOne++;
        if (state.charAt(4) != '5') heuristicOneGoalOne++;
        if (state.charAt(5) != '6') heuristicOneGoalOne++;
        if (state.charAt(6) != '7') heuristicOneGoalOne++;
        if (state.charAt(7) != '8') heuristicOneGoalOne++;
        if (state.charAt(8) != '9') heuristicOneGoalOne++;
        if (state.charAt(9) != 'A') heuristicOneGoalOne++;
        if (state.charAt(10) != 'B') heuristicOneGoalOne++;
        if (state.charAt(11) != 'C') heuristicOneGoalOne++;
        if (state.charAt(12) != 'D') heuristicOneGoalOne++;
        if (state.charAt(13) != 'E') heuristicOneGoalOne++;
        if (state.charAt(14) != 'F') heuristicOneGoalOne++;
        if (state.charAt(15) != ' ') heuristicOneGoalOne++;
    }

    /**
     * Sets up the heuristic one on initialization of 'this' object (i.e.,
     * constructor is called) or alteration of 'board' on 'goalState1'
     */
    private void initializeHeuristicOneGoalTwo() {
        if (state.charAt(0) != '1') heuristicOneGoalTwo++;
        if (state.charAt(1) != '2') heuristicOneGoalTwo++;
        if (state.charAt(2) != '3') heuristicOneGoalTwo++;
        if (state.charAt(3) != '4') heuristicOneGoalTwo++;
        if (state.charAt(4) != '5') heuristicOneGoalTwo++;
        if (state.charAt(5) != '6') heuristicOneGoalTwo++;
        if (state.charAt(6) != '7') heuristicOneGoalTwo++;
        if (state.charAt(7) != '8') heuristicOneGoalTwo++;
        if (state.charAt(8) != '9') heuristicOneGoalTwo++;
        if (state.charAt(9) != 'A') heuristicOneGoalTwo++;
        if (state.charAt(10) != 'B') heuristicOneGoalTwo++;
        if (state.charAt(11) != 'C') heuristicOneGoalTwo++;
        if (state.charAt(12) != 'D') heuristicOneGoalTwo++;
        if (state.charAt(13) != 'F') heuristicOneGoalTwo++;
        if (state.charAt(14) != 'E') heuristicOneGoalTwo++;
        if (state.charAt(15) != ' ') heuristicOneGoalTwo++;
    }

    /**
     * Sets up the heuristic two on initialization of 'this' object (i.e.,
     * constructor is called) or alteration of 'board' on 'goalState1'
     */
    private void initializeHeuristicTwoGoalOne() {
        int sumManhattanDist = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char currChar = board[i][j];
                if (currChar == '1') {
                    sumManhattanDist += Math.abs(0 - i) + Math.abs(0 - j);
                } else if (currChar == '2') {
                    sumManhattanDist += Math.abs(0 - i) + Math.abs(1 - j);
                } else if (currChar == '3') {
                    sumManhattanDist += Math.abs(0 - i) + Math.abs(2 - j);
                } else if (currChar == '4') {
                    sumManhattanDist += Math.abs(0 - i) + Math.abs(3 - j);
                } else if (currChar == '5') {
                    sumManhattanDist += Math.abs(1 - i) + Math.abs(0 - j);
                } else if (currChar == '6') {
                    sumManhattanDist += Math.abs(1 - i) + Math.abs(1 - j);
                } else if (currChar == '7') {
                    sumManhattanDist += Math.abs(1 - i) + Math.abs(2 - j);
                } else if (currChar == '8') {
                    sumManhattanDist += Math.abs(1 - i) + Math.abs(3 - j);
                } else if (currChar == '9') {
                    sumManhattanDist += Math.abs(2 - i) + Math.abs(0 - j);
                } else if (currChar == 'A') {
                    sumManhattanDist += Math.abs(2 - i) + Math.abs(1 - j);
                } else if (currChar == 'B') {
                    sumManhattanDist += Math.abs(2 - i) + Math.abs(2 - j);
                } else if (currChar == 'C') {
                    sumManhattanDist += Math.abs(2 - i) + Math.abs(3 - j);
                } else if (currChar == 'D') {
                    sumManhattanDist += Math.abs(3 - i) + Math.abs(0 - j);
                } else if (currChar == 'E') {
                    sumManhattanDist += Math.abs(3 - i) + Math.abs(1 - j);
                } else if (currChar == 'F') {
                    sumManhattanDist += Math.abs(3 - i) + Math.abs(2 - j);
                } else if (currChar == ' ') {
                    sumManhattanDist += Math.abs(3 - i) + Math.abs(3 - j);
                }
            }
        }
        heuristicTwoGoalOne = sumManhattanDist;
    }

    /**
     * Sets up the heuristic two on initialization of 'this' object (i.e.,
     * constructor is called) or alteration of 'board' on 'goalState2'
     */
    private void initializeHeuristicTwoGoalTwo() {
        int sumManhattanDist = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                char currChar = board[i][j];
                if (currChar == '1') {
                    sumManhattanDist += Math.abs(0 - i) + Math.abs(0 - j);
                } else if (currChar == '2') {
                    sumManhattanDist += Math.abs(0 - i) + Math.abs(1 - j);
                } else if (currChar == '3') {
                    sumManhattanDist += Math.abs(0 - i) + Math.abs(2 - j);
                } else if (currChar == '4') {
                    sumManhattanDist += Math.abs(0 - i) + Math.abs(3 - j);
                } else if (currChar == '5') {
                    sumManhattanDist += Math.abs(1 - i) + Math.abs(0 - j);
                } else if (currChar == '6') {
                    sumManhattanDist += Math.abs(1 - i) + Math.abs(1 - j);
                } else if (currChar == '7') {
                    sumManhattanDist += Math.abs(1 - i) + Math.abs(2 - j);
                } else if (currChar == '8') {
                    sumManhattanDist += Math.abs(1 - i) + Math.abs(3 - j);
                } else if (currChar == '9') {
                    sumManhattanDist += Math.abs(2 - i) + Math.abs(0 - j);
                } else if (currChar == 'A') {
                    sumManhattanDist += Math.abs(2 - i) + Math.abs(1 - j);
                } else if (currChar == 'B') {
                    sumManhattanDist += Math.abs(2 - i) + Math.abs(2 - j);
                } else if (currChar == 'C') {
                    sumManhattanDist += Math.abs(2 - i) + Math.abs(3 - j);
                } else if (currChar == 'D') {
                    sumManhattanDist += Math.abs(3 - i) + Math.abs(0 - j);
                } else if (currChar == 'F') {
                    sumManhattanDist += Math.abs(3 - i) + Math.abs(1 - j);
                } else if (currChar == 'E') {
                    sumManhattanDist += Math.abs(3 - i) + Math.abs(2 - j);
                } else if (currChar == ' ') {
                    sumManhattanDist += Math.abs(3 - i) + Math.abs(3 - j);
                }
            }
        }
        heuristicTwoGoalTwo = sumManhattanDist;
    }

    /**
     * Given a location of a blank space cell (row and column location), swap to the right
     * direction in 2D array ('board')
     * @param row row location of cell to be swapped rightwards
     * @param col column location of cell to be swapped rightwards
     */
    private void swapBlankDirectionRight(int row, int col) {
        char temp = board[row][col];
        board[row][col] = board[row][col+1];
        board[row][col+1] = temp;
        blankSpacePosition[0] = row;
        blankSpacePosition[1] = col + 1;
        // Reinitialize 'state' field
        state = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                state += board[i][j];
            }
        }
        // Reinitialize heuristics for altered state
        initializeHeuristicOneGoalOne();
        initializeHeuristicOneGoalTwo();
        initializeHeuristicTwoGoalOne();
        initializeHeuristicTwoGoalTwo();
    }

    /**
     * Given a location of a blank space cell (row and column location), swap downwards
     * in 2D array ('board')
     * @param row row location of cell to be swapped downwards
     * @param col column location of cell to be swapped downwards
     */
    private void swapBlankDirectionDown(int row, int col) {
        char temp = board[row][col];
        board[row][col] = board[row+1][col];
        board[row+1][col] = temp;
        blankSpacePosition[0] = row + 1;
        blankSpacePosition[1] = col;
        // Reinitialize 'state' field
        state = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                state += board[i][j];
            }
        }
        // Reinitialize heuristics for altered state
        initializeHeuristicOneGoalOne();
        initializeHeuristicOneGoalTwo();
        initializeHeuristicTwoGoalOne();
        initializeHeuristicTwoGoalTwo();
    }

    /**
     * Given a location of a blank space cell (row and column location), swap leftwards
     * in 2D array ('board')
     * @param row row location of cell to be swapped leftwards
     * @param col column location of cell to be swapped leftwards
     */
    private void swapBlankDirectionLeft(int row, int col) {
        char temp = board[row][col];
        board[row][col] = board[row][col-1];
        board[row][col-1] = temp;
        blankSpacePosition[0] = row;
        blankSpacePosition[1] = col - 1;
        // Reinitialize 'state' field
        state = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                state += board[i][j];
            }
        }
        // Reinitialize heuristics for altered state
        initializeHeuristicOneGoalOne();
        initializeHeuristicOneGoalTwo();
        initializeHeuristicTwoGoalOne();
        initializeHeuristicTwoGoalTwo();
    }

    /**
     * Given a location of a blank space cell (row and column location), swap upwards
     * in 2D array ('board')
     * @param row row location of cell to be swapped upwards
     * @param col column location of cell to be swapped upwards
     */
    private void swapBlankDirectionUp(int row, int col) {
        char temp = board[row][col];
        board[row][col] = board[row-1][col];
        board[row-1][col] = temp;
        blankSpacePosition[0] = row - 1;
        blankSpacePosition[1] = col;
        // Reinitialize 'state' field
        state = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                state += board[i][j];
            }
        }
        // Reinitialize heuristics for altered state
        initializeHeuristicOneGoalOne();
        initializeHeuristicOneGoalTwo();
        initializeHeuristicTwoGoalOne();
        initializeHeuristicTwoGoalTwo();
    }

    /**
     * Initializes 'goalState1' and 'goalState2' fields which hold 2D arrays
     * that resemble the goal state the 'board' field should be
     */
    private void initializeGoalStates() {
        // Initialize 'goalState1'
        goalState1 = new char[][] {
                {'1', '2', '3', '4'},
                {'5', '6', '7', '8'},
                {'9', 'A', 'B', 'C'},
                {'D', 'E', 'F', ' '}
        };

        // Initialize 'goalState2'
        goalState2 = new char[][] {
                {'1', '2', '3', '4'},
                {'5', '6', '7', '8'},
                {'9', 'A', 'B', 'C'},
                {'D', 'F', 'E', ' '}
        };
    }

    /**
     * Returns a string representation of the 'Board' object
     * @return a string representing the 'Board'
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            sb.append("[" + board[i][0]);
            for (int j = 1; j < board[i].length; j++) {
                sb.append(", " + board[i][j]);
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}