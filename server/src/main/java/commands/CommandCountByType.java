package commands;

import collection.VehicleType;
import utility.CollectionEditor;

/**
 * show the number of elements with the same vehicle type
 */
public class CommandCountByType implements CommandWithArgument {

    private CollectionEditor collectionEditor;
    private String userEnteredType;

    public CommandCountByType(CollectionEditor collectionEditor) {
        this.collectionEditor = collectionEditor;
    }

    public void setArgument(String argument) {
        userEnteredType = argument;
    }

    @Override
    public String execute() {
        int AmountOfElementsWithTheSameType;
        if (isAType(userEnteredType)) {
            userEnteredType = userEnteredType.toUpperCase();
            VehicleType vehicleType = getTypeFromString(userEnteredType);
            AmountOfElementsWithTheSameType = collectionEditor.getAmountOfElementWithType(vehicleType);
            return "There is " + String.valueOf(AmountOfElementsWithTheSameType) +
                    " elements with the type " + userEnteredType.toUpperCase();
        } else {
            return "you wrote a wrong type \"" + userEnteredType + "\", try better";
        }
    }

    @Override
    public String getDescription() {
        return "show the number of elements with the same vehicle type";
    }

    private boolean isAType(String typeForCheck) {
        try {
            typeForCheck = typeForCheck.toUpperCase();
            VehicleType.valueOf(typeForCheck);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (NullPointerException e){
            return false;
        }
    }

    private VehicleType getTypeFromString(String type) {
        return VehicleType.valueOf(type);
    }

    public String getSyntacticsExample() {
        return "" + getName() + " type\n" +
                "available types is:" +
                "\nMOTORCYCLE" +
                "\nCHOPPER" +
                "\nSPACESHIP";
    }
}
