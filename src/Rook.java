public class Rook extends Piece {

    public Rook(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Cell start, Cell destination, Board board) {

        // Hedef hücrede aynı renkte başka bir taş varsa
        if (start.getPiece().isWhite() == destination.getPiece().isWhite()) {
            return false;
        }

        int diffX = Math.abs(destination.getX() - start.getX());
        int diffY = Math.abs(destination.getY() - start.getY());

        // sadece x ve ya sadece y koordinatı değişeceği için biri sabit
        if ((diffX == 0 && diffY > 0) || (diffX > 0 && diffY == 0)) {
            //dikey hareketinde
            if (diffX == 0) {
                int yDir = (destination.getY() - start.getY()) > 0 ? 1 : -1;
                int y = start.getY() + yDir;

                // yol boyunca taş var mı
                while (y != destination.getY()) {
                    if (board.getCell(start.getX(), y).getPiece() != null) {
                        return false; // yolda taş varsa gidemez
                    }
                    y += yDir;
                }
            }
            // yatay hareket
            if (diffY == 0){
                int xDir = (destination.getX() - start.getX()) > 0 ? 1 : -1;
                int x = start.getX() + xDir;

                // yol boyunca taş var mı
                while (x != destination.getX()) {
                    if (board.getCell(x, start.getY()).getPiece() != null) {
                        return false; // yolda taş varsa gidemez
                    }
                    x += xDir;
                }
            }
        }
        return false;
    }
}
