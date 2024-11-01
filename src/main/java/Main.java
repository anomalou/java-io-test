import sun.misc.Resource;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Author: Aleksandr Borodin
 * Creation date: 11/1/24
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        int input = System.in.read();
        System.out.println("Im system out stream. Im input " + input);

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

        URI path = Thread.currentThread().getContextClassLoader().getResource("resource.txt").toURI();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            System.out.println(reader.readLine());
        }

        try (SeekableByteChannel channel = Files.newByteChannel(Paths.get(path), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            ByteBuffer writeBuffer = ByteBuffer.wrap("buffered line".getBytes());

            channel.write(writeBuffer);
        }

        try (RandomAccessFile file = new RandomAccessFile(path.getPath(), "r")) {
            FileChannel channel = file.getChannel();

            Charset charset = Charset.defaultCharset();
            ByteBuffer buffer = ByteBuffer.allocate(10);

            int i = 0;
            while ((i = channel.read(buffer)) > 0) {
                buffer.rewind();
                CharBuffer decoded = charset.decode(buffer);
                System.out.println(decoded.limit(i));
                buffer.clear();
            }
        }
    }
}
