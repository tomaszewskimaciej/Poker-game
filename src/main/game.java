package main;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class game {
    static int money;
    static int difficulty = 3;
    static String name;
    static int whoWon;
    static int boardMoney = 0;


    static boolean numberChecker(int min, int max, int current) {
        if (current < min || current > max) {
            System.out.println("This number is incorrect.");
            System.out.println("You must provide number between+ " + min + " and " + max);
            System.out.println("Please put the correct number.");
            return false;
        } else {
            return true;
        }
    }

//    static boolean playerAccountBalanceChecker(int amount, Player player) {
//        if (amount > player.getMoney()) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    static boolean botAccountBalanceChecker(int amount, Bot bot) {
//        if (amount > bot.getMoney()) {
//            return false;
//        } else {
//            return true;
//        }
//    }


    static void playerHasNoEnoughMoney(Player player, Bot bot, int amount) {
        int restOFmoney = amount - player.getMoney();
        bot.setMoney(bot.getMoney() + restOFmoney);
        player.setMoney(0);
    }

    static void botHasNoEnoughMoney(Player player, Bot bot, int amount) {
        int restOFmoney = amount - bot.getMoney();
        player.setMoney(player.getMoney() + restOFmoney);
        bot.setMoney(0);
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

    static void checkBotCards(ArrayList<Card> hands) {
        System.out.println("You chose difficulty 1. These are bot's cards: ");
        System.out.println("Bot's hand:");
        System.out.println(hands.get(0));
        System.out.println(hands.get(2));
    }

    static void afterDealing(int order, int bigHand, int smallHand, Player player, Bot bot) {
        System.out.println("Cards were dealt. It's time to play.\n");
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
            System.out.println("Bot decided to play, so I take difference between small and big hand from his account");
            bot.setMoney(bot.getMoney() - (bigHand - smallHand));
            boardMoney += smallHand;
            return 0;
        }

        if (difficulty == 3) {
            if (Check.diff3StartCheck(hands)) {
                System.out.println("Bot decided to play, so I take difference between small and big hand from his account");
                bot.setMoney(bot.getMoney() - (bigHand - smallHand));
                boardMoney += smallHand;
                return 0;
            } else {
                int decide = rand.nextInt(10);
                if (decide > 7) {
                    System.out.println("Bot decided to pass, round is over.");
                    return -1;
                } else {
                    System.out.println("Bot decided to play, so I take difference between small and big hand from his account");
                    bot.setMoney(bot.getMoney() - (bigHand - smallHand));
                    boardMoney += smallHand;
                    return 0;
                }
            }
        }
        if (difficulty == 4) {
            if (Check.diff3StartCheck(hands)) {
                System.out.println("Bot decided to play, so I take difference between small and big hand from his account");
                bot.setMoney(bot.getMoney() - (bigHand - smallHand));
                boardMoney += smallHand;
                return 0;
            } else {
                System.out.println("Bot decided to pass, round is over.");
                return -1;
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

    static void round2(ArrayList<Card> board, ArrayList<Card> deck) {
        putCardsOnBoard(3, board, deck);
        System.out.println("Cards were put on board, here they are: ");
        System.out.println(board.get(0) + " " + board.get(1) + " " + board.get(2) + "\n");
    }


    static int round2Play(Player player) {
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
                System.out.printf("You decided to check.");
                return 0;
            case 2:
                System.out.println("How much you wanna outbid for?");
                int outbidAmount = scan.nextInt();
                if (player.canPlayerPay(outbidAmount)) {
                    player.setMoney(player.getMoney() - outbidAmount);
                    boardMoney = -outbidAmount;
                    return outbidAmount;
                } else {
                    System.out.println("Outbid amount is greater than your account. You play for all in.");
                    outbidAmount = player.getMoney();
                    boardMoney += outbidAmount;
                    return outbidAmount;
                }
        }
        return 0;
    }

    static int round2PlayerChecks(Bot bot) {
        Random rand = new Random();
        if (difficulty == 4) {
            int decide = rand.nextInt(10);
            if (decide >= 5) {
                int outbidAmount = bot.getMoney() / 10;
                System.out.println("Bot decided to outbid, he outbids by: " + outbidAmount);
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return outbidAmount;
            } else {
                System.out.println("Bot also checks.");
                return 0;
            }
        } else {
            System.out.println("Bot also checks.");
            return 0;
        }
    }

    static boolean round2PlayerOutbids(Bot bot, Player player, int outbidAmount, ArrayList<Card> hands) {
        int whatBotDoes = 1;
        //we don't check for difficulty 1 and 2 because these will always check
        if (difficulty == 3) {
            if (bot.getMoney() / 4 < outbidAmount) {
                if (Check.diff3StartCheck(hands)) {
                    System.out.println("Bot decided to check.");
                    if (bot.canBotPay(outbidAmount)) {
                        bot.setMoney(bot.getMoney() - outbidAmount);
                        boardMoney += outbidAmount;
                    } else {
                        System.out.println("Bot doesn't have enough money to check that. He goes all in");


                    }
                } else {
                    System.out.println("Bot decided to pass, round is now over.");
                    return false;
                }
            }


        }

        if (difficulty == 4) {

        }

        if (whatBotDoes == 1) {
            System.out.println("Bot decided to check.");
            if (bot.canBotPay(outbidAmount)) {
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return true;
            }
        }

    }

    static boolean round2BotOutbidded(Player player, Bot bot, int outbidAmount) {
        System.out.println("Bot decided to outbid you. You need to decide whether to check or pass");
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
                System.out.printf("You decided to check. I take money from your account.");
                if (player.canPlayerPay(outbidAmount)) {
                    player.setMoney(player.getMoney() - outbidAmount);
                    boardMoney += outbidAmount;
                    return true;
                } else {
                    System.out.println("You don't have enough money to match that outbid. You are now all in, good luck");
                    playerHasNoEnoughMoney(player, bot, outbidAmount);
                    boardMoney += player.getMoney();
                    player.setMoney(0);
                    return true;
                }
            case 2:
                System.out.println("You decide to pass, round is now over.");
                return false;
        }
        return true;
    }


}