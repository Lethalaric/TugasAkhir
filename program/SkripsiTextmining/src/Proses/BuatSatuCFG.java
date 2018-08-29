/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proses;

import java.util.ArrayList;
import java.util.Stack;
import Proses.Kelas.Aturan;
import java.util.Arrays;

/**
 *
 * @author Mukhtar
 */
public class BuatSatuCFG {
    private ArrayList<Aturan> aturanCFG = new ArrayList<>();
    private boolean stackHabis = true;
    private ArrayList<String> kumpulanKode = new ArrayList<>(Arrays.asList("S","VP","NP","PP","AdjP","AdvP","CP","SBAR","RPN","SQ","NNO","NNP","PPO","CCN","CSN","SYM","PRR","PRI",
                                             "PRK","JJJ","VBI","VBT","VBL","VBE","ADV","NEG","PPN","INT","PHA","DET","PAR","NUM","$$$","UNS","PRN","ADJ","VBP","ADK","ART","LBR","RBR","KUA")) ;
    
    
    public ArrayList<String> baca(String satuKalimat) {
        ArrayList<String> temp = new ArrayList<>();
        String zzz = "";
        for(char x : satuKalimat.toCharArray()) {
//            System.out.println(x + " : " + (x!=')' && x!='('));
//            if (x==' ') {
//                temp.add(zzz);
//                temp.add(""+x);
//                zzz = "";
//            }
            if (x=='(' || x==')' || x==' '){
                if (!zzz.isEmpty() || zzz != "") {
                    temp.add(zzz);
                    zzz = "";
                }
                temp.add(""+x);
            }
            if (x != '(' && x != ')' && x != ' ') {
                zzz+=""+x;
            }
        }
        return temp;
    }
    
    public void doCFG(ArrayList<String> data) {
        Stack<String> stackKata = new Stack<>();
        Stack<Aturan> stackAturan = new Stack<>();
        Aturan temp = new Aturan();
        stackKata.push("BATAS");
        stackAturan.push(new Aturan("BATAS"));
        
        for (String x : data) {
            if (!kumpulanKode.contains(x) && !x.equals(" ") && !x.equals("(") && !x.equals(")")) {
                stackHabis = false;
                System.out.println("yang salah :-"+x+".");
                break;
            }  else {
                if (x.equals(")")) {
                    if (stackKata.peek().equals(" ")) {
    //                    stack.pop();
    //                    String popStack = stack.pop();
    //                    temp.setNamaTag(popStack);
    //                    aturanCFG.add(temp);
    //                    temp = new AturanCFG();
    //                    temp.addSintaks(new AturanCFG(popStack));
    //                    stack.pop();

                        Aturan stackAturanToArrayAturan = stackAturan.pop();
//                        String temp2 = stackAturanToArrayAturan.getNamaTag() + " -> ";
//                        for (Aturan xx : stackAturanToArrayAturan.getSintaks()) {
//                            temp2 += xx.getNamaTag()+ " ";
//                        } 
//                        System.out.println("going to basic " + temp2);
                        aturanCFG.add(stackAturanToArrayAturan);
                        Aturan zzz = stackAturan.pop();
                        zzz.addSintaks(stackAturanToArrayAturan);
//                        String temp3 = "__"+zzz.getNamaTag();
//                        for (Aturan xx : zzz.getSintaks()) {
//                            System.out.println("\t="+xx.getNamaTag());
//                            for (Aturan xxx : xx.getSintaks()) {
//                                System.out.println("\t\t=="+xxx.getNamaTag());
//                            }
//                        } 
//                        System.out.println("going to basic2 " + temp3);
                        stackAturan.push(zzz);

                        stackKata.pop();
                        stackKata.pop();
                        stackKata.pop();

                    } else {
    //                    String popStack = stack.pop();
    //                    stack.pop();
    //                    temp.addSintaks(new AturanCFG(popStack));

                        String hasilPop = stackKata.pop();
                        Aturan zzz = stackAturan.pop();
                        
                        zzz.addSintaks(new Aturan(hasilPop));
                        stackAturan.push(zzz);
                        stackKata.pop();
                    }
                } else {
                    if (x.equals(" ")) {
                        temp.setNamaTag(stackKata.peek());
                        stackAturan.push(temp);
                        temp = new Aturan();
                    }
                    stackKata.push(x);
                }
                if (stackKata.peek().equals("BATAS")) {
                    stackKata.pop();
                    break;
                }
            }
            
        }
        
        if (!stackKata.isEmpty()) {
            stackHabis = false;
        }
    }
    
    public ArrayList<Aturan> getAturan() {
        return this.aturanCFG;
    }
    
    public boolean getStackHabis() {
        return stackHabis;
    }
}