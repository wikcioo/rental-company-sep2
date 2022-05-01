package test.model;

import application.model.Equipment;
import application.model.Reservation;
import application.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Objects;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {
  private final User rentee = new User("Selina","Ceban");
  private final Equipment equipment = new Equipment("Arizona","Vacuum Clenaer",50);
  private Reservation sut = new Reservation(rentee,equipment);

  @Test void canBeApproved()
  {
    sut.approve();
    assertTrue(sut.isApproved());

  }

  @Test void getRentee()
  {
    assertEquals(rentee,sut.getRentee());
  }

  @Test void getReservationDate()
  {
    assertTrue(sut.getReservationDate() instanceof LocalDateTime);
  }

  @Test void getEquipment()
  {
    assertEquals(sut.getEquipment(),equipment);
  }
}