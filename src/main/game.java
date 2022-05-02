package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class game {
    static int money;
    static int difficulty = 3;
    static String name;

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
            hands.add(deck.get(i));
        }
        for (int i = 0; i < 4; i++) {                     //removing bottoms cards from deck cuz they've been already dealt to players
            deck.remove(0);
        }
    }

    static void myHand(ArrayList<Card> hands) {
        System.out.println("\nYour hand:");
        System.out.println(hands.get(1));
        System.out.println(hands.get(3));
        System.out.printf("\n");

    }

    static void checkBotsCards(ArrayList<Card> hands, int difficulty) {
        if (difficulty == 1) {
            System.out.println("Bot's hand:");
            System.out.println(hands.get(0));
            System.out.println(hands.get(2));
        } else {
            System.out.println("You are not allowed to check bot's cards at this difficulty.");
        }
    }

    static void afterDealing(int order, int bigHand, int smallHand, Player player, Bot bot) {
        System.out.println("Cards were dealt. It's time to play.\n");
        if (order == 1) {
            //System.out.println("You've big hand. I take " + bigHand + " from your account.\n");
            player.setMoney(player.getMoney() - bigHand);
            bot.setMoney(bot.getMoney() - smallHand);
        } else {
            //System.out.println("You've small hand. I take " + smallHand + " from your account.\n");
            player.setMoney(player.getMoney() - smallHand);
            bot.setMoney(bot.getMoney() - bigHand);
        }
    }

    static int botSmallHand(Bot bot, ArrayList<Card> hands, int difficulty, int bigHand, int smallHand) {
        if (difficulty == 1 || difficulty == 2) {
            System.out.println("Bot decided to play, so I take difference between small and big hand from his account");
            bot.setMoney(bot.getMoney() - (bigHand - smallHand));
            return 0;
        }
        if (difficulty == 3) {
            if (Check.diff3StartCheck(hands)) {
                System.out.println("Bot decided to play, so I take difference between small and big hand from his account");
                bot.setMoney(bot.getMoney() - (bigHand - smallHand));
                return 0;
            }else{
                System.out.println("Bot decided to pass, round is over.");
                return -1;
            }
        }
        if (difficulty == 4) {
            if (Check.diff3StartCheck(hands)) {
                System.out.println("Bot decided to play, so I take difference between small and big hand from his account");
                bot.setMoney(bot.getMoney() - (bigHand - smallHand));
                return 0;
            }else{
                System.out.println("Bot decided to pass, round is over.");
                return -1;
            }
        }
        return 0;
    }

    static int playerSmallHand(Player player, ArrayList<Card> hands, int bigHand, int smallHand) {
        System.out.println("You have small hand, I've already taken small hand amount from your account.");
        System.out.println("It's time to decide whether you wanna play or pass.");
        System.out.println("Press 1 to pass, 2 to pay the difference, 3 to outbid.");
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
                return 0;
            case 3:
                System.out.println("How much money do you wanna bid? Please that");
        }

        return 0;
    }


    static int botPlay(Bot bot, ArrayList<Card> hands, int bigHand, int smallHand, int difficulty1) {
        if (difficulty1 == 1 || difficulty1 == 2) {
            System.out.println("Bot decided to play.");
            bot.setMoney(bot.getMoney() - (bigHand - smallHand));
            return 0;
        }
        if (difficulty1 == 3) {
            Check.diff3StartCheck(hands);

        }
        if (difficulty1 == 4) {

        }
        return 0;
    }

    static void playerPlays() {

    }
}