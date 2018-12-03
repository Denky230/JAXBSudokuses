
package jaxbsudokuses;

import java.io.IOException;
import java.util.List;
import model.sudokus.Sudokus;
import model.sudokus.Sudokus.Sudoku;
import persistence.IOManager;

public class JAXBSudokuses {

    public static void main(String[] args) {
        try {
            // Load data from persistence
            IOManager.loadData();

            List<Sudoku> sudokus = new Sudokus().getSudoku();
            for (Sudoku sudoku : sudokus) {
                System.out.println(sudoku.toString());
            }

            System.out.println(sudokus.size());
        } catch (IOException e) {}
    }
}