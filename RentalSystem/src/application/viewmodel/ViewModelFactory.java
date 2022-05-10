package application.viewmodel;

import application.model.Model;

public class ViewModelFactory {
    private final DummyViewModel dummyViewModel;
    private final AddEquipmentViewModel addEquipmentViewModel;
    private final EquipmentViewModel equipmentViewModel;
    private final LogInViewModel logInViewModel;
    private final ReservationViewModel reservationViewModel;
    private final ManagerEquipmentViewModel managerEquipmentViewModel;

    public ViewModelFactory(Model model) {
        this.dummyViewModel = new DummyViewModel(model);
        this.addEquipmentViewModel = new AddEquipmentViewModel(model);
        this.equipmentViewModel = new EquipmentViewModel(model);
        this.logInViewModel = new LogInViewModel(model);
        this.reservationViewModel = new ReservationViewModel(model);
        managerEquipmentViewModel = new ManagerEquipmentViewModel(model);
    }

    public DummyViewModel getDummyViewModel() {
        return dummyViewModel;
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

    public ManagerEquipmentViewModel getManagerEquipmentViewModel() {
        return managerEquipmentViewModel;
    }
}
