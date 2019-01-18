package CivilizationSimulator;

import java.util.Arrays;

public abstract class Lifeform implements Comparable<Lifeform>{
    //variables
    protected static final int GENE_SIZE=10; //size of a lifeform's genes
    protected double MUTATION_RATE; //chance of genes mutating
    protected int[] genes=new int[GENE_SIZE]; //lifeform's genes
    protected int id,fitnessPoints; //lifeform's id and fitness points
    protected String name; //name of lifeform
    
    //original population's lifeform constructor
    public Lifeform(){
        //creates random genes for lifeform
        for(int i=0;i<GENE_SIZE;i++)
            genes[i]=(int)(Math.random()*10);
        //evaluate fitness points
        fitnessPoints=train(CivilizationSimulator.getTargetGenes());
    }
    //child of original population's lifeform constructor, sends in parent's genes
    public Lifeform(int[] genesCopy){
        //creates a copy (tempGenes) of genesCopy
        int[] tempGenes=new int[GENE_SIZE];
        for(int i=0;i<GENE_SIZE;i++)
            tempGenes[i]=genesCopy[i];
        genes=tempGenes;
        //mutates genes
        mutateGenes();
        //evaluate fitness points
        fitnessPoints=train(CivilizationSimulator.getTargetGenes());
    }
    
    //returns name of lifeform
    public abstract String getName();
    //used when a game is reset so ids are back at 1
    public abstract void resetId();
    //returns genes of lifeform
    public final int[] getGenes() {
        return genes;
    }
    //returns lifeform's id
    public final int getId() {
        return id;
    }
    
    public int compareTo(Lifeform lf){
        return this.fitnessPoints - lf.fitnessPoints;
    }
    //returns lifeform's fitness points
    public final int getFitness() {
        return fitnessPoints;
    }
    //evaluates lifeform's fitness points by comparing to targetGenes
    private int train(int[] targetGenes){
        int fitness=0;
        for(int i=0;i<GENE_SIZE;i++)
            if(genes[i]==targetGenes[i]) fitness++; //if gene and target gene match, lifeform gains a point
        return fitness;
    }
    //mutates genes of lifeform
    private void mutateGenes(){
        for(int i=0;i<GENE_SIZE;i++)
            if(Math.random()<MUTATION_RATE) genes[i]=(int)(Math.random()*10);
    }
    //prints object's information
    @Override
    public String toString() {
        return " #"+id+" | Fitness Points: "+fitnessPoints+" | DNA: "+Arrays.toString(genes);
    }
}
