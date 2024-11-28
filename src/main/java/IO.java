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
            int input = System.in.read();
            System.out.println("Im system out stream. Im typed " + input);

            Scanner scanner = new Scanner(System.in);

            String scanned = scanner.next();
            System.out.println("I was scanned " + scanned);

            File directory = new File("./newDirectory");
            directory.mkdirs();

            File newFile = new File("./newDirectory/file.txt");
            newFile.createNewFile();
            try (FileOutputStream outputStream = new FileOutputStream(newFile)) {
                outputStream.write("im new line".getBytes());
            }

            try (FileInputStream inputStream = new FileInputStream(newFile)) {
                String line = new Scanner(inputStream).nextLine();
                System.out.println("Im file - "+ line);
            }

            try (FileWriter writer = new FileWriter("./newDirectory/props.properties")) {
                Properties properties = new Properties();

                properties.setProperty("test1", "test1");
                properties.setProperty("test2", "2");

                properties.store(writer, "properties save");
            }

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
