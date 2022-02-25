package io.github.alen_alex.helpcord;

import io.github.alen_alex.helpcord.interfaces.Module;
import io.github.alen_alex.helpcord.logs.Debug;

import java.util.Scanner;
import java.util.function.Consumer;

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
            if(command.equalsIgnoreCase("stop")) {
                this.instance.end("Stop command triggered!", false);
            }else if(command.equalsIgnoreCase("debug:true")) {
                Debug.setEnabled(true);
            } else if(command.equalsIgnoreCase("debug:false")) {
                Debug.setEnabled(false);
            } else if(command.equalsIgnoreCase("enabled-module")){
                instance.getModuleRegistry().getAllEnabledModules().forEachRemaining(mod -> {
                    instance.getLogger().info("Module Name: "+mod.name());
                    instance.getLogger().info("Module Description: "+mod.description());
                    instance.getLogger().info("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
                });
            } else if(command.startsWith("enable:")){
                String[] enableArgs = command.split(":");
                if(enableArgs.length <= 1){
                    instance.getLogger().info("You should provide a valid module name to enable");
                }else {
                    instance.getModuleRegistry().getIfPresent(enableArgs[1]).ifPresentOrElse(new Consumer<Module>() {
                        @Override
                        public void accept(Module module) {
                            if(module.isEnabled()){
                                instance.getLogger().severe("Module is already enabled!");
                                return;
                            }
                            module.registerModule();
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            instance.getLogger().severe("Module is not found!");
                        }
                    });
                }
            } else if(command.startsWith("disable:")){
                String[] enableArgs = command.split(":");
                if(enableArgs.length <= 1){
                    instance.getLogger().info("You should provide a valid module name to enable");
                }else {
                    instance.getModuleRegistry().getIfPresent(enableArgs[1]).ifPresentOrElse(new Consumer<Module>() {
                        @Override
                        public void accept(Module module) {
                            if(!module.isEnabled()){
                                instance.getLogger().severe("Module is already disabled!");
                                return;
                            }
                            module.unregisterModule();
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            instance.getLogger().severe("Module is not found!");
                        }
                    });
                }
            }else if(command.equalsIgnoreCase("modules")){
                instance.getModuleRegistry().getAllModules().forEachRemaining(mod -> {
                    instance.getLogger().info("Module Name: "+mod.name());
                    instance.getLogger().info("Module Description: "+mod.description());
                    instance.getLogger().info("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_");
                });
            }


        }
    }
}
