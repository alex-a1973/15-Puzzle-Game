import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class represents a search method (GBFS) which is ran when the user wants to
 * run this specific search method. This search methods searches over a 15-puzzle
 * tile game in hopes of finding a goal state/board
 * @author Alex Amado
 */
public class Greedy {
    /**
     * String to compare against when deciphering if user wants heuristic 1
     */
    private static final String HEURISTIC_ONE = "h1";
    /**
     * String to compare against when deciphering if user wants heuristic 2
     */
    private static final String HEURISTIC_TWO = "h2";

    /**
     * Our fringe data structure for this algorithm
     */
    private PriorityQueue<Node> myPrioQueue;
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
     * A boolean value which indicates whether the user wanted to run this algorithm with
     * heuristic 1
     */
    private boolean isHeuristicOne;
    /**
     * A boolean value which indicates whether the user wanted to run this algorithm with
     * heuristic 2
     */
    private boolean isHeuristicTwo;

    /**
     * Constructs a GBFS object which initializes properties of a GBFS such as
     * statistical measurements, and runs the 'search()' method as well
     * @param initialState user provided initial state of the board the user
     *                     wants to start from on search
     * @param theHeuristic user provided heuristic to invoke on each state at each
     *                     moment of the search (number of missing tiles and
     *                     sum of Manhattan distances to each cell of the boards
     *                     correct positions)
     */
    public Greedy(String initialState, String theHeuristic) {
        // Create new 'Board' object with 'initialState'
        Board rootBoard = new Board(initialState);
        // Create new 'Node' object with 'rootBoard'
        Node rootNode = new Node(rootBoard);
        myPrioQueue = new PriorityQueue<>();
        // Add new 'Board' object to queue
        myPrioQueue.add(rootNode);
        maxDepth = 0;
        nodesCreated = 1;
        nodesExpanded = 0;
        maxFringeSize = myPrioQueue.size();
        // Create new set of visited states
        visited = new HashSet<>();
        // Initialize which heuristic to choose
        if (theHeuristic.equals(HEURISTIC_ONE)) {
            isHeuristicOne = true;
        } else {
            isHeuristicTwo = true;
        }
        // Run the search algorithm
        search();
    }

    /**
     * Runs the search algorithm on 'initialState' passed by user from
     * the constructor and reports stats on completion
     */
    private void search() {
        while (!myPrioQueue.isEmpty()) {
            // Dequeue head of queue
            Node node = myPrioQueue.remove();
            // Store board state of 'node'
            Board board = node.myState;
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
            ArrayList<Board> successorBoards = board.getSuccessorNodesBFSAStarGBFS();
            // Iterate over every successor board/node and add to the queue
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
                // Create new 'Node' object from 'successorBoard'
                Node successorNode = new Node(successorBoard);
                myPrioQueue.add(successorNode);
            }

            // Check if fringe size is the max we've seen so far
            if (myPrioQueue.size() > maxFringeSize) {
                maxFringeSize = myPrioQueue.size();
            }
        }
        System.out.println(maxDepth + ", " + nodesCreated + ", " + nodesExpanded + ", " + maxFringeSize);
    }

    /**
     * This class represents a way to apply a natural ordering of states/boards through
     * implementation of 'Comparable<Node>' needed for use of data structure this
     * particular algorithm utilizes
     */
    private class Node implements Comparable<Node> {
        /**
         * Represents this 'Node's data which it holds, being a 'Board'
         */
        private Board myState;

        /**
         * Constructs a 'Node' object and initializes our state field
         * @param theState the state/board used to initialize our state field
         */
        public Node(Board theState) {
            myState = theState;
        }

        /**
         * This method gives natural ordering for the PriorityQueue to work by comparing
         * the minimum heuristic measurement for goal state 1 and 2 for 'this' object's
         * state and minimum heuristic measurement for goal state 1 and 2 for another
         * 'Node' object's state depending on which heuristic the user provided
         * @param theOtherNode the other 'Node' object to compare state heuristics with
         * @return -1 if 'this' state's heuristic is lower than the other, 0 if they're
         * equal in heuristic, and +1 if 'this' state's heuristic is greater than the
         * other
         */
        @Override
        public int compareTo(Node theOtherNode) {
            int result = 0;
            Board theOtherState = theOtherNode.myState;
            // Get minimum first heuristic out of the two goal states for 'this'
            int thisHeuristicOne = Math.min(myState.getHeuristicOneGoalOne(), myState.getHeuristicOneGoalTwo());
            // Get minimum first heuristic out of the two goal states for 'theOtherNode'
            int otherHeuristicOne = Math.min(theOtherState.getHeuristicOneGoalOne(), theOtherState.getHeuristicOneGoalTwo());
            // Get minimum second heuristic out of the two goal states for 'this'
            int thisHeuristicTwo = Math.min(myState.getHeuristicTwoGoalOne(), myState.getHeuristicTwoGoalTwo());
            // Get minimum second heuristic out of the two goal states for 'theOtherNode'
            int otherHeuristicTwo = Math.min(theOtherState.getHeuristicTwoGoalOne(), theOtherState.getHeuristicTwoGoalTwo());
            if (isHeuristicOne) {
                if (thisHeuristicOne < otherHeuristicOne) {
                    result = -1;
                } else {
                    result = 1;
                }
            } else {
                if (thisHeuristicTwo < otherHeuristicTwo) {
                    result = -1;
                } else {
                    result = 1;
                }
            }
            return result;
        }
    }
}