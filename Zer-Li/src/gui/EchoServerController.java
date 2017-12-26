package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.labelText;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.ClientGui;
import logic.EchoServer;
import logic.MyClient;
import logic.Product;
import logic.ServerGui;
import ocsf.client.AbstractClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

public class EchoServerController 
{
	private EchoServer myServer;
	final private static int DEFAULT_PORT = 5555;
	
	
	@FXML
    private Label lblUserName;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button ConnectBtn;
    
    @FXML
    private Button DisconnectBtn;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblLoginToServer;

    @FXML
    private PasswordField txtPassword;

   
    @FXML
    private TextField txtPort;
    
    @FXML
    private Label lblPort;
    
    @FXML
    private TextField txtDatabaseName;
    
    @FXML
    private  Label lblConnected;
    
    @FXML
    private  Label lblDissconnected;

       
    
    @FXML
    private TextArea OutputMessage;
    

    @FXML
    private ImageView serverImage;
    
    
    @FXML
    void btnConnectPressed(ActionEvent event) throws IOException
    {
    		ConnectBtn.setDisable(true);
    		DisconnectBtn.setDisable(false);
    		txtUserName.setDisable(true);
    		txtPassword.setDisable(true);
    		txtPort.setDisable(true);
    		txtDatabaseName.setDisable(true);
    		OutputMessage.appendText("\nWe are trying to connect to DB...\n");
    		System.out.println("We are trying to connect to DB");
    		int port = 0; //Port to listen on

    	    try
    	    {
    	      port = Integer.parseInt(txtPort.getText() ); //Get port from textField
    	    }
    	    catch(Throwable t)
    	    {
    	      port = DEFAULT_PORT; //Set port to 5555
    	    }
    		
    	    this.myServer = new EchoServer(port, txtUserName.getText(), txtPassword.getText() , txtDatabaseName.getText() );
    	    //serverIsConnected=true;
    	    try 
    	    {
    	    	if(myServer.getStatusDBLogin() == true)
    	    	{
    	    	OutputMessage.appendText("Server connected successfully to the database!\n");
    	    	myServer.listen(); //Start listening for connections
    	    	OutputMessage.appendText("Server listening for connections on port " + myServer.getPort()+"...\n");
    	    	lblDissconnected.setVisible(false);
        	    lblConnected.setVisible(true);
        	    ServerGui.serverIsConnected=true;

    	    	}
    	    	
    	    	else
    	    	{
        	    	OutputMessage.appendText("You inserted wrong details to databse login- We are closing your connection!\n");
    	    		CrashDisconnect();
    	    	}
            } 
    	    catch (Exception ex) 
    	    {
    	      System.out.println("ERROR - Could not listen for clients!");
    	      OutputMessage.appendText("ERROR - Could not listen for clients! We are closing the connection!");
    	      CrashDisconnect();
       	    }
    	
    	    Alert alert = new Alert(AlertType.WARNING);
    	    alert.setTitle("Do not exit without disconnecting");
    	    
    	    alert.setContentText("Do not exit before disconnecting from the server!");

    	    alert.showAndWait();

    }
   
    
    @FXML
    void btnDisconnectPressed(ActionEvent event) throws SQLException, IOException 
    {
		OutputMessage.clear();
    	ConnectBtn.setDisable(false);
		DisconnectBtn.setDisable(true);
		txtUserName.setDisable(false);
		txtPassword.setDisable(false);
		txtPort.setDisable(false);
		txtDatabaseName.setDisable(false);
		lblConnected.setVisible(false);
	    lblDissconnected.setVisible(true);
	  	    
	    try
	    {
	    if( ServerGui.serverIsConnected==true)
	    {
	    	myServer.close();
	    	System.out.println("The server closed sucessfully!");
	    	OutputMessage.appendText("The server closed sucessfully!");
    	    ServerGui.serverIsConnected=false;
	    	this.myServer=null;
	    }
	    }
	    catch (IOException e)
	    {
	    	System.out.println("We could not close the server because of error");
		    OutputMessage.appendText("We could not close the server because of error\n");


	    }
	    
    }
    
	public void start(Stage primaryStage) throws Exception 
	{		
	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/TheguiOfTheServer.fxml"));
		
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/images/db.png").toExternalForm()); // change the style of the display
		primaryStage.setTitle("Connect To Server"); // name of the title of the window
		primaryStage.setScene(scene);
	   primaryStage.setResizable(false);
	   
		primaryStage.show();
		primaryStage.setOnCloseRequest( event -> {
			if(ServerGui.serverIsConnected==true)
			{
				//try
			//	{
					event.consume();

					//myServer.stopListening();
				//}
				
				//catch( Exception e)
			//	{
				//	System.out.println("error");
				//}

			}
			
			} );

		
		
	}

	
	private void CrashDisconnect() throws IOException
	{
		myServer.close();
		ServerGui.serverIsConnected=false;
		myServer=null;
	    ConnectBtn.setDisable(false);
		DisconnectBtn.setDisable(true);
		txtUserName.setDisable(false);
		txtPassword.setDisable(false);
		txtPort.setDisable(false);
		txtDatabaseName.setDisable(false);
		lblConnected.setVisible(false);
		lblDissconnected.setVisible(true);

	}
	
	
	
}


