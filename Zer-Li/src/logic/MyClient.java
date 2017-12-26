package logic;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import com.sun.security.ntlm.Client;

import client.ChatClient;
import common.ChatIF;
import javafx.beans.Observable;
import ocsf.client.AbstractClient;

public class MyClient extends AbstractClient implements ChatIF
{

	static ChatClient client;

	public MyClient(String host, int port) throws IOException 
	{
		super(host, port);
		 client= new ChatClient(host, port, this);
	}

	
	@Override
	protected void handleMessageFromServer(Object msg) 
	{
		ArrayList<Product> getListFromServer = (ArrayList<Product>)msg;
		
		for (int i=0; i < getListFromServer.size() ; i++ )
		{
			ClientGui.products.add(getListFromServer.get(i));
		}
	}
	
	
	
	
	  public void sendRequestToGetProducts() throws IOException
	  {
		  
		  openConnection();
		  sendToServer("GiveMeData");

	  }


	@Override
	public void display(String message) 
	{
		// TODO Auto-generated method stub
		
	}
}
