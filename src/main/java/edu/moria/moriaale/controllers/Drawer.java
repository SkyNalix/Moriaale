package edu.moria.moriaale.controllers;

import edu.moria.moriaale.App;
import edu.moria.moriaale.InputMenu;
import edu.moria.moriaale.Utils;
import edu.moria.moriaale.Utils.GUI;
import edu.moria.moriaale.fractals.Fractal;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class Drawer {

	public Drawer instance;
 
	public static Drawer instance2;
	public Drawer instance;
	public ArrayList<Thread> threads = new ArrayList<>();
	public App app;

	public Pane gamePane;
	public BorderPane drawingPane;
	public ButtonBar buttonBar;

	public double ZOOM = 1.0;
	public double MOVE_X = 0.1;
	public double MOVE_Y = 0.1;
	public InputMenu.Inputs inputs;
	public Runnable generator;
	private WritableImage buffer;

	public Drawer getInstance(){
		if( this.instance == null){
			instance = instance2;		
			return instance2;
		}
		return this.instance;
	}

	public void initialize() {
		this.instance = this;
		instance2 = this;  //le problème était là
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
				this.generator = (Runnable) MainMenu.chosenFractal.constructors.newInstance( pw, startY, startY + blockSize,inputs, MOVE_X, MOVE_Y );
			} catch( Exception e ) {
				e.printStackTrace();
			}
			
			Platform.runLater(this.generator);
			
		}
		this.getInstance().drawingPane.getChildren().add(new ImageView (buffer) );
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
		this.app.transferTo(Utils.GUI.MAIN_MENU);
	}

	@FXML
	private void newDrawer(){
		App z = new App();
		Stage x = new Stage();
		try {
			z.start(x);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	@FXML
	private void onZoomPressed() {
		ZOOM *= 1.5;
		this.instance.draw();
	}

	@FXML
	private void onUnzoomPressed() {
		ZOOM *= 0.5;
		instance.draw();
	}

	@FXML
	private void onMoveUpPressed() {
		if( MainMenu.chosenFractal == Fractal.MANDELBROT ) {
			this.instance.MOVE_Y -= 50 / ZOOM;
		} else {
			this.instance.MOVE_Y -= 0.2 / ZOOM;
		}
		this.instance.draw();
	}

	@FXML
	private void onMoveDownPressed() {
		if( MainMenu.chosenFractal == Fractal.MANDELBROT ) {
			instance.MOVE_Y += 50 / ZOOM;
		} else {
			instance.MOVE_Y += 0.2 / ZOOM;
		}
		this.draw();

	}

	@FXML
	private void onMoveLeftPressed() {
		if( MainMenu.chosenFractal == Fractal.MANDELBROT ) {
			MOVE_X -= 50 / ZOOM;
		} else {
			MOVE_X -= 0.2 / ZOOM;
		}
		instance.draw();
	}

	@FXML
	private void onMoveRightPressed() {
		if( MainMenu.chosenFractal == Fractal.MANDELBROT ) {
			MOVE_X += 50 / ZOOM;
		} else {
			MOVE_X += 0.2 / ZOOM;
		}
		instance.draw();
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

	public void refreshButtonsPosition() {
		//instance.buttonBar.setLayoutX( ( App.mainInstance.primaryStage.getWidth() - instance.buttonBar.getWidth() ) / 2 - 205 );
		//instance.buttonBar.setLayoutY( App.mainInstance.primaryStage.getHeight() - ( 2 * instance.buttonBar.getHeight() ) - 110 );
		instance.buttonBar.setLayoutX( ( this.app.getSecondInstance().primaryStage.getWidth() - instance.buttonBar.getWidth() ) / 2 - 205 );
		instance.buttonBar.setLayoutY( this.app.getSecondInstance().primaryStage.getHeight() - ( 2 * instance.buttonBar.getHeight() ) - 110 );
	}

}
