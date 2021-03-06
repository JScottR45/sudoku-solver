package sudoku_solver;

/*
* The Sudoku board column class. The board has nine Column instances, each of which contains an array
* that holds all of the Values in the column.
*/

class Column {
    /** The column number. */
    private int colID;
    /** The array containing all of the Values in this column. */
    private Value[] values;
    /** The length of a row, column, and quadrant. */
    private static final int LENGTH = 9;

    /** Initializes the column number and its Value array. */
    Column(int ID) {
        colID = ID;
        values = new Value[LENGTH];
    }

    /** Returns the Value in this Column indicated by INDEX. */
    Value getValue(int index) {
        return values[index];
    }

    /** Sets the INDEX of the values array to VAL. */
    void setValue(int index, Value val) {
        values[index] = val;
    }

    /** Returns true if this column contains NUM. Returns false otherwise. */
    boolean contains(int num) {
        for (int i = 0; i < LENGTH; i++) {
            if (values[i].getNum() == num) {
                return true;
            }
        }
        return false;
    }
}
