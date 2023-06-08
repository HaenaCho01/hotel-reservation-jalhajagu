package service;

import entity.Customer;
import exception.CustomerNotFoundException;

import java.util.HashMap;

public class CustomerService {
    public HashMap<String, Customer> customers;  // 전화번호를 key 값으로 가짐

    public CustomerService(){
        this.customers = new HashMap<>();
    }

    public Customer findOne(String phoneNumber) {
        if(!customers.containsKey(phoneNumber)) {
            throw new CustomerNotFoundException("해당 고객을 찾을 수 없습니다.");
        }
        return this.customers.get(phoneNumber);
    }
}