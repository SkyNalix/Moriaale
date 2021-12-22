package edu.moria.moriaale.controllers;

import edu.moria.moriaale.App;
import edu.moria.moriaale.InputMenu;
import edu.moria.moriaale.Utils;
import edu.moria.moriaale.InputMenu.InputsCouleur;
import edu.moria.moriaale.fractals.Fractal;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class Drawer {

	public Drawer instance;
	public static Drawer instance2;
	public final ArrayList<Thread> threads = new ArrayList<>();
	public App app;

	public Pane gamePane;
	public BorderPane drawingPane;
	public ButtonBar buttonBar;

	public double MOVE_X = 0.1;
	public double MOVE_Y = 0.1;
	public InputMenu.Inputs inputs;
	public Runnable generator;
	private WritableImage buffer;

	public InputsCouleur couleurFond;
	public InputsCouleur couleurFractal;

	
	public Drawer getInstance() {
		if( this.instance == null ) {
			instance = instance2;
			return instance2;
		}
		return this.instance;
	}

	public void initialize() {
		if(MainMenu.liste != null){
			this.couleurFond = MainMenu.liste.get(0);
			this.couleurFractal = MainMenu.liste.get(1);
		}
		
		this.instance = this;
		instance2 = this;
		inputs = InputMenu.showDialog();

		App tmp = new App();
		if( inputs == null ) Platform.runLater( () -> App.mainInstance.transferTo( Utils.GUI.MAIN_MENU ) );
		this.app = tmp.getSecondInstance();

		this.draw();
		refreshButtonsPosition();
	}

	public void draw() {
		if( drawingPane != null ) {
			drawingPane.getChildren().clear();
			for( Thread thread : threads ) {
				thread.interrupt();
				threads.clear();
			}
			drawingPane = null;
		}
		drawingPane = new BorderPane();
		gamePane.getChildren().add( drawingPane );

		if( inputs == null ) {
			Platform.runLater( () -> App.mainInstance.transferTo( Utils.GUI.MAIN_MENU ) );
			return;
		}
		buffer = new WritableImage( inputs.maxWidth, inputs.maxHeight );
		PixelWriter pw = buffer.getPixelWriter();

		int nbBlock = 4;
		int blockSize = ( inputs.maxHeight / nbBlock );
		for( int y = 0; y < nbBlock; y++ ) {
			int startY = y * blockSize;

			try {
				this.generator = (Runnable) MainMenu.chosenFractal.constructors.newInstance(
						  pw, startY, startY + blockSize, inputs, MOVE_X, MOVE_Y, this.couleurFond, this.couleurFractal );
				Platform.runLater( this.generator );
			} catch( Exception e ) {
				e.printStackTrace();
			}
		}
		this.getInstance().drawingPane.getChildren().add( new ImageView( buffer ) );
		buttonBar.toFront();
	}

	@FXML
	private void onToImagePressed() {
		Utils.saveWritableImage( buffer );
	}

	@FXML
	private void onExitPressed() {
		for( Thread thread : threads ) {
			thread.interrupt();
			threads.clear();
		}
		this.app.change = false;
		this.app.actuel = null;
		this.app.transferTo( Utils.GUI.MAIN_MENU );
	}

	@FXML
	private void newDrawer() {
		App nouveau = new App();
		Stage x = new Stage();

		try {
			nouveau.start( x );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}

	@FXML
	private void onZoomPressed() {
		inputs.zoom *= 1.5;
		this.instance.draw();
	}

	@FXML
	private void onUnZoomPressed() {
		inputs.zoom *= 0.5;
		instance.draw();
	}

	@FXML
	private void onMoveUpPressed() {
		if( MainMenu.chosenFractal == Fractal.MANDELBROT ) {
			this.instance.MOVE_Y -= 50 / inputs.zoom;
		} else {
			this.instance.MOVE_Y -= 0.2 / inputs.zoom;
		}
		this.instance.draw();
	}

	@FXML
	private void onMoveDownPressed() {
		if( MainMenu.chosenFractal == Fractal.MANDELBROT ) {
			instance.MOVE_Y += 50 / inputs.zoom;
		} else {
			instance.MOVE_Y += 0.2 / inputs.zoom;
		}
		this.draw();

	}

	@FXML
	private void onMoveLeftPressed() {
		if( MainMenu.chosenFractal == Fractal.MANDELBROT ) {
			MOVE_X -= 50 / inputs.zoom;
		} else {
			MOVE_X -= 0.2 / inputs.zoom;
		}
		instance.draw();
	}

	@FXML
	private void onMoveRightPressed() {
		if( MainMenu.chosenFractal == Fractal.MANDELBROT ) {
			MOVE_X += 50 / inputs.zoom;
		} else {
			MOVE_X += 0.2 / inputs.zoom;
		}
		instance.draw();
	}

	public void refreshButtonsPosition() {
		instance.buttonBar.toFront();
		instance.buttonBar.setLayoutX( ( this.app.getSecondInstance().primaryStage.getWidth() - instance.buttonBar.getWidth() ) / 2 - 255 );
		instance.buttonBar.setLayoutY( this.app.getSecondInstance().primaryStage.getHeight() - ( 2 * instance.buttonBar.getHeight() ) - 110 );
	}

}
