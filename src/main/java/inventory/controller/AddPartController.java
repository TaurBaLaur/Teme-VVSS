package inventory.controller;

import inventory.model.Part;
import inventory.service.InventoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddPartController implements Initializable, Controller {

    // Declare fields
    private Stage stage;
    private Parent scene;
    private boolean isOutsourced = true;
    private String errorMessage = new String();
    private int partId;

    private InventoryService service;
    
    @FXML
    private RadioButton inhouseRBtn;

    @FXML
    private RadioButton outsourcedRBtn;
    
    @FXML
    private Label addPartDynamicLbl;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField priceTxt;
    
    @FXML
    private TextField addPartDynamicTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Parent scene) {
        this.scene = scene;
    }

    public void setOutsourced(boolean outsourced) {
        isOutsourced = outsourced;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public void setInhouseRBtn(RadioButton inhouseRBtn) {
        this.inhouseRBtn = inhouseRBtn;
    }

    public void setOutsourcedRBtn(RadioButton outsourcedRBtn) {
        this.outsourcedRBtn = outsourcedRBtn;
    }

    public void setAddPartDynamicLbl(Label addPartDynamicLbl) {
        this.addPartDynamicLbl = addPartDynamicLbl;
    }

    public void setPartIdTxt(TextField partIdTxt) {
        this.partIdTxt = partIdTxt;
    }

    public void setNameTxt(TextField nameTxt) {
        this.nameTxt = nameTxt;
    }

    public void setInventoryTxt(TextField inventoryTxt) {
        this.inventoryTxt = inventoryTxt;
    }

    public void setPriceTxt(TextField priceTxt) {
        this.priceTxt = priceTxt;
    }

    public void setAddPartDynamicTxt(TextField addPartDynamicTxt) {
        this.addPartDynamicTxt = addPartDynamicTxt;
    }

    public void setMaxTxt(TextField maxTxt) {
        this.maxTxt = maxTxt;
    }

    public void setMinTxt(TextField minTxt) {
        this.minTxt = minTxt;
    }

    public Stage getStage() {
        return stage;
    }

    public Parent getScene() {
        return scene;
    }

    public boolean isOutsourced() {
        return isOutsourced;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getPartId() {
        return partId;
    }

    public InventoryService getService() {
        return service;
    }

    public RadioButton getInhouseRBtn() {
        return inhouseRBtn;
    }

    public RadioButton getOutsourcedRBtn() {
        return outsourcedRBtn;
    }

    public Label getAddPartDynamicLbl() {
        return addPartDynamicLbl;
    }

    public TextField getPartIdTxt() {
        return partIdTxt;
    }

    public TextField getNameTxt() {
        return nameTxt;
    }

    public TextField getInventoryTxt() {
        return inventoryTxt;
    }

    public TextField getPriceTxt() {
        return priceTxt;
    }

    public TextField getAddPartDynamicTxt() {
        return addPartDynamicTxt;
    }

    public TextField getMaxTxt() {
        return maxTxt;
    }

    public TextField getMinTxt() {
        return minTxt;
    }

    public AddPartController(){}

    @Override
    public void setService(InventoryService service){

        this.service=service;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        outsourcedRBtn.setSelected(true);
    }
    /**
     * Method to add to button handler to switch to scene passed as source
     * @param event
     * @param source
     * @throws IOException
     */
    @FXML
    private void displayScene(ActionEvent event, String source) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource(source));
        //scene = FXMLLoader.load(getClass().getResource(source));
        scene = loader.load();
        Controller ctrl=loader.getController();
        ctrl.setService(service);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Ask user for confirmation before canceling part addition
     * and switching scene to Main Screen
     * @param event
     * @throws IOException
     */
    @FXML
    void handleAddPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Cancelation");
        alert.setContentText("Are you sure you want to cancel adding part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.out.println("Ok selected. Part addition canceled.");
            displayScene(event, "/fxml/MainScreen.fxml");
        } else {
            System.out.println("Cancel clicked.");
        }
    }

    /**
     * If in-house radio button is selected set isOutsourced boolean
     * to false and modify dynamic label to Machine ID
     * @param event 
     */
    @FXML
    void handleInhouseRBtn(ActionEvent event) {
        isOutsourced = false;
        addPartDynamicLbl.setText("Machine ID");
    }

    /**
     * If outsourced radio button is selected set isOutsourced boolean
     * to true and modify dynamic label to Company Name
     * @param event 
     */
    @FXML
    void handleOutsourcedRBtn(ActionEvent event) {
        isOutsourced = true;
        addPartDynamicLbl.setText("Company Name");
    }

    /**
     * Validate given part parameters.  If valid, add part to inventory,
     * else give user an error message explaining why the part is invalid.
     * @param event
     * @throws IOException
     */
    @FXML
    void handleAddPartSave(ActionEvent event) throws IOException {
        String name = nameTxt.getText();
        String price = priceTxt.getText();
        String inStock = inventoryTxt.getText();
        String min = minTxt.getText();
        String max = maxTxt.getText();
        String partDynamicValue = addPartDynamicTxt.getText();
        errorMessage = "";
        
        try {
            errorMessage = Part.isValidPart(name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), errorMessage);
            if(errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part!");
                alert.setHeaderText("Error!");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            } else {
               if(isOutsourced == true) {
                    service.addOutsourcePart(name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), partDynamicValue);
                } else {
                    service.addInhousePart(name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(partDynamicValue));
                }
                //displayScene(event, "/fxml/MainScreen.fxml");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Form contains blank field.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part!");
            alert.setHeaderText("Error!");
            alert.setContentText("Form contains blank field.");
            alert.showAndWait();
        }
    }

}
