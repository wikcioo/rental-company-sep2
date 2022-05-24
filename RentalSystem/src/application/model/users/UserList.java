package application.model.users;

import java.util.ArrayList;

public class UserList {
    private ArrayList<User> users;

    public UserList() {
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
