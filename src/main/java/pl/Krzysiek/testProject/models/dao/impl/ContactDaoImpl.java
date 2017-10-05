package pl.Krzysiek.testProject.models.dao.impl;

import pl.Krzysiek.testProject.models.MySqlConnector;
import pl.Krzysiek.testProject.models.UserSession;
import pl.Krzysiek.testProject.models.dao.ContactDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDaoImpl implements ContactDao {

    private MySqlConnector connector = MySqlConnector.getInstace();
    private UserSession session = UserSession.getInstance();

    @Override
    public List<String> getAllContactNames(String name) {

        List<String> nameList = new ArrayList<>();
        try {
            PreparedStatement statement = connector.getConnection().prepareStatement(
                    "SELECT name FROM contact INNER JOIN user ON user.id = contact.user WHERE user.userName=?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                nameList.add(resultSet.getString("name"));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nameList;
    }

    @Override
    public String getNumber(String contact) {
        try {
            PreparedStatement statement = connector.getConnection().prepareStatement(
                    "SELECT number FROM contact WHERE name=?");
            statement.setString(1, contact);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            return resultSet.getString("number");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean addContact(String name, String number) {
        try {
            PreparedStatement statement = connector.getConnection().prepareStatement(
                    "INSERT INTO contact VALUES (?,?,?,?)");
            statement.setInt(1, 0);
            statement.setString(2, name);
            statement.setString(3, number);
            statement.setInt(4, session.getId());
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeContact(String name) {

    }

    @Override
    public boolean editContact(String name, String number) {
        try {
            PreparedStatement statement = connector.getConnection().prepareStatement(
                    "UPDATE contact SET number=? WHERE name=?");
            statement.setString(1, name);
            statement.setString(2, number);
            statement.execute();
            statement.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
