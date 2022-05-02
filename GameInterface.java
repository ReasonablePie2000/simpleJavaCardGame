import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class includes the main function of the program and provide the GUI interface of the game.
 *
 * This is a simple card game. Good Luck, Have Fun!
 *
 * @author Ng Kong Pang
 * @version 1.0
 */

public class GameInterface{
    private int[] cards = {0, 0, 0, 0, 0, 0};
    private int playerMoney = 100;
    private int playerBet = 0;
    private int timeOfReplace = 0;
    private JLabel Dealer1 = new JLabel();
    private JLabel Dealer2 = new JLabel();
    private JLabel Dealer3 = new JLabel();
    private JLabel Player1 = new JLabel();
    private JLabel Player2 = new JLabel();
    private JLabel Player3 = new JLabel();
    private JButton buttonCard1 = new JButton("Replace Card 1");
    private JButton buttonCard2 = new JButton("Replace Card 2");
    private JButton buttonCard3 = new JButton("Replace Card 3");
    private JButton buttonStart = new JButton("Start");
    private JButton buttonResult = new JButton("Result");
    private JFrame frame = new JFrame();
    private JLabel betText = new JLabel("Bet: $");
    private JLabel AmountText = new JLabel("Please place you bet! Amount of money you have: $100");
    private JTextField InputBet = new JTextField(10);

    private Game d = new Game();
    /**
     * This method is the main method to run the game.
     *
     * @param args Unused
     */
    public static void main(String[] args) {
        GameInterface gui = new GameInterface();
        gui.go();
    }

    /**
     * This method starts the game and the GUI interface.
     *
     */
    public void go(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menuCon = new JMenu("Control");
        JMenuItem menuItemExit = new JMenuItem("Exit");
        JMenu menuHelp = new JMenu("Help");
        JMenuItem menuItemHelp = new JMenuItem("Instruction");
        menuCon.add(menuItemExit);
        menuHelp.add(menuItemHelp);
        menuBar.add(menuCon);
        menuBar.add(menuHelp);

        JPanel MainPanel = new JPanel();
        JPanel DealerCardsPanel = new JPanel();
        JPanel PlayerCardsPanel = new JPanel();
        JPanel RpCardBtnPanel = new JPanel();
        JPanel StartButtonPanel = new JPanel();
        JPanel InfoPanel = new JPanel();

        MainPanel.setLayout(new GridLayout(5,1));
        MainPanel.add(DealerCardsPanel);
        MainPanel.add(PlayerCardsPanel);
        MainPanel.add(RpCardBtnPanel);
        MainPanel.add(StartButtonPanel);
        MainPanel.add(InfoPanel);
        DealerCardsPanel.setBackground(Color.green);
        PlayerCardsPanel.setBackground(Color.green);
        RpCardBtnPanel.setBackground(Color.green);

        updateCards();
        DealerCardsPanel.add(Dealer1);
        DealerCardsPanel.add(Dealer2);
        DealerCardsPanel.add(Dealer3);
        PlayerCardsPanel.add(Player1);
        PlayerCardsPanel.add(Player2);
        PlayerCardsPanel.add(Player3);

        buttonCard1.addActionListener(new listenReplaceCardOne());
        buttonCard2.addActionListener(new listenReplaceCardTwo());
        buttonCard3.addActionListener(new listenReplaceCardThree());
        buttonCard1.setBackground(Color.green);
        buttonCard2.setBackground(Color.green);
        buttonCard3.setBackground(Color.green);
        buttonCard1.setEnabled(false);
        buttonCard2.setEnabled(false);
        buttonCard3.setEnabled(false);
        buttonResult.setEnabled(false);
        buttonStart.addActionListener(new listenStart());
        buttonResult.addActionListener(new listenResult());
        menuItemHelp.addActionListener(new listenHelp());
        menuItemExit.addActionListener(new listenExit());

        RpCardBtnPanel.add(buttonCard1);
        RpCardBtnPanel.add(buttonCard2);
        RpCardBtnPanel.add(buttonCard3);
        StartButtonPanel.add(betText);
        StartButtonPanel.add(InputBet);
        StartButtonPanel.add(buttonStart);
        StartButtonPanel.add(buttonResult);
        InfoPanel.add(AmountText);

        frame.getContentPane().add(MainPanel);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("A Simple Card Game");
        frame.setSize(500, 700);
        frame.setVisible(true);
    }

    private class listenReplaceCardOne implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            d.putBackCard(cards[3]);
            cards[3] = d.draw();
            updateCards();
            timeOfReplace++;
            if(timeOfReplace == 2){
                buttonCard1.setEnabled(false);
                buttonCard2.setEnabled(false);
                buttonCard3.setEnabled(false);
            }else {
                buttonCard1.setEnabled(false);
            }
        }
    }

    private class listenReplaceCardTwo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            d.putBackCard(cards[4]);
            cards[4] = d.draw();
            updateCards();
            timeOfReplace++;
            if(timeOfReplace == 2){
                buttonCard1.setEnabled(false);
                buttonCard2.setEnabled(false);
                buttonCard3.setEnabled(false);
            }else {
                buttonCard2.setEnabled(false);
            }
        }
    }

    private class listenReplaceCardThree implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            d.putBackCard(cards[5]);
            cards[5] = d.draw();
            updateCards();

            timeOfReplace++;
            if(timeOfReplace == 2){
                buttonCard1.setEnabled(false);
                buttonCard2.setEnabled(false);
                buttonCard3.setEnabled(false);
            }else {
                buttonCard3.setEnabled(false);
            }
        }
    }

    private class listenStart implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String inputNum = InputBet.getText();

            if(d.checkBet(inputNum)){
                playerBet = Integer.parseInt(inputNum);
                if(playerBet > playerMoney){
                    JOptionPane.showMessageDialog(frame,"WARNING: You only have $" + playerMoney + "!");
                    return;
                }
            }else {
                JOptionPane.showMessageDialog(frame,"WARNING: The bet you place must be a positive integer!");
                return;
            }

            timeOfReplace = 0;
            d.shuffleDeck();
            for (int i = 3; i < 6; i++){
                cards[i] = d.draw();
            }
            updateCards();
            buttonCard1.setEnabled(true);
            buttonCard2.setEnabled(true);
            buttonCard3.setEnabled(true);
            buttonStart.setEnabled(false);
            buttonResult.setEnabled(true);
        }
    }

    private class listenResult implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < 3; i++){
                cards[i] = d.draw();
            }
            updateCards();
            for (int i = 0; i < 6; i++){
                d.putBackCard(cards[i]);
            }
            boolean win = d.checkResult(cards);

            if(win){
                JOptionPane.showMessageDialog(frame, "Congrauations! You win this round!");
                playerMoney = playerMoney + playerBet;
            }else {
                JOptionPane.showMessageDialog(frame, "Sorry! The Dealer wins this round!");
                playerMoney = playerMoney - playerBet;
                if(playerMoney == 0){
                    JOptionPane.showMessageDialog(frame, "Game over!\nYou have no more money!\nPlease start a new game!");
                    AmountText.setText("You have no more money! Please start a new game!");
                    buttonCard1.setEnabled(false);
                    buttonCard2.setEnabled(false);
                    buttonCard3.setEnabled(false);
                    buttonStart.setEnabled(false);
                    buttonResult.setEnabled(false);
                    return;
                }
            }
            for (int i = 0; i < 6; i++){
                cards[i]=0;
            }
            buttonCard1.setEnabled(false);
            buttonCard2.setEnabled(false);
            buttonCard3.setEnabled(false);
            buttonStart.setEnabled(true);
            buttonResult.setEnabled(false);
            AmountText.setText("Please place you bet! Amount of money you have: $" + playerMoney);
            updateCards();
        }
    }

    private class listenExit implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
        }
    }

    private class listenHelp implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(frame,"Rules to determine who has better cards:\n" +
                    "J, Q, K are regarded as special cards.\n" +
                    "Rule 1: The one with more special cards wins.\n" +
                    "Rule 2: If both have the same number of special cards, add the face values of the other card(s) and take the remainder after dividing the sum by 10. The one with a bigger remainder wins. (Note: Ace = 1).\n" +
                    "Rule 3: The dealer wins if both rule 1 and rule 2 cannot distinguish the winner.");
        }
    }

    private void updateCards(){
        Dealer1.setIcon(new ImageIcon("Images/card_"+ cards[0] +".gif"));
        Dealer2.setIcon(new ImageIcon("Images/card_"+ cards[1] +".gif"));
        Dealer3.setIcon(new ImageIcon("Images/card_"+ cards[2] +".gif"));
        Player1.setIcon(new ImageIcon("Images/card_"+ cards[3] +".gif"));
        Player2.setIcon(new ImageIcon("Images/card_"+ cards[4] +".gif"));
        Player3.setIcon(new ImageIcon("Images/card_"+ cards[5] +".gif"));
    }

}
