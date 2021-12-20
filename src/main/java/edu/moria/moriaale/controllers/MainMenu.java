package edu.moria.moriaale.controllers;

import edu.moria.moriaale.App;
import edu.moria.moriaale.Utils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import javafx.stage.Stage;

import java.util.List;

public class MainMenu {
	Stage x;
	
	static String choix = "Julia Set";
	@FXML
	ChoiceBox<String> choiceBox;

	@FXML
	public void initialize() {
		ObservableList<String> elements = FXCollections.observableList( List.of(
				  "Julia Set",
				  "Mandelbrot"
																			   ) );
		choiceBox.setItems( elements );
		choiceBox.setValue( elements.get( 0 ) );


	}

	@FXML
	private void drawFractalPressed() {
		choix = choiceBox.getValue();
		App.mainInstance.transferTo(Utils.GUI.DRAWER);
	}

	public void exitButtonPressed() {
		
		//Platform.exit();
	}

}
