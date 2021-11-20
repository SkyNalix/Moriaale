package edu.moria.moriaale;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class App extends Application {

	public static App mainInstance;
	public static Parent layout;
	public static Scene scene;
	public Stage primaryStage;

	public static void main( String[] args ) {
		launch();
	}

	public static void transferTo( Utils.GUI destination ) {
		Stage primaryStage = mainInstance.primaryStage;

		FadeTransition fade = new FadeTransition();
		fade.setDuration( Duration.millis( 100 ) );
		fade.setFromValue( 20 );
		fade.setToValue( 0.3 );
		fade.setNode( layout );
		fade.setCycleCount( 1 );
		fade.setAutoReverse( true );
		fade.play();
		fade.setOnFinished( ( event ) -> {
			try {
				layout = Utils.getParentFromResource( destination );
			} catch( IOException e ) {
				e.printStackTrace();
			}
			primaryStage.getScene().setRoot( layout );
			appearance();
		} );
		primaryStage.setTitle( "Moriaale - " + destination.name );
	}

	public static void appearance() {
		FadeTransition fade = new FadeTransition();
		fade.setDuration( Duration.millis( 100 ) );
		fade.setFromValue( 0.3 );
		fade.setToValue( 20 );
		fade.setNode( layout );
		fade.setCycleCount( 1 );
		fade.setAutoReverse( true );
		fade.play();
	}

	@Override
	public void start( Stage primaryStage ) throws IOException {
		mainInstance = this;
		this.primaryStage = primaryStage;
		mainInstance.primaryStage.setWidth( 830 );
		mainInstance.primaryStage.setHeight( 450 );
		layout = Utils.getParentFromResource( Utils.GUI.MAIN_MENU );
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


}
