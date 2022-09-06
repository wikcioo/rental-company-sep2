package application.dao;

import application.server.RentalSystemServer;
import application.server.RentalSystemServerImplementation;
import application.util.ConsoleLogger;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartDAO {

    public static void main(String[] args) throws IOException, AlreadyBoundException {
        daoRMI server = new DAOServerImplementation();
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registry.bind("DAO", server);
        ConsoleLogger.getInstance().success("Registry opened. DAO ready to connect");
    }

}
