package application.viewmodel;

import application.model.models.ManagerModel;
import application.model.models.RenteeModel;
import application.model.models.UserModel;
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

    public ViewModelFactory(UserModel userModel, RenteeModel renteeModel, ManagerModel managerModel) {
        this.addEquipmentViewModel = new AddEquipmentViewModel(managerModel);
        this.renteeEquipmentViewModel = new RenteeEquipmentViewModel(renteeModel);
        this.logInViewModel = new LogInViewModel(userModel);
        this.reservationViewModel = new ReservationViewModel(managerModel);
        this.managerEquipmentViewModel = new ManagerEquipmentViewModel(managerModel);
        this.addUserViewModel = new AddUserViewModel(managerModel);
        this.approvedReservationViewModel = new ApprovedReservationViewModel(managerModel);
        this.rejectedReservationViewModel = new RejectedReservationViewModel(managerModel);
        this.expiredReservationViewModel = new ExpiredReservationViewModel(managerModel);
        this.registeredUserViewModel = new RegisteredUserViewModel(managerModel);
        this.renteeReservationViewModel = new RenteeReservationViewModel(renteeModel);
        this.managerMainMenuViewModel = new ManagerMainMenuViewModel(managerModel);
        this.returnedReservationViewModel = new ReturnedReservationViewModel(managerModel);
        this.renteeMainMenuViewModel = new RenteeMainMenuViewModel(renteeModel);
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
