package main;

import java.util.*;

public class Check {
    static boolean diff3StartCheck(ArrayList<Card> hands) {
        if (hands.get(0).getRank() > 8 && hands.get(2).getRank() > 8) {
            return true;
        }
        if (hands.get(0).getRank() == 12 && hands.get(2).getRank() == 12) {
            return true;
        }
        if (hands.get(0).getRank() == 12 && hands.get(2).getRank() > 8) {
            return true;
        }
        if (hands.get(0).getRank() > 8 && hands.get(2).getRank() == 12) {
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
        TreeSet<Card> checkUnique = new TreeSet<>();
        for (Card a : checkIt) {
            checkUnique.add(a);
        }

        // these 2 integers are used to tell how strong your hand is, 10 is the maximum value, it's royal flush, straight flush is 9, four of a kind is 8 etc.
        // pair is the lowest possible, it has value of 2, if both players return value of 2 then we compare for the highest card
        int current = 0, max = 0;
        return 1;
    }

    int royalFlush(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        return 1;

    }

    int straightFlush(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        System.out.println("Yes");
        System.out.println("nonono");
        return 1;

    }

    int fourOfKind(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        if (checkIt.size() != checkUnique.size()) {
            return 1;
        }
        return 0;
    }

    int fullHouse(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        boolean pair = false;
        boolean three = false;
        if (checkIt.size() != checkUnique.size()) {
            for (int a = 0; a < checkIt.size() - 2; a++) {
                if (checkIt.get(a).getRank() == checkIt.get(a + 1).getRank()) {
                    if (checkIt.get(a).getRank() == checkIt.get(a + 2).getRank()) {
                        three = true;
                        a += 2;
                    } else {
                        pair = true;
                        a += 1;
                    }
                }
            }
        }
        if (pair && three) {
            return 7;
        }
        return 0;
    }

    int flush(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        int diamonds = 0;
        int clubs = 0;
        int hearths = 0;
        int spades = 0;
        for (int a = 0; a < checkIt.size(); a++) {
            if (checkIt.get(a).getSuit() == 0) {
                clubs++;
            }
            if (checkIt.get(a).getSuit() == 1) {
                diamonds++;
            }
            if (checkIt.get(a).getSuit() == 2) {
                hearths++;
            }
            if (checkIt.get(a).getSuit() == 3) {
                spades++;
            }
        }
        if (clubs >= 5 || diamonds >= 5 || hearths >= 5 || spades >= 5) {
            return 6;
        }
        return 0;
    }

    int straight(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        int straightLength = 0;
        for (int a = 0; a < checkIt.size(); a++) {
            if (checkIt.get(a).getRank() == checkIt.get(a + 1).getRank() - 1) {
                straightLength++;
            } else {
                straightLength = 0;
            }
        }
        if (straightLength >= 5) {
            return 5;
        }
        return 0;
    }

    int threeOfKind(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        if (checkIt.size() - 2 == checkUnique.size()) {
            for (int a = 1; a < checkIt.size() - 1; a++) {
                if (checkIt.get(a).getRank() == checkIt.get(a - 1).getRank() && checkIt.get(a).getRank() == checkIt.get(a + 1).getRank()) {
                    return 4;
                }
            }
        }
        return 0;
    }

    int twoPair(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        //I'm aware that this method might mistake 3 same cards as a 2 pairs but it does not matter as it means that there's Three of a kind which is stronger
        int howManyPairs = 0;
        for (int a = 0; a < checkIt.size() - 1; a++) {
            if (checkIt.get(a).getRank() == checkIt.get(a + 1).getRank()) {
                howManyPairs += 1;
                a += 1;
            }
        }
        if (howManyPairs == 2) {
            return 3;
        }
        return 0;
    }

    int twoPairOrThreeOfAKind(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        if (checkIt.size() - 1 == checkUnique.size()) {
            for (int a = 0; a < checkIt.size() - 1; a++) {

            }
        }
        return 0;
    }

    int pair(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        if (checkIt.size() == checkUnique.size() - 1) {
            return 2;
        }
        return 0;
    }


}
