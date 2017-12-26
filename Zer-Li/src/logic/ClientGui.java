package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import client.ChatClient;
import common.ChatIF;
import gui.CatalogFrameController;
import gui.ProductFormController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class ClientGui extends Application implements ChatIF  
{
	//vector of all the products in the table.// haim is the king
	public static Vector<Product> products=new Vector<Product>();
	
	// The default port to connect on.
	final public static int DEFAULT_PORT = 5555;
	public static String host;
	public CatalogFrameController aFrame;
	
	  
	// The instance of the client that created this ConsoleChat.
	static ChatClient client;
	
	// Constructor:
	  public ClientGui() 
	  {
	    try 
	    {
	      client= new ChatClient(host, DEFAULT_PORT, this);
	    } 
	    catch(IOException exception) 
	    {
	      System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	      System.exit(1);
	    }
	  }
	  
	  
	  // This method send Arraylist to the client's message handler.
	  public void accept(ArrayList <String> list) 
	  {
		  client.handleMessageFromClientUI(list);  
	  }
	  
	  
	  

	  
	  

	  // This method overrides the method in the ChatIF interface.
	  // It displays a message onto the screen.
	  public void display(String message) 
	  {
	    System.out.println("> " + message);
	  }
	  
	  
	
	//Class methods
	public static void main( String args[] ) throws Exception
	   { 
		
	    
	    host = "";
	    try
	    {
	    	host = args[0];
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	    	host = "localhost";
	    }
		
	    

	    MyClient ClientGetData=new MyClient(ClientGui.host,ClientGui.DEFAULT_PORT);
	    ClientGetData.sendRequestToGetProducts();
        launch(args);		
	  } // end main
	
	@Override
	public void start(Stage arg0) throws Exception 
	{
		
		aFrame= new CatalogFrameController();
		aFrame.start(arg0);
	}
}
