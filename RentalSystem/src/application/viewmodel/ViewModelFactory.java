package application.viewmodel;

import application.model.Model;

public class ViewModelFactory {
    private final DummyViewModel dummyViewModel;
    private final AddEquipmentViewModel addEquipmentViewModel;

    public ViewModelFactory(Model model) {
        this.dummyViewModel = new DummyViewModel(model);
        this.addEquipmentViewModel = new AddEquipmentViewModel(model);
    }

    public DummyViewModel getDummyViewModel() {
        return dummyViewModel;
    }
    public AddEquipmentViewModel getAddEquipmentViewModel() {
        return addEquipmentViewModel;
    }
}
