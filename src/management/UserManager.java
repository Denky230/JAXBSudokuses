
package management;

import java.util.List;
import model.users.Users;
import model.users.Users.User;

public class UserManager {

    private static List<User> users;

    public static void setUp() {
        users = new Users().getUser();
    }

    public static boolean findUserByName(String username) {
        for (User user : users) {
            if (user.getName().equals(username))
                return true;
        }

        return false;
    }

    public static User validateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }

        // TO DO: make custom exception
        throw new NullPointerException("User not found");
    }

    public static boolean addUser(User user) {
        if (!findUserByName(user.getName()))
            return users.add(user);

        return false;
    }
}