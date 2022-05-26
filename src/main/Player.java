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

    public boolean  canPlayerPay(int amount){
        if(amount>money){
            return false;
        }else{
            return true;
        }
    }

}
