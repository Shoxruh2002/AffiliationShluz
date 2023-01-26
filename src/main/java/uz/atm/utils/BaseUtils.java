package uz.atm.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 10/24/22 10:12 AM
 **/
@Slf4j
public class BaseUtils {

    private static final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

    public static String generateRandomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static Long generateLongId(String path) {
        try ( RandomAccessFile file = new RandomAccessFile(path, "rw");
              FileChannel channel = file.getChannel();
              FileLock lock = channel.lock() ) {
            file.seek(0);
            long l = file.readLong();
            file.seek(0);
            file.write(longToBytes(l + 1));
            return l;
        } catch ( OverlappingFileLockException | IOException e ) {
            log.error("Exception occurred while generating requestId ; Cause : {}", e.getMessage());
            return new SecureRandom().nextLong(999999999999L, 999999999999999999L);
        }
    }

    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();
        return buffer.getLong();
    }
}
