package utility;

import collection.*;
import exceptions.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * class for working with the collection
 */
public class CollectionEditor {
    private List<Vehicle> collection;
    private ZonedDateTime initializationDate;

    /**
     * constructor is private, so there is only one instance of that class
     */
    public CollectionEditor(List<Vehicle> collection) {
        this.initializationDate = ZonedDateTime.now();
        this.collection = collection;
    }

    /**
     * return the average engine power of all items in the collection
     */
    public String getAverageOfEnginePower() {
        if (collection.isEmpty()) {
            return "unable to count average engine power because collection is empty";
        } else {
            float sumOfEnginePowers = collection.stream().
                    map(x -> x.getEnginePower()).
                    reduce((acc, x) -> acc += x).get();
            float averageEnginePower = sumOfEnginePowers / collection.size();
            return "average engine power of all vehicle in a collection: " +
                    averageEnginePower;
        }
    }

    /**
     * cleans collection
     */
    public void cleanCollection() {
        collection.clear();
    }

    /**
     * return all information about collection (size, initializationDate, type of collection, stored type)
     */
    public String getInformationAboutCollection() {
        String size = String.valueOf(collection.size());
        if (collection.isEmpty()) {
            size = "collection is empty";
        }
        String initializationDate = DateTimeFormatter.ISO_DATE_TIME.format(this.initializationDate);
        String type = collection.getClass().getTypeName();
        String storedType = "Vehicle";
        String res = "information about collection:" +
                "\nsize                 | " + size +
                "\ninitializationDate   | " + initializationDate +
                "\ntype of collection   | " + type +
                "\nstored type          | " + storedType;
        return res;
    }

    /**
     * return Fuel type field values in descending order
     */
    public String getTheFuelTypeFieldValuesInDescendingOrder() {
        if (collection.isEmpty()) {
            return "collection is clear, so theres no objects to show their fuel type ¯\\_(ツ)_/¯";
        } else {
            String res = "fuel Type field values of all elements in descending order:\n";
            for (int maxOrdinalAtTheMoment = 4; maxOrdinalAtTheMoment >= 0; maxOrdinalAtTheMoment--) {
                for (Vehicle collectionElement : collection) {
                    if (collectionElement.getFuelType().ordinal() == maxOrdinalAtTheMoment) {
                        res += collectionElement.getFuelType().toString() + "\n";
                    }
                }
            }
            return res;
        }
    }

    /**
     * ensures that there is no element with this id in the collection
     *
     * @param id of element you want to remove
     */
    public boolean removeById(int id) {
        return collection.removeIf(vehicle -> vehicle.getId() == id);
    }

    /**
     * return list of collection elements with their parameters
     */
    public String getStringInterpretationOfCollection() {
        if (collection.isEmpty()) {
            return "Collection is empty";
        } else {
            String res = "Collection of vehicle: ";


            for (Vehicle collectionElement : collection.stream().sorted(Comparator.comparing(Vehicle::getName)).collect(Collectors.toList())) {
                res += "\nname          ¯\\_(ツ)_/¯ " + collectionElement.getName() +
                        "\nid            ¯\\_(ツ)_/¯ " + collectionElement.getId() +
                        "\nxCoordinate   ¯\\_(ツ)_/¯ " + collectionElement.getCoordinates().getX() +
                        "\nyCoordinate   ¯\\_(ツ)_/¯ " + collectionElement.getCoordinates().getY() +
                        "\nenginePower   ¯\\_(ツ)_/¯ " + collectionElement.getEnginePower() +
                        "\nVehicleType   ¯\\_(ツ)_/¯ " + collectionElement.getType() +
                        "\nFuelType      ¯\\_(ツ)_/¯ " + collectionElement.getFuelType() +
                        "\ninitialization time ¯\\_(ツ)_/¯ " + DateTimeFormatter.ISO_DATE_TIME.format(collectionElement.getCreationDate()) +
                        "\n--------------------------";

            }
            return res;
        }
    }

    /**
     * return list of collection elements with their parameters
     */
    public String getStringInterpretationOfCollection(String requesterLogin) {
        if (collection.isEmpty()) {
            return "Collection is empty";
        } else {
            String res = "Collection of vehicle: ";


            for (Vehicle collectionElement : collection) {
               // res += "\u001B[30m";
                if (collectionElement.getOwnerLogin().equals(requesterLogin))
                    res += "\u001B[34m";
                res += "\nname          ¯\\_(ツ)_/¯ " + collectionElement.getName() +
                        "\nid            ¯\\_(ツ)_/¯ " + collectionElement.getId() +
                        "\nxCoordinate   ¯\\_(ツ)_/¯ " + collectionElement.getCoordinates().getX() +
                        "\nyCoordinate   ¯\\_(ツ)_/¯ " + collectionElement.getCoordinates().getY() +
                        "\nenginePower   ¯\\_(ツ)_/¯ " + collectionElement.getEnginePower() +
                        "\nVehicleType   ¯\\_(ツ)_/¯ " + collectionElement.getType() +
                        "\nFuelType      ¯\\_(ツ)_/¯ " + collectionElement.getFuelType() +
                        "\ninitialization time ¯\\_(ツ)_/¯ " + DateTimeFormatter.ISO_DATE_TIME.format(collectionElement.getCreationDate()) +
                        "\u001B[0m" +
                        "\n--------------------------";

            }
            return res;
        }
    }

    /**
     * removing all elements with id greater that the one you specified
     *
     * @param id of the element, all elements greater than which you want to remove
     * @return true if collection changed after operation
     */
    public boolean removeElementsWithIdGreaterThan(int id) {
        return collection.removeIf(x -> x.getId() > id);
    }

    public boolean removeElementsWithIdGreaterThan(int id,String requesterLogin) {
       return collection.removeIf(x -> x.getId() > id &&
               x.getOwnerLogin().equals(requesterLogin));
    }

    /**
     * removing all elements with id lower that the one you specified
     *
     * @param id of the element, all elements lower than which you want to remove
     * @return true if collection changed after operation
     */
    public boolean removeElementsWithIdLowerThan(int id) {
        return collection.removeIf(x -> x.getId() < id);
    }

    public boolean removeElementsWithIdLowerThan(int id, String requesterLogin) {
        return collection.removeIf(x -> x.getId() < id &&
                x.getOwnerLogin().equals(requesterLogin));
    }


    /**
     * return the biggest id in the collection or return 0 if there is no element
     *
     * @return max id of the collection
     */
    public Integer getMaxIdOfCollection() {
        return collection.stream().map(x -> x.getId()).max(Integer::compareTo).orElseGet(() -> -1);
    }

    /**
     * removing last element of the collection
     */
    public void removeLastElement() {
        collection.remove(collection.size() - 1);
    }


    /**
     * return the amount of elements with the same type
     *
     * @param type of Vehicle
     */
    public int getAmountOfElementWithType(VehicleType type) {
        int amount = 0;
        for (Vehicle vehicle : collection) {
            if (!(vehicle.getType() == null) && vehicle.getType().equals(type)) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * adding vehicle to the collection
     *
     * @param vehicle you want to add to the collection
     */
    public void addElement(Vehicle vehicle) {
        collection.add(vehicle);
    }

    /**
     * return boolean depends on whether element in the collection or not
     *
     * @param id of the element you want to check
     */
    public boolean alreadyHaveElementWithSameId(int id) {
        return collection.stream().anyMatch(x -> x.getId() == id);
    }

    /**
     * says whether collection is empty or not
     *
     * @return boolean
     */
    public boolean isCollectionEmpty() {
        return collection.isEmpty();
    }

    /**
     * return collection
     *
     * @return collection
     */
    public Collection<Vehicle> getCollection() {
        return collection;
    }

    /**
     * replace existing element with some id with another with the same id
     *
     * @param element that we want to put instead of existing
     * @throws NoElementWithSuchIdException if theres no elements in the
     *                                      collection with the id of entered element
     */
    public void updateElement(Vehicle element) throws NoElementWithSuchIdException {
        int elementIndex = getElementIndexById(element.getId());
        collection.set(elementIndex, element);
    }

    public void updateElement(Vehicle element, String requesterLogin) throws NoElementWithSuchIdException {
        if (isVehicleOwnedBy(element.getId(), requesterLogin))
            updateElement(element);

    }

    public boolean isVehicleOwnedBy(int idOfTheElement, String requesterLogin) throws NoElementWithSuchIdException {
        if (getVehicleById(idOfTheElement).getOwnerLogin().equals(requesterLogin))
            return true;
        return false;
    }


    /**
     * return element with specified id
     *
     * @param id of the element you want to get
     * @throws NoElementWithSuchIdException if theres no elements in the
     *                                      collection with the id of entered element
     */
    public Vehicle getVehicleById(int id) throws NoElementWithSuchIdException {
        Optional<Vehicle> vehicle = collection.stream().filter(x -> x.getId() == id).findFirst();
        if (vehicle.isPresent())
            return vehicle.get();
        throw new NoElementWithSuchIdException();
    }

    /**
     * says whether collection have element with specified id or not
     *
     * @param id integer value
     * @return boolean
     */
    public boolean isThereAnElementWithSuchId(int id) {
        if (collection.stream().anyMatch(element -> element.getId() == id))
            return true;
        return false;
    }

    /**
     * gives you an element by it index (or throws an exception at you
     *
     * @param id of the element
     * @return int value
     * @throws NoElementWithSuchIdException if element not found
     */
    private int getElementIndexById(int id) throws NoElementWithSuchIdException {
        if (collection.stream().noneMatch(e -> e.getId() == id))
            throw new NoElementWithSuchIdException();
        Vehicle vehicle = collection.stream().filter(e -> e.getId() == id).findFirst().get();
        return collection.indexOf(vehicle);
    }

    public Vehicle getLastElement() {
        return collection.get(getMaxIdOfCollection());
    }

}



