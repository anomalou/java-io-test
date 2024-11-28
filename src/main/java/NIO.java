import java.io.BufferedReader;
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

/**
 * Author: Aleksandr Borodin
 * Creation date: 11/28/24
 */
public class NIO implements Runnable{
    @Override
    public void run() {
        try {
            URI path = Thread.currentThread().getContextClassLoader().getResource("resource.txt").toURI();

            try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
                System.out.println(reader.readLine());
            }

            try (SeekableByteChannel channel = Files.newByteChannel(Paths.get(path), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
                ByteBuffer writeBuffer = ByteBuffer.wrap("buffered line".getBytes());

                channel.write(writeBuffer);
            }

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

            try (RandomAccessFile file = new RandomAccessFile("./tempFile.txt", "rw")) {
                FileChannel channel1 = file.getChannel();

            }


        } catch (Exception e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
        }
    }
}
