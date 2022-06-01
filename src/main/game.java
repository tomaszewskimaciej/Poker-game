package main;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class game {
    static int money;
    static int difficulty = 4;
    static String name;
    static int whoWon;
    static int boardMoney = 0;
    static boolean allIn = false;

    static boolean numberChecker(int min, int max, int current) {
        if (current < min || current > max) {
            System.out.println("This number is incorrect.");
            System.out.println("You must provide number between " + min + " and " + max);
            System.out.println("Please put the correct number.");
            return false;
        } else {
            return true;
        }
    }

    static void gameState(Player player, Bot bot, ArrayList<Card> hands, ArrayList<Card> board) {
        System.out.println("\n\nCurrent money on board is: " + boardMoney);
        System.out.println("Your balance: " + player.getMoney());
        System.out.println("Bot's balance: " + bot.getMoney());
        if (!board.isEmpty()) {
            System.out.println("\nCurrent cards on board are: ");
            for (int i = 0; i < board.size(); i++) {
                System.out.println(board.get(i));
            }
        } else {
            System.out.println("There are no cards on board yet.");
        }
        myHand(hands);
    }

//    static void playerHasNoEnoughMoney(Player player, Bot bot, int amount) {
//        int restOFmoney = amount - player.getMoney();
//        bot.setMoney(bot.getMoney() + restOFmoney);
//        player.setMoney(0);
//    }
//
//    static void botHasNoEnoughMoney(Player player, Bot bot, int amount) {
//        int restOFmoney = amount - bot.getMoney();
//        player.setMoney(player.getMoney() + restOFmoney);
//        bot.setMoney(0);
//
//    }

    static int playerHasNoEnoughMoney(Player player, Bot bot, int outbidAmount) {
        System.out.println("Outbid amount is greater than player's account. Player plays goes all in.");
        int restOFmoney = outbidAmount - player.getMoney();
        boardMoney += player.getMoney();
        bot.setMoney(bot.getMoney() + restOFmoney);
        player.setMoney(0);
        allIn = true;
        return restOFmoney;
    }

    static int botHasNoEnoughMoney(Player player, Bot bot, int amount) {
        System.out.println("Outbid amount is greater than bot's account. Bot plays goes all in.");
        int restOFmoney = amount - bot.getMoney();
        boardMoney += bot.getMoney();
        player.setMoney(player.getMoney() + restOFmoney);
        bot.setMoney(0);
        allIn = true;
        return restOFmoney;
    }


    static void startGame() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Poker game");
        System.out.println("Whats your name?");
        name = scan.nextLine();
        System.out.println("Please let me know the amount of money you wanna play for:");
        money = scan.nextInt();
        System.out.println("Thank you, please set the level of bots where 1 is the lowest and 4 is the highest one");
        difficulty = scan.nextInt();
        while (difficulty > 5 || difficulty < 1) {
            System.out.println("Level must be  greater or equal 1 and equal or smaller 4, please set the correct level number");
            difficulty = scan.nextInt();
        }
        System.out.println("Thank you. Please remember that there's small and big hand, small one is 5% of starting amount and big one is 10% of starting money.");
    }


    static void putCardsInDeck(ArrayList<Card> deck) {
        deck.removeAll(deck);
        for (int i = 0; i < 4; i++) {
            for (int x = 0; x < 13; x++) {
                deck.add(new Card(i, x));
            }
        }
    }

    static void shuffleDeck(ArrayList<Card> deck) {
        Collections.shuffle(deck);
    }

    static void dealCards(ArrayList<Card> deck, ArrayList<Card> hands) {
        hands.removeAll(hands);
        for (int i = 0; i < 4; i++) {                     //adding cards to hands
            hands.add(deck.get(0));
            deck.remove(0);
        }
    }

    static void putCardsOnBoard(int amountOfCards, ArrayList<Card> board, ArrayList<Card> deck) {
        for (int i = 0; i < amountOfCards; i++) {
            board.add(deck.get(0));
            deck.remove(0);
        }
    }

    static void myHand(ArrayList<Card> hands) {
        System.out.println("\nYour hand:");
        System.out.println(hands.get(1));
        System.out.println(hands.get(3));
        System.out.printf("\n");

    }

    static void boardCards(ArrayList<Card> board) {
        System.out.println("\nBoard cards: ");
        for (int a = 0; a < board.size(); a++) {
            System.out.println(board.get(a));
            System.out.println("   ");
        }
    }

    static void checkBotCards(ArrayList<Card> hands) {
        System.out.println("You chose difficulty 1. These are bot's cards: ");
        System.out.println("Bot's hand:");
        System.out.println(hands.get(0));
        System.out.println(hands.get(2));
        System.out.println("\n");
    }

    static void afterDealing(int order, int bigHand, int smallHand, Player player, Bot bot) {
        System.out.println("\nCards were dealt. It's time to play.\n");
        if (order == 1) {
            //System.out.println("You've big hand. I take " + bigHand + " from your account.\n");
            player.setMoney(player.getMoney() - bigHand);
            bot.setMoney(bot.getMoney() - smallHand);
            boardMoney += smallHand + bigHand;
        } else {
            //System.out.println("You've small hand. I take " + smallHand + " from your account.\n");
            player.setMoney(player.getMoney() - smallHand);
            bot.setMoney(bot.getMoney() - bigHand);
            boardMoney += smallHand + bigHand;
        }
    }

    static int botSmallHand(Bot bot, ArrayList<Card> hands, int bigHand, int smallHand) {
        Random rand = new Random();
        if (difficulty == 1 || difficulty == 2) {
            System.out.println("Bot decided to play, so I take difference between small and big hand from his account.");
            bot.setMoney(bot.getMoney() - (bigHand - smallHand));
            boardMoney += smallHand;
            return 0;
        }

        if (difficulty == 3) {
            if (Check.diff3StartCheck(hands)) {
                System.out.println("Bot decided to play, so I take difference between small and big hand from his account.");
                bot.setMoney(bot.getMoney() - (bigHand - smallHand));
                boardMoney += smallHand;
                return 0;
            } else {
                int decide = rand.nextInt(10);
                if (decide > 7) {
                    System.out.println("Bot decided to pass, round is over.");
                    return -1;
                } else {
                    System.out.println("Bot decided to play, so I take difference between small and big hand from his account.");
                    bot.setMoney(bot.getMoney() - (bigHand - smallHand));
                    boardMoney += smallHand;
                    return 0;
                }
            }
        }
        if (difficulty == 4) {
            int decide = rand.nextInt(10);
            if (Check.diff3StartCheck(hands)) {
                System.out.println("Bot decided to play, so I take difference between small and big hand from his account.");
                bot.setMoney(bot.getMoney() - (bigHand - smallHand));
                boardMoney += smallHand;
                return 0;
            } else {
                if (decide > 7) {
                    System.out.println("Bot decided to pass, round is over.");
                    return -1;
                }
            }
        }
        return 0;
    }


    static int playerSmallHand(Player player, int bigHand, int smallHand) {
        System.out.println("You have small hand, I've already taken small hand amount from your account.");
        System.out.println("It's time to decide whether you wanna play or pass.");
        System.out.println("Press 1 to pass, 2 to pay the difference.");
        int choice;
        Scanner scan = new Scanner(System.in);
        boolean good = true;
        do {
            choice = scan.nextInt();
            good = numberChecker(1, 4, choice);
        } while (!good);

        switch (choice) {
            case 1:
                System.out.printf("You decided to pass. Round is over.");
                return -1;
            case 2:
                System.out.println("You decided to pay the difference. I take money from your account.");
                player.setMoney(player.getMoney() - (bigHand - smallHand));
                boardMoney += smallHand;
                return 0;
        }

        return 0;
    }

    // **************** FROM HERE ON THERE ARE METHODS FOR ROUND 2 (3 CARDS ON BOARD)*********************************************

    static void round2(ArrayList<Card> board, ArrayList<Card> deck) {
        putCardsOnBoard(3, board, deck);
        System.out.println("Cards were put on board, here they are: ");
        System.out.println(board.get(0) + ", " + board.get(1) + ", " + board.get(2) + "\n");
    }


    static int roundPlay(Player player, Bot bot) {
        System.out.println("You need to decide what you wanna do, you can check or outbid.");
        System.out.println("Press 1 to check, 2 to outbid");
        int choice;
        Scanner scan = new Scanner(System.in);
        boolean good = true;
        do {
            choice = scan.nextInt();
            good = numberChecker(1, 2, choice);
        } while (!good);
        switch (choice) {
            case 1:
                System.out.printf("You decided to check. ");
                return 0;
            case 2:
                System.out.println("How much you wanna outbid for?");
                int outbidAmount = scan.nextInt();
                if (player.canPlayerPay(outbidAmount)) {
                    player.setMoney(player.getMoney() - outbidAmount);
                    boardMoney += outbidAmount;
                    return outbidAmount;
                } else {
                    int value = playerHasNoEnoughMoney(player, bot, outbidAmount);
                    return value;
                }
        }
        return 0;
    }

    static int round2PlayerChecks(Bot bot, ArrayList<Card> hands, ArrayList<Card> board) {
        Random rand = new Random();
        if (difficulty == 4) {
            int decide = rand.nextInt(10);
            decide = Check.finalCheck(hands, board);
            if (decide >= 3) {
                int outbidAmount = bot.getMoney() / 10;
                System.out.println("\nBot decided to outbid, he outbids by: " + outbidAmount);
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return outbidAmount;
            } else {
                System.out.println("\nBot also checks.");
                return 0;
            }
        } else {
            System.out.println("\nBot also checks.");
            return 0;
        }
    }

    static boolean round2PlayerOutbids(Player player, Bot bot, int outbidAmount, ArrayList<Card> hands, ArrayList<Card> board) {
        int whatBotDoes = 1;
        Random rand = new Random();
        int random = rand.nextInt(10);
        //we don't check for difficulty 1 and 2 because these will always check
        if (difficulty == 3) {
            //we check whether bet is greater than 50% of bot's current money, if so he needs to calculate if it's worth to check
            if (bot.getMoney() / 2 < outbidAmount) {
                if (Check.diff3StartCheck(hands)) {
                    whatBotDoes = 1;
                } else {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return false;
                }
            }
            if (bot.getMoney() / 2 >= outbidAmount && bot.getMoney() / 3 < outbidAmount) {
                if (random >= 4) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return false;
                }
            }
            if (bot.getMoney() / 3 >= outbidAmount && bot.getMoney() / 7 < outbidAmount) {
                if (random >= 6) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return false;
                }
            }
        }
        if (difficulty == 4) {
            if (!Check.diff3StartCheck(hands)) {
                if (bot.getMoney() / 2 < outbidAmount) {
                    if (Check.finalCheck(hands, board) < 5) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
                if (bot.getMoney() / 2 >= outbidAmount && bot.getMoney() / 4 < outbidAmount) {
                    if (random >= 3) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
                if (bot.getMoney() / 4 >= outbidAmount && bot.getMoney() / 6 < outbidAmount) {
                    if (random >= 4) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
                if (bot.getMoney() / 5 >= outbidAmount && bot.getMoney() / 10 < outbidAmount) {
                    if (random >= 5) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
            }
        }
        if (whatBotDoes == 1) {
            System.out.println("\nBot decided to check.");
            if (bot.canBotPay(outbidAmount)) {
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return true;
            } else {
                botHasNoEnoughMoney(player, bot, outbidAmount);
                return true;
            }
        }
        return true;
    }

    static boolean roundBotOutbidded(Player player, Bot bot, int outbidAmount) {
        System.out.println("\nBot decided to outbid you by: " + outbidAmount + ". You need to decide whether to check or pass");
        System.out.println("Press 1 to check or press 2 to pass");
        int choice;
        Scanner scan = new Scanner(System.in);
        boolean good = true;
        do {
            choice = scan.nextInt();
            good = numberChecker(1, 2, choice);
        } while (!good);
        switch (choice) {
            case 1:
                System.out.printf("\nYou decided to check. I take money from your account.");
                if (player.canPlayerPay(outbidAmount)) {
                    player.setMoney(player.getMoney() - outbidAmount);
                    boardMoney += outbidAmount;
                    return true;
                } else {
                    playerHasNoEnoughMoney(player, bot, outbidAmount);
                    return true;
                }
            case 2:
                System.out.println("\nYou decide to pass, round is now over.");
                return false;
        }
        return true;
    }

    //******************* HERE ROUND 2 IS OVER, TIME FOR ROUND 3(5th CARD ON BOARD, ANOTHER BETTING TIME)**********************
    static void round3(ArrayList<Card> board, ArrayList<Card> deck) {
        putCardsOnBoard(1, board, deck);
        System.out.println("4th card was put on board, cards on board are: ");
        System.out.println(board.get(0) + ", " + board.get(1) + ", " + board.get(2) + ", " + board.get(3) + "\n");
    }


    static int round3PlayerChecks(Bot bot, ArrayList<Card> hands, ArrayList<Card> board) {
        int botDecision = 1;
        if (difficulty == 4) {
            int decide = Check.finalCheck(hands, board);
            if (decide >= 9) {
                int outbidAmount = bot.getMoney();
                System.out.println("\n Bot decided to all in (" + bot.getMoney() + ").");
                bot.setMoney(0);
                boardMoney += outbidAmount;
                allIn = true;
                return outbidAmount;
            }

            if (decide >= 7) {
                int outbidAmount = bot.getMoney() / 2;
                System.out.println("\nBot decided to outbid, he outbids by: " + outbidAmount);
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return outbidAmount;
            }
            if (decide >= 4) {
                int outbidAmount = bot.getMoney() / 6;
                System.out.println("\nBot decided to outbid, he outbids by: " + outbidAmount);
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return outbidAmount;
            }
            System.out.println("\nBot also checks.");
            return 0;
        }
        if (difficulty == 3) {
            if (Check.diff3StartCheck(hands)) {
                int outbidAmount = bot.getMoney() / 8;
                System.out.println("\n Bot decided to outbid, he outbids by: " + outbidAmount);
                boardMoney += outbidAmount;
                bot.setMoney(bot.getMoney() - outbidAmount);
                return outbidAmount;
            }
        }
        System.out.println("\nBot also checks.");
        return 0;

    }

    //difference between this method and round2playeroutbids is that this is the first time where level4 bot compares his and board cards to make a decision
    static boolean round3PlayerOutbids(Player player, Bot bot, int outbidAmount, ArrayList<Card> hands, ArrayList<Card> board) {
        int whatBotDoes = 1;
        Random rand = new Random();
        int random = rand.nextInt(10);
        //we don't check for difficulty 1 and 2 because these will always check
        if (difficulty == 3) {
            //we check whether bet is greater than 50% of bot's current money, if so he needs to calculate if it's worth to check
            if (bot.getMoney() / 2 < outbidAmount) {
                if (!Check.diff3StartCheck(hands)) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return false;
                }
            }
            if (bot.getMoney() / 2 >= outbidAmount && bot.getMoney() / 3 < outbidAmount) {
                if (random >= 4) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return false;
                }
            }
            if (bot.getMoney() / 3 >= outbidAmount && bot.getMoney() / 7 < outbidAmount) {
                if (random >= 6) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return false;
                }
            }
            if (bot.getMoney() / 7 >= outbidAmount) {
                if (random >= 8) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return false;
                }
            }
        }
        if (difficulty == 4) {
            int check = Check.finalCheck(board, hands);
            if (check < 6) {
                if (bot.getMoney() / 2 < outbidAmount) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return false;
                }
                if (bot.getMoney() / 2 >= outbidAmount && bot.getMoney() / 4 < outbidAmount) {
                    if (check <= 5) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
                if (bot.getMoney() / 4 >= outbidAmount && bot.getMoney() / 6 < outbidAmount) {
                    if (check <= 4) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
                if (bot.getMoney() / 5 >= outbidAmount && bot.getMoney() / 10 < outbidAmount) {
                    if (random <= 3) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
            }
        }
        System.out.println("\nBot decided to check.");
        if (bot.canBotPay(outbidAmount)) {
            bot.setMoney(bot.getMoney() - outbidAmount);
            boardMoney += outbidAmount;
            return true;
        } else {
            botHasNoEnoughMoney(player, bot, outbidAmount);
            return true;
        }
    }


    //******************* HERE ROUND 3 IS OVER, TIME FOR ROUND 4(5th CARD ON BOARD, ANOTHER BETTING TIME)**********************

    static void round4(ArrayList<Card> board, ArrayList<Card> deck) {
        putCardsOnBoard(1, board, deck);
        System.out.println("5th card was put on board, cards on board are: ");
        System.out.println(board.get(0) + ", " + board.get(1) + ", " + board.get(2) + ", " + board.get(3) + " " + board.get(4) + "\n");
    }

    static int round4PlayerChecks(Bot bot, ArrayList<Card> hands, ArrayList<Card> board) {
        int botDecision = 1;
        if (difficulty == 4) {
            int decide = Check.finalCheck(hands, board);
            if (decide >= 9) {
                int outbidAmount = bot.getMoney();
                System.out.println("\n Bot decided to all in (" + bot.getMoney() + ").");
                bot.setMoney(0);
                boardMoney += outbidAmount;
                allIn = true;
                return outbidAmount;
            }
            if (decide < 9 && decide >= 7) {
                int outbidAmount = bot.getMoney() / 2;
                System.out.println("\n Bot decided to outbid by: " + outbidAmount + ".\n");
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return outbidAmount;
            }
            if (decide < 7 && decide >= 5) {
                int outbidAmount = bot.getMoney() / 4;
                System.out.println("\n Bot decided to outbid by: " + outbidAmount + ".\n");
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return outbidAmount;
            }
            if (decide < 5 && decide >= 4) {
                int outbidAmount = bot.getMoney() / 6;
                System.out.println("\nBot decided to outbid, he outbids by: " + outbidAmount);
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return outbidAmount;
            }
            if (decide < 4 && decide >= 2) {
                int outbidAmount = bot.getMoney() / 8;
                System.out.println("\nBot decided to outbid, he outbids by: " + outbidAmount);
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return outbidAmount;
            }
            System.out.println("\nBot also checks.");
            return 0;
        }
        if (difficulty == 3) {
            if (Check.diff3StartCheck(hands)) {
                int outbidAmount = bot.getMoney() / 6;
                System.out.println("\n Bot decided to outbid, he outbids by: " + outbidAmount);
                boardMoney += outbidAmount;
                bot.setMoney(bot.getMoney() - outbidAmount);
                return outbidAmount;
            }
            System.out.println("\nBot also checks.");
            return 0;
        }
        System.out.println("\nBot also checks.");
        return 0;
    }


    static int round4PlayerOutbids(Player player, Bot bot, int outbidAmount, ArrayList<Card> hands, ArrayList<Card> board) {
        int whatBotDoes = 1;
        Random rand = new Random();
        int random = rand.nextInt(10);
        //we don't check for difficulty 1 and 2 because these will always check
        if (difficulty == 3) {
            //we check whether bet is greater than 50% of bot's current money, if so he needs to calculate if it's worth to check
            if (bot.getMoney() / 2 < outbidAmount) {
                if (!Check.diff3StartCheck(hands)) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return -1;
                }
            }
            if (bot.getMoney() / 2 >= outbidAmount && bot.getMoney() / 3 < outbidAmount) {
                if (Check.diff3StartCheck(hands)) {
                    System.out.println("Bot decided to outbid your bet.");
                    if(bot.canBotPay(outbidAmount)){
                        bot.setMoney(bot.getMoney() - outbidAmount);
                        outbidAmount = bot.getMoney() / 5;
                        boardMoney += outbidAmount;
                        return outbidAmount;
                    }else{
                        botHasNoEnoughMoney(player,bot,outbidAmount);
                        return 0;
                    }
                } else if (random >= 4) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return -1;
                }
            }
            if (bot.getMoney() / 3 >= outbidAmount && bot.getMoney() / 7 < outbidAmount) {
                if (Check.diff3StartCheck(hands)) {
                    if(bot.canBotPay(outbidAmount)){
                        System.out.println("Bot decided to outbid your bet.");
                        bot.setMoney(bot.getMoney() - outbidAmount);
                        outbidAmount = bot.getMoney() / 4;
                        boardMoney += outbidAmount;
                        return outbidAmount;
                    }else{
                        botHasNoEnoughMoney(player,bot,outbidAmount);
                        return 0;
                    }
                } else if (random >= 6) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return -1;
                }
            }
            if (bot.getMoney() / 7 >= outbidAmount) {
                if (Check.diff3StartCheck(hands)) {
                    if(bot.canBotPay(outbidAmount)){
                        System.out.println("Bot decided to outbid your bet.");
                        bot.setMoney(bot.getMoney() - outbidAmount);
                        outbidAmount = bot.getMoney() / 3;
                        boardMoney += outbidAmount;
                        return outbidAmount;
                    }else{
                        botHasNoEnoughMoney(player,bot,outbidAmount);
                        return 0;
                    }

                }
                if (random >= 8) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return -1;
                }
            }
        }
        if (difficulty == 4) {
            int check = Check.finalCheck(board, hands);
            if (check < 6) {
                if (bot.getMoney() / 2 < outbidAmount) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return false;
                }
                if (bot.getMoney() / 2 >= outbidAmount && bot.getMoney() / 4 < outbidAmount) {
                    if (check <= 5) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
                if (bot.getMoney() / 4 >= outbidAmount && bot.getMoney() / 6 < outbidAmount) {
                    if (check <= 4) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
                if (bot.getMoney() / 5 >= outbidAmount && bot.getMoney() / 10 < outbidAmount) {
                    if (random <= 3) {
                        System.out.println("\nBot decided to pass, round is now over.");
                        return false;
                    }
                }
            }
        }

        if (whatBotDoes == 1) {
            System.out.println("\nBot decided to check.");
            if (bot.canBotPay(outbidAmount)) {
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
            } else {
                botHasNoEnoughMoney(player, bot, outbidAmount);
                return 0;
            }
            return true;
        }
        return true;
    }

}