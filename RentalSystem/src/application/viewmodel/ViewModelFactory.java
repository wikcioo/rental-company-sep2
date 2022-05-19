package application.viewmodel;

import application.model.Model;
import application.viewmodel.manager.*;
import application.viewmodel.rentee.EquipmentViewModel;

public class ViewModelFactory {
    private final AddEquipmentViewModel addEquipmentViewModel;
    private final EquipmentViewModel equipmentViewModel;
    private final LogInViewModel logInViewModel;
    private final ReservationViewModel reservationViewModel;
    private final ManagerEquipmentViewModel managerEquipmentViewModel;
    private final AddUserViewModel addUserViewModel;
    private final ApprovedReservationViewModel approvedReservationViewModel;

    public ViewModelFactory(Model model) {
        this.addEquipmentViewModel = new AddEquipmentViewModel(model);
        this.equipmentViewModel = new EquipmentViewModel(model);
        this.logInViewModel = new LogInViewModel(model);
        this.reservationViewModel = new ReservationViewModel(model);
        this.managerEquipmentViewModel = new ManagerEquipmentViewModel(model);
        this.addUserViewModel = new AddUserViewModel(model);
        this.approvedReservationViewModel = new ApprovedReservationViewModel(model);
    }

    public AddEquipmentViewModel getAddEquipmentViewModel() {
        return addEquipmentViewModel;
    }

    public EquipmentViewModel getEquipmentViewModel() {
        return equipmentViewModel;
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

    public ManagerEquipmentViewModel getManagerEquipmentViewModel() {
        return managerEquipmentViewModel;
    }

    public AddUserViewModel getAddUserViewModel() {
        return addUserViewModel;
    }
}
