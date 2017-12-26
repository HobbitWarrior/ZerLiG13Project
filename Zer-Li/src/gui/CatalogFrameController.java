package gui;

import java.awt.Label;
import java.awt.TextField;
import java.net.URL;
import java.util.ResourceBundle;


import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.image.ImageView;

import javafx.event.ActionEvent;
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
import logic.MyClient;
import logic.Product;
import ocsf.client.AbstractClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;


public class CatalogFrameController implements Initializable
{
	
	//variables
	private ProductFormController pfc;
	private static int itemIndex=0; //tell witch item chosen by the user
	
	@FXML
	private Button btnExit = null;
		
	@FXML
	private Label lstOfProduct = null;

	@FXML
    private ComboBox productBox;
	
	@FXML
    private ImageView flowerImage;
	
	ObservableList<String> list;


	   
	
	public void ProductInfo(ActionEvent event) throws Exception 
	{
		
		
	    
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/gui/ProductForm.fxml").openStream());
		
		//load the correct chosen product details
		ProductFormController productFormController = loader.getController();		
		productFormController.loadProduct(ClientGui.products.get(itemIndex));
		
		Scene scene = new Scene(root);			
		scene.getStylesheets().add(getClass().getResource("/gui/ProductForm.css").toExternalForm());
		
		primaryStage.setScene(scene);		
		primaryStage.show();
	}
	
	public void start(Stage primaryStage) throws Exception 
	{	
		Parent root = FXMLLoader.load(getClass().getResource("/gui/CatalogFrame.fxml"));
				
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("/gui/CatalogFrame.css").toExternalForm()); // change the style of the display
		
		primaryStage.setTitle("Catalog Managment"); // name of the title of the window
		primaryStage.setScene(scene);
		
		primaryStage.show();		
	}
	
	//this method is executed when the user click on exit button
	public void getExitBtn1(ActionEvent event) throws Exception {
		System.out.println("exit Academic Tool"); // print message
		System.exit(0);			
	}

	//load the product
	public void loadProduct(Product p) 
	{
		this.pfc.loadProduct(p);
	}

	
	//set products in ComboBox
	private void setProductsComboBox() throws IOException 
	{
		//al= array list of the product names
		ArrayList<String> al = new ArrayList<String>();	
		
		
		
		
		
		for(int i=0; i< ClientGui.products.size() ; i++)// loop to add product names in the al list
		{
			String s="";
			s=""+ ClientGui.products.get(i).getName();
			al.add(s);
			s="";
		}
						
		list = FXCollections.observableArrayList(al); 
		productBox.setItems(list);	//set products in ComboBox
	}
	
	//this method is executed when user click on the button (productBox) in the catalog menu after he chose product from the ComboBox list. 
	public void getChosenProduct(ActionEvent event) throws IOException
	{
		int i=0;
		String ValueChosen=""+productBox.getValue(); // get the chosen product

		for(i=0;i < ClientGui.products.size(); i++) // loop on all the products
		{
			if(ClientGui.products.get(i).getName().equals(ValueChosen))//if found the chosen product
			{
				itemIndex=i;//get chosen product index
			}

		}
	}
	

	
	public void alertBox(String msg)
	{
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Success! ");
    	alert.setHeaderText("Product Updated on DB");
    	alert.setContentText("Changes Saved.");

    	alert.showAndWait();
	}
  
	
	// the function call setProductsComboBox() to initialize the items in the ComboBox .
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{	
		try {
			setProductsComboBox();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	
	
	
	
	
	
}
