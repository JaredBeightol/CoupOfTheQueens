import java.io.BufferedReader;
import java.io.InputStreamReader;

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


    // variable to store data from buffered reader
    private static String consoleInput;
    //flag to be used for various purposes
    private static boolean flag;

    public static void main(String[] args) {
        //Sets Up Variables
        setup();

        //Start Game Scree
        startScreen();


    }

    private static void setup() {
        bufReader = new BufferedReader(new InputStreamReader(System.in));
        consoleInput = "";
        flag = true;
        deck = new Deck();
        queensideSpadeStack = new CardStack();
        queensideHeartStack = new CardStack();
        queensideClubStack = new CardStack();
        queensideDiamondStack = new CardStack();
        kingsideSpadeStack = new CardStack();
        kingsideHeartStack = new CardStack();
        kingsideClubStack = new CardStack();
        kingsideDiamondStack = new CardStack();
    }

    private static void startScreen() {
        flag = true;

        while (flag) {
            System.out.println("Begin?");
            System.out.print("Type 'Y' or 'N' and then press 'Enter': ");

            bufReader = new BufferedReader(new InputStreamReader(System.in));

            try {
                consoleInput = bufReader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (consoleInput.equals("Y") || consoleInput.equals("y")) {
                startGame();
            } else if (consoleInput.equals("N") || consoleInput.equals("n")) {
                break;
            } else {
                System.out.print("WRONG! Try Again. \n\n");
            }
        }
    }

    private static void startGame() {
        deck = new Deck();
        queensideSpadeStack = new CardStack();
        queensideHeartStack = new CardStack();
        queensideClubStack = new CardStack();
        queensideDiamondStack = new CardStack();
        kingsideSpadeStack = new CardStack();
        kingsideHeartStack = new CardStack();
        kingsideClubStack = new CardStack();
        kingsideDiamondStack = new CardStack();

        deck.popSpecificCard(new Card("K", "Spades"));
        deck.popSpecificCard(new Card("K", "Hearts"));
        deck.popSpecificCard(new Card("K", "Clubs"));
        deck.popSpecificCard(new Card("K", "Diamonds"));
        deck.popSpecificCard(new Card("Q", "Spades"));
        deck.popSpecificCard(new Card("Q", "Hearts"));
        deck.popSpecificCard(new Card("Q", "Clubs"));
        deck.popSpecificCard(new Card("Q", "Diamonds"));

        System.out.println("                          _______       _______                          ");
        System.out.println("                         |Q♤     |     |K♤     |                         ");
        System.out.println("                         |       |     |       |                         ");
        System.out.println("                         |   ♤   |     |   ♤   |                         ");
        System.out.println("                         |       |     |       |                         ");
        System.out.println("                         |_____Q♤|     |_____K♤|                         ");
        System.out.println("                                  SPADES                                 ");
        System.out.println("                          _______       _______                          ");
        System.out.println("                         |Q♡     |     |K♡     |                         ");
        System.out.println("                         |       |     |       |                         ");
        System.out.println("                         |   ♡   |     |   ♡   |                         ");
        System.out.println("                         |       |     |       |                         ");
        System.out.println("                         |_____Q♡|     |_____K♡|                         ");
        System.out.println("                                  HEARTS                                 ");
        System.out.println("                          _______       _______                          ");
        System.out.println("                         |Q♧     |     |K♧     |                         ");
        System.out.println("                         |       |     |       |                         ");
        System.out.println("                         |   ♧   |     |   ♧   |                         ");
        System.out.println("                         |       |     |       |                         ");
        System.out.println("                         |_____Q♧|     |_____K♧|                         ");
        System.out.println("                                  CLUBS                                  ");
        System.out.println("                          _______       _______                          ");
        System.out.println("                         |Q♢     |     |K♢     |                         ");
        System.out.println("                         |       |     |       |                         ");
        System.out.println("                         |   ♢   |     |   ♢   |                         ");
        System.out.println("                         |       |     |       |                         ");
        System.out.println("                         |_____Q♢|     |_____K♢|                         ");
        System.out.println("                                 DIAMONDS                                ");

        while(deck.checkNumCardsLeft() > 0) {
            displayBoard();
            System.out.println("");
            Card cardToPlay = deck.popTopCard();

            if (cardToPlay.getPip().equals("J")) {
                System.out.println("\nPulled Jack MUST be played Queenside");
                System.out.println("Pulled Jack MUST be played Queenside");
                System.out.println("Pulled Jack MUST be played Queenside\n");
                queenPlay(cardToPlay);
            } else {

                flag = true;

                while (flag) {
                    System.out.println("Type 'Q' or 'K' to send card to the Queens or Kings side.");
                    System.out.println("Card: " + cardToPlay.toStringSymbol());
                    System.out.print("Queen or King?: ");

                    bufReader = new BufferedReader(new InputStreamReader(System.in));

                    try {
                        consoleInput = bufReader.readLine();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (consoleInput.equals("Q") || consoleInput.equals("q")) {
                        queenPlay(cardToPlay);
                        break;
                    } else if (consoleInput.equals("K") || consoleInput.equals("k")) {
                        kingPlay(cardToPlay);
                        break;
                    } else {
                        System.out.print("WRONG! Try Again. \n\n");
                    }
                }
            }
        }

        gameEnd();
    }

    public static void kingPlay(Card cardPlayed) {
        System.out.println("\nKing Play");

        //Check for Ace
        if (cardPlayed.getPip().equals("A")) {
            if (cardPlayed.getSuit().equals("Spades")) {
                if (!kingsideSpadeStack.flippedStack.isEmpty()) {
                    kingsideSpadeStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Hearts")) {
                if (!kingsideHeartStack.flippedStack.isEmpty()) {
                    kingsideHeartStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Clubs")) {
                if (!kingsideClubStack.flippedStack.isEmpty()) {
                    kingsideClubStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Diamonds")) {
                if (!kingsideDiamondStack.flippedStack.isEmpty()) {
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

        //Add cards to king side
        if (!deck.deck.isEmpty()) {
            Card kingCard1 = deck.popTopCard();
            System.out.println(kingCard1.toStringSymbol() + "For Queen");
            //CHECK for ace
            if (kingCard1.getPip().equals("A")) {
                if (kingCard1.getSuit().equals("Spades")) {
                    if (!queensideSpadeStack.flippedStack.isEmpty()) {
                        queensideSpadeStack.unflipFlippedStack();
                    }
                } else if (kingCard1.getSuit().equals("Hearts")) {
                    if (!queensideHeartStack.flippedStack.isEmpty()) {
                        queensideHeartStack.unflipFlippedStack();
                    }
                } else if (kingCard1.getSuit().equals("Clubs")) {
                    if (!queensideClubStack.flippedStack.isEmpty()) {
                        queensideClubStack.unflipFlippedStack();
                    }
                } else if (kingCard1.getSuit().equals("Diamonds")) {
                    if (!queensideDiamondStack.flippedStack.isEmpty()) {
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
                } else if (kingCard1.getSuit().equals("Hearts")) {
                    queensideHeartStack.addCardToTop(kingCard1);
                    queensideHeartStack.flipStack();
                } else if (kingCard1.getSuit().equals("Clubs")) {
                    queensideClubStack.addCardToTop(kingCard1);
                    queensideClubStack.flipStack();
                } else if (kingCard1.getSuit().equals("Diamonds")) {
                    queensideDiamondStack.addCardToTop(kingCard1);
                    queensideDiamondStack.flipStack();
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

    public static void queenPlay(Card cardPlayed) {
        System.out.println("\nQueen Play");

        //Check for Ace
        if (cardPlayed.getPip().equals("A")) {
            if (cardPlayed.getSuit().equals("Spades")) {
                if (!queensideSpadeStack.flippedStack.isEmpty()) {
                    queensideSpadeStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Hearts")) {
                if (!queensideHeartStack.flippedStack.isEmpty()) {
                    queensideHeartStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Clubs")) {
                if (!queensideClubStack.flippedStack.isEmpty()) {
                    queensideClubStack.unflipFlippedStack();
                }
            } else if (cardPlayed.getSuit().equals("Diamonds")) {
                if (!queensideDiamondStack.flippedStack.isEmpty()) {
                    queensideDiamondStack.unflipFlippedStack();
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
                System.out.println(kingCard1.toStringSymbol() + "For King");
                //CHECK for ace
                if (kingCard1.getPip().equals("A")) {
                    if (kingCard1.getSuit().equals("Spades")) {
                        if (!kingsideSpadeStack.flippedStack.isEmpty()) {
                            kingsideSpadeStack.unflipFlippedStack();
                        }
                    } else if (kingCard1.getSuit().equals("Hearts")) {
                        if (!kingsideHeartStack.flippedStack.isEmpty()) {
                            kingsideHeartStack.unflipFlippedStack();
                        }
                    } else if (kingCard1.getSuit().equals("Clubs")) {
                        if (!kingsideClubStack.flippedStack.isEmpty()) {
                            kingsideClubStack.unflipFlippedStack();
                        }
                    } else if (kingCard1.getSuit().equals("Diamonds")) {
                        if (!kingsideDiamondStack.flippedStack.isEmpty()) {
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
                    } else if (kingCard1.getSuit().equals("Hearts")) {
                        kingsideHeartStack.addCardToTop(kingCard1);
                        kingsideHeartStack.flipStack();
                    } else if (kingCard1.getSuit().equals("Clubs")) {
                        kingsideClubStack.addCardToTop(kingCard1);
                        kingsideClubStack.flipStack();
                    } else if (kingCard1.getSuit().equals("Diamonds")) {
                        kingsideDiamondStack.addCardToTop(kingCard1);
                        kingsideDiamondStack.flipStack();
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
}
