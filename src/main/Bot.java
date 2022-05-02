package main;

public  class  Bot {
    private int money=0;
    private int level=0;
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
    /*
    About bots level, there are 3 different bots level, where 1 is the easiest one:
        Level 1: In this difficulty you are allowed to see bot's cards. Will never pass, will never outbid player.
        Level 2: Will never pass, will never outbid player.
        Level 3: Might pass if player bids too much, might outbid if and only if his starting hand (first 2 cards) were pretty good (doesn't check cards put on board).
                 Hands he considers as good are pairs; 2 high value cards, where he considers 10 as the first high value card; 2 cards of the same suit.
        Level 4: Highest level possible atm. Analyses starting hand like level 2 bot and is able to analyse board cards and compare them to its cards.
        TODO Level 4: This one could be able to check its own cards and player's card and then calculate its chance to win, using math algorithm.

        BOTS will never outbid during first round, even bot level4.
     */
}
