package application.util;

import java.beans.PropertyChangeListener;

public interface NamedPropertyChangeSubject
{
  public void addListener(String propertyName, PropertyChangeListener listener);
  public void removeListener(String propertyName, PropertyChangeListener listener);
}
