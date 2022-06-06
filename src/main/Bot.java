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
    About bots level, there are 3 different bots level, where 1 is the easiest one:
        Level 1: In this difficulty you are allowed to see bot's cards. Will never pass, will never outbid player.
        Level 2: Will never pass, will never outbid player.
        Level 3: Might pass if player bids too much, analyses starting hand but he may check even though he doesn't consider his hand as good. To check what is good hand look at bot level 4. He is not able to compare his cards with board cards.
        Level 4: Highest level possible atm. Analyses starting hand, hands he considers as good are pairs; 2 high value cards, where he considers 10 as the first high value card; 2 cards of the same suit. Compares his cards to board cards.
        TODO Level 4: This one could be able to check its own cards and player's card and then calculate its chance to win, using math algorithm.

        Outbiding after 2 first card is not allowed, bots never pass after first 2cards.
        Game is over when either of players has no money left or when either of players have no money for bighand.

     */
}
