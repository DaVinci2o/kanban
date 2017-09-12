package kanbanserver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.ITask;
import model.Project;
import model.SimpleProject;
import model.User;

public class ProjectXML
{
	private String projectXMLDirectory;

	public ProjectXML(String directory, String pID)
	{
		projectXMLDirectory = directory + pID + ".xml";
	}

	public Project readProjectXML(String directory, String loginName)
	{
		// create new Directory of the project xml
		String projectXMLDirectory = directory + loginName + ".xml";

		// create object of class project, returning at the end of the function
		Project projectRead = new Project(null, null, null, null, null, null, null);

		// check if xml file exists
		File file = new File(projectXMLDirectory);
		if (file.exists())
		{
			System.out.println("XML wurde gefunden");
		} else
		{
			System.out.println("XML wurde NICHT gefunden");
		}

		try
		{
			//
			File fXmlFile = new File(projectXMLDirectory);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(fXmlFile);
			
			

			
			
			// normalize the XML Structure; >>recommended<<
			doc.getDocumentElement().normalize();

		} catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (org.xml.sax.SAXException e)
		{
			e.printStackTrace();
		}

		return projectRead;
	}

	public boolean writeProjectXML(Project project)
	{
		boolean isWritten = false;
		// essentiall to cast a date into a string
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		// create new file
		File file = new File(projectXMLDirectory);

		// delete existing xml file
		if (file.exists())
		{
			file.delete();

			//Debugging output
			//System.out.println("Alte XML wurde gel�scht");
		}

		try
		{
			// create new object of type document to store the object of type Project
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			// create root Element in document
			Element rootElement = doc.createElement("data");
			doc.appendChild(rootElement);

			// add new element "project" to root Element "data"
			Element projectXML = doc.createElement("project");
			rootElement.appendChild(projectXML);

			// set attribute to project element
			projectXML.setAttribute("pID", project.getID());

			// add new element "pName" to project
			Element pName = doc.createElement("pName");
			pName.appendChild(doc.createTextNode(project.getName()));
			projectXML.appendChild(pName);

			// add new element "pDescription" to project
			Element pDescription = doc.createElement("pDescription");
			pDescription.appendChild(doc.createTextNode(project.getDescription()));
			projectXML.appendChild(pDescription);

			// add new element "mDate" to project
			Element mDate = doc.createElement("mDate");
			// cast Date to String
			String reportMDate = df.format(project.getModified());
			// 
			mDate.appendChild(doc.createTextNode(reportMDate));
			projectXML.appendChild(mDate);

			// add new element "cDate" to project
			Element cDate = doc.createElement("cDate");
			// cast Date to String
			String reportCDate = df.format(project.getCreated());
			cDate.appendChild(doc.createTextNode(reportCDate));
			projectXML.appendChild(cDate);
			
			// store all authorized projects in a list of type SimpleProject
			List<ITask> taskList = project.getTasks();
			
			// loop to store every task in xml file
			for (int i = 0; i < taskList.size(); i++)
			{
				// add new element "task" to project
				Element task = doc.createElement("task");
				projectXML.appendChild(task);
				// add attribute title to task
				task.setAttribute("tName", taskList.get(i).getTitle());
				// add new element "description" to task
				Element tDescription = doc.createElement("tDescription");
				tDescription.appendChild(doc.createTextNode(taskList.get(i).getDescription()));
				task.appendChild(tDescription);
				// add new element "categorie" to task
				Element tCategorie = doc.createElement("tCategorie");
				tCategorie.appendChild(doc.createTextNode(taskList.get(i).getCategorie()));
				task.appendChild(tCategorie);
				// add new element "status" to task
				Element tStatus = doc.createElement("tStatus");
				tStatus.appendChild(doc.createTextNode(taskList.get(i).getStatus()));
				task.appendChild(tStatus);
				// add new element "creatorID" to task
				Element tCreatorID = doc.createElement("tCreatorID");
				tCreatorID.appendChild(doc.createTextNode(taskList.get(i).getCreatorID()));
				task.appendChild(tCreatorID);
				// add new element "lastCall" to task
				Element tLastCall = doc.createElement("tLastCall");
				// cast Date to String
				String reportLastCall = df.format(taskList.get(i).getLastCall());
				tLastCall.appendChild(doc.createTextNode(reportLastCall));
				task.appendChild(tLastCall);
				// add new element "dateCreate" to task
				Element tDateCreate = doc.createElement("tDateCreate");
				// cast Date to String
				String reportDateCreate = df.format(taskList.get(i).getCreatorDate());
				tDateCreate.appendChild(doc.createTextNode(reportDateCreate));
				task.appendChild(tDateCreate);
							
				// loop to store the memberList in project xml
				
				
//				// add new element "comment" to task
//				Element tComment = doc.createElement("tComment");
//				task.appendChild(tComment);
//				
//				// store all comments in a list of type String
//				List<String> comments = taskList.get(i).getComment();
//				// loop to store the commentlines in comment
//				for (int d = 0; d < comments.size(); d++)
//				{
//					// add new element "commentline" to comment
//					Element tCommentLine = doc.createElement("tCommentLine");
//					
//					tComment.appendChild(tCommentLine);
//					
//				}
//				
//				// add new element "List<String> comment" to task
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(projectXMLDirectory));

			transformer.transform(source, result);

			System.out.println("Neue Project XMl wurde angelegt");
			isWritten = true;

		} catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
			System.out.println("Schreiben NICHT erfolgreich!");
		} catch (TransformerException tfe)
		{
			tfe.printStackTrace();
			System.out.println("Schreiben NICHT erfolgreich!");
		}
		return isWritten;
	}
}
