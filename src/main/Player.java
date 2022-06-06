package main;

public class Player {
    private int money;
    private String name;

    public Player(int money, String name) {
        this.money = money;
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    /**
     * This method is used to check whether player is able to pay "x" money.
     *
     * @return true if player can pay, false if cannot.
     */
    public boolean canPlayerPay(int amount) {
        if (amount > money) {
            return false;
        } else {
            return true;
        }
    }

}
