import java.util.Set;

public class Card {
    private String pip;
    private String suit;
    private String suitAsciiArt;

    private int value;

    public Card(String inPip, String inSuit, String inSuitAsciiArt) {
        pip = inPip;
        suit = inSuit;
        suitAsciiArt = inSuitAsciiArt;

        if (Set.of("2", "3", "4", "5", "6", "7", "8", "9", "10").contains(inPip)) {
            value = Integer.parseInt(inPip);
        } else {
            switch(inPip) {
                case "A":
                    value = 1;
                    break;
                case "J":
                    value = 5;
                    break;
                default:
                    value = 0;
                    break;
            }
        }
    }

    public Card(String inPip, String inSuit) {
        pip = inPip;
        suit = inSuit;
        if (inSuit.equals("Spades")) {
            suitAsciiArt = "♤";
        }else if (inSuit.equals("Hearts")) {
            suitAsciiArt = "♡";
        } else if (inSuit.equals("Clubs")) {
            suitAsciiArt = "♧";
        } else if (inSuit.equals("Diamonds")) {
            suitAsciiArt = "♢";
        }

        if (Set.of("2", "3", "4", "5", "6", "7", "8", "9", "10").contains(inPip)) {
            value = Integer.parseInt(inPip);
        } else {
            switch(inPip) {
                case "A":
                    value = 1;
                    break;
                case "J":
                    value = 5;
                    break;
                default:
                    value = 0;
                    break;
            }
        }
    }

    public Card() {

    }

    public String toString() {
        return pip + suit;
    }

    public String toStringSymbol() {
        return pip + suitAsciiArt;
    }


    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getPip() {
        return pip;
    }

    public void setPip(String pip) {
        this.pip = pip;
    }

    public String getSuitAsciiArt() {
        return suitAsciiArt;
    }

    public void setSuitAsciiArt(String suitAsciiArt) {
        this.suitAsciiArt = suitAsciiArt;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
