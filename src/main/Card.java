package main;

public class Card {
    private int suit;
    private int rank;

    public Card(int suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String toString() {
        String[] ranks = { "Ace", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String s = ranks[this.rank] + " of " + suits[this.suit];
        return s;
    }

}
