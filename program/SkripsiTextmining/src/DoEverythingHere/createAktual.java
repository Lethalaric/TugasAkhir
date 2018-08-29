/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DoEverythingHere;

import Proses.ImportExportFile;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Mukhtar
 */
public class createAktual {
    public static void main(String[] args) {
//        File file = new File("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\aktual\\contih kalimat.txt");
        ArrayList<String> Datakalimat = new ArrayList<>();
        ArrayList<String> hasil = new ArrayList<>();
        ImportExportFile ief = new ImportExportFile();
        
        ief.ImportFile("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\aktual\\data uji.txt");
        Datakalimat = ief.getIsi();
        for(String x : Datakalimat) {
//            x = x + ("_" + ThreadLocalRandom.current().nextInt(0,2));
            x = x + "_1";
            hasil.add(x);
        }
        
        for (String x : hasil) {
            System.out.println(x);
        }
    }
}
