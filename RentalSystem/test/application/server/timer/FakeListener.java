package application.server.timer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FakeListener implements PropertyChangeListener {
    private PropertyChangeEvent  pce = null;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        pce = evt;
    }

    public PropertyChangeEvent getLastEvt() {
        return pce;
    }
}

