/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses.Kelas;

import java.util.ArrayList;

/**
 *
 * @author Mukhtar
 */
public class SatuFile implements Cloneable{
    private String namaFile = "";
    private ArrayList<SatuKalimat> listKalimat = new ArrayList<>();
    private double datahasil = 0.0;
    private int dataCount = 0;
    private ArrayList<SatuKalimat> listKalimatSalah = new ArrayList<>();
    
    public SatuFile() {
        this.namaFile = "";
        this.listKalimat = new ArrayList<>();
    }
    
    public SatuFile(String namaFile, ArrayList<SatuKalimat> list) {
        this.namaFile = namaFile;
        this.listKalimat = list;
    }

    public String getNamaFile() {
        return namaFile;
    }

    public void setNamaFile(String namaFile) {
        this.namaFile = namaFile;
    }

    public ArrayList<SatuKalimat> getListKalimat() {
        return listKalimat;
    }

    public void setListKalimat(ArrayList<SatuKalimat> listKalimat) {
        this.listKalimat = listKalimat;
    }
    
    public void addKalimat(SatuKalimat value) {
        this.listKalimat.add(value);
    }

    public double getDatahasil() {
        return datahasil;
    }

    public void setDatahasil(double datahasil) {
        this.datahasil = datahasil;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public ArrayList<SatuKalimat> getListKalimatSalah() {
        return listKalimatSalah;
    }

    public void setListKalimatSalah(ArrayList<SatuKalimat> listKalimatSalah) {
        this.listKalimatSalah = listKalimatSalah;
    }
    
    public void addListKalimatSalah(SatuKalimat value) {
        listKalimatSalah.add(value);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
