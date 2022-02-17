package io.github.alen_alex.helpcord;

import java.net.URISyntaxException;

public class BootStrap {

    public static void main(String[] args){
        try {
            HelpCord helpCord = new HelpCord();
            helpCord.start();
        } catch (URISyntaxException e) {
            System.out.println("Failed to initialize bootstrap. The error was caused due with fetching the datafolder of the plugin.");
            e.printStackTrace();
            System.exit(-1);
        }


    }

}
