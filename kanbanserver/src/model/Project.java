package model;
import java.io.Serializable;
import java.util.*;

import javafx.beans.property.*;
import javafx.collections.*;

public class Project implements IProject, IProjectFX, Serializable{
/**
 * project class containing id, name, description, date last modified, creation date
 * @version 1.1
 */

	private static final long serialVersionUID = -2398522995120490637L;
	/**project id*/
	private String id;	
	/**project name */
	private String name;
	/**description of the project */
	private String description;	
	/**date the project was last modified */
	private Date modified;	
	/**date the project was created */
	private Date created;					
	//TODO delete members, add courses?
	/**list of tasks */
	private List<ITask> tasks;	
	
	private ObservableList<ITask> tasksProperty = FXCollections.observableArrayList();
	/**list of project member ids */
	private List<String> members;	
	
	/**
	 * constructor for loading project
	 * @param name				project name
	 * @param description		project description
	 * @param modified			datelast modified date
	 * @param created			date creation date
	 * @param tasks 				list of tasks
	 * @param members		list of member ids
	 * @param id						project id
	 */
	public Project(String name,String description,Date modified, Date created, List<ITask> tasks ,List<String> members, String id ) {
		this.name = name;
		this.description = description;
		this.modified = modified;
		this.created = created;
		this.tasks = tasks;
		this.members = members;
		this.id = id;
	}
	
	/**
	 *  constructor for creating new project, 
	 *  creates id = -1 for the project, 
	 *  sets current dateTime to modified and created, 
	 *  creates members list and adds creator id to the list
	 * @param name 				project name
	 * @param description		project description
	 * @param creator			creator id
	 */
	public Project(String name,String description ,String creator ) {
			this(name, description,null,null,new ArrayList<ITask>(),null,"-1");
			Date current = new Date();
			modified = current;
			created = current;		
			members = new ArrayList<String>();
			members.add(creator);
	}
	
	@Override 	
	public String getID() {
		return id;
	}
  
	@Override
	public void setID(String value) {
		id = value;	
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String value) {
		name = value;
	}

	@Override
	public Date getModified() {
		return modified;
	}

	@Override
	public void setModified(Date value) {
		modified = value;
		
	}

	@Override
	public Date getCreated() {
		return created;
	}

	@Override
	public void setCreated(Date value) {
		created = value;		
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String value) {
		description = value;		
	}

	@Override
	public List<ITask> getTasks() {
		return new ArrayList<ITask>(tasks);
	}

	@Override
	public void setTasks(List<ITask> value) {
		tasks = value;
	}

	@Override
	public List<String> getMembers() {
		return new ArrayList<String>(members);
	}

	@Override
	public void setMembers(List<String> value) {
		members = value;		
	}
	
	@Override
	public void addTask(ITask value) {
		tasks.add(value);
		tasksProperty.add(value);
	}

	@Override
	public void addMember(int value) {
//		members.add(value);	
	}

	@Override
	public SimpleStringProperty getIDProperty() {
		return new SimpleStringProperty(id);
	}

	@Override
	public void setID(SimpleStringProperty value) {
		id = value.get();
	}

	@Override
	public SimpleStringProperty getNameProperty() {
		return new SimpleStringProperty(name);
	}

	@Override
	public void setName(SimpleStringProperty value) {
		name = value.get();
	}

	@Override
	public ObjectProperty<Date> getModifiedProperty() {
		return new SimpleObjectProperty<Date>(modified);
	}

	@Override
	public void setModified(ObjectProperty<Date> value) {
		modified = value.get();		
	}

	@Override
	public ObjectProperty<Date> getCreatedProperty() {
		return new SimpleObjectProperty<Date>(created);
	}

	@Override
	public void setCreated(ObjectProperty<Date> value) {
		created = value.get();
	}

	@Override
	public SimpleStringProperty getDescriptionProperty() {
		return new SimpleStringProperty(description);
	}

	@Override
	public void setDescription(SimpleStringProperty value) {
		description = value.get();	
	}

	@Override
	public ObservableList<ITask> getTasksProperty() {
		return tasksProperty;
	}

	@Override
	public void setTasks(ObservableList<ITask> value) {
		tasks = value;
	}

	@Override
	public ObservableList<String> getMembersProperty() {
		
		return (ObservableList<String>)getMembers();
	}

	@Override
	public void setMembers(ObservableList<String> value) {
		members = value;	
	}
}
