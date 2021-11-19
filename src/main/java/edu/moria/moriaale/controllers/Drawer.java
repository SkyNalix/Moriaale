package edu.moria.moriaale.controllers;

import edu.moria.moriaale.App;
import edu.moria.moriaale.Utils;
import javafx.fxml.FXML;

public class Drawer {

	@FXML
	private void onExitPressed() {
		App.transferTo( Utils.GUI.MAIN_MENU );
	}

	@FXML
	private void onZoomPressed() {
		System.out.println( "TODO zoom" );
	}

	@FXML
	private void onUnzoomPressed() {
		System.out.println( "TODO unzoom" );
	}

}
