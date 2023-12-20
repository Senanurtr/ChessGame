import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame implements ActionListener {
    Board board= new Board();
    Game game = new Game();

    private JButton[][] jboard;
    private JButton startButton;
    private JButton destinationButton;
    private Cell startCell;

    public Gui() {
        jboard = new JButton[8][8];
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Satranç Oyunu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //
                jboard[i][j] = new JButton();
                jboard[i][j].addActionListener(this);
                if ((i + j) % 2 == 0) {
                    jboard[i][j].setBackground(Color.WHITE);
                } else {
                    //siyah olunca siyah taşlar kötü görünüyordu yeşil yaptım değiştirebiliriz
                    jboard[i][j].setBackground(new Color(149, 141, 148, 255));
                }
                add(jboard[i][j]);
            }
        }

        initializeBoard();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeBoard() {
        //Siyah taşlar
        jboard[0][0].setIcon(new ImageIcon("src/images/br.png"));
        jboard[0][1].setIcon(new ImageIcon("src/images/bn.png"));
        jboard[0][2].setIcon(new ImageIcon("src/images/bb.png"));
        jboard[0][3].setIcon(new ImageIcon("src/images/bq.png"));
        jboard[0][4].setIcon(new ImageIcon("src/images/bk.png"));
        jboard[0][5].setIcon(new ImageIcon("src/images/bb.png"));
        jboard[0][6].setIcon(new ImageIcon("src/images/bn.png"));
        jboard[0][7].setIcon(new ImageIcon("src/images/br.png"));

        //Siyah piyon
        for (int i = 0; i < 8; i++) {
            jboard[1][i].setIcon(new ImageIcon("src/images/bp.png"));
        }

        //Beyaz taşlar
        jboard[7][0].setIcon(new ImageIcon("src/images/wr.png"));
        jboard[7][1].setIcon(new ImageIcon("src/images/wn.png"));
        jboard[7][2].setIcon(new ImageIcon("src/images/wb.png"));
        jboard[7][3].setIcon(new ImageIcon("src/images/wq.png"));
        jboard[7][4].setIcon(new ImageIcon("src/images/wk.png"));
        jboard[7][5].setIcon(new ImageIcon("src/images/wb.png"));
        jboard[7][6].setIcon(new ImageIcon("src/images/wn.png"));
        jboard[7][7].setIcon(new ImageIcon("src/images/wr.png"));

        //Beyaz piyon
        for (int i = 0; i < 8; i++) {
            jboard[6][i].setIcon(new ImageIcon("src/images/wp.png"));
        }
    }

    public JButton waitForButtonClick() {
        final JButton[] clickedButton = {null};

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickedButton[0] = (JButton) e.getSource();
            }
        };

        // ActionListener ile butona tıklama dinlenir
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                jboard[i][j].addActionListener(actionListener);
            }
        }

        // clickedButton[0] null değilse bir butona tıklanmış demektir
        while (clickedButton[0] == null) {
            try {
                Thread.sleep(100); // Bekleme süresi, gerekirse ayarlayabilirsiniz
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // ActionListener'ı kaldırarak sonraki tıklamaları etkilememesi için temizleyin
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                jboard[i][j].removeActionListener(actionListener);
            }
        }

        return clickedButton[0];
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = getSelectedButton(e);

        boolean turn = game.getTurn();
        Player player= new Player(turn);
            if (startButton == null) {
                startButton = clickedButton;

                int xs = getButtonX(startButton);
                int ys = getButtonY(startButton);
                startCell = board.getCell(xs, ys);

                System.out.println("Start x: " + getButtonX(startButton) + ", y: " + getButtonY(startButton)+startCell.getPiece());

                // Eğer startButton'a ait taş, şu anki oyuncunun taşı değilse, startButton'u sıfırla
                if (startCell.getPiece() == null || startCell.getPiece().isWhite() != turn) {
                    startButton = null;
                    startCell = null;
                    System.out.println("Bu taş sizin değil!");
                }

            } else {
                destinationButton = clickedButton;
                System.out.println("Destination x: " + getButtonX(destinationButton) + ", y: " + getButtonY(destinationButton));

                if (startButton != null && destinationButton != null && startCell != null) {
                    int xd = getButtonX(destinationButton);
                    int yd = getButtonY(destinationButton);
                    Cell destination = board.getCell(xd, yd);

                    // Eğer destinationButton'a ait hücre boşsa veya düşman taşı varsa
                    if (destination.getPiece() == null || destination.getPiece().isWhite() != turn) {
                        Move move = new Move(startCell, destination, player);

                        if (startCell.getPiece().canMove(startCell, destination, board)) {
                            System.out.println("Seçilen taş hedefe gidebiliyor ");
                            destinationButton.setIcon(startCell.getPiece().getPath());
                            startButton.setIcon(null);
                            game.makeMove(move, game.getCurrentTurn());
                        } else {
                            System.out.println("Bu taşın böyle bir hareketi yok!");
                        }
                    } else {
                        System.out.println("Hedef hücrede kendi taşınız var!");
                    }
                }

                startButton = null;
                destinationButton = null;
                startCell = null;
            }
        }

    public int getButtonX(JButton button) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (jboard[i][j] == button) {
                    return j;
                }
            }
        }
        return -1; // Bulunamadı
    }

    public int getButtonY(JButton button) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (jboard[i][j] == button) {
                    return i;
                }
            }
        }
        return -1; // Bulunamadı
    }

    public JButton getSelectedButton(ActionEvent e) {
        return (JButton) e.getSource();
    }


    public static void main(String[] args) {

        Game game = new Game();
        Player player0 = new Player(true);
        Player player1 = new Player(false);

        game.initialize(player0, player1);

        Gui gui = new Gui();

        while (game.getStatus() == GameStatus.ACTIVE) {
            JButton startJB = null;
            JButton destJB = null;

            // Start butonunu seçene kadar bekleyin
            while (startJB == null) {
                startJB = gui.waitForButtonClick();
            }

            int startX = gui.getButtonX(startJB);
            int startY = gui.getButtonY(startJB);
            Cell start = game.getBoard().getCell(startX, startY);

            // Hareketi seçene kadar bekleyin
            while (destJB == null) {
                destJB = gui.waitForButtonClick();
            }

            int destinationX = gui.getButtonX(destJB);
            int destinationY = gui.getButtonY(destJB);
            Cell destination = game.getBoard().getCell(destinationX, destinationY);
            Move move = new Move(start, destination, game.getCurrentTurn());

            game.makeMove(move, game.getCurrentTurn());
            startJB.setIcon(null);
            destJB.setIcon(start.getPiece().getPath());
        }
    }
}