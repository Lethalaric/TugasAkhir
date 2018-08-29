/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

//import java.util.ArrayList;
import java.util.*;
import Proses.Kelas.Aturan;
import Proses.Kelas.SatuFile;
import Proses.Kelas.SatuKalimat;

/**
 *
 * @author Mukhtar
 */
public class CFG {
    private ArrayList<String> SeluruhKalimat = new ArrayList<>();
//    private ArrayList<ArrayList<Aturan>> CFGSeluruhKalimat = new ArrayList<>();
    private SatuFile satuFile = new SatuFile();
//    private ArrayList<Aturan> HasilNormalisasi = new ArrayList<>();
    private ArrayList<Aturan> HasilNormalisasi = new ArrayList<>();
    private boolean stackHabis = true;
    
    public void ProsesPembuatanCFG(EkstraksiData ed) {
        ArrayList<String> data = ed.Proses();
        SeluruhKalimat = data;
        satuFile.setNamaFile(ed.getFilePath().replaceAll(".*\\\\|(.tre)", ""));
        System.out.println(ed.getFilePath());
        for(String s : data) {
            BuatSatuCFG bsCFG = new BuatSatuCFG();
            ArrayList<String> temp = bsCFG.baca(s);
            String a = "";
            for(String s1 : temp) {
                a+=s1;
            }
            bsCFG.doCFG(temp);
            if (bsCFG.getStackHabis()== false) {
                stackHabis = false;
            } 
//            CFGSeluruhKalimat.add(bsCFG.getAturan());
//            for (Aturan x : bsCFG.getAturan()) {
//                System.out.println("213= "+ x.getNamaTag());
//                for (Aturan xx : x.getSintaks()) {
//                    System.out.println("\t123== " + xx.getNamaTag());
//                    for (Aturan xxx : xx.getSintaks()) {
//                        System.out.println("\t\t123=== " + xxx.getNamaTag());
//                    }
//                }
//            }
            SatuKalimat satukalimat = new SatuKalimat(a, bsCFG.getAturan());
            satuFile.addKalimat(satukalimat);
        }
    }
    
    public void ProsesNormalisasiCFG() {
//        HasilNormalisasi.add(CFGSeluruhKalimat.get(0).get(0));
//        for (ArrayList<Aturan> CFGSatuKalimat : CFGSeluruhKalimat) {
//            for (Aturan SatuProductionRule : CFGSatuKalimat) {
//                if (!cekAturan(SatuProductionRule)) {
//                    HasilNormalisasi.add(SatuProductionRule);
//                }
//            }
//        }
        
        HasilNormalisasi.add(satuFile.getListKalimat().get(0).getListCFG().get(0));
        for (SatuKalimat x : satuFile.getListKalimat()) {
            for (Aturan satuProductionRule : x.getListCFG()) {
                if (!cekAturan(satuProductionRule)) {
                    HasilNormalisasi.add(satuProductionRule);
                }
            }
        }
        sortDataNormalisasi();
    }
    
    public void printAll () {
//        System.out.println("Jumlah Kalimat : " + SeluruhKalimat.size());
//        System.out.println("Jumlah Kalimat : " + CFGSeluruhKalimat.size());
//        for (int i = 0; i < SeluruhKalimat.size(); i++) {
//            System.out.println("===== Kalimat "+i+"=====");
//            System.out.println(SeluruhKalimat.get(i));
//            System.out.println("Ukuran Aturan : " + CFGSeluruhKalimat.get(i).size());
//            for (Aturan x : CFGSeluruhKalimat.get(i)) {
//                String temp = "";
//                for (Aturan y : x.getSintaks()) {
//                    temp += y.getNamaTag() + " ";
//                }
//                System.out.println("\t " + x.getNamaTag() + " -> " + temp);
//            }
//            System.out.println("===============================================");
//        }
//        
//        System.out.println("---------------------------------------------");
//        System.out.println("------------ Hasil Normalisasi --------------");
//        for (Aturan x : HasilNormalisasi) {
//            String temp = x.getNamaTag() + " -> ";
//            for (Aturan y : x.getSintaks()) {
//                temp += y.getNamaTag() + " ";
//            }
//            System.out.println(temp);
//        }
        
        System.out.println("Jumlah Kalimat : " + SeluruhKalimat.size());
        System.out.println("Jumlah Kalimat : " + satuFile.getListKalimat().size());
        for (int i = 0; i < satuFile.getListKalimat().size(); i++) {
            System.out.println("===== Kalimat "+i+"=====");
            System.out.println(satuFile.getListKalimat().get(i).getKalimat());
            System.out.println("Ukuran Aturan : " + satuFile.getListKalimat().get(i).getListCFG().size());
            for (Aturan x : satuFile.getListKalimat().get(i).getListCFG()) {
                String temp = "";
                for (Aturan y : x.getSintaks()) {
                    temp += y.getNamaTag() + " ";
                }
                System.out.println("\t " + x.getNamaTag() + " -> " + temp);
            }
            System.out.println("===============================================");
        }
        
        System.out.println("---------------------------------------------");
        System.out.println("------------ Hasil Normalisasi --------------");
        for (Aturan x : HasilNormalisasi) {
            String temp = x.getNamaTag() + " -> ";
            for (Aturan y : x.getSintaks()) {
                temp += y.getNamaTag() + " ";
            }
            System.out.println(temp);
        }
    }
    
    private boolean cekAturan(Aturan value) {
        
        String aturan = value.getNamaTag() + " -> ";
        for (Aturan x : value.getSintaks()) {
            aturan += x.getNamaTag() + " ";
        }
        
        for (Aturan x : HasilNormalisasi) {
            String temp = x.getNamaTag() + " -> ";
            for (Aturan y : x.getSintaks()) {
                temp += y.getNamaTag() + " ";
            }
            
            if (temp.equals(aturan)) {
                return true;
            }
        }
        
        return false;
    }
    
    public void sortDataNormalisasi() {
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
        Collections.sort(HasilNormalisasi, ALPHABETICAL_ORDER);
    }
    
    public void RemoverNormalisasi() {
        ArrayList<String> tempHasil = new ArrayList<>();
        for (Aturan x : HasilNormalisasi) {
            String temp = x.getNamaTag() + " -> ";
            for (Aturan isiRule : x.getSintaks()) {
                if (!isiRule.getNamaTag().equalsIgnoreCase("SYM") && !(isiRule.getNamaTag().equalsIgnoreCase("CCN") && x.getSintaks().indexOf(isiRule) != 0)) {
                    temp += isiRule.getNamaTag() + " ";
                }
            }
            if (!tempHasil.contains(temp)) {
                tempHasil.add(temp);
            }
        }
        
        ArrayList<Aturan> hasil = new ArrayList<>();
        
        for (String x : tempHasil) {
            String var = x.replaceAll(" ->.*", "");
            String[] rule = x.split(".*-> | ");
            Aturan temp = new Aturan(var);
            for (String y : rule) {
                if (!y.equals("")) {
                    
                    Aturan y1 = new Aturan(y);
                    temp.addSintaks(y1);
                }
            }
            hasil.add(temp);
        }
        
        HasilNormalisasi = new ArrayList<>();
        HasilNormalisasi = hasil;
    }
    
    public ArrayList<Aturan> RemoverDataUji2(SatuKalimat perKalimat) {
        ArrayList<String> tempHasil = new ArrayList<>();
        for (Aturan x : perKalimat.getListCFG()) {
            String temp = x.getNamaTag() + " -> ";
            for (Aturan isiRule : x.getSintaks()) {
                if (!isiRule.getNamaTag().equalsIgnoreCase("SYM") && !(isiRule.getNamaTag().equalsIgnoreCase("CCN") && x.getSintaks().indexOf(isiRule) != 0)) {
                    temp += isiRule.getNamaTag() + " ";
                }
            }
            tempHasil.add(temp);
            System.out.println("bb : " + temp);
        }
        
        ArrayList<Aturan> hasil = new ArrayList<>();
        
        for (String x : tempHasil) {
            String var = x.replaceAll(" ->.*", "");
            String[] rule = x.split(".*-> | ");
            Aturan temp = new Aturan(var);
            for (String y : rule) {
                if (!y.equals("")) {
//                    Aturan temp2 = new Aturan()
                    Aturan y1 = new Aturan();
                    boolean alreadyFound = false;
                    for (Aturan dataAturan : perKalimat.getListCFG()) {
                        if (dataAturan.getNamaTag().equals(var)) {
                            for (Aturan dataAturan2 : dataAturan.getSintaks()) {
                                if (dataAturan2.getNamaTag().equals(y)) {
                                    y1 = dataAturan2;
                                    alreadyFound = true;
                                    break;
                                }
                            }
                        }
                        if (alreadyFound) {
                            break;
                        }
                    }
                    temp.addSintaks(y1);
                }
            }
            hasil.add(temp);
        }
        
        return hasil;
    }
    
    public ArrayList<Aturan> RemoverDataUji(SatuKalimat perKalimat) {
        ArrayList<Aturan> hasil = new ArrayList<>();
        
        for (Aturan temp : perKalimat.getListCFG()) {
            Iterator<Aturan> xx = temp.getSintaks().iterator();
            int counter = 0;
            while(xx.hasNext()) {
                Aturan xxx = xx.next();
                if (xxx.getNamaTag().equalsIgnoreCase("SYM") || (xxx.getNamaTag().equalsIgnoreCase("CCN") && counter != 0)) {
                    xx.remove();
//                    temp.getSintaks().remove(i);
                }
                counter++;
            }
            hasil.add(temp);
        }
        
        return hasil;
    }
    
    public ArrayList<Aturan> getHasilNormalisasi() {
        return HasilNormalisasi;
    }

    public ArrayList<String> getSeluruhKalimat() {
        return SeluruhKalimat;
    }

//    public ArrayList<ArrayList<Aturan>> getCFGSeluruhKalimat() {
//        return CFGSeluruhKalimat;
//    }

    public boolean isStackHabis() {
        return stackHabis;
    }

    public SatuFile getSatuFile() {
        return satuFile;
    }

    public void setSatuFile(SatuFile satuFile) {
        this.satuFile = satuFile;
    }
}

class GroupingCFG {
    private String namaGroup = "";
    private ArrayList<Aturan> list = new ArrayList<>();

    public String getNamaGroup() {
        return namaGroup;
    }

    public void setNamaGroup(String namaGroup) {
        this.namaGroup = namaGroup;
    }

    public ArrayList<Aturan> getList() {
        return list;
    }

    public void setList(Aturan value) {
        this.list.add(value);
    }
    
    public void setList(ArrayList<Aturan> list) {
        this.list.addAll(list);
    }
}
