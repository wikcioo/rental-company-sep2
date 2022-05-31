package application.model.users;

import application.model.reservations.Approved;
import application.model.reservations.Reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CurrentUserManager {
    private User currentlyLoggedInUser;

    public CurrentUserManager() {
        this.currentlyLoggedInUser = null;
    }

    public void setCurrentlyLoggedInUser(User currentlyLoggedInUser) {
        this.currentlyLoggedInUser = currentlyLoggedInUser;
    }

    public User getCurrentlyLoggedInUser() {
        return currentlyLoggedInUser;
    }

    public boolean currentUserIsManger(){
        return currentlyLoggedInUser.isManager();
    }

    public ArrayList<Reservation> getCurrentUserReservations(ArrayList<Reservation> reservations) {
        ArrayList<Reservation> userReservations = new ArrayList<>();
        if (currentlyLoggedInUser != null) {
            for (Reservation r : reservations) {
                if (currentlyLoggedInUser.getEmail().equals(r.getRentee().getEmail())) {
                    userReservations.add(r);
                }
            }
        }
        return userReservations;
    }

    public int getCurrentUserOverDueEquipmentAmount(ArrayList<Approved> approvedReservations) {
        int amount = 0;
        if (currentlyLoggedInUser != null) {
            for (Approved r : approvedReservations) {
                if (currentlyLoggedInUser.getEmail().equals(r.getRentee().getEmail())) {
                    if (r.getRentedFor().isBefore(LocalDateTime.now())) {
                        amount++;
                    }
                }
            }
        }
        return amount;
    }
}
