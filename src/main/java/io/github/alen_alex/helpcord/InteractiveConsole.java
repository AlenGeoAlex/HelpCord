package io.github.alen_alex.helpcord;

import io.github.alen_alex.helpcord.logs.Debug;

import java.util.Scanner;

public class InteractiveConsole extends Thread {

    private final HelpCord instance;
    private final Scanner scanner;

    public InteractiveConsole(HelpCord instance) {
        this.instance = instance;
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true){
            String command = scanner.nextLine();
            if(command.equalsIgnoreCase("stop"))
                this.instance.end("Stop command triggered!",false);
            if(command.equalsIgnoreCase("debug:true"))
                Debug.setEnabled(true);
            if(command.equalsIgnoreCase("debug:false"))
                Debug.setEnabled(false);
        }
    }
}
