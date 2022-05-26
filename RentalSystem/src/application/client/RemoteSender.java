package application.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSender extends Remote {
    public void replyReservationId(int id) throws RemoteException;
}
