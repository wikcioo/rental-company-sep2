package application.viewmodel;

import application.model.Model;
import application.viewmodel.manager.*;
import application.viewmodel.rentee.RenteeEquipmentViewModel;
import application.viewmodel.rentee.RenteeMainMenuViewModel;
import application.viewmodel.rentee.RenteeReservationViewModel;

public class ViewModelFactory {
    private final AddEquipmentViewModel addEquipmentViewModel;
    private final RenteeEquipmentViewModel renteeEquipmentViewModel;
    private final LogInViewModel logInViewModel;
    private final ReservationViewModel reservationViewModel;
    private final ManagerEquipmentViewModel managerEquipmentViewModel;
    private final AddUserViewModel addUserViewModel;
    private final ApprovedReservationViewModel approvedReservationViewModel;
    private final RejectedReservationViewModel rejectedReservationViewModel;
    private final ExpiredReservationViewModel expiredReservationViewModel;
    private final RegisteredUserViewModel registeredUserViewModel;
    private final RenteeReservationViewModel renteeReservationViewModel;
    private final ManagerMainMenuViewModel managerMainMenuViewModel;
    private final ReturnedReservationViewModel returnedReservationViewModel;
    private final RenteeMainMenuViewModel renteeMainMenuViewModel;

    public ViewModelFactory(Model model) {
        this.addEquipmentViewModel = new AddEquipmentViewModel(model);
        this.renteeEquipmentViewModel = new RenteeEquipmentViewModel(model);
        this.logInViewModel = new LogInViewModel(model);
        this.reservationViewModel = new ReservationViewModel(model);
        this.managerEquipmentViewModel = new ManagerEquipmentViewModel(model);
        this.addUserViewModel = new AddUserViewModel(model);
        this.approvedReservationViewModel = new ApprovedReservationViewModel(model);
        this.rejectedReservationViewModel = new RejectedReservationViewModel(model);
        this.expiredReservationViewModel = new ExpiredReservationViewModel(model);
        this.registeredUserViewModel = new RegisteredUserViewModel(model);
        this.renteeReservationViewModel = new RenteeReservationViewModel(model);
        this.managerMainMenuViewModel = new ManagerMainMenuViewModel(model);
        this.returnedReservationViewModel = new ReturnedReservationViewModel(model);
        this.renteeMainMenuViewModel = new RenteeMainMenuViewModel(model);
    }

    public AddEquipmentViewModel getAddEquipmentViewModel() {
        return addEquipmentViewModel;
    }

    public RenteeEquipmentViewModel getEquipmentViewModel() {
        return renteeEquipmentViewModel;
    }

    public LogInViewModel getLogInViewModel() {
        return logInViewModel;
    }

    public ReservationViewModel getReservationViewModel() {
        return reservationViewModel;
    }

    public ApprovedReservationViewModel getApprovedReservationViewModel() {
        return approvedReservationViewModel;
    }

    public RejectedReservationViewModel getRejectedReservationViewModel() {
        return rejectedReservationViewModel;
    }

    public ExpiredReservationViewModel getExpiredReservationViewModel() {
        return expiredReservationViewModel;
    }

    public RenteeReservationViewModel getRenteeReservationViewModel() {
        return renteeReservationViewModel;
    }

    public ManagerEquipmentViewModel getManagerEquipmentViewModel() {
        return managerEquipmentViewModel;
    }

    public AddUserViewModel getAddUserViewModel() {
        return addUserViewModel;
    }

    public RegisteredUserViewModel getRegisteredUserViewModel() {
        return registeredUserViewModel;
    }

    public ManagerMainMenuViewModel getManagerMainMenuViewModel(){
        return managerMainMenuViewModel;
    }

    public ReturnedReservationViewModel getReturnedReservationViewModel() {
        return returnedReservationViewModel;
    }

    public RenteeMainMenuViewModel getRenteeMainMenuViewModel() {
        return renteeMainMenuViewModel;
    }
}
