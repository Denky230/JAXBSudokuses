
package management;

import model.sudokus.Sudokus;
import model.users.Users;
import model.users.Users.User;
import persistence.IOManager;

public class Manager {

    private static Users users;
    private static User userLoggedIn;
    private static Sudokus sudokus;

    public static void initApp() throws Exception {
        IOManager.loadData();
    }

    public static Sudokus getSudokus() { return sudokus; }
    public static void setSudokus(Sudokus s) { sudokus = s; }
}