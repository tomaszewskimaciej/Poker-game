package main;

import java.util.*;

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
        } else {
            return 0;
        }

    }

    int fullHouse(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        if (checkIt.size() != checkUnique.size()) {
            return 1;
        } else {
            return 0;
        }

    }

    int flush(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        return 1;

    }

    int straight(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        return 1;

    }

    int threeOfKind(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        if (checkIt.size()-2 == checkUnique.size()) {
            return 1;
        } else {
            return 0;
        }

    }

    int twoPair(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        //I'm aware that this method might mistake 3 same cards as a 2 pairs but it does not matter as it means that there's Three of a kind which is stronger
        int howManyPairs = 0;
        if (checkIt.size()-2 == checkUnique.size()) {
            for (int a = 0; a < checkIt.size() - 1; a++) {
                for (int b = a+1; b < checkIt.size(); b++) {
                    if (checkIt.get(a).getRank() == checkIt.get(b).getRank()) {
                        howManyPairs+=1;
                        break;
                    }
                }
            }
        }
        if(howManyPairs==2){
            return 3;
        }
        return 0;
    }

    int twoPairOrThreeOfAKind(ArrayList<Card> checkIt, TreeSet<Card> checkUnique){
        if(checkIt.size()-1==checkUnique.size()){
            for(int a = 0; a < checkIt.size()-1;a++){

            }
        }
        return 0;
    }
    int pair(ArrayList<Card> checkIt, TreeSet<Card> checkUnique) {
        if(checkIt.size()==checkUnique.size()-1){
            return 2;
        }
        return 0;
    }


}
