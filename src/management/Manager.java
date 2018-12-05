
package management;

import java.io.IOException;
import javax.xml.bind.JAXBException;
import model.sudokus.Sudokus;
import model.users.Users.User;
import persistence.IOManager;

public class Manager {

    private static User userLoggedIn;
    private static Sudokus sudokus;

    public static void initApp() throws Exception {
        IOManager.loadData();
    }

    public static void registerUser() throws IOException, JAXBException {
        // Add new user to memory + persistence
        User user = ViewManager.askForRegister();
        UserManager.addUser(user);
        IOManager.marshallUsers();

        // Show app menu
        appMenu();
    }

    public static void loginUser() throws IOException {
        // Log user in
        userLoggedIn = ViewManager.askForLogin();

        // Show app menu
        appMenu();
    }

    static void appMenu() throws IOException {
        boolean exit = false;
        int menuOption;

        while (!exit) {
            menuOption = ViewManager.showAppMenu();
            switch (menuOption) {
                case 1: // CHANGE PASSWORD
                    break;
                case 2: // START SUDOKU
                    break;
                case 3: // END SUDOKU
                    break;
                case 4: // CHECK AVG SDK TIME
                    break;
                case 0: // EXIT
                    exit = true;
                    break;
            }
        }
    }

    static void changeUserPassword() {
        System.out.println("");
    }

    public static Sudokus getSudokus() { return sudokus; }
    public static void setSudokus(Sudokus s) { sudokus = s; }
}