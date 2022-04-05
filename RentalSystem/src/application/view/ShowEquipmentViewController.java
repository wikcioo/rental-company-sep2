package application.view;

import application.model.Equipment;
import application.viewmodel.AddEquipmentViewModel;
import application.viewmodel.ShowEquipmentViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Objects;

public class ShowEquipmentViewController implements PropertyChangeListener
{
  private ViewHandler viewHandler;
  private ShowEquipmentViewModel viewModel;
  private Region root;
  @FXML private Button addButton;
  @FXML private Button closeButton;
  @FXML private ListView<Equipment> equipmentList;


  public void init(ViewHandler viewHandler, ShowEquipmentViewModel showEquipmentViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = showEquipmentViewModel;
    this.root = root;

    viewModel.addListener(ShowEquipmentViewModel.EQUIPMENT_LIST_PROPERTY_NAME,this);

    equipmentList.setCellFactory(new Callback<ListView<Equipment>, ListCell<Equipment>>()
    {
      @Override public ListCell<Equipment> call(
          ListView<Equipment> equipmentListView)
      {
        return new ListCell<>() {
          @Override protected void updateItem(Equipment equipment, boolean b)
          {
            super.updateItem(equipment, b);
            if(Objects.equals(equipment,null)) {
              setText("");
              return;
            }
            setText(equipment.getName() + " " + equipment.getModel() + " ," +  equipment.getCategory() +"."+ ((equipment.getPrice() != 0) ? " Price of rent: " + equipment.getPrice() : ""));
          }
        };
      }
    });
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    switch (evt.getPropertyName()) {
    case ShowEquipmentViewModel.EQUIPMENT_LIST_PROPERTY_NAME -> updateEquipmentList((List<Equipment>) evt.getNewValue());
    }
  }

  private void updateEquipmentList(List<Equipment> updateList) {
    equipmentList.getItems().setAll(updateList);
  }

  public void reset() {

  }

  public Region getRoot() {
    return root;
  }

  public void addButtonPressed() {
    viewHandler.openView(ViewHandler.ADD_VIEW);
  }


  public void closeButtonPressed() {
  viewHandler.closeView();
  }

}
