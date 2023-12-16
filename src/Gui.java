import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame implements ActionListener {

    private JButton[][] jboard;
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
                    jboard[i][j].setBackground(new Color(0, 128, 0));
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


    //Seçilen butona göre yapılacak işlemler bu fonksiyonda yazılacak
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton secilenButton = (JButton) e.getSource();

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gui::new);
    }
}