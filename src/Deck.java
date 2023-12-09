import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        initialize();
    }

    /**
     * Initializes a deck of 52 cards
     */
    public void initialize() {
        for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 13; j++) {
                Card card = new Card();

                switch(i) {
                    case 0:
                        card.setSuit("Spades");
                        card.setSuitAsciiArt("♤");
                        break;
                    case 1:
                        card.setSuit("Hearts");
                        card.setSuitAsciiArt("♡");
                        break;
                    case 2:
                        card.setSuit("Clubs");
                        card.setSuitAsciiArt("♧");
                        break;
                    case 3:
                        card.setSuit("Diamonds");
                        card.setSuitAsciiArt("♢");
                        break;
                }

                switch(j) {
                    case 0:
                        card.setPip("A");
                        card.setValue(1);
                        break;
                    case 1:
                        card.setPip("2");
                        card.setValue(2);
                        break;
                    case 2:
                        card.setPip("3");
                        card.setValue(3);
                        break;
                    case 3:
                        card.setPip("4");
                        card.setValue(4);
                        break;
                    case 4:
                        card.setPip("5");
                        card.setValue(5);
                        break;
                    case 5:
                        card.setPip("6");
                        card.setValue(6);
                        break;
                    case 6:
                        card.setPip("7");
                        card.setValue(7);
                        break;
                    case 7:
                        card.setPip("8");
                        card.setValue(8);
                        break;
                    case 8:
                        card.setPip("9");
                        card.setValue(9);
                        break;
                    case 9:
                        card.setPip("10");
                        card.setValue(10);
                        break;
                    case 10:
                        card.setPip("J");
                        card.setValue(5);
                        break;
                    case 11:
                        card.setPip("Q");
                        card.setValue(0);
                        break;
                    case 12:
                        card.setPip("K");
                        card.setValue(0);
                        break;
                }

                deck.add(card);
            }
        }

        shuffle();
    }

    /**
     * Shuffles Deck of Cards
     */
    public void shuffle() {
        Collections.shuffle(deck);
    }


    /**
     * Pulls the top card from the deck
     */
    public Card popTopCard() {
        Card topCard = deck.get(deck.size() - 1);

        deck.remove(deck.size() - 1);

        return topCard;
    }

    /**
     * Pulls specific card from the deck
     */
    public void popSpecificCard(Card card) {
        for (Card deckCard : deck) {
            if (deckCard.toString().equals(card.toString())) {
                deck.remove(deckCard);
                break;
            }
        }
    }

    /**
     * randomly inserts card to deck
     */
    public void insertCardRandomly(Card card) {

    }

    /**
     * Check the number of cards left in the deck
     */
    public int checkNumCardsLeft() {
        return deck.size();
    }
}
