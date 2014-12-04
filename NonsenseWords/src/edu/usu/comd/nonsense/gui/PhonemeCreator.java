package edu.usu.comd.nonsense.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import edu.usu.comd.nonsense.Glyph;
import edu.usu.comd.nonsense.Phoneme;
import edu.usu.comd.nonsense.Utils;

public class PhonemeCreator {
	
	private static Phoneme createdPhoneme;
	private static Stage myStage;
	private static TextField nameField;
	private static ListView<Glyph> glyphList;
	
	private static Stage createDialog(final Stage primaryStage, ListView<Phoneme> phonemeList)
	{
		createdPhoneme = null;
		myStage = new Stage();
		myStage.initModality(Modality.APPLICATION_MODAL);
		myStage.initOwner(primaryStage);
		
		VBox root = new VBox();
		HBox glyphBox = new HBox();
		
		glyphList.setCellFactory(glyphCellFactory);
		glyphBox.getChildren().add(glyphList);
		Button removeGlyph = new Button("Remove Selected Glyph");
		removeGlyph.setOnAction(e -> glyphList.getItems().remove(glyphList.getSelectionModel().getSelectedIndex()));
		glyphBox.getChildren().add(removeGlyph);
		
		HBox addGlyphBox = new HBox();
		TextField createGlyphText = new TextField();
		createGlyphText.setOnKeyPressed(e -> { if (e.getCode().equals(KeyCode.ENTER)) doAddGlyph(createGlyphText, glyphList);});
		addGlyphBox.getChildren().add(createGlyphText);
		Button addGlyphButton = new Button("Add Glyph");
		addGlyphButton.setOnAction(e -> doAddGlyph(createGlyphText, glyphList));
		addGlyphBox.getChildren().add(addGlyphButton);
		
		Button save = new Button("Save");
		save.setOnAction(e-> doSaveAction(glyphList, nameField, phonemeList));
		
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> doCancelAction());
		
		root.getChildren().add(new Label("Phoneme name:"));
		root.getChildren().add(nameField);
		root.getChildren().add(new Label("Current glyphs:"));
		root.getChildren().add(glyphBox);
		root.getChildren().add(new Label("Add new glyph:"));
		root.getChildren().add(addGlyphBox);
		root.getChildren().add(save);
		root.getChildren().add(cancel);
		
		Scene dialogScene = new Scene(root, 300, 400);
		myStage.setScene(dialogScene);
		return myStage;
	}
	


	public static Stage createCreateDialog(final Stage primaryStage, ListView<Phoneme> phonemeList)
	{
		nameField = new TextField("Phoneme Name: ");
		glyphList = new ListView<Glyph>();
		return createDialog(primaryStage, phonemeList);
	}
	
	public static Stage createEditDialog(final Stage primaryStage, ListView<Phoneme> phonemeList, Phoneme p)
	{
		nameField = new TextField(p.getName());
		glyphList = new ListView<Glyph>();
		List<Glyph> tempList = new ArrayList<Glyph>();
		for (String s : p.getPossibilities())
		{
			tempList.add(new Glyph(s.toCharArray()));
		}
		glyphList.setItems(FXCollections.observableArrayList(tempList));
		
		return createDialog(primaryStage, phonemeList);
	}

	private static Object doSaveAction(ListView<Glyph> glyphList, TextField nameField, ListView<Phoneme> phonemeList) {
		List<String> possibilities = new ArrayList<String>();
		for (Glyph g : glyphList.getItems())
		{
			possibilities.add(String.valueOf(g.getChars()));
		}
		
		String name = nameField.getText();
		int priority = 1;
		
		if (possibilities.isEmpty() || name.trim().isEmpty())
		{
			return doCancelAction();
		}
		
		createdPhoneme = new Phoneme(possibilities, priority, name);
		
		boolean matchesName = false;
		String tempName = name;
		for (Phoneme p : phonemeList.getItems())
		{
			if (p.getName().equals(tempName))
			{
				matchesName = true;
				tempName = tempName.concat("+");
			}
		}
		if (matchesName == true)
		{
			createdPhoneme.setName(tempName);
		}
		phonemeList.getItems().add(createdPhoneme);
		Utils.addPhoneme(createdPhoneme.clone());
		
		if (myStage != null)
		{
			myStage.hide();
		}
		return null;
	}
	
	private static Object doCancelAction() {
		if (myStage != null)
		{
			myStage.hide();
		}
		return null;
	}

	private static Object doAddGlyph( TextField createGlyphText, ListView<Glyph> glyphList) {
		Glyph g = new Glyph(createGlyphText.getText().toCharArray());
		glyphList.getItems().add(g);
		createGlyphText.clear();
		createGlyphText.requestFocus();
		return null;
	}
	
	private static Callback<ListView<Glyph>, ListCell<Glyph>> glyphCellFactory = new Callback<ListView<Glyph>, ListCell<Glyph>>() {

		@Override
		public ListCell<Glyph> call(ListView<Glyph> arg0) {
			return new GlyphCell();
		}
		
	};
	
	public static Phoneme getCreatedPhoneme()
	{
		return createdPhoneme;
	}

}
