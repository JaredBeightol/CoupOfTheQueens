import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.*;
import javax.swing.border.Border;

public class main {
    // Buffered Reader to get input from console
    private static BufferedReader bufReader;

    //The deck used for the games
    private static Deck deck;

    //King and queen card stacks
    private static CardStack queensideSpadeStack;
    private static CardStack queensideHeartStack;
    private static CardStack queensideClubStack;
    private static CardStack queensideDiamondStack;
    private static CardStack kingsideSpadeStack;
    private static CardStack kingsideHeartStack;
    private static CardStack kingsideClubStack;
    private static CardStack kingsideDiamondStack;
    private static JLabel keyLabel;
    private static JLabel cardToPlayJLabel;
    private static KeyAdapter monitor;

    private static Card cardToPlay;
    private static boolean newCardPlayable;
    private static boolean gameEnd;
    private static int layerPaneIndexCounter;


    // variable to store data from buffered reader
    private static String consoleInput;
    //flag to be used for various purposes
    private static boolean flag;

    private static JFrame gameFrame;

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0");

        //Sets up JFrame for game
        gameFrame = new JFrame();
        gameFrame.getContentPane().setBackground(new Color(42, 97, 54));
        gameFrame.setTitle("Coup of the Queens");
        gameFrame.setLayout(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setBounds(0, 0, 1500, 750);

        //sets up key listener
        setupKeyListener();

        //Sets Up Variables
        setup();

        //Start Game Scree
        startScreen();


    }

    private static void setupKeyListener() {
        monitor = new KeyAdapter() {
            public void keyTyped(KeyEvent event) {
                if (newCardPlayable) {
                    if (event.getKeyChar() == 'Q' || event.getKeyChar() == 'q') {
                        queenPlay(cardToPlay);
                        gameFrame.repaint();
                        newCardPlayable = false;
                        mainGame();
                    }

                    if (event.getKeyChar() == 'K' || event.getKeyChar() == 'k') {
                        kingPlay(cardToPlay);
                        gameFrame.repaint();
                        newCardPlayable = false;

                        mainGame();
                    }
                }
            }
        };

        gameFrame.addKeyListener(monitor);
    }


    /**
     * Setup method to initialize variables in program
     */
    private static void setup() {
        bufReader = new BufferedReader(new InputStreamReader(System.in));
        consoleInput = "";
        flag = true;
        deck = new Deck();
        newCardPlayable = false;
        gameEnd = false;
        layerPaneIndexCounter = 0;
        cardToPlayJLabel = new JLabel();
        cardToPlayJLabel.setBounds(10,10,100,150);
        cardToPlayJLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        queensideSpadeStack = new CardStack();
        queensideHeartStack = new CardStack();
        queensideClubStack = new CardStack();
        queensideDiamondStack = new CardStack();
        kingsideSpadeStack = new CardStack();
        kingsideHeartStack = new CardStack();
        kingsideClubStack = new CardStack();
        kingsideDiamondStack = new CardStack();



        keyLabel = new JLabel();
        gameFrame.add(keyLabel);
        gameFrame.setFocusable(true);

        gameFrame.setVisible(true);
    }

    /**
     * The starting screen. Pretty much a menu screen
     */
    private static void startScreen() {
        startGame();

    }

    /**
     * Main function of the game lies here.
     */
    private static void startGame() {
        gameEnd = false;
        deck = new Deck();
        queensideSpadeStack = new CardStack();
        queensideHeartStack = new CardStack();
        queensideClubStack = new CardStack();
        queensideDiamondStack = new CardStack();
        kingsideSpadeStack = new CardStack();
        kingsideHeartStack = new CardStack();
        kingsideClubStack = new CardStack();
        kingsideDiamondStack = new CardStack();

        //Pop King and Queen Cards
        deck.popSpecificCard(new Card("K", "Spades"));
        deck.popSpecificCard(new Card("K", "Hearts"));
        deck.popSpecificCard(new Card("K", "Clubs"));
        deck.popSpecificCard(new Card("K", "Diamonds"));
        deck.popSpecificCard(new Card("Q", "Spades"));
        deck.popSpecificCard(new Card("Q", "Hearts"));
        deck.popSpecificCard(new Card("Q", "Clubs"));
        deck.popSpecificCard(new Card("Q", "Diamonds"));

        gameFrame.add(cardToPlayJLabel);

        //Add king and queen cards to game frame
        addKQToGameFrame();

        //main game
        mainGame();
    }

    private static void mainGame() {
        if (deck.checkNumCardsLeft() > 0) {
            displayBoard();
            System.out.println("");
            cardToPlay = deck.popTopCard();
            String imgFileName = (cardToPlay.toString() + ".png");
            ImageIcon cardImageIcon = new ImageIcon(new ImageIcon("src/CardImages/" + imgFileName)
                    .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
            cardToPlayJLabel.setIcon(cardImageIcon);
            gameFrame.repaint();


            if (cardToPlay.getPip().equals("J")) {
                System.out.println("\nPulled Jack MUST be played Queenside");
                System.out.println("Pulled Jack MUST be played Queenside");
                System.out.println("Pulled Jack MUST be played Queenside\n");
                queenPlay(cardToPlay);
                mainGame();
            } else {
                newCardPlayable = true;
            }
        } else {
            gameEnd();
        }
    }


    /**
     * Takes care of the logic if a card is played kingside by the player
     * @param cardPlayed
     */
    public static void kingPlay(Card cardPlayed) {
        System.out.println("\nKing Play");
        addKingsideCardToGameFrame(cardPlayed);

        //Check for Ace
        if (cardPlayed.getPip().equals("A")) {
            if (cardPlayed.getSuit().equals("Spades")) {
                if (!kingsideSpadeStack.flippedStack.isEmpty()) {
                    unflipJLabelStack(kingsideSpadeStack);
                    kingsideSpadeStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Hearts")) {
                if (!kingsideHeartStack.flippedStack.isEmpty()) {
                    unflipJLabelStack(kingsideHeartStack);
                    kingsideHeartStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Clubs")) {
                if (!kingsideClubStack.flippedStack.isEmpty()) {
                    unflipJLabelStack(kingsideClubStack);
                    kingsideClubStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Diamonds")) {
                if (!kingsideDiamondStack.flippedStack.isEmpty()) {
                    unflipJLabelStack(kingsideDiamondStack);
                    kingsideDiamondStack.unflipFlippedStack();
                }
            }

            if (cardPlayed.getSuit().equals("Spades")) {
                kingsideSpadeStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Hearts")) {
                kingsideHeartStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Clubs")) {
                kingsideClubStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Diamonds")) {
                kingsideDiamondStack.addCardToTop(cardPlayed);
            }
        } else { //Lay cards normally
            if (cardPlayed.getSuit().equals("Spades")) {
                kingsideSpadeStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Hearts")) {
                kingsideHeartStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Clubs")) {
                kingsideClubStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Diamonds")) {
                kingsideDiamondStack.addCardToTop(cardPlayed);
            }
        }

        //Add cards to king side TODO Fix comment from king to queen
        if (!deck.deck.isEmpty()) {
            Card kingCard1 = deck.popTopCard();
            addQueensideCardToGameFrame(kingCard1);
            System.out.println(kingCard1.toStringSymbol() + "For Queen");
            //CHECK for ace
            if (kingCard1.getPip().equals("A")) {
                if (kingCard1.getSuit().equals("Spades")) {
                    if (!queensideSpadeStack.flippedStack.isEmpty()) {
                        unflipJLabelStack(queensideSpadeStack);
                        queensideSpadeStack.unflipFlippedStack();
                    }
                } else if (kingCard1.getSuit().equals("Hearts")) {
                    if (!queensideHeartStack.flippedStack.isEmpty()) {
                        unflipJLabelStack(queensideHeartStack);
                        queensideHeartStack.unflipFlippedStack();
                    }
                } else if (kingCard1.getSuit().equals("Clubs")) {
                    if (!queensideClubStack.flippedStack.isEmpty()) {
                        unflipJLabelStack(queensideClubStack);
                        queensideClubStack.unflipFlippedStack();
                    }
                } else if (kingCard1.getSuit().equals("Diamonds")) {
                    if (!queensideDiamondStack.flippedStack.isEmpty()) {
                        unflipJLabelStack(queensideDiamondStack);
                        queensideDiamondStack.unflipFlippedStack();
                    }
                }

                //add card after check
                if (kingCard1.getSuit().equals("Spades")) {
                    queensideSpadeStack.addCardToTop(kingCard1);
                } else if (kingCard1.getSuit().equals("Hearts")) {
                    queensideHeartStack.addCardToTop(kingCard1);
                } else if (kingCard1.getSuit().equals("Clubs")) {
                    queensideClubStack.addCardToTop(kingCard1);
                } else if (kingCard1.getSuit().equals("Diamonds")) {
                    queensideDiamondStack.addCardToTop(kingCard1);
                }
                //check for jack
            } else if (kingCard1.getPip().equals("J")) {
                if (kingCard1.getSuit().equals("Spades")) {
                    queensideSpadeStack.addCardToTop(kingCard1);
                    queensideSpadeStack.flipStack();
                    flipJLabelStack(queensideSpadeStack);
                } else if (kingCard1.getSuit().equals("Hearts")) {
                    queensideHeartStack.addCardToTop(kingCard1);
                    queensideHeartStack.flipStack();
                    flipJLabelStack(queensideHeartStack);
                } else if (kingCard1.getSuit().equals("Clubs")) {
                    queensideClubStack.addCardToTop(kingCard1);
                    queensideClubStack.flipStack();
                    flipJLabelStack(queensideClubStack);
                } else if (kingCard1.getSuit().equals("Diamonds")) {
                    queensideDiamondStack.addCardToTop(kingCard1);
                    queensideDiamondStack.flipStack();
                    flipJLabelStack(queensideDiamondStack);
                }
            } else { //play card normally
                if (kingCard1.getSuit().equals("Spades")) {
                    queensideSpadeStack.addCardToTop(kingCard1);
                } else if (kingCard1.getSuit().equals("Hearts")) {
                    queensideHeartStack.addCardToTop(kingCard1);
                } else if (kingCard1.getSuit().equals("Clubs")) {
                    queensideClubStack.addCardToTop(kingCard1);
                } else if (kingCard1.getSuit().equals("Diamonds")) {
                    queensideDiamondStack.addCardToTop(kingCard1);
                }
            }
        }
    }

    /**
     * Takes care of the logic if a card is player queenside by the player
     * @param cardPlayed
     */
    public static void queenPlay(Card cardPlayed) {
        System.out.println("\nQueen Play");

        addQueensideCardToGameFrame(cardPlayed);

        //Check for Ace
        if (cardPlayed.getPip().equals("A")) {
            if (cardPlayed.getSuit().equals("Spades")) {
                if (!queensideSpadeStack.flippedStack.isEmpty()) {
                    unflipJLabelStack(queensideSpadeStack);
                    queensideSpadeStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Hearts")) {
                if (!queensideHeartStack.flippedStack.isEmpty()) {
                    unflipJLabelStack(queensideHeartStack);
                    queensideHeartStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Clubs")) {
                if (!queensideClubStack.flippedStack.isEmpty()) {
                    unflipJLabelStack(queensideClubStack);
                    queensideClubStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Diamonds")) {
                if (!queensideDiamondStack.flippedStack.isEmpty()) {
                    unflipJLabelStack(queensideDiamondStack);
                    queensideDiamondStack.unflipFlippedStack();
                }
            }

            if (cardPlayed.getSuit().equals("Spades")) {
                queensideSpadeStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Hearts")) {
                queensideHeartStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Clubs")) {
                queensideClubStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Diamonds")) {
                queensideDiamondStack.addCardToTop(cardPlayed);
            }
        } else { //Lay cards normally
            if (cardPlayed.getSuit().equals("Spades")) {
                queensideSpadeStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Hearts")) {
                queensideHeartStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Clubs")) {
                queensideClubStack.addCardToTop(cardPlayed);
            } else if (cardPlayed.getSuit().equals("Diamonds")) {
                queensideDiamondStack.addCardToTop(cardPlayed);
            }
        }

        //Add cards to king side
        for (int i = 0; i < 2; i++) {
            if (!deck.deck.isEmpty()) {
                Card kingCard1 = deck.popTopCard();
                addKingsideCardToGameFrame(kingCard1);

                System.out.println(kingCard1.toStringSymbol() + "For King");
                //CHECK for ace
                if (kingCard1.getPip().equals("A")) {
                    if (kingCard1.getSuit().equals("Spades")) {
                        if (!kingsideSpadeStack.flippedStack.isEmpty()) {
                            unflipJLabelStack(kingsideSpadeStack);
                            kingsideSpadeStack.unflipFlippedStack();
                        }
                    } else if (kingCard1.getSuit().equals("Hearts")) {
                        if (!kingsideHeartStack.flippedStack.isEmpty()) {
                            unflipJLabelStack(kingsideHeartStack);
                            kingsideHeartStack.unflipFlippedStack();
                        }
                    } else if (kingCard1.getSuit().equals("Clubs")) {
                        if (!kingsideClubStack.flippedStack.isEmpty()) {
                            unflipJLabelStack(kingsideClubStack);
                            kingsideClubStack.unflipFlippedStack();
                        }
                    } else if (kingCard1.getSuit().equals("Diamonds")) {
                        if (!kingsideDiamondStack.flippedStack.isEmpty()) {
                            unflipJLabelStack(kingsideDiamondStack);
                            kingsideDiamondStack.unflipFlippedStack();
                        }
                    }

                    //add card after check
                    if (kingCard1.getSuit().equals("Spades")) {
                        kingsideSpadeStack.addCardToTop(kingCard1);
                    } else if (kingCard1.getSuit().equals("Hearts")) {
                        kingsideHeartStack.addCardToTop(kingCard1);
                    } else if (kingCard1.getSuit().equals("Clubs")) {
                        kingsideClubStack.addCardToTop(kingCard1);
                    } else if (kingCard1.getSuit().equals("Diamonds")) {
                        kingsideDiamondStack.addCardToTop(kingCard1);
                    }
                //check for jack
                } else if (kingCard1.getPip().equals("J")) {
                    if (kingCard1.getSuit().equals("Spades")) {
                        kingsideSpadeStack.addCardToTop(kingCard1);
                        kingsideSpadeStack.flipStack();
                        flipJLabelStack(kingsideSpadeStack);
                    } else if (kingCard1.getSuit().equals("Hearts")) {
                        kingsideHeartStack.addCardToTop(kingCard1);
                        kingsideHeartStack.flipStack();
                        flipJLabelStack(kingsideHeartStack);
                    } else if (kingCard1.getSuit().equals("Clubs")) {
                        kingsideClubStack.addCardToTop(kingCard1);
                        kingsideClubStack.flipStack();
                        flipJLabelStack(kingsideClubStack);
                    } else if (kingCard1.getSuit().equals("Diamonds")) {
                        kingsideDiamondStack.addCardToTop(kingCard1);
                        kingsideDiamondStack.flipStack();
                        flipJLabelStack(kingsideDiamondStack);
                    }
                } else { //play card normally
                    if (kingCard1.getSuit().equals("Spades")) {
                        kingsideSpadeStack.addCardToTop(kingCard1);
                    } else if (kingCard1.getSuit().equals("Hearts")) {
                        kingsideHeartStack.addCardToTop(kingCard1);
                    } else if (kingCard1.getSuit().equals("Clubs")) {
                        kingsideClubStack.addCardToTop(kingCard1);
                    } else if (kingCard1.getSuit().equals("Diamonds")) {
                        kingsideDiamondStack.addCardToTop(kingCard1);
                    }
                }
            }
        }
    }

    public static void gameEnd() {
        //Display Queenside Points
        JLabel queensideSpadeCardJLabel = new JLabel();
        queensideSpadeCardJLabel.setBounds(550 - ((queensideSpadeStack.cardStack.size() + queensideSpadeStack.flippedStack.size()) * 50),
                20,80,120);
        for (Card card : queensideSpadeStack.cardStack) {
            queensideSpadeStack.stackValue += card.getValue();
        }
        queensideSpadeCardJLabel.setText(Integer.toString(queensideSpadeStack.stackValue));
        queensideSpadeCardJLabel.setSize(50,30);
        queensideSpadeCardJLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        queensideSpadeStack.cardJLabelStack.add(queensideSpadeCardJLabel);
        gameFrame.add(queensideSpadeCardJLabel);

        JLabel queensideHeartCardJLabel = new JLabel();
        queensideHeartCardJLabel.setBounds(550 - ((queensideHeartStack.cardStack.size() + queensideHeartStack.flippedStack.size()) * 50),
                180,80,120);
        for (Card card : queensideHeartStack.cardStack) {
            queensideHeartStack.stackValue += card.getValue();
        }
        queensideHeartCardJLabel.setText(Integer.toString(queensideHeartStack.stackValue));
        queensideHeartCardJLabel.setSize(50,30);
        queensideHeartCardJLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        queensideHeartStack.cardJLabelStack.add(queensideHeartCardJLabel);
        gameFrame.add(queensideHeartCardJLabel);

        JLabel queensideClubCardJLabel = new JLabel();
        queensideClubCardJLabel.setBounds(550 - ((queensideClubStack.cardStack.size() + queensideClubStack.flippedStack.size()) * 50),
                340,80,120);
        for (Card card : queensideClubStack.cardStack) {
            queensideClubStack.stackValue += card.getValue();
        }
        queensideClubCardJLabel.setText(Integer.toString(queensideClubStack.stackValue));
        queensideClubCardJLabel.setSize(50,30);
        queensideClubCardJLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        queensideClubStack.cardJLabelStack.add(queensideClubCardJLabel);
        gameFrame.add(queensideClubCardJLabel);

        JLabel queensideDiamondCardJLabel = new JLabel();
        queensideDiamondCardJLabel.setBounds(550 - ((queensideDiamondStack.cardStack.size() + queensideDiamondStack.flippedStack.size()) * 50),
                500,80,120);
        for (Card card : queensideDiamondStack.cardStack) {
            queensideDiamondStack.stackValue += card.getValue();
        }
        queensideDiamondCardJLabel.setText(Integer.toString(queensideDiamondStack.stackValue));
        queensideDiamondCardJLabel.setSize(50,30);
        queensideDiamondCardJLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        queensideDiamondStack.cardJLabelStack.add(queensideDiamondCardJLabel);
        gameFrame.add(queensideDiamondCardJLabel);


        //Display Kingside Points
        JLabel kingsideSpadeCardJLabel = new JLabel();
        kingsideSpadeCardJLabel.setBounds(940 + ((kingsideSpadeStack.cardStack.size() + kingsideSpadeStack.flippedStack.size()) * 50),
                20,80,120);
        for (Card card : kingsideSpadeStack.cardStack) {
            kingsideSpadeStack.stackValue += card.getValue();
        }
        kingsideSpadeCardJLabel.setText(Integer.toString(kingsideSpadeStack.stackValue));
        kingsideSpadeCardJLabel.setSize(50,30);
        kingsideSpadeCardJLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        kingsideSpadeStack.cardJLabelStack.add(kingsideSpadeCardJLabel);
        gameFrame.add(kingsideSpadeCardJLabel);

        JLabel kingsideHeartCardJLabel = new JLabel();
        kingsideHeartCardJLabel.setBounds(940 + ((kingsideHeartStack.cardStack.size() + kingsideHeartStack.flippedStack.size()) * 50),
                180,80,120);
        for (Card card : kingsideHeartStack.cardStack) {
            kingsideHeartStack.stackValue += card.getValue();
        }
        kingsideHeartCardJLabel.setText(Integer.toString(kingsideHeartStack.stackValue));
        kingsideHeartCardJLabel.setSize(50,30);
        kingsideHeartCardJLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        kingsideHeartStack.cardJLabelStack.add(kingsideHeartCardJLabel);
        gameFrame.add(kingsideHeartCardJLabel);

        JLabel kingsideClubCardJLabel = new JLabel();
        kingsideClubCardJLabel.setBounds(940 + ((kingsideClubStack.cardStack.size() + kingsideClubStack.flippedStack.size()) * 50),
                340,80,120);
        for (Card card : kingsideClubStack.cardStack) {
            kingsideClubStack.stackValue += card.getValue();
        }
        kingsideClubCardJLabel.setText(Integer.toString(kingsideClubStack.stackValue));
        kingsideClubCardJLabel.setSize(50,30);
        kingsideClubCardJLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        kingsideClubStack.cardJLabelStack.add(kingsideClubCardJLabel);
        gameFrame.add(kingsideClubCardJLabel);

        JLabel kingsideDiamondCardJLabel = new JLabel();
        kingsideDiamondCardJLabel.setBounds(940 + ((kingsideDiamondStack.cardStack.size() + kingsideDiamondStack.flippedStack.size()) * 50),
                500,80,120);
        for (Card card : kingsideDiamondStack.cardStack) {
            kingsideDiamondStack.stackValue += card.getValue();
        }
        kingsideDiamondCardJLabel.setText(Integer.toString(kingsideDiamondStack.stackValue));
        kingsideDiamondCardJLabel.setSize(50,30);
        kingsideDiamondCardJLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        kingsideDiamondStack.cardJLabelStack.add(kingsideDiamondCardJLabel);
        gameFrame.add(kingsideDiamondCardJLabel);

        calculateWinner();

        cardToPlayJLabel.setIcon(new ImageIcon(new ImageIcon("src/CardImages/CardBack.png")
                .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));

        gameFrame.repaint();

        JButton restartGameButton = new JButton("Restart");
        restartGameButton.setFont(new Font("SansSerif", Font.PLAIN, 20));
        restartGameButton.setBounds(10,645,150,50);
        restartGameButton.setLayout(null);
        gameFrame.add(restartGameButton);
        gameFrame.repaint();
        restartGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //your actions
                gameFrame.getContentPane().removeAll();
                emptyLayeredPane();
                gameFrame.repaint();
                setup();
                startGame();
            }
        });

        gameEnd = true;
    }

    public static void displayBoard() {
        System.out.print("\n\nQUEEN: ");
        for (Card card : queensideSpadeStack.cardStack) {
            System.out.print(card.toStringSymbol() + "  ");
        }
        System.out.print("\nKING:  ");
        for (Card card : kingsideSpadeStack.cardStack) {
            System.out.print(card.toStringSymbol() + "  ");
        }
        System.out.print("\n\n");
        System.out.print("QUEEN: ");
        for (Card card : queensideHeartStack.cardStack) {
            System.out.print(card.toStringSymbol() + "  ");
        }
        System.out.print("\nKING:  ");
        for (Card card : kingsideHeartStack.cardStack) {
            System.out.print(card.toStringSymbol() + "  ");
        }
        System.out.print("\n\n");
        System.out.print("QUEEN: ");
        for (Card card : queensideClubStack.cardStack) {
            System.out.print(card.toStringSymbol() + "  ");
        }
        System.out.print("\nKING:  ");
        for (Card card : kingsideClubStack.cardStack) {
            System.out.print(card.toStringSymbol() + "  ");
        }
        System.out.print("\n\n");
        System.out.print("QUEEN: ");
        for (Card card : queensideDiamondStack.cardStack) {
            System.out.print(card.toStringSymbol() + "  ");
        }
        System.out.print("\nKING:  ");
        for (Card card : kingsideDiamondStack.cardStack) {
            System.out.print(card.toStringSymbol() + "  ");
        }
        System.out.print("\n\n");
    }

    /**
     * Adds King and queen cards to center JFrame gameFrame and prints the out in ascii text to the console
     */
    public static void addKQToGameFrame() {
        ImageIcon kSpadeImageIcon = new ImageIcon(new ImageIcon("src/CardImages/KSpades.png")
                .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        JLabel kSpadeJLabel = new JLabel(kSpadeImageIcon);
        kSpadeJLabel.setBounds(760, 10, 100, 150);
        kSpadeJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameFrame.add(kSpadeJLabel);

        ImageIcon qSpadeImageIcon = new ImageIcon(new ImageIcon("src/CardImages/QSpades.png")
                .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        JLabel qSpadeJLabel = new JLabel(qSpadeImageIcon);
        qSpadeJLabel.setBounds(650, 10, 100, 150);
        qSpadeJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameFrame.add(qSpadeJLabel);

        ImageIcon kHeartImageIcon = new ImageIcon(new ImageIcon("src/CardImages/KHearts.png")
                .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        JLabel kHeartJLabel = new JLabel(kHeartImageIcon);
        kHeartJLabel.setBounds(760, 170, 100, 150);
        kHeartJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameFrame.add(kHeartJLabel);

        ImageIcon qHeartImageIcon = new ImageIcon(new ImageIcon("src/CardImages/QHearts.png")
                .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        JLabel qHeartJLabel = new JLabel(qHeartImageIcon);
        qHeartJLabel.setBounds(650, 170, 100, 150);
        qHeartJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameFrame.add(qHeartJLabel);

        ImageIcon kClubImageIcon = new ImageIcon(new ImageIcon("src/CardImages/KClubs.png")
                .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        JLabel kClubJLabel = new JLabel(kClubImageIcon);
        kClubJLabel.setBounds(760, 330, 100, 150);
        kClubJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameFrame.add(kClubJLabel);

        ImageIcon qClubImageIcon = new ImageIcon(new ImageIcon("src/CardImages/QClubs.png")
                .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        JLabel qClubJLabel = new JLabel(qClubImageIcon);
        qClubJLabel.setBounds(650, 330, 100, 150);
        qClubJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameFrame.add(qClubJLabel);

        ImageIcon kDiamondImageIcon = new ImageIcon(new ImageIcon("src/CardImages/KDiamonds.png")
                .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        JLabel kDiamondJLabel = new JLabel(kDiamondImageIcon);
        kDiamondJLabel.setBounds(760, 490, 100, 150);
        kDiamondJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameFrame.add(kDiamondJLabel);

        ImageIcon qDiamondImageIcon = new ImageIcon(new ImageIcon("src/CardImages/QDiamonds.png")
                .getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH));
        JLabel qDiamondJLabel = new JLabel(qDiamondImageIcon);
        qDiamondJLabel.setBounds(650, 490, 100, 150);
        qDiamondJLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        gameFrame.add(qDiamondJLabel);

        //Repaints Game Frame with added cards
        gameFrame.repaint();
    }

    /**
     * Add card to GameFrame queenside
     * @param card
     */
    public static void addQueensideCardToGameFrame(Card card) {
        String imgFileName = (card.toString() + ".png");
        ImageIcon cardImageIcon = new ImageIcon(new ImageIcon("src/CardImages/" + imgFileName)
                .getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH));
        JLabel cardJLabel = new JLabel(cardImageIcon);
        cardJLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        if (card.getSuit().equals("Spades")) {
            cardJLabel.setBounds(550 - ((queensideSpadeStack.cardStack.size() + queensideSpadeStack.flippedStack.size()) * 50),
                    20,80,120);
            gameFrame.getLayeredPane().add(cardJLabel, queensideSpadeStack.cardStack.size() + queensideSpadeStack.flippedStack.size() + 1);
            queensideSpadeStack.cardJLabelStack.add(cardJLabel);
        }else if (card.getSuit().equals("Hearts")) {
            cardJLabel.setBounds(550 - ((queensideHeartStack.cardStack.size() + queensideHeartStack.flippedStack.size()) * 50),
                    180,80,120);
            gameFrame.getLayeredPane().add(cardJLabel, queensideHeartStack.cardStack.size() + queensideHeartStack.flippedStack.size() + 1);
            queensideHeartStack.cardJLabelStack.add(cardJLabel);
        } else if (card.getSuit().equals("Clubs")) {
            cardJLabel.setBounds(550 - ((queensideClubStack.cardStack.size() + queensideClubStack.flippedStack.size()) * 50),
                    340,80,120);
            gameFrame.getLayeredPane().add(cardJLabel, queensideClubStack.cardStack.size() + queensideClubStack.flippedStack.size() + 1);
            queensideClubStack.cardJLabelStack.add(cardJLabel);
        } else if (card.getSuit().equals("Diamonds")) {
            cardJLabel.setBounds(550 - ((queensideDiamondStack.cardStack.size() + queensideDiamondStack.flippedStack.size()) * 50),
                    500,80,120);
            gameFrame.getLayeredPane().add(cardJLabel, queensideDiamondStack.cardStack.size() + queensideDiamondStack.flippedStack.size() + 1);
            queensideDiamondStack.cardJLabelStack.add(cardJLabel);
        }

        gameFrame.getLayeredPane().moveToFront(cardJLabel);
        //gameFrame.add(cardJLabel);
        gameFrame.repaint();
    }

    /**
     * Add card to GameFrame kingside
     * @param card
     */
    public static void addKingsideCardToGameFrame(Card card) {
        String imgFileName = (card.toString() + ".png");
        ImageIcon cardImageIcon = new ImageIcon(new ImageIcon("src/CardImages/" + imgFileName)
                .getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH));
        JLabel cardJLabel = new JLabel(cardImageIcon);
        cardJLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        if (card.getSuit().equals("Spades")) {
            cardJLabel.setBounds(880 + ((kingsideSpadeStack.cardStack.size() + kingsideSpadeStack.flippedStack.size()) * 50),
                    20,80,120);
            gameFrame.getLayeredPane().add(cardJLabel, kingsideSpadeStack.cardStack.size() + kingsideSpadeStack.flippedStack.size() + 1);
            kingsideSpadeStack.cardJLabelStack.add(cardJLabel);
        }else if (card.getSuit().equals("Hearts")) {
            cardJLabel.setBounds(880 + ((kingsideHeartStack.cardStack.size() + kingsideHeartStack.flippedStack.size()) * 50),
                    180,80,120);
            gameFrame.getLayeredPane().add(cardJLabel, kingsideHeartStack.cardStack.size() + kingsideHeartStack.flippedStack.size() + 1);
            kingsideHeartStack.cardJLabelStack.add(cardJLabel);
        } else if (card.getSuit().equals("Clubs")) {
            cardJLabel.setBounds(880 + ((kingsideClubStack.cardStack.size() + kingsideClubStack.flippedStack.size()) * 50),
                    340,80,120);
            gameFrame.getLayeredPane().add(cardJLabel, kingsideClubStack.cardStack.size() + kingsideClubStack.flippedStack.size() + 1);
            kingsideClubStack.cardJLabelStack.add(cardJLabel);
        } else if (card.getSuit().equals("Diamonds")) {
            cardJLabel.setBounds(880 + ((kingsideDiamondStack.cardStack.size() + kingsideDiamondStack.flippedStack.size()) * 50),
                    500,80,120);
            gameFrame.getLayeredPane().add(cardJLabel, kingsideDiamondStack.cardStack.size() + kingsideDiamondStack.flippedStack.size() + 1);
            kingsideDiamondStack.cardJLabelStack.add(cardJLabel);
        }

        gameFrame.getLayeredPane().moveToFront(cardJLabel);
        //gameFrame.add(cardJLabel);
        gameFrame.repaint();
    }

    public static void flipJLabelStack(CardStack cardStack) {
        for (JLabel cardJLabel: cardStack.flippedJLabelStack) {
            cardJLabel.setIcon(new ImageIcon(new ImageIcon("src/CardImages/CardBack.png")
                    .getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH)));
        }

        gameFrame.repaint();
    }

    public static void unflipJLabelStack(CardStack cardStack) {
        String fileName = "";
        JLabel cardJLabel;

        for (int i = 0; i < cardStack.flippedStack.size(); i++) {
            cardJLabel = cardStack.flippedJLabelStack.get(i);
            fileName = cardStack.flippedStack.get(i).toString() + ".png";

            cardJLabel.setIcon(new ImageIcon(new ImageIcon("src/CardImages/" + fileName)
                    .getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH)));
        }

        gameFrame.repaint();
    }

    public static void emptyLayeredPane() {
        for (JLabel label : queensideSpadeStack.cardJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        queensideSpadeStack.cardJLabelStack.clear();
        for (JLabel label : queensideSpadeStack.flippedJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        queensideSpadeStack.flippedJLabelStack.clear();
        for (JLabel label : queensideHeartStack.cardJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        queensideHeartStack.cardJLabelStack.clear();
        for (JLabel label : queensideHeartStack.flippedJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        queensideHeartStack.flippedJLabelStack.clear();
        for (JLabel label : queensideClubStack.cardJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        queensideClubStack.cardJLabelStack.clear();
        for (JLabel label : queensideClubStack.flippedJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        queensideClubStack.flippedJLabelStack.clear();
        for (JLabel label : queensideDiamondStack.cardJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        queensideDiamondStack.cardJLabelStack.clear();
        for (JLabel label : queensideDiamondStack.flippedJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        queensideDiamondStack.flippedJLabelStack.clear();

        for (JLabel label : kingsideSpadeStack.cardJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        kingsideSpadeStack.cardJLabelStack.clear();
        for (JLabel label : kingsideSpadeStack.flippedJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        kingsideSpadeStack.flippedJLabelStack.clear();
        for (JLabel label : kingsideHeartStack.cardJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        kingsideHeartStack.cardJLabelStack.clear();
        for (JLabel label : kingsideHeartStack.flippedJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        kingsideHeartStack.flippedJLabelStack.clear();
        for (JLabel label : kingsideClubStack.cardJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        kingsideClubStack.cardJLabelStack.clear();
        for (JLabel label : kingsideClubStack.flippedJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        kingsideClubStack.flippedJLabelStack.clear();
        for (JLabel label : kingsideDiamondStack.cardJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        kingsideDiamondStack.cardJLabelStack.clear();
        for (JLabel label : kingsideDiamondStack.flippedJLabelStack) {
            gameFrame.getLayeredPane().remove(label);
        }
        kingsideDiamondStack.flippedJLabelStack.clear();

    }

    public static void calculateWinner() {
        int queenSuitsWon = 0;
        int kingSuitsWon = 0;

        if (queensideSpadeStack.stackValue > kingsideSpadeStack.stackValue) {
            queenSuitsWon++;
        }else if (queensideSpadeStack.stackValue < kingsideSpadeStack.stackValue) {
            kingSuitsWon++;
        }
        if (queensideHeartStack.stackValue > kingsideHeartStack.stackValue) {
            queenSuitsWon++;
        }else if (queensideHeartStack.stackValue < kingsideHeartStack.stackValue) {
            kingSuitsWon++;
        }
        if (queensideClubStack.stackValue > kingsideClubStack.stackValue) {
            queenSuitsWon++;
        }else if (queensideSpadeStack.stackValue < kingsideSpadeStack.stackValue) {
            kingSuitsWon++;
        }
        if (queensideDiamondStack.stackValue > kingsideDiamondStack.stackValue) {
            queenSuitsWon++;
        }else if (queensideDiamondStack.stackValue < kingsideDiamondStack.stackValue) {
            kingSuitsWon++;
        }


        JLabel winnerJLabel = new JLabel();
        winnerJLabel.setBounds(1100, 450, 400,450);
        winnerJLabel.setFont(new Font("SansSerif", Font.BOLD, 50));

        if (queenSuitsWon > kingSuitsWon) {
            //queen win
            winnerJLabel.setText("QUEEN WINS!");
        }else if (kingSuitsWon > queenSuitsWon) {
            //king win
            winnerJLabel.setText("KING WINS!");
        }else {
            //tie
            winnerJLabel.setText("TIE!");
        }

        winnerJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gameFrame.add(winnerJLabel);
    }
}
