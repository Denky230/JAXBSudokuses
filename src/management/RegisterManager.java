
package management;

import java.util.ArrayList;
import java.util.List;
import model.register.Register;
import model.users.Users;

public class RegisterManager {

    private static Register register;

    public static List<Register.Entry.Sudoku> getSudokusByUser(Users.User user) {
        List<Register.Entry.Sudoku> sudokus = new ArrayList<>();

        for (Register.Entry entry : register.getEntry()) {
            if (Manager.compareUsers(user, entry.getUser()))
                sudokus.add(entry.getSudoku());
        }

        return sudokus;
    }

    public static Register getRegister() { return register; }
    public static void setRegister(Register r) { register = r; }
}