package Menu.GUI;

import java.util.HashMap;

import Menu.ExampleMenu;
import Model.ProgramState;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIMenu extends Application {

    private ExampleMenu menu;

    public GUIMenu() throws Exception{
        this.menu = new ExampleMenu();
    }

    interface cLambda {
        void run();
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        // primaryStage = new Stage();

        primaryStage.setTitle("Main Menu");
        var exampleMenu = this.menu.getMenu();
        var stringList = exampleMenu.getCommands().values().stream().map(x -> x.getDescription()).collect(java.util.stream.Collectors.toList());
        ObservableList<String> observableList = FXCollections.observableList(stringList);
        ListView<String> listView = new ListView<String>(observableList);

        

        var button = new Button("Open Program");
        button.setOnAction(
            e -> {
                var selected = listView.getSelectionModel().getSelectedItem();
                var command = exampleMenu.getCommands().values().stream().filter(x -> x.getDescription().equals(selected)).findFirst().get();
                var controller = command.getController();
                controller.restartExecution();
                // controller.restartExecution();
                Stage programStage = new Stage();
                programStage.setTitle("Program Window");
                
                
                var nrStatesField = new javafx.scene.control.TextField();
                nrStatesField.setEditable(false);
                
                Label nrStatesLabel = new Label("Number of states: ");

                Label heapLabel = new Label("Heap: ");

                var heapTable = new javafx.scene.control.TableView<javafx.util.Pair<Integer, String>>();
                TableColumn<javafx.util.Pair<Integer, String>, Integer> heapAddressColumn = new TableColumn<>("Address");
                heapAddressColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("Key"));
                TableColumn<javafx.util.Pair<Integer, String>, String> heapValueColumn = new TableColumn<>("Value");
                heapValueColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("Value"));
                heapTable.getColumns().addAll(heapAddressColumn, heapValueColumn);
                heapTable.setPlaceholder(new javafx.scene.control.Label("Heap is empty"));
                // heapTable.autosize();

                Label outLabel = new Label("Output: ");
                var out = new javafx.scene.control.ListView<String>();
                
                Label fileTableLabel = new Label("File Table: ");
                var fileTable = new javafx.scene.control.ListView<String>();

                Label programStateIDsLabel = new Label("Program State IDs: ");
                var programStateIDs = new javafx.scene.control.ListView<String>();
                // set to single selection
                programStateIDs.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
                
                Label symbolTableLabel = new Label("Symbol Table: ");
                var symbolTableView = new javafx.scene.control.TableView<javafx.util.Pair<String, String>>();
                TableColumn<javafx.util.Pair<String, String>, String> symbolTableVariableColumn = new TableColumn<>("Variable");
                symbolTableVariableColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("Key"));
                TableColumn<javafx.util.Pair<String, String>, String> symbolTableValueColumn = new TableColumn<>("Value");
                symbolTableValueColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("Value"));
                symbolTableView.getColumns().addAll(symbolTableVariableColumn, symbolTableValueColumn);
                
                Label exeStackLabel = new Label("Execution Stack: ");
                var exeStackView = new javafx.scene.control.ListView<String>();

                Label lockTableLabel = new Label("Lock Table: ");
                var lockTableView = new javafx.scene.control.TableView<javafx.util.Pair<Integer, Integer>>();
                TableColumn<javafx.util.Pair<Integer, Integer>, Integer> lockTableLocationColumn = new TableColumn<>("Location");
                lockTableLocationColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("Key"));
                TableColumn<javafx.util.Pair<Integer, Integer>, Integer> lockTableValueColumn = new TableColumn<>("Value");
                lockTableValueColumn.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("Value"));
                lockTableView.getColumns().addAll(lockTableLocationColumn, lockTableValueColumn);

                var programButton = new Button("Run one step");
                
                cLambda updateTexts = () -> {
                    var allStates = controller.getProgramStates();
                    ProgramState programState = allStates.get(0);

                    var nrStates = controller.getProgramStates().size();
                    nrStatesField.setText("Number of states: " + nrStates);
                    
                    var heap = new HashMap<>(programState.getHeap().getContent());
                    heapTable.getItems().clear();
                    for (var entry : heap.entrySet()) {
                        // System.out.println(entry.getKey() + " = " + entry.getValue());
                        var key = entry.getKey();
                        var value = entry.getValue().toString();
                        heapTable.getItems().add(new javafx.util.Pair<Integer, String>(key, value));
                    }

                    ObservableList<String> outList = FXCollections.observableList(programState.getOut().getContent().stream().map(x -> x.toString()).collect(java.util.stream.Collectors.toList()));
                    out.getItems().clear();
                    out.getItems().addAll(outList);

                    var fileTableList = programState.getFileTable().getContent().entrySet().stream().map(x -> x.getKey() + " = " + x.getValue()).collect(java.util.stream.Collectors.toList());
                    fileTable.getItems().clear();
                    fileTable.getItems().addAll(fileTableList);

                    var programStateIDsList = allStates.stream().map(x -> Integer.toString(x.getProgramId())).collect(java.util.stream.Collectors.toList());
                    programStateIDs.getItems().clear();
                    programStateIDs.getItems().addAll(programStateIDsList);
                    
                    var lockTableList = new HashMap<>(programState.getLockTable().getContent());
                    lockTableView.getItems().clear();
                    for (var entry : lockTableList.entrySet()) {
                        var key = entry.getKey();
                        var value = entry.getValue();
                        lockTableView.getItems().add(new javafx.util.Pair<Integer, Integer>(key, value));
                    }
                    // if no item is selected, select first one                    
                };

                cLambda updateSymbolTable = () -> {
                    var allStates = controller.getProgramStates();

                    if (programStateIDs.getSelectionModel().getSelectedItem() == null) {
                        programStateIDs.getSelectionModel().select(0);
                    }
                    
                    var selectedProgramState = programStateIDs.getSelectionModel().getSelectedItem();
                    var selectedProgramStateID = Integer.parseInt(selectedProgramState);
                    var selectedProgramStateObject = allStates.stream().filter(x -> x.getProgramId() == selectedProgramStateID).findFirst().get();
                    var symbolTable = selectedProgramStateObject.getSymbolTable().getContent();
                    symbolTableView.getItems().clear();
                    for (var entry : symbolTable.entrySet()) {
                        var key = entry.getKey();
                        var value = entry.getValue().toString();
                        symbolTableView.getItems().add(new javafx.util.Pair<String, String>(key, value));
                    }

                    var exeStack = selectedProgramStateObject.getExecutionStack().getReversed().stream().map(x -> x.toString()).collect(java.util.stream.Collectors.toList());
                    exeStackView.getItems().clear();
                    exeStackView.getItems().addAll(exeStack);

                };

                updateTexts.run();
                updateSymbolTable.run();
                
                programButton.setOnAction(e2 -> {
                    try {
                        controller.runOneStep();

                        updateTexts.run();
                        updateSymbolTable.run();
                    } catch (Exception e1) {
                        
                        programStage.close();
                        // System.out.println(e1.getMessage());
                    }
                });

                // update texts when changing selection in program ids list
                programStateIDs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (oldSelection != null && newSelection != null && !oldSelection.equals(newSelection)) {
                        updateSymbolTable.run();
                    }
                });
                


                HBox layout = new HBox(10);
                VBox leftLayout = new VBox(10);
                HBox.setHgrow(leftLayout, Priority.ALWAYS);
                VBox rightLayout = new VBox(10);
                HBox.setHgrow(rightLayout, Priority.ALWAYS);
                leftLayout.getChildren().add(nrStatesLabel);
                leftLayout.getChildren().add(nrStatesField);
                leftLayout.getChildren().add(programStateIDsLabel);
                leftLayout.getChildren().add(programStateIDs);
                leftLayout.getChildren().add(heapLabel);
                leftLayout.getChildren().add(heapTable);
                leftLayout.getChildren().add(outLabel);
                leftLayout.getChildren().add(out);
                leftLayout.autosize();

                rightLayout.getChildren().add(fileTableLabel);
                rightLayout.getChildren().add(fileTable);
                rightLayout.getChildren().add(symbolTableLabel);
                rightLayout.getChildren().add(symbolTableView);
                rightLayout.getChildren().add(exeStackLabel);
                rightLayout.getChildren().add(exeStackView);
                rightLayout.getChildren().add(lockTableLabel);
                rightLayout.getChildren().add(lockTableView);
                rightLayout.getChildren().add(programButton);
                rightLayout.autosize();

                layout.getChildren().add(leftLayout);
                layout.getChildren().add(rightLayout);

                layout.autosize();

                Scene scene = new Scene(layout, 800, 800);
                programStage.setScene(scene);
                programStage.show();

                programStage.onCloseRequestProperty().set(e2 -> {
                    // System.out.println("closing");
                    controller.restartExecution();
                    programStage.close();
                });
                }
            );

            VBox layout = new VBox(10);
            VBox.setVgrow(listView, Priority.ALWAYS);
            layout.autosize();
            layout.getChildren().addAll(listView, button);

            Scene scene = new Scene(layout, 800, 800);
            primaryStage.setScene(scene);
            primaryStage.show();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    public void show() {
        launch();
    }
    
}
