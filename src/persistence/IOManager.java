
package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.sudokus.Sudokus;
import model.sudokus.Sudokus.Sudoku;

public class IOManager {

    // Operative System's path separator
    static final String PATH_SEPARATOR = File.separator;
    static final String ROOT_PATH = System.getProperty("user.dir");
    static final String PERSISTENCE_DIRECTORY_PATH = ROOT_PATH + PATH_SEPARATOR + "persistence";

    public static void loadData() throws IOException {
        File sudokusXML = new File(PERSISTENCE_DIRECTORY_PATH + PATH_SEPARATOR + "sudokus.xml");
        if (sudokusXML.createNewFile()) {
            // Read from TXT
            readSudokusTXT();
        } else {
            // Read from XML
            readSudokusXML();
        }
    }
    static void readSudokusTXT() throws IOException {
        try {
            File sudokusTXT = new File(PERSISTENCE_DIRECTORY_PATH + "sudokus.txt");
            FileReader fr = new FileReader(sudokusTXT);
            BufferedReader br = new BufferedReader(fr);
            Sudokus sudokus = new Sudokus();

            // Read txt file
            String line;
            while ((line = br.readLine()) != null) {
                // Read sudoku data
                String[] difficulty = line.split(" ");
                String problem = br.readLine();
                String solution = br.readLine();

                // Build new sudoku
                Sudoku sudoku = new Sudoku();
                sudoku.setLevel(Integer.parseInt(difficulty[1]));
                sudoku.setDescription(difficulty[2]);
                sudoku.setProblem(problem);
                sudoku.setSolution(Integer.parseInt(solution));

                // Add new sudoku to sudokus list
                sudokus.getSudoku().add(sudoku);
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("No existe el fichero");
        }
    }
    static void readSudokusXML() {
        
    }
}