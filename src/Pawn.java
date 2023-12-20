import javax.swing.*;

public class Pawn extends Piece{
    private String type = "P";
    private final String path;
    @Override
    public Icon getPath() {
        return new ImageIcon(path);
    }
    private boolean moved = false;  //Eger hareket etmemis ise ilk hareketinde iki kare ilerleyebilme opsiyonu olmali
    public Pawn(boolean white){
        super(white);
        if (isWhite()){
            path="src/images/wp.png";
        }else path="src/images/bp.png";
    }

    public boolean hasMoved(){
        return this.moved;
    }
    public void setMoved(boolean moved){
        this.moved = moved;
    }
    public String getType(){
        return this.type;
    }


    //  Piyonun secili kareye hareket edip edemeyecegini kontrol eden fonksiyon.
    @Override
    public boolean canMove(Cell start, Cell destination, Board board) {


        //  Beyazsa bir kare ilerleme durumu:
        if (start.getPiece().isWhite() && (destination.getY() - start.getY() == -1) && destination.getX() == start.getX()){
            return destination.getPiece() == null;

            //  Siyahsa bir kare ilerleme durumu:
        } else if (!start.getPiece().isWhite() && (destination.getY() - start.getY() == 1) && start.getX() == destination.getX()) {
            return destination.getPiece() == null;

            //  Beyaz icin carprazindaki tasi yeme durumu:
        } else if (start.getPiece().isWhite() && Math.abs(destination.getX() - start.getX()) == 1 && destination.getY() - start.getY() == -1) {
            return !destination.getPiece().isWhite();

            //  Siyah icin carprazindaki tasi yeme durumu:
        }else if (!start.getPiece().isWhite() && Math.abs(destination.getX()) - start.getX() == 1 && destination.getY() - start.getY() == 1){
            return destination.getPiece().isWhite();

            //  Beyaz icin iki kare ilerleme durumu:
        }else if (!hasMoved() && start.getPiece().isWhite() && destination.getY() - start.getY() == -2 && destination.getX() == start.getX()){
            return board.getCell(start.getX(), start.getY() - 1).getPiece() == null && board.getCell(start.getX(), start.getY() - 2).getPiece() == null;

            //  Siyah icin iki kare ilerleme durumu:
        }else if (!hasMoved() && !start.getPiece().isWhite() && destination.getY() - start.getY() == 2 && destination.getX() == start.getX()){
            return board.getCell(start.getX(), start.getY() + 1).getPiece() == null && board.getCell(start.getX(), start.getY() + 2).getPiece() == null;

        }
        return false;
    }
}