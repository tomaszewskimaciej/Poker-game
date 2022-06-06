package main;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        game.money = 1000;
        game.startGame();
        Bot bot = new Bot(game.money, game.difficulty, "Johny");
        Player player = new Player(game.money, game.name);
        int bigHand = game.money / 10;
        int smallHand = bigHand / 2;

        /**
         * bighand == 1 = you have bighand, 2 means bot has bighand
         */
        int whoHasBigHand = 1;
        boolean playing = true;


        /*instead of creating arraylist for each player we create 1 arraylist that is responsible for hands of both players.
        First card always goes to bot, so index 0 and 2 will be bot's cards and indexes 1 and 3 will be player's cards.
        * */

        //creating an array that is our "deck"
        ArrayList<Card> cardsDeck = new ArrayList<>(52);
        //creating an array that is responsible for our hands
        ArrayList<Card> handsCards = new ArrayList<>(4);
        //creating an array that holds board cards
        ArrayList<Card> boardCards = new ArrayList<>(5);

        while (playing) {
            System.out.println("\n\n");
            System.out.println("____________________________________________________________________________________________________");
            boolean round = true;
            game.allIn = false;
            game.putCardsInDeck(cardsDeck);
            game.shuffleDeck(cardsDeck);
            game.dealCards(cardsDeck, handsCards);
            game.boardMoney = 0;
            //at this point we have  already  dealt cards to each player
            game.myHand(handsCards);
            game.afterDealing(whoHasBigHand, bigHand, smallHand, player, bot);
            if (game.difficulty == 1) {
                game.checkBotCards(handsCards);
            }
            //bighand == 1 means you have bighand and it's bot's turn
            if (whoHasBigHand == 1) {
                if (game.botSmallHand(bot, handsCards, bigHand, smallHand) == 0) {
                    System.out.println("Both players paid big hand amount.");
                    System.out.println("It's time to put 3 cards on board.");
                } else {
                    player.setMoney(player.getMoney() + game.boardMoney);
                    game.boardMoney = 0;
                    round = false;
                }
                //bighand == 2 means you have small hand and it's your turn
            } else {
                if (game.playerSmallHand(player, bigHand, smallHand) == 0) {
                    System.out.println("Both players paid big hand amount.");
                    System.out.println("It's time to put 3 cards on board.");
                } else {
                    bot.setMoney(bot.getMoney() + game.boardMoney);
                    game.boardMoney = 0;
                    round = false;
                }
            }
            // HERE THE 1ST ROUND IS OVER, CARDS WERE DEALT, BIGHANDS WERE PAID, TIME FOR 3 CARDS ON BOARD
            System.out.println("\n");
            game.round2(boardCards, cardsDeck);
            if (!game.allIn) {
                if (round) {
                    game.gameState(player, bot, handsCards, boardCards);
                    int playerDecision = game.roundPlay(player, bot);
                    if (playerDecision == 0) {
                        int decision = game.round2PlayerChecks(bot, handsCards, boardCards);
                        if (decision != 0) {
                            if (!game.roundBotOutbid(player, bot, decision)) {
                                round = false;
                                game.roundOver(player, bot, 1);
                            } else {
                                System.out.println("Both players checked, it's time for 4rd card.");
                            }
                        }
                    } else {
                        //if we here it means that player outbidded and bot needs to decide whether to check or pass
                        round = game.round2PlayerOutbids(player, bot, playerDecision, handsCards, boardCards);
                        if (!round) {
                            player.setMoney(player.getMoney() + game.boardMoney);
                            game.boardMoney = 0;
                        }
                    }
                }
            }
            // HERE THE 2nd ROUND IS OVER, 3 cards were put on board, both players checked, time for 4rd card on a board.
            System.out.println("\n");
            game.round3(boardCards, cardsDeck);
            if (!game.allIn) {
                if (round) {
                    game.gameState(player, bot, handsCards, boardCards);
                    int playerDecision = game.roundPlay(player, bot);
                    if (playerDecision == 0) {
                        int decision = game.round3PlayerChecks(bot, handsCards, boardCards);
                        if (decision != 0) {
                            if (!game.roundBotOutbid(player, bot, decision)) {
                                round = false;
                                game.roundOver(player, bot, 1);
                            } else {
                                System.out.println("Both players checked, it's time for 4rd card.");
                            }
                        }
                    } else {
                        //if we here it means that player outbidded and bot needs to decide whether to check or pass
                        round = game.round3PlayerOutbids(player, bot, playerDecision, handsCards, boardCards);
                        if (!round) {
                            game.roundOver(player, bot, 0);
                        }
                    }
                }
            }
            // HERE THE 3rd ROUND IS OVER, 4 cards were put on board, both players checked, time for last card on a board.
            System.out.println("\n");
            game.round4(boardCards, cardsDeck);
            if (!game.allIn) {
                if (round) {
                    game.gameState(player, bot, handsCards, boardCards);
                    int playerDecision = game.roundPlay(player, bot);                                                   //player decides to checks or outbids
                    if (playerDecision == 0) {                                                                          //if player checks here we go:
                        int decision = game.round4PlayerChecks(bot, handsCards, boardCards);                            //bot needs to decide what to do, check or outbid
                        if (decision != 0) {                                                                            //if bot decided to outbid here we go:
                            if (!game.roundBotOutbid(player, bot, decision)) {                                          //if player decided to pass after bot outbided:
                                round = false;
                                game.roundOver(player, bot, 1);
                            } else {                                                                                    //if player decided to check after bot outbided:
                                System.out.println("Both players checked, it's time for 4rd card.");
                            }
                        }
                    } else {                                                                                            //if player outbided here we go:
                        int decision = game.round4PlayerOutbids(player, bot, playerDecision, handsCards, boardCards);
                        if (decision == -1) {
                            round = false;
                            game.roundOver(player, bot, 1);
                        }
                        if (decision > 0) {
                            if (!game.roundBotOutbid(player, bot, decision)) {
                                round = false;
                                game.roundOver(player, bot, 1);
                            } else {
                                System.out.println("Both players checked.");
                            }
                        }
                    }
                }
            }
            //HERE THE ROUND 4th IS OVER, BOTH PLAYERS CHECKED TO THE FINAL STAGE, NONE PASSED, NOW WE NEED TO CHECK WHO WINS
            if (round) {
                System.out.println("\n");
                game.gameState(player, bot, handsCards, boardCards);
                game.whoWon(player, bot, handsCards, boardCards);
            }
            playing = game.ifGameISon(player, bot, bigHand);
            if (whoHasBigHand == 1) {
                whoHasBigHand = 2;
            } else {
                whoHasBigHand = 1;
            }
        }

    }
}


