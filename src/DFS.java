import java.util.*;

/**
 * This class represents a search method (DFS) which is ran when the user wants to
 * run this specific search method. This search methods searches over a 15-puzzle
 * tile game in hopes of finding a goal state/board
 * @author Alex Amado
 */
public class DFS {
    /**
     * Our fringe data structure for this algorithm
     */
    private Stack<Board> myStack;
    /**
     * Represents the depth in the search tree where the solution is found
     */
    private int maxDepth;
    /**
     * Represents the counter that is incremented every time a node of the
     * search tree is created
     */
    private int nodesCreated;
    /**
     * Represents the counter that will be incremented every time the search
     * algorithm acquires the successor states to the current state
     */
    private int nodesExpanded;
    /**
     * Represents the maximum size of the fringe at any point during the search
     */
    private int maxFringeSize;
    /**
     * A set which takes care of not visiting already visited states/boards
     */
    private Set<String> visited;

    /**
     * Constructs a DFS object which initializes properties of a DFS such as
     * statistical measurements, and runs the 'search()' method as well
     * @param initialState user provided initial state of the board the user
     *                     wants to start from on search
     */
    public DFS(String initialState) {
        // Create new 'Board' object with 'initialState'
        Board rootBoard = new Board(initialState);
        myStack = new Stack<>();
        // Add new 'Board' object to queue
        myStack.add(rootBoard);
        maxDepth = 0;
        nodesCreated = 1;
        nodesExpanded = 0;
        maxFringeSize = myStack.size();
        // Create new set of visited states
        visited = new HashSet<>();
        // Run the search algorithm
        search();
    }

    /**
     * Runs the search algorithm on 'initialState' passed by user from
     * the constructor and reports stats on completion
     */
    private void search() {
        while (!myStack.isEmpty()) {
            // Dequeue head of queue
            Board board = myStack.pop();
            // Get String representation of 'board'
            String strBoard = board.getState();
            // Add dequeue'd board/node to 'visited' set
            visited.add(strBoard);
            // Increment 'nodesExpanded'
            nodesExpanded++;
            // Check if dequeue'd board is equal to either goal states
            if (board.isGoalState()) {
                maxDepth = board.getDepth();
                break;
            }
            // Get successor boards/nodes
            ArrayList<Board> successorBoards = board.getSuccessorNodesDFSDLS();
            for (Board successorBoard : successorBoards) {
                // Check if 'successorBoard' was already previously added to 'visited' set
                if (visited.contains(successorBoard.getState())) {
                    continue;
                }
                // Increment 'numCreated' by number of 'successorBoards'
                nodesCreated++;
                // Get parent's depth (parent is the 'board' local variable)
                int parentDepth = board.getDepth();
                // Set successor board's/node's to be equal to 1 + 'parentDepth'
                successorBoard.setDepth(parentDepth + 1);
                myStack.add(successorBoard);
            }

            // Check if fringe size is the max we've seen so far
            if (myStack.size() > maxFringeSize) {
                maxFringeSize = myStack.size();
            }
        }
        System.out.println(maxDepth + ", " + nodesCreated + ", " + nodesExpanded + ", " + maxFringeSize);
    }
}
