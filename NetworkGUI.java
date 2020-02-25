import javafx.application.Application;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.event.EventHandler;
import javafx.scene.text.TextAlignment;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import javafx.geometry.Orientation;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ScrollPane;
import java.io.*;

public class NetworkGUI extends Application
{
	private native void hostRun(int ckfreqt, char check, int index, String password, int ipAdd, int socketType, int port);
	
	private native String[] getNetworkNames();
	
	private native String getBSSIDs(int index);
	
	private native boolean[] passwordProtected();
	
	private native boolean isConnected();
	
	private native int[] getIP();
    
	private native int[] getGW();
    
	private native int[] getNetmask();
    
    private native int[] getMAC();

    private native String getHostname();
    
    private native String getMessage();
    
    private native void freeMessage();
    
    private native void freeHost();
    
    private native int getSocketID();
    
    private native void freeData();
    
    private native void enterAddress(int address);
    
    private native void enterValue(int value);
    
    private native void enterMAC(String mac);
    
    private native void enterData(String data);
    
    private native void enterDataRate(String meanDataRate);
    
    private native void enterMSDUSize(String msduSize);
    
    private native void enterFixedSizeFlag(String fixedSizeFlag);
    
    private native void enterUPTID(String upTid);
    
    private native void enterSubtype(String subtype);
    
    private native void enterAC(String aC);
    
    private native void enterCategory(String category);
    
    private native void enterRTS(String rts);
    
    private native void enterPHYRate(String phyRate);
    
    private native void enterPHYMode(String phyMode);
    
    private native void enterPreamble(String preamble);
    
    private String meanDataRate;
    
    private String msduSize;
    
    private String fixedSizeFlag;
    
    private String upTid;
    
    private String subtype;
    
    private String ac;
    
    private String category;
    
    private String rts;
    
    private String phyRate;
    
    private String phyMode;
    
    private String preamble;
    
	private int i;
	
	private String addressString1;
	
	private String valueString1;
	
	private String addressString2;
	
	private String valueString2;
	
	private String addressString3;
	
	private String addressString4;
	
	private String macString;
	
	private int macAdd;
	
	private int scans;
	
	private int address;
	
	private int value; 
	
	private int index;
	
	private int portNumber;
	
	private static String passw;
	
	private String[] networkNamesList;
	
	private boolean[] isPasswordProtected;
	
	private String password;
	
	private String ipString;
	
	private int ipAddress;
	
	private String data;
	
	private int dataSize;
	
	static { System.loadLibrary("NetworkGUI"); }
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
    public void start(Stage primaryStage) 
    {
    	scans = 0;
    	
    	final Label message = new Label("");
    	
    	final Label connectionStatus = new Label("");
    	
    	connectionStatus.setStyle("-fx-font-weight: bold");

    	//BackgroundImage logo = new BackgroundImage(new Image("Logo.png"), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    	
    	primaryStage.getIcons().add(new Image("Logo.png"));
    	
    	primaryStage.setTitle("Ubilite Inc");
        
        Label title = new Label("Select a Network");
        
        VBox mainBox = new VBox(5);
        
        //mainBox.setBackground(new Background(logo));
        
        mainBox.setAlignment(Pos.BASELINE_CENTER);
        
        mainBox.prefWidthProperty().bind(primaryStage.widthProperty());
        
        mainBox.prefHeightProperty().bind(primaryStage.heightProperty());
        
        mainBox.getChildren().add(title);
        
        HBox displayBox = new HBox(10);
        
        VBox messageBox = new VBox(5);
        
        mainBox.getChildren().add(displayBox);
        
        VBox buttonBox = new VBox();
        
        buttonBox.setSpacing(12);
        
        ScrollPane buttonPane = new ScrollPane();
        
        displayBox.prefWidthProperty().bind(mainBox.widthProperty());
        
        displayBox.prefHeightProperty().bind(mainBox.heightProperty().multiply(.9));
        
        displayBox.getChildren().add(buttonPane);
        
        buttonPane.prefWidthProperty().bind(displayBox.widthProperty().multiply(.2));
        
        buttonPane.prefHeightProperty().bind(displayBox.heightProperty().multiply(.5));
        
        ListView<String> networkNames = new ListView<String>();
        
        networkNames.setOrientation(Orientation.VERTICAL);
        
        messageBox.prefWidthProperty().bind(displayBox.widthProperty().multiply(.3));
        
        messageBox.prefHeightProperty().bind(displayBox.heightProperty());
        
        networkNames.prefHeightProperty().bind(messageBox.heightProperty());
        
        networkNames.prefWidthProperty().bind(messageBox.widthProperty());  
        
        //message.prefHeightProperty(displayBox.heightProperty().multiply(.2));
        
        messageBox.getChildren().add(networkNames);
        
        displayBox.getChildren().add(messageBox);
        
        Button scanButton = new Button("Scan");
        
        Button connectButton = new Button("Connect");
        
        Button disconnectButton = new Button("Disconnect");
        
        Button bindButton = new Button("Bind");
        
        Button createButton = new Button("Create");
        
        Button listenButton = new Button("Listen");
        
        Button acceptButton = new Button("Accept");
        
        Button writeRegister = new Button("Write Register");
        
        Button readRegister = new Button("Read Register");
        
        Button writePRI = new Button("Write PRI");
        
        Button readPRI = new Button("Read PRI");
        
        Button addTS = new Button("Add TS");
        
        Button delTS = new Button("Del TS");
        
        Button setQMF = new Button("Set QMF");
        
        Button getRTS = new Button("Get RTS");
        
        Button setRTS = new Button("Set RTS");
        
        Button getPHYRate = new Button("Get PHY Rate");
        
        Button setPHYRate = new Button("Set PHY Rate");
        
        Button getPHYMode = new Button("Get PHY Mode");
        
        Button setPHYMode = new Button("Set PHY Mode");
        
        Button get11B = new Button("Get 11B");
        
        Button set11B = new Button("Set 11B");
        
        connectButton.setDisable(true);
        
        buttonBox.getChildren().add(scanButton);
        
        buttonBox.getChildren().add(connectButton);
        
        buttonBox.getChildren().add(disconnectButton);
        
        buttonBox.getChildren().addAll(createButton, bindButton, listenButton, acceptButton, writeRegister, readRegister, writePRI, readPRI);   
        
        buttonBox.getChildren().addAll(addTS, delTS, setQMF, getRTS, setRTS, getPHYRate, setPHYRate, getPHYMode, setPHYMode, get11B, set11B);
        
        buttonBox.setAlignment(Pos.BASELINE_LEFT);
        
        buttonPane.setContent(buttonBox);
        
        final ToggleGroup clientServerSelection = new ToggleGroup();
        
        final ToggleGroup tcpUdpSelection = new ToggleGroup();
        
        RadioButton client = new RadioButton("Client");
        
        //client.setDisable(true);
        
        client.setToggleGroup(clientServerSelection);
        
        client.setSelected(false);
        
        RadioButton server = new RadioButton("Server");
        
        RadioButton tcpButton = new RadioButton("TCP");
        
        tcpButton.setToggleGroup(tcpUdpSelection);
        
    	RadioButton udpButton = new RadioButton("UDP");
    	
    	VBox radioButtonBox = new VBox(4);
    	
    	radioButtonBox.getChildren().addAll(client, server, tcpButton, udpButton);
    	
    	udpButton.setToggleGroup(tcpUdpSelection);
    	
    	ScrollPane infoPane = new ScrollPane();
    	
    	HBox topRight = new HBox(4);
    	
    	VBox fieldBox = new VBox(4);
    	
    	final Label addressLabel = new Label("Address");
    	
    	final Label valueLabel = new Label("Value");
    	
    	final Label macLabel = new Label("MAC");
    	
    	TextField addressField = new TextField();
    	
    	TextField valueField = new TextField();
    	
    	TextField macField = new TextField();
    	
    	HBox addressBox = new HBox(5);
    	
    	HBox valueBox = new HBox(5);
    	
    	HBox macBox = new HBox(5);
    	
    	addressBox.getChildren().addAll(addressLabel, addressField);
    	
    	valueBox.getChildren().addAll(valueLabel, valueField);
    	
    	macBox.getChildren().addAll(macLabel, macField);
    	
    	addressBox.setAlignment(Pos.CENTER_RIGHT);
    	
    	valueBox.setAlignment(Pos.CENTER_RIGHT);
    	
    	macBox.setAlignment(Pos.CENTER_RIGHT);
    	
    	server.setToggleGroup(clientServerSelection);
        
        HBox ipBox = new HBox();
        
        ipBox.setSpacing(10);
        
        ipBox.setAlignment(Pos.CENTER_RIGHT);
        
        HBox portBox = new HBox();
        
        portBox.setSpacing(10);
        
        portBox.setAlignment(Pos.CENTER_RIGHT);
        
        HBox dataBox = new HBox();
        
        dataBox.setSpacing(10);
        
        dataBox.setAlignment(Pos.CENTER_RIGHT);
        
        Label iPLabel = new Label("IP");
        
        Label portLabel = new Label("Port");
        
        Label dataLabel = new Label("Data");
        
        final TextField iPField = new TextField();
        
        iPField.setDisable(true);
        
        final TextField portField = new TextField();
        
        portField.setDisable(true);
        
        final TextField dataField = new TextField();
        
        dataField.setDisable(true);
        
        ipBox.getChildren().addAll(iPLabel, iPField);
        
        portBox.getChildren().addAll(portLabel, portField);
        
        dataBox.getChildren().addAll(dataLabel, dataField);
        
        fieldBox.getChildren().addAll(ipBox, portBox, dataBox);
        
        VBox radioBox = new VBox(8);

        topRight.getChildren().addAll(radioButtonBox, fieldBox);
        
        VBox infoBox = new VBox(8);
        
        infoPane.setContent(infoBox);
        
        radioBox.prefWidthProperty().bind(primaryStage.widthProperty().multiply(.43));
        
        infoPane.prefHeightProperty().bind(primaryStage.heightProperty().multiply(.5));
    	radioBox.getChildren().addAll(topRight, infoPane, connectionStatus, message);
    	
    	infoBox.getChildren().addAll(addressBox, valueBox, macBox);
    	
    	displayBox.getChildren().add(radioBox);
    	//addTS, delTS, setQMF
    	if(!isConnected())
        {
    		iPField.setDisable(true);
    		portField.setDisable(true);
    		dataField.setDisable(true);
         	disconnectButton.setDisable(true);
         	client.setDisable(true);
         	server.setDisable(true);
         	createButton.setDisable(true);
         	bindButton.setDisable(true);
         	listenButton.setDisable(true);
         	acceptButton.setDisable(true);
         	udpButton.setDisable(true);
         	tcpButton.setDisable(true);
         	addTS.setDisable(true);
         	delTS.setDisable(true);
         	setQMF.setDisable(true);
        }
         
        scanButton.setOnAction(a -> 
        {
        	message.setTextFill(Color.BLACK);
        	message.setText("Scanning...");
        	if(scans != 0)
        	{
        		freeData();
        	}
        	scans++;
        	
        	connectButton.setDisable(false);
        	
        	hostRun(4000000, '1', 0, "", 0, 0, 0);
        	message.setText("Scan complete");
        	
        	System.out.println("Java Test 1");
        	networkNamesList = getNetworkNames();
        	System.out.println("Java Test 2");
        	isPasswordProtected = passwordProtected();
        	System.out.println("Java Test 3");
        	networkNames.getItems().clear();
        	
        	networkNames.getItems().addAll(networkNamesList);
        	System.out.println("Java Test 4");
            networkNames.setOnMouseClicked(c -> 
            {
            	connectButton.setDisable(false);
            	
            	index = networkNames.getSelectionModel().getSelectedIndex();
            	if(isPasswordProtected[index])
            	{
            		message.setText("BSSID: " + getBSSIDs(networkNames.getSelectionModel().getSelectedIndex()) + "\nPassword Protected");
            	}
            	else
            	{
            		message.setText("BSSID: " + getBSSIDs(networkNames.getSelectionModel().getSelectedIndex()));
            	}

           	
            });
            
        	
        } );
        
        connectButton.setOnAction(b -> 
    	{
    		index = networkNames.getSelectionModel().getSelectedIndex();
    		
    		if(isPasswordProtected[index])
    		{
    			password = enterPassword();
    			
    			
    			hostRun(4000000, '0', index, password, 0, 0, 0);
    			
    			if (!isConnected())
    			{
    				message.setText("Your password is incorrect!\n" + getMessage());
    				freeMessage();
    				message.setTextFill(Color.rgb(210, 39, 30));
    			} 
    			else 
    			{
    				disconnectButton.setDisable(false);
    				client.setDisable(false);
    				server.setDisable(false);
    				addTS.setDisable(false);
    	         	delTS.setDisable(false);
    	         	setQMF.setDisable(false);
    				connectionStatus.setText("Connected to " + getNetworkNames()[index]);
    				message.setText("Your password has been confirmed\n" + getMessage());
    				freeMessage();
    				message.setTextFill(Color.rgb(21, 117, 84));
    			}
    		}
    		else
    		{
    			//System.out.println("Index: " + index);
    			hostRun(4000000, '0', index, "", 0, 0, 0);
    			if(isConnected())
    			{
    				connectionStatus.setText("Connected to " + getNetworkNames()[index]);
    				message.setText("Connection successful,\n" + getMessage());
    				freeMessage();
    				disconnectButton.setDisable(false);
    				addTS.setDisable(false);
    	         	delTS.setDisable(false);
    	         	setQMF.setDisable(false);
    				client.setDisable(false);
    				server.setDisable(false);
    	        	
    			}
    		}
            
    		
    	} );
        
        udpButton.setOnAction(c -> 
        {
        	portField.setDisable(false);
        	createButton.setDisable(false);
        	bindButton.setDisable(false);
        	listenButton.setDisable(true);
        	if(client.isSelected())
        	{
        		iPField.setDisable(false);
        		iPField.setText("192.168.1.");
        		dataField.setDisable(false);
        		acceptButton.setDisable(false);
        	}
        	else
        	{
        		iPField.setDisable(true);
        		iPField.setText("");
        		dataField.setDisable(true);
        		acceptButton.setDisable(true);
        	}
        	
        });
        tcpButton.setOnAction(d -> 
        {
        	portField.setDisable(false);
        	createButton.setDisable(false);
        	bindButton.setDisable(false);
        	listenButton.setDisable(false);
        	acceptButton.setDisable(false);
        	if(client.isSelected())
        	{
        		iPField.setDisable(false);
        		iPField.setText("192.168.1.");
        		dataField.setDisable(false);
        		
        	}
        	else
        	{
        		iPField.setDisable(true);
        		iPField.setText("");
        		dataField.setDisable(true);
        	}
        });
        client.setOnAction(e -> 
        {
        	tcpButton.setDisable(false);
        	udpButton.setDisable(false);
        	
        	if(tcpButton.isSelected())
        	{
        		listenButton.setDisable(false);
        		acceptButton.setDisable(false);
        		iPField.setDisable(false);
        		iPField.setText("");
        		dataField.setDisable(false);
        	}
        	else if(udpButton.isSelected())
        	{
        		listenButton.setDisable(true);
        		acceptButton.setDisable(false);
        		iPField.setDisable(false);
        		iPField.setText("192.168.1.");
        		dataField.setDisable(false);
        	}
        	listenButton.setText("Connect To");
        	acceptButton.setText("Send To");
        });
        server.setOnAction(f ->
        {
        	
        	tcpButton.setDisable(false);
        	udpButton.setDisable(false);
        	listenButton.setText("Listen");
        	acceptButton.setText("Accept");
        	if(tcpButton.isSelected())
        	{
        		listenButton.setDisable(false);
        		acceptButton.setDisable(false);
        		iPField.setDisable(true);
        		iPField.setText("");
        		dataField.setDisable(true);
        	}
        	else if(udpButton.isSelected())
        	{
        		listenButton.setDisable(true);
        		acceptButton.setDisable(true);
        		iPField.setDisable(true);
        		iPField.setText("192.168.1.");
        		dataField.setDisable(true);
        	}
        });
        createButton.setOnAction(g -> 
        {
        	message.setTextFill(Color.BLACK);
        	if(server.isSelected())
        	{
        		if(portField.getText() == null || portField.getText().trim().isEmpty())
        		{
        			message.setText("Error, must enter a port number");
        		}
        		else
        		{
        			portNumber = Integer.parseInt(portField.getText());
                	if(portNumber != 0)
                	{
                		if(udpButton.isSelected())
                    	{
                    		hostRun(4000000, 'l', 0, "", 0, 0, portNumber);
                    		
                    	}
                    	else
                    	{
                    		hostRun(4000000, 'l', 0, "", 0, 1, portNumber);
                    		
                    	}
                		
                	}
                	message.setText(getMessage() + getSocketID());
                	freeMessage();
        		}
        			
        		
        	}
        	else
        	{
        		if(portField.getText() == null || portField.getText().trim().isEmpty())
        		{
        			message.setText("Error: must enter a portNumber");
        		}
        		else
        		{
        			
                	portNumber = Integer.parseInt(portField.getText());
                	if(udpButton.isSelected())
                	{
                		hostRun(4000000, 'l', 0, "", 0, 0, portNumber);
                	}
                	else
                	{
                		hostRun(4000000, 'l', 0, "", 0, 1, portNumber);
                	}
                	message.setText(getMessage() + getSocketID());
                	freeMessage();
        		}
        		
        	}
        	
        	
        	
        	
        });
        bindButton.setOnAction(h -> 
        {
        	message.setTextFill(Color.BLACK);
        	if(server.isSelected())
        	{
        		hostRun(4000000, 'o', 0, "", 0, 0, portNumber);
        		message.setText(getMessage() + getSocketID());
            	freeMessage();
        		if(udpButton.isSelected())
        		{
        			hostRun(4000000, 'g', 0, "", 0, 0, portNumber);
            		message.setText(printData(getIP(), getGW(), getNetmask(), getMAC(), getHostname()) + "\n" + getMessage());
            		freeMessage();
            		freeHost();
        		}
        	}
        	else
        	{
        		if(iPField.getText() == null || iPField.getText().trim().isEmpty())
        		{
        			message.setText("Error, must enter the final digits of an IP Address");
        		}
        		else
        		{
        	        ipString  = iPField.getText();
        	       	ipAddress = convertIP(ipString);
              		hostRun(4000000, 'o', 0, "", ipAddress, 0, portNumber);
              		message.setText(getMessage() + getSocketID());
                	freeMessage();
        		}
        		
        	}
        	
        	
        });
        listenButton.setOnAction(i -> 
        {
        	message.setTextFill(Color.BLACK);
        	if(server.isSelected())
        	{
        		hostRun(4000000, 'p', 0, "", 0, 0, 0);
        	}
        	else
        	{
        		hostRun(4000000, 'q', 0, "", ipAddress, 0, portNumber);
        	}
        	
        	message.setText(getMessage());
        	freeMessage();
        });
        acceptButton.setOnAction(j -> 
        {
        	message.setTextFill(Color.BLACK);
        	if(server.isSelected())
        	{
        		hostRun(4000000, 'n', 0, "", 0, 0, portNumber);
            	hostRun(4000000, 'g', 0, "", 0, 0, portNumber);
        		message.setText(printData(getIP(), getGW(), getNetmask(), getMAC(), getHostname()) + "\n" + getMessage());
        		freeMessage();
        		freeHost();
        	}
        	else
        	{
        		if(dataField.getText().trim().length() != 2 || !validHex(dataField.getText()))
        		{
        			message.setText("Error: Must enter a valid 2 digit data value.");
        		}
        		else
        		{
        			enterData(dataField.getText());
                	
                	//System.out.println("Data " + convertStringToHex(data));
                	if(udpButton.isSelected())
                	{
                		hostRun(4000000, 't', 0, "", ipAddress, 0, portNumber);
                	}
                	else
                	{
                		hostRun(4000000, '5', 0, "", ipAddress, 0, 0);
                	}
        		}
        		
        	}
        	
        	
        	
        });
        
        writeRegister.setOnAction(k -> 
        {
        	message.setTextFill(Color.BLACK);
        	addressString1 = addressField.getText();
        	
        	valueString1 = valueField.getText();
        	
        	address = hex2decimal(addressString1);
        	
        	value = hex2decimal(valueString1);
        	
        	if(address % 4 != 0)
        	{
        		message.setText("Error, Address is invalid");
        	}
        	else
        	{
        		enterAddress(address);
        		enterValue(value);
        		hostRun(4000000, 'H', 0, "", 0, 0, 0);
        	}
        	
        });
        
        readRegister.setOnAction(l -> 
        {
        	message.setTextFill(Color.BLACK);
        	
        	addressString2 = addressField.getText();
        	
        	address = hex2decimal(addressString2);
        	
        	if(address % 4 != 0)
        	{
        		message.setText("Error, Address is invalid");
        	}
        	else
        	{

        		enterAddress(address);
        		hostRun(4000000, 'I', 0, "", 0, 0, 0);
        	}
        });
        
        writePRI.setOnAction(m -> 
        {
        	message.setTextFill(Color.BLACK);
        	
        	addressString3 = addressField.getText();
        	
        	valueString2 = valueField.getText();
        	
        	address = hex2decimal(addressString3);
        	
        	value = hex2decimal(valueString2);
        	
        	enterAddress(address);
    		enterValue(value);
    		hostRun(4000000, 'J', 0, "", 0, 0, 0);
        });
        
        readPRI.setOnAction(n -> 
        {
        	message.setTextFill(Color.BLACK);
        	
        	addressString4 = addressField.getText();
        	
        	address = hex2decimal(addressString4);
        	
        	enterAddress(address);
    		hostRun(4000000, 'K', 0, "", 0, 0, 0);
        	
        });
        
        macField.setOnAction(o -> 
        {
        	if(macField.getText().trim().length() != 12 || !validHex(macField.getText()))
        	{
        		message.setText("Error, must enter a valid 12 digit MAC address");
        	}
        	else
        	{
        		macString = macField.getText();
            	enterMAC(macString);
            	hostRun(4000000, 'N', 0, "", 0, 0, 0);
        	}
        	
        });
        
        //addTS, delTS, getPHYRate, setPHYRate, getPHYMode, setPHYMode, get11B, set11B, writeLED
        HBox dataRateBox = new HBox(10);
        
        final Label dataRateLabel = new Label("Mean Data Rate");
        
        TextField dataRateField = new TextField();
        
        dataRateBox.getChildren().addAll(dataRateLabel, dataRateField);
        
        dataRateBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(dataRateBox);
        
        HBox msduBox = new HBox(10);
        
        final Label msduLabel = new Label("MSDU Size");
        
        TextField msduField = new TextField();
        
        msduBox.getChildren().addAll(msduLabel, msduField);
        
        msduBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(msduBox);
        
        HBox flagBox = new HBox(10);
        
        final Label flagLabel = new Label("Fixed Size Flag");
        
        TextField flagField = new TextField();
        
        flagBox.getChildren().addAll(flagLabel, flagField);
        
        flagBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(flagBox);
        
        HBox uptidBox = new HBox(10);
        
        final Label uptidLabel = new Label("UP/TID");
        
        TextField uptidField = new TextField();
        
        uptidBox.getChildren().addAll(uptidLabel, uptidField);
        
        uptidBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(uptidBox);
        
        addTS.setOnAction(p -> //v, mean data rate, msdu size, fixed size flag, UP/TID
        {
        	hostRun(4000000, 'v', 0, "", 0, 0, 0);
        });
        
        delTS.setOnAction(q -> //w, UP/TID
        {
        	hostRun(4000000, 'w', 0, "", 0, 0, 0);
        });
        
        HBox subtypeBox = new HBox(10);
        
        final Label subtypeLabel = new Label("Subtype");
        
        TextField subtypeField = new TextField();
        
        subtypeBox.getChildren().addAll(subtypeLabel, subtypeField);
        
        subtypeBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(subtypeBox);
        
        HBox acBox = new HBox(10);
        
        final Label acLabel = new Label("AC");
        
        TextField acField = new TextField();
        
        acBox.getChildren().addAll(acLabel, acField);
        
        acBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(acBox);
        
        HBox categoryBox = new HBox(10);
        
        final Label categoryLabel = new Label("Category");
        
        TextField categoryField = new TextField();
        
        categoryBox.getChildren().addAll(categoryLabel, categoryField);
        
        categoryBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(categoryBox);
        
        setQMF.setOnAction(r -> //x, subtype, AC, category
        {
        	hostRun(4000000, 'x', 0, "", 0, 0, 0);
        });
        
        getRTS.setOnAction(s -> //y
        {
        	hostRun(4000000, 'y', 0, "", 0, 0, 0);
        });
        HBox rtsBox = new HBox(10);
        
        final Label rtsLabel = new Label("RTS");
        
        TextField rtsField = new TextField();
        
        rtsBox.getChildren().addAll(rtsLabel, rtsField);
        
        rtsBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(rtsBox);
        setRTS.setOnAction(t -> //z, RTS th
        {
        	hostRun(4000000, 'z', 0, "", 0, 0, 0);
        });
        
        getPHYRate.setOnAction(u -> //A
        {
        	hostRun(4000000, 'A', 0, "", 0, 0, 0);
        });
        HBox phyRateBox = new HBox(10);
        
        final Label phyRateLabel = new Label("PHY Rate");
        
        TextField phyRateField = new TextField();
        
        phyRateBox.getChildren().addAll(phyRateLabel, phyRateField);
        
        phyRateBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(phyRateBox);
        
        setPHYRate.setOnAction(v -> //B, PHY rate
        {
        	hostRun(4000000, 'B', 0, "", 0, 0, 0);
        });
        
        getPHYMode.setOnAction(w -> //C
        {
        	hostRun(4000000, 'C', 0, "", 0, 0, 0);
        });
        HBox phyModeBox = new HBox(10);
        
        final Label phyModeLabel = new Label("PHY Mode");
        
        TextField phyModeField = new TextField();
        
        phyModeBox.getChildren().addAll(phyModeLabel, phyModeField);
        
        phyModeBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().add(phyModeBox);
        
        setPHYMode.setOnAction(x -> //D, PHY Mode
        {
        	hostRun(4000000, 'D', 0, "", 0, 0, 0);
        });
        
        get11B.setOnAction(y -> //E
        {
        	hostRun(4000000, 'E', 0, "", 0, 0, 0);
        });
        HBox preambleBox = new HBox(10);
        
        final Label preambleLabel = new Label("Preamble");
        
        TextField preambleField = new TextField();
        
        preambleBox.getChildren().addAll(preambleLabel, preambleField);
        
        preambleBox.setAlignment(Pos.CENTER_RIGHT);
        
        infoBox.getChildren().addAll(preambleBox);
        set11B.setOnAction(z -> //F preamble
        {
        	hostRun(4000000, 'F', 0, "", 0, 0, 0);
        });
        disconnectButton.setOnAction(A -> 
        {
        	message.setTextFill(Color.BLACK);
        	hostRun(4000000, '3', 0, "", 0, 0, 0);
        	message.setText(getMessage());
        	freeMessage();	
        	if(!isConnected())
        	{
        		connectionStatus.setText("");
        		disconnectButton.setDisable(true);
            	client.setDisable(true);
            	server.setDisable(true);
        	}
        });
        	
        Scene scene = new Scene(mainBox, 600, 700);
        
        primaryStage.setScene(scene);
        
       	primaryStage.show();
       	
       	primaryStage.setOnCloseRequest(B -> {
       		
       		if(scans != 0) freeData();
       		if(isConnected()) hostRun(4000000, '3', 0, "", 0, 0, 0);
       	});
    }
    private static String printData(int[] iP, int[] gW, int[] netmask, int[] mac, String hostname)
    {
    	String message = "";
    	message += "IP: ";
    	int i;
    	for(i = 0; i < 4; i++)
    	{
    		message += iP[i];
    		if(i != 3)
    		{
    			message += ".";
    		}
    	}
    	message += "\nGW: ";
    	for(i = 0; i < 4; i++)
    	{
    		message += gW[i];
    		if(i != 3)
    		{
    			message += ".";
    		}
    	}
    	message += "\nNetmask: ";
    	for(i = 0; i < 4; i++)
    	{
    		message += netmask[i];
    		if(i != 3)
    		{
    			message += ".";
    		}
    	}
    	message += "\nMAC Address: " + macAddress(mac);
    	message += "\nHostname: " + hostname;
    	
    	return message;
    }
    private static String macAddress(int[] addressDec)
    {
    	String address ="";
    	for(int i = 0; i < 6; i++)
    	{
    		address += String.format("%x", addressDec[i]);
    		if(i != 5)
    		{
    			address += ":";
    		}
    		
    	}
    	return address;
    }
    private static String bSSID(int[] id)
    {
    	String bss = "";
    	for(int i = 0; i < 6; i++)
    	{
    		bss += decToHexa(id[i]);
    		
    	}
    	return bss;
    }
    private static String decToHexa(int n)
    {   
        // char array to store hexadecimal number
        String hexaDeciNum = "";
        
        char firstChar = '\u0000';
        
        char secondChar = '\u0000';
      
        // counter for hexadecimal number array
        int i = 0;
        while(n!=0)
        {   
            // temporary variable to store remainder
            int temp  = 0;
          
            // storing remainder in temp variable.
            temp = n % 16;
          
            // check if temp < 10
            if(temp < 10)
            {
            	if(i == 0)
            	{
            		secondChar = (char)(temp + 48);
            	}
            	else
            	{
            		firstChar = (char)(temp + 48);
            	}
                i++;
            }
            else
            {
            	if(i == 0)
            	{
            		secondChar = (char)(temp + 55);
            	}
            	else
            	{
            		firstChar = (char)(temp + 55);
            	}
                i++;
            }
          
            n = n/16;
        }
       	hexaDeciNum += firstChar;
        hexaDeciNum += secondChar;
        
        
        // printing hexadecimal number array in reverse order
        return hexaDeciNum;
    }
    private static String enterPassword()
    {
    	Stage primaryStage = new Stage();
        
        HBox hb = new HBox();
        
        hb.setSpacing(10);
        
        hb.setAlignment(Pos.CENTER_LEFT);
        
        Label label = new Label("Password");
        
        final PasswordField pb = new PasswordField();
        
        final TextField textField = new TextField();
        
        textField.setManaged(false);
        
        textField.setVisible(false);
        
        CheckBox checkBox = new CheckBox("Show password");
        
        textField.managedProperty().bind(checkBox.selectedProperty());
        
        textField.visibleProperty().bind(checkBox.selectedProperty());
        
        pb.managedProperty().bind(checkBox.selectedProperty().not());
        
        pb.visibleProperty().bind(checkBox.selectedProperty().not());
        
        
        textField.textProperty().bindBidirectional(pb.textProperty());
        
        pb.setOnAction(e -> 
        {
            primaryStage.close();
        	
        	passw = pb.getText();
           
        });
        
        textField.setOnAction(e -> 
        {
        	primaryStage.close();
        	
        	passw = textField.getText();
        });
        
        hb.getChildren().addAll(label, pb, textField, checkBox);
        Scene scene = new Scene(hb, 375, 60);
        primaryStage.setScene(scene);
        
        
       	primaryStage.showAndWait();
       	
       	return passw;
       
    }
    private static int convertIP(String ipInput)
    {
    	int iP = 0;
    	
    	String[] addresses = ipInput.split("\\.");
    	
    	for(int i = 0; i < addresses.length; i++)
    	{
    		iP <<= 8;
    		iP |= Integer.parseInt(addresses[i]);
    		
    		
    		
    	}
    	return iP;
    	
    }
    private static boolean validHex(String input)
    {
    	if(input == null || input.trim().isEmpty())
    	{
    		return false;
    	}
    	else
    	{
    		for(int i = 0; i < input.length(); i++)
    		{
    			if((input.charAt(i) < '0' || input.charAt(i) > '9') && (Character.toLowerCase(input.charAt(i)) < 'a' || (Character.toLowerCase(input.charAt(i)) > 'f')))
    			{
    				return false;
    			}
    		}
    	}
    	return true;
    }
    private static String hexToAscii(String s) 
    {
    	  int n = s.length();
    	  StringBuilder sb = new StringBuilder(n / 2);
    	  for (int i = 0; i < n; i += 2) {
    	    char a = s.charAt(i);
    	    char b = s.charAt(i + 1);
    	    sb.append((char) ((hexToInt(a) << 4) | hexToInt(b)));
    	  }
    	  return sb.toString();
    }
    private static int hex2decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
        }
        return val;
    }
    private static int hexToInt(char ch) 
    {
    	  if ('a' <= ch && ch <= 'f') { return ch - 'a' + 10; }
    	  if ('A' <= ch && ch <= 'F') { return ch - 'A' + 10; }
    	  if ('0' <= ch && ch <= '9') { return ch - '0'; }
    	  throw new IllegalArgumentException(String.valueOf(ch));
    	}
}


