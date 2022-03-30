package application.view;

import application.viewmodel.DummyViewModel;
import javafx.scene.layout.Region;

public class DummyViewController {
    ViewHandler viewHandler;
    DummyViewModel dummyViewModel;
    Region root;

    public void init(ViewHandler viewHandler, DummyViewModel dummyViewModel, Region root) {
        this.viewHandler = viewHandler;
        this.dummyViewModel = dummyViewModel;
        this.root = root;
    }

    public void reset() {

    }

    public Region getRoot() {
        return root;
    }
}
