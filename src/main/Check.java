package main;

import java.util.ArrayList;
import java.util.Collections;

public class Check {
    static boolean diff3StartCheck(ArrayList<Card> hands) {
        if (hands.get(0).getRank() > 8 && hands.get(2).getRank() > 8) {
            return true;
        }
        if (hands.get(0).getRank() == 0 && hands.get(2).getRank() == 0) {
            return true;
        }
        if (hands.get(0).getRank() == 0 && hands.get(2).getRank() > 8) {
            return true;
        }
        if (hands.get(0).getRank() > 8 && hands.get(2).getRank() == 0) {
            return true;
        }
        if (hands.get(0).getRank() == hands.get(2).getRank()) {
            return true;
        }
        if (hands.get(0).getSuit() == hands.get(2).getSuit()) {
            return true;
        }
        return false;
    }

    static boolean checkBoard(ArrayList<Card> board) {
        ArrayList<Card> aaa = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            aaa.add(board.get(i));
        }
        return true;
    }

    static int finalCheck(ArrayList<Card> hands, ArrayList<Card> board, int playerOrBot) {
        ArrayList<Card> checkIt = new ArrayList<>();
        // so this is where we are checking player's/bot's hands
        // I'm creating 1 array, once we have 7 cards in 1 array we can check for flushes, fours, houses etc.
        for (Card a : board) {
            checkIt.add(a);
        }
        for (Card a : hands) {
            checkIt.add(a);
        }
        //we sort it by ranks
        Collections.sort(checkIt);
        // these 2 integers are used to tell how strong your hand is, 10 is the maximum value, it's royal flush, straight flush is 9, four of a kind is 8 etc.
        // pair is the lowest possible, it has value of 2, if both players return value of 2 then we compare for the highest card
        int current = 0, max = 0;
        return 1;
    }

    int royalFlush(ArrayList<Card> checkIt){
        return 1;
    }


}
