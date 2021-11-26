package edu.moria.moriaale.controllers;

import edu.moria.moriaale.App;
import edu.moria.moriaale.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.List;

public class MainMenu {

	@FXML
	ChoiceBox<String> choiceBox;

	@FXML
	public void initialize() {
		ObservableList<String> elements = FXCollections.observableList( List.of(
				  "El",
				  "Psy",
				  "Congroo"
																			   ) );
		choiceBox.setItems( elements );
		choiceBox.setValue( elements.get( 0 ) );


	}

	@FXML
	private void drawFractalPressed() { // Start new game
		System.out.printf( "Selected : %s%n", choiceBox.getValue() );
		App.transferTo( Utils.GUI.DRAWER );
	}

	public void exitButtonPressed() {
		Platform.exit();
	}

}
