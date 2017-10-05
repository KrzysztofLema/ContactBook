package pl.Krzysiek.testProject.models.dao.impl;

import pl.Krzysiek.testProject.models.MySqlConnector;
import pl.Krzysiek.testProject.models.UserSession;
import pl.Krzysiek.testProject.models.Utils;
import pl.Krzysiek.testProject.models.dao.UserDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {

    private MySqlConnector connector = MySqlConnector.getInstace();
    private UserSession session = UserSession.getInstance();


    @Override
    public boolean login(String name, String password) {
        try {
            PreparedStatement statement = connector.getConnection().prepareStatement(
                    "SELECT * FROM user WHERE userName = ?"
            );
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            session.setId(resultSet.getInt("id"));
            return resultSet.getString("password").equals(Utils.shaHash(password));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean register(String name, String password) {
        try {
            PreparedStatement statement = connector.getConnection().prepareStatement(
                    "SELECT * FROM user WHERE userName = ?"
            );
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return false;
            }
            PreparedStatement statmentInsert = connector.getConnection().prepareStatement(
                    "INSERT INTO user VALUES (?,?,?)"
            );
            statmentInsert.setInt(1, 0);
            statmentInsert.setString(2, name);
            statmentInsert.setString(3, Utils.shaHash(password));

            statmentInsert.execute();

            statement.close();
            statmentInsert.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeUser(int id) {
        try {
            PreparedStatement statement = connector.getConnection().prepareStatement("DELETE FROM user WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
