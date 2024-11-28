import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.spi.AbstractSelectableChannel;

/**
 * Author: Aleksandr Borodin
 * Creation date: 11/28/24
 */

@RequiredArgsConstructor
public class DelayChannelRead implements Runnable{

    private final String text;
    private final FileChannel channel;
    private final int delay;

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println("Delay thread is dead");
        }

        if (channel.isOpen()) {
            ByteBuffer buffer = ByteBuffer.wrap(text.getBytes());
            try {
                channel.write(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
