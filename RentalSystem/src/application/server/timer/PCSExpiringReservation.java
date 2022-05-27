package application.server.timer;

import application.util.NamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PCSExpiringReservation implements NamedPropertyChangeSubject {
    private static final PCSExpiringReservation instance = new PCSExpiringReservation();
    private final PropertyChangeSupport pcs;
    public static final String RESERVATION_EXPIRED = "reservation_expired";

    private PCSExpiringReservation() {
        pcs = new PropertyChangeSupport(this);
    }

    public static PCSExpiringReservation getInstance() {
        return instance;
    }

    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName,listener);
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName,listener);
    }

    public void alertAboutExpiration(int id) {
        pcs.firePropertyChange(RESERVATION_EXPIRED, null, id);
    }


}
