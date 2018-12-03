
package management;

import java.io.IOException;
import model.sudokus.Sudokus;
import persistence.IOManager;

public class Manager {

    private static Sudokus sudokus;

    public static void initApp() throws IOException {
        sudokus = new Sudokus();
        IOManager.loadData();
    }

    public static Sudokus getSudokus() { return sudokus; }
}