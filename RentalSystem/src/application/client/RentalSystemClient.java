package application.client;

import application.shared.RMICommon;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface RentalSystemClient extends RMICommon {
    /**
     * Reserves the equipment with given id in the database.
     *
     * @param equipment_id equipment's id
     * @param rentee_id    rentee's id
     * @param rentedFor    expiration date of the reserved equipment
     * @throws RemoteException indicates connection failure
     */
    void reserveEquipment(int equipment_id, String rentee_id, LocalDateTime rentedFor) throws RemoteException;

    /**
     * Adds a listener to start listening to events in the client
     *
     * @param listener the listener which will catch events
     * @throws RemoteException indicates connection failure
     */
    void addListener(PropertyChangeListener listener) throws RemoteException;

    /**
     * Removes a listener to stop listening to events in the client
     *
     * @param listener the listener which will catch events
     * @throws RemoteException indicates connection failure
     */
    void removeListener(PropertyChangeListener listener) throws RemoteException;

    /**
     * Receives a reservation id given in the argument and fires a property change
     *
     * @param id - a reservation id that's been created and returned
     * @throws RemoteException indicates connection failure
     */
    void replyReservationId(int id) throws RemoteException;
}
