
package management;

import java.io.IOException;
import model.sudokus.Sudokus;
import model.users.Users.User;
import persistence.IOManager;

public class Manager {

    private static User userLoggedIn;
    private static Sudokus sudokus;

    public static void initApp() throws Exception {
        IOManager.loadData();
        UserManager.setUp();
    }

    public static void loginUser() throws IOException {
        userLoggedIn = ViewManager.askForLogin();
    }

    public static Sudokus getSudokus() { return sudokus; }
    public static void setSudokus(Sudokus s) { sudokus = s; }
}