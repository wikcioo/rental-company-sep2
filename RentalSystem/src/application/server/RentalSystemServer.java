package application.server;

import application.client.RentalSystemClient;
import application.shared.RMICommon;
import dk.via.remote.observer.RemotePropertyChangeListener;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface RentalSystemServer extends RMICommon {
    /**
     * Expires the reservation with given id in the database.
     *
     * @param id reservation's id
     * @throws RemoteException indicates connection failure
     */
    void expireReservation(int id) throws RemoteException;

    /**
     * Reserves the equipment with given id in the database and notifies a sender about new reservation's id.
     *
     * @param equipment_id equipment's id
     * @param rentee_id    rentee's id
     * @param rentedFor    expiration date of the reserved equipment
     * @param sender       sender, to which id of a new reservation will be replied to
     * @throws RemoteException indicates connection failure
     */
    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor, RentalSystemClient sender) throws RemoteException;

    /**
     * Adds a listener to start listening to events in the server
     *
     * @param listener the listener which will catch events
     * @throws RemoteException indicates connection failure
     */
    void addPropertyChangeListener(RemotePropertyChangeListener<ArrayList> listener) throws RemoteException;

    /**
     * Removes a listener to stop listening to events in the server
     *
     * @param listener the listener which will catch events
     * @throws RemoteException indicates connection failure
     */
    void removePropertyChangeListener(RemotePropertyChangeListener<ArrayList> listener) throws RemoteException;
}
