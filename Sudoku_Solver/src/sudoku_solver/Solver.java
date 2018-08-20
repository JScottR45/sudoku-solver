package sudoku_solver;

/*
 * The driver class of the Sudoku Solver program. This class creates and initializes the Sudoku board and
 * then solves the puzzle.
 */

import java.util.LinkedList;

public class Solver {
    /** The length of a row, column, and quadrant. */
    private final int LENGTH = 9;
    /** The Sudoku board. */
    private Board board;
    /** LinkedList containing the states of the board before a guess is made. Used for
     *  undo purposes. */
    private LinkedList<Board> boardStates;
    /** LinkedList containing the Values whose numeric values were guessed. Listed in the order
     *  in which they were guessed. */
    private LinkedList<Value> guessValues;
    /** Number of board states saved in boardStates. */
    private int numBoardStates;
    /** Equals 1 when a blank square has been filled and equals 0 when the value for a
     *  blank square needs to be guessed. */
    private int boardChanged;

    /** Initializes this Sudoku Solver using BOARD. */
    Solver(Board board) {
        this.board = board;
        boardStates = new LinkedList<>();
        guessValues = new LinkedList<>();
        boardStates.add(null);
        guessValues.add(null);
        numBoardStates = 0;
        boardChanged = 0;

        initializeColumns(board);
        initializeQuadrants(board);
        updateValueLists();
    }

    /** Returns the Sudoku board. */
    Board getBoard() {
        return board;
    }

    /** Creates all of the Column instances and fills each with the appropriate Values
     *  from the Row instances. PLaces each Column into BOARD. */
    private void initializeColumns(Board board) {
        Row[] rows = board.getRows();
        Column column;

        for (int i = 0; i < LENGTH; i++) {
            column = new Column(i);

            for (int j = 0; j < LENGTH; j++) {
                column.setValue(j, rows[j].getValue(i));
            }
            board.setColumn(i, column);
        }
    }

    /** Creates all of the Quadrant instances and fills each with the appropriate Values
     *  from the Row instances. Places each Quadrant into BOARD. */
    private void initializeQuadrants(Board board) {
        int row = 0;
        int quadID = 0;
        int quadIDOffset = 0;
        int index = 0;
        int start = 0;
        int limit = 3;
        Row[] rows = board.getRows();
        Quadrant quadrant = new Quadrant(quadID);

        while (true) {
            if (row == LENGTH && (limit + 3) > LENGTH) {
                board.setQuadrant(quadID, quadrant);
                break;
            }

            if (row > 0 && (row % 3) == 0) {
                board.setQuadrant(quadID, quadrant);
                quadID += 3;
                quadrant = new Quadrant(quadID);
                index = 0;
            }

            if (row == LENGTH) {
                row = 0;
                start += 3;
                limit += 3;
                quadIDOffset += 1;
                quadID = quadIDOffset;
                continue;
            }

            for (int i = start; i < limit; i++) {
                quadrant.setValue(index, rows[row].getValue(i));
                index += 1;
            }
            row += 1;
        }
    }

    /** Scans through each row of the board and calls setValues on any zero Value
     *  to update its list of possible numbers it could take on. */
    private void updateValueLists() {
        Row[] rows = board.getRows();

        for (int i = 0; i < LENGTH; i++) {
            Row currRow = rows[i];

            for (int j = 0; j < LENGTH; j++) {
                Value currValue = currRow.getValue(j);

                if (currValue.getNum() == 0) {
                    setValues(currValue, i, j);
                }
            }
        }
    }

    /** Removes all numbers in VALUE's numList that appear in either the row, column,
     *  or quadrant in which VALUE resides. The row, column, and quadrant are found using
     *  ROW and COLUMN. */
    private void setValues(Value value, int row, int column) {
        int currNum;
        LinkedList<Integer> numList = value.getNumList();
        Row currRow = board.getRow(row);
        Column currColumn = board.getColumn(column);
        Quadrant currQuadrant = board.getQuadrant(row, column);

        for (int i = 0; i < numList.size(); i++) {
            currNum = numList.get(i);

            if (currRow.contains(currNum) || currColumn.contains(currNum)
                    || currQuadrant.contains(currNum)) {
                numList.remove(i);
                i--;
            }
        }
    }

    /** Scans through each row of the Sudoku board and changes blank squares to the correct
     *  values based on the output of changeValue, which is called on every zero Value.
     *  (zero Values represent blank squares) */
    void solvePuzzle() {
        int changeValue;
        Row[] rows = board.getRows();
        Row currRow;
        Value currValue;

        for (int i = 0; i < LENGTH; i++) {
            currRow = rows[i];

            for (int j = 0; j < LENGTH; j++) {
                currValue = currRow.getValue(j);

                if (currValue.getNum() == 0) {
                    changeValue = changeValue(currValue, i, j);

                    if (changeValue > 0) {
                        currValue.setNum(changeValue);
                        boardChanged = 1;
                    } else if (changeValue == -1) {
                        backtrack();
                        updateValueLists();
                        rows = board.getRows();
                        i = 0;
                        break;
                    }
                }
            }
        }

        if (!puzzleSolved()) {
            if (boardChanged == 0) {
                numBoardStates += 1;
                copyBoardState(board);
                makeGuess();
                updateValueLists();
                solvePuzzle();
            } else {
                boardChanged = 0;
                updateValueLists();
                solvePuzzle();
            }
        }
    }


    /** Returns the correct value that must fill the blank square represented by VALUE
     *  if it is the only number that can legally fill the square. Returns -1 otherwise. */
    private int changeValue(Value value, int row, int col) {
        int val;
        LinkedList<Integer> numList = value.getNumList();
        Row currRow = board.getRow(row);
        Column currColumn = board.getColumn(col);
        Quadrant currQuadrant = board.getQuadrant(row, col);

        if (numList.size() == 1) {
            val = numList.getFirst();

            if (numBoardStates > 0 && (currRow.contains(val) || currColumn.contains(val) || currQuadrant.contains(val))) {
                return -1;
            } else {
                return val;
            }
        } else {
            return 0;
        }
    }

    /** Returns true if the Sudoku puzzle has been solved (i.e. all of the blank squares have
     *  been filled with the correct numbers). */
    private boolean puzzleSolved() {
        Row[] rows = board.getRows();
        Row currRow;

        for (int i = 0; i < LENGTH; i++) {
            currRow = rows[i];

            for (int j = 0; j < LENGTH; j++) {
                if (currRow.getValue(j).getNum() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Creates a clone of the current board and stores it at the end of boardStates. */
    private void copyBoardState(Board board) {
        Board copyBoard = new Board();
        Value copyValue;
        Value currValue;
        LinkedList<Integer> numList;
        LinkedList<Integer> copyNumList;
        Row copyRow;
        Row currRow;

        for (int i = 0; i < LENGTH; i++) {
            currRow = board.getRow(i);
            copyRow = new Row(i);

            for (int j = 0; j < LENGTH; j++) {
                currValue = currRow.getValue(j);

                if (currValue.getNum() > 0) {
                    copyValue = new Value(currValue.getNum(), i, j);
                } else {
                    copyValue = new Value(i, j);
                    numList = currValue.getNumList();
                    copyNumList = new LinkedList<>();

                    for (Integer num : numList) {
                        copyNumList.add(num);
                    }
                    copyValue.setNumList(copyNumList);
                }
                copyRow.setValue(j, copyValue);
            }
            copyBoard.setRow(i, copyRow);
        }

        initializeColumns(copyBoard);
        initializeQuadrants(copyBoard);
        boardStates.add(copyBoard);
    }

    /** Guesses the numeric value for a Value with the smallest list of possible
     *  values which it could take on. */
    private void makeGuess() {
        int guess;
        Value guessValue = findShortestNumList();

        if (guessValue.getNumList().size() == 0) {
            numBoardStates -= 1;
            boardStates.pollLast();
            backtrack();
        } else {
            guessValues.add(guessValue);
            guess = guessValue.getNumList().pollFirst();
            guessValue.setNum(guess);
        }
    }

    /** Returns the Value with the shortest numList. */
    private Value findShortestNumList() {
        int min = LENGTH;
        int currSize;
        Row[] rows = board.getRows();
        Row currRow;
        Value currValue;
        Value guessValue = null;

        for (int i = 0; i < LENGTH; i++) {
            currRow = rows[i];

            for (int j = 0; j < LENGTH; j++) {
                currValue = currRow.getValue(j);

                if (currValue.getNum() == 0) {
                    currSize = currValue.getNumList().size();

                    if (currSize == 2) {
                        return currValue;
                    } else if (currSize < min) {
                        min = currSize;
                        guessValue = currValue;
                    }
                }
            }
        }
        return guessValue;
    }

    /** Resets the Sudoku board to a previous state to allow another guess to be made. Backtrack is called
     *  when an incorrect guess is made and the program reaches a point where it cannot legally fill
     *  another empty square. */
    private void backtrack() {
        int guess;
        int length;
        Board prevBoardState;
        Value currValue;
        Value prevStateValue;
        LinkedList<Integer> currNumList;

        currValue = guessValues.get(numBoardStates);
        currNumList = currValue.getNumList();
        length = currNumList.size();

        while (length == 0) {
            boardStates.pollLast();
            guessValues.pollLast();
            numBoardStates -= 1;
            currValue = guessValues.get(numBoardStates);
            currNumList = currValue.getNumList();
            length = currNumList.size();
        }

        guess = currNumList.pollFirst();
        prevBoardState = boardStates.pollLast();
        prevStateValue = prevBoardState.getValue(currValue.getRow(), currValue.getColumn());
        guessValues.pollLast();

        if (numBoardStates == 1 && length == 1) {
            prevStateValue.setNum(guess);
            board = prevBoardState;
            numBoardStates = 0;
        } else {
            copyBoardState(prevBoardState);
            prevStateValue.setNum(guess);
            prevStateValue.setNumList(currNumList);
            guessValues.add(prevStateValue);
            board = prevBoardState;
        }
    }
}
