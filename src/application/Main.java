package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

//class main extends from application to create GUI 
public class Main extends Application {

	// instance from classes node avl and double list and stack all things
	static DoubleCircularLinkedList listD = new DoubleCircularLinkedList();// instance from LinkedListDoubly for
	NodeDLL DLL = null; // instance from NodeDLL
	AVLMartyr AVL1 = null;
	AVLStackMartyr AVL2 = null ;
																			// location
	AVLMartyr listavlmartyr = new AVLMartyr();
	AVLStackMartyr listavlstack = new AVLStackMartyr();
	StackMartyr liststack = new StackMartyr();
//root main screen
	BorderPane root = new BorderPane();

	// tabs
	TabPane tabPane = new TabPane();
	Tab tab1 = new Tab("Location Screen");
	Tab tab2 = new Tab("Martyrs Screen");
	Tab tab3 = new Tab("Statistics Screen");
	Tab tab4 = new Tab("Save Screen");

	// create file and file chooser
	FileChooser fileChooser = new FileChooser();
	File file;
	
	FileChooser fileChooser1 = new FileChooser();
	File file1;

	ArrayList<String> locations;

	
	BufferedWriter writer ;
	
	Button btsave;
	//method start
	@Override
	public void start(Stage primaryStage) {
		Button openFileButton = new Button("Open File");
		Image image = new Image("C:\\Users\\user\\Desktop\\ayham new\\2nd year\\2nd year 2sem\\Data Structuer\\635871582169010000.jpg");
		ImageView imageView = new ImageView(image);
		imageView.fitWidthProperty().bind(root.widthProperty());
		imageView.fitHeightProperty().bind(root.heightProperty());
		root.getChildren().add(imageView);

		root.setBottom(openFileButton);
		root.setTop(tabPane());
		root.setAlignment(openFileButton, Pos.CENTER);
		

		// StackPane root = new StackPane(openFileButton);
		Scene scene = new Scene(root, 750, 450);

		openFileButton.setOnAction(e -> {// invoke this method when i click on openFileButton button
			ShowDialog(primaryStage);
		});
		
		
		btsave.setOnAction(e ->{
			ShowDialogSave(primaryStage);
		});

		primaryStage.setTitle("Martyrs File Processor");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
//method to create content of tab 1 is locations 
	public BorderPane contenttab1() {
		BorderPane bpane = new BorderPane();
		bpane.setPadding(new Insets(25));

		ComboBox<String> comboOptions = new ComboBox<>();
		 ObservableList<String> items = FXCollections.observableArrayList("Insert", "Update", "Delete", "Search");
		 comboOptions.setItems(items);
		comboOptions.setValue(items.get(0));
		//comboOptions.getItems().addAll("Insert", "Update", "Delete", "Search");

		TextField textLocation = new TextField();
		textLocation.setPromptText("Insert Location");
		Button btInsert = new Button("Insert");
		HBox hpane = new HBox(15);
		hpane.getChildren().addAll(textLocation, btInsert);
		hpane.setAlignment(Pos.BOTTOM_CENTER);
		

		// cannot enter any number or any things
		textLocation.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[a-zA-Z]*$")) {
				textLocation.setText(oldValue);
			}
		});

		ComboBox<String> comboLocation = new ComboBox<>();
		locations = listD.getLocations();
		comboLocation.getItems().addAll(locations);
		TextField textnewLocation = new TextField();
		textnewLocation.setPromptText("Enter new Location");
		Button btUpdate = new Button("Update");
		HBox hpane1 = new HBox(15);
		hpane1.getChildren().addAll(comboLocation, textnewLocation, btUpdate);
		hpane1.setAlignment(Pos.BOTTOM_CENTER);
		
		ComboBox<String> comboLocationDelete = new ComboBox<>();
		comboLocationDelete.getItems().addAll(locations);
		Button btDelete = new Button("Delete");
		HBox hpane2 = new HBox(15);
		hpane2.getChildren().addAll(comboLocationDelete, btDelete);
		hpane2.setAlignment(Pos.BOTTOM_CENTER);
		
		ComboBox<String> comboLocationSearch = new ComboBox<>();
		comboLocationSearch.getItems().addAll(locations);
		Button btSearch = new Button("Search");
		HBox hpane3 = new HBox(15);
		hpane3.getChildren().addAll(comboLocationSearch, btSearch);
		hpane3.setAlignment(Pos.BOTTOM_CENTER);
		
		btSearch.setOnAction(e ->{
			 DLL = listD.searchCDLL(comboLocationSearch.getValue()); // instance from NodeDLL
			if(DLL != null) {
				Done();
				tab2.setDisable(false);
				tab3.setDisable(false);
				tab4.setDisable(false);
			}
			else {
				CantDone();
			}
		});
		
		btDelete.setOnAction(e ->{
			if(showConfirmationDialog()) {
			String fromcombo = comboLocationDelete.getValue();
			if(listD.deleteCDLL(fromcombo)) {
				Done();
				locations = listD.getLocations();
				comboLocationDelete.getItems().clear();
				comboLocationDelete.getItems().addAll(locations);
				
				comboLocation.getItems().clear();
				comboLocation.getItems().addAll(locations);
				
				comboLocationSearch.getItems().clear();
				comboLocationSearch.getItems().addAll(locations);
				
			}
			else {
				CantDone();
			}
		}
			else {
				CantDone();
			}
		});
		
		
		btUpdate.setOnAction(e ->{
			String fromcombo = comboLocation.getValue();
			if(showConfirmationDialog()) {
			if(!textnewLocation.getText().isEmpty() && listD.searchCDLL(textnewLocation.getText()) == null ) {
				boolean check = listD.updateCDLL(textnewLocation.getText(), fromcombo);
				if(check ) {
					Done();
					comboLocation.getItems().clear();
					locations = listD.getLocations();
					comboLocation.getItems().addAll(locations);
					
					comboLocationDelete.getItems().clear();
					comboLocationDelete.getItems().addAll(locations);
					
					comboLocationSearch.getItems().clear();
					comboLocationSearch.getItems().addAll(locations);
				}
			}
			else {
				CantDone();
			}
		}
			else {
				CantDone();
			}
		});

		btInsert.setOnAction(e -> {
			boolean check = false;
			if (!textLocation.getText().isEmpty()) {
				check = listD.insertCDLL(textLocation.getText());
			}
			if (check) {
				Done();
				locations.clear();
				locations.add(textLocation.getText());
				comboLocation.getItems().addAll(locations);
				comboLocationDelete.getItems().addAll(locations);
				comboLocationSearch.getItems().addAll(locations);
				
			} else {
				CantDone();
			}
			textLocation.clear();
		});
		bpane.setCenter(hpane);
		comboOptions.setOnAction(event -> {
			String selectedOption = comboOptions.getValue();

			// Handle the selected option accordingly
			if (selectedOption.equals("Insert")) {
				bpane.setCenter(hpane);
			} else if (selectedOption.equals("Update")) {
				bpane.setCenter(hpane1);
			} else if (selectedOption.equals("Delete")) {
				bpane.setCenter(hpane2);
			}
			else if(selectedOption.equals("Search")){
				bpane.setCenter(hpane3);
			}
		});

		bpane.setTop(comboOptions);
		bpane.setAlignment(comboOptions, Pos.TOP_CENTER);
		return bpane;
	}
//method to create content of tab 2 is martyrs
	public BorderPane contenttab2() {
		BorderPane bpane = new BorderPane();
		bpane.setPadding(new Insets(25));

		ComboBox<String> comboOptions = new ComboBox<>();
		 ObservableList<String> items = FXCollections.observableArrayList("Insert", "Update", "Delete", "Search");
		 comboOptions.setItems(items);
		comboOptions.setValue(items.get(0));
		bpane.setAlignment(comboOptions, Pos.TOP_CENTER);
		
		TextField textMartyr = new TextField();
		textMartyr.setPromptText("Insert Name Martyr");
		TextField textMartyrAge = new TextField();
		textMartyrAge.setPromptText("Insert Age");
		TextField textMartyrGender = new TextField();
		textMartyrGender.setPromptText("Insert Gender");
		TextField textMartyrDate = new TextField();
		textMartyrDate.setPromptText("Insert Date");
		
		Button btInsert = new Button("Insert");
		HBox hpane = new HBox(15);
		hpane.getChildren().addAll(textMartyr,textMartyrAge,textMartyrGender,textMartyrDate, btInsert);
		hpane.setAlignment(Pos.BOTTOM_CENTER);
		bpane.setCenter(hpane);
		
		
		Button btUpdate = new Button("Update");
		TextField textMartyrnew = new TextField();
		textMartyrnew.setPromptText("Insert new Name");
		ComboBox<String> comboMartyr = new ComboBox<>();/////////
		bpane.setAlignment(comboMartyr, Pos.TOP_CENTER);
		HBox hpane1 = new HBox(15);
		hpane1.setAlignment(Pos.BOTTOM_CENTER);
		Button btrefresh = new Button("Refresh");
		hpane1.getChildren().addAll(btrefresh ,comboMartyr,textMartyrnew,btUpdate);
		
		
		Button btDelete = new Button("Delete");
		ComboBox<String> comboMartyrDelete = new ComboBox<>();
		HBox hpane2 = new HBox(15);
		hpane2.setAlignment(Pos.BOTTOM_CENTER);
		Button btrefreshDelete = new Button("Refresh");
		hpane2.getChildren().addAll(btrefreshDelete ,comboMartyrDelete,btDelete);
		
		
		Button btSearch = new Button("Search");
		ComboBox<String> comboMartyrSearch = new ComboBox<>();
		HBox hpane3 = new HBox(15);
		hpane3.setAlignment(Pos.BOTTOM_CENTER);
		Button btrefreshSearch = new Button("Refresh");
		hpane3.getChildren().addAll(btrefreshSearch ,comboMartyrSearch,btSearch);
		
		btSearch.setOnAction(e ->{
			if(comboMartyrSearch.getValue() != null) {
			Martyr martyr = DLL.getAvlMartyr().find(comboMartyrSearch.getValue()).martyr;
			String info = martyr.toString();
			DoneSearch(info);
			}
			else {
				CantDone();
			}
		});
		btrefreshSearch.setOnAction(e ->{
			comboMartyrSearch.getItems().clear();
			ArrayList<String> listMartyr = DLL.getAvlMartyr().getMartyr();
			comboMartyrSearch.getItems().addAll((listMartyr));
			listMartyr.clear();
		});
		
		
		btrefreshDelete.setOnAction(e ->{
			comboMartyrDelete.getItems().clear();
			ArrayList<String> listMartyr = DLL.getAvlMartyr().getMartyr();
			comboMartyrDelete.getItems().addAll((listMartyr));
			listMartyr.clear();
			});
		
		btDelete.setOnAction(e ->{
			if(showConfirmationDialog()) {
				if(DLL.getAvlMartyr().find(comboMartyrDelete.getValue()) != null) {
					Martyr mar = DLL.getAvlMartyr().find(comboMartyrDelete.getValue()).martyr;
				DLL.getAvlMartyr().delete(mar);
				SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy");
				String dateString = format.format(mar.getDod());
				try {
					DLL.getAvlMartyrStack().delete(dateString);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				Done();
				}
				else {
					CantDone();
				}
			}
			else {
				CantDone();
			}
		});
		
		
		btUpdate.setOnAction(e ->{
			if(showConfirmationDialog()) {
				
			if(DLL.getAvlMartyr().find(comboMartyr.getValue()) != null && !textMartyrnew.getText().isEmpty()) {
				Martyr mar = DLL.getAvlMartyr().find(comboMartyr.getValue()).martyr;
				mar.setName(textMartyrnew.getText());
				 SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy");
				 String dateString = format.format(mar.getDod());

//				try {
//					DLL.getAvlMartyrStack().find(dateString).getMartyrStack().Search(comboMartyr.getValue()).setName(textMartyrnew.getText());
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				Done();
				textMartyrnew.clear();
			}
			else {
				CantDone();
			}
		}
			else {
				CantDone();
			}
		});
		
		
		btInsert.setOnAction(e ->{
			if(!textMartyr.getText().isEmpty() && !textMartyrAge.getText().isEmpty() && !textMartyrGender.getText().isEmpty() && !textMartyrDate.getText().isEmpty()) {
				
			Martyr martyr = null;
			try {
				 martyr = new Martyr(textMartyr.getText(),Integer.parseInt(textMartyrAge.getText()) , DLL.getLocation(),
						textMartyrGender.getText().charAt(0),textMartyrDate.getText());
			} catch (NumberFormatException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(!DLL.getAvlMartyr().contains(martyr)) {
				DLL.getAvlMartyr().insert(martyr);
				try {
					DLL.getAvlMartyrStack().insert(textMartyrDate.getText(), martyr);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Done();
				textMartyr.clear();
				textMartyrAge.clear();
				textMartyrGender.clear();
				textMartyrDate.clear();
				
				locations.clear();
				locations.add(textMartyr.getText());
				comboMartyr.getItems().addAll(locations);
			}
			else {
				CantDone();
			}
		}
			
		});
		
		
		btrefresh.setOnAction(e ->{
		comboMartyr.getItems().clear();
		ArrayList<String> listMartyr = DLL.getAvlMartyr().getMartyr();
		comboMartyr.getItems().addAll((listMartyr));
		listMartyr.clear();
		});
		
		comboOptions.setOnAction(event -> {
			String selectedOption = comboOptions.getValue();
			if (selectedOption.equals("Insert")) {
				bpane.setCenter(hpane);
			} else if (selectedOption.equals("Update")) {
				bpane.setCenter(hpane1);
			} else if (selectedOption.equals("Delete")) {
				bpane.setCenter(hpane2);
			}
			else if(selectedOption.equals("Search")){
				bpane.setCenter(hpane3);
			}
		});

		
		bpane.setTop(comboOptions);
		return bpane;
	}
	//method to create content of tab3 is statistic 
	public BorderPane contenttab3() {
		BorderPane bpane = new BorderPane();
		bpane.setPadding(new Insets(25));
		
		ComboBox<String> comboOptions = new ComboBox<>();
		 ObservableList<String> items = FXCollections.observableArrayList("AVL1", "AVL2");
		 comboOptions.setItems(items);
		//comboOptions.setValue(items.get(0));
		bpane.setAlignment(comboOptions, Pos.TOP_CENTER);
		
		Button btNumber = new Button("Number of Martyr");
		Button btTravers = new Button("Travers");
		Button btMaxDate = new Button("Max Date");
		Button btHeight = new Button("Height");
		Button btnext = new Button("Next");
		Button btprev = new Button("Previous");
		
		HBox hpane = new HBox(25);
		hpane.setAlignment(Pos.CENTER);
		hpane.getChildren().addAll(btprev,btNumber,btTravers,btMaxDate,btHeight,btnext);
		
		
		bpane.setCenter(hpane);
		TextField textLocation = new TextField();
		//textLocation.setText(DLL.getLocation());
		
		btnext.setOnAction(e ->{
			DLL = DLL.next;
			textLocation.setText("The Locataion is "+DLL.getLocation());
			AVL2 = DLL.getAvlMartyrStack();
			 AVL1	= DLL.getAvlMartyr();
		});
		btprev.setOnAction(e ->{
			DLL = DLL.previous;
			textLocation.setText("The Locataion is "+DLL.getLocation());
			AVL2 = DLL.getAvlMartyrStack();
			 AVL1	= DLL.getAvlMartyr();
		});
		
		btMaxDate.setOnAction(e ->{
			if(AVL2 != null) {
				DoneMax(maxDate(AVL2.root));
				//System.out.println(maxDate(AVL2.root));				
			}
			else {
				AVL2 = DLL.getAvlMartyrStack();
				DoneMax(maxDate(AVL2.root));
			//	System.out.println(maxDate(AVL2.root));		
				AVL2 = null;
				
			}
		});
		
		btHeight.setOnAction(e ->{
			if(AVL1 != null) {
				DoneHeight(AVL1.height(AVL1.root));
			}
			else if(AVL2 != null) {
				DoneHeight(AVL2.height(AVL2.root));
			}
		});
		
		btTravers.setOnAction(e ->{
			if(AVL1 != null) {
				TextArea area = new TextArea();
				area = AVL1.printLevelByLevel(area);	
				Stage stage = new Stage();
				stage.setScene(new Scene(area , 750 , 350));
				stage.show();
			}
			else if(AVL2 != null) {
				TextArea area = new TextArea();
				area = AVL2.printLevelByLevelReverse(area);
				Stage stage = new Stage();
				stage.setScene(new Scene(area , 750 , 350));
				stage.show();
			}
			
		});
		
		btNumber.setOnAction(e ->{
			if(AVL1 != null) {
				DoneNumber(AVL1.countElements());
			}
			else if(AVL2 != null) {
				DoneNumber(AVL2.countElements());
			}
		});
		
		comboOptions.setOnAction(event -> {
			String selectedOption = comboOptions.getValue();
			if (selectedOption.equals("AVL1")) {
			 AVL1	= DLL.getAvlMartyr();
			 AVL2 = null;
				bpane.setCenter(hpane);
			} else if (selectedOption.equals("AVL2")) {
				//bpane.setCenter(hpane1);
				AVL1= null;
			AVL2 =	DLL.getAvlMartyrStack();
			}
			textLocation.setText("The Locataion is "+DLL.getLocation());
		});
		HBox hp = new HBox(25);
		hp.getChildren().addAll(comboOptions , textLocation);
		hp.setAlignment(Pos.CENTER);
		bpane.setTop(hp);
		textLocation.setStyle("-fx-control-inner-background: #333333; -fx-text-fill: white;");
		textLocation.setStyle("-fx-text-fill: Black;");
		textLocation.setStyle("-fx-pref-height: 40px; -fx-pref-width: 200px;");
		textLocation.setStyle("-fx-font-size: 20px;");


		textLocation.setDisable(true);
		return bpane;
	}
	//method to create content of tab 4 is save all info in new file
	public BorderPane contenttab4()  {
		BorderPane bpane = new BorderPane();
		
		btsave = new Button("Save");
		bpane.setCenter(btsave);
		bpane.setAlignment(btsave, Pos.BOTTOM_CENTER);
		
		return bpane;
	}
	//method to create tabs 
	public TabPane tabPane() {
		tabPane.getTabs().addAll(tab1, tab2, tab3, tab4);
		tab1.setContent(contenttab1());
		tab2.setContent(contenttab2());
		tab3.setContent(contenttab3());
		tab4.setContent(contenttab4());
		
		
		tab2.setDisable(true);
        tab3.setDisable(true);
        tab4.setDisable(true);
		return tabPane;
	}
//to show dialog 
	public void ShowDialogSave(Stage primaryStage) {
		fileChooser1.setTitle("Open File");
		file1 = fileChooser1.showOpenDialog(primaryStage);
		if (file1 != null) {// check if this file is empty or not
			try {
				saveToCSV(file1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//to show dialog
	public void ShowDialog(Stage primaryStage) {
		fileChooser.setTitle("Open File");
		file = fileChooser.showOpenDialog(primaryStage);
		if (file != null) {// check if this file is empty or not
			try {
				readFile();
				//listD.printCDLL();
				// listD.searchCDLL("Ramallah").getAvlMartyrStack().find("02/22/2023").getMartyrStack().printStack();
				// listavlstack.find("01/21/2023").getMartyrStack().printStack();

			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
//method to read data from file by file chooser
	// method to read file when i choose the file from computer
	public void readFile() throws ParseException {
		NodeDLL DLL = null;
		int counter = 0;
		Martyr martyr;
		try {// use try catch to forbid errors
			Scanner scan = new Scanner(file);
			scan.nextLine();
			while (scan.hasNextLine()) {// while to get all rows in my file
				String[] check = scan.nextLine().split(",");
				String name = check[0];
				int age = 0;
				if (!check[1].isEmpty()) {
					age = Integer.parseInt(check[1]);
				}
				String event = check[2];
				String date = check[3];
				char gender = check[4].charAt(0);
				martyr = new Martyr(name, age, event, gender, date);
				DLL = listD.searchCDLL(event);
				if (DLL != null && event.equals(DLL.getLocation())) {
					if (!DLL.getAvlMartyr().contains(martyr)) {
						DLL.getAvlMartyr().insert(martyr);
						DLL.getAvlMartyrStack().insert(date, martyr);
					}

					counter++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DoneRead();
	}// end method read file;
//method to show if the operation is done or no
	private void DoneSearch(String info) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Done Search");
		alert.setHeaderText(null);
		alert.setContentText(info);
		alert.showAndWait();
	}
	//method to show if the operation is done or no
	private void DoneNumber(int info) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Done Search");
		alert.setHeaderText(null);
		alert.setContentText("The number of node is : "+String.valueOf(info));
		alert.showAndWait();
	}
	//method to show if the operation is done or no
	private void DoneHeight(int info) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Done Search");
		alert.setHeaderText(null);
		alert.setContentText("The height of root is : "+String.valueOf(info));
		alert.showAndWait();
	}
	//method to show if the operation is done or no
	private void Done() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Done");
		alert.setHeaderText(null);
		alert.setContentText("The Opreation is done.");
		alert.showAndWait();
	}
	//method to show if the operation is done or no
	private void CantDone() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("------------> Error <---------");
		alert.setHeaderText(null);
		alert.setContentText("Please check the Input.");
		alert.showAndWait();
	}
	//method to show if the operation is done read from file or no
	// method to show if done the operation or no
	private void DoneRead() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("File Read");
		alert.setHeaderText(null);
		alert.setContentText("File reading is done.");
		alert.showAndWait();
	}
	//method to show confirmation 
	//method to show the dialog
	private boolean showConfirmationDialog() {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure to delete", ButtonType.YES, ButtonType.NO);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
			return alert.getResult() == ButtonType.YES;
		}
	//main method 
	public static void main(String[] args) {
		listD.insertCDLL("Nablus");
		listD.insertCDLL("Jenin");
		listD.insertCDLL("Hebron");
		listD.insertCDLL("Ramallah");
		
		// System.out.println(listD.insertCDLL("Nablus"));
		launch(args);
	}
	//method to save the data to the file
	public void saveToCSV(File filePath) throws IOException {
	     writer = new BufferedWriter(new FileWriter(filePath)) ;
	        // Write the CSV header
	        writer.write("Name,Age,Location,Gender,Date of Death");
	        writer.newLine();

	        NodeDLL dll = listD.getHead();
	        
	        // Write the martyr information
	        while(!dll.next.equals(listD.getHead())) {
	        	 Writer(dll.getAvlMartyr().root);
	        dll = dll.next;
	        }
	        Writer(dll.getAvlMartyr().root);
	        writer.close();
	        Done();
	    }
	//method to write on file 
	public void Writer(NodeAVL root) throws IOException {
		if (root != null) {
		Writer(root.left);
		Writer(root.right);
		 writer.write(String.format("%s,%d,%s,%s,%s",
                 root.martyr.getName(),
                 root.martyr.getAge(),
                 root.martyr.getLocation(),
                 root.martyr.getGender(),
                 root.martyr.getDod()));
         writer.newLine();
	}
	
}
//to show max of date
	public void DoneMax(String str) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Done Search");
		alert.setHeaderText(null);
		alert.setContentText("The date that had the maximum number of martyrs : "+ str);
		alert.showAndWait();	
	}
	//get max date 
	public String maxDate(NodeAVLStack node) {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
	    if (node == null) {
	        return null; // or throw an exception indicating an empty tree
	    }

	    // Traverse to the rightmost node of the AVL tree
	    while (node.right != null) {
	        node = node.right;
	    }

	    // Get the top element (martyr) from the stack at the maximum date
	    StackMartyr stack = node.getMartyrStack();
	    if (!stack.isEmpty()) {
	        Martyr maxMartyr = stack.peek();
	        return dateFormat.format(maxMartyr.getDod());
	      //  return maxMartyr.getDod();
	    }

	    return null; // or handle the case when the stack is empty
	}

}

