import com.sun.corba.se.spi.orbutil.fsm.Action;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

/**
 * Author: Aleksandr Borodin
 * Creation date: 11/25/24
 */
public class IO implements Runnable {
    @Override
    public void run() {
        try {
            //System.in input
            System.out.println("IO: Base input");
            System.out.println("Type int");
            int input = System.in.read();
            System.out.println("Im system out stream. Im typed " + input);

            //Scanner input
            System.out.println("IO: Scanner input");
            Scanner scanner = new Scanner(System.in);

            System.out.println("Type text");
            String scanned = scanner.next();
            System.out.println("I was scanned " + scanned);

            //Directory creation
            System.out.println("IO: Directory creation");
            File directory = new File("./newDirectory");
            directory.mkdirs();

            //File creation
            System.out.println("IO: File creation");
            File newFile = new File("./newDirectory/file.txt");
            newFile.createNewFile();
            try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                outputStream.write("im new line".getBytes());
            }

            //Scanner read from file
            System.out.println("IO: Scanner read from file");
            try (FileInputStream inputStream = new FileInputStream(newFile)) {
                String line = new Scanner(inputStream).nextLine();
                System.out.println("Im file - "+ line);
            }

            //Property file creation
            System.out.println("IO: Property file creation");
            try (FileWriter writer = new FileWriter("./newDirectory/props.properties")) {
                Properties properties = new Properties();

                properties.setProperty("test1", "test1");
                properties.setProperty("test2", "2");

                properties.store(writer, "properties save");
            }

            //Property reading and editing
            System.out.println("IO: Property file reading and editing");
            try (FileReader reader = new FileReader("./newDirectory/props.properties")) {
                Properties properties = new Properties();
                properties.load(reader);

                System.out.println("Property: " + properties.get("test1"));

                properties.setProperty("test1", "notTest1");

                System.out.println("Property: " + properties.get("test1"));
            }
        } catch (Exception e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
        }
    }
}
