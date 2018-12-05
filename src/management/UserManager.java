
package management;

import model.users.Users;
import model.users.Users.User;

public class UserManager {

    private static Users users;

    public static Users getUsers() { return users; }
    public static void setUsers(Users u) { users = u; }

    public static boolean findUserByName(String username) {
        for (User user : users.getUser()) {
            if (user.getName().equals(username))
                return true;
        }

        return false;
    }

    public static User validateUser(String username, String password) {
        for (User user : users.getUser()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }

        // TO DO: custom exception
        throw new NullPointerException("Wrong credentials");
    }

    /**
     * User registration.
     * @param user User to register
     */
    public static void addUser(User user) {
        if (!findUserByName(user.getName()))
            users.getUser().add(user);

        // TO DO: else throw register custom exception
    }
}