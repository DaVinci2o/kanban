package view;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import control.ClientControl;
import control.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Project;
import model.SimpleProject;
import model.User;

public class ProjectSelectionScreenController {
    
    @FXML
    private Label lb_greeting;
    @FXML
    private Button bt_accept;
    @FXML
    private TableView<User> projectOverviewTable;
    @FXML
    private TableColumn<User, String> projectNameColumn;
    
    private ClientControl clientControl;
    private MainApp mainApp;
    
    List<SimpleProject> listOfProjectNames = new ArrayList<SimpleProject>();
    
    Stage prevStage;
    
    public ProjectSelectionScreenController() {
	
    }
    
    @FXML
    private void initialize() {
    	this.clientControl = ClientControl.getInstance();
	// Initialize current Date and User and show them in a textfield
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	LocalDate localDate = LocalDate.now();
	String reportDate = dtf.format(localDate);
//		tb_date.setText(reportDate);
	lb_greeting.setText("Hallo " + mainApp.loginName);
	listOfProjectNames = clientControl.getSimpleProjects();
//	projectNameColumn.setCellValueFactory(cellData -> cellData.getValue().getProjects());
    }
    
    @FXML
    private void handleProjektAuswahl() {
	// Create a file chooser
	final JFileChooser fileChooser = new JFileChooser();
	fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
	int result = fileChooser.showOpenDialog(fileChooser);
	
	//
	
	if (result == JFileChooser.APPROVE_OPTION) {
	    
	    File file = fileChooser.getSelectedFile();
	    String filename = file.getAbsolutePath();
	    // String[] parts = string.split("/");
//			tb_status.setText(filename);
	    // This is where a real application would open the file.
	    
	} else {
//			tb_status.setText("Open command cancelled by user.");
	}
	// In response to a button click:
	// int returnVal = fc.showOpenDialog(aComponent);
    }
    
    // TB 20170907
    // button methods
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewProject() {
//		Project tmpP = new Project();
	mainApp.showProjectInsertDialog();
//		if (okClicked) {
//			mainApp.getProjektData().add(tmpP);
//		}
    }
    
    @FXML
    private void handleAccept() {
	
//	listOfProjectNames.add(new SimpleProject("ich", "123"));
//	listOfProjectNames.add(new SimpleProject("du", "234"));
//	listOfProjectNames.add(new SimpleProject("er", "345"));
//	System.out.println(listOfProjectNames.get(0).getName());
	
	MainApp mainApp = new MainApp();
	
	mainApp.showMainScreen();
	
	prevStage = (Stage) bt_accept.getScene().getWindow();
	prevStage.close();
    }
    
    public void setMainApp(MainApp mainApp) {
	this.mainApp = mainApp;
    }
    
}
