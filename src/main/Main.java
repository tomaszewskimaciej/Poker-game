package main;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        game.money = 1000;
        //game.startGame();
        // starts game, asks for name, money to play, bot difficulty etc.
        Bot bot = new Bot(game.money, game.difficulty, "Johny");                            //creating new bot with money set in startgame()
        Player player = new Player(game.money, game.name);                                   //creating new player with money set in startgame()
        int bigHand = game.money / 10;
        int smallHand = bigHand / 2;
        int whoHasBigHand = 1;                                                              //bighand == 1 = you have bighand, 2 means bot has bighand
        /*instead of creating arraylist for each player we create 1 arraylist that is responsible for hands of both players.
        First card always goes to bot, so index 0 and 2 will be bot's cards and indexes 1 and 3 will be player's cards.
        * */


        //creating an array that is our "deck"
        ArrayList<Card> cardsDeck = new ArrayList<>(52);
        //creating an array that is responsible for our hands
        ArrayList<Card> handsCards = new ArrayList<>(4);
        //creating an array that holds board cards
        ArrayList<Card> boardCards = new ArrayList<>(5);

        boolean playing = true;

        //boolean to check whether the game is still on

        while (playing) {
            boolean round = true;
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
                //it returns values, -1 means player passed and round is over, 0 means he paid the difference
                if (game.playerSmallHand(player, bigHand, smallHand) == 0) {
                    System.out.println("Both players paid big hand amount.");
                    System.out.println("It's time to put 3 cards on board.");
                } else {
                    bot.setMoney(bot.getMoney() + game.boardMoney);
                    game.boardMoney = 0;
                    round = false;
                }
            }
            game.gameState(player, bot, handsCards, boardCards);
            if (round) {
                game.round2(boardCards, cardsDeck);
                int playerDecision = game.round2Play(player);
                if (playerDecision == 0) {
                    int decision = game.round2PlayerChecks(bot);
                    if (decision != 0) {
                        if (!game.round2BotOutbidded(player, bot, decision)) {
                            round = false;
                            bot.setMoney(bot.getMoney() + game.boardMoney);
                            game.boardMoney = 0;
                        } else {
                            System.out.println("Both players checked, it's time for 4rd card.");
                        }
                        //here both check so time for 4rd card on board
                    }
                } else {
                    round = game.round2PlayerOutbids(player, bot, playerDecision, handsCards);
                }
            }
            // HERE THE SECOND ROUND IS OVER, 3 cards were put on board, both players checked, time for 4rd card on a board.
            game.gameState(player, bot, handsCards, boardCards);
            if(round){
                game.round3(boardCards, cardsDeck);
            }
            playing = false;
        }

    }
}


