package yokai.groupe;

public class Position {

    private int row;
    private int column;

    public Position(int row, int column) {
        this.column = column;
        this.row = row;
    }


      public String toString() {
        return row+";"+column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
