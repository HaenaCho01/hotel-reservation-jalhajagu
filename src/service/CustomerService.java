package service;

import entity.Customer;
import util.ConsoleUtil;

import java.util.HashMap;

public class CustomerService {
    private ConsoleUtil consoleUtil;
    public HashMap<String, Customer> customers;  // 전화번호를 key 값으로

    public CustomerService(){
        this.customers = new HashMap<>();
        this.consoleUtil = new ConsoleUtil();
    }

    public void checkMoney(Customer customer) { // 소지금 조회하기
        if (customer.getMoney() != 0) { // 고객 소지금 출력
            System.out.println("고객님의 소지금은 " + customer.getMoney() + "원 입니다.");
            consoleUtil.printLine();
        } else if (customer.getMoney() == 0){ // 소지금이 없을 시 출력
            System.out.println("고객님의 소지금이 없습니다.");
            consoleUtil.printLine();
        }
    }
}
