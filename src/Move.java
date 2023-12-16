public class Move {
    private Cell start;
    private Cell destination;
    private Piece movingPiece;
    private Player player;
    public Move(Cell start, Cell destination, Player player){
        this.start = start;
        this.destination = destination;
        this.player = player;
        this.movingPiece = start.getPiece();
    }
    public Cell getStart(){
        return this.start;
    }
    public Cell getDestination(){
        return this.destination;
    }



    //  Yapilan hamle sonucu Sah'in tehdit altinda kalip kalmayacagi durumu kontrol eder.
    //  Tehdit altinda kaliyorsa true dondurur.
    public boolean resultInThreat(Cell start, Cell destination, Board board, Player player){



        if (start.getPiece().canMove(start, destination, board)){

            destination.setPiece(start.getPiece());
            start.setPiece(null);
        }
        if (player.isWhiteSide()){

            Cell whiteKingsPosition = whiteKingsPosition(board);
            return whiteKingsPosition.getPiece() instanceof King &&
                    ((King) whiteKingsPosition.getPiece()).isWhiteUnderThreat(board);

        } else if (!player.isWhiteSide()) {
            Cell blackKingPosition = blackKingsPosition(board);
            return blackKingPosition.getPiece() instanceof King &&
                    (((King) blackKingPosition.getPiece()).isBlackUnderThreat(board));
        }
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
                if (board.getCell(x, y).getPiece() instanceof King && !board.getCell(x, y).getPiece().isWhite()) {
                    blackKingsPosition = board.getCell(x, y);
                    break;
                }
            }
        }
        return blackKingsPosition;
    }
}
