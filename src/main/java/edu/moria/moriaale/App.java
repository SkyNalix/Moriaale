package edu.moria.moriaale;

import edu.moria.moriaale.controllers.Drawer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

	public static App mainInstance;
	public Parent layout;
	public Scene scene;
	public Stage primaryStage;

	public static void main( String[] args ) {
		launch();
	}

	public static void transferTo( Utils.GUI destination ) {
		Stage primaryStage = mainInstance.primaryStage;
		try {
			mainInstance.layout = Utils.getParentFromResource( destination );
		} catch( IOException e ) {
			e.printStackTrace();
		}
		primaryStage.getScene().setRoot( mainInstance.layout );
		primaryStage.setTitle( "Moriaale - " + destination.name );
		ChangeListener<Number> stageSizeListener = ( observable, oldValue, newValue ) -> {
			ButtonBar buttonBar = Drawer.instance.buttonBar;
			buttonBar.setLayoutX( ( primaryStage.getWidth() - buttonBar.getWidth() ) / 2 );
			buttonBar.setLayoutY( primaryStage.getHeight() - ( 2 * buttonBar.getHeight() ) );
		};
		primaryStage.widthProperty().addListener( stageSizeListener );
		primaryStage.heightProperty().addListener( stageSizeListener );
	}

	@Override
	public void start( Stage primaryStage ) throws IOException {
		mainInstance = this;
		this.primaryStage = primaryStage;
		primaryStage.setWidth( 830 );
		primaryStage.setHeight( 450 );
		layout = Utils.getParentFromResource( Utils.GUI.MAIN_MENU );
		primaryStage.setTitle( "Moriaale - " + Utils.GUI.MAIN_MENU.name );
		scene = new Scene( layout );
		primaryStage.setScene( scene );
		primaryStage.setMinWidth( primaryStage.getWidth() );
		primaryStage.setMinHeight( primaryStage.getHeight() );
		primaryStage.setFullScreenExitKeyCombination( KeyCombination.NO_MATCH );
		try {
			primaryStage.getIcons().add( new Image( String.valueOf( Utils.getResource( "/logo.png" ) ) ) );
		} catch( Exception ignored ) {
		}
		primaryStage.show();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}

}
