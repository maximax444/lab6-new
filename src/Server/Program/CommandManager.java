package Server.Program;
import java.util.*;


import Server.Commands.*;
import Server.ResponseOut;
import common.Exceptions.WrongCommandException;

public class CommandManager {
    private HashMap<String, Command> commands = new HashMap<String, Command>();
    private ArrayDeque<Command> history = new ArrayDeque<Command>(8);
    private Stack<String> scriptStack;

    public CommandManager(Command... commands) {
        for (Command command : commands){
            this.commands.put(command.getName(), command);
        }
        scriptStack = new Stack<>();
    }



    /**
     * Add new command to CM
     * **/
    public void addCommand(AbstractCommand command){
        commands.put(command.getName(),command);
    }

    /**
     * Get all commands
     * **/
    public HashMap<String, Command> getCommands(){
        return this.commands;
    }

    public boolean contains(String commandName){
        try{
            if (!commands.containsKey(commandName)) throw new WrongCommandException("Команды "+ commandName + " нет в программе");
            return true;
        } catch(WrongCommandException e){
            Console.println(e.getMessage());
            return false;
        }
    }
    /**
     * Launchs the command.
     * @return Exit status of executable command
     */
    public boolean startExecuteCommand(String command, String key, Object o) {
        if(contains(command)){
            history.add(commands.get(command));
            switch (command) {
                case "help": return help();
                case "history": return history();
                default: return commands.get(command).startExecute(key, o);
            }
        } else {return false;}
    }
    /**
     * Executes HistoryCommand
     * @return exit status of command
     * **/
    public boolean history(){
        if(contains("history")){
            Iterator iterator = history.iterator();
            int i = 0;
            ResponseOut.println("Последние 8 комманд: ");
            while(iterator.hasNext() && (i < 8)){
                i++;
                Command command = (Command)iterator.next();
                ResponseOut.println(i + ") " + command.getName() + "\n");
            }
            return commands.get("history").startExecute("", null);
        }else{
            return false;
        }
    }


    /**
     * Executes HelpCommand
     * @return exit status of command
     * **/
    public boolean help(){
            if(contains("help")) {
                String result = "";
                HashMap<String, Command> commands = this.commands;

                for(Map.Entry<String, Command> entry : commands.entrySet()) {
                    String key = entry.getKey();
                    Command command = entry.getValue();
                    result += command.getName() + " " + command.getDescr() + "\n";
                }
                ResponseOut.println(result);
                return true;
            } else {
                return false;
            }

    }

}
