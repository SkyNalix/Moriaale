package edu.moria.moriaale.controllers;

import edu.moria.moriaale.App;
import edu.moria.moriaale.Utils;
import edu.moria.moriaale.fractals.Fractal;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.List;

public class MainMenu {

	public static Fractal chosenFractal = Fractal.JULIA;
	@FXML
	ChoiceBox<String> choiceBox;

	@FXML
	public void initialize() {
		ObservableList<String> elements = FXCollections.observableList( List.of(
				  Fractal.JULIA.name,
				  Fractal.MANDELBROT.name
																			   ) );
		choiceBox.setItems( elements );
		choiceBox.setValue( elements.get( 0 ) );
	}

	@FXML
	private void drawFractalPressed() {
		chosenFractal = Fractal.valueOf( choiceBox.getValue().toUpperCase() );
		App.transferTo( Utils.GUI.DRAWER );
	}

	public void exitButtonPressed() {
		Platform.exit();
	}

}
