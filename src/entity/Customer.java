package entity;

public class Customer {
    private String name;
    private String phoneNumber;
    private String password;
    private int money;

    public Customer(String name, String phoneNumber, String password, int money) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public int getMoney() {
        return money;
    }

    public boolean canAfford(int price) {
        return this.money >= price;
    }

    public void makePayment(int price) {
        this.money -= price;
    }

    public void getRefund(int price) {
        this.money += price;
    }
}
