
package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import management.Manager;
import model.sudokus.Sudokus.Sudoku;

public class IOManager {

    // Operative System's path separator
    static final String PATH_SEPARATOR = File.separator;
    static final String ROOT_PATH = System.getProperty("user.dir");
    static final String PERSISTENCE_DIRECTORY_PATH = ROOT_PATH + PATH_SEPARATOR + "persistence";

    public static void loadData() throws Exception {
        File sudokusXML = new File(PERSISTENCE_DIRECTORY_PATH + PATH_SEPARATOR + "sudokus.xml");
        if (sudokusXML.createNewFile()) {
            // Read from TXT
            readSudokusTXT();
        } else {
            // Read from XML
            readSudokusXML();
        }
    }
    static void readSudokusTXT() throws Exception {
        try {
            File sudokusTXT = new File(PERSISTENCE_DIRECTORY_PATH + PATH_SEPARATOR + "sudokus.txt");
            FileReader fr = new FileReader(sudokusTXT);
            BufferedReader br = new BufferedReader(fr);

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
                sudoku.setSolution(solution);

                // Add new sudoku to sudokus list
                Manager.getSudokus().getSudoku().add(sudoku);
            }

            // Write Sudokus into XML
            JAXBContext jc = JAXBContext.newInstance(Sudoku.class);

        } catch (JAXBException ex) {
            throw new JAXBException("There was an error when writting into persistence");
        }
    }
    static void readSudokusXML() {
        
    }
}