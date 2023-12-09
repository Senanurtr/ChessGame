public class Bishop extends Piece {

    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Cell start, Cell destination, Board board) {

        // Hedef hücrede aynı renkte başka bir taş varsa
        if (start.getPiece().isWhite() == destination.getPiece().isWhite()) {
            return false;
        }

        //Fil X'de ne kadar yol alıyorsa Y'de de o kadar yol alır onun için değişken
        int diffX = Math.abs(destination.getX() - start.getX());
        int diffY = Math.abs(destination.getY() - start.getY());

        // Filin çapraz hareketlerinin kontrolü
        if (diffX == diffY) {

            //hangi çapraza doğru gitmek istediğine göre x y ye eklenecek sayıyı belirlemek için
            int xDir = (destination.getX() - start.getX()) > 0 ? 1 : -1;
            int yDir = (destination.getY() - start.getY()) > 0 ? 1 : -1;
            int x = start.getX() + xDir;
            int y = start.getY() + yDir;

            // Yol boyunca herhangi bir taş var mı kontrol
            while (x != destination.getX() && y != destination.getY()) {
                if (board.getCell(x, y).getPiece() != null) {
                        return false; // Yolda bir taş varsa gidemez
                }
                x += xDir;
                y += yDir;
            }

            return true;
        }
        return false; // gitmek istediği konum çaprazında değilse gidemez
    }
}

/*
        // Hedef hücrede aynı renkte başka bir taş varsa
        // Fil X'de ne kadar yol alıyorsa Y'de de o kadar yol alır onun için değişken
        // Filin çapraz hareketlerinin kontrolü
        // hangi çapraza doğru gitmek istediğine göre x y ye eklenecek sayıyı belirlemek için
        // Yol boyunca herhangi bir taş var mı kontrol
        // Yolda bir taş varsa gidemez
        // gitmek istediği konum çaprazında değilse gidemez

 */