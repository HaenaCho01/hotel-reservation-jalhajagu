package service;

import entity.Customer;

import java.util.HashMap;

public class CustomerService {
    public HashMap<String, Customer> customers;  // 전화번호를 key 값으로

    public CustomerService(){
        this.customers = new HashMap<>();
    }
}
