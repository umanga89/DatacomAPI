package utilities;

import java.util.HashMap;
import java.util.Map;

public class BookAPIBodyUtil {

    public static Map<String, Object> createBookBodyInteger(int sourceX, int sourceY, int destinationX, int destinationY) {
        Map<String, Object> sourceBody = new HashMap<>();
        sourceBody.put("x",sourceX);
        sourceBody.put("y",sourceY);

        Map<String, Object> destinationBody = new HashMap<>();
        destinationBody.put("x",destinationX);
        destinationBody.put("y",destinationY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source",sourceBody);
        requestBody.put("destination",destinationBody);

        return requestBody;
    }

    public static Map<String, Object> createBookBodyIntegerWithoutSourceX(int sourceY, int destinationX, int destinationY) {
        Map<String, Object> sourceBody = new HashMap<>();
        sourceBody.put("y",sourceY);

        Map<String, Object> destinationBody = new HashMap<>();
        destinationBody.put("x",destinationX);
        destinationBody.put("y",destinationY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source",sourceBody);
        requestBody.put("destination",destinationBody);

        return requestBody;
    }

    public static Map<String, Object> createBookBodyIntegerWithoutSourceY(int sourceX, int destinationX, int destinationY) {
        Map<String, Object> sourceBody = new HashMap<>();
        sourceBody.put("x",sourceX);

        Map<String, Object> destinationBody = new HashMap<>();
        destinationBody.put("x",destinationX);
        destinationBody.put("y",destinationY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source",sourceBody);
        requestBody.put("destination",destinationBody);

        return requestBody;
    }

    public static Map<String, Object> createBookBodyIntegerWithoutDestinationX(int sourceX, int sourceY, int destinationY) {
        Map<String, Object> sourceBody = new HashMap<>();
        sourceBody.put("x",sourceX);
        sourceBody.put("y",sourceY);

        Map<String, Object> destinationBody = new HashMap<>();
        destinationBody.put("y",destinationY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source",sourceBody);
        requestBody.put("destination",destinationBody);

        return requestBody;
    }

    public static Map<String, Object> createBookBodyIntegerWithoutDestinationY(int sourceX, int sourceY, int destinationX) {
        Map<String, Object> sourceBody = new HashMap<>();
        sourceBody.put("x",sourceX);
        sourceBody.put("y",sourceY);

        Map<String, Object> destinationBody = new HashMap<>();
        destinationBody.put("x",destinationX);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source",sourceBody);
        requestBody.put("destination",destinationBody);

        return requestBody;
    }

    public static Map<String, Object> createBookBodyIntegerWithoutSourceTag(int destinationX, int destinationY) {

        Map<String, Object> destinationBody = new HashMap<>();
        destinationBody.put("x",destinationX);
        destinationBody.put("y",destinationY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("destination",destinationBody);

        return requestBody;
    }

    public static Map<String, Object> createBookBodyIntegerWithoutDestinationTag(int sourceX, int sourceY) {
        Map<String, Object> sourceBody = new HashMap<>();
        sourceBody.put("x",sourceX);
        sourceBody.put("y",sourceY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source",sourceBody);

        return requestBody;
    }

    public static Map<String, Object> createBookBodyLong(long sourceX, long sourceY, long destinationX, long destinationY) {
        Map<String, Object> sourceBody = new HashMap<>();
        sourceBody.put("x",sourceX);
        sourceBody.put("y",sourceY);

        Map<String, Object> destinationBody = new HashMap<>();
        destinationBody.put("x",destinationX);
        destinationBody.put("y",destinationY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source",sourceBody);
        requestBody.put("destination",destinationBody);

        return requestBody;
    }

}
