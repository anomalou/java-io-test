import java.io.BufferedReader;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.concurrent.Future;

/**
 * Author: Aleksandr Borodin
 * Creation date: 11/28/24
 */
public class NIO implements Runnable{
    @Override
    public void run() {
        try {
            //Reading file through buffer
            System.out.println("NIO: Reading file through buffer");
            URI path = Thread.currentThread().getContextClassLoader().getResource("resource.txt").toURI();

            try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
                System.out.println(reader.readLine());
            }

            //Writing file through buffer
            System.out.println("NIO: Writing file through buffer");
            try (SeekableByteChannel channel = Files.newByteChannel(Paths.get(path), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
                ByteBuffer writeBuffer = ByteBuffer.wrap("buffered line".getBytes());

                channel.write(writeBuffer);
            }

            //Reading all file through byte buffer
            System.out.println("NIO: Reading file through byte buffer");
            try (RandomAccessFile file = new RandomAccessFile(path.getPath(), "r"); FileChannel channel = file.getChannel()) {
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

            //Async file read
            System.out.println("NIO: Async file read");
            try (AsynchronousFileChannel file = AsynchronousFileChannel.open(Paths.get(URI.create("./tempFile.txt")))) {
                ByteBuffer buffer = ByteBuffer.allocate(255);
                Future<Integer> future = file.read(buffer, 0);

                while (!future.isDone()) {
                    System.out.println("File is not loaded");
                }

                String fileStr = new String(buffer.array(), StandardCharsets.UTF_8);
                System.out.println(fileStr);
            }


        } catch (Exception e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
        }
    }
}
