package main.org.example;

import java.util.Scanner;

public class ConsoleManager extends Thread {
    private final Scanner scanner;

    public ConsoleManager() {
        this.scanner = new Scanner(System.in);
    }

    public void run()
    {
        while(true)
        {
            if (scanner.hasNext())
            {
                String input = scanner.nextLine();
                if (input.equals("exit"))
                {
                    System.out.println("Server stopping");
                    System.exit(1);
                }
            }
        }
    }
}
