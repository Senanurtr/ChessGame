import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();

        Player player0 = new Player(true);
        Player player1 = new Player(false);

        game.initialize(player0, player1);

        while(game.getStatus() == GameStatus.ACTIVE){

            for (int column = 0; column <= 8; column++){
                System.out.println("+----+----+----+----+----+----+----+----+");

                if (column != 8){
                    for (int row = 0; row < 8; row++){
                        String color;
                        if (game.getBoard().getCell(row, column).getPiece() != null && game.getBoard().getCell(row, column).getPiece().isWhite()){
                            color = "W";
                        }else {
                            color = "B";
                        }
                        if (game.getBoard().getCell(row, column).getPiece() != null){
                            System.out.print("| " + color + game.getBoard().getCell(row, column).getPiece().getType() + " ");
                        }else {
                            System.out.print("|    ");
                        }
                        if (row == 7){
                            System.out.print("|");
                        }
                    }
                }
                System.out.println();
            }

            System.out.println();
            if (game.getCurrentTurn().isWhiteSide()){
                System.out.println("Sira beyazda");
            }else {
                System.out.println("Sira siyahta");
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("\nHamle yapacaginiz tasin x kordinatini giriniz: ");
            int startX = scanner.nextInt() - 1;
            System.out.print("\nHamle yapacaginiz tasin y kordinatini giriniz: ");
            int startY = scanner.nextInt() - 1;

            System.out.print("\nTasin gidecegi yerin x kordinatini giriniz: ");
            int destinationX = scanner.nextInt() - 1;
            System.out.print("\nTasin gidecegi yerin y kordinatini giriniz: ");
            int destinationY = scanner.nextInt() - 1;

            Move move = new Move(game.getBoard().getCell(startX, startY), game.getBoard().getCell(destinationX, destinationY), player0);
            game.makeMove(move, game.getCurrentTurn());

        }
    }
}