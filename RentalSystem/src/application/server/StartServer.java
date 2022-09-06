package application.server;

import application.util.ConsoleLogger;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {
    public static void main(String[] args) throws IOException, AlreadyBoundException, NotBoundException {
        RentalSystemServer server = new RentalSystemServerImplementation("10.154.204.106",Registry.REGISTRY_PORT);
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT+1);
        registry.bind("Server", server);
        ConsoleLogger.getInstance().success("Registry opened. Server ready to connect");
    }
}
