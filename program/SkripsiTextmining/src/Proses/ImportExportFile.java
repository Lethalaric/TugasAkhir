/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mukhtar
 */
public class ImportExportFile {
    public ImportExportFile() {
        data = new ArrayList<>();
    }
    private ArrayList<String> data;
    public void ExportFile(String file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            DataOutputStream dos = new DataOutputStream(fos);
            for (String s : data) {
                dos.writeBytes(s + System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportExportFile.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportExportFile.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    public void ImportFile(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            DataInputStream dis = new DataInputStream(fis);
            String temp = dis.readLine();
            while (temp != null) {
                data.add(temp);
                temp = dis.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImportExportFile.class.getName()).
                    log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImportExportFile.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }
    public void setIsi(ArrayList<String> tempIsi) {
        this.data = tempIsi;
    }
    public ArrayList<String> getIsi() {
        return this.data;
    }
}
