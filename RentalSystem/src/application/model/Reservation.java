package application.model;

import java.time.LocalDateTime;

public class Reservation
{
  private User approvedBy;
  private User rentee;
  private LocalDateTime reservationDate;
  private Equipment equipment;

  public Reservation(User rentee, Equipment equipment)
  {
    this.rentee = rentee;
    this.equipment = equipment;
    reservationDate = LocalDateTime.now();
    approvedBy = null;
  }

  public boolean isApproved() {
    return approvedBy != null;
  }

  public User getApprovedBy()
  {
    return approvedBy;
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
