package main;

public class Bot {
    private int money = 0;
    private int level = 0;
    private String name;

    public Bot(int money, int aggression, String name) {
        this.money = money;
        this.level = aggression;
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    /**
     * This method is used to check whether bot is able to pay "x" money.
     *
     * @return true if bot can pay, false if cannot.
     */

    public boolean canBotPay(int amount) {
        if (amount > money) {
            return false;
        } else {
            return true;
        }
    }

    /*
    About bots level, there are 4 different bots level, where 1 is the easiest one:
        Level 1: You are allowed to see bot’s cards. It’ll never pass or outbid the player.
        Level 2: It’ll never pass or outbid the player.
        Level 3: He’s able to analyze the starting hand (only the first 2 cards), you can read about the starting hand it considers as good below. It might outbid the player if his starting hand was good and might pass if the player bids too much.
        Level 4: It’s able to compare its hand with board cards and search for combinations of cards. It might play aggressively (e.g. outbid by huge amounts of money) if it has a good combination. If the player bids too much it’ll check all the combinations before making a decision. It’s able to analyze the starting hand like a level 3 bot.

        The starting hands that bots level 3 and 4 consider as ‘good’ are:
        pair, 2 high value cards where 10 is the first high value cards, 2 cards of the same suit.
        TODO Level 5: This one could be able to check its own cards and player's card and then calculate its chance to win, using math algorithm.

        Outbidding after 2 first card is not allowed.
        Game is over when either of players has no money left or when either of players have no money for big hand.

     */
}
