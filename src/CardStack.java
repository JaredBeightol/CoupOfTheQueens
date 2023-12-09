import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class CardStack {
    ArrayList<Card> cardStack;
    ArrayList<Card> flippedStack;

    ArrayList<JLabel> cardJLabelStack;
    ArrayList<JLabel> flippedJLabelStack;

    int stackValue;


    public CardStack() {
        cardStack = new ArrayList<>();
        flippedStack = new ArrayList<>();
        cardJLabelStack = new ArrayList<>();
        flippedJLabelStack = new ArrayList<>();
        stackValue = 0;
    }

    public void shuffle() {
        Collections.shuffle(cardStack);
    }


    /**
     * Pulls the top card from the deck
     */
    public Card popTopCard() {
        Card topCard = cardStack.get(cardStack.size() - 1);

        cardStack.remove(cardStack.size() - 1);

        return topCard;
    }

    /**
     * Pulls specific card from the deck
     */
    public void popSpecificCard(Card card) {
        for (Card deckCard : cardStack) {
            if (deckCard.toString().equals(card.toString())) {
                cardStack.remove(deckCard);
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
        return cardStack.size();
    }

    public void addCardToTop(Card card) {
        cardStack.add(card);
    }

    public boolean checkIfCardIsInStack(Card inCard) {
        boolean flag = false;

        for (Card card : cardStack) {
            if (inCard.toString().equals(card.toString())) {
                flag = true;
            }
        }

        return flag;
    }

    public void flipStack() {
        flippedStack.addAll(cardStack);
        flippedJLabelStack.addAll(cardJLabelStack);

        cardStack.clear();
        cardJLabelStack.clear();
    }

    public void unflipFlippedStack() {
        cardStack.addAll(flippedStack);
        cardJLabelStack.addAll(flippedJLabelStack);

        flippedStack.clear();
        flippedJLabelStack.clear();
    }

}
