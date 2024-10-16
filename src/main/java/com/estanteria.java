import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Estanteria {

    private String name;
    private ArrayList<Set<String>> distribution;


    public Estanteria(String n, int t) {
        name = n;
        distribution = new ArrayList<Set<String>>();
        for (int i = 0; i < t; ++i) {
            distribution.add(new HashSet<String>());
        }

    }

    public String getName() {
        return name;
    }

    public ArrayList<Set<String>> getDistribution() {
        return distribution;
    }
        

}
