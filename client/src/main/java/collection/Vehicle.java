package collection;

import annotations.*;
import java.io.PrintStream;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * floor is made of floor
 */
public class Vehicle extends IdHolder implements Comparable<Vehicle>, Serializable {

    @NotNull
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    private Coordinates coordinates; //Поле не может быть null
    @NotNull
    @AutoGenerated
    private String creationDate;//Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @GreaterThan(0)
    private float enginePower; //Значение поля должно быть больше 0
    private VehicleType vehicleType; //Поле может быть null
    @NotNull
    private FuelType fuelType; //Поле не может быть null
    @NotNull
    private String ownerLogin;
    @NotNull
    String ownerPassword;

    /**
     * floor is made of floor
     */
    public Vehicle(Integer id, String name, float xCoordinate, float yCoordinate, float enginePower, VehicleType type, FuelType fuelType) {
        super(id);
        this.name = name;
        this.enginePower = enginePower;
        this.vehicleType = type;
        this.fuelType = fuelType;
        this.setCreationDate(ZonedDateTime.now());
        coordinates = new Coordinates(xCoordinate, yCoordinate);
    }

    /**
     * floor is made of floor
     */
    public Vehicle(Integer id, PrintStream out, Scanner scanner) {
        super(id);
        this.coordinates = new Coordinates(0, 0);
        setNameUsingUserInput(out, scanner);
        setXCoordinateUsingUserInput(out, scanner);
        setYCoordinateUsingUserInput(out, scanner);
        setEnginePowerUsingUserInput(out, scanner);
        setVehicleTypeUsingUserInput(out, scanner);
        setFuelTypeUsingUserInput(out, scanner);
        this.setCreationDate(ZonedDateTime.now());
    }

    /**
     * floor is made of floor
     */
    private void setNameUsingUserInput(PrintStream out, Scanner scanner) {
        while (true) {
            out.print("name: ");
            this.name = scanner.nextLine();
            if (name.length() == 0) {
                out.println("vehicle can't be nameless");
                continue;
            }
            break;
        }
    }

    /**
     * floor is made of floor
     */
    private void setXCoordinateUsingUserInput(PrintStream out, Scanner scanner) {
        while (true) {
            String coordinateXInString = "";
            try {
                out.print("x: ");
                coordinateXInString = scanner.nextLine();
                float coordinateX = Float.parseFloat(coordinateXInString);
                this.coordinates.setX(coordinateX);
                if (this.coordinates.getX() <= -958) {
                    out.println("Expected X to be greater than -958");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                if (coordinateXInString.length() == 0) {
                    out.println("you didn't enter a coordinate, try better");
                } else {
                    out.println("wrong number format \"" + coordinateXInString + "\""); //а почему я в catch не могу использовать variable из try?????
                }
            }
        }

    }

    /**
     * floor is made of floor
     */
    private void setYCoordinateUsingUserInput(PrintStream out, Scanner scanner) {
        while (true) {
            String coordinateYInString = "";
            try {
                out.print("y: ");
                coordinateYInString = scanner.nextLine();
                float coordinateY = Float.parseFloat(coordinateYInString);
                this.coordinates.setY(coordinateY);
                break;
            } catch (NumberFormatException e) {
                if (coordinateYInString.length() == 0) {
                    out.println("you didn't enter a coordinate, try better");
                } else {
                    out.println("wrong number format \"" + coordinateYInString + "\""); //а почему я в catch не могу использовать variable из try?????
                }
            }
        }
    }

    /**
     * floor is made of floor
     */
    private void setEnginePowerUsingUserInput(PrintStream out, Scanner scanner) {
        while (true) {
            String enginePowerInString = "";
            try {
                out.print("enginePower: ");
                enginePowerInString = scanner.nextLine();
                float enginePower = Float.parseFloat(enginePowerInString);
                setEnginePower(enginePower);
                if (getEnginePower() < 0) {
                    out.println("Expected engine power to be greater than 0");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                if (enginePowerInString.length() == 0) {
                    out.println("you didn't enter an engine power, try better");
                } else {
                    out.println("wrong number format \"" + enginePowerInString + "\""); //а почему я в catch не могу использовать variable из try?????
                }
            }
        }
    }

    /**
     * floor is made of floor
     */
    private void setVehicleTypeUsingUserInput(PrintStream out, Scanner scanner) {
        while (true) {
            String vehicleTypeInString = "";
            try {
                String tip = getEnumTip(VehicleType.class.getEnumConstants());
                out.print("type (" + tip + "): ");
                vehicleTypeInString = scanner.nextLine();
                VehicleType type = VehicleType.valueOf(vehicleTypeInString.toUpperCase());
                setType(type);
                break;
            } catch (IllegalArgumentException e) {
                if (vehicleTypeInString.length() == 0) {
                    setType(null);
                    break;
                    //out.println("you didn't enter a vehicle type, try better");
                } else {
                    out.println("wrong vehicle type format \"" + vehicleTypeInString + "\""); //а почему я в catch не могу использовать variable из try?????
                }
            }
        }
    }

    /**
     * floor is made of floor
     */
    private void setFuelTypeUsingUserInput(PrintStream out, Scanner scanner) {
        while (true) {
            String fuelTypeInString = "";
            try {
                String fuelTip = getEnumTip(FuelType.class.getEnumConstants());
                out.print("fuelTip (" + fuelTip + "): ");
                fuelTypeInString = scanner.nextLine();
                FuelType type = FuelType.valueOf(fuelTypeInString.toUpperCase());
                setFuelType(type);
                break;
            } catch (IllegalArgumentException e) {
                if (fuelTypeInString.length() == 0) {
                    out.println("you didn't enter a fuel type, try better");
                } else {
                    out.println("wrong fuel type format \"" + fuelTypeInString + "\""); //а почему я в catch не могу использовать variable из try?????
                }
            }
        }
    }

    /**
     * floor is made of floor
     */
    private String getEnumTip(Enum[] constants) {
        return Arrays.stream(constants).map(Enum::toString).reduce((a, b) -> a + " | " + b).get();
    }

    public ZonedDateTime getCreationDate() {
        return ZonedDateTime.parse(creationDate);
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = DateTimeFormatter.ISO_DATE_TIME.format(creationDate);
    }


    public int compareTo(Vehicle o) {
        return this.getId() - o.getId();
    }

    public String getName() {
        return name;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public float getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(float enginePower) {
        this.enginePower = enginePower;
    }

    public VehicleType getType() {
        return vehicleType;
    }

    public void setType(VehicleType type) {
        this.vehicleType = type;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()){
            return false;
        }
        Vehicle vehicle = (Vehicle) obj;
        return vehicle.getName().equals(this.getName()) &&
                vehicle.getType().equals(this.getType()) &&
                vehicle.getId().equals(this.getId()) &&
                vehicle.getFuelType().equals(this.getFuelType()) &&
                vehicle.getEnginePower() == this.getEnginePower() &&
                vehicle.getCoordinates().getX().equals(this.getCoordinates().getX()) &&
                vehicle.getCoordinates().getY().equals(this.getCoordinates().getY());
    }

    @Override
    public String toString() {
        return getName() + " " + getId() + " " + getType() + " " + getFuelType() +
                " " + getEnginePower() + " " + getCoordinates().getX() + " " +
                getCoordinates().getY() + " " + getCreationDate() +
                " \n owned by " + getOwnerLogin();
    }
}



