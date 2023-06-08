package service;

import entity.Customer;
import exception.CustomerNotFoundException;
import exception.DuplicatedCustomerException;
import exception.PasswordNotMatchedException;

import java.util.HashMap;

public class CustomerService {
    public HashMap<String, Customer> customers;  // 전화번호를 key 값으로

    public CustomerService() {
        this.customers = new HashMap<>();
    }


    public Customer findCustomer(String phoneNumber) {
        if (!exist(phoneNumber)) {
            throw new CustomerNotFoundException("해당 고객을 찾을 수 없습니다.");
        }
        return this.customers.get(phoneNumber);
    }

    public void addCustomer(Customer customer) {
        String phoneNumber = customer.getPhoneNumber();
        if (exist(phoneNumber)) {
            throw new DuplicatedCustomerException("이미 존재하는 고객입니다.");
        }
        customers.put(phoneNumber, customer);
    }

    private boolean exist(String phoneNumber) {
        return customers.containsKey(phoneNumber);
    }

    public void checkPassword(Customer customer, String password) {
        if (!customer.getPassword().equals(password)) {
            throw new PasswordNotMatchedException("비밀번호가 올바르지 않습니다. 다시 시도해주세요.");
        }
        System.out.println("환영합니다 " + customer.getName() + "님");
    }
}