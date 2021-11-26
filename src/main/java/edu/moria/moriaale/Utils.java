package edu.moria.moriaale;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class Utils {


	public static float divergenceIndex( int MAX_ITERATIONS, Complexe z, Complexe c ) {
		float i = 0;
		while( z.module() < 2 && i <= MAX_ITERATIONS ) {
			z = z.product( z ).add( c );
			i++;
		}
		return i;
	}

	public static Parent getParentFromResource( GUI gui ) throws IOException {
		return FXMLLoader.load( getResource( gui.FXMLPath ) );
	}

	public static URL getResource( String address ) {
		return Utils.class.getResource( address );
	}

	public enum GUI {

		MAIN_MENU( "Main menu", "/FXML/MainMenu.fxml" ),
		DRAWER( "Drawer", "/FXML/Drawer.fxml" );

		public String name;
		public String FXMLPath;

		GUI( String label, String FXMLPath ) {
			this.name = label;
			this.FXMLPath = FXMLPath;
		}
	}

}
