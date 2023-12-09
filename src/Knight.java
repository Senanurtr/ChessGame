public class Knight extends Piece {

    public Knight(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Cell start, Cell destination, Board board) {

        // Hedef hücrede aynı renkte başka bir taş varsa
        if (start.getPiece().isWhite() == destination.getPiece().isWhite()) {
            return false;
        }

        /*         (5,5)
          \x+-2  y+-1 \x+-1  y+-2
           (3,4)(3,6)  (4,4)(4,6)
           (7,4)(7,6)  (6,4)(6,6)
        **/
        int diffX = Math.abs(destination.getX() - start.getX());
        int diffY = Math.abs(destination.getY() - start.getY());

        // L şeklindeki hareketi kontrol eder
        if ((diffX == 1 && diffY == 2) || (diffX == 2 && diffY == 1)) {
            return true;
        }else
            return false;
    }
}