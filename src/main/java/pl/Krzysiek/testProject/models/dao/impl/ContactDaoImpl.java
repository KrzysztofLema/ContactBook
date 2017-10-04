package pl.Krzysiek.testProject.models.dao.impl;

import pl.Krzysiek.testProject.models.MySqlConnector;
import pl.Krzysiek.testProject.models.dao.ContactDao;

import java.util.List;

public class ContactDaoImpl implements ContactDao {

    private MySqlConnector connector = MySqlConnector.getInstace();

    @Override
    public List<String> getAllUserNames(String name) {
        return null;
    }

    @Override
    public String getNumber(String contact) {
        return null;
    }

    @Override
    public boolean addContact(String name, String number) {
        return false;
    }

    @Override
    public void removeContact(String name) {

    }

    @Override
    public boolean editContact(String name, String number) {
        return false;
    }
}
