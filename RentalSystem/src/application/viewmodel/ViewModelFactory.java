package application.viewmodel;

import application.model.Model;

public class ViewModelFactory {
    private final DummyViewModel dummyViewModel;

    public ViewModelFactory(Model model) {
        this.dummyViewModel = new DummyViewModel(model);
    }

    public DummyViewModel getDummyViewModel() {
        return dummyViewModel;
    }
}
