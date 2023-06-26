package com.example.client.utilities;

import com.example.client.factores.CityFactory;
import main.org.example.utility.CommandFactory;
import main.org.example.utility.DataInOutStatus;

import java.util.ArrayList;


    public class CommandChecker {

        private static AvailableCommands availableCommands;
        private ArrayList<String> arguments;


        public DataInOutStatus checkCorrectnessOfCommand(String commandName , ArrayList<String> argumentsToCommand) {
            DataInOutStatus correctnessStatus = DataInOutStatus.SUCCESSFULLY;
            MetaInfoCommand metaInfoCommand = new MetaInfoCommand();
            availableCommands = new AvailableCommands();
            /*обращаемся ко всем командам и смотрим есть ли такая команда вообще */
            //Map<String, AbstractCommand> mapCommand = metaInfoCommand.getMapOfCommand();
            /*ищем по имени */
            //   if (mapCommand.containsKey(commandName)) {
            /* создаем объект команды */
            if (availableCommands.noArgumentCommands.contains(commandName) || availableCommands.scriptArgumentCommand.contains(commandName) || availableCommands.numArgumentCommands.contains(commandName) || availableCommands.objectArgumentCommands.contains(commandName) || availableCommands.objAndNumArgumentCommand.contains(commandName) || availableCommands.stringArgumentCommands.contains(commandName)) {
                if (availableCommands.noArgumentCommands.contains(commandName)) {
                    if (argumentsToCommand.size() != 0) {
                        return DataInOutStatus.WRONGARGS;
                    }
                    if (!commandName.equals("exit")) {
                        CommandFactory commandFactory = new CommandFactory(commandName, null);
                        RequestHandler.getInstance().send(commandFactory);
                    }
                    else {
                        CommandFactory commandFactory = new CommandFactory(commandName, null);
                        RequestHandler.getInstance().send(commandFactory);
                    }
                    return DataInOutStatus.SUCCESSFULLY;
                }
                /*проверяем команду , нужны ли ей дополнительные аргументы */

                    if (availableCommands.objAndNumArgumentCommand.contains(commandName) || availableCommands.scriptArgumentCommand.contains(commandName) || availableCommands.numArgumentCommands.contains(commandName)) {
                        if (argumentsToCommand.size() != 1) {
                            AlertUtility.errorAlert("You have wrong argument");
                            return DataInOutStatus.FAILED;
                        }
                        if (availableCommands.numArgumentCommands.contains(commandName)){
                            CommandFactory commandFactory = new CommandFactory(commandName, argumentsToCommand);
                            System.out.println(RequestHandler.getInstance().send(commandFactory));
                            return DataInOutStatus.SUCCESSFULLY;
                        }
                        if (availableCommands.scriptArgumentCommand.contains(commandName)) {
                            String fileName = argumentsToCommand.get(0);
                            ScriptReader.setFile(fileName);
                            try {
                                ScriptReader.execute();
                                return DataInOutStatus.SUCCESSFULLY;
                            } catch (Exception e) {
                                System.out.println("exception");
                            }


                        }
                    }
                    if (availableCommands.stringArgumentCommands.contains(commandName)){
                        CommandFactory commandFactory = new CommandFactory(commandName, argumentsToCommand);
                        RequestHandler.getInstance().send(commandFactory);
                        return DataInOutStatus.SUCCESSFULLY;
                    }
                    correctnessStatus = checkCorrectnessOfComplicatedCommand(commandName);
                        if (correctnessStatus == DataInOutStatus.SUCCESSFULLY) {
                            CommandFactory commandFactory;
                            if (availableCommands.objectArgumentCommands.contains(commandName)) {
                                commandFactory = new CommandFactory(commandName, null);
                            } else {
                                commandFactory = new CommandFactory(commandName, argumentsToCommand);
                            }
                            RequestHandler.getInstance().send(commandFactory, new CityFactory().createCity(arguments));
                        }


                }
            //  }
            else {
                return DataInOutStatus.NOCOMMAND;
            }
            return DataInOutStatus.SUCCESSFULLY;
        }


        /*проверяем команду у которой много аргументов на правильность введения */
        private DataInOutStatus checkCorrectnessOfComplicatedCommand(String command) {
            DataInOutStatus correctnessStatus = DataInOutStatus.SUCCESSFULLY;
            CommandDataChecker commandChecker = new CommandDataChecker();
            correctnessStatus = commandChecker.checkInputCommand(command);
            if (correctnessStatus == DataInOutStatus.SUCCESSFULLY) {
                arguments = new ArrayList<>();
                arguments.addAll(commandChecker.getExtraArgs());
            }
            if (arguments.size() == 0) {
                return DataInOutStatus.WRONGARGS;
            }

        return correctnessStatus;
    }
}

