/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DoEverythingHere;

import java.util.ArrayList;
import Proses.CFG;
import Proses.EkstraksiData;
import Proses.Kelas.Aturan;
import Proses.Kelas.SatuFile;
import Proses.Kelas.SatuKalimat;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Mukhtar
 */
public class Praproses {
    private ArrayList<Aturan> CFGNormalisasi = new ArrayList<>();
    
    public void doPraproses() {
//        EkstraksiData ed = new EkstraksiData();
//        ed.Ekstrak("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\training\\ALT-Corpus-Tagged-Id-106.tre");
//        CFG cfg = new CFG();
//        cfg.ProsesPembuatanCFG(ed);
//        if (cfg.isStackHabis() == false) {
//            System.out.println("terdapat kesalahan dalam penyusunan sintaks data latih");
//        } else {
//            cfg.ProsesNormalisasiCFG();
//            CFGNormalisasi = cfg.getHasilNormalisasi();
//            cfg.printAll();
//        }
        
        File folder = new File("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\training");
        File[] listOfFiles = folder.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            EkstraksiData ed = new EkstraksiData();
            System.out.println("Nama File = "+listOfFiles[i].getName());
            ed.Ekstrak("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\training\\"+listOfFiles[i].getName());
            CFG cfg = new CFG();
            cfg.ProsesPembuatanCFG(ed);
            
            if (cfg.isStackHabis() == false) {
                System.out.println("terdapat kesalahan dalam penyusunan sintaks data latih");
            } else {
                cfg.ProsesNormalisasiCFG();
//                CFGNormalisasi = cfg.getHasilNormalisasi();
//                cfg.printAll();
                cfg.RemoverNormalisasi();
//                CFGNormalisasi = cfg.getHasilNormalisasi();
                sort(cfg.getHasilNormalisasi());
                System.out.println("normalisasi dengan remover : " + cfg.getHasilNormalisasi().size());
                cfg.printAll();
            }
        }
    }
    
    public ArrayList<Aturan> getNormalisasi() {
        return CFGNormalisasi;
    }
    
    private void sort(ArrayList<Aturan> newData) {
        ArrayList<String> tempNorm = new ArrayList<>();
        for (Aturan x : CFGNormalisasi) {
            String temp = x.getNamaTag() + " -> ";
            for (Aturan y : x.getSintaks()) {
                temp += y.getNamaTag() + " ";
            }
            tempNorm.add(temp);
        }
        
        for (Aturan x : newData) {
            String temp = x.getNamaTag() + " -> ";
            for (Aturan y : x.getSintaks()) {
                temp += y.getNamaTag() + " ";
            }
            if (!tempNorm.contains(temp)) {
                CFGNormalisasi.add(x);
                tempNorm.add(temp);
            }
        }
//        hapusTagS();
        
        Comparator<Aturan> ALPHABETICAL_ORDER = new Comparator<Aturan>() {
            public int compare(Aturan str1, Aturan str2) {
                int res = 0;
                if (res == 0) {
                    String aturan1 = str1.getNamaTag() + " -> ";
                    for (Aturan x : str1.getSintaks()) {
                        aturan1 += x.getNamaTag() + " ";
                    }
                    String aturan2 = str2.getNamaTag() + " -> ";
                    for (Aturan x : str2.getSintaks()) {
                        aturan2 += x.getNamaTag() + " ";
                    }
                    res = aturan1.compareTo(aturan2);
                }
                return res;
            }
        };
        Collections.sort(CFGNormalisasi, ALPHABETICAL_ORDER);
//        hapusTagS();
    }
    
    public void hapusTagS() {
//        for (int i = 0; i < CFGNormalisasi.size(); i++) {
//            System.out.println("yg baca : " + CFGNormalisasi.get(i).getNamaTag());
//            if (CFGNormalisasi.get(i).getNamaTag().equals("S")) {
//                System.out.println("\t yg terhapus : " + CFGNormalisasi.get(i).getNamaTag());
//                CFGNormalisasi.remove(i);
//                System.out.println("\t yg terhapus 2 : " + CFGNormalisasi.get(i).getNamaTag());
//            }
//        }
        
        ArrayList<Aturan> temp = new ArrayList<>();
        for (Aturan x : CFGNormalisasi) {
            if (!x.getNamaTag().equals("S")) {
                temp.add(x);
            }
        }
//        System.out.println("this is it");
//        for (Aturan str2 : temp) {
//            String aturan2 = str2.getNamaTag() + " -> ";
//            for (Aturan x : str2.getSintaks()) {
//                aturan2 += x.getNamaTag() + " ";
//            }
//            System.out.println(aturan2);
//        }
        
        CFGNormalisasi = temp;
            
    }
    
    public void printAll() {
        System.out.println("keluarkan data normalisasi : ");
        for (Aturan x : CFGNormalisasi) {
            String temp = x.getNamaTag() + " -> ";
            for (Aturan y : x.getSintaks()) {
                temp += y.getNamaTag() + " ";
            }
            System.out.println(temp);
        }
    }
}
