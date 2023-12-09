public class King extends Piece{
    private boolean alive = true;
    private boolean castlingDone = false;   //  Eger sah hareket etmis ise oyuncu rok yapamamali.
    public King(boolean white){
        super(white);
    }


    public boolean hasCastlingDone(){
        return this.castlingDone;
    }
    public void setCastlingDone(boolean castlingDone){
        this.castlingDone = castlingDone;
    }

    //Hareket etmesi istenirse ancak rakip tas tarafindan yenme durumu olursa belirtilen noktaya hareket edememeli.
    //Daha sonra eklenecek.
    //Rok yapma durumu eklenecek.
    @Override
    public boolean canMove(Cell start, Cell destination, Board board){

        //  Sah X ve Y ekseninde en fazla 1er kare ilerleyebilmeli.
        if (Math.abs(start.getX() - destination.getX()) <= 1 && Math.abs(start.getY() - destination.getY()) <= 1){

            //  Eger hareket edecek tas beyazsa fakat hareket edecegi konumda beyaz tas varsa hareket edememeli.
            if (start.getPiece().isWhite() && destination.getPiece().isWhite()){
                return false;
            }
            else{
                return true;
            }

        }else {
            return false;
        }
    }
}