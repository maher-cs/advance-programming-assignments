/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author mapvsnp
 */
//single ton pattern
public class FileStoreService {
    
    private final String SEPERATE = "\t";
    
    private static FileStoreService instanse;
    
    private FileStoreService() {
        
    }
    
    public static FileStoreService getFileStoreService() {
        if(instanse == null) {
            instanse = new FileStoreService();
        }
        return instanse;
    }
    
    public void addNewRecord(String fileName, String data) {
        File f = new File(fileName);
        try {
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.newLine();
            bw.close();
        } catch(IOException e) {
            System.err.println(e);
        }
    }
    
    public ArrayList<String[]> readAllData(String fileName) {
        ArrayList<String[]> data = new ArrayList<String[]>();
        File f = new File(fileName);
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while(true) {
                String line = br.readLine();
                if(line == null)
                    break;
                String[] user = line.split(SEPERATE);
                data.add(user);
            }
        } catch(IOException e) {
            System.err.println(e);
        }
        return data;
    }
    
    public String readLineContains(String fileName, String key) {
    
        return null;
    }
    
}
