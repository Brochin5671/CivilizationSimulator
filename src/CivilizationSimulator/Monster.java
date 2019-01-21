package CivilizationSimulator;

public class Monster extends Lifeform {
    //variables
    private static int nextid=1; //keeps track of monsters
    
    //constructors
    public Monster(){
        super();
        id=nextid++; //id of monster (increments nextid everytime a new lifeform is made)
    }
    public Monster(int[] genesCopy){
        super(genesCopy); //calls parent and sends in genesCopy
        id=nextid++; //id of monsters (increments nextid everytime a new lifeform is made)
    }
    
    //returns monster
    @Override
    public String getName() {
        return "Monster";
    }
    //resets ids when game is over
    public static void resetId() {
        nextid=1;
    }
    //returns true if random number (0-999) is less than 5, kills half of an enemy's population
    public static boolean purge(){
        if((Math.random()*1000)<5) return true;
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
