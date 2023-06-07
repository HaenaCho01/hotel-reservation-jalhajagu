package service;

import entity.Customer;

import java.util.HashMap;

public class CustomerService {
    HashMap<String, Customer> customers;  // 전화번호를 key값으로 함

    public CustomerService(){
        this.customers = new HashMap<>();
    }
}
