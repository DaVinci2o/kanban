package view;

import control.ClientControl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Project;

public class NewProjectDialogController {
    
    @FXML
    private TextField projectNameField;
    @FXML
    private TextArea projectDescriptionField;
    @FXML
    private Label creatorNameField;
    
    private Stage dialogStage;
    private boolean okClicked = false;
    
    private ClientControl clientControl;
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	this.clientControl = ClientControl.getInstance();
//		setting creator to logged in user uid;
	creatorNameField.setText(clientControl.getUser().getUid());
    }
    
    /**
     * Sets the stage of this dialog.
     */
    public void setDialogStage(Stage dialogStage) {
	this.dialogStage = dialogStage;
    }
    
    /**
     * Sets field in the dialog empty.
     */
    public void setProject() {
	
	projectNameField.setText("");
	projectDescriptionField.setText("");
	creatorNameField.setText("");
	
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     */
    public boolean on_Ok_Clicked() {
	return okClicked;
    }
    
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
	if (isInputValid()) {
	    Project prject = new Project(projectNameField.getText(), projectDescriptionField.getText(), creatorNameField.getText());
	    try {
		clientControl.projectCreate(prject);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    okClicked = true;
	    dialogStage.close();
	}
    }
    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleAbbrechen() {
	dialogStage.close();
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
	String errorMessage = "";
	
	if (projectNameField.getText() == null || projectNameField.getText().length() == 0) {
	    errorMessage += "No valid projectname!\n";
	}
	if (projectDescriptionField.getText() == null || projectDescriptionField.getText().length() == 0) {
	    errorMessage += "No valid last name!\n";
	}
	
	if (errorMessage.length() == 0) {
	    return true;
	} else {
	    // Show the error message.
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.initOwner(dialogStage);
	    alert.setTitle("Invalid Fields");
	    alert.setHeaderText("Please correct invalid fields");
	    alert.setContentText(errorMessage);
	    
	    alert.showAndWait();
	    
	    return false;
	}
    }
}
