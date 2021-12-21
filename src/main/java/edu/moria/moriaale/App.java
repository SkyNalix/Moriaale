package edu.moria.moriaale;

import edu.moria.moriaale.controllers.Drawer;
import edu.moria.moriaale.controllers.MainMenu;
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
	public App secondInstance;

	public Drawer actuel;
	public boolean change = false;

	public Parent layout;
	public Scene scene;
	public Stage primaryStage;

	public static void main( String[] args ) {
		launch();
	}

	public App getSecondInstance() {
		if( this.secondInstance == null ) {
			secondInstance = mainInstance;
			return secondInstance;
		}
		return this.secondInstance;
	}


	public void transferTo( Utils.GUI destination ) {
		Stage primaryStage = this.secondInstance.primaryStage;
		MainMenu.sortie = this.secondInstance;
		try {
			mainInstance.layout = Utils.getParentFromResource( destination );

		} catch( IOException e ) {
			e.printStackTrace();
		}
		primaryStage.getScene().setRoot( mainInstance.layout );
		primaryStage.setTitle( "Moriaale - " + destination.name );

		ChangeListener<Number> stageSizeListener = ( observable, oldValue, newValue ) -> {
			if( !this.change ) {
				this.change = true;
				this.actuel = Drawer.instance2;
			}
			ButtonBar buttonBar = this.actuel.buttonBar;
			buttonBar.setLayoutX( ( primaryStage.getWidth() - buttonBar.getWidth() ) / 2 );
			buttonBar.setLayoutY( primaryStage.getHeight() - ( 1.3 * buttonBar.getHeight() ) );
		};
		primaryStage.widthProperty().addListener( stageSizeListener );
		primaryStage.heightProperty().addListener( stageSizeListener );
	}

	@Override
	public void start( Stage primaryStage ) throws IOException {
		mainInstance = this;
		this.primaryStage = primaryStage;

		this.secondInstance = this;

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

}
