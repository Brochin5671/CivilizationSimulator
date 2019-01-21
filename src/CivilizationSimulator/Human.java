package CivilizationSimulator;

public class Human extends Lifeform {
    //variables
    private static int nextid=1; //keeps track of humans
    
    //constructors
    public Human(){
        super();
        id=nextid++; //id of humans (increments nextid everytime a new lifeform is made)
    }
    public Human(int[] genesCopy){
        super(genesCopy); //calls parent and sends in genesCopy
        id=nextid++; //id of humans (increments nextid everytime a new lifeform is made)
    }
    
    //returns human
    @Override
    public String getName() {
        return "Human";
    }
    //resets ids when game is over
    public static void resetId() {
        nextid=1;
    }
    //returns true if a number (0-99) is less than 5, increases population by number purged
    public static boolean recover(){
        if((Math.random()*100)<5) return true;
        return false;
    }
    //prints object's information
    @Override
    public String toString(){
        String str=getName();
        str+=super.toString();
        return str;
    }
}
