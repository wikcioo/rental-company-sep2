package application.view;

import application.model.Equipment;
import application.viewmodel.EquipmentViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Objects;

public class EquipmentViewController
{
  private ViewHandler viewHandler;
  private EquipmentViewModel viewModel;
  private Region root;
  @FXML private Button addButton;
  @FXML private Button closeButton;
  @FXML private TableView<Equipment> equipmentTable;
  @FXML private TableColumn<Equipment, String> modelColumn;
  @FXML private TableColumn<Equipment, String> categoryColumn;
  @FXML private TableColumn<Equipment, String> reserveColumn;


  public void init(ViewHandler viewHandler, EquipmentViewModel equipmentViewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = equipmentViewModel;
    this.root = root;

    modelColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Equipment, String>, ObservableValue<String>>() {
      @Override
      public ObservableValue<String> call(TableColumn.CellDataFeatures<Equipment, String> p) {
        if (p.getValue() != null) {
          return new SimpleStringProperty(p.getValue().getModel());
        } else {
          return new SimpleStringProperty("<no model>");
        }
      }
    });

    categoryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Equipment, String>, ObservableValue<String>>() {
      @Override
      public ObservableValue<String> call(TableColumn.CellDataFeatures<Equipment, String> p) {
        if (p.getValue() != null) {
          return new SimpleStringProperty(p.getValue().getCategory());
        } else {
          return new SimpleStringProperty("<no category>");
        }
      }
    });

    reserveColumn.setCellFactory(new Callback<TableColumn<Equipment, String>, TableCell<Equipment, String>>() {

      @Override
      public TableCell<Equipment, String> call(final TableColumn<Equipment, String> param) {
        final TableCell<Equipment, String> cell = new TableCell<Equipment, String>() {

          private final Button btn = new Button("Reserve");

          {
            btn.setOnAction((ActionEvent event) -> {
              Equipment data = getTableView().getItems().get(getIndex());
              System.out.println("selectedData: " + data);
            });
          }

          @Override
          public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setGraphic(null);
            } else {
              setGraphic(btn);
            }
          }
        };
        return cell;
      }
    });
    viewModel.bindEquipmentList(equipmentTable.itemsProperty());


  }

  public void reset() {

  }

  public Region getRoot() {
    return root;
  }

  public void addButtonPressed() {
    viewHandler.openView(ViewHandler.ADD_VIEW);
    System.out.println(equipmentTable.itemsProperty().toString());
  }


  public void closeButtonPressed() {
  viewHandler.closeView();
  }

}
