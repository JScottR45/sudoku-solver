package sudoku_solver;

/*
* The Sudoku board row class. The board has nine Row instances, each of which contains an array
* that holds all of the Values in the row.
*/

class Row {
    /** The row number. */
    private int rowID;
    /** The array containing all of the Values in this row. */
    private Value[] values;
    /** The length of a row, column, and quadrant. */
    private static final int LENGTH = 9;

    /** Initializes the row number and its Value array. */
    Row(int ID) {
        rowID = ID;
        values = new Value[LENGTH];
    }

    /** Returns the Value in this Row indicated by INDEX. */
    Value getValue(int index) {
        return values[index];
    }

    /** Sets the INDEX of the values array to VAL. */
    void setValue(int index, Value val) {
        values[index] = val;
    }

    /** Returns true if this row contains NUM. Returns false otherwise. */
    boolean contains(int num) {
        for (int i = 0; i < LENGTH; i++) {
            if (values[i].getNum() == num) {
                return true;
            }
        }
        return false;
    }
}
