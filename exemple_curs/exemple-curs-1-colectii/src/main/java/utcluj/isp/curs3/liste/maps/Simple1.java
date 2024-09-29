package utcluj.isp.curs3.liste.maps;

import java.util.HashMap;
import java.util.Map;

public class Simple1 {
    public static void main(String[] args) {
// Create a new HashMap
        Map<String, Integer> map = new HashMap<>();

        // Add key-value pairs to the HashMap
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("orange", 3);

        // Search for a key in the HashMap
        String keyToSearch = "banana";
        if (map.containsKey(keyToSearch)) {
            int value = map.get(keyToSearch);
            System.out.println(keyToSearch + " has value " + value);
        } else {
            System.out.println(keyToSearch + " does not exist in the HashMap");
        }
    }
}
