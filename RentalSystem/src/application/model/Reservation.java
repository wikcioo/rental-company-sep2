package application.model;

import java.time.LocalDateTime;

public class Reservation
{
  private boolean approved;
  private User rentee;
  private LocalDateTime reservationDate;
  private Equipment equipment;

  public Reservation(User rentee, Equipment equipment)
  {
    this.rentee = rentee;
    this.equipment = equipment;
    reservationDate = LocalDateTime.now();
    approved = false;
  }

  public boolean isApproved() {
    return approved;
  }

  public void approve() {
    approved = true;
  }

  public User getRentee()
  {
    return rentee;
  }

  public LocalDateTime getReservationDate()
  {
    return reservationDate;
  }

  public Equipment getEquipment()
  {
    return equipment;
  }
}
