package edu.moria.moriaale;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class Utils {

	public static Parent getParentFromResource( GUI gui ) throws IOException {
		// TODO: fix NullPointerException here
		return FXMLLoader.load( getResource( gui.FXMLPath ) );
	}

	public static URL getResource( String address ) {
		return Utils.class.getResource( address );
	}

	public enum GUI {

		MAIN_MENU( "Main menu", "FXML/MainMenu.fxml" ),
		DRAWER( "Drawer", "FXML/Drawer.fxml" );

		public String name;
		public String FXMLPath;

		GUI( String label, String FXMLPath ) {
			this.name = label;
			this.FXMLPath = FXMLPath;
		}
	}

}
