package commands;

import collection.Vehicle;
import utility.CollectionEditor;

import java.util.Scanner;

/**
 * command that adding new element to the collection
 */
public class CommandAdd implements Command {

    private Scanner scanner;
    private CollectionEditor collectionEditor;

    public CommandAdd(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }

    @Deprecated
    @Override
    public String execute() {
        Vehicle vehicle = new Vehicle(getId(), System.out, scanner);
        this.collectionEditor.addElement(vehicle);
        return "unknown element was added to the collection";
    }


    public String execute(Vehicle vehicle){
        vehicle.setId(getId());
        this.collectionEditor.addElement(vehicle);
        return "new element was successfully added to the collection";
    }


    private int getId() {
        if (collectionEditor.isCollectionEmpty()) {
            return 1;
        } else {
            return collectionEditor.getMaxIdOfCollection() + 1;
        }
    }

    @Override
    public String getDescription() {
        return "creating element and adding it to the collection";
    }
}
