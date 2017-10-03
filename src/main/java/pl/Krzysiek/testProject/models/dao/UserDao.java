package pl.Krzysiek.testProject.models.dao;

public interface UserDao {
    boolean login(String name, String password);

    boolean register(String name, String password);

    boolean removeUser(int id);
}
