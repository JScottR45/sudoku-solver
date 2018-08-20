package sudoku_solver;

/*
* This class represents all of the values that fill the Sudoku board. If a Value is initialized to have
* a numeric value 1-9, then it represents a given number in the Sudoku puzzle and will not change. If it
* is initialized to have a numeric value of zero, then it represents a blank square in the Sudoku board.
* In this case, it will also have an array, which based on its location in the board, will contain all of
* the possible numeric values in the range 1-9 that it can take on.
*/

import java.util.LinkedList;

class Value {
    /** The numeric value of this Value. */
    private int num;
    /** The row in which this Value is located. */
    private int row;
    /** The column in which this Value is located. */
    private int column;
    /** The LinkedList containing all possible numeric values that this Value can
     *  can take on. It is null if this Value is initialized to have a numeric
     *  value other than zero. */
    private LinkedList<Integer> numList;
    /** The length of a row, column, and quadrant. */
    private static final int LENGTH = 9;

    /** Initializes this Value's numeric value to NUM, which is a number 1-9.
     *  The numList is thus set to null. */
    Value(int num, int row, int column) {
        this.num = num;
        this.row = row;
        this.column = column;
        numList = null;
    }

    /** Initializes this Value's numeric value to zero. Initializes its numList and
     * fills it with integers 1-9. */
    Value(int row, int column) {
        num = 0;
        this.row = row;
        this.column = column;
        numList = new LinkedList<>();

        for (int i = 0; i < LENGTH; i++) {
            numList.add(i + 1);
        }
    }

    /** Returns the numeric value of this Value. */
    int getNum() {
        return num;
    }

    /** Returns the row number in which this Value is located. */
    int getRow() {
        return row;
    }

    /** Returns the column number in which this Value is located. */
    int getColumn() {
        return column;
    }

    /** Returns the list of all possible numeric values that this Value can take on. */
    LinkedList<Integer> getNumList() {
        return numList;
    }

    /** Sets the numeric value of this Value to NUM. */
    void setNum(int num) {
        this.num = num;
    }

    /** Sets the numList of this Value to NUMLIST. */
    void setNumList(LinkedList<Integer> numList) {
        this.numList = numList;
    }
}
