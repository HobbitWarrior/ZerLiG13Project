package gui;

import java.util.ArrayList;

import client.ChatClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.ClientGui;
import logic.Product;
import logic.labelText;

public class ProductFormController 
{
	//variables
	Product p;
	
	@FXML
	private Label lblPrID;
	@FXML
	private Label lblName;
	@FXML
	private Label lblPrType;
	
	@FXML
	private TextField txtPrID;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtPrType;
	
	@FXML
	private Button btnClose;
	
	@FXML
	private Button btnSave;
	
	@FXML
	private Label lblInformation;
	
	
	
	public static labelText lt;
	
	// set product details in the TextField
	public void loadProduct(Product p1)
	{
		this.p=p1;
		this.txtPrID.setText(p.getId());
		this.txtName.setText(p.getName());
		this.txtPrType.setText(p.getType());
		txtPrID.setDisable(true);
		txtPrType.setDisable(true);
		lt=new labelText();
		this.lblInformation.textProperty().bind(lt.getStatus());

	}
	
	//this method is executed when the user click on back button
	public void getBackBtn(ActionEvent event) throws Exception 
	{
		System.out.println("return to main menu"); //print message
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window	
		CatalogFrameController aFrame = new CatalogFrameController(); // create StudentFrame
		Stage arg0 = new Stage();
		aFrame.start(arg0);
	}
	
	//this method is executed when the user click on save button
	public void SaveBtn (ActionEvent event) throws Exception
	{
		//create list that the first element is the element before changing and all the others elements are after change
		ArrayList <String> List= new ArrayList<String> ();
		List.add(p.getName());//add first element (name before changing) to list
		//change the product details 
		p.setId(txtPrID.getText());
		p.setName(txtName.getText());
		p.setType(txtPrType.getText());

		//add elements to list (id,name,type of the product after the user changing)
		List.add(txtPrID.getText());
		List.add(txtName.getText());
		List.add(txtPrType.getText());
		
		ClientGui chat= new ClientGui();
		chat.accept(List); //call to accept function in ClientGui class
		lt.setStatus("Update Saved To DB.");
	}
	
	
	

}
