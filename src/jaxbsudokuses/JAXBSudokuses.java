
package jaxbsudokuses;

import java.io.IOException;
import java.util.List;
import management.Manager;
import model.sudokus.Sudokus.Sudoku;

public class JAXBSudokuses {

    public static void main(String[] args) {
        try {
            // Load data from persistence
            Manager.initApp();

            List<Sudoku> sudokus = Manager.getSudokus().getSudoku();
            System.out.println(sudokus.size());

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}