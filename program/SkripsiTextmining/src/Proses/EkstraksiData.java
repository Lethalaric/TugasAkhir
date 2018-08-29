/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.util.ArrayList;

/**
 *
 * @author Mukhtar
 */
public class EkstraksiData {
    private ArrayList<String> Datakalimat = new ArrayList<>();
    private String filePath = "";
    
    public void Ekstrak(String filePath) {
        ImportExportFile ief = new ImportExportFile();
        this.filePath = filePath;
        ief.ImportFile(filePath);
        Datakalimat = ief.getIsi();
    }
    
    public ArrayList<String> Proses(){
        ArrayList<String> tempData = new ArrayList<>();
        for (String x : Datakalimat) {
            String hasilCleaning = x.replaceAll(" [a-zA-Z0-9\\-\\.\\,\\_\"\\:\\?\\%\\`\\~\\!\\@\\#\\$\\^\\&\\*\\'\\;\\>\\<\\\\|\\}\\{\\[\\]]*\\)", "\\)");
//            hasilCleaning = hasilCleaning.replaceAll(" ", "_");
            tempData.add(hasilCleaning);
        }
        
        return tempData;
    }

    public ArrayList<String> getDatakalimat() {
        return Datakalimat;
    }
    
    public String getFilePath() {
        return this.filePath;
    }
}
