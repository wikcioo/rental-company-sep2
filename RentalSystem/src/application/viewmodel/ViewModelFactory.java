package application.viewmodel;

import application.model.Model;

public class ViewModelFactory {
    private final DummyViewModel dummyViewModel;
    private final AddEquipmentViewModel addEquipmentViewModel;
    private final ShowEquipmentViewModel showEquipmentViewModel;

    public ViewModelFactory(Model model) {
        this.dummyViewModel = new DummyViewModel(model);
        this.addEquipmentViewModel = new AddEquipmentViewModel(model);
        this.showEquipmentViewModel = new ShowEquipmentViewModel(model);
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
}
