package JavaMazeGame.Controller;

import java.util.Arrays;
import java.util.Scanner;
import static JavaMazeGame.Constants.MyConstants.*;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);
    private static final String[] ALLOWED_ONE_ARGUMENT_COMMANDS = {HELP_COMMAND, LOCATION_COMMAND, ITEMS_COMMAND, QUIT_COMMAND};
    private static final String[] ALLOWED_TWO_ARGUMENT_COMMANDS = {DROP_ITEM_COMMAND};
    private static final String[] ALLOWED_THREE_ARGUMENT_COMMANDS = {GOTO_COMMAND, PICK_UP_ITEM_COMMAND};

    public static void inputName(){
        String value = scanner.nextLine();

        InputExecutor.executeCommand(NAME_COMMAND, value);
    }

    public static void inputCommand(){
        String[] inputTokens = scanner.nextLine().split(" ");

        StringBuilder commandArguments = new StringBuilder();

        int argumentsStartIndex = 0;
        String commandToCheck = inputTokens[0].toLowerCase();
        int typeOfCommand = typeOfCommand(commandToCheck);

        if (typeOfCommand == 1){
            InputExecutor.executeCommand(commandToCheck);
        }
        else if (typeOfCommand == 2){
            argumentsStartIndex = 1;
        }
        else if (typeOfCommand == 3){
            argumentsStartIndex = 2;
        }
        else {
            InputExecutor.executeCommand(INVALID_COMMAND);
        }

        for (int i = argumentsStartIndex; i < inputTokens.length; i++){
            commandArguments.append(inputTokens[i]);
        }
        InputExecutor.executeCommand(commandToCheck, commandArguments.toString());
    }

    private static int typeOfCommand(String command){
        if (Arrays.asList(ALLOWED_ONE_ARGUMENT_COMMANDS).contains(command.toLowerCase())){
            return 1;
        }
        else if (Arrays.asList(ALLOWED_TWO_ARGUMENT_COMMANDS).contains(command.toLowerCase())){
            return 2;
        }
        else if (Arrays.asList(ALLOWED_THREE_ARGUMENT_COMMANDS).contains(command.toLowerCase())){
            return 3;
        }

        return 0;
    }
}
