package com.goku.redis;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;
import org.xerial.snappy.Snappy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: user
 * Date: 15/11/7
 * Version: 1.0
 */
@Slf4j
public class SerializationUtils {
    //private static final Logger log = LoggerFactory.getLogger(SerializationUtils.class);
    private static final FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
    public static byte[] EMPTY_BYTES = new byte[0];
    public static byte[] fstSerialize(final Object graph) {
        if (graph == null)
            return EMPTY_BYTES;

        try {
            @Cleanup
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            FSTObjectOutput oos = conf.getObjectOutput(os);
            oos.writeObject(graph);
            oos.flush();

            return os.toByteArray();
        } catch (IOException e) {
            log.warn("Fail to serializer graph. graph=" + graph, e);
            throw new IllegalArgumentException(e);
        }
    }
    static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
    public static <T> T fstDeserialize(final byte[] bytes) {
        if ( isEmpty(bytes) )
            return null;

        try {
            @Cleanup
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            FSTObjectInput ois = conf.getObjectInput(is);
            return (T) ois.readObject();
        } catch (IOException e) {
            log.warn("Fail to deserialize bytes.", e);
            throw new IllegalArgumentException(e);
        }catch (ClassNotFoundException e) {
            log.warn("Fail to deserialize bytes.", e);
            throw new IllegalArgumentException(e);
        }
    }
    public static byte[] serialize(Object graph) {
        if (graph == null)
            return EMPTY_BYTES;
        try {
            byte[] fstSeri = fstSerialize(graph);
            return Snappy.compress(fstSeri);
        }catch (IOException e) {
            e.printStackTrace();
            log.error("Fail to serialize graph.", e);
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> T deserialize(byte[] bytes) {
        if ( isEmpty(bytes) )
            return null;
        try {
            return fstDeserialize(Snappy.uncompress(bytes));
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
