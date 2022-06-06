package main;

import javax.swing.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class game {
    static int money;
    /**
     * Bot level.
     */
    static int difficulty = 4;
    static String name;
    static int whoWon;
    /**
     * The amount of money is currently on board.
     */
    static int boardMoney = 0;
    /**
     * Tells whether either of players went all in.
     */
    static boolean allIn = false;

    /**
     * this method is used to check whether number given by user is correct
     */
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

    /**
     * this method is used to show game state, it shows both player balances and cards on board. It's used after each round.
     */
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


    /**
     * It's used when player has no money to make a bid/outbid of bot's is too big for player's salary.
     * It calculates how much money player can bet, makes all in bet for him, returns "extra" money to bot.
     *
     * @return - returns how much money player bet
     */
    static int playerHasNoEnoughMoney(Player player, Bot bot, int outbidAmount) {
        System.out.println("Outbid amount is greater than player's account. Player plays goes all in.");
        int restOFmoney = outbidAmount - player.getMoney();
        boardMoney += player.getMoney();
        bot.setMoney(bot.getMoney() + restOFmoney);
        player.setMoney(0);
        allIn = true;
        return restOFmoney;
    }

    /**
     * It's used when bot has no money to make a bid/outbid of player's is too big for bot's salary.
     * It calculates how much money bot can bet, makes all in bet for him, returns "extra" money to player.
     *
     * @return - returns how much money bot bet
     */
    static int botHasNoEnoughMoney(Player player, Bot bot, int amount) {
        System.out.println("Outbid amount is greater than bot's account. Bot plays goes all in.");
        int restOFmoney = amount - bot.getMoney();
        boardMoney += bot.getMoney();
        player.setMoney(player.getMoney() + restOFmoney);
        bot.setMoney(0);
        allIn = true;
        return restOFmoney;
    }

    /**
     * Used to start the game. Here you set the starting money, difficulty level.
     */
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

    /**
     * First clears the deck and then puts 52 cards into it.
     */
    static void putCardsInDeck(ArrayList<Card> deck) {
        deck.removeAll(deck);
        for (int i = 0; i < 4; i++) {
            for (int x = 0; x < 13; x++) {
                deck.add(new Card(i, x));
            }
        }
    }

    /**
     * Shuffles deck, used right after putting cards in deck.
     */
    static void shuffleDeck(ArrayList<Card> deck) {
        Collections.shuffle(deck);
    }

    /**
     * Deals cards to players.
     */
    static void dealCards(ArrayList<Card> deck, ArrayList<Card> hands) {
        hands.removeAll(hands);
        for (int i = 0; i < 4; i++) {                     //adding cards to hands
            hands.add(deck.get(0));
            deck.remove(0);
        }
    }

    /**
     * It's used to put cards on board.
     */
    static void putCardsOnBoard(int amountOfCards, ArrayList<Card> board, ArrayList<Card> deck) {
        for (int i = 0; i < amountOfCards; i++) {
            board.add(deck.get(0));
            deck.remove(0);
        }
    }

    /**
     * Shows player its hand.
     */
    static void myHand(ArrayList<Card> hands) {
        System.out.println("\nYour hand:");
        System.out.println(hands.get(1));
        System.out.println(hands.get(3));
        System.out.printf("\n");

    }

    /**
     * Shows board cards.
     */
    static void boardCards(ArrayList<Card> board) {
        System.out.println("\nBoard cards: ");
        for (int a = 0; a < board.size(); a++) {
            System.out.println(board.get(a));
            System.out.println("   ");
        }
    }

    /**
     * Shows bot's cards. Avaiable only at difficulty 1.
     */
    static void checkBotCards(ArrayList<Card> hands) {
        System.out.println("You chose difficulty 1. These are bot's cards: ");
        System.out.println("Bot's hand:");
        System.out.println(hands.get(0));
        System.out.println(hands.get(2));
        System.out.println("\n");
    }

    /**
     * It's called right after dealing cards to players, at the beggining of round.
     * It takes big hand money from player/bot.
     *
     * @param order     - it's int, if it's 1 then player has big hand, 0 mean bot has smallhand.
     * @param bigHand   - 10 % of starting money
     * @param smallHand - 5 % of starting money
     */
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

    /**
     * It's called when bot has small hand. Bot needs to decide whether to check (pay difference between big and small hand) or pass.
     *
     * @return - 0 if bot checks, -1 if bot passes
     */
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
        System.out.println("Bot decided to play, so I take difference between small and big hand from his account.");
        bot.setMoney(bot.getMoney() - (bigHand - smallHand));
        boardMoney += smallHand;
        return 0;
    }

    /**
     * It's called when player has small hand. Player needs to decide whether to check (pay difference between big and small hand) or pass.
     *
     * @return - 0 if player checks, -1 if player passes
     */
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

    /**
     * It's called when either of players decided to pass.
     * It gives board money to winning player, set board money to 0 and change all in to false.
     *
     * @param whoWon 0 if player won, 1 if bot won.
     */
    static void roundOver(Player player, Bot bot, int whoWon) {
        if (whoWon == 0) {
            System.out.println("Round is over, player wins!");
            player.setMoney(player.getMoney() + boardMoney);
            boardMoney = 0;
            allIn = false;
        }
        if (whoWon == 1) {
            System.out.println("Round is over, bot wins!");
            bot.setMoney(bot.getMoney() + boardMoney);
            boardMoney = 0;
            allIn = false;
        }
        if (whoWon == 3) {
            System.out.println("Both players combinations are equal, so are their strongest cards.");
            System.out.println("This is very rare event but it's a draw");
            player.setMoney(player.getMoney() + boardMoney / 2);
            bot.setMoney(bot.getMoney() + boardMoney / 2);
            boardMoney = 0;
            allIn = false;
        }
    }

    // **************** FROM HERE ON THERE ARE METHODS FOR ROUND 2 (3 CARDS ON BOARD)*********************************************

    /**
     * Puts cards on board and show them to players.
     */
    static void round2(ArrayList<Card> board, ArrayList<Card> deck) {
        putCardsOnBoard(3, board, deck);
        System.out.println("Cards were put on board, here they are: ");
        System.out.println(board.get(0) + ", " + board.get(1) + ", " + board.get(2) + "\n");
    }

    /**
     * Player needs to decide what to do. He can either check or outbid.
     *
     * @return 0 if player checks, positive number if player outbids (this number is equal to the amount of outbid).
     */
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

    /**
     * Bot needs to decide what to do after player checked.
     * Only bot level 4 might outbid. He might do this only if his hand have a pretty good (considering that it's an early stage of round) combination with board cards.
     *
     * @return 0 if bot checks, positive number if bot outbids (this number is equal to the amount of outbid).
     */
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

    /**
     * Bot needs to decide what to do after player outbid.
     * Bots level 1 and 2 will always check.
     * Bot level 3 decision depends on his starting hand and how big the outbid is.
     * Bot level 4 decision depends on: first his starting hand (as it's still an early stage of the round) and then of combination of his and board cards.
     *
     * @return true if bot checks, false if bot passes.
     */
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

    /**
     * Player needs to decide what to do after bot outbid him.
     *
     * @return true if  checks, false if player passes.
     */
    static boolean roundBotOutbid(Player player, Bot bot, int outbidAmount) {
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

    //******************* HERE ROUND 2 IS OVER, TIME FOR ROUND 3(4th CARD ON BOARD, ANOTHER BETTING TIME)**********************

    /**
     * Puts 4th card on board.
     */
    static void round3(ArrayList<Card> board, ArrayList<Card> deck) {
        putCardsOnBoard(1, board, deck);
        System.out.println("4th card was put on board, cards on board are: ");
        System.out.println(board.get(0) + ", " + board.get(1) + ", " + board.get(2) + ", " + board.get(3) + "\n");
    }

    /**
     * Bot needs to decide what to do after player checked.
     * Bots level 1 and 2 will always check.
     * Bot level 3 might outbid only if his starting hand was good.
     * Bot level 4 might outbid, it depends on combination of his and board cards.
     *
     * @return 0 if bot also checks, positive number if it outbids.
     */
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

    /**
     * Bot needs to decide what to do after player outbid.
     * Bots level 1 and 2 will always check.
     * Bot level 3 might pass if the bid is too high considering his starting hand
     * Bot level 4 checks his and board cards and then makes a decision.
     *
     * @return true if bot checks, false if bot passes.
     */
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

    /**
     * Puts last card on board
     */
    static void round4(ArrayList<Card> board, ArrayList<Card> deck) {
        putCardsOnBoard(1, board, deck);
        System.out.println("5th card was put on board, cards on board are: ");
        System.out.println(board.get(0) + ", " + board.get(1) + ", " + board.get(2) + ", " + board.get(3) + " " + board.get(4) + "\n");
    }

    /**
     * Bot needs to decide what to do after player checked.
     * Bots level 1 and 2 will always check.
     * Bot level 3 might outbid, depends on his starting hand. It might also outbid even thought his hand wasn't good (rng).
     * Bot level 4 at this stage is very likely to outbid. He'll always outbid if he has any combinations. Might all in if he has a really strong combination.
     *
     * @return 0 if bot checks, positive number if outbids.
     */
    static int round4PlayerChecks(Bot bot, ArrayList<Card> hands, ArrayList<Card> board) {
        int botDecision = 1;
        Random rand = new Random();
        int random = rand.nextInt(10);
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
                System.out.println("\n Bot decided to outbid by: " + outbidAmount + ".\n");
                bot.setMoney(bot.getMoney() - outbidAmount);
                boardMoney += outbidAmount;
                return outbidAmount;
            }
            if (decide >= 5) {
                int outbidAmount = bot.getMoney() / 4;
                System.out.println("\n Bot decided to outbid by: " + outbidAmount + ".\n");
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
            if (decide >= 2) {
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
            } else if (random > 5) {
                int outbidAmount = bot.getMoney() / 10;
                System.out.println("\n Bot decided to outbid, he outbids by: " + outbidAmount);
                boardMoney += outbidAmount;
                bot.setMoney(bot.getMoney() - outbidAmount);
                return outbidAmount;
            }
        }
        System.out.println("\nBot also checks.");
        return 0;
    }

    /**
     * Bot needs to decide what to do after player outbid.
     * Level 1 and 2 will always check.
     * Level 3 might outbid. It's more likely to outbid your outbid here than ever before because it's late stage of round and it has already put some money on board.
     * Bot level 4 at this stage is very likely to outbid. He'll always outbid if he has any combinations. Might all in if he has a really strong combination.
     *
     * @return -1 if bot passes, 0 if bot checks, positive number if bot outbids.
     */
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
                    if (bot.canBotPay(outbidAmount)) {
                        bot.setMoney(bot.getMoney() - outbidAmount);
                        outbidAmount = bot.getMoney() / 5;
                        boardMoney += outbidAmount;
                        return outbidAmount;
                    } else {
                        botHasNoEnoughMoney(player, bot, outbidAmount);
                        return 0;
                    }
                } else if (random >= 4) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return -1;
                }
            }
            if (bot.getMoney() / 3 >= outbidAmount && bot.getMoney() / 7 < outbidAmount) {
                if (Check.diff3StartCheck(hands)) {
                    if (bot.canBotPay(outbidAmount)) {
                        System.out.println("Bot decided to outbid your bet.");
                        bot.setMoney(bot.getMoney() - outbidAmount);
                        outbidAmount = bot.getMoney() / 4;
                        boardMoney += outbidAmount;
                        return outbidAmount;
                    } else {
                        System.out.println("Bot checks.");
                        botHasNoEnoughMoney(player, bot, outbidAmount);
                        return 0;
                    }
                } else if (random >= 6) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return -1;
                }
            }
            if (bot.getMoney() / 7 >= outbidAmount) {
                if (Check.diff3StartCheck(hands)) {
                    if (bot.canBotPay(outbidAmount)) {
                        System.out.println("Bot decided to outbid your bet.");
                        bot.setMoney(bot.getMoney() - outbidAmount);
                        outbidAmount = bot.getMoney() / 3;
                        boardMoney += outbidAmount;
                        return outbidAmount;
                    } else {
                        System.out.println("Bot checks.");
                        botHasNoEnoughMoney(player, bot, outbidAmount);
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
            if (check >= 9) {
                if (bot.canBotPay(outbidAmount)) {
                    System.out.println("Bot decided to outbid your bet. He goes allin.");
                    bot.setMoney(bot.getMoney() - outbidAmount);
                    outbidAmount = bot.getMoney();
                    boardMoney += outbidAmount;
                    allIn = true;
                    return outbidAmount;
                } else {
                    System.out.println("Bot checks.");
                    botHasNoEnoughMoney(player, bot, outbidAmount);
                    return 0;
                }
            }
            if (check >= 7) {
                if (bot.canBotPay(outbidAmount)) {
                    System.out.println("Bot decided to outbid your bet.");
                    bot.setMoney(bot.getMoney() - outbidAmount);
                    outbidAmount = bot.getMoney() / 2;
                    boardMoney += outbidAmount;
                    return outbidAmount;
                } else {
                    System.out.println("Bot checks.");
                    botHasNoEnoughMoney(player, bot, outbidAmount);
                    return 0;
                }
            }

            if (check >= 5) {
                if (bot.canBotPay(outbidAmount)) {
                    System.out.println("Bot decided to outbid your bet.");
                    bot.setMoney(bot.getMoney() - outbidAmount);
                    outbidAmount = bot.getMoney() / 4;
                    boardMoney += outbidAmount;
                    return outbidAmount;
                } else {
                    System.out.println("Bot checks.");
                    botHasNoEnoughMoney(player, bot, outbidAmount);
                    return 0;
                }
            }

            if (check >= 3) {
                if (bot.canBotPay(outbidAmount)) {
                    System.out.println("Bot decided to outbid your bet.");
                    bot.setMoney(bot.getMoney() - outbidAmount);
                    outbidAmount = bot.getMoney() / 8;
                    boardMoney += outbidAmount;
                    return outbidAmount;
                } else {
                    System.out.println("Bot checks.");
                    botHasNoEnoughMoney(player, bot, outbidAmount);
                    return 0;
                }
            }

            if (check >= 2) {
                if (bot.canBotPay(outbidAmount)) {
                    System.out.println("Bot decided to outbid your bet.");
                    bot.setMoney(bot.getMoney() - outbidAmount);
                    outbidAmount = bot.getMoney() / 10;
                    boardMoney += outbidAmount;
                    return outbidAmount;
                } else {
                    System.out.println("Bot checks.");
                    botHasNoEnoughMoney(player, bot, outbidAmount);
                    return 0;
                }
            }

            if (bot.getMoney() / 2 < outbidAmount) {
                if (check < 7) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return -1;
                }
            }
            if (bot.getMoney() / 2 >= outbidAmount && bot.getMoney() / 4 < outbidAmount) {
                if (check < 5) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return -1;
                }
            }
            if (bot.getMoney() / 4 >= outbidAmount && bot.getMoney() / 6 < outbidAmount) {
                if (check < 4) {
                    System.out.println("\nBot decided to pass, round is now over.");
                    return -1;
                }
            }
        }

        System.out.println("\nBot decided to check.");
        if (bot.canBotPay(outbidAmount)) {
            bot.setMoney(bot.getMoney() - outbidAmount);
            boardMoney += outbidAmount;
        } else {
            botHasNoEnoughMoney(player, bot, outbidAmount);
        }
        return 0;
    }

    //******************* HERE ROUND 4 IS OVER, TIME TO CHECK WHO WON**********************

    /**
     * It's used to declare the winner of round.
     */
    static void whoWon(Player player, Bot bot, ArrayList<Card> hands, ArrayList<Card> board) {
        System.out.println("Betting is over, it's time to check who won");
        int playerStrength = 0;
        int botStrength = 0;
        ArrayList<Card> playerHand = new ArrayList<>();
        ArrayList<Card> botHand = new ArrayList<>();
        playerHand.add(hands.get(1));
        playerHand.add(hands.get(3));
        botHand.add(hands.get(0));
        botHand.add(hands.get(2));
        playerStrength = Check.finalCheck(playerHand, board);
        botStrength = Check.finalCheck(botHand, board);
        System.out.println("These are player's cards:");
        System.out.println(hands.get(1));
        System.out.println(hands.get(3));
        System.out.println("These are bot's cards");
        System.out.println(hands.get(0));
        System.out.println(hands.get(2));

        if (playerStrength > botStrength) {
            System.out.println("Player wins: " + boardMoney + " money.");
            roundOver(player, bot, 0);
        }
        if (playerStrength < botStrength) {
            System.out.println("Bot wins: " + boardMoney + " money.");
            roundOver(player, bot, 1);
        }
        if (playerStrength == botStrength) {
            int result = Check.equalHands(player, bot, hands);
            roundOver(player, bot, result);
        }
    }

    static boolean ifGameISon(Player player, Bot bot, int bigHand) {

        if (player.getMoney() < bigHand) {
            System.out.println("Game is over. Bot wins the game.");
            return false;
        }
        if (bot.getMoney() < bigHand) {
            System.out.println("Game is over. Players wins the game.");
            return false;
        }
        return true;
    }
}