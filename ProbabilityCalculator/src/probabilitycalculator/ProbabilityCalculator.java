/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools / Templates
 * and open the template in the editor.
 */
package probabilitycalculator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MS Vaswani
 */
public class ProbabilityCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        HashSet<String> wordSet = new HashSet<>();
        HashSet<String> tagSet = new HashSet<>();
        HashMap<String, Integer> wordTagCount = new HashMap<>();
        HashMap<String, Integer> tagCount = new HashMap<>();
        HashMap<String, Integer> tagTagCount = new HashMap<>();
        HashMap<String, Integer> tagStartCount = new HashMap<>();
        int wordCount = 0, x = 0, startCount = 0;
        String[] bi = new String[2];
//        BufferedWriter biGramWriter = new BufferedWriter(new FileWriter("tagBigrams.txt"));
        String[] temp;
        String t;
//        File folder = new File("corpus/");
//        File[] listOfFiles = folder.listFiles();
        ArrayList<File> listOfFiles = new ArrayList<>();
        listf("data/", listOfFiles);
        for (File file : listOfFiles) {
            if (file.isFile()) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String str;
                    while (null != (str = br.readLine())) {
                        startCount++;
                        temp = str.split(" ");
                        wordCount += temp.length;
                        if (tagStartCount.get(temp[0].split("[/]")[1]) != null) {
                            tagStartCount.replace(temp[0].split("[/]")[1], tagStartCount.get(temp[0].split("[/]")[1]) + 1);
                        } else {
                            tagStartCount.put(temp[0].split("[/]")[1], 1);
                        }
                        for (String s : temp) {
                            try {
                                if (!s.split("[/]")[1].isEmpty()) {
                                    wordSet.add(s.split("[/]")[0]);

                                    tagSet.add(s.split("[/]")[1]);
                                    if (wordTagCount.get(s) != null) {
                                        wordTagCount.replace(s, wordTagCount.get(s) + 1);
                                    } else {
                                        wordTagCount.put(s, 1);
                                    }
                                    if (tagCount.get(s.split("[/]")[1]) != null) {
                                        tagCount.replace(s.split("[/]")[1], (tagCount.get(s.split("[/]")[1]) + 1));
                                    } else {
                                        tagCount.put(s.split("[/]")[1], 1);
                                    }
                                    bi[x] = s.split("[/]")[1];
                                    x++;
                                    if (x == 2) {
                                        t = bi[0] + " " + bi[1];
                                        if (tagTagCount.get(t) != null) {
                                            tagTagCount.replace(t, tagTagCount.get(t) + 1);
                                        } else {
                                            tagTagCount.put(t, 1);
                                        }
//                                biGramWriter.write(bi[0] + " " + bi[1] + "\n");
                                        bi[0] = bi[1];
                                        x = 1;
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println(s);
                            }
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ProbabilityCalculator.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ProbabilityCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //Start Probabilities
        BufferedWriter startProbWriter = new BufferedWriter(new FileWriter("start.txt"));
        BufferedWriter emisionProbWriter = new BufferedWriter(new FileWriter("emission.txt"));
        BufferedWriter transitionProbWriter = new BufferedWriter(new FileWriter("transition.txt"));
        BufferedWriter tagSetWriter = new BufferedWriter(new FileWriter("tags.txt"));
        emisionProbWriter.write(wordSet.size() + "");
        emisionProbWriter.newLine();
        BufferedWriter tagCountWriter = new BufferedWriter(new FileWriter("tagCount.txt"));
        BufferedWriter unknownProbWriter = new BufferedWriter(new FileWriter("unknownProb.txt"));
        for (String tag : tagSet) {
            tagCountWriter.write(tag+" "+tagCount.get(tag));
            tagCountWriter.newLine();
            unknownProbWriter.write(tag + " = " + (1 / ((float) tagCount.get(tag) + wordSet.size())));
            unknownProbWriter.newLine();
            tagSetWriter.write(tag);
            tagSetWriter.newLine();
            if (tagStartCount.get(tag) != null) {
                startProbWriter.write(tag + " " + ((tagStartCount.get(tag) + 1) / (float) startCount));
                startProbWriter.newLine();
            } else {
                startProbWriter.write(tag + " " + (1 / (float) startCount));
                startProbWriter.newLine();
            }
            for (String tag2 : tagSet) {
                if (tagTagCount.get(tag + " " + tag2) != null) {
                    transitionProbWriter.write(tag + " " + tag2 + " " + (((tagTagCount.get(tag + " " + tag2)) / (float) tagCount.get(tag)) + (tagCount.get(tag) / (float) wordCount)));
                    transitionProbWriter.newLine();
                } else {
                    transitionProbWriter.write(tag + " " + tag2 + " " + (0.00000001));
                    transitionProbWriter.newLine();
                }
            }
            for (String word : wordSet) {
                if (wordTagCount.get(word + "/" + tag) != null) {
                    emisionProbWriter.write(tag + " " + word + " " + (((wordTagCount.get(word + "/" + tag) + 1) / ((float) tagCount.get(tag) + wordSet.size()))));
                    emisionProbWriter.newLine();
                } else {
                    emisionProbWriter.write(tag + " " + word + " " + (1 / ((float) tagCount.get(tag) + wordSet.size())));
                    emisionProbWriter.newLine();
                }
            }
        }
        unknownProbWriter.close();
        startProbWriter.close();
        emisionProbWriter.close();
        transitionProbWriter.close();
        tagSetWriter.close();
        tagCountWriter.close();
    }

    public static void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }

}
