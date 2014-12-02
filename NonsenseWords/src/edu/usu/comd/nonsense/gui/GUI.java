package edu.usu.comd.nonsense.gui;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import edu.usu.comd.nonsense.Creator;
import edu.usu.comd.nonsense.Phoneme;
import edu.usu.comd.nonsense.Utils;

public class GUI extends Application {
	
	private Button addPhonemeButton;
	private Button removePhonemeButton;
	private Button createWordListButton;
	private ListView<Phoneme> phonemeListView;
	private ListView<Phoneme> wordBuilderList;
	private TextArea output;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		List<Phoneme> phonemeList = Utils.readPhonemeList();
		phonemeListView = new ListView<Phoneme>();
		ObservableList<Phoneme> items = FXCollections.observableArrayList(phonemeList);
		phonemeListView.setItems(items);
		phonemeListView.setCellFactory(phonemeCellFactory);
		phonemeListView.getSelectionModel().selectedItemProperty().addListener((o, o1, o2) -> addPhonemeButton.setText("Add " + o2.getName()));
		
		wordBuilderList = new ListView<Phoneme>();
		wordBuilderList.setOrientation(Orientation.HORIZONTAL);
		wordBuilderList.setCellFactory(phonemeCellFactory);

		
		BorderPane root = new BorderPane();
		VBox vbox = new VBox();
		addPhonemeButton = new Button();
		addPhonemeButton.setText("Add Phoneme");
		addPhonemeButton.setOnAction(e -> wordBuilderList.getItems().add(wordBuilderList.getSelectionModel().isEmpty() ? wordBuilderList.getItems().size() : wordBuilderList.getSelectionModel().getSelectedIndex() + 1, phonemeListView.getSelectionModel().getSelectedItem()));
		
		removePhonemeButton = new Button("Remove Phoneme");
		removePhonemeButton.setOnAction(e -> wordBuilderList.getItems().remove(wordBuilderList.getSelectionModel().getSelectedIndex()));
		
		createWordListButton = new Button();
		createWordListButton.setText("Create List");
		createWordListButton.setOnAction(e -> output.setText(Creator.createOutputString(wordBuilderList.getItems(), 5)));
		
		output = new TextArea();
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(addPhonemeButton, removePhonemeButton, createWordListButton);

		vbox.getChildren().add(hbox);
		vbox.getChildren().add(phonemeListView);
		vbox.getChildren().add(wordBuilderList);
		vbox.getChildren().add(output);
		root.setCenter(vbox);
		Scene scene = new Scene(root, 300,800);
		
		primaryStage.setTitle("Titlezorz");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	private Callback<ListView<Phoneme>, ListCell<Phoneme>> phonemeCellFactory = new Callback<ListView<Phoneme>, ListCell<Phoneme>>() {

		@Override
		public ListCell<Phoneme> call(ListView<Phoneme> arg0) {
			return new PhonemeCell();
		}
		
	};
}
