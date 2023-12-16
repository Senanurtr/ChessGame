import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();
        Player player0 = new Player(true);
        Player player1 = new Player(false);

        game.initialize(player0, player1);

        while(game.getStatus() == GameStatus.ACTIVE){

            for (int y = 0; y <= 7; y++){
                for (int x = 0; x <= 7; x++){
                    if (game.getBoard().getCell(x, y).getPiece() != null){
                        System.out.print(game.getBoard().getCell(x, y).getPiece().getType());
                        System.out.print(" ");
                    }else {
                        System.out.print("-");
                        System.out.print(" ");
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
            int startX = scanner.nextInt();
            System.out.print("\nHamle yapacaginiz tasin y kordinatini giriniz: ");
            int startY = scanner.nextInt();



            System.out.print("\nTasin gidecegi yerin x kordinatini giriniz: ");
            int destinationX = scanner.nextInt();
            System.out.print("\nTasin gidecegi yerin y kordinatini giriniz: ");
            int destinationY = scanner.nextInt();

            Move move = new Move(game.getBoard().getCell(startX, startY), game.getBoard().getCell(destinationX, destinationY), player0);
            game.makeMove(move, game.getCurrentTurn());


        }

    }

}
