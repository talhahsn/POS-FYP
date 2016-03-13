/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generictagger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author MS Vaswani
 */
public class DataAccess {

    public static HashMap<String, Double> emmisionProbMap = new HashMap<>();
    public static HashMap<String, Double> taggedEmissionMap = new HashMap<>();
    public static HashMap<String, Double> unTaggedEmissionMap = new HashMap<>();
    public static HashMap<String, Double> tagCount = new HashMap<>();

    public static HashMap<String, Double> urduWordWeight = new HashMap<>();
    public static HashMap<String, Double> sindhiWordWeight = new HashMap<>();
    public static HashMap<String, Double> punjabiWordWeight = new HashMap<>();
    public static Double minimumWordWeight = 0.000001;
    public static Double maxEmissionProb = 4.0;
    public static HashMap<String, String> sindhiTaggedWords = new HashMap<>();
    public static HashMap<String, String> punjabiTaggedWords = new HashMap<>();
    public static HashMap<String, String> ruleMap = new HashMap<>();

    public static String[] getTagset(String tagsFile) throws FileNotFoundException, IOException {
        String str;
        ArrayList<String> temp = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(tagsFile));
        while ((str = br.readLine()) != null) {
            temp.add(str);

        }
        br.close();
        String[] tags = temp.toArray(new String[temp.size()]);
        return tags;
    }

    static double[][] getTransitionProb(int size, String transFile) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(transFile));
        double[][] transPro = new double[size][size];
        String str;

        for (int i = 0; i < size; i++) //35 means 35 tags
        {
            for (int j = 0; j < size; j++) {
                str = br.readLine();
                transPro[i][j] = Double.parseDouble(str.split(" ")[2]);
            }
        }

        return transPro;
    }

    static HashMap<String, Double> getAllUrduEmissionProb(String emmisionProbFile) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(emmisionProbFile));
        String str;
        String[] arr;
        while ((str = br.readLine()) != null) {
            try {
                arr = str.split(" ");
                emmisionProbMap.put(arr[0] + " " + arr[1], Double.parseDouble(arr[2]));
            } catch (Exception e) {

            }
        }
        return emmisionProbMap;
    }

    static HashMap<String, String> getTaggedWords(String language, String path) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String str;
        String[] arr;
        switch (language.toLowerCase()) {

            case "sindhi":
                if (sindhiTaggedWords.size() == 0) {
                    while ((str = br.readLine()) != null) {
                        try {
                            arr = str.split(" ");
                            sindhiTaggedWords.put(arr[0], arr[1]);
                        } catch (Exception e) {

                        }
                    }
                }
                return sindhiTaggedWords;
            case "punjabi":
                if (punjabiTaggedWords.size() == 0) {
                    while ((str = br.readLine()) != null) {
                        try {
                            arr = str.split(" ");
                            punjabiTaggedWords.put(arr[0], arr[1]);
                        } catch (Exception e) {

                        }
                    }
                    return punjabiTaggedWords;
                }
            default:
                return null;

        }

    }

    static HashMap<String, Double> getWordWeight(String language, String path) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String str;
        String[] arr;
        switch (language.toLowerCase()) {

            case "urdu":
                while ((str = br.readLine()) != null) {
                    arr = str.split(" ");
                    urduWordWeight.put(arr[0], Double.parseDouble(arr[1]));
                }
                return urduWordWeight;

            case "sindhi":
                while ((str = br.readLine()) != null) {
                    arr = str.split(" ");
                    sindhiWordWeight.put(arr[0], Double.parseDouble(arr[1]));
                }
                return sindhiWordWeight;
            case "punjabi":
                while ((str = br.readLine()) != null) {
                    arr = str.split(" ");
                    punjabiWordWeight.put(arr[0], Double.parseDouble(arr[1]));
                }
                return punjabiWordWeight;
            default:
                return null;

        }
    }

    static HashMap<String, Double> getTaggedEmissionProb(String unkownEmmisionFile) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(unkownEmmisionFile));
        String str;
        String[] arr;
        while ((str = br.readLine()) != null) {
            arr = str.split(" ");
            taggedEmissionMap.put(arr[0], Double.parseDouble(arr[1]));
        }
        return taggedEmissionMap;
    }

    static HashMap<String, Double> getUnTaggedEmissionProb(String unkownEmmisionFile) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(unkownEmmisionFile));
        String str;
        String[] arr;
        while ((str = br.readLine()) != null) {
            arr = str.split(" ");
            unTaggedEmissionMap.put(arr[0], Double.parseDouble(arr[1]));
        }
        return unTaggedEmissionMap;
    }

    static double[][] getUrduEmmissionProb(String[] words, String[] tags) {

        double[][] emission_probability = new double[tags.length][words.length];
        for (int i = 0; i < tags.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (emmisionProbMap.get(tags[i] + " " + words[j]) != null) {
                    try {
                        emission_probability[i][j] = emmisionProbMap.get(tags[i] + " " + words[j]);
                    } catch (Exception e) {
                        System.out.println("");
                    }
                } else {
                    try {
                        if (urduWordWeight.get(words[j]) != null) {
                            emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * urduWordWeight.get(words[j]) / tagCount.get(tags[i]);
                        } else {
                            emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * minimumWordWeight;
                        }
                    }
                    catch(Exception e){
                        System.out.println("");
                    }
                }
            }
        }
        return emission_probability;
    }

    static double[][] getSindhiEmmissionProb(String[] words, String[] tags) {

        double[][] emission_probability = new double[tags.length][words.length];
        for (int i = 0; i < tags.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (sindhiTaggedWords.get(words[j]) != null) {
                    if (sindhiWordWeight.get(words[j]) != null) {
                        if (sindhiTaggedWords.get(words[j]).equals(tags[i])) {
                            emission_probability[i][j] = taggedEmissionMap.get(tags[i]) * sindhiWordWeight.get(words[j]) / tagCount.get(tags[i]);
                            System.out.println(words[j] + " " + sindhiTaggedWords.get(words[j]));
                            System.out.println(words[j] + " " + sindhiTaggedWords.get(words[j]) + ">" + taggedEmissionMap.get(tags[i]) + " * " + sindhiWordWeight.get(words[j]) / tagCount.get(tags[i]) + " = " + (maxEmissionProb * sindhiWordWeight.get(words[j]) / tagCount.get(tags[i])));
                        } else {
                            emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * sindhiWordWeight.get(words[j]) / tagCount.get(tags[i]);
                            System.out.println(words[j] + " " + sindhiTaggedWords.get(words[j]));
                        }
                    } else {
                        if (sindhiTaggedWords.get(words[j]).equals(tags[i])) {
                            emission_probability[i][j] = taggedEmissionMap.get(tags[i]) * minimumWordWeight;
                            System.out.println(words[j] + " " + sindhiTaggedWords.get(words[j]) + ">" + taggedEmissionMap.get(tags[i]) + " * " + minimumWordWeight + " = " + (maxEmissionProb * minimumWordWeight));

                        } else {
                            emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * minimumWordWeight;
                        }
                    }
                } else if (sindhiWordWeight.get(words[j]) != null) {
                    emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * sindhiWordWeight.get(words[j]) / tagCount.get(tags[i]);
                } else {
                    try {
                        emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * minimumWordWeight;
                    } catch (Exception e) {
                        System.out.println("");
                    }
                }

            }
        }
        return emission_probability;
    }

    static double[][] getTransliteratedSindhiEmmissionProb(String[] words, String[] tags) {

        double[][] emission_probability = new double[tags.length][words.length];
        for (int i = 0; i < tags.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (sindhiTaggedWords.get(words[j]) != null) {
                    if (sindhiWordWeight.get(words[j]) != null) {
                        if (sindhiTaggedWords.get(words[j]).equals(tags[i])) {
                            emission_probability[i][j] = maxEmissionProb * sindhiWordWeight.get(words[j]);
                            System.out.println(words[j] + " " + sindhiTaggedWords.get(words[j]));
                            System.out.println(words[j] + " " + sindhiTaggedWords.get(words[j]) + ">" + maxEmissionProb + " * " + sindhiWordWeight.get(words[j]) + " = " + (maxEmissionProb * sindhiWordWeight.get(words[j])));
                        } else {
                            emission_probability[i][j] = taggedEmissionMap.get(tags[i]) * sindhiWordWeight.get(words[j]);
                            System.out.println(words[j] + " " + sindhiTaggedWords.get(words[j]));
                        }
                    } else {
                        if (sindhiTaggedWords.get(words[j]).equals(tags[i])) {
                            emission_probability[i][j] = maxEmissionProb * minimumWordWeight;
                            System.out.println(words[j] + " " + sindhiTaggedWords.get(words[j]) + ">" + maxEmissionProb + " * " + minimumWordWeight + " = " + (maxEmissionProb * minimumWordWeight));

                        } else {
                            emission_probability[i][j] = taggedEmissionMap.get(tags[i]) * minimumWordWeight;
                        }
                    }
                } else if (sindhiWordWeight.get(words[j]) != null) {
                    emission_probability[i][j] = taggedEmissionMap.get(tags[i]) * sindhiWordWeight.get(words[j]);
                } else {
                    try {
                        emission_probability[i][j] = taggedEmissionMap.get(tags[i]) * minimumWordWeight;
                    } catch (Exception e) {
                        System.out.println("");
                    }
                }

            }
        }
        return emission_probability;
    }

    static HashMap<String, String> getTransliterationRules(String path) throws FileNotFoundException, IOException {
        if (ruleMap.size() == 0) {
            BufferedReader rules = new BufferedReader(new FileReader(path));
            ruleMap = new HashMap<>();
            String str;
            String[] arr;
            while ((str = rules.readLine()) != null) {
                try {
                    arr = str.split("،");
                    ruleMap.put(arr[0], arr[1]);
                } catch (Exception e) {
                    System.out.println(str);
                }
            }
            rules.close();
        }
        return ruleMap;
    }

    static String transliterate(String word) {
        String out = "";
        ArrayList<String> temp = new ArrayList<>();
        String[] arr;
        temp.add(out);
        int n;
        for (int i = 0; i < word.length(); i++) {
            if (ruleMap.get(word.charAt(i) + "") != null) {
                arr = ruleMap.get(word.charAt(i) + "").split("/");

                if (arr.length > 1) {
                    n = temp.size();
                    for (int j = 0; j < n; j++) {
                        temp.add(temp.get(j));
                    }
                    n = temp.size();
                    for (int j = 0; j < n / 2; j++) {
                        temp.set(j, temp.get(j) + arr[0]);
                    }
                    for (int j = n / 2; j < n; j++) {
                        temp.set(j, temp.get(j) + arr[1]);
                    }
                } else {
                    for (int j = 0; j < temp.size(); j++) {
                        temp.set(j, temp.get(j) + arr[0]);
                    }
                }

            } else {
                for (int j = 0; j < temp.size(); j++) {
                    temp.set(j, temp.get(j) + word.charAt(i));
                }
            }
        }

        return null;

    }

    static double[][] getPunjabiEmmissionProb(String[] words, String[] tags) {

        double[][] emission_probability = new double[tags.length][words.length];
        for (int i = 0; i < tags.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (punjabiTaggedWords.get(words[j]) != null) {
                    if (punjabiWordWeight.get(words[j]) != null) {
                        if (punjabiTaggedWords.get(words[j]).equals(tags[i])) {
                            emission_probability[i][j] = taggedEmissionMap.get(tags[i]) * punjabiWordWeight.get(words[j]) / tagCount.get(tags[i]);
                            System.out.println(words[j] + " " + punjabiTaggedWords.get(words[j]));
                            System.out.println(words[j] + " " + punjabiTaggedWords.get(words[j]) + ">" + taggedEmissionMap.get(tags[i]) + " * " + punjabiWordWeight.get(words[j]) / tagCount.get(tags[i]) + " = " + (maxEmissionProb * punjabiWordWeight.get(words[j]) / tagCount.get(tags[i])));
                        } else {
                            emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * punjabiWordWeight.get(words[j]) / tagCount.get(tags[i]);
                            System.out.println(words[j] + " " + punjabiTaggedWords.get(words[j]));
                        }
                    } else {
                        if (punjabiTaggedWords.get(words[j]).equals(tags[i])) {
                            emission_probability[i][j] = taggedEmissionMap.get(tags[i]) * minimumWordWeight;
                            System.out.println(words[j] + " " + punjabiTaggedWords.get(words[j]) + ">" + taggedEmissionMap.get(tags[i]) + " * " + minimumWordWeight + " = " + (maxEmissionProb * minimumWordWeight));

                        } else {
                            emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * minimumWordWeight;
                        }
                    }
                } else if (punjabiWordWeight.get(words[j]) != null) {
                    emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * punjabiWordWeight.get(words[j]) / tagCount.get(tags[i]);
                } else {
                    try {
                        emission_probability[i][j] = unTaggedEmissionMap.get(tags[i]) * minimumWordWeight;
                    } catch (Exception e) {
                        System.out.println("");
                    }
                }

            }
        }
        return emission_probability;
    }

    public static void calcWordCount(String path) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String str;
        String[] arr;
        int n = 0;
        double max = 1;
        while ((str = br.readLine()) != null) {

            arr = str.split("[\\s+|,|\"|.|-]");
            for (String s : arr) {
                n++;
                s = s.replaceAll("،", "");
                s = s.replaceAll("[۔]", "");
                s = s.replaceAll("\"", "");

                if (urduWordWeight.get(s) != null && !s.equals("")) {
                    urduWordWeight.replace(s, urduWordWeight.get(s) + 1);

                } else {
                    urduWordWeight.put(s, 1.0);
                }
            }
        }
        Iterator it = urduWordWeight.entrySet().iterator();
        BufferedWriter wr = new BufferedWriter(new FileWriter("weights.txt"));
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            wr.write(pair.getKey() + " " + ((double) pair.getValue()));
            wr.newLine();
            it.remove(); // avoids a ConcurrentModificationException
        }
        wr.close();

    }

    static double[] getStartProb(int size, String path) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String str;
        double[] temp = new double[size];
        int i = 0;
        while ((str = br.readLine()) != null) {
            temp[i] = Double.parseDouble(str.split(" ")[1]);
            i++;
        }

        return temp;

    }

    static void getTagCount(String string) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(string));
        String str;
        String[] arr;
        while ((str = br.readLine()) != null) {
            try {
                arr = str.split(" ");

                tagCount.put(arr[0], Double.parseDouble(arr[1]));
            } catch (Exception e) {

            }
        }
        System.out.println("");
    }

}
