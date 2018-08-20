package sudoku_solver;

/*
 * The controller class for the Sudoku GUI. This class gathers all of the user inputs from the GUI, uses the Solver
 * class to solve the puzzle, and then updates the GUI to display the solution.
 */

import java.util.LinkedList;
import javafx.scene.control.TextField;

public class Controller {

    /**
     *  The variables representing the values in each square of the Sudoku board.
     */
    public TextField data1;      public TextField data2;      public TextField data3;      public TextField data4;
    public TextField data5;      public TextField data6;      public TextField data7;      public TextField data8;
    public TextField data9;      public TextField data10;     public TextField data11;     public TextField data12;

    public TextField data13;     public TextField data14;     public TextField data15;     public TextField data16;
    public TextField data17;     public TextField data18;     public TextField data19;     public TextField data20;
    public TextField data21;     public TextField data22;     public TextField data23;     public TextField data24;

    public TextField data25;     public TextField data26;     public TextField data27;     public TextField data28;
    public TextField data29;     public TextField data30;     public TextField data31;     public TextField data32;
    public TextField data33;     public TextField data34;     public TextField data35;     public TextField data36;

    public TextField data37;     public TextField data38;     public TextField data39;     public TextField data40;
    public TextField data41;     public TextField data42;     public TextField data43;     public TextField data44;
    public TextField data45;     public TextField data46;     public TextField data47;     public TextField data48;

    public TextField data49;     public TextField data50;     public TextField data51;     public TextField data52;
    public TextField data53;     public TextField data54;     public TextField data55;     public TextField data56;
    public TextField data57;     public TextField data58;     public TextField data59;     public TextField data60;

    public TextField data61;     public TextField data62;     public TextField data63;     public TextField data64;
    public TextField data65;     public TextField data66;     public TextField data67;     public TextField data68;
    public TextField data69;     public TextField data70;     public TextField data71;     public TextField data72;

    public TextField data73;     public TextField data74;     public TextField data75;     public TextField data76;
    public TextField data77;     public TextField data78;     public TextField data79;     public TextField data80;
    public TextField data81;

    /** The 2D array containing all of the Sudoku board values. */
    private TextField[][] data;

    /** The length of a row, column, and quadrant. */
    private static final int LENGTH = 9;

    /** Creates and fills a Board instance from user input and solves the puzzle. */
    public void solve() {
        data = new TextField[][]{{data1, data2, data3, data4, data5, data6, data7, data8, data9},
                        {data10, data11, data12, data13, data14, data15, data16, data17, data18},
                        {data19, data20, data21, data22, data23, data24, data25, data26, data27},
                        {data28, data29, data30, data31, data32, data33, data34, data35, data36},
                        {data37, data38, data39, data40, data41, data42, data43, data44, data45},
                        {data46, data47, data48, data49, data50, data51, data52, data53, data54},
                        {data55, data56, data57, data58, data59, data60, data61, data62, data63},
                        {data64, data65, data66, data67, data68, data69, data70, data71, data72},
                        {data73, data74, data75, data76, data77, data78, data79, data80, data81}};

        int val = -1;
        boolean errorOccurred = false;
        Board board = new Board();
        LinkedList<TextField> generalErrors = new LinkedList<>();
        LinkedList<TextField> repeatErrors = new LinkedList<>();
        Solver solver;
        Row currRow;
        TextField[] currInput;
        TextField currData;
        Value currValue;

        for (int i = 0; i < LENGTH; i++) {
            currRow = new Row(i);
            currInput = data[i];

            for (int j = 0; j < LENGTH; j++) {
                currData = currInput[j];

                if (currData.getCharacters().length() == 0) {
                    currValue = new Value(i, j);
                } else if (currData.getCharacters().length() > 1) {
                    generalErrors.add(currData);

                    currValue = new Value(i, j);
                } else {
                    val = Character.getNumericValue(currData.getCharacters().charAt(0));

                    if (val < 1 || val > 9) {
                        generalErrors.add(currData);
                        currValue = new Value(i, j);
                    } else {
                        currValue = new Value(val, i, j);
                    }
                }
                currRow.setValue(j, currValue);
            }
            board.setRow(i, currRow);
        }

        if (generalErrors.size() > 0) {
            AlertBox.display("Only numbers 1-9 are allowed.", 250, 150);
            errorOccurred = true;

            for (TextField data : generalErrors) {
                if (data.getCharacters().length() > 0) {
                    data.clear();
                }
            }
        }

        solver = new Solver(board);

        if (repeats(solver.getBoard(), repeatErrors)) {
            AlertBox.display("No repeated values are allowed in any row, column, or quadrant.", 450, 150);
            errorOccurred = true;

            for (TextField data : repeatErrors) {
                if (data.getCharacters().length() > 0) {
                    data.clear();
                }
            }
        }

        if (errorOccurred) {
            return;
        }

        try {
            solver.solvePuzzle();
            updateGUI(solver.getBoard());
        } catch (NullPointerException err) {
            AlertBox.display("This Sudoku puzzle has no solution.", 300, 150);
        }
    }

    /** Returns true if BOARD has repeated values in any of its rows, columns, or quadrants. In this
     *  case, the repeated inputs are stored in REPEATERRORS. Returns false otherwise. */
    private boolean repeats(Board board, LinkedList<TextField> repeatErrors) {
        Row[] rows = board.getRows();
        Column[] columns = board.getColumns();
        Quadrant[] quadrants = board.getQuadrants();

        boolean rowRepeats = rowRepeats(rows, repeatErrors);
        boolean columnRepeats = columnRepeats(columns, repeatErrors);
        boolean quadrantRepeats = quadrantRepeats(quadrants, repeatErrors);

        return rowRepeats || columnRepeats || quadrantRepeats;
    }

    /** Returns true if any of the rows in ROWS have repeated values. If so, the repeated values are
     *  stored in REPEATERRORS. Returns false otherwise. */
    private boolean rowRepeats(Row[] rows, LinkedList<TextField> repeatErrors) {
        int[] occurrences = new int[LENGTH + 1];
        int val;
        boolean result = false;
        Row currRow;
        Value currValue;

        for (int i = 0; i < LENGTH; i++) {
            currRow = rows[i];

            for (int j = 0; j < LENGTH; j++) {
                val = currRow.getValue(j).getNum();

                if (val > 0 && val <= LENGTH) {
                    occurrences[val] += 1;
                }
            }

            for (int k = 1; k <= LENGTH; k++) {
                if (occurrences[k] > 1) {

                    result = true;
                    for (int n = 0; n < LENGTH; n++) {
                        currValue = currRow.getValue(n);

                        if (currValue.getNum() == k) {
                            repeatErrors.add(data[currValue.getRow()][currValue.getColumn()]);
                        }
                    }
                }
            }
            occurrences = new int[LENGTH + 1];
        }
        return result;
    }

    /** Returns true if any of the columns in COLUMNS have repeated values. If so, the repeated values are
     *  stored in REPEATERRORS. Returns false otherwise. */
    private boolean columnRepeats(Column[] columns, LinkedList<TextField> repeatErrors) {
        int[] occurrences = new int[LENGTH + 1];
        int val;
        boolean result = false;
        Column currColumn;
        Value currValue;

        for (int i = 0; i < LENGTH; i++) {
            currColumn = columns[i];

            for (int j = 0; j < LENGTH; j++) {
                val = currColumn.getValue(j).getNum();

                if (val > 0 && val <= LENGTH) {
                    occurrences[val] += 1;
                }
            }

            for (int k = 1; k <= LENGTH; k++) {
                if (occurrences[k] > 1) {

                    result = true;
                    for (int n = 0; n < LENGTH; n++) {
                        currValue = currColumn.getValue(n);

                        if (currValue.getNum() == k) {
                            repeatErrors.add(data[currValue.getRow()][currValue.getColumn()]);
                        }
                    }
                }
            }
            occurrences = new int[LENGTH + 1];
        }
        return result;
    }

    /** Returns true if any of the quadrants in QUADRANTS have repeated values. If so, the repeated values are
     *  stored in REPEATERRORS. Returns false otherwise. */
    private boolean quadrantRepeats(Quadrant[] quadrants, LinkedList<TextField> repeatErrors) {
        int[] occurrences = new int[LENGTH + 1];
        int val;
        boolean result = false;
        Quadrant currQuadrant;
        Value currValue;

        for (int i = 0; i < LENGTH; i++) {
            currQuadrant = quadrants[i];

            for (int j = 0; j < LENGTH; j++) {
                val = currQuadrant.getValue(j).getNum();

                if (val > 0 && val <= LENGTH) {
                    occurrences[val] += 1;
                }
            }

            for (int k = 1; k <= LENGTH; k++) {
                if (occurrences[k] > 1) {

                    result = true;
                    for (int n = 0; n < LENGTH; n++) {
                        currValue = currQuadrant.getValue(n);

                        if (currValue.getNum() == k) {
                            repeatErrors.add(data[currValue.getRow()][currValue.getColumn()]);
                            currValue.setNum(0);
                        }
                    }
                }
            }
            occurrences = new int[LENGTH + 1];
        }
        return result;
    }

    /** Fills in all of the blank squares of the Sudoku GUI once the puzzle has been solved. */
    private void updateGUI(Board board) {
        Row currRow;
        TextField[] currInput;
        TextField currData;
        Value currValue;

        for (int i = 0; i < LENGTH; i++) {
            currRow = board.getRow(i);
            currInput = data[i];

            for (int j = 0; j < LENGTH; j++) {
                currData = currInput[j];
                currValue = currRow.getValue(j);
                currData.setText(Integer.toString(currValue.getNum()));
            }
        }
    }

    /** Resets the Sudoku GUI to all blank squares to allow the user to enter another puzzle
     *  to be solved. */
    public void reset() {
        TextField[] currInput;
        TextField currData;

        for (int i = 0; i < LENGTH; i++) {
            currInput = data[i];

            for (int j = 0; j < LENGTH; j++) {
                currData = currInput[j];

                if (currData.getCharacters().length() > 0) {
                    currData.clear();
                }
            }
        }
    }
}
