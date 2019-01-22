package CivilizationSimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class CivilizationSimulator extends javax.swing.JFrame {
    //variables
    private static int[] targetGenes=new int[10];
    private int POPULATION_HSIZE=10,POPULATION_MSIZE=10,POPULATION_ASIZE=10; //population sizes of all lifeforms
    private int generationNum; //keeps track of generation number
    private int attackMonsters=3,attackAliens=3; //number of attacks for each population for humans
    private boolean mutationEvent=false;
    //lists
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
        //simulation hasn't started so disable buttons
        btnskip1.setEnabled(false);
        btnskip5.setEnabled(false);
        btnskip10.setEnabled(false);
        btnattackmonster.setEnabled(false);
        btnattackalien.setEnabled(false);
    }

    //creates target genes
    public void initializeTargetGenes(){
        for(int i=0;i<10;i++)
            targetGenes[i]=(int)(Math.random()*10);
        System.out.println("Target Genes: "+Arrays.toString(targetGenes));
    }
    //returns target genes for lifeform's training
    public static int[] getTargetGenes(){
        return targetGenes;
    }
    //creates new generation(s) of lifeforms
    public void createNewGeneration(int generations){
        while(generations!=0){
            /*for each civilization, runs through half the population (list sorted worst to best), removing the worst half
            and replacing it with the best half's children who recieve their genes from the best half.
            This works by starting at the end of the list and taking in account for the decreasing size of the list and moving backwards.
            When the genes are sent in, the child mutates the genes. All three lists are sorted and added to each model*/
            //humans
            for(int i=0;i<POPULATION_HSIZE/2;i++){
                hlist.remove(i);
                hlist.add(i,new Human(hlist.get(POPULATION_HSIZE-2-i).getGenes()));
            }
            humanlistmodel.clear();
            Collections.sort(hlist, (Lifeform lf1, Lifeform lf2) -> lf1.compareTo(lf2));
            for(Lifeform lf : hlist)
                humanlistmodel.addElement(lf);
            //monsters
            for(int i=0;i<POPULATION_MSIZE/2;i++){
                mlist.remove(i);
                mlist.add(i,new Monster(mlist.get(POPULATION_MSIZE-2-i).getGenes()));
            }
            monsterlistmodel.clear();
            Collections.sort(mlist, (Lifeform lf1, Lifeform lf2) -> lf1.compareTo(lf2));
            for(Lifeform lf : mlist)
                monsterlistmodel.addElement(lf);
            //aliens
            for(int i=0;i<POPULATION_ASIZE/2;i++){
                alist.remove(i);
                alist.add(i,new Alien(alist.get(POPULATION_ASIZE-2-i).getGenes()));
            }
            alienlistmodel.clear();
            Collections.sort(alist, (Lifeform lf1, Lifeform lf2) -> lf1.compareTo(lf2));
            for(Lifeform lf : alist)
                alienlistmodel.addElement(lf);
            /*decreases generations by one so when generations reaches zero it can stop creating new generations
            this is because of how many generations were requested to be skipped by the user*/
            generations--;
            //updates simulation generation count
            updateLog();
            //checks if there is a winner, and ends simulation
            if(!isWinner().equals("Nobody")){
                //display winner
                txtactivity.append("Congratulations to the "+isWinner()+" on winning\nthe race thanks to "+getWinner(isWinner())+"!");
                //reset simulation stats to default
                Human.resetId();
                Monster.resetId();
                Alien.resetId();
                POPULATION_HSIZE=POPULATION_MSIZE=POPULATION_ASIZE=10;
                attackMonsters=attackAliens=3;
                mutationEvent=false;
                Lifeform.setMutationRate(true);
                //enable and disable buttons to prevent simulation from continuing
                btnstart.setEnabled(true);
                btnskip1.setEnabled(false);
                btnskip5.setEnabled(false);
                btnskip10.setEnabled(false);
                btnattackmonster.setEnabled(false);
                btnattackalien.setEnabled(false);
                break;
            }
            //checks if any actions or abilities are used/occur
            else{
                checkActions();
                txtactivity.append(isWinning()+" are in the lead!"); //displays who's winning
            }
        }
    }
    //updates simulation log
    public void updateLog(){
        generationNum++;
        txtactivity.setText("---Generation "+generationNum+"---\n");
    }
    //checks if any of the populations attack or use an ability, or if a mutation event happens
    public void checkActions(){
        //humans get a recover number, if it does not equal zero they regenerate their population after attack, and update list
        if(Human.recover()){
            int recoverNum=10-POPULATION_HSIZE;
            if(recoverNum==0){
                txtactivity.append("Humans tried to recover, but everything was ok!\n");
            }
            else{
                POPULATION_HSIZE=10;
                for(int i=0;i<recoverNum;i++){
                    hlist.add(new Human());
                }
                Collections.sort(hlist, (Lifeform lf1, Lifeform lf2) -> lf1.compareTo(lf2));
                humanlistmodel.clear();
                for(Lifeform lf : hlist)
                    humanlistmodel.addElement(lf);
                txtactivity.append("Humans recovered from attack!\n");
            }
        }
        //aliens increase population by one and update list
        if(Alien.increasePopulation()){
            POPULATION_ASIZE++;
            alist.add(new Alien());
            Collections.sort(alist, (Lifeform lf1, Lifeform lf2) -> lf1.compareTo(lf2));
            alienlistmodel.clear();
            for(Lifeform lf : alist)
                alienlistmodel.addElement(lf);
            txtactivity.append("Aliens decided to increase their population!\n");
        }
        //monsters reduce the other population sizes by half
        if(Monster.purge()){
            for(int i=0;i<POPULATION_HSIZE/2;i++)
                hlist.remove(i);
            for(int i=0;i<POPULATION_ASIZE/2;i++)
                alist.remove(i);
            POPULATION_HSIZE/=2;
            POPULATION_ASIZE/=2;
            txtactivity.append("Monsters purged half of each civilization!\n");
        }
        //mutation event happens if random number (0-99) is less than 2, affects the chances of a lifeform's child's genes mutating (changing)
        if(!mutationEvent){ //if mutation event hasn't occurred
            if((Math.random()*100)<2){
                Lifeform.setMutationRate(mutationEvent);
                txtactivity.append("A mutation event has occurred!\nThe mutation rate is now "+String.format("%.2f!\n",Lifeform.getMutationRate()));
                mutationEvent=true;
            }
        }
        else{ //if mutation event has occurred, revert to normal if random number (0-99) is less than 10
            if((Math.random()*100)<10){
                Lifeform.setMutationRate(mutationEvent);
                txtactivity.append("The mutation event has ended!\nThe mutation rate is now back to "+Lifeform.getMutationRate()+"!\n");
                mutationEvent=false;
            }
        }
    }
    /*goes through lists comparing high score to score of each civilization
    if lifeform's score is higher, high score becomes the lifeform's score and determines which population is winning*/
    public String isWinning(){
        int highScore=0;
        String winning="";
        for(int i=0;i<POPULATION_HSIZE;i++){
            if(hlist.get(i).getFitness()>highScore){
                highScore=hlist.get(i).getFitness();
                winning="Humans";
            }
        }
        for(int i=0;i<POPULATION_MSIZE;i++){
            if(mlist.get(i).getFitness()>highScore){
                highScore=mlist.get(i).getFitness();
                winning="Monsters";
            }
        }
        for(int i=0;i<POPULATION_ASIZE;i++){
            if(alist.get(i).getFitness()>highScore){
                highScore=alist.get(i).getFitness();
                winning="Aliens";
            }
        }
        return winning;
    }
    //checks each list to see if 10 fitness points (the maximum) was reached so there is a winner and the simulation can end
    public String isWinner(){
        for(int i=0;i<POPULATION_HSIZE;i++)
            if(hlist.get(i).getFitness()==10) return "Humans";
        for(int i=0;i<POPULATION_MSIZE;i++)
            if(mlist.get(i).getFitness()==10) return "Monsters";
        for(int i=0;i<POPULATION_ASIZE;i++)
            if(alist.get(i).getFitness()==10) return "Aliens";
        return "Nobody";
    }
    //returns the winner by sending in name of civilization and searches through list
    public String getWinner(String name){
        if(name.equals("Humans")){
            for(int i=0;i<POPULATION_HSIZE;i++)
                if(hlist.get(i).getFitness()==10) return hlist.get(i).getName()+" #"+hlist.get(i).getId();
        }
        else if(name.equals("Monsters")){
            for(int i=0;i<POPULATION_MSIZE;i++)
                if(mlist.get(i).getFitness()==10) return mlist.get(i).getName()+" #"+mlist.get(i).getId();
        }
        else{
            for(int i=0;i<POPULATION_ASIZE;i++)
                if(alist.get(i).getFitness()==10) return alist.get(i).getName()+" #"+alist.get(i).getId();
        }
        return "Error";
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
        btnattackmonster = new javax.swing.JButton();
        btnskip1 = new javax.swing.JButton();
        btnskip5 = new javax.swing.JButton();
        btnskip10 = new javax.swing.JButton();
        btnhelp = new javax.swing.JButton();
        btnexit = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtactivity = new javax.swing.JTextArea();
        lbltargetgenes = new javax.swing.JLabel();
        btnattackalien = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setMaximumSize(new java.awt.Dimension(939, 600));
        setMinimumSize(new java.awt.Dimension(939, 600));
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

        btnattackmonster.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        btnattackmonster.setText("Attack");
        btnattackmonster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnattackmonsterActionPerformed(evt);
            }
        });

        btnskip1.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        btnskip1.setText("Skip 1 Generation");
        btnskip1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnskip1ActionPerformed(evt);
            }
        });

        btnskip5.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        btnskip5.setText("Skip 5 Generations");
        btnskip5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnskip5ActionPerformed(evt);
            }
        });

        btnskip10.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
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

        lbltargetgenes.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        lbltargetgenes.setText("Target Genes: [?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?]");
        lbltargetgenes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnattackalien.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        btnattackalien.setText("Attack");
        btnattackalien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnattackalienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(360, 360, 360)
                .addComponent(lbltargetgenes)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnskip1)
                .addGap(18, 18, 18)
                .addComponent(btnskip5)
                .addGap(18, 18, 18)
                .addComponent(btnskip10)
                .addGap(230, 230, 230))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
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
                                .addGap(98, 98, 98)
                                .addComponent(btnattackmonster)
                                .addGap(171, 171, 171)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(btnexit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnstart)
                        .addGap(175, 175, 175)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(105, 105, 105))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnattackalien)
                            .addComponent(btnhelp))
                        .addGap(92, 92, 92))))
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
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnattackalien)
                            .addComponent(btnattackmonster))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbltargetgenes)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnskip1)
                    .addComponent(btnskip5)
                    .addComponent(btnskip10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        //clears lists and models
        hlist.clear();
        mlist.clear();
        alist.clear();
        humanlistmodel.clear();
        monsterlistmodel.clear();
        alienlistmodel.clear();
        generationNum=1; //sets gen num to 1
        //create target genes and initial populations
        initializeTargetGenes();
        //lbltargetgenes.setText("Target Genes: "+Arrays.toString(getTargetGenes()));
        //create lifeforms
        for(int i=0;i<POPULATION_HSIZE;i++){ //...HSIZE starts as the official default size (10)
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
        //initial log of simulation
        txtactivity.setText("---Generation 1---\n");
        txtactivity.append(isWinning()+" are in the lead!");
        //enable and disable buttons
        btnstart.setEnabled(false);
        btnskip1.setEnabled(true);
        btnskip5.setEnabled(true);
        btnskip10.setEnabled(true);
        btnattackmonster.setEnabled(true);
        btnattackalien.setEnabled(true);
    }//GEN-LAST:event_btnstartActionPerformed
    //ends program
    private void btnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnexitActionPerformed
    //info and help on playing the simulation
    private void btnhelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhelpActionPerformed
        JOptionPane.showMessageDialog(this,"The humans are racing towards having the best genes on the planet!\nBut, the aliens and monsters want to beat them to it.");
        JOptionPane.showMessageDialog(this,"Every generation, natural selection occurs and replaces the bad half\nof each population with the best half's children.");
        JOptionPane.showMessageDialog(this,"You can use the skip buttons to skip a generation, 5 generations,\nor 10 if you want to speed things up.\n*It is recommended to skip 1 generation at a time so \nthe simulation log for each generation is shown*");
        JOptionPane.showMessageDialog(this,"You can stagnate your enemies progress with your attack.\nBut watch out, they have abilities!");
        JOptionPane.showMessageDialog(this,"Monsters are able to purge.\nThis ability reduces the size of all other populations by half!");
        JOptionPane.showMessageDialog(this,"But don't worry, you are able to recover from purges.");
        JOptionPane.showMessageDialog(this,"Also, Aliens are able to increase their population by one at random!");
        JOptionPane.showMessageDialog(this,"Achieve the target gene and you win.\nDo you have what it takes to lead your population to victory?");
    }//GEN-LAST:event_btnhelpActionPerformed
    //one generation goes by
    private void btnskip1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnskip1ActionPerformed
        createNewGeneration(1);
    }//GEN-LAST:event_btnskip1ActionPerformed
    //five generations go by
    private void btnskip5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnskip5ActionPerformed
        createNewGeneration(5);
    }//GEN-LAST:event_btnskip5ActionPerformed
    //ten generations go by
    private void btnskip10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnskip10ActionPerformed
        createNewGeneration(10);
    }//GEN-LAST:event_btnskip10ActionPerformed
    //attacks monsters
    private void btnattackmonsterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnattackmonsterActionPerformed
        //checks if there are still attacks left, if not, notify user
        if(attackMonsters!=0){
            int removeIndex=listmonsters.getSelectedIndex(); //stores index of selected item
            //checks if there is an item
            if(removeIndex==-1){
                JOptionPane.showMessageDialog(this,"Please select a target to attack.");
                return;
            }
            //removes item from lists
            Lifeform m=mlist.remove(removeIndex);
            monsterlistmodel.remove(removeIndex);
            mlist.add(new Monster());
            //decrement attack and update log
            attackMonsters--;
            txtactivity.append("\nHumans eliminated "+m.getName()+" #"+m.getId()+"!");
        }
        else JOptionPane.showMessageDialog(this,"You are out of attacks on monsters.");
    }//GEN-LAST:event_btnattackmonsterActionPerformed
    //attacks aliens
    private void btnattackalienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnattackalienActionPerformed
        //checks if there are still attacks left, if not, notify user
        if(attackAliens!=0){
            int removeIndex=listaliens.getSelectedIndex(); //stores index of selected item
            //checks if there is an item
            if(removeIndex==-1){
                JOptionPane.showMessageDialog(this,"Please select a target to attack.");
                return;
            }
            //removes item from lists
            Lifeform a=alist.remove(removeIndex);
            alienlistmodel.remove(removeIndex);
            alist.add(new Alien());
            //decrement attack and update log
            attackAliens--;
            txtactivity.append("\nHumans eliminated "+a.getName()+" #"+a.getId()+"!");
        }
        else JOptionPane.showMessageDialog(this,"You are out of attacks on aliens.");
    }//GEN-LAST:event_btnattackalienActionPerformed

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
    private javax.swing.JButton btnattackalien;
    private javax.swing.JButton btnattackmonster;
    private javax.swing.JButton btnexit;
    private javax.swing.JButton btnhelp;
    private javax.swing.JButton btnskip1;
    private javax.swing.JButton btnskip10;
    private javax.swing.JButton btnskip5;
    private javax.swing.JButton btnstart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbltargetgenes;
    private javax.swing.JList<String> listaliens;
    private javax.swing.JList<String> listhumans;
    private javax.swing.JList<String> listmonsters;
    private javax.swing.JTextArea txtactivity;
    // End of variables declaration//GEN-END:variables
}
