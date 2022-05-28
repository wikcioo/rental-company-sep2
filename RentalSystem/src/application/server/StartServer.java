package application.server;

import application.util.ConsoleLogger;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {
    public static void main(String[] args) throws IOException, AlreadyBoundException {
        RentalSystemServer server = new RentalSystemServerImplementation();
        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        registry.bind("Server", server);
        ConsoleLogger.getInstance().success("Registry opened. Server ready to connect");
    }
}
