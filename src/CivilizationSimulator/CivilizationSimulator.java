package CivilizationSimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class CivilizationSimulator extends javax.swing.JFrame {
    //variables
    private static int[] targetGenes=new int[10];
    private final static int POPULATION_SIZE=10;
    DefaultListModel humanlistmodel,monsterlistmodel,alienlistmodel;
    ArrayList<Lifeform> hlist = new ArrayList<Lifeform>();
    ArrayList<Lifeform> mlist = new ArrayList<Lifeform>();
    ArrayList<Lifeform> alist = new ArrayList<Lifeform>();
    
    public CivilizationSimulator() {
        initComponents();
        //setup lifeform lists and models
        humanlistmodel=new DefaultListModel();
        listhumans.setModel(humanlistmodel);
        monsterlistmodel=new DefaultListModel();
        listmonsters.setModel(monsterlistmodel);
        alienlistmodel=new DefaultListModel();
        listaliens.setModel(alienlistmodel);
    }

    //creates target genes
    public static void initializeTargetGenes(){
        for(int i=0;i<10;i++)
            targetGenes[i]=(int)(Math.random()*10);
        System.out.println("Target Genes: "+Arrays.toString(targetGenes));
    }
    //returns target genes for lifeform's training
    public static int[] getTargetGenes(){
        return targetGenes;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnstart = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listmonsters = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listhumans = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaliens = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        btnskip1 = new javax.swing.JButton();
        btnskip5 = new javax.swing.JButton();
        btnskip10 = new javax.swing.JButton();
        btnhelp = new javax.swing.JButton();
        btnexit = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtactivity = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setResizable(false);

        btnstart.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        btnstart.setText("Start");
        btnstart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnstartActionPerformed(evt);
            }
        });

        listmonsters.setBackground(new java.awt.Color(0, 153, 255));
        listmonsters.setFont(new java.awt.Font("Segoe UI Symbol", 1, 11)); // NOI18N
        listmonsters.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(listmonsters);

        jLabel1.setFont(new java.awt.Font("Segoe UI Symbol", 1, 18)); // NOI18N
        jLabel1.setText("Monsters");

        jLabel2.setFont(new java.awt.Font("Segoe UI Symbol", 1, 18)); // NOI18N
        jLabel2.setText("Humans");

        jLabel3.setFont(new java.awt.Font("Segoe UI Symbol", 1, 18)); // NOI18N
        jLabel3.setText("Aliens");

        listhumans.setBackground(new java.awt.Color(0, 153, 255));
        listhumans.setFont(new java.awt.Font("Segoe UI Symbol", 1, 11)); // NOI18N
        listhumans.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(listhumans);

        listaliens.setBackground(new java.awt.Color(0, 153, 255));
        listaliens.setFont(new java.awt.Font("Segoe UI Symbol", 1, 11)); // NOI18N
        listaliens.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(listaliens);

        jButton1.setText("Attack");

        btnskip1.setText("Skip 1 Generation");
        btnskip1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnskip1ActionPerformed(evt);
            }
        });

        btnskip5.setText("Skip 5 Generations");
        btnskip5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnskip5ActionPerformed(evt);
            }
        });

        btnskip10.setText("Skip 10 Generations");
        btnskip10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnskip10ActionPerformed(evt);
            }
        });

        btnhelp.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        btnhelp.setText("Help");
        btnhelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhelpActionPerformed(evt);
            }
        });

        btnexit.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        btnexit.setText("Exit");
        btnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexitActionPerformed(evt);
            }
        });

        txtactivity.setEditable(false);
        txtactivity.setColumns(20);
        txtactivity.setRows(5);
        jScrollPane4.setViewportView(txtactivity);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(btnexit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnstart)
                        .addGap(108, 108, 108))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jLabel1)
                                .addGap(259, 259, 259)
                                .addComponent(jLabel2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(jButton1)
                                .addGap(180, 180, 180)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(105, 105, 105))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnhelp)
                        .addGap(92, 92, 92))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnskip1)
                .addGap(18, 18, 18)
                .addComponent(btnskip5)
                .addGap(18, 18, 18)
                .addComponent(btnskip10)
                .addGap(262, 262, 262))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnskip10)
                    .addComponent(btnskip5)
                    .addComponent(btnskip1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnstart)
                    .addComponent(btnhelp)
                    .addComponent(btnexit))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //begins simulation
    private void btnstartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnstartActionPerformed
        //create target genes and initial populations
        initializeTargetGenes();
        txtactivity.setText("Simulation Started.\n---Generation 1---\n");
        //create lifeforms
        for(int i=0;i<POPULATION_SIZE;i++){
            hlist.add(new Human());
            mlist.add(new Monster());
            alist.add(new Alien());
        }
        //sort lists by fitness points
        Collections.sort(hlist, (Lifeform lf1, Lifeform lf2) -> lf1.compareTo(lf2));
        Collections.sort(mlist, (Lifeform lf1, Lifeform lf2) -> lf1.compareTo(lf2));
        Collections.sort(alist, (Lifeform lf1, Lifeform lf2) -> lf1.compareTo(lf2));
        //add to lists
        for(Lifeform lf : hlist)
            humanlistmodel.addElement(lf);
        for(Lifeform lf : mlist)
            monsterlistmodel.addElement(lf);
        for(Lifeform lf : alist)
            alienlistmodel.addElement(lf);
        //disable button
        btnstart.setEnabled(false);
    }//GEN-LAST:event_btnstartActionPerformed
    //ends program
    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnexitActionPerformed
    //info and help on playing the simulation
    private void btnhelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhelpActionPerformed
        JOptionPane.showMessageDialog(this,"The humans are racing towards having the best genes on the planet!\nBut, the aliens and monsters want to beat them to it.");
        JOptionPane.showMessageDialog(this,"Every generation, natural selection occurs and replaces the bad half\nof each population with the best half's children.");
        JOptionPane.showMessageDialog(this,"You can use the skip buttons to skip a generation, 5 generations,\nor 10 if you want to speed things up.");
        JOptionPane.showMessageDialog(this,"You can stagnate your enemies progress with your attack.\nBut watch out, they can attack you too!");
        JOptionPane.showMessageDialog(this,"Achieve the target gene and you win.\nDo you have what it takes to lead your population to victory?");
    }//GEN-LAST:event_btnhelpActionPerformed
    //one generation goes by
    private void btnskip1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnskip1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnskip1ActionPerformed
    //five generations go by
    private void btnskip5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnskip5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnskip5ActionPerformed
    //ten generations go by
    private void btnskip10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnskip10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnskip10ActionPerformed

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
            java.util.logging.Logger.getLogger(CivilizationSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CivilizationSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CivilizationSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CivilizationSimulator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CivilizationSimulator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btnhelp;
    private javax.swing.JButton btnskip1;
    private javax.swing.JButton btnskip10;
    private javax.swing.JButton btnskip5;
    private javax.swing.JButton btnstart;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList<String> listaliens;
    private javax.swing.JList<String> listhumans;
    private javax.swing.JList<String> listmonsters;
    private javax.swing.JTextArea txtactivity;
    // End of variables declaration//GEN-END:variables
}
