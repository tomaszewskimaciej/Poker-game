package main;

import java.util.ArrayList;

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


}
