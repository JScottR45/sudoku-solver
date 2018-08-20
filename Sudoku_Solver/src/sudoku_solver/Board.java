package sudoku_solver;

/*
 * The Sudoku board class. It contains arrays of all of the rows, columns, and quadrants of the board.
 */

class Board {
    /** The rows of the Sudoku board. */
    private Row[] rows;
    /** The columns of the Sudoku board. */
    private Column[] columns;
    /** The quadrants of the Sudoku board. */
    private Quadrant[] quadrants;
    /** The length of a row, column, and quadrant. */
    private static final int LENGTH = 9;

    /** Initializes the board arrays. */
    Board() {
        rows = new Row[LENGTH];
        columns = new Column[LENGTH];
        quadrants = new Quadrant[LENGTH];
    }

    /** Returns the array containing all of the Row instances. */
    Row[] getRows() {
        return rows;
    }

    /** Returns the Row indicated by INDEX. */
    Row getRow(int index) {
        return rows[index];
    }

    /** Returns the array containing all of the Column instances. */
    Column[] getColumns() {
        return columns;
    }

    /** Returns the Column indicated by INDEX. */
    Column getColumn(int index) {
        return columns[index];
    }

    /** Returns the array containing all of the Quadrant instances. */
    Quadrant[] getQuadrants() {
        return quadrants;
    }

    /** Returns the Quadrant indicated by ROW and COLUMN. */
    Quadrant getQuadrant(int row, int column) {

        if (row < 3) {
            if (column < 3) {
                return quadrants[0];
            } else if (column < 6) {
                return quadrants[1];
            } else {
                return quadrants[2];
            }
        } else if (row < 6) {
            if (column < 3) {
                return quadrants[3];
            } else if (column < 6) {
                return quadrants[4];
            } else {
                return quadrants[5];
            }
        } else {
            if (column < 3) {
                return quadrants[6];
            } else if (column < 6) {
                return quadrants[7];
            } else {
                return quadrants[8];
            }
        }
    }

    /** Returns the Value located at ROW, COlUMN. */
    Value getValue (int row, int column) {
        return rows[row].getValue(column);
    }

    /** Sets the INDEX of the rows array to ROW. */
    void setRow(int index, Row row) {
        rows[index] = row;
    }

    /** Sets the INDEX of the columns array to COLUMN. */
    void setColumn(int index, Column column) {
        columns[index] = column;
    }

    /** Sets the INDEX of the quadrants array to QUADRANT. */
    void setQuadrant(int index, Quadrant quadrant) {
        quadrants[index] = quadrant;
    }
}
