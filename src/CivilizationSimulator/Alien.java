package CivilizationSimulator;

public class Alien extends Lifeform {
    //variables
    private static int nextid=1; //keeps track of aliens
    
    //constructors
    public Alien(){
        super();
        id=nextid++; //id of aliens (increments nextid everytime a new lifeform is made)
    }
    public Alien(int[] genesCopy){
        super(genesCopy); //calls parent and sends in genesCopy
        id=nextid++; //id of aliens (increments nextid everytime a new lifeform is made)
    }
    
    //returns alien
    @Override
    public String getName() {
        return "Alien";
    }
    //resets ids when game is over
    public static void resetId() {
        nextid=1;
    }
    //returns true if random number (0-99) is less than 4, increases population by 1
    public static boolean increasePopulation(){
        if((Math.random()*100)<4) return true;
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
