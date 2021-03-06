
package persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import management.Manager;
import management.RegisterManager;
import management.UserManager;
import model.register.Register;
import model.sudokus.Sudokus;
import model.sudokus.Sudokus.Sudoku;
import model.users.Users;

public class IOManager {

    // Operative System's path separator
    static final String PATH_SEPARATOR = File.separator;
    // Project path
    static final String ROOT_PATH = System.getProperty("user.dir");

    static final String PERSISTENCE_DIRECTORY_PATH = ROOT_PATH + PATH_SEPARATOR + "persistence" + PATH_SEPARATOR;
    static final File SUDOKUS_TXT = new File(PERSISTENCE_DIRECTORY_PATH + "sudokus.txt");
    static final File SUDOKUS_XML = new File(PERSISTENCE_DIRECTORY_PATH + "sudokus.xml");
    static final File USERS_XML = new File(PERSISTENCE_DIRECTORY_PATH + "users.xml");
    static final File REGISTER_XML = new File(PERSISTENCE_DIRECTORY_PATH + "history.xml");

    public static void loadData() throws Exception {
        // Check if first time running the app
        if (!SUDOKUS_XML.exists()) {
            // FIRST TIME
            readSudokusFromTXT();
        } else {
            // NOT FIRST TIME
            readSudokusFromXML();
        }

        loadUsers();
        loadRegister();
    }
    static void readSudokusFromTXT() throws IOException, JAXBException {
        try {
            FileReader fr = new FileReader(SUDOKUS_TXT);
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
                sudoku.setSolution(solution);

                // Add new sudoku to sudokus list
                sudokus.getSudoku().add(sudoku);
            }

            // Write sudokus into XML + save in memory
            marshallToXML(sudokus, SUDOKUS_XML);
            Manager.setSudokus(sudokus);

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("There was an error when reading from persistence - Error: " + e.getMessage());
        }
    }
    static void readSudokusFromXML() throws JAXBException {
        // Get data from XML + save in memory
        Sudokus sudokus = (Sudokus) unmarshallXML(Sudokus.class, SUDOKUS_XML);
        Manager.setSudokus(sudokus);
    }

    static void loadUsers() throws JAXBException {
        Users users = new Users();

        if (USERS_XML.exists()) {
            // Get users from persistence
             users = (Users) unmarshallXML(Users.class, USERS_XML);
        }

        // Save users in memory
        UserManager.setUsers(users);
    }
    public static void marshallUsers() throws JAXBException {
        marshallToXML(UserManager.getUsers(), USERS_XML);
    }

    static void loadRegister() throws JAXBException {
        Register register = new Register();

        if (REGISTER_XML.exists()) {
            // Get register from persistence
             register = (Register) unmarshallXML(Register.class, REGISTER_XML);
        }

        // Save register in memory
        RegisterManager.setRegister(register);
    }
    public static void marshallRegister() throws JAXBException {
        marshallToXML(RegisterManager.getRegister(), REGISTER_XML);
    }

    /**
     * Parse data from an XML file using a JAXB class.
     * @param jaxbClass JAXB class
     * @param input XML file
     * @return Object containing XML data
     * @throws JAXBException
     */
    static Object unmarshallXML(Class jaxbClass, File input) throws JAXBException {
        try {
            JAXBContext jc = JAXBContext.newInstance(jaxbClass);
            Unmarshaller um = jc.createUnmarshaller();

            // Parse data from XML + return it
            return um.unmarshal(input);

        } catch (JAXBException e) {
            throw new JAXBException("There was an error when reading from persistence - Error: " + e.getMessage());
        }
    }
    /**
     * Write JAXB object data into an XML file.
     * @param jaxbInstance JAXB object
     * @param output XML file
     * @throws JAXBException
     */
    static void marshallToXML(Object jaxbInstance, File output) throws JAXBException {
        try {
            JAXBContext jc = JAXBContext.newInstance(jaxbInstance.getClass());
            Marshaller m = jc.createMarshaller();
            // Marshall with indentation
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Write into XML
            m.marshal(jaxbInstance, output);

        } catch (JAXBException e) {
            throw new JAXBException("There was an error when writing into persistence - Error: " + e.getMessage());
        }
    }
}