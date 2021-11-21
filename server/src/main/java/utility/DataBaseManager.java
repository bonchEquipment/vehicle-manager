package utility;

import collection.FuelType;
import collection.Vehicle;
import collection.VehicleType;

import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;

import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DataBaseManager {

    private final String DB_URL;
    private final String LOGIN = System.getenv("HELIOS_LOGIN");
    private final String PASS = System.getenv("HELIOS_PASSWORD");
    private final ReadWriteLock collectionLock = new ReentrantReadWriteLock();
    private final ReadWriteLock usersLock = new ReentrantReadWriteLock();
    private Connection connection;


    public DataBaseManager(int host) throws ClassNotFoundException, SQLException {
        this.DB_URL = "jdbc:postgresql://localhost:" + host + "/studs";
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(DB_URL, LOGIN, PASS);
    }


    public HashMap<String, String> getUsersMap() throws SQLException {
        try {
            usersLock.readLock().lock();
            HashMap<String, String> usersInfo = new HashMap<>();
            String query = "SELECT * FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                usersInfo.put(resultSet.getString(1), resultSet.getString(2));
            }
            return usersInfo;
        } finally {
            usersLock.readLock().unlock();
        }
    }


    public LinkedList<Vehicle> getVehicleCollection() throws SQLException {
        try {
            collectionLock.readLock().lock();
            LinkedList<Vehicle> list = new LinkedList<>();
            String query = "SELECT * FROM collection";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                float coordinate_x = Float.parseFloat(resultSet.getString(3));
                float coordinate_y = Float.parseFloat(resultSet.getString(4));
                ZonedDateTime creationDate = ZonedDateTime.parse(resultSet.getString(5));
                float enginePower = Float.parseFloat(resultSet.getString(6));
                VehicleType vehicleType = null;
                try {
                    vehicleType = VehicleType.valueOf(resultSet.getString(7));
                } catch (IllegalArgumentException e) {
                }

                FuelType fuelType = FuelType.valueOf(resultSet.getString(8));
                String ownerLogin = resultSet.getString(9);
                Vehicle vehicle = new Vehicle(id, name, coordinate_x, coordinate_y, enginePower, vehicleType, fuelType);
                vehicle.setCreationDate(creationDate);
                vehicle.setOwnerLogin(ownerLogin);
                list.add(vehicle);
            }
            return list;
        } finally {
            collectionLock.readLock().unlock();
        }
    }

    public void writeCollectionToDataBase(LinkedList<Vehicle> collection) throws SQLException {
        try {
            collectionLock.writeLock().lock();

            for (Vehicle collectionElement : collection) {
                addVehicle(collectionElement);
            }
        } finally {
            collectionLock.writeLock().unlock();
        }
    }


    public boolean addUser(String login, String password) {
        try {
            usersLock.writeLock().lock();
            String query = "INSERT INTO users(login, password) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            usersLock.writeLock().unlock();
        }
    }

    public boolean clearUsersTable() {
        try {
            usersLock.writeLock().lock();
            String query = "DELETE FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            usersLock.writeLock().unlock();
        }
    }

    public boolean clearVehiclesTable() {
        try {
            collectionLock.writeLock().lock();
            String query = "DELETE FROM collection";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            collectionLock.writeLock().unlock();
        }

    }

    public void addVehicle(Vehicle vehicle) throws SQLException {
        try {
            collectionLock.writeLock().lock();
            String query = "INSERT INTO collection(vehicle_name, " +
                    "coordinate_x, coordinate_y, creation_date, " +
                    "engine_power, vehicle_type, fuel_type, owner_login) " +
                    " VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicle.getName());
            preparedStatement.setString(2, String.valueOf(vehicle.getCoordinates().getX()));
            preparedStatement.setString(3, String.valueOf(vehicle.getCoordinates().getY()));
            preparedStatement.setString(4, DateTimeFormatter.ISO_DATE_TIME.format(vehicle.getCreationDate()));
            preparedStatement.setString(5, String.valueOf(vehicle.getEnginePower()));
            preparedStatement.setString(6, vehicle.getType() == null ? "not stated" : vehicle.getType().toString());
            preparedStatement.setString(7, vehicle.getFuelType().toString());
            preparedStatement.setString(8, vehicle.getOwnerLogin());
            preparedStatement.executeUpdate();
        } finally {
            collectionLock.writeLock().unlock();
        }
    }

    public boolean removeVehicle(int id) throws SQLException {
        try {
            collectionLock.writeLock().lock();
            boolean b = isVehicleExist(id);
            String query = "DELETE FROM collection WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return b;
        } finally {
            collectionLock.writeLock().unlock();
        }
    }

    public boolean removeUser(String login) throws SQLException {
        try {
            usersLock.writeLock().lock();
            boolean isUserExist = isUserExist(login);
            String query = "DELETE FROM users WHERE login = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            return isUserExist;
        } finally {
            usersLock.writeLock().unlock();
        }
    }

    public boolean isUserExist(String login, String password) throws SQLException {
        try {
            usersLock.readLock().lock();
            return (!(getUsersMap().get(login) == null)) && (getUsersMap().get(login).equals(password));
        } finally {
            usersLock.readLock().unlock();
        }
    }


    public boolean isUserExist(String login) throws SQLException {
        try {
            usersLock.readLock().lock();
            return (!(getUsersMap().get(login) == null));
        } finally {
            usersLock.readLock().unlock();
        }
    }

    public boolean isVehicleExist(int id) throws SQLException {
        try {
            collectionLock.readLock().lock();
            Optional<Vehicle> optional = getVehicleCollection().stream().filter(x -> x.getId() == id).findFirst();
            return optional.isPresent();
        } finally {
            collectionLock.readLock().unlock();
        }
    }


    public void updateElement(int id, Vehicle vehicle) throws SQLException {
        try{
            collectionLock.writeLock().lock();
        if (isVehicleExist(id)) {
            String query = "UPDATE collection SET vehicle_name = ?, " +
                    "coordinate_x = ?, coordinate_y = ?, " +
                    "engine_power = ?, vehicle_type = ?, fuel_type = ? " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, vehicle.getName());
            preparedStatement.setString(2, String.valueOf(vehicle.getCoordinates().getX()));
            preparedStatement.setString(3, String.valueOf(vehicle.getCoordinates().getY()));
            preparedStatement.setString(4, String.valueOf(vehicle.getEnginePower()));
            preparedStatement.setString(5, vehicle.getType() == null ? "not stated" : vehicle.getType().toString());
            preparedStatement.setString(6, vehicle.getFuelType().toString());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        }
        } finally {
            collectionLock.writeLock().unlock();
        }
    }


    public boolean isVehicleOwnedBy(int id, String login) throws SQLException {
        try {
            collectionLock.readLock().lock();
            return getVehicleCollection().stream().
                    filter(x -> x.getId() == id).
                    anyMatch(x -> x.getOwnerLogin().equals(login));
        } finally {
            collectionLock.readLock().unlock();
        }
    }

    public int getMaxIdOfTheCollection() throws SQLException {
        try {
            collectionLock.readLock().lock();
            return getVehicleCollection().stream().
                    map(x -> x.getId()).
                    max((x, y) -> Integer.compare(x, y)).orElse(0);
        } finally {
            collectionLock.readLock().unlock();
        }
    }

    public boolean isDataBaseAvailable() {
        try {
            collectionLock.readLock().lock();
            getMaxIdOfTheCollection();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            collectionLock.readLock().unlock();
        }
    }


}
