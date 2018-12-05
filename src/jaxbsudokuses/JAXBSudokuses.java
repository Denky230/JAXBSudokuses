
package jaxbsudokuses;

import management.Manager;
import utils.Reader;

public class JAXBSudokuses {

    public static void main(String[] args) {
        // Menu variables
        boolean exit = false;
        int menuOption;

        try {
            // Load data from persistence
            Manager.initApp();

            while (!exit) {
                System.out.println(
                        "\n*** Sudokuses ***\n"
                        + "1 - Log in\n"
                        + "2 - Register\n"
                        + "0 - Exit"
                );

                menuOption = Reader.nextInt(2);
                switch (menuOption) {
                    case 1: // LOG IN
                        Manager.loginUser();
                        break;
                    case 2: // REGISTER
                        Manager.registerUser();
                        break;
                    case 0: // EXIT
                        exit = true;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}