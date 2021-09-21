package Server.Commands;

public interface Command {
    String getName();
    String getDescr();
    boolean startExecute(String arg, Object o);
}

