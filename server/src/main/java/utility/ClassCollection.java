package utility;

import collection.Vehicle;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * class for giving the only instance of vehicle collection
 */
public class ClassCollection {

    private static List<Vehicle> list = Collections.synchronizedList(new LinkedList<>());

    public static List<Vehicle> getList() {
        return list;
    }

    public static void updateCollection(List<Vehicle> newList){
        list.clear();
        list.addAll(newList);
    }


}
