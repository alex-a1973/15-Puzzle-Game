import java.util.HashSet;
import java.util.Set;

/**
 * This class contains the 'main()' method which is responsible for running this program
 * @author Alex Amado
 */
public class Main {
    /**
     * Driver method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            String initialState = args[0];
            if (!verifyInitialState(initialState)) {
                throw new IllegalArgumentException("Please specify valid initial state.");
            }

            String searchMethod = args[1];
            switch (searchMethod) {
                case "BFS":
                    // Use breadth-first search
                    System.out.println("BFS");
                    BFS bfs = new BFS(initialState);
                    break;
                case "DFS":
                    // Use depth-first search
                    System.out.println("DFS");
                    DFS dfs = new DFS(initialState);
                    break;
                default:
                    throw new IllegalArgumentException("Please specify either \'BFS\' or \'DFS\' search methods.");
            }
        } else if (args.length == 3) {
            String initialState = args[0];
            if (!verifyInitialState(initialState)) {
                throw new IllegalArgumentException("Please specify valid initial state.");
            }

            String searchMethod = args[1];
            String options = args[2];
            switch (searchMethod) {
                case "GBFS":
                    if (!verifyOptions(searchMethod, options)) {
                        throw new IllegalArgumentException("Please specify valid options");
                    }
                    // Use greedy search
                    System.out.println("GBFS " + options);
                    Greedy greedy = new Greedy(initialState, options);
                    break;
                case "AStar":
                    if (!verifyOptions(searchMethod, options)) {
                        throw new IllegalArgumentException("Please specify valid options");
                    }
                    // Use A* search
                    System.out.println("A* " + options);
                    AStar aStar = new AStar(initialState, options);
                    break;
                case "DLS":
                    if (!verifyOptions(searchMethod, options)) {
                        throw new IllegalArgumentException("Please specify valid options");
                    }
                    // Use depth-limited search
                    System.out.println("DLS " + options);
                    int limitedDepth = Integer.parseInt(options);
                    DLS dls = new DLS(initialState, limitedDepth);
                    break;
                default:
                    throw new IllegalArgumentException("Please specify either \'GBFS\', \'AStar\', or \'DLS\' search methods.");
            }
        } else {
            throw new IllegalArgumentException("Please specify: {initialState}, {searchMethod}, {options (optional)}");
        }
    }

    /**
     * Verifies the initial state passed in through command line by the user
     * @param initialState the initial state of the 'Board' we must perform a search on
     * @return a boolean whether or not the initial state is valid
     */
    private static boolean verifyInitialState(String initialState) {
        boolean isValid = true;
        Set<Character> validCharacters = new HashSet<Character>();
        validCharacters.add(' ');
        validCharacters.add('1');
        validCharacters.add('2');
        validCharacters.add('3');
        validCharacters.add('4');
        validCharacters.add('5');
        validCharacters.add('6');
        validCharacters.add('7');
        validCharacters.add('8');
        validCharacters.add('9');
        validCharacters.add('A');
        validCharacters.add('B');
        validCharacters.add('C');
        validCharacters.add('D');
        validCharacters.add('E');
        validCharacters.add('F');

        if (initialState.length() != 16) {
            isValid = false;
        } else {
            for (int i = 0; i < initialState.length(); i++) {
                if (validCharacters.contains(initialState.charAt(i))) {
                    validCharacters.remove(initialState.charAt(i));
                } else {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }

    /**
     * Verifies the option passed by the user through the command line
     * @param option the option to be verified. Can be a integer or string.
     * @param searchMethod the search method to use which corresponds to
     *                     which options are allowed
     * @return a boolean indicating whether or not the option is valid
     */
    private static boolean verifyOptions(String searchMethod, String option) {
        boolean isValid = true;
        if (option.length() > 0) {
            if (searchMethod.equals("GBFS") || searchMethod.equals("AStar")) {
                if (!option.equals("h1") && !option.equals("h2")) {
                    isValid = false;
                }
            } else if (searchMethod.equals("DLS")) {
                if (!Character.isDigit(option.charAt(0))) {
                    isValid = false;
                }
            }
        } else {
            isValid = false;
        }
        return isValid;
    }
}