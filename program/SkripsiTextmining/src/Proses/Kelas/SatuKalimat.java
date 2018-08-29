/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses.Kelas;

import Proses.Kelas.Aturan;
import java.util.ArrayList;

/**
 *
 * @author Mukhtar
 */
public class SatuKalimat {
    private String kalimat = "";
    private ArrayList<Aturan> listCFG = new ArrayList<>();
    private ArrayList<Integer> listNilai = new ArrayList<>();
    
    public SatuKalimat() {
        this.kalimat = "===";
        this.listCFG = new ArrayList<>();
        this.listNilai = new ArrayList<>();
    }
    
    public SatuKalimat(String kalimat, ArrayList<Aturan> list) {
        this.kalimat = kalimat;
        this.listCFG = list;
    }
    
    public SatuKalimat(String kalimat, ArrayList<Aturan> list, ArrayList<Integer> listnilai) {
        this.kalimat = kalimat;
        this.listCFG = list;
        this.listNilai = listnilai;
    }

    public String getKalimat() {
        return kalimat;
    }

    public void setKalimat(String kalimat) {
        this.kalimat = kalimat;
    }

    public ArrayList<Aturan> getListCFG() {
        return listCFG;
    }

    public void setListCFG(ArrayList<Aturan> listCFG) {
        this.listCFG = listCFG;
    }
    
    public void addCFG(Aturan cfg, int nilai) {
        this.listCFG.add(cfg);
        this.listNilai.add(nilai);
    }

    public ArrayList<Integer> getListNilai() {
        return listNilai;
    }

    public void setListNilai(ArrayList<Integer> listNilai) {
        this.listNilai = listNilai;
    }
}
