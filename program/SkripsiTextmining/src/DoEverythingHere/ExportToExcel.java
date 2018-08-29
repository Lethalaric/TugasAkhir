/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DoEverythingHere;

import Proses.Kelas.Aturan;
import Proses.Kelas.SatuFile;
import Proses.Kelas.SatuKalimat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 *
 * @author Mukhtar
 */
public class ExportToExcel {
    
    private static final String FILE_NAME = "DetailHasil.xlsx";
    private XSSFWorkbook workbook = null;
    private XSSFSheet sheet = null;
    private ArrayList<ArrayList<String>> dataTypes = new ArrayList<>();
    
    public ExportToExcel() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Detail Hasil");
        dataTypes.add(new ArrayList<>(Arrays.asList("Nama Kalimat", "Aturan Valid", "Aturan Edited", "Aturan yang Disarankan")));
    }
    
    public void setData(String namaKalimat, ArrayList<String> AturanValid, ArrayList<String> AturanEdited, ArrayList<String> AturanSaran) {
        
        for (int i = 0; i < AturanValid.size(); i++) {
            dataTypes.add(new ArrayList<>(Arrays.asList(namaKalimat, AturanValid.get(i), AturanEdited.get(i), AturanSaran.get(i))));
        }

        int rowNum = 0;

        for (ArrayList<String> datatype : dataTypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (String field : datatype) {
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(field);
            }
        }
    }
    
    public void setData(ArrayList<String> namaKalimat, ArrayList<ArrayList<String>> AturanValid, ArrayList<ArrayList<String>> AturanEdited, ArrayList<ArrayList<String>> AturanSaran, ArrayList<SatuFile> CFGSurvey) {
        ArrayList<String> judul = dataTypes.get(0);
        for (SatuFile x : CFGSurvey) {
            judul.add(x.getNamaFile());
        }
        dataTypes.set(0, judul);
        
        ArrayList<ArrayList<ArrayList<String>>> tempSurvey = new ArrayList<>();
        for (SatuFile x : CFGSurvey) {
            ArrayList<ArrayList<String>> tempKalimat = new ArrayList<>();
            for (SatuKalimat xx : x.getListKalimat()) {
                ArrayList<String> tempCFG = new ArrayList<>();
                for (Aturan xxx : xx.getListCFG()) {
                    String temp = xxx.getNamaTag() + " -> ";
                    for (Aturan x4 : xxx.getSintaks()) {
                        temp += x4.getNamaTag() + " ";
                    }
                    tempCFG.add(temp);
                }
                tempKalimat.add(tempCFG);
            }
            tempSurvey.add(tempKalimat);
        }
        
        for (int i = 0; i < AturanValid.size(); i++) {
            ArrayList<String> isian = new ArrayList<>();
            isian.add(namaKalimat.get(i));
            for (int j = 0; j < AturanValid.get(i).size(); j++) {
//                dataTypes.add(new ArrayList<>(Arrays.asList(namaKalimat.get(i), AturanValid.get(i).get(j), AturanEdited.get(i).get(j), AturanSaran.get(i).get(j))));
                isian.add(AturanValid.get(i).get(j));
                isian.add(AturanEdited.get(i).get(j));
                isian.add(AturanSaran.get(i).get(j));
                for (int k = 0; k < tempSurvey.size(); k++) {
                    isian.add(tempSurvey.get(k).get(i).get(j));
                }
                
                dataTypes.add(isian);
                isian = new ArrayList<>();
                isian.add(namaKalimat.get(i));
            }
            
        }

        int rowNum = 0;

        for (ArrayList<String> datatype : dataTypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (String field : datatype) {
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(field);
            }
        }
    }
    
    public void doExport() {
        
        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
