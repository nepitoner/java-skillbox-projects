package org.example;

import java.util.HashMap;
import java.util.Map;

public class CustomerStorage {
    private final Map<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws ComponentNumberException,
            WrongFormatNumberException, WrongFormatEmailException {
        final int INDEX_NAME = 0;
        final int INDEX_SURNAME = 1;
        final int INDEX_EMAIL = 2;
        final int INDEX_PHONE = 3;

        String[] components = data.split("\\s+");

        if (components.length != 4) {
            throw new ComponentNumberException("Wrong number of components!");
        }
        if (components[INDEX_PHONE].length() != 12
                || !components[INDEX_PHONE].matches("^\\+[0-9]+$")) {
            throw new WrongFormatNumberException("Wrong number format!");
        }
        if (!components[INDEX_EMAIL].matches("^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[a-z]+$")) {
            throw new WrongFormatEmailException("Wrong email format!");
        }
        String name = components[INDEX_NAME] + " " + components[INDEX_SURNAME];
        storage.put(name, new Customer(name, components[INDEX_PHONE], components[INDEX_EMAIL]));
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    public int getCount() {
        return storage.size();
    }
}