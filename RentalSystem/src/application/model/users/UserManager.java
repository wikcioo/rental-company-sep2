package application.model.users;

import java.util.ArrayList;

public class UserManager {
    private ArrayList<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> newUsers) {
        this.users = newUsers;
    }

    public void clear() {
        users.clear();
    }
}
