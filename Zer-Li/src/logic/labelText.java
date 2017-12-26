package logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class labelText {
	
	private SimpleStringProperty status;
	public labelText()
	{
		status= new SimpleStringProperty("Status: waiting for command");
	}

	
	public SimpleStringProperty getStatus()
	{
		return this.status;
	}
	
	public void setStatus(String s)
	{
		this.status.set(s);
		
	}
}
