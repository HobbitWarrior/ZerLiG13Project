package logic;

import java.io.*;
import java.net.URL;

import logic.Product;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.mysql.jdbc.Statement;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ocsf.server.*;

//Haim and Elias hate Alex too much

public class EchoServer extends AbstractServer implements Initializable
{
  //Class attributes *************************************************
	
  private String UserName;
  private String Password;
  private String DataBaseName;
  private Connection ServerDataBase;
  private boolean DB_ACCOUNT;
  
  //Class attributes *************************************************

  
  //Constructors ****************************************************
  
  public EchoServer(int port,  String UserName, String Password, String DataBaseName) 
  {
    super(port);
    ServerDataBase= connectToDB(UserName, Password, DataBaseName);
  }

  //Constructors ****************************************************

 
 
  public void handleMessageFromClient  (Object msg, ConnectionToClient client)
  {
	  
	  
	  if( msg instanceof String )
		 {
		  if ( ((String)msg).equals("GiveMeData") )
		  {
			  System.out.println("Get all info from DB");
			  

			  ArrayList<Product> itemFromDB=new ArrayList<Product>();
			  try 
			  {
				  itemFromDB=PutOutAllInformation(itemFromDB);
				  System.out.println(""+ itemFromDB);
				    this.sendToAllClients(itemFromDB);

				  return;
			  } 
			  catch (SQLException e) 
			  {
				  System.out.println("error-can't get data from db");
				  this.sendToAllClients("GetFail");
			  }
		  }
		 }
	  
	  	PreparedStatement ps = null;
	    System.out.println("Message received: " + msg + " from " + client);

	    try {
	    	ps=	parsingTheData( ServerDataBase,(ArrayList<String>) msg) ;
	    	saveUserToDB(ps);
		}
	    catch (Exception e)
	    {
			e.printStackTrace();
		}
	  
	    this.sendToAllClients("SUCSESS");
 }
	    
  
    //this method get all information from the DB and sent it to the comboBox of the clientGUI
  private ArrayList<Product> PutOutAllInformation(ArrayList<Product> itemFromDB) throws SQLException 
  {
	  
	  Statement st = (Statement) ServerDataBase.createStatement();

	  ResultSet rs = st.executeQuery( "select * from product ");
	  
	  while (rs.next())
	  {
	    Product itemReturnToClient= new Product(rs.getString(1),rs.getString(2),rs.getString(3));
	    itemFromDB.add(itemReturnToClient);
	    
	  }
	  rs.close();
	  st.close();
	 
				
	    return itemFromDB;
	  
  }



  protected void serverStarted()
  {
    System.out.println ("Server listening for connections on port " + getPort());    
  }
  
  
  
  protected void serverStopped()
  {
    System.out.println ("Server has stopped listening for connections.");
  }
  
  
  public synchronized PreparedStatement parsingTheData(Connection dbh,ArrayList<String> List) 
  {
	  PreparedStatement ps=null;
	    try
	    {
    		ps = dbh.prepareStatement(" UPDATE Product SET ProductID=? WHERE ProductName=?;");
            ps.setString(1,List.get(1));
            ps.setString(2,List.get(0));
            
            ps.executeUpdate();
         
	    	ps = dbh.prepareStatement(" UPDATE Product SET ProductName=? WHERE ProductName=?;");
	        ps.setString(1,List.get(2));
	        ps.setString(2,List.get(0));
	        
	        ps.executeUpdate();
	              
	    	ps = dbh.prepareStatement(" UPDATE Product SET ProductType=? WHERE ProductName=?;");
	        ps.setString(1,List.get(3));
	        ps.setString(2,List.get(0));
	        
	        ps.executeUpdate();
	    		
	    }
	    
	    
	    catch(SQLException ex)
	    {
	    	System.out.print("Sorry we had  problem, could not save changes to Database\n");
	    	this.sendToAllClients("UpdateFail");
	    }
	    this.sendToAllClients("UpdateSuccess");
	    return ps;
  }
  
  protected Connection connectToDB(String UserName, String Password, String DataBaseName)
  {
	  Connection ServerDataBase=null;  
	    try		
	    {
	    	String DataBaseAdress="jdbc:mysql://localhost:3306/" + DataBaseName;
	    	ServerDataBase = DriverManager.getConnection( DataBaseAdress, UserName , Password );
	    	System.out.print("Server connected to Database sucessfully!\n");	
	    	this.DB_ACCOUNT=true;
	    	
	    }
	    catch(SQLException ex)
	    {
	    	System.out.print("Sorry we had a problem, could not connect to DB server\n");	
	    	this.sendToAllClients("DBConnectFail");
	    	this.DB_ACCOUNT=false;
	    }
	    return ServerDataBase;

  }
  
  protected void saveUserToDB(PreparedStatement ps)
  {
	    try
	    {
	    	ps.executeUpdate();
	    }
	    catch(SQLException ex)
	    {
	    	System.out.print("Sorry we had a problem, Could not save in Database\n");
	    	this.sendToAllClients("UpdateFail");
	    }
	    this.sendToAllClients("UpdateSucces");
  }
  
  //Class methods ***************************************************
  
  
   



@Override
public void initialize(URL location, ResourceBundle resources) 
{
	// TODO Auto-generated method stub
	
}
  
  public boolean getStatusDBLogin()
  {
	  return this.DB_ACCOUNT;
  }
  
  
}