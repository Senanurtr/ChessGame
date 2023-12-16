import java.util.concurrent.SubmissionPublisher;

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
    public boolean playerMove(Player player, int startX, int startY, int destinationX, int destinationY){
        Cell startCell = board.getCell(startX, startY);
        Cell destinationCell = board.getCell(destinationX, destinationY);
        Move move = new Move(startCell, destinationCell, player);

        return this.makeMove(move, player);
    }

    public boolean makeMove(Move move, Player player){
        Piece selectedPiece = move.getStart().getPiece();

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

        //  Secili tas sirasi gelen kisinin tasi mi?
        if (selectedPiece.isWhite() != currentTurn.isWhiteSide()){
            System.out.println("Sirasi gelen kisi kendi taslarini oynamiyor");
            return false;
        }

        //  Secili tas belirtilen hamleyi yapabilir mi?
        if (!selectedPiece.canMove(move.getStart(), move.getDestination(), board)){
            System.out.println("Secili tas istenilen konuma gidemez");
            return false;
        }

        //  Asagidaki durum kontrol edilmeden onceki secili hucrelerin kopyasi:
        Cell copyOfStart = new Cell(move.getStart().getX(), move.getStart().getY(), move.getStart().getPiece());
        Cell copyOfDestination = new Cell(move.getDestination().getX(), move.getDestination().getY(), move.getDestination().getPiece());

        //  Yapilan hamle sonucu Sah tehdit altinda kaliyor mu?
        if (move.resultInThreat(move.getStart(), move.getDestination(), board, currentTurn)){
            System.out.println("yapilan hamle sonucu sah tehdit altinda kalir");
            return false;
        }

        //  Sah'in tehdit altinda kalma durumu kontrol edildikten sonra taslarin yerlerine geri donmesi:

        move.getDestination().setPiece(copyOfDestination.getPiece());
        move.getStart().setPiece(copyOfStart.getPiece());


        //  Secili tas piyon ise sonraki hamlelerdde 2 kare ilerlememesi icin moved attribute'u true olur.
        if (selectedPiece instanceof Pawn){
            ((Pawn) selectedPiece).setMoved(true);
        }

        //  Eger Kale veya Sah hareket etmis ise Rok yapamamali.
        if (selectedPiece instanceof Rook){
            ((Rook) selectedPiece).setCastlingDone(true);
        }
        if (selectedPiece instanceof King){
            ((King) selectedPiece).setCastlingDone(true);
        }

        //  Tas alindi mi?
        Piece destinationPiece = move.getDestination().getPiece();
        if (destinationPiece != null){

            destinationPiece.setAlive(false);
        }

        //  Tasin hareket etmesi:
        move.getDestination().setPiece(selectedPiece);
        move.getStart().setPiece(null);


        //  Eger alinan tas Sah ise oyun biter.
        if (destinationPiece != null && destinationPiece instanceof King){
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
