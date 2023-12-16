public class King extends Piece{
    private String type = "S";
    private boolean alive = true;
    private boolean castlingDone = false;   //  Eger sah hareket etmis ise oyuncu rok yapamamali.
    public King(boolean white){
        super(white);
    }
    public String getType(){
        return this.type;
    }


    public boolean hasCastlingDone(){
        return this.castlingDone;
    }
    public void setCastlingDone(boolean castlingDone){
        this.castlingDone = castlingDone;
    }
    public boolean canMove(Cell start, Cell destination, Board board){

        //  Sah X ve Y ekseninde en fazla 1er kare ilerleyebilmeli.
        if (Math.abs(start.getX() - destination.getX()) <= 1 && Math.abs(start.getY() - destination.getY()) <= 1){

            //  Hareket edecek tasin gidecegi konumda ayni renk tas var ise o konuma gidememeli.
            if (start.getPiece().isWhite() && destination.getPiece() != null && destination.getPiece().isWhite()){
                return false;
            }
            else{
                return true;
            }

            //  Sah beyaz ise rok yapma durumu.
        }else if(start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 1 && destination.getY() == 7) {

            return isCastlingMove(start, destination, board);
        }else if(start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 5 && destination.getY() == 7){

            return isCastlingMove(start, destination, board);


            //  Sah siyah ise rok yapma durumu.
        }else if (!start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 1 && destination.getY() == 0){

            return isCastlingMove(start, destination, board);
        } else if (!start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 5 && destination.getY() == 0) {

            return isCastlingMove(start, destination, board);
        }else
            return false;
    }


    //  Girilen parametrelere gore Sah'in yaptigi hareketin rok olup olmadigini kontrol eden fonksiyon.
    //  Tehdit altinda olma durumu daha sonra eklenecek.
    public boolean isCastlingMove(Cell start, Cell destination, Board board){
        if (start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 1 && destination.getY() == 7){

            return board.getCell(2, 7).getPiece() == null && board.getCell(0, 7).getPiece() instanceof Rook
                    && !((Rook) board.getCell(0, 7).getPiece()).hasCastlingDone();

        } else if (start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 5 && destination.getY() == 7) {

            return board.getCell(4, 7).getPiece() == null && board.getCell(5, 7).getPiece() == null &&
                    board.getCell(6, 7).getPiece() == null && board.getCell(7, 7).getPiece() instanceof Rook &&
                    !((Rook) board.getCell(7, 7).getPiece()).hasCastlingDone();

        } else if (!start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 1 && destination.getY() == 0) {

            return board.getCell(2, 0).getPiece() == null && board.getCell(0, 0).getPiece() instanceof Rook
                    && !((Rook) board.getCell(0, 0).getPiece()).hasCastlingDone();

        }else if(!start.getPiece().isWhite() && !this.hasCastlingDone() && destination.getX() == 5 && destination.getY() == 0){

            return board.getCell(4, 0).getPiece() == null && board.getCell(5, 0).getPiece() == null &&
                    board.getCell(6, 0).getPiece() == null && board.getCell(7, 0).getPiece() instanceof Rook &&
                    !((Rook) board.getCell(7, 0).getPiece()).hasCastlingDone();

        }else
            return false;
    }

    //  Beyaz Sah'in yerini bulan fonksiyon:
    public Cell whiteKingsPosition(Board board){
        Cell whiteKingsPosition = new Cell(0, 0, null);
        for (int y = 0; y <= 7; y++){
            for (int x = 0; x <=7; x++){
                if (board.getCell(x, y).getPiece() instanceof King && board.getCell(x, y).getPiece().isWhite()) {
                    whiteKingsPosition = board.getCell(x, y);
                    break;
                }
            }
        }
        return whiteKingsPosition;
    }


    //  Siyah Sah'in yerini bulan fonksiyon:
    public Cell blackKingsPosition(Board board){
        Cell blackKingsPosition = new Cell(0, 0, null);
        for (int y = 0; y <= 7; y++){
            for (int x = 0; x <=7; x++){
                if (board.getCell(x, y).getPiece() instanceof King && board.getCell(x, y).getPiece().isWhite()) {
                    blackKingsPosition = board.getCell(x, y);
                    break;
                }
            }
        }
        return blackKingsPosition;
    }


    //  Beyaz Sah'in tehdit altinda olup olmadigini kontrol eden fonksiyon:
    public boolean isWhiteUnderThreat(Board board){

        //  Beyaz Sah'in yerinin bulunmasi:
        Cell whiteKingsPosition = whiteKingsPosition(board);

        //  Sol üstten baslayarak tum hucreleri kontrol eder ve kontrol ettigi hucredeki tas siyahsa
        //  Sah'in hucresine gidip gidemeyecegini kontrol eder.Gidebiliyorsa true dondurur.
        for (int y = 0; y <= 7; y++){
            for (int x = 0; x <= 7; x++){
                if (board.getCell(x, y).getPiece() != null && !board.getCell(x, y).getPiece().isWhite() &&
                        board.getCell(x, y).getPiece().canMove(board.getCell(x, y), whiteKingsPosition, board)){
                    return true;
                }
            }
        }
        return false;
    }

    //  Siyah Sah'in tehdit altinda olup olmadigini kontrol eden fonksiyon:
    public boolean isBlackUnderThreat(Board board){

        //  Sah'in yerinin bulunmasi:
        Cell blackKingsPosition = blackKingsPosition(board);

        //  Sol üstten baslayarak tum hucreleri kontrol eder ve kontrol ettigi hucredeki tas beyazsa
        //  Sah'in hucresine gidip gidemeyecegini kontrol eder.Gidebiliyorsa true dondurur.
        for (int y = 0; y <= 7; y++){
            for (int x = 0; x <= 7; x++){
                if (board.getCell(x, y).getPiece() != null && board.getCell(x, y).getPiece().isWhite() &&
                        board.getCell(x, y).getPiece().canMove(board.getCell(x, y), blackKingsPosition, board)){
                    return true;
                }
            }
        }
        return false;
    }
}