
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Supermarket {
    private HashMap<String, Shelf> shelves;
    private HashMap<String, Object> catalogs; //<catalog>
    private String name;
    private HashMap<String, Object> solutions; //<solution>


    public Supermarket(String n){
        name = n;
        shelves = new HashMap<>();
        catalogs = new HashMap<>();
        solutions = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Shelf> getShelves() {
        return new ArrayList<>(shelves.values());
    }

    public Shelf getShelf(String s){
        return shelves.get(s);
    }

    public ArrayList<Object> getCatalogs() {
        return new ArrayList<>(catalogs.values());
    }

    public Object getCatalog(String s){
        return catalogs.get(s);
    }

    public ArrayList<Object> getSolutions() {
        return new ArrayList<>(solutions.values());
    }

    public Object getSolution(String s){
        return solutions.get(s);
    }

    /*shelf, catalog, heuristic , id algorithm*/
    public void generateSolution(String sh, String c, String h, int alg){

    }

    public void addShelf(Shelf s){
        shelves.put(s.getName(), s);
    }

    /*Devuelve un boolean indicando si se ha eliminado o no la estanteria con nombre s*/
    public boolean deleteShelf(String s){
        return shelves.remove(s) != null;
    }




}