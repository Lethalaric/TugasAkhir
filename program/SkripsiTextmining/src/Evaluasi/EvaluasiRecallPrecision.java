/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Evaluasi;

import DoEverythingHere.ExportToExcel;
import DoEverythingHere.SurveyXValid;
import Proses.Kelas.Aturan;
import Proses.Kelas.SatuFile;
import Proses.Kelas.SatuKalimat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mukhtar
 */
public class EvaluasiRecallPrecision {
    private ArrayList<Aturan> dataNormalisasi;
    private ArrayList<SatuFile> datasetValid;
    private ArrayList<SatuFile> datasetAktual;
    private ArrayList<SatuFile> datasetAktual2;
    
    private ArrayList<SatuFile> hasilValidXAktual;
    private ArrayList<SatuFile> hasilPrediksi;
    private ArrayList<SatuFile> hasilSaran;
    private ArrayList<Integer> dataKebenaran = new ArrayList<>();

//      |----------------------------------------------------------------|
//	|			RIIL                                     |
//	|----------------------------------------------------------------|
//	|           |           |    0            |	  1              |
//	|           |-----------|-----------------|----------------------|
//	|           |           |  Positif-Keliru |	Negatif-Benar	 |
//	|warning    |-----------|-----------------|----------------------|
//	|	0   |Positif-ON |   tp            |	fp               |
//	|	1   |Negatif=OFF|   fn            |	tn               |
//	|----------------------------------------------------------------|
    
    // T = True = benar = 1 = off/tidak menyala 
    // F = False = salah = 0 = on/menyala
    private int[][] dataRecallPrecisition = {{0,0},{0,0}};
    
    public void Evaluasi() {
        
//        datasetAktual2 = new ArrayList<>();
//        for (SatuFile x : datasetAktual) {
//            SatuFile tempFile = new SatuFile();
//            tempFile.setNamaFile(x.getNamaFile());
//            for (SatuKalimat xx : x.getListKalimat()) {
//                SatuKalimat tempKalimat = new SatuKalimat();
//                tempKalimat.setKalimat(xx.getKalimat());
//                for (Aturan xxx : xx.getListCFG()) {
//                    Aturan tempAturan = new Aturan();
//                    tempAturan.setNamaTag(xxx.getNamaTag());
//                    for (Aturan xxxx : xxx.getSintaks()) {
//                        Aturan tempAturan2 = new Aturan();
//                        tempAturan.addSintaks(xxxx);
//                    }
//                    tempKalimat.addCFG(xxx, 100);
//                }
//                tempFile.addKalimat(xx);
//            }
//            datasetAktual2.add(x);
//        }
        System.out.println("sebelum--");
        cekAkurasi(datasetAktual);
        
        ArrayList<SatuFile> datasetAktualTemp = new ArrayList<>();
        for (int i = 0; i < datasetAktual.size(); i++) {
            SatuFile temp1 = new SatuFile();
            for (int j = 0; j < datasetAktual.get(i).getListKalimat().size(); j++) {
                SatuKalimat temp2 = new SatuKalimat();
                for (int k = 0; k < datasetAktual.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
                    temp2.setKalimat(datasetAktual.get(i).getListKalimat().get(j).getKalimat());
                    temp2.addCFG(datasetAktual.get(i).getListKalimat().get(j).getListCFG().get(k), datasetAktual.get(i).getListKalimat().get(j).getListNilai().get(k));
                }
                temp1.addKalimat(temp2);
            }
            datasetAktualTemp.add(temp1);
        }
        EvaluasiValidXAktual(datasetAktualTemp);
//        for (SatuFile x1 : hasilValidXAktual) {
//            for (SatuKalimat x2 : x1.getListKalimat()) {
//                for (int i = 0; i < x2.getListCFG().size(); i++) {
//                    String tempAturan = x2.getListCFG().get(i).getNamaTag() + " -> ";
//                    for (Aturan x3 : x2.getListCFG().get(i).getSintaks()) {
//                        tempAturan += x3.getNamaTag() + " ";
//                    }
//                    System.out.println("hasil aktual x valid : " + tempAturan + " : " + x2.getListNilai().get(i));
//                }
//            }
//        }
        
//        Prediksi();
//        for (SatuFile x1 : hasilPrediksi) {
//            for (SatuKalimat x2 : x1.getListKalimat()) {
//                for (int i = 0; i < x2.getListCFG().size(); i++) {
//                    String tempAturan = x2.getListCFG().get(i).getNamaTag() + " -> ";
//                    for (Aturan x3 : x2.getListCFG().get(i).getSintaks()) {
//                        tempAturan += x3.getNamaTag() + " ";
//                    }
//                    System.out.println("hasil prediksi : " + tempAturan + " : " + x2.getListNilai().get(i));
//                }
//            }
//        }
        ArrayList<Integer> jumlahBenar = new ArrayList<>();
        ArrayList<Integer> jumlahCFG = new ArrayList<>();
        ArrayList<ArrayList<String>> daftarEdited = new ArrayList<>();
        ArrayList<ArrayList<String>> daftarValid = new ArrayList<>();
        ArrayList<ArrayList<String>> daftarSaran = new ArrayList<>();
        ArrayList<String> daftarKalimat = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey = new ArrayList<>();
        SurveyXValid sxv = new SurveyXValid();
        CFGSurvey = sxv.akurasi();
        ExportToExcel ete = new ExportToExcel();
        hasilSaran = new ArrayList<>();
        for (int i = 0 ; i < datasetValid.size(); i++) {
            SatuFile tempFile = new SatuFile();
            tempFile.setNamaFile(datasetValid.get(i).getNamaFile());
            for (int j = 0; j < datasetValid.get(i).getListKalimat().size(); j++) {
                String namaKalimat = datasetValid.get(i).getListKalimat().get(j).getKalimat();
                ArrayList<String> StringEdited = new ArrayList<>();
                ArrayList<String> StringValid = new ArrayList<>();
                ArrayList<String> StringSaran = new ArrayList<>();
                SatuKalimat tempKalimat = new SatuKalimat();
                tempKalimat.setKalimat(namaKalimat);
                for (int k = 0; k < datasetValid.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
                    String AturanEdited = datasetAktual.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    for (Aturan x3 : datasetAktual.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        AturanEdited += x3.getNamaTag() + " ";
                    }
                    
                    String AturanValid = datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    for (Aturan x3 : datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        AturanValid += x3.getNamaTag() + " ";
                    }
                    
//                    String AturanSaran = hasilSaran.get(k).getNamaTag() + " -> ";
//                    for (Aturan x3 : hasilSaran.get(k).getSintaks()) {
//                        AturanSaran += x3.getNamaTag() + " ";
//                    }
                    
                    StringValid.add(AturanValid);
                    StringEdited.add(AturanEdited);
//                    StringSaran.add(AturanSaran);
                }
                
                ArrayList<Aturan> tempSaran = Suggestion(datasetAktual.get(i).getListKalimat().get(j).getListCFG());
                tempKalimat.setListCFG(tempSaran);
                tempFile.addKalimat(tempKalimat);
                int count = 0;
                int totalCFG = 0;
                for (int k = 0; k < tempSaran.size(); k++) {
                    String ProductionRuleAktual = tempSaran.get(k).getNamaTag() + " -> ";
                    for (Aturan x : tempSaran.get(k).getSintaks()) {
                        ProductionRuleAktual += x.getNamaTag() + " ";
                    }
                    
                    StringSaran.add(ProductionRuleAktual);
                    
                    String validRule = datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    for (Aturan x : datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        validRule += x.getNamaTag() + " ";
                    }
                    
                    if (validRule.equalsIgnoreCase(ProductionRuleAktual)) {
                        count++;
                    }
                    totalCFG++;
//                    System.out.println("++ " + ProductionRuleAktual);
                }
//                for (Aturan aturan : tempSaran) {
//                    
//                    String AturanSaran = aturan.getNamaTag() + " -> ";
//                    for (Aturan x3 : aturan.getSintaks()) {
//                        AturanSaran += x3.getNamaTag() + " ";
//                    }
//                    
//                }
                jumlahBenar.add(count);
                jumlahCFG.add(totalCFG);
//                ete.setData(namaKalimat, StringValid, StringEdited, StringSaran);
                daftarKalimat.add(namaKalimat);
                daftarEdited.add(StringEdited);
                daftarValid.add(StringValid);
                daftarSaran.add(StringSaran);
            }
            hasilSaran.add(tempFile);
        }
        ete.setData(daftarKalimat, daftarValid, daftarEdited, daftarSaran, CFGSurvey);
        ete.doExport();
        System.out.println("survey survey");
//        int XjumlahBenar = 0;
//        int XjumlahCFG = 0;
//        for (int i = 0; i < CFGSurvey.size(); i++) {
//            System.out.println("nama file : " + CFGSurvey.get(i).getNamaFile());
//            for (int j = 0; j < CFGSurvey.get(i).getListKalimat().size(); j++) {
//                for (int k = 0; k < CFGSurvey.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
//                    String CFGDataValid = datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
//                    for (Aturan x : datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
//                        CFGDataValid += x.getNamaTag() + " ";
//                    }
//                    
//                    String CFGSurvey2 = CFGSurvey.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
//                    for (Aturan x : CFGSurvey.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
//                        CFGSurvey2 += x.getNamaTag() + " ";
//                    }
//                    
//                    if (CFGDataValid.equals(CFGSurvey2)) {
//                        XjumlahBenar ++;
//                    }
//                    XjumlahCFG ++;
//                }
//            }
//            
//            System.out.println("Akurasi Survey Dengan Nama File : " + CFGSurvey.get(i).getNamaFile() + " : " + (100.0*XjumlahBenar/XjumlahCFG));
//            XjumlahBenar = 0;
//            XjumlahCFG = 0;
//        }
        
//        System.out.println("Tabel");
////        System.out.println("ATURAN\t\t\t " + "NILAI PREDIKSI\t\t" + "NILAI VALID\t\t");
//        String formattedStringHeader = String.format("%-15s %-30s %-30s %-30s %-15s %-15s %-20s", "Kalimat", "Aturan Aktual", "Aturan Valid", "Aturan Saran", "Hasil Valid",  "Hasil Prediksi", "Beda");
//        System.out.println(formattedStringHeader);
//        for (int i = 0; i < hasilPrediksi.size(); i++) {
//            for (int j = 0; j < hasilPrediksi.get(i).getListKalimat().size(); j++) {
//                ArrayList<Aturan> temphasilPrediksi = new ArrayList<>();
//                for (int z = 0; z < hasilPrediksi.get(i).getListKalimat().get(j).getListCFG().size(); z++) {
//                    try {
//                        Aturan x = (Aturan) hasilPrediksi.get(i).getListKalimat().get(j).getListCFG().get(z).clone();
//                        temphasilPrediksi.add(x);
//                    } catch (CloneNotSupportedException ex) {
//                        Logger.getLogger(EvaluasiRecallPrecision.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                
//                ArrayList<Aturan> temp100 = new ArrayList<>();
//                for (int z = 0; z < hasilPrediksi.get(i).getListKalimat().get(j).getListCFG().size(); z++) {
//                    try {
//                        Aturan x = (Aturan) hasilPrediksi.get(i).getListKalimat().get(j).getListCFG().get(z).clone();
//                        temp100.add(x);
//                    } catch (CloneNotSupportedException ex) {
//                        Logger.getLogger(EvaluasiRecallPrecision.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                ArrayList<Aturan> hasilSaran = Suggestion(temp100);
//                for (int k = 0; k < hasilPrediksi.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
////                    try {
////                        String tempAturan = hasilPrediksi.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
////                        for (Aturan x3 : hasilPrediksi.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
////                            tempAturan += x3.getNamaTag() + " ";
////                        }
//                        String tempAturan = temphasilPrediksi.get(k).getNamaTag() + " -> ";
//                        for (Aturan x3 : temphasilPrediksi.get(k).getSintaks()) {
//                            tempAturan += x3.getNamaTag() + " ";
//                        }
//                        
//                        String tempAturanValid = datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
//                        for (Aturan x3 : datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
//                            tempAturanValid += x3.getNamaTag() + " ";
//                        }
//                        
////                        Aturan target = (Aturan) hasilPrediksi.get(i).getListKalimat().get(j).getListCFG().get(k);
//                        
////                        Aturan hasilSaran = Suggestion2(target);
//                        
//                        String tempAturanSuggestion = hasilSaran.get(k).getNamaTag() + " -> ";
//                        for (Aturan x3 : hasilSaran.get(k).getSintaks()) {
//                            tempAturanSuggestion += x3.getNamaTag() + " ";
//                        }
//                        
//                        String beda = "";
//                        if (hasilValidXAktual.get(i).getListKalimat().get(j).getListNilai().get(k) != hasilPrediksi.get(i).getListKalimat().get(j).getListNilai().get(k)) {
//                            beda = "beda";
//                        }
//                        String formattedStringBody = String.format("%-15s %-30s %-30s %-30s %-15d %-15d %-20s", hasilPrediksi.get(i).getListKalimat().get(j).getKalimat(), tempAturan, tempAturanValid, tempAturanSuggestion, hasilValidXAktual.get(i).getListKalimat().get(j).getListNilai().get(k), hasilPrediksi.get(i).getListKalimat().get(j).getListNilai().get(k), beda);
//                        System.out.println(formattedStringBody);
//                        
////                    System.out.println(tempAturan + "\t\t\t" + hasilValidXAktual.get(i).getListKalimat().get(j).getListNilai().get(k)+ "\t\t" + hasilPrediksi.get(i).getListKalimat().get(j).getListNilai().get(k));
////                    } catch (CloneNotSupportedException ex) {
////                        Logger.getLogger(EvaluasiRecallPrecision.class.getName()).log(Level.SEVERE, null, ex);
////                    }
//                }
//            }
//        }
        
//        System.out.println("Suggestion : ");
//        for (SatuFile satuFile : hasilPrediksi) {
//            for (SatuKalimat satuKalimat : satuFile.getListKalimat()) {
//                for (int i = 0; i < satuKalimat.getListCFG().size(); i++) {
//                    ArrayList<String> saran = new ArrayList<>();
//                    if (satuKalimat.getListNilai().get(i) == 0) {
//                        saran = Suggestion(satuKalimat.getListCFG().get(i));
//                    }
//                    String tempAturan = satuKalimat.getListCFG().get(i).getNamaTag() + " -> ";
//                    for (Aturan x3 : satuKalimat.getListCFG().get(i).getSintaks()) {
//                        tempAturan += x3.getNamaTag() + " ";
//                    }
//                    if (saran.size() != 0) {
//                        
//                        System.out.println("Aturan : " + tempAturan);
//                        System.out.println("Saran : ");
//                        for (String x : saran) {
//                            System.out.println("\t" + x);
//                        }
//                    }
//                    
//                }
//            }
//        }
        
//        ArrayList<SatuFile> hasilSistem = new ArrayList<>();
//        int count = 0;
//        int totalCFG = 0;
//        
//        int noKalimat = 1;
//        dataRecallPrecisition = {{0,0},{0,0}};
//        for (int i = 0; i < hasilPrediksi.size(); i++) {
//            for (int j = 0; j < hasilPrediksi.get(i).getListKalimat().size(); j++) {
//                System.out.println("");
//                System.out.println("Kalimat " + noKalimat);
//                noKalimat++;
//                System.out.println("=================");
//                ArrayList<Aturan> tempp = (hasilPrediksi.get(i).getListKalimat().get(j).getListCFG());
//                for (int k = 0; k < tempp.size(); k++) {
//                    System.out.println("");
//                    String ProductionRuleAktual = tempp.get(k).getNamaTag() + " -> ";
//                    for (Aturan x : tempp.get(k).getSintaks()) {
//                        ProductionRuleAktual += x.getNamaTag() + " ";
//                    }
//                    
//                    String validRule = datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
//                    for (Aturan x : datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
//                        validRule += x.getNamaTag() + " ";
//                    }
////                    
////                    data uji => sistem
////                    Np -> NP VP => sistem berubah = 0
//                    dataRecallPrecisition[hasilPrediksi.get(i).getListKalimat().get(j).getListNilai().get(k)][datasetValid.get(i).getListKalimat().get(j).getListNilai().get(k)] ++;
//                    
//                    int trueFalse = 0;
//                    if (validRule.equalsIgnoreCase(ProductionRuleAktual)) {
//                        trueFalse = 1;
//                        count++;
//                    }
//                    System.out.println("Aturan Valid\t\t: " + validRule);
//                    System.out.println("Aturan Hasil Sistem\t: " + ProductionRuleAktual);
//                    System.out.println("Valid = Hasil Sistem?\t: " + trueFalse);
//                    totalCFG++;
////                    System.out.println("++ " + ProductionRuleAktual);
//                }
//            }
//        }
//        
////        ada yang salah disini
//        System.out.println("=======================================");
//        System.out.println("Evaluasi Tanpa Rekomendasi : ");
//        System.out.println("Jumlah benar : " + dataRecallPrecisition[1][1]);
//        System.out.println("Total aturan : " + totalCFG);
////        System.out.println("Akurasi : " + (count*100.0/totalCFG) + "%");
//        System.out.println("Akurasi : " + Accuracy() + "%");
//        System.out.println("=======================================");
//        
//        count = 0;
//        totalCFG = 0;
//        noKalimat = 1;
//        System.out.println("Hasil rekomendasi sistem terhadap aturan responden");
//        for (int i = 0; i < hasilPrediksi.size(); i++) {
//            for (int j = 0; j < hasilPrediksi.get(i).getListKalimat().size(); j++) {
//                System.out.println("");
//                System.out.println("Kalimat " + noKalimat);
//                noKalimat++;
//                System.out.println("=================");
//                ArrayList<Aturan> tempp = Suggestion(hasilPrediksi.get(i).getListKalimat().get(j).getListCFG());
//                ArrayList<Integer> nilaiSuggestion = dataKebenaran;
//                    
//                for (int k = 0; k < tempp.size(); k++) {
//                    System.out.println("");
//                    String ProductionRuleAktual = tempp.get(k).getNamaTag() + " -> ";
//                    for (Aturan x : tempp.get(k).getSintaks()) {
//                        ProductionRuleAktual += x.getNamaTag() + " ";
//                    }
//                    
//                    String validRule = datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
//                    for (Aturan x : datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
//                        validRule += x.getNamaTag() + " ";
//                    }
//                    
//                    int trueFalse = 0;
//                    if (validRule.equalsIgnoreCase(ProductionRuleAktual)) {
//                        trueFalse = 1;
//                        count++;
//                    }
//                    System.out.println("Aturan Valid\t\t: " + validRule);
//                    System.out.println("Aturan Hasil Sistem\t: " + ProductionRuleAktual);
//                    System.out.println("Valid = Hasil Sistem?\t: " + trueFalse);
//                    totalCFG++;
////                    System.out.println("++ " + ProductionRuleAktual);
//                }
//            }
//        }
//        
//        System.out.println("");
//        
////        ada yang salah disini
//        System.out.println("=======================================");
//        System.out.println("Evaluasi Dengan Rekomendasi : ");
//        System.out.println("Jumlah benar : " + count);
//        System.out.println("Total aturan : " + totalCFG);
//        System.out.println("Akurasi : " + (count*100.0/totalCFG) + "%");
//        System.out.println("=======================================");
        
        for (int i = 0; i < datasetAktual.size(); i++) {
            System.out.println("======================");
            System.out.println("Nama File : " + datasetAktual.get(i).getNamaFile());
            
            for (int j = 0; j < datasetAktual.get(i).getListKalimat().size(); j++) {
                System.out.println("\tKalimat " + (j+1) + " : " + datasetAktual.get(i).getListKalimat().get(j).getKalimat());
                for (int k = 0; k < datasetAktual.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
                    String ruleValid = datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    String ruleAktual = datasetAktual2.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    String ruleHasil = hasilSaran.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    
                    for (Aturan x : datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        ruleValid += x.getNamaTag() + " ";
                    }
                    for (Aturan x : datasetAktual2.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        ruleAktual += x.getNamaTag() + " ";
                    }
                    for (Aturan x : hasilSaran.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        ruleHasil += x.getNamaTag() + " ";
                    }
                    System.out.println("Aturan Valid : " + ruleValid);
                    System.out.println("Aturan Awal  : " + ruleAktual);
                    System.out.println("Aturan Hasil : " + ruleHasil);
                    System.out.println("Kebenaran : " + (ruleValid.equals(ruleAktual) + " x " + (ruleAktual.equals(ruleHasil))) + " x kalimat " + (j+1));
                    System.out.println("");
                }
                System.out.println("");
            }
            
            System.out.println("======================");
        }
        
        System.out.println("setelah--");
        cekAkurasi(datasetAktual2);
        
        recall_precision(datasetAktual2, hasilSaran);
        System.out.println("yang survey--");
        ArrayList<SatuFile> CFGSurvey1 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey2 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey3 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey4 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey5 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey6 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey7 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey8 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey9 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey10 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey11 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey12 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey13 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey14 = new ArrayList<>();
        ArrayList<SatuFile> CFGSurvey15 = new ArrayList<>();
        CFGSurvey1.add(CFGSurvey.get(0));
        CFGSurvey2.add(CFGSurvey.get(1));
        CFGSurvey3.add(CFGSurvey.get(2));
        CFGSurvey4.add(CFGSurvey.get(3));
        CFGSurvey5.add(CFGSurvey.get(4));
        CFGSurvey6.add(CFGSurvey.get(5));
        CFGSurvey7.add(CFGSurvey.get(6));
        CFGSurvey8.add(CFGSurvey.get(7));
        CFGSurvey9.add(CFGSurvey.get(8));
        CFGSurvey10.add(CFGSurvey.get(9));
        CFGSurvey11.add(CFGSurvey.get(10));
        CFGSurvey12.add(CFGSurvey.get(11));
        CFGSurvey13.add(CFGSurvey.get(12));
        CFGSurvey14.add(CFGSurvey.get(13));
        CFGSurvey15.add(CFGSurvey.get(14));
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey1);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey2);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey3);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey4);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey5);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey6);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey7);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey8);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey9);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey10);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey11);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey12);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey13);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey14);
        System.out.println("");
        System.out.println("==========");
        recall_precision(datasetAktual2, CFGSurvey15);
        System.out.println("");
        System.out.println("==========");
        
        System.out.println("=================");
        System.out.println("Rata-Rata : ");
        System.out.println("Rerata Akurasi   : " + (rerataAkurasi/12));
        System.out.println("Rerata Recall    : " + (rerataRecall/12));
        System.out.println("Rerata Precision : " + (rerataPrecision/12));
        System.out.println("Rerata F1 Score  : " + (rerataF1/12));
        System.out.println("=================");
        
        System.out.println("");
        System.out.println("==============================================");
        System.out.println("akurasi sistem");
        int totalBenar = 0;
        for (Integer x : jumlahBenar) {
            totalBenar+= x;
        }
        
        int totalCFG = 0;
        for (Integer x : jumlahCFG) {
            totalCFG+= x;
        }
        double akurasi = (100.0*totalBenar/totalCFG);
        System.out.println("Jumlah Benar : " + totalBenar);
        System.out.println("Total CFG : " + totalCFG);
        System.out.println("Akurasi Sistem : " + akurasi);
        
        cekAkurasi(CFGSurvey);
        
//        System.out.println("");
//        System.out.println("akurasi survey");
//        SurveyXValid sxv = new SurveyXValid();
//        sxv.akurasi(datasetValid);
//        System.out.println("Evaluasi Tanpa Rekomendasi : ");
//        EvaluasiAktualXPrediksi();
//        System.out.println("\t Data Riil");
//        System.out.println("\t P \t N");
//        for (int i = 0; i < dataRecallPrecisition.length; i++) {
//            String temp = "P";
//            if (i%2!=0) {
//                temp = "N";
//            }
//            System.out.print("Sistem " + temp + " ");
//            for (int j = 0; j < dataRecallPrecisition[i].length; j++) {
//                System.out.print(dataRecallPrecisition[i][j] + "\t ");
//            }
//            System.out.println("");
//        }
//        System.out.println("Recall : " + Recall());
//        System.out.println("Precision : " + Precision());
//        System.out.println("Total Akurasi : " + Accuracy());
    }
    
    public void setData(ArrayList<Aturan> dataNormalisasi, ArrayList<SatuFile> datasetValid, ArrayList<SatuFile> datasetAktual,  ArrayList<SatuFile> datasetAktual2) {
        this.dataNormalisasi = dataNormalisasi;
        this.datasetValid = datasetValid;
        this.datasetAktual = datasetAktual;
        this.datasetAktual2 = datasetAktual2;
    }
    
    private void cekAkurasi(ArrayList<SatuFile> data) {
        
        int XjumlahBenar = 0;
        int XjumlahCFG = 0;
        for (int i = 0; i < data.size(); i++) {
            System.out.println("nama file : " + data.get(i).getNamaFile());
            for (int j = 0; j < data.get(i).getListKalimat().size(); j++) {
                for (int k = 0; k < data.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
                    String CFGDataValid = datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    for (Aturan x : datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        CFGDataValid += x.getNamaTag() + " ";
                    }
                    
                    String xx = data.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    for (Aturan x : data.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        xx += x.getNamaTag() + " ";
                    }
                    
                    if (CFGDataValid.equals(xx)) {
                        XjumlahBenar ++;
                    }
                    XjumlahCFG ++;
                }
            }
            
            System.out.println("Akurasi Survey Dengan Nama File : " + data.get(i).getNamaFile() + " : " + (100.0*XjumlahBenar/XjumlahCFG));
            XjumlahBenar = 0;
            XjumlahCFG = 0;
        }
    }
    
    //dataset aktual, dataset valid
    //1 = ya/true, 0 = tidak/false
    private void EvaluasiValidXAktual(ArrayList<SatuFile> value) {
        
        hasilValidXAktual = value;
        
        for (int i = 0; i < datasetAktual.size(); i++) {
            ArrayList<SatuKalimat> listKalimatAktual = datasetAktual.get(i).getListKalimat();
            ArrayList<SatuKalimat> listKalimatValid = datasetValid.get(i).getListKalimat();
//            ArrayList<Integer> dataNilai = new ArrayList<>();
            for (int j = 0; j < listKalimatAktual.size(); j++) {
                ArrayList<Aturan> listAturanAktual = listKalimatAktual.get(j).getListCFG();
                ArrayList<Aturan> listAturanValid = listKalimatValid.get(j).getListCFG();
                ArrayList<Integer> dataNilai = new ArrayList<>();
                for (int k = 0; k < listAturanAktual.size(); k++) {
                    String ProductionRuleAktual = listAturanAktual.get(k).getNamaTag() + " -> ";
                    for (Aturan x : listAturanAktual.get(k).getSintaks()) {
                        ProductionRuleAktual += x.getNamaTag() + " ";
                    }
                    
                    String ProductionRuleValid = listAturanValid.get(k).getNamaTag() + " -> ";
                    for (Aturan x : listAturanValid.get(k).getSintaks()) {
                        ProductionRuleValid += x.getNamaTag() + " ";
                    }
//                    System.out.println("cekcekcek");
                    System.out.println("pengecekan : \nAturan Aktual : "+ProductionRuleAktual + "=" + listKalimatAktual.get(j).getListNilai().get(k) + " \nAturan Valid : " + ProductionRuleValid + "="+ listKalimatValid.get(j).getListNilai().get(k) + " : " + ProductionRuleAktual.equals(ProductionRuleValid));
//                    System.out.println(listKalimatAktual.get(j).getListNilai().get(k) + " : " + listKalimatValid.get(j).getListNilai().get(k));
//                    if (!ProductionRuleAktual.contains("S ->")) {
//                        if ((ProductionRuleAktual.equals(ProductionRuleValid) && (listKalimatAktual.get(j).getListNilai().get(k) == listKalimatValid.get(j).getListNilai().get(k))) || (!ProductionRuleAktual.equals(ProductionRuleValid) && (listKalimatAktual.get(j).getListNilai().get(k) != listKalimatValid.get(j).getListNilai().get(k)))) {
                        if ((ProductionRuleAktual.equals(ProductionRuleValid))) {
                            dataNilai.add(1);                        
                        }else {
                            dataNilai.add(0);
                        }
//                    }
                }
                for (Integer xx : dataNilai) {
                    System.out.println("cek2cek : " + xx);
                }
                hasilValidXAktual.get(i).getListKalimat().get(j).setListNilai(dataNilai);
            }
        }
    }
    
    //dataset dicocokan dengan data normalisasi
    private void Prediksi() {
        
        hasilPrediksi = new ArrayList<>();
        
        //mengubah data normalisasi menjadi string agar mudah dicek
        ArrayList<String> dataNormalisasiModeString = new ArrayList<>();
        for (Aturan x : dataNormalisasi) {
            String temp = x.getNamaTag() + " -> ";
            for (Aturan y : x.getSintaks()) {
                temp += y.getNamaTag() + " ";
            }
            dataNormalisasiModeString.add(temp);
        }
        
        for (SatuFile satuFile : datasetAktual) {
            SatuFile untukHasilSatuFile = new SatuFile();
            int count = 1;
            for (SatuKalimat satuKalimat : satuFile.getListKalimat()) {
                SatuKalimat untukHasilSatuKalimat = new SatuKalimat();
                untukHasilSatuKalimat.setKalimat("kalimat "+count);
                count++;
//                System.out.println("waduhx : " + satuKalimat.getKalimat());
                for (int i = 0; i < satuKalimat.getListCFG().size(); i++) {
//                    System.out.println("waduhhh : " + i + " : " + satuKalimat.getListCFG().size());
                    Aturan aturan = satuKalimat.getListCFG().get(i);
                    String temp = aturan.getNamaTag() + " -> ";
                    for (Aturan y : aturan.getSintaks()) {
                        temp += y.getNamaTag() + " ";
                    }
//                    if (!temp.contains("S -> ")) {
                        
                        if (dataNormalisasiModeString.contains(temp)) {
                            untukHasilSatuKalimat.addCFG(aturan, 1);
                        } else {
                            untukHasilSatuKalimat.addCFG(aturan, 0);
                        }
//                    }
                }
                untukHasilSatuFile.addKalimat(untukHasilSatuKalimat);
            }
            hasilPrediksi.add(untukHasilSatuFile);
        }
        
    }
    
    //hasil prediksi, hasil evaluasi validxaktual
    private void EvaluasiAktualXPrediksi() {
        for (int i = 0; i < hasilPrediksi.size(); i++) {
            ArrayList<SatuKalimat> listKalimatPrediksi = hasilPrediksi.get(i).getListKalimat();
            ArrayList<SatuKalimat> listKalimatValidxAktual = datasetValid.get(i).getListKalimat();
            for (int j = 0; j < listKalimatPrediksi.size(); j++) {
                ArrayList<Integer> listNilaiPrediksi = listKalimatPrediksi.get(j).getListNilai();
                ArrayList<Integer> listNilaiValidxAktual = listKalimatValidxAktual.get(j).getListNilai();
                for (int k = 0; k < listNilaiPrediksi.size(); k++) {
                    
                    
//                    dataRecallPrecisition[listNilaiValidxAktual.get(k)][listNilaiPrediksi.get(k)] ++;
                    dataRecallPrecisition[listNilaiPrediksi.get(k)][listNilaiValidxAktual.get(k)] ++;
                }
            }
        }
    }
    
    //hasil prediksi, hasil evaluasi validxaktual
    private void EvaluasiWarning() {
        for (int i = 0; i < hasilPrediksi.size(); i++) {
            ArrayList<SatuKalimat> listKalimatPrediksi = hasilPrediksi.get(i).getListKalimat();
            ArrayList<SatuKalimat> listKalimatValidxAktual = hasilValidXAktual.get(i).getListKalimat();
            for (int j = 0; j < listKalimatPrediksi.size(); j++) {
                ArrayList<Integer> listNilaiPrediksi = listKalimatPrediksi.get(j).getListNilai();
                ArrayList<Integer> listNilaiValidxAktual = listKalimatValidxAktual.get(j).getListNilai();
                for (int k = 0; k < listNilaiPrediksi.size(); k++) {
                    dataRecallPrecisition[listNilaiValidxAktual.get(k)][listNilaiPrediksi.get(k)] ++;
                }
            }
        }
    }
    
//        System.out.println("True Negative  : " + dataRecallPrecision[1][1]);
//        System.out.println("True Positive  : " + dataRecallPrecision[1][0]); <-
//        System.out.println("False Negative : " + dataRecallPrecision[0][1]);
//        System.out.println("False Positive : " + dataRecallPrecision[0][0]);
    private double Recall(int[][] value) {
        
//        double recall = (dataRecallPrecisition[0][0] / (dataRecallPrecisition[0][0]+dataRecallPrecisition[1][0])) * 100;
        
        double recall = 0.0;
        try {
            recall = (1.0*value[1][0] / (value[1][0]+value[0][1])) * 100.0;
        } catch (Exception E) {
            recall = 0;
        }
        
        return recall;
    }
    
    private double Precision(int[][] value) {
//        double precision = (dataRecallPrecisition[0][0] / (dataRecallPrecisition[0][1]+dataRecallPrecisition[0][0])) * 100;
        
        double precision = 0.0;
        try {
            precision = (1.0*value[1][0] / (value[1][0]+value[0][0])) * 100.0;
        } catch (Exception E) {
            precision = 0;
        }
        
        return precision;
    }
        
    private double Accuracy(int[][] value) {
        double accuracy = ((1.0*value[1][0]+value[1][1]) / (value[0][0]+value[0][1]+value[1][0]+value[1][1]))*100.0;
        
        return accuracy;
    }
    
    //suggestion di cek per komposisi pola
    private ArrayList<String> Suggestion(Aturan aturanTarget) {
        ArrayList<String> hasilSuggestion = new ArrayList<>();
        String ruleTarget = "";
        for (Aturan x : aturanTarget.getSintaks()) {
            ruleTarget += x.getNamaTag() + " ";
        }
        
        for (Aturan aturan : dataNormalisasi) {
            String ruleAturan = "";
            for (Aturan x : aturan.getSintaks()) {
                ruleAturan += x.getNamaTag() + " ";
            }
            if (ruleTarget.equals(ruleAturan) && !hasilSuggestion.contains(aturan.getNamaTag())) {
                hasilSuggestion.add(aturan.getNamaTag());
            }
        }
        
        return hasilSuggestion;
    }
    
    //suggestion 2
    //suggestion di cek per komposisi pola
    private Aturan Suggestion2(Aturan aturanTarget) {
        ArrayList<String> hasilSuggestion = new ArrayList<>();
        Aturan hasil = new Aturan();
        String ruleTarget = "";
        for (Aturan x : aturanTarget.getSintaks()) {
            ruleTarget += x.getNamaTag() + " ";
        }
        
        for (Aturan aturan : dataNormalisasi) {
            String ruleAturan = "";
            for (Aturan x : aturan.getSintaks()) {
                ruleAturan += x.getNamaTag() + " ";
            }
            if (ruleTarget.equals(ruleAturan) && !hasilSuggestion.contains(aturan.getNamaTag())) {
                hasilSuggestion.add(aturan.getNamaTag());
                hasil = aturan;
            }
        }
        
        return hasil;
    }
    
    //auto saran
    private ArrayList<Aturan> Suggestion(ArrayList<Aturan> dataAturan) {
        
        ArrayList<Aturan> hasil = new ArrayList<>();
        dataKebenaran = new ArrayList<>();
        ArrayList<String> stringDataNormalisasi = new ArrayList<>();
        
        for (Aturan aturan : dataNormalisasi) {
            String ruleTarget = aturan.getNamaTag() + " -> ";
            for (Aturan x : aturan.getSintaks()) {
                ruleTarget += x.getNamaTag() + " ";
            }
            stringDataNormalisasi.add(ruleTarget);
        }
        
//        for (Aturan x : dataAturan) {
        for (int i = 0; i < dataAturan.size(); i++) {
            try {
                Aturan x = (Aturan) dataAturan.get(i).clone();
                hasil.add((Aturan) x.clone());
                dataKebenaran.add(1);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(EvaluasiRecallPrecision.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Aturan SavedAturan = new Aturan();
        for (Aturan aturanTarget : hasil) {
            
            String saran = "-";
            String ruleTarget = "";
            int nilaiKebenaran = 1;
            for (Aturan x : aturanTarget.getSintaks()) {
                ruleTarget += x.getNamaTag() + " ";
            }

            if (!stringDataNormalisasi.contains(aturanTarget.getNamaTag()+ " -> "+ruleTarget)) {
                for (Aturan aturan : dataNormalisasi) {
                    String ruleAturan = "";
                    for (Aturan x : aturan.getSintaks()) {
                        ruleAturan += x.getNamaTag() + " ";
                    }
                    if (ruleTarget.equals(ruleAturan)) {
                        nilaiKebenaran = 0;
                        saran = aturan.getNamaTag();
                        break;
                    }
                }
            }
            
//            System.out.println("saran nya adalah " + saran);
            if (!saran.equals("-")) {
                int index = 0;
                for (Aturan temp : hasil) {
                    boolean ruleFound = false;
                    for (Aturan temp2 : temp.getSintaks()) {
//                        System.out.println(temp2.getNamaTag()+" :=: "+aturanTarget.getNamaTag());
                        if (temp2.getNamaTag().equals(aturanTarget.getNamaTag())) {
                            String ruleTemp2 = "";
                            for (Aturan x : temp2.getSintaks()) {
                                ruleTemp2 += x.getNamaTag() + " ";
                            }

//                            System.out.println(ruleTarget+" :==: "+ruleTemp2);
                            if (ruleTarget.equals(ruleTemp2)) {
                                temp2.setNamaTag(saran);
                                ruleFound = true;
                                break;
                            }
                        }
                    }
                    if (ruleFound) {
                        dataKebenaran.set(index, nilaiKebenaran);
                        break;
                    }
                    index++;
                }
                
                aturanTarget.setNamaTag(saran);
                
            }
        }
        
        return hasil;
    }
    
    public void SistemXValid(ArrayList<ArrayList<Aturan>> value) {
        
    }
    
    private double AccuracyRecomendation() {
        double accuracy = 0;
        
        return accuracy;
    }
    
    private double rerataAkurasi = 0.0;
    private double rerataRecall = 0.0;
    private double rerataPrecision = 0.0;
    private double rerataF1 = 0.0;
    
    private void recall_precision(ArrayList<SatuFile> sebelumSistem, ArrayList<SatuFile> setelahSistem) {
//        for (SatuFile x : sebelumSistem) {
//            for (SatuKalimat xx : x.getListKalimat()) {
//                for (int i = 0; i < xx.getListCFG().size(); i++) {
//                    String aturanDataUji = xx.getListCFG().get(i).getNamaTag() + " -> ";
//                    for (Aturan xxx : xx.getListCFG().get(i).getSintaks()) {
//                        aturanDataUji += xxx.getNamaTag() + " ";
//                    }
//                    
//                    
//                    String aturanDataValid = datasetValid.getNamaTag() + " -> ";
//                    for (Aturan xxx : xx.getListCFG().get(i).getSintaks()) {
//                        aturanDataUji += xxx.getNamaTag() + " ";
//                    }
//                }
//            }
//        }
        
        for (int i = 0; i < sebelumSistem.size(); i++) {
            for (int j = 0; j < sebelumSistem.get(i).getListKalimat().size(); j++) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (int k = 0; k < sebelumSistem.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
                    String aturanDataUji = sebelumSistem.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    for (Aturan xxx : sebelumSistem.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        aturanDataUji += xxx.getNamaTag() + " ";
                    }
                    
                    
                    String aturanDataValid = datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    for (Aturan xxx : datasetValid.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        aturanDataValid += xxx.getNamaTag() + " ";
                    }
                    
                    if (aturanDataUji.equals(aturanDataValid)) {
                        temp.add(1);
                    } else {
                        temp.add(0);
                    }
                }
                sebelumSistem.get(i).getListKalimat().get(j).setListNilai(temp);
            }
        }
        
        for (int i = 0; i < setelahSistem.size(); i++) {
            for (int j = 0; j < setelahSistem.get(i).getListKalimat().size(); j++) {
                ArrayList<Integer> temp = new ArrayList<>();
                for (int k = 0; k < setelahSistem.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
                    String aturanDataUji = setelahSistem.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    for (Aturan xxx : setelahSistem.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        aturanDataUji += xxx.getNamaTag() + " ";
                    }
                    
                    String aturanDataValid = datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
                    for (Aturan xxx : datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
                        aturanDataValid += xxx.getNamaTag() + " ";
                    }
                    
                    if (aturanDataUji.equals(aturanDataValid)) {
                        temp.add(1);
                    } else {
                        temp.add(0);
                    }
                }
                setelahSistem.get(i).getListKalimat().get(j).setListNilai(temp);
            }
        }
        
        int[][] dataRecallPrecision = {{0,0},{0,0}};
        for (int i = 0; i < setelahSistem.size(); i++) {
            for (int j = 0; j < setelahSistem.get(i).getListKalimat().size(); j++) {
//                System.out.println("");
                for (int k = 0; k < setelahSistem.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
//                    [setelah][sebelum]
//                    System.out.println("=============");
//                    System.out.println("sebelum : " + sebelumSistem.get(0).getListKalimat().get(j).getListNilai().get(k));
//                    System.out.println("setelah : " + setelahSistem.get(i).getListKalimat().get(j).getListNilai().get(k));
                    dataRecallPrecision[setelahSistem.get(i).getListKalimat().get(j).getListNilai().get(k)][sebelumSistem.get(0).getListKalimat().get(j).getListNilai().get(k)] ++;
                }
                
            }
        }
        System.out.println("Nama File : " + setelahSistem.get(0).getNamaFile());
        System.out.println("True Negative  : " + dataRecallPrecision[1][1]);
        System.out.println("True Positive  : " + dataRecallPrecision[1][0]);
        System.out.println("False Negative : " + dataRecallPrecision[0][1]);
        System.out.println("False Positive : " + dataRecallPrecision[0][0]);
        System.out.println("---------------");
        System.out.println("Accuracy  : " + Accuracy(dataRecallPrecision));
        System.out.println("Recall    : " + Recall(dataRecallPrecision));
        System.out.println("Precision : " + Precision(dataRecallPrecision));
        System.out.println("F1  : " + (2*((Recall(dataRecallPrecision)*Precision(dataRecallPrecision))/(Precision(dataRecallPrecision)+Recall(dataRecallPrecision)))));
        
        if (setelahSistem.get(0).getNamaFile().contains("orang")) {
            rerataAkurasi += Accuracy(dataRecallPrecision);
            rerataRecall += Recall(dataRecallPrecision);
            rerataPrecision += Precision(dataRecallPrecision);
            rerataF1 += (2*((Recall(dataRecallPrecision)*Precision(dataRecallPrecision))/(Precision(dataRecallPrecision)+Recall(dataRecallPrecision))));
            
            System.out.println("=================");
            System.out.println("Rerata Akurasi   : " + (rerataAkurasi));
            System.out.println("Rerata Recall    : " + (rerataRecall));
            System.out.println("Rerata Precision : " + (rerataPrecision));
            System.out.println("Rerata F1 Score  : " + (rerataF1));
            System.out.println("=================");
        }
        
    }
}
