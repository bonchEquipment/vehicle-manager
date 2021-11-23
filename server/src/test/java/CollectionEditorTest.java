import collection.FuelType;
import collection.Vehicle;
import collection.VehicleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utility.CollectionEditor;

import java.util.ArrayList;
import java.util.List;

public class CollectionEditorTest {

    private CollectionEditor collectionEditor;

    //TODO add method addElement, so that editor will manage id by itself
    @BeforeEach
    void init() {
        List<Vehicle> list = new ArrayList<>();
        list.add(new Vehicle(0, "motorcycle", 10, 101, 144.4f, VehicleType.MOTORCYCLE, FuelType.DIESEL));
        list.add(new Vehicle(1, "motorcycle", -10.4f, 13, 200f, VehicleType.MOTORCYCLE, FuelType.DIESEL));
        list.add(new Vehicle(2, "motorcycle", 308, -40, 19999f, VehicleType.SPACESHIP, FuelType.PLASMA));
        collectionEditor = new CollectionEditor(list);

    }

    @Test
    void isCollectionEmpty_ShouldPass(){
        Assertions.assertFalse(collectionEditor.isCollectionEmpty());
    }


    @Test
    void cleanCollection_ShouldPass(){
        collectionEditor.cleanCollection();
        Assertions.assertTrue(collectionEditor.isCollectionEmpty());
    }




}
