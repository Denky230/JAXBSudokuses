
package management;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.users.Users.User;
import utils.Reader;

public class ViewManager {

    public static User askForLogin() throws IOException {
        try {
            System.out.println("Username");
            String username = Reader.nextString();
            System.out.println("Password");
            String password = Reader.nextString();

            // return User with give username and password if exists
            return UserManager.validateUser(username, password);

        } catch (Exception e) {
            // TO DO: catch validateUser custom exception
            throw new IOException();
        }
    }

    public static void askForRegister() {
        try {
            System.out.println("Name: (surname, name)");
            String name = Reader.nextString();

            // Make sure name is not taken
            while (UserManager.findUserByName(name)) {
                System.out.println("Name taken. Choose a different one!");
                name = Reader.nextString();
            }

            // TO DO: ask for login

        } catch (IOException e) {
            
        }
    }
}