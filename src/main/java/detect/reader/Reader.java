package main.java.detect.reader;

import java.io.DataInputStream;
import java.io.IOException;

public class Reader {

    private final int BUFFER_SIZE = 1 << 16;
    private final DataInputStream dataInputStream;
    private final byte[] buffer;
    private int bufferPointer;
    private int bytesRead;

    public Reader() {
        dataInputStream = new DataInputStream(System.in);
        buffer = new byte[BUFFER_SIZE];
        bufferPointer = bytesRead = 0;
    }

    public int nextInt() throws IOException {
        int result = 0;
        byte count = read();
        while (count <= ' ') {
            count = read();
        }
        boolean negative = (count == '-');
        if (negative)
            count = read();
        do {
            result = result * 10 + count - '0';
        } while ((count = read()) >= '0' && count <= '9');

        if (negative)
            return -result;
        return result;
    }

    private void fillBuffer() throws IOException {
        bytesRead = dataInputStream.read(buffer, bufferPointer = 0,
                BUFFER_SIZE);
        if (bytesRead == -1)
            buffer[0] = -1;
    }

    private byte read() throws IOException {
        if (bufferPointer == bytesRead)
            fillBuffer();
        return buffer[bufferPointer++];
    }
}