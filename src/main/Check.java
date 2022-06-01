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

    static int finalCheck(ArrayList<Card> hands, ArrayList<Card> board) {
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
        ArrayList<Card> uniqueCards = new ArrayList<>(checkUnique);

        int [] checkedValues = new int[9];
        checkedValues[0]=royalFlush(checkIt,checkUnique);
        checkedValues[1]=straightFlush(checkIt,checkUnique);
        checkedValues[2]=fourOfKind(checkIt,checkUnique);
        checkedValues[3]=fullHouse(checkIt,checkUnique);
        checkedValues[4]=flush(checkIt);
        checkedValues[5]=straight(checkIt,uniqueCards);
        checkedValues[6]=threeOfKind(checkIt);
        checkedValues[7]=twoPair(checkIt);
        checkedValues[8]=pair(checkIt,checkUnique);
        int max = Arrays.stream(checkedValues).max().getAsInt();

        return max;
    }

//    static int checkAll(ArrayList<Card> checkIt, TreeSet<Card> checkUnique, ArrayList<Card> uniqueCards) {
//        int [] checkedValues = new int[9];
//        checkedValues[0]=royalFlush(checkIt,checkUnique);
//        checkedValues[1]=straightFlush(checkIt,checkUnique);
//        checkedValues[2]=fourOfKind(checkIt,checkUnique);
//        checkedValues[3]=fullHouse(checkIt,checkUnique);
//        checkedValues[4]=flush(checkIt);
//        checkedValues[5]=straight(checkIt,uniqueCards);
//        checkedValues[6]=threeOfKind(checkIt);
//        checkedValues[7]=twoPair(checkIt);
//        checkedValues[8]=pair(checkIt,checkUnique);
//        int max = Arrays.stream(checkedValues).max().getAsInt();
//        return max;
//    }

    static int royalFlush(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        int straightLength = 0;
        int tempRank = 0;
        int tempSuit = 0;
        if (checkUnique.size() > 4) {
            for (int a = checkIt.size() - 1; a > 0; a--) {
                if (checkIt.get(checkIt.size() - 1).getRank() == 12) {
                    if (checkIt.get(a).getRank() == checkIt.get(a - 1).getRank()) {
                        tempRank = checkIt.get(a).getRank();
                        tempSuit = checkIt.get(a).getSuit();
                    } else {
                        if (checkIt.get(a).getRank() + -1 == checkIt.get(a - 1).getRank() && checkIt.get(a).getSuit() == checkIt.get(a - 1).getSuit() || tempRank == checkIt.get(a - 1).getRank() && tempSuit == checkIt.get(a - 1).getSuit()) {
                            straightLength++;
                            if (straightLength >= 4) {
                                return 10;
                            }
                        } else {
                            tempRank = 0;
                            tempSuit = 0;
                            straightLength = 0;
                        }
                    }
                }
            }
        }
        return 0;
    }

    static int straightFlush(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        int straightLength = 0;
        int tempRank = 0;
        int tempSuit = 0;
        if (checkUnique.size() > 4) {
            for (int a = 0; a < checkIt.size() - 1; a++) {
                if (checkIt.get(a).getRank() == checkIt.get(a + 1).getRank()) {
                    tempRank = checkIt.get(a).getRank();
                    tempSuit = checkIt.get(a).getSuit();
                } else {
                    if (checkIt.get(a).getRank() + 1 == checkIt.get(a + 1).getRank() && checkIt.get(a).getSuit() == checkIt.get(a + 1).getSuit() || tempRank == checkIt.get(a + 1).getRank() && tempSuit == checkIt.get(a + 1).getSuit()) {
                        straightLength++;
                    } else {
                        tempRank = 0;
                        tempSuit = 0;
                        straightLength = 0;
                    }
                }
                if (straightLength == 5) {
                    return 9;
                }
            }
        }
        return 0;
    }

    static int fourOfKind(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        if (checkUnique.size() < checkIt.size() - 2) {
            for (int a = 1; a < checkIt.size() - 2; a++) {
                if (checkIt.get(a).getRank() == checkIt.get(a - 1).getRank() && checkIt.get(a).getRank() == checkIt.get(a + 1).getRank()) {
                    if (checkIt.get(a).getRank() == checkIt.get(a + 2).getRank()) {
                        return 8;
                    } else {
                        a += 2;
                    }
                }
            }
        }
        return 0;
    }

    static int fullHouse(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        boolean pair = false;
        boolean three = false;
        boolean isThreeTrue = false;
        if (checkIt.size() != checkUnique.size()) {
            for (int a = 0; a < checkIt.size() - 2; a++) {
                if (checkIt.get(a).getRank() == checkIt.get(a + 1).getRank()) {
                    if (checkIt.get(a).getRank() == checkIt.get(a + 2).getRank()) {
                        if (isThreeTrue) {
                            pair = true;
                        }
                        three = true;
                        isThreeTrue = true;
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

    static int flush(ArrayList<Card> checkIt) {
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

    static int straight(ArrayList<Card> checkIt, ArrayList<Card> uniqueCards) {
        int straightLength = 0;
        for (int a = 1; a < uniqueCards.size(); a++) {
            if (uniqueCards.get(a).getRank() == uniqueCards.get(a + 1).getRank() - 1) {
                straightLength += 1;
                if (straightLength >= 4) {
                    return 5;
                }
            } else {
                straightLength = 0;
            }
        }
        return 0;
    }

    static int threeOfKind(ArrayList<Card> checkIt) {
        for (int a = 1; a < checkIt.size() - 1; a++) {
            if (checkIt.get(a).getRank() == checkIt.get(a - 1).getRank() && checkIt.get(a).getRank() == checkIt.get(a + 1).getRank()) {
                return 4;
            }
        }
        return 0;
    }

    static int twoPair(ArrayList<Card> checkIt) {
        //I'm aware that this method might mistake 3 same cards as a 2 pairs but it does not matter as it means that there's Three of a kind which is way stronger
        int howManyPairs = 0;
        for (int a = 0; a < checkIt.size() - 1; a++) {
            if (checkIt.get(a).getRank() == checkIt.get(a + 1).getRank()) {
                howManyPairs += 1;
                a += 1;
            }
        }
        if (howManyPairs >= 2) {
            return 3;
        }
        return 0;
    }

    static int pair(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        if (checkIt.size() == checkUnique.size() + 1) {
            return 2;
        }
        return 0;
    }

}