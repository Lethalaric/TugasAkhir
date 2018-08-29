/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DoEverythingHere;

import java.io.File;
import java.util.ArrayList;
import Proses.CFG;
import Proses.EkstraksiData;
import Proses.ImportExportFile;
import Proses.Kelas.Aturan;
import Proses.Kelas.SatuFile;
import Proses.Kelas.SatuKalimat;

/**
 *
 * @author Mukhtar
 */
public class ProsesPembuatanPohonSintaks {
//    private ArrayList<dataCFGPerKalimat> data = new ArrayList<>();
    private ArrayList<SatuFile> data = new ArrayList<>();
    private boolean truefalse = true;
    
    public void doProses(String StringFolder) {
        File folder = new File("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\dataset");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            EkstraksiData ed = new EkstraksiData();
            System.out.println("Nama File = "+listOfFiles[i].getName());
            ed.Ekstrak("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\dataset\\"+listOfFiles[i].getName());
            CFG cfg = new CFG();
            cfg.ProsesPembuatanCFG(ed);
            
            if (cfg.isStackHabis() == false) {
                truefalse = false;
                data.add(null);
            } else {
//                dataCFGPerKalimat temp = new dataCFGPerKalimat();
//                temp.setDataCFG(cfg.getCFGSeluruhKalimat());
//                temp.setDataKalimat(cfg.getSeluruhKalimat());
//                SatuFile temp = new SatuFile();
//                temp = cfg.getSatuFile();
                
                SatuFile temp = new SatuFile();
                temp = cfg.getSatuFile();
                System.out.println("yang ini XXX : "+temp.getNamaFile());
                ArrayList<SatuKalimat> arrayKalimat = new ArrayList<>();
                for (SatuKalimat x : temp.getListKalimat()) {
                    System.out.println("yang ini : " + x.getKalimat() + " " + x.getListCFG().size());
                    SatuKalimat tempKalimat = new SatuKalimat();
                    tempKalimat.setKalimat(x.getKalimat());
//                    tempKalimat.setListNilai(x.getListNilai());
                    tempKalimat.setListCFG(cfg.RemoverDataUji(x));
//                    tempKalimat.setListCFG(x.getListCFG());
                    for (Aturan x1 : tempKalimat.getListCFG()) {
                            
                        System.out.println("213= "+ x1.getNamaTag());
                        for (Aturan xx : x1.getSintaks()) {
                            System.out.println("\t123== " + xx.getNamaTag());
                            for (Aturan xxx : xx.getSintaks()) {
                                System.out.println("\t\t123=== " + xxx.getNamaTag());
                            }
                        }

                    }
                    
//                    tempKalimat.setListCFG(x.getListCFG());
                    System.out.println(" yang inih : " + tempKalimat.getKalimat() + " " + tempKalimat.getListCFG().size());
                    arrayKalimat.add(tempKalimat);
                
                }
                temp.setListKalimat(arrayKalimat);
                System.out.println("yang ini XX : ");
                data.add(temp);
                
//                for (SatuFile satuFile : data) {
//                    System.out.println("===================================");
//                    for (SatuKalimat satuKalimat : satuFile.getListKalimat()) {
//                        System.out.println("-----------------------------------");
//                        for (Aturan x : satuKalimat.getListCFG()) {
//                            
//                            System.out.println("213= "+ x.getNamaTag());
//                            for (Aturan xx : x.getSintaks()) {
//                                System.out.println("\t123== " + xx.getNamaTag());
//                                for (Aturan xxx : xx.getSintaks()) {
//                                    System.out.println("\t\t123=== " + xxx.getNamaTag());
//                                }
//                            }
//                            
//                        }
//                    }
//                }
//                cfg.printAll();
            }
        }
        masukanNilai(StringFolder);
    }

//    public ArrayList<ArrayList<ArrayList<Aturan>>> getData() {
//        ArrayList<ArrayList<ArrayList<Aturan>>> hasil = new ArrayList<>();
//        
//        for (dataCFGPerKalimat x : data) {
//            if (x == null) {
//                hasil.add(null);
//            } else {
//                hasil.add(x.getDataCFG());
//            }
//        }
//        
//        return hasil;
//    }
    
    public ArrayList<SatuFile> getData() {
        ArrayList<SatuFile> hasil = new ArrayList<>();
        
        for (SatuFile x : data) {
            if (x == null) {
                hasil.add(null);
            } else {
                hasil.add(x);
            }
        }
        
        return hasil;
    }
    
    public boolean getTrueFalse() {
        return truefalse;
    }
    
    public void masukanNilai(String StringFolder) {
        if (!StringFolder.equals("")) {
            
//            File folder = new File("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\aktual");
            File folder = new File(StringFolder);
            File[] listOfFiles = folder.listFiles();

            ArrayList<SatuFile> dataNilai = new ArrayList<>();

            for (int i = 0; i < listOfFiles.length; i++) {
                EkstraksiData ed = new EkstraksiData();
                System.out.println(listOfFiles[i].getName());
                ed.Ekstrak(StringFolder+"\\"+listOfFiles[i].getName());
                ArrayList<String> satuBaris = ed.getDatakalimat();
                int indexKalimat = 0;
                ArrayList<Integer> tempNilai = new ArrayList<>();
                for (String x : satuBaris) {

                    String tempNilaiAktual = x.replaceAll("(.*_)|(=.*)", "");
                    System.out.println("101010 : " + tempNilaiAktual);
                    if ((tempNilaiAktual.equals("") && tempNilai.size() != 0)) {
                        data.get(i).getListKalimat().get(indexKalimat).setListNilai(tempNilai);
                        tempNilai = new ArrayList<>();
                        indexKalimat++;
                    } else if (!tempNilaiAktual.equals("")){
                        tempNilai.add(Integer.parseInt(tempNilaiAktual));
                    }
                    if (satuBaris.indexOf(x) == satuBaris.size()-1) {

    //                    tempNilai.add(Integer.parseInt(tempNilaiAktual));
                        data.get(i).getListKalimat().get(indexKalimat).setListNilai(tempNilai);
                        tempNilai = new ArrayList<>();
                    }
                }
            }
        }
    }
    
    public void hapusTagS() {
        for (SatuFile satuFile : data) {
            for (SatuKalimat satuKalimat : satuFile.getListKalimat()) {
                for (int i = 0; i < satuKalimat.getListCFG().size(); i++) {
                    if (satuKalimat.getListCFG().get(i).getNamaTag().equals("S")) {
                        satuKalimat.getListCFG().remove(i);
                    }
                }
            }
        }
    }
    
    public void forDataValid(String stringFolder) {
        File folder = new File(stringFolder);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            EkstraksiData ed = new EkstraksiData();
            System.out.println(listOfFiles[i].getName());
            ed.Ekstrak(stringFolder+"\\"+listOfFiles[i].getName());
            ArrayList<String> satuBaris = ed.getDatakalimat();
            SatuFile sf = new SatuFile();
            SatuKalimat tempKalimat = new SatuKalimat();
            sf.setNamaFile(ed.getFilePath().replaceAll(".*\\\\|(.tre)|(.txt)", ""));
            int count = 0;
            for (String x : satuBaris) {
                //baca data aktual
                //masih error
                //salah tempat nyimpen data nya
                count++;
                Aturan xx = new Aturan();
                String tempJudulKalimat = x.replaceAll("([A-Za-z]{1,10} ->.*)","");
                String tempVariableCFG = x.replaceAll("( .*)|(=.*)", "");
                String[] tempSintaksCFG = x.split("(.*-> )|( )|(_.*)|(=.*)");
                String tempNilaiAktual = x.replaceAll("(.*_)|(=.*)", "");
                System.out.println("count : " + count + " : " + (satuBaris.size()-1));
                if (!tempJudulKalimat.equals("")) {
//                    System.out.println("==== " + !tempJudulKalimat.equals(""));
//                    System.out.println(x.equalsIgnoreCase(satuBaris.get(satuBaris.size()-1)));
                    if (!tempKalimat.getKalimat().equals("===")) {
                        
                        sf.addKalimat(tempKalimat);
                        tempKalimat = new SatuKalimat();
                    }
                    tempKalimat.setKalimat(tempJudulKalimat);
                } else {
                    xx.setNamaTag(tempVariableCFG);
                    for (String a : tempSintaksCFG) {
                        
                        if (!a.equals("")) {

                            xx.addSintaks(new Aturan(a));
                        }
                    }
                    tempKalimat.addCFG(xx, Integer.parseInt(tempNilaiAktual));
                }
                if ((count == satuBaris.size())) {
                    sf.addKalimat(tempKalimat);
                }
            }
            data.add(sf);
        }
    }
    
    public void printAll() {
        System.out.println(data.size());
        for (SatuFile satuFile : data) {
            System.out.println("Nama File : " + satuFile.getNamaFile());
            int count = 0;
            for (SatuKalimat satuKalimat : satuFile.getListKalimat()) {
                System.out.println("===== Kalimat "+(count+1)+"=====");
                for (Aturan x : satuKalimat.getListCFG()) {
                    String temp = x.getNamaTag() + " -> ";
                    for (Aturan y : x.getSintaks()) {
                        temp += y.getNamaTag() + " ";
                    }
                    System.out.println(temp);
                }
                count++;
            }
        }
    }
    
    public void printAll2() {
        System.out.println("print 2");
        System.out.println(data.size());
        for (SatuFile satuFile : data) {
            System.out.println("Nama File : " + satuFile.getNamaFile());
            int count = 0;
            for (SatuKalimat satuKalimat : satuFile.getListKalimat()) {
                System.out.println("===== Kalimat "+(count+1)+"=====");
                for (int i = 0; i < satuKalimat.getListCFG().size(); i++) {
                    System.out.println(satuKalimat.getListCFG().size() + " ::: " +satuKalimat.getListNilai().size());
                    String temp = satuKalimat.getListCFG().get(i).getNamaTag() + " -> ";
                    for (Aturan y : satuKalimat.getListCFG().get(i).getSintaks()) {
                        temp += y.getNamaTag() + " ";
                    }
                    System.out.println("tttt : "+temp + " : " + satuKalimat.getListNilai().get(i));
                }
                count++;
            }
        }
    }
}

//class dataCFGPerKalimat {
//    private ArrayList<ArrayList<Aturan>> dataCFG = new ArrayList<>();
//    private ArrayList<String> dataKalimat = new ArrayList<>();
//
//    public ArrayList<ArrayList<Aturan>> getDataCFG() {
//        return dataCFG;
//    }
//
//    public void setDataCFG(ArrayList<ArrayList<Aturan>> data) {
//        this.dataCFG = data;
//    }
//
//    public ArrayList<String> getDataKalimat() {
//        return dataKalimat;
//    }
//
//    public void setDataKalimat(ArrayList<String> dataKalimat) {
//        this.dataKalimat = dataKalimat;
//    }
//}
