package application.viewmodel;

import application.model.Model;

public class ViewModelFactory {
    private final DummyViewModel dummyViewModel;
    private final AddEquipmentViewModel addEquipmentViewModel;
    private final ShowEquipmentViewModel showEquipmentViewModel;
    private final LogInViewModel logInViewModel;

    public ViewModelFactory(Model model) {
        this.dummyViewModel = new DummyViewModel(model);
        this.addEquipmentViewModel = new AddEquipmentViewModel(model);
        this.showEquipmentViewModel = new ShowEquipmentViewModel(model);
        this.logInViewModel = new LogInViewModel(model);
    }

    public DummyViewModel getDummyViewModel() {
        return dummyViewModel;
    }

    public AddEquipmentViewModel getAddEquipmentViewModel() {
        return addEquipmentViewModel;
    }

    public ShowEquipmentViewModel getShowEquipmentViewModel() {
        return showEquipmentViewModel;
    }

    public LogInViewModel getLogInViewModel() {
        return logInViewModel;
    }
}
