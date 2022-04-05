package application.view;

import application.viewmodel.DummyViewModel;
import javafx.scene.layout.Region;

public class DummyViewController {
    private ViewHandler viewHandler;
    private DummyViewModel dummyViewModel;
    private Region root;

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
