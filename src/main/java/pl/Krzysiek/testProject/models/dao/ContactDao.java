package pl.Krzysiek.testProject.models.dao;

import java.util.List;

public interface ContactDao {
    List<String> getAllUserNames(String name);

    String getNumber(String contact);

    boolean addContact(String name, String number);

    void removeContact(String name);

    boolean editContact(String name, String number);
}
