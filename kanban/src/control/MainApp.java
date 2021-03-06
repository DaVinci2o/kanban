package control;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ITask;
import view.MainScreenController;
import view.NewProjectDialogController;
import view.NewTaskDialogController;
import view.ProjectSelectionScreenController;

public class MainApp extends Application {
    
    private Stage primaryStage;
    
    private BorderPane rootLayout;
    private AnchorPane loginLayout;
    private AnchorPane newTaskLayout;
    private static ClientControl clientControl;
    
    public static String loginName;
    
    /**
     * Constructor
     */
    public MainApp() {
    }
    
    @Override
    public void start(Stage primaryStage) {
	this.primaryStage = primaryStage;
	this.primaryStage.getIcons().add(new Image("KbLogo.png"));
	this.primaryStage.setTitle("HEMS Kanban");
	
	showLoginScreen();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
	try {
	    
	    Stage stage = new Stage();
	    stage.getIcons().add(new Image("KbLogo.png"));
	    stage.setTitle("HEMS Kanban");
	    
	    // Load root layout from fxml file.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("/view/rootLayout.fxml"));
	    rootLayout = (BorderPane) loader.load();
	    
	    // Show the scene containing the root layout.
	    Scene scene = new Scene(rootLayout);
	    stage.setScene(scene);
	    stage.show();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Shows the first Screen.
     */
    public void showLoginScreen() {
	try {
	    // Load login screen.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("/view/LoginScreen.fxml"));
	    
	    loginLayout = (AnchorPane) loader.load();
	    
	    // Show the scene containing the root layout.
	    Scene scene = new Scene(loginLayout);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Shows the third screen.
     * This is the task overview Screen, which is the main screen
     * inside the root layout.
     */
    public void showMainScreen() {
	try {
	    
	    initRootLayout();
	    // Load task overview.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("/view/MainScreen.fxml"));
	    BorderPane MainScreen = (BorderPane) loader.load();
	    
	    // Set mainscreen into the center of root layout.
	    rootLayout.setCenter(MainScreen);
	    
	    // Give the controller access to the main app.
	    MainScreenController controller = loader.getController();
	    controller.setMainApp(this);
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Shows the second screen.
     * This is the project selection screen inside the root layout.
     */
    public void showProjectScreen() {
	try {
	    initRootLayout();
	    // Load project overview.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("/view/ProjectSelectionScreen.fxml"));
	    BorderPane ProjectScreen = (BorderPane) loader.load();
	    
	    // Set project selection into the center of root layout.
	    rootLayout.setCenter(ProjectScreen);
	    
	    // Give the controller access to the main app.
	    ProjectSelectionScreenController controller = loader.getController();
	    controller.setMainApp(this);
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /**
     * Shows the creation screen for a new task.
     * 
     * @return false
     *         as a flag.
     */
    public boolean showNewTaskDialog() {
	
	try {
	    // Load the fxml file and create a new stage for the popup dialog.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("/view/NewTaskDialog.fxml"));
	    newTaskLayout = (AnchorPane) loader.load();
	    
	    // Create the dialog Stage.
	    Stage dialogStage = new Stage();
	    dialogStage.getIcons().add(new Image("KbLogo.png"));
	    dialogStage.setTitle("New Task");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    
	    Scene scene = new Scene(newTaskLayout);
	    dialogStage.setScene(scene);
	    // dialogStage.show();
	    
	    // Set the task into the controller.
	    NewTaskDialogController controller = loader.getController();
	    controller.setDialogStage(dialogStage);
	    // controller.setTask(selectedTask);
	    
	    // Show the dialog and wait until the user closes it
	    dialogStage.showAndWait();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	return false;
    }
    
    /**
     * Shows the screen to edit the selected Task.
     * 
     * @param selectedTask
     * @return false
     *         as a flag.
     */
    public boolean showEditTaskDialog(ITask selectedTask) {
	
	try {
	    // Load the fxml file and create a new stage for the popup dialog.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("/view/NewTaskDialog.fxml"));
	    newTaskLayout = (AnchorPane) loader.load();
	    
	    // Create the dialog Stage.
	    Stage dialogStage = new Stage();
	    dialogStage.getIcons().add(new Image("KbLogo.png"));
	    dialogStage.setTitle("Edit Task");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    
	    Scene scene = new Scene(newTaskLayout);
	    dialogStage.setScene(scene);
	    // dialogStage.show();
	    
	    // Set the task into the controller.
	    NewTaskDialogController controller = loader.getController();
	    controller.setDialogStage(dialogStage);
	    controller.setTask(selectedTask);
	    
	    // Show the dialog and wait until the user closes it
	    dialogStage.showAndWait();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return false;
    }
    
    /**
     * Shows the screen to create a new project.
     */
    public void showProjectInsertDialog() {
	try {
	    // Load the fxml file and create a new stage for the popup dialog.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(MainApp.class.getResource("/view/NewProjectDialog.fxml"));
	    newTaskLayout = (AnchorPane) loader.load();
	    
	    // Create the dialog Stage.
	    Stage dialogStage = new Stage();
	    dialogStage.getIcons().add(new Image("KbLogo.png"));
	    dialogStage.setTitle("New Project");
	    dialogStage.initModality(Modality.WINDOW_MODAL);
	    dialogStage.initOwner(primaryStage);
	    
	    Scene scene = new Scene(newTaskLayout);
	    dialogStage.setScene(scene);
	    // dialogStage.show();
	    
	    // Set the task into the controller.
	    NewProjectDialogController controller = loader.getController();
	    controller.setDialogStage(dialogStage);
	    // controller.setTask(selectedTask);
	    
	    // Show the dialog and wait until the user closes it
	    dialogStage.showAndWait();
	    
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public static void main(String[] args) {
	launch(args);
    }
    
    /**
     * Returns the main stage.
     * 
     * @return
     */
    public Stage getPrimaryStage() {
	return primaryStage;
    }
}
