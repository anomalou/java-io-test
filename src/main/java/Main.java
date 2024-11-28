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
import java.util.*;

/**
 * Author: Aleksandr Borodin
 * Creation date: 11/1/24
 */
public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        IO io = new IO();
        NIO nio = new NIO();

        io.run();
        nio.run();
    }
}
