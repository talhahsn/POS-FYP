/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generictagger;

import static generictagger.Viterbi.forwardViterbi;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.text.AttributedCharacterIterator.Attribute.LANGUAGE;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author MS Vaswani
 */
public class MainScreen extends javax.swing.JFrame {

    /**
     * Creates new form MainScreen
     */
    private String language = "urdu";
    private static ArrayList<String[]> sentences = new ArrayList<>();
    private static ArrayList<String[]> output = new ArrayList<>();
    private static String[] tagSet;
    private static double[] start_probability;
    private static double[][] transition_probability;
    private static double[][] emission_probability;

    public MainScreen() {
        initComponents();
        jCheckBox1.setEnabled(false);
        jCheckBox3.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Text", "File" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Inout Type:");

        jCheckBox1.setText("Transliterate");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Word Cluster");

        jCheckBox3.setText("Stem + Transliterate");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jCheckBox4.setText("Morpho specific Tags");

        jLabel2.setText("Method:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel3.setText("Input:");

        jButton1.setText("Tag");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jLabel4.setText("Input:");

        jLabel5.setText("Language:");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Urdu", "Sindhi","Punjabi" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if (jComboBox1.getSelectedIndex() == 1) {
            jTextArea1.setEnabled(false);
            jButton1.setEnabled(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            JFileChooser jFileChooser1 = new JFileChooser();

            jFileChooser1.setFileFilter(filter);
            if (jFileChooser1.showDialog(this, "Select") == 0) {
                try {
                    sentences = new ArrayList<>();
                    sentences = fetchSentences(jFileChooser1.getSelectedFile().getPath());
                } catch (IOException ex) {
                    Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "File not opening.");
            }
            jButton1.setEnabled(true);

        } else {
            jTextArea1.setEnabled(true);
            sentences = new ArrayList<>();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        language = jComboBox2.getSelectedItem().toString().toLowerCase();
        if (jComboBox2.getSelectedIndex() != 0) {
            jCheckBox1.setEnabled(true);
            jCheckBox3.setEnabled(true);
            try {
                DataAccess.getWordWeight("urdu", "data\\" + language + "\\weights.txt");
                DataAccess.getTaggedWords(language, "data\\" + language + "\\taggedwords.txt");
            } catch (IOException ex) {
                Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            jCheckBox1.setEnabled(false);
            jCheckBox3.setEnabled(false);
        }

    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jButton1.setEnabled(false);
        jTextArea1.setEditable(false);
        String[] arr;
        if (jComboBox1.getSelectedIndex() == 0) {
            sentences = new ArrayList<>();
            arr = jTextArea1.getText().split("[\n]");
            for (String str : arr) {
                sentences.add(str.split(" "));
            }
        }
        if (sentences.size() != 0) {
            tag();

        }
        jTextArea1.setEditable(true);
        jButton1.setEnabled(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        try {
            // TODO add your handling code here:
            DataAccess.getTransliterationRules("data\\transliterationRules.txt");
        } catch (IOException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables

    void start() throws IOException {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
        jTextArea2.setEditable(false);
        DataAccess.getTransliterationRules("data\\transliterationRules.txt");
//        DataAccess.transliterate("هاتي");

//        jCheckBox1.setEnabled(false);
//        jCheckBox2.setEnabled(false);
        DataAccess.getTagCount(("data\\tagCount.txt"));
//        DataAccess.calcWordCount("data\\urdu\\output.txt");
//        System.out.println("ad");
        
        DataAccess.getAllUrduEmissionProb("data\\urdu\\emission.txt");
        tagSet = DataAccess.getTagset("data\\tagset.txt");
        transition_probability = DataAccess.getTransitionProb(tagSet.length, "data\\transition.txt");
        DataAccess.getTaggedEmissionProb("data\\taggedEmission.txt");
        DataAccess.getUnTaggedEmissionProb("data\\untaggedEmission.txt");
        DataAccess.getWordWeight("urdu", "data\\urdu\\weights.txt");
        start_probability = DataAccess.getStartProb(tagSet.length, "data\\start.txt");
    }

    private static ArrayList<String[]> fetchSentences(String datatxt) throws FileNotFoundException, IOException {
        String str;
        ArrayList<String[]> sentences = new ArrayList<String[]>();
        BufferedReader br = new BufferedReader(new FileReader(datatxt));
        String arr[];
        while ((str = br.readLine()) != null) {
            str.trim();
            arr = str.split("۔");
            for (String s : arr) {
                sentences.add(s.split(" "));
            }
        }
        br.close();
        return sentences;
    }

    private void tag() {
        jTextArea2.setText("");
        output = new ArrayList<>();
        String[] temp;
        int[] args;
        if (null != language) {
            switch (language) {
                case "urdu":
                    for (String[] arr : sentences) {
                        emission_probability = DataAccess.getUrduEmmissionProb(arr, tagSet);
//                        forwardViterbi(observations, states, start_probability, transition_probability, emission_probability);
                        Viterbi v = new Viterbi(arr, tagSet, start_probability, transition_probability, emission_probability);
                        args = Viterbi.start();
                        temp = new String[args.length];
                        for (int i = 0; i < args.length - 1; i++) {
                            temp[i] = arr[i] + "/" + tagSet[args[i]] + " ";
                            jTextArea2.append(temp[i]);
                        }
                        jTextArea2.append("\n");
                        output.add(temp);
                    }
                    break;
                case "sindhi":
                    for (String[] arr : sentences) {
                        emission_probability = DataAccess.getSindhiEmmissionProb(arr, tagSet);
                        Viterbi v = new Viterbi(arr, tagSet, start_probability, transition_probability, emission_probability);
                        args = Viterbi.start();
                        temp = new String[args.length];
                        for (int i = 0; i < args.length - 1; i++) {
                            temp[i] = arr[i] + "/" + tagSet[args[i]] + " ";
                            jTextArea2.append(temp[i]);
                        }
                        jTextArea2.append("\n");
                        output.add(temp);
                    }
                    break;
                case "punjabi":
                    for (String[] arr : sentences) {

                        emission_probability = DataAccess.getPunjabiEmmissionProb(arr, tagSet);
                        Viterbi v = new Viterbi(arr, tagSet, start_probability, transition_probability, emission_probability);
                        args = Viterbi.start();
                        temp = new String[args.length];
                        for (int i = 0; i < args.length - 1; i++) {
                            temp[i] = arr[i] + "/" + tagSet[args[i]] + " ";
                            jTextArea2.append(temp[i]);
                        }
                        jTextArea2.append("\n");
                        output.add(temp);

                    }
                    break;
            }

        }

    }

}
