package test.model;

import application.model.Equipment;
import application.model.Reservation;
import application.model.User;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest
{

  @org.junit.jupiter.api.BeforeAll void setUp()
  {
    Reservation reservation = new Reservation(new User("Selina","Ceban"),new Equipment("Vacuum cleaner","Arizona","Vacuum Clenaer",50));
  }

  @org.junit.jupiter.api.Test void isApproved()
  {

  }

  @org.junit.jupiter.api.Test void getApprovedBy()
  {
  }

  @org.junit.jupiter.api.Test void getRentee()
  {
  }

  @org.junit.jupiter.api.Test void getReservationDate()
  {
  }

  @org.junit.jupiter.api.Test void getEquipment()
  {
  }
}