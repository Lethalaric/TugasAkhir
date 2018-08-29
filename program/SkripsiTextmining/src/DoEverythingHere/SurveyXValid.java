/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DoEverythingHere;

import Proses.CFG;
import Proses.EkstraksiData;
import Proses.Kelas.Aturan;
import Proses.Kelas.SatuFile;
import Proses.Kelas.SatuKalimat;
import java.io.File;
import java.util.ArrayList;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 *
 * @author Mukhtar
 */
public class SurveyXValid {
    private ArrayList<SatuFile> data = new ArrayList<>();
    private ArrayList<SatuFile> datasetValid;
    private boolean truefalse = true;
    
    public ArrayList<SatuFile> akurasi() {
        File folder = new File("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\survey\\");
        File[] listOfFiles = folder.listFiles();
        
        for (int i = 0; i < listOfFiles.length; i++) {
            EkstraksiData ed = new EkstraksiData();
            ed.Ekstrak("C:\\Users\\Mukhtar\\Documents\\NetBeansProjects\\SkripsiTextmining\\dataset\\survey\\"+listOfFiles[i].getName());
            CFG cfg = new CFG();
            cfg.ProsesPembuatanCFG(ed);
            
            if (cfg.isStackHabis() == false) {
                truefalse = false;
                data.add(null);
            } else {
                SatuFile temp = new SatuFile();
                temp = cfg.getSatuFile();
                ArrayList<SatuKalimat> arrayKalimat = new ArrayList<>();
                for (SatuKalimat x : temp.getListKalimat()) {
                    SatuKalimat tempKalimat = new SatuKalimat();
                    tempKalimat.setKalimat(x.getKalimat());
                    tempKalimat.setListCFG(cfg.RemoverDataUji(x));
                    arrayKalimat.add(tempKalimat);
                }
                temp.setListKalimat(arrayKalimat);
                data.add(temp);
            }
        }
        
        
        for (SatuFile satuFile : data) {
            for (SatuKalimat satuKalimat : satuFile.getListKalimat()) {
                for (int i = 0; i < satuKalimat.getListCFG().size(); i++) {
                    if (satuKalimat.getListCFG().get(i).getNamaTag().equals("S")) {
                        satuKalimat.getListCFG().remove(i);
                    }
                }
            }
        }
        
        //cek dgn data valid
//        int jumlahBenar = 0;
//        int jumlahCFG = 0;
//        for (int i = 0; i < data.size(); i++) {
//            System.out.println("nama file : " + data.get(i).getNamaFile());
//            for (int j = 0; j < data.get(i).getListKalimat().size(); j++) {
//                for (int k = 0; k < data.get(i).getListKalimat().get(j).getListCFG().size(); k++) {
//                    String CFGDataValid = datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
//                    for (Aturan x : datasetValid.get(0).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
//                        CFGDataValid += x.getNamaTag() + " ";
//                    }
//                    
//                    String CFGSurvey = data.get(i).getListKalimat().get(j).getListCFG().get(k).getNamaTag() + " -> ";
//                    for (Aturan x : data.get(i).getListKalimat().get(j).getListCFG().get(k).getSintaks()) {
//                        CFGSurvey += x.getNamaTag() + " ";
//                    }
//                    
//                    if (CFGDataValid.equals(CFGSurvey)) {
//                        jumlahBenar ++;
//                    }
//                    jumlahCFG ++;
//                }
//            }
//            
//            System.out.println("Akurasi Survey Dengan Nama File : " + data.get(i).getNamaFile() + " : " + (100.0*jumlahBenar/jumlahCFG));
//            jumlahBenar = 0;
//            jumlahCFG = 0;
//        }
        
        return data;
        
    }
}
