# Poker game

### About

This is a console version of a poker game. It’s played 1 v 1 against a bot, you can choose from 4 different bot levels (You can read more about differences between each bot level in the bot section).


### Game stages.

Game can be divided into 4 stages.  
**Stage 1:** Dealing cards, paying big and small hands. Outbidding is not allowed yet.  
**Stage 2:** 3 cards on board, you can outbid.  
**Stage 3:** 4th card on board, another outbidding time.  
**Stage 4:** 5th card on board, final outbidding.  
**After stage 4:** cards and combinations are compared and the winner is announced.  



### Bots

There are 4 bot levels, where level 1 is the weakest one.     
**Level 1:** You are allowed to see bot’s cards. It’ll never pass or outbid the player. 
  
**Level 2:** It’ll never pass or outbid the player.
  
**Level 3:** He’s able to analyze the starting hand (only the first 2 cards), you can read about the starting hand it considers as good below. It might outbid the player if his starting hand was good and might pass if the player bids too much. 
  
**Level 4:** It’s able to compare its hand with board cards and search for combinations of cards. It might play aggressively (e.g. outbid by huge amounts of money) if it has a good combination. If the player bids too much it’ll check all the combinations before making a decision. It’s able to analyze the starting hand like a level 3 bot. 

  
Bots are very unlikely to pass after 2 first cards.    

The starting hands that bots level 3 and 4 consider as ‘good’ are:  
pair, 2 high value cards where 10 is the first high value card, 2 cards of the same suit.  
