package Server;

public class ResponseOut {
    private static StringBuilder responseText = new StringBuilder();


    public static void println(Object toOut) {
        responseText.append(toOut + "\n");
    }

    public static String get() {
        return responseText.toString();
    }
    public static String getToClient() {
        String toReturn = responseText.toString();
        responseText.delete(0, responseText.length());
        return toReturn;
    }
}
