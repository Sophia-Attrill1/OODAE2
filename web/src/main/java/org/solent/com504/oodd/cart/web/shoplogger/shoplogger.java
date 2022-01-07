/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.web.shoplogger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *
 * @author acer
 */
public class shoplogger {
    public static void Logger(String message){
        try{
            String timestamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
            FileWriter fw = new FileWriter("../logs/Transaction Logger.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(timestamp + " " + message);
            bw.newLine();
            bw.close();
        }
        catch(IOException e){
            System.out.println("Error writing to file");
        }
    }
}

