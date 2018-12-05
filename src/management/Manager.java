
package management;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;
import model.register.Register;
import model.register.Register.Entry;
import model.sudokus.Sudokus;
import model.sudokus.Sudokus.Sudoku;
import model.users.Users;
import model.users.Users.User;
import persistence.IOManager;

public class Manager {

    private static User userLoggedIn;
    private static Sudoku activeSudoku;

    private static Sudokus sudokus;
    private static Register register;

    public static void initApp() throws Exception {
        IOManager.loadData();
    }

    public static void registerUser() throws IOException, JAXBException {
        // Add new user to memory + persistence
        User user = ViewManager.askForRegister();
        UserManager.addUser(user);
        IOManager.marshallUsers();

        // Show app menu
        ViewManager.showAppMenu();
    }
    public static void loginUser() throws IOException {
        // Log user in
        userLoggedIn = ViewManager.askForLogin();

        // Show app menu
        ViewManager.showAppMenu();
    }

    static void appMenu(int menuOption) throws IOException, JAXBException {
        switch (menuOption) {
            case 1: // CHANGE PASSWORD
                changeUserPassword();
                break;
            case 2: // START SUDOKU
                startSudoku();
                break;
            case 3: // END SUDOKU
                endSudoku();
                break;
            case 4: // CHECK AVG SDK TIME
                break;
        }
    }

    static void changeUserPassword() throws IOException, JAXBException {
        String newPassword = ViewManager.askForPassword();
        // TO DO: check newPassword != currPassword
        // TO DO: user feedback

        userLoggedIn.setPassword(newPassword);
        IOManager.marshallUsers();
    }

    static void startSudoku() {
        List<Sudokus.Sudoku> sudokusNotPlayed = sudokus.getSudoku();
        List<Register.Entry.Sudoku> userPlayedSudokus = RegisterManager.getSudokusByUser(userLoggedIn);

        for (Sudoku npSudoku : sudokusNotPlayed) {
            for (Register.Entry.Sudoku pSudoku : userPlayedSudokus) {
                if (compareSudokus(npSudoku, pSudoku))
                    System.out.println(sudokusNotPlayed.remove(npSudoku));
                // TO DO: cant remove from own looping collection
            }
        }

        // Get random sudoku yet to complete by userLoggedIn
        activeSudoku = sudokus.getSudoku().get((int)(Math.random() * sudokusNotPlayed.size()));
    }
    static void endSudoku() throws JAXBException {
        Entry entry = new Entry();

        // Convert sudoku to Register.sudoku
        Register.Entry.Sudoku sudoku = convertSudoku(activeSudoku);
        // Convert user to Register.user
        Register.Entry.User user = convertUser(userLoggedIn);

        entry.setSudoku(sudoku);
        entry.setUser(user);
        entry.setTime((int)(Math.random() * 11));

        // Add new register entry to memory + persistence
        RegisterManager.getRegister().getEntry().add(entry);
        IOManager.marshallRegister();
    }

    /**
     * Converts a Sudokus.sudoku instance into a Register.sudoku instance.
     * @param sudoku Sudokus.sudoku
     * @return Register.sudoku
     */
    static Register.Entry.Sudoku convertSudoku(Sudokus.Sudoku sudoku) {
        Register.Entry.Sudoku registerSudoku = new Register.Entry.Sudoku();

        registerSudoku.setDescription(sudoku.getDescription());
        registerSudoku.setLevel(sudoku.getLevel());
        registerSudoku.setProblem(sudoku.getProblem());
        registerSudoku.setSolution(sudoku.getSolution());

        return registerSudoku;
    }
    static Sudokus.Sudoku convertSudoku(Register.Entry.Sudoku registerSudoku) {
        Sudokus.Sudoku sudoku = new Sudokus.Sudoku();

        sudoku.setDescription(registerSudoku.getDescription());
        sudoku.setLevel(registerSudoku.getLevel());
        sudoku.setProblem(registerSudoku.getProblem());
        sudoku.setSolution(registerSudoku.getSolution());

        return sudoku;
    }
    /**
     * Converts a Users.user instance into a Register.user instance.
     * @param user Users.user
     * @return Register.user
     */
    static Register.Entry.User convertUser(Users.User user) {
        Register.Entry.User registerUser = new Register.Entry.User();

        registerUser.setName(user.getName());
        registerUser.setUsername(user.getUsername());
        registerUser.setPassword(user.getPassword());

        return registerUser;
    }

    public static boolean compareUsers(Users.User user, Register.Entry.User registerUser) {
        if (!user.getName().equals(registerUser.getName()) ||
            !user.getPassword().equals(registerUser.getPassword()) ||
            !user.getUsername().equals(registerUser.getUsername())) {
            return false;
        }

        return true;
    }
    public static boolean compareSudokus(Sudokus.Sudoku sudoku, Register.Entry.Sudoku registerSudoku) {
        if (!sudoku.getDescription().equals(registerSudoku.getDescription()) ||
            !sudoku.getLevel().equals(registerSudoku.getLevel()) ||
            !sudoku.getProblem().equals(registerSudoku.getProblem()) ||
            !sudoku.getSolution().equals(registerSudoku.getSolution())) {
            return false;
        }

        return true;
    }

    public static Sudokus getSudokus() { return sudokus; }
    public static void setSudokus(Sudokus s) { sudokus = s; }
}