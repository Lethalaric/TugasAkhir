
import DoEverythingHere.Praproses;
import DoEverythingHere.ProsesPembuatanPohonSintaks;
import Evaluasi.EvaluasiRecallPrecision;
import java.util.ArrayList;
import Proses.BuatSatuCFG;
import Proses.CFG;
import Proses.EkstraksiData;
import Proses.Kelas.Aturan;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mukhtar
 */
public class kelasTest {
    public static void main(String[] args) {
        Praproses Praproses = new Praproses();
        Praproses.doPraproses();
        System.out.println("terhapusss");
        Praproses.hapusTagS();
        Praproses.printAll();
        ProsesPembuatanPohonSintaks dataAktual = new ProsesPembuatanPohonSintaks();
        dataAktual.doProses("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\aktual");
        dataAktual.hapusTagS();
        dataAktual.printAll2();
        ProsesPembuatanPohonSintaks dataAktual2 = new ProsesPembuatanPohonSintaks();
        dataAktual2.doProses("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\aktual");
        dataAktual2.hapusTagS();
        dataAktual2.printAll2();
        ProsesPembuatanPohonSintaks dataValid = new ProsesPembuatanPohonSintaks();
//        dataValid.doProses("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\valid");
        dataValid.forDataValid("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\valid");
        dataValid.hapusTagS();
        dataValid.printAll2();
        
        System.out.println("cek keanehan : ");
        for (int i = 0; i < dataAktual.getData().size(); i++) {
            for (int j = 0; j < dataAktual.getData().get(i).getListKalimat().size(); j++) {
                for (int k = 0; k < dataAktual.getData().get(i).getListKalimat().get(j).getListCFG().size(); k++) {
                    Aturan zzz = dataAktual.getData().get(i).getListKalimat().get(j).getListCFG().get(k);
                    System.out.println("ini apa ?? "+zzz.getNamaTag());
                    for (Aturan x : zzz.getSintaks()) {
                        System.out.println("\t==" + x.getNamaTag());
                        for (Aturan y : x.getSintaks()) {
                            System.out.println("\t\t==="+y.getNamaTag());
                        }
                    }
                }
            }
        }
        EvaluasiRecallPrecision eval = new EvaluasiRecallPrecision();
        eval.setData(Praproses.getNormalisasi(), dataValid.getData(), dataAktual.getData(), dataAktual2.getData());
        eval.Evaluasi();
    }
}
