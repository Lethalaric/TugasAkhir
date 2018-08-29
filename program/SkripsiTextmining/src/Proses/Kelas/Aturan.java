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
public class Aturan implements Cloneable{
    private String namaTag = "";
    private ArrayList<Aturan> Sintaks = new ArrayList<>();
    private boolean isVisited = false;

    public Aturan(String value, ArrayList<Aturan> aturan) {
        this.namaTag = value;
        this.Sintaks = aturan;
    }
    
    public Aturan(String value) {
        this.namaTag = value;
        this.Sintaks = new ArrayList<>();
    }
    
    public Aturan() {
        this.namaTag = "";
        this.Sintaks = new ArrayList<>();
    }
    
    public String getNamaTag() {
        return namaTag;
    }

    public void setNamaTag(String namaTag) {
        this.namaTag = namaTag;
    }

    public ArrayList<Aturan> getSintaks() {
        return Sintaks;
    }

    public void setSintaks(ArrayList<Aturan> Sintaks) {
        this.Sintaks = Sintaks;
    }
    
    public void addSintaks(Aturan data) {
        this.Sintaks.add(data);
    }
    
    public void setVisited(boolean value) {
        this.isVisited = value;
    }
    
    public boolean getVisited() {
        return this.isVisited;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
