package sudoku_solver;

/*
* The Sudoku board quadrant class. The board has nine Quadrant instances, each of which contains an array
* that holds all of the Values in the quadrant.
*/

class Quadrant {
    /** The quadrant number. */
    private int quadID;
    /** The array containing all of the Values in this quadrant. */
    private Value[] values;
    /** The length of a row, column, and quadrant. */
    private static final int LENGTH = 9;

    /** Initializes the quadrant number and its Value array. */
    Quadrant(int ID) {
        quadID = ID;
        values = new Value[LENGTH];
    }

    /** Returns the Value in this Quadrant indicated by INDEX. */
    Value getValue(int index) {
        return values[index];
    }

    /** Sets the INDEX of the values array to VAL. */
    void setValue(int index, Value val) {
        values[index] = val;
    }

    /** Returns true if this quadrant contains NUM. Returns false otherwise. */
    boolean contains(int num) {
        for (int i = 0; i < LENGTH; i++) {
            if (values[i].getNum() == num) {
                return true;
            }
        }
        return false;
    }
}
