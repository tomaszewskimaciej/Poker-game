package main;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        game.money = 1000;
        game.difficulty = 1;
        //game.startGame();
        //
        // starts game, asks for name, money to play, bot difficulty etc.
        Bot bot = new Bot(game.money, game.difficulty, "Johny");                            //creating new bot with money set in startgame()
        Player player = new Player(game.money, game.name);                                   //creating new player with money set in startgame()
        int bigHand = game.money / 10;
        int smallHand = bigHand / 2;
        int whoHasBigHand = 2;                                                              //bighand == 1 = you have bighand, 2 means bot has bighand
        /*instead of creating arraylist for each player we create 1 arraylist that is responsible for hands of both players.
        First card always goes to bot, so index 0 and 2 will be bot's cards and indexes 1 and 3 will be player's cards.
        * */

//        System.out.println(cardsDeck.size());
//        System.out.println(cardsDeck);
        ArrayList<Card> cardsDeck = new ArrayList<>(52);                        //creating array that is our "deck"
        ArrayList<Card> handsCards = new ArrayList<>(4);                        //creating array that is responsible for our hands
        boolean playing = true;                                                             //boolean to check whether the game is still on
        while (playing) {
            boolean isRoundOver = false;
            game.putCardsInDeck(cardsDeck);
            game.shuffleDeck(cardsDeck);
            game.dealCards(cardsDeck, handsCards);
            //at this point we have  already  dealt cards to each player
            game.myHand(handsCards);

            game.afterDealing(whoHasBigHand, bigHand, smallHand, player, bot);
            if (game.difficulty == 1) {
                System.out.println("You chose difficulty 1. These are bot's cards: ");
                System.out.println(handsCards.get(0));
                System.out.println(handsCards.get(2));
                System.out.println("\n");
            }

            if (whoHasBigHand == 1) {
                game.botSmallHand(bot, handsCards, game.difficulty, bigHand, smallHand);       //bighand == 1 means you have bighand and it's bot's turn

                whoHasBigHand = 2;
            } else {                                                                          //bighand == 2 means you have small hand and it's your turn
                game.playerSmallHand(player, handsCards, bigHand, smallHand);                 //it returns values, -1 means player passed and round is over, 0 means he paid the difference, 1 means he outbid
                whoHasBigHand = 1;
            }
            playing = false;
        }


    }
}


