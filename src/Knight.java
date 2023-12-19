public class Knight extends Piece {
    private String type = "K";
    private final String path;
    public Knight(boolean white) {
        super(white);
        if (isWhite()){
            path="src/images/wn.png";
        }else path="src/images/bn.png";
    }
    @Override
    public String getType(){
        return this.type;
    }

    @Override
    public boolean canMove(Cell start, Cell destination, Board board) {

        if (destination.getPiece() == null){
            return true;
        }

        // Hedef hücrede aynı renkte başka bir taş varsa
        if (start.getPiece().isWhite() == destination.getPiece().isWhite()) {
            return false;
        }

        // L şeklindeki hareketi kontrol eder
        if ((Math.abs(destination.getX() - start.getX()) == 1 && Math.abs(destination.getY() - start.getY()) == 2)
                || (Math.abs(destination.getX() - start.getX()) == 2 && Math.abs(destination.getY() - start.getY()) == 1)) {
            return true;
        }else
            return false;
    }
}