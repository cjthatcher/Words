package edu.usu.comd.nonsense.gui;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
		
		Label pListViewLabel = new Label("Avaiable Phonemes");
		List<Phoneme> phonemeList = Utils.getPhonemeList();
		phonemeListView = new ListView<Phoneme>();
		ObservableList<Phoneme> items = FXCollections.observableArrayList(phonemeList);
		phonemeListView.setItems(items);
		phonemeListView.setCellFactory(phonemeCellFactory);
		phonemeListView.setPrefHeight(150);
		
		
		Label wListViewLabel = new Label("The Word You're Building:");
		wordBuilderList = new ListView<Phoneme>();
		wordBuilderList.setCellFactory(phonemeCellFactory);
		wordBuilderList.setPrefHeight(200);

		
		BorderPane root = new BorderPane();
		VBox vbox = new VBox();
		addPhonemeButton = new Button();
		addPhonemeButton.setText("Insert Phoneme");
		addPhonemeButton.setOnAction(e -> doAddPhonemeToList());

		Button editPhonemeButton = new Button("Edit Phoneme");
		editPhonemeButton.setOnAction(e -> doEditPhoneme(phonemeListView, primaryStage));
		
		Button createPhonemeButton = new Button("Create New Phoneme");
		createPhonemeButton.setOnAction(e -> doCreatePhoneme(phonemeListView, primaryStage));
		
		Button deletePhonemeButton = new Button("Delete Phoneme");
		deletePhonemeButton.setOnAction(e -> doDeletePhoneme(phonemeListView));
		
		removePhonemeButton = new Button("Remove Phoneme");
		removePhonemeButton.setOnAction(e -> wordBuilderList.getItems().remove(wordBuilderList.getSelectionModel().getSelectedIndex()));
		
		createWordListButton = new Button();
		createWordListButton.setText("Create List");
		createWordListButton.setOnAction(e -> output.setText(Creator.createOutputString(wordBuilderList.getItems(), 5)));
		
		output = new TextArea();
		
		HBox hbox = new HBox();
		hbox.getChildren().addAll(addPhonemeButton, removePhonemeButton, createWordListButton);

		Label availablePhonemesLabel = new Label("Available Phonemes:");
		HBox phonemeBox = new HBox();
		phonemeBox.getChildren().add(phonemeListView);
		VBox phonemeButtonBox = new VBox();
		phonemeButtonBox.getChildren().add(addPhonemeButton);
		phonemeButtonBox.getChildren().add(editPhonemeButton);
		phonemeButtonBox.getChildren().add(createPhonemeButton);
		phonemeButtonBox.getChildren().add(deletePhonemeButton);
		phonemeBox.getChildren().add(phonemeButtonBox);
		
		vbox.getChildren().add(availablePhonemesLabel);
		vbox.getChildren().add(phonemeBox);
		
		Label wordBoxLabel = new Label("Word You're Building");
		HBox wordBox = new HBox();
		wordBox.getChildren().add(wordBuilderList);
		VBox wordButtonBox = new VBox();
		
		Button markAsTopPriority = new Button("Mark as top priority");
		markAsTopPriority.setTooltip(new Tooltip("Ensures the selected phoneme\nwill appear at least the number of times you specify.\nOther phonemes may appear fewer times."));
		markAsTopPriority.setOnAction(e -> doSetOnTopPriorityAction(wordBuilderList));
		
		Button moveUp = new Button("Move up");
		moveUp.setOnAction(e -> moveUpAction(wordBuilderList));
		
		Button moveDown = new Button("Move down");
		moveDown.setOnAction(e -> moveDownAction(wordBuilderList));
		
		Label numberToTestLabel = new Label("Words per phoneme:");
		TextField numberTextField = new TextField("4");
		
		Button run = new Button("Run");
		run.setOnAction(e -> doRunAction(wordBuilderList, numberTextField));
		
		wordButtonBox.getChildren().add(removePhonemeButton);
		wordButtonBox.getChildren().add(markAsTopPriority);
		wordButtonBox.getChildren().add(moveUp);
		wordButtonBox.getChildren().add(moveDown);
		wordButtonBox.getChildren().add(numberToTestLabel);
		wordButtonBox.getChildren().add(numberTextField);
		wordButtonBox.getChildren().add(run);
		
		wordBox.getChildren().add(wordButtonBox);
		
		vbox.getChildren().add(wordBoxLabel);
		vbox.getChildren().add(wordBox);

		vbox.getChildren().add(output);
		root.setCenter(vbox);
		Scene scene = new Scene(root, 450,800);
		
		primaryStage.setTitle("Titlezorz");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private Object doDeletePhoneme(ListView<Phoneme> phonemeListView2) {
		Phoneme p = phonemeListView2.getSelectionModel().getSelectedItem();
		if (p != null)
		{
			Utils.removePhoneme(p);
			phonemeListView2.getItems().remove(p);
		}
		return null;
	}

	private Object moveDownAction(ListView<Phoneme> wordBuilderList2) {
		Phoneme phonemeToMove = wordBuilderList2.getSelectionModel().getSelectedItem();
		int newPlace = wordBuilderList2.getSelectionModel().getSelectedIndex();
		newPlace = newPlace < wordBuilderList2.getItems().size() - 1 ? newPlace + 1 : newPlace;
		
		wordBuilderList2.getItems().remove(phonemeToMove);
		wordBuilderList2.getItems().add(newPlace, phonemeToMove);
		wordBuilderList2.getSelectionModel().select(newPlace);
		return null;
	}

	private Object moveUpAction(ListView<Phoneme> wordBuilderList2) {
		Phoneme phonemeToMove = wordBuilderList2.getSelectionModel().getSelectedItem();
		int newPlace = wordBuilderList2.getSelectionModel().getSelectedIndex();
		newPlace = newPlace > 0 ? newPlace -1 : 0;
		
		wordBuilderList2.getItems().remove(phonemeToMove);
		wordBuilderList2.getItems().add(newPlace, phonemeToMove);
		wordBuilderList2.getSelectionModel().select(newPlace);
		return null;
	}

	private Object doAddPhonemeToList() {
		Phoneme phonemeToAdd = phonemeListView.getSelectionModel().getSelectedItem().clone();
		wordBuilderList.getItems().add(phonemeToAdd);
		return null;
	}

	private Object doRunAction(ListView<Phoneme> wordBuilderList2, TextField numberField) {
		int numToRun = 4;
		try {
			numToRun = Integer.parseInt(numberField.getText());
		}
		catch (Exception e)
		{
			numToRun = 4;
			numberField.setText("4");
		}
		output.setText(Creator.createOutputString(wordBuilderList.getItems(), numToRun));
		return null;
	}

	private Object doSetOnTopPriorityAction(ListView<Phoneme> wordBuilderList2) {
		Phoneme p = wordBuilderList2.getSelectionModel().getSelectedItem();
		p.setAsTopPriority();
		int index = wordBuilderList2.getSelectionModel().getSelectedIndex();
		wordBuilderList2.getItems().remove(p);
		wordBuilderList2.getItems().add(index, p);
		wordBuilderList2.getSelectionModel().select(index);
		return null;
	}

	private Object doCreatePhoneme(ListView<Phoneme> phonemeListView2, final Stage primaryStage) {
		Stage s = PhonemeCreator.createCreateDialog(primaryStage, phonemeListView2);
		s.show();
		return null;
	}

	private Object doEditPhoneme(ListView<Phoneme> phonemeListView2, final Stage primaryStage) {
		Phoneme p = phonemeListView2.getSelectionModel().getSelectedItem();
		Stage s = PhonemeCreator.createEditDialog(primaryStage, phonemeListView2, p);
		s.show();
		return null;
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
