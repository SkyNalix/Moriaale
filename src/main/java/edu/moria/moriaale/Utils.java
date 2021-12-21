package edu.moria.moriaale;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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

	public static void saveWritableImage( WritableImage writableImage ) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter( "PNG", "*.png" ) );
		saveWritableImage( writableImage, fileChooser.showSaveDialog( App.mainInstance.primaryStage ) );
	}

	public static void saveWritableImage( WritableImage writableImage, File file ) {

		if( file != null ) {
			String fileName = file.getName();

			if( !fileName.toUpperCase().endsWith( ".PNG" ) ) {
				file = new File( file.getAbsolutePath() + ".png" );
			}
			try {
				ImageIO.write( SwingFXUtils.fromFXImage( writableImage, null ),
							   "png", file );
			} catch( IOException e ) {
				e.printStackTrace();
				System.out.println( "An error occurred while trying to save the image" );
			}
		}

	}

	public enum GUI {

		MAIN_MENU( "Main menu", "/FXML/MainMenu.fxml" ),
		DRAWER( "Drawer", "/FXML/Drawer.fxml" );

		public final String name;
		public final String FXMLPath;

		GUI( String label, String FXMLPath ) {
			this.name = label;
			this.FXMLPath = FXMLPath;
		}
	}

}
