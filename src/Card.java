public class Card {
    private String pip;
    private String suit;


    private String suitAsciiArt;

    public Card(String inPip, String inSuit, String inSuitAsciiArt) {
        pip = inPip;
        suit = inSuit;
        suitAsciiArt = inSuitAsciiArt;
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
    }

    public Card() {

    }

    public String toString() {
        return pip + " of " + suit;
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

}
