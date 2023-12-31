public class Game {
    private Player[] players = new Player[2];
    private Board board = new Board();
    private Player currentTurn;
    private GameStatus status;


    public Player getCurrentTurn(){
        return this.currentTurn;
    }
    public GameStatus getStatus(){
        return this.status;
    }
    public void setStatus(GameStatus status){
        this.status = status;
    }
    public void initialize(Player player1, Player player2){
        players[0] = player1;
        players[1] = player2;

        board.resetBoard();

        if (player1.isWhiteSide()){
            this.currentTurn = player1;
        }else{
            this.currentTurn = player2;
        }
        setStatus(GameStatus.ACTIVE);
    }
    public Board getBoard(){
        return this.board;
    }

    public boolean makeMove(Move move, Player player){
        Piece selectedPiece = move.getStart().getPiece();
        Piece selectedRook;

        //  Secili hucrede tas yoksa hamle yapilamaz.
        if (selectedPiece == null){
            System.out.println("Secilen hucrede tas yok");
            return false;
        }

        //  Sirasi gelen kisi mi hamle yapiyor?
        if (player != currentTurn){
            System.out.println("Sirasi gelen kisi hamle yapmiyor");
            return false;
        }

        //   RecentlyMoved false olması

        if (currentTurn.isWhiteSide()){
            for (int x = 0; x <= 7; x++){
                if (board.getCell(x, 4).getPiece() instanceof Pawn && ((Pawn) board.getCell(x, 4).getPiece()).isRecentlyMoved()){
                    ((Pawn) board.getCell(x, 4).getPiece()).setRecentlyMoved(false);
                }
            }
        }else {
            for (int x = 0; x <= 7; x++){
                if (board.getCell(x, 3).getPiece() instanceof Pawn && ((Pawn) board.getCell(x, 3).getPiece()).isRecentlyMoved()){
                    ((Pawn) board.getCell(x, 3).getPiece()).setRecentlyMoved(false);
                }
            }
        }

        //  Secili tas sirasi gelen kisinin tasi mi?
        if (selectedPiece.isWhite() != currentTurn.isWhiteSide()){
            System.out.println("Sirasi gelen kisi kendi taslarini oynamiyor");
            return false;
        }

        //  Secili tas belirtilen hamleyi yapabilir mi?
        if (!move.isEnPassant(move.getStart(), move.getDestination(), board)){
            if (!selectedPiece.canMove(move.getStart(), move.getDestination(), board)){
                System.out.println("Secili tas istenilen konuma gidemez");

                return false;
            }

        }

        //  Tas alindi mi?
        Piece destinationPiece = move.getDestination().getPiece();
        if (destinationPiece != null){

            destinationPiece.setAlive(false);
            System.out.println("taş alındı");
        }

        //  Yapilan hamle sonucu hamle yapan kisinin Sah'i tehdit altinda kaliyor mu?
        if (move.resultInThreat(move.getStart(), move.getDestination(), board, currentTurn)){
            System.out.println("yapılan hamle sonucu şah tehdit altında kalıyor");
            return false;
        }


        //  Yapilan hamle Rok ise
        //  Beyaz icin:
        if (selectedPiece instanceof King &&
                ((King) selectedPiece).isCastlingMove(move.getStart(), move.getDestination(), board)){

            if (move.getStart().getPiece().isWhite() && move.getDestination().getX() == 2){
                //  Sahin hareket etmesi
                move.getDestination().setPiece(selectedPiece);
                move.getStart().setPiece(null);

                selectedRook = board.getCell(0, 7).getPiece();

                //  Kalenin hareket etmesi
                board.getCell(0, 7).setPiece(null);
                board.getCell(3, 7).setPiece(selectedRook);
                System.out.println("Beyaz uzun rok yaptı");

            } else if (move.getStart().getPiece().isWhite() && move.getDestination().getX() == 6) {
                //  Sahin hareket etmesi
                move.getDestination().setPiece(selectedPiece);
                move.getStart().setPiece(null);

                selectedRook = board.getCell(7, 7).getPiece();

                //  Kalenin hareket etmesi
                board.getCell(7, 7).setPiece(null);
                board.getCell(5, 7).setPiece(selectedRook);
                System.out.println("Beyaz kısa rok yaptı");



                //  Siyah Tas icin
                //  Sah'in hareket etmesi
            } else if (!move.getStart().getPiece().isWhite() && move.getDestination().getX() == 2) {
                //  Sahin hareket etmesi
                move.getDestination().setPiece(selectedPiece);
                move.getStart().setPiece(null);

                selectedRook = board.getCell(0, 0).getPiece();

                //  Kalenin hareket etmesi
                board.getCell(0, 0).setPiece(null);
                board.getCell(3, 0).setPiece(selectedRook);
                System.out.println("Siyah kısa rok yaptı");


            } else if (!move.getStart().getPiece().isWhite() && move.getDestination().getX() == 6) {
                //  Sahin hareket etmesi
                move.getDestination().setPiece(selectedPiece);
                move.getStart().setPiece(null);

                selectedRook = board.getCell(7, 0).getPiece();

                //  Kalenin hareket etmesi
                board.getCell(7, 0).setPiece(null);
                board.getCell(5, 0).setPiece(selectedRook);
                System.out.println("Siyah uzun rok yaptı");

            }

            //  Gecerken alma
        }else if (move.isEnPassant(move.getStart(), move.getDestination(), board)){

            move.getDestination().setPiece(selectedPiece);
            move.getStart().setPiece(null);

            if (selectedPiece.isWhite()){
                board.getCell(move.getDestination().getX(), move.getDestination().getY() + 1).setPiece(null);
            }else {
                board.getCell(move.getDestination().getX(), move.getDestination().getY() - 1).setPiece(null);
            }

        }else {
            //  Tasin hareket etmesi:
            System.out.println("hamle yapıldı.");
            move.getDestination().setPiece(selectedPiece);
            move.getStart().setPiece(null);
        }

        //  Piyon tahtanin diger ucuna ulasmis ise terfi almali
        move.promote(move.getDestination());

        //  Eger Kale veya Sah hareket etmis ise Rok yapamamali.
        if (selectedPiece instanceof Rook){
            ((Rook) selectedPiece).setCastlingDone(true);
        }
        if (selectedPiece instanceof King){
            ((King) selectedPiece).setCastlingDone(true);
        }



        //  Secili tas piyon ise sonraki hamlelerdde 2 kare ilerlememesi icin moved attribute'u true olur.
        if (selectedPiece instanceof Pawn){
            ((Pawn) selectedPiece).setMoved(true);
        }

        //  İki kare ilerlemis ise recentlyMoved true olur.
        if (selectedPiece instanceof Pawn && Math.abs(move.getDestination().getY() - move.getStart().getY()) == 2){
            ((Pawn) selectedPiece).setRecentlyMoved(true);
        }

        //  Sah Mat olma durumu
        if (move.blackKingsPosition(board).getPiece() instanceof King && ((King) move.blackKingsPosition(board).getPiece()).isCheckmate(board) && currentTurn.whiteSide){
            this.setStatus(GameStatus.WHITE_WIN);

        } else if (move.whiteKingsPosition(board).getPiece() instanceof King && ((King) move.whiteKingsPosition(board).getPiece()).isCheckmate(board)&& !currentTurn.whiteSide) {
            this.setStatus(GameStatus.BLACK_WIN);
        }


        //  Eger alinan tas Sah ise oyun biter.
        if (destinationPiece instanceof King){
            if (player.isWhiteSide()){
                this.setStatus(GameStatus.WHITE_WIN);
            }else {
                this.setStatus(GameStatus.BLACK_WIN);
            }
        }

        //  Siranin diger oyuncuya gecmesi:
        if (this.currentTurn == players[0]){
            this.currentTurn = players[1];
        }else {
            this.currentTurn = players[0];
        }

        return true;
    }

}
