package edu.moria.moriaale.controllers;

import edu.moria.moriaale.App;
import edu.moria.moriaale.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MainMenu {

	@FXML
	private void newGameButtonPressed() { // Start new game
		App.transferTo( Utils.GUI.DRAWER );
	}

	@FXML
	private void aboutUs() {
		Alert dialog = new Alert(
				  Alert.AlertType.INFORMATION,
				  "We are a group of five students of the University of Paris : \n" +
				  "Arbi, Virgile, Changrui, Sandra and Yasmina\n" +
				  "MoriaMines is our educational project we did during the 4th semester.\n",
				  ButtonType.OK
		);
		dialog.initOwner( App.mainInstance.primaryStage );
		dialog.setTitle( "About us" );
		dialog.setHeaderText( null );
		dialog.showAndWait();
	}

	public void exitButtonPressed() {
		try {
			App.mainInstance.stop();
		} catch( Exception e ) {
			e.printStackTrace();
			System.exit( 1 );
		}
	}

}
