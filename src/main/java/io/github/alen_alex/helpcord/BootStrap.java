package io.github.alen_alex.helpcord;

import io.github.alen_alex.helpcord.logs.Debug;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class BootStrap {

    public static void main(String[] args){
        final List<String> providedArgs = Arrays.stream(args).toList();
        if(providedArgs.contains("-hcdebug:true")) {
            Debug.setEnabled(true);
            Debug.debug("Told to debug on startup!");
        }
        try {
            Debug.debug("Starting HelpCordInstance. Debug format will be changed!");
            HelpCord helpCord = new HelpCord();
            helpCord.start();
        } catch (URISyntaxException e) {
            System.out.println("Failed to initialize bootstrap. The error was caused due with fetching the data folder of the plugin.");
            e.printStackTrace();
            System.exit(-1);
        }


    }

}
