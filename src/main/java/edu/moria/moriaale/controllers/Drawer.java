package edu.moria.moriaale.controllers;

import edu.moria.moriaale.App;
import edu.moria.moriaale.Complexe;
import edu.moria.moriaale.InputMenu;
import edu.moria.moriaale.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;


public class Drawer {

	public static Drawer instance;
	public ArrayList<Thread> threads = new ArrayList<>();

	public Pane gamePane;
	public BorderPane drawingPane;
	public ButtonBar buttonBar;

	public double ZOOM = 1.0;
	public double MOVE_X = 0;
	public double MOVE_Y = 0;
	public InputMenu.Inputs inputs;


	public void initialize() {
		Drawer.instance = this;
		inputs = InputMenu.showDialog();
		if( inputs == null )
			onExitPressed();
		draw();
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
		WritableImage buffer = new WritableImage( inputs.maxWidth, inputs.maxHeight );
		PixelWriter pw = buffer.getPixelWriter();

		int nbBlock = 3;
		int blockSize = ( inputs.maxHeight / nbBlock );
		for( int y = 0; y < nbBlock; y++ ) {
			int startY = y * blockSize;
			DrawerThread generator = new DrawerThread( pw, startY, startY + blockSize );
			Platform.runLater( generator );
		}

		Drawer.instance.drawingPane.getChildren().add( new ImageView( buffer ) );
	}

	@FXML
	private void onExitPressed() {
		for( Thread thread : threads ) {
			thread.interrupt();
			threads.clear();
		}
		App.transferTo( Utils.GUI.MAIN_MENU );
	}

	@FXML
	private void onZoomPressed() {
		ZOOM *= 1.5;
		draw();
	}

	@FXML
	private void onUnzoomPressed() {
		ZOOM *= 0.5;
		draw();
	}

	public void refreshButtonsPosition() {
		buttonBar.setLayoutX( ( App.mainInstance.primaryStage.getWidth() - buttonBar.getWidth() ) / 2 - 132.5 );
		buttonBar.setLayoutY( App.mainInstance.primaryStage.getHeight() - ( 2 * buttonBar.getHeight() ) - 89 );
	}

	public class DrawerThread implements Runnable {

		private final PixelWriter pw;
		private final int minY;
		private final int maxY;

		public DrawerThread( PixelWriter pw, int minY, int maxY ) {
			this.pw = pw;
			this.minY = minY;
			this.maxY = maxY;
		}

		@Override
		public void run() {
			for( int y = minY; y < maxY; y++ ) {
				for( int x = 0; x < inputs.maxWidth; x++ ) {
					int MAX_ITERATIONS = 1200;
					Complexe c = new Complexe( inputs.CReal, inputs.CImaginary );
					Complexe z = new Complexe(
							  1.5 * ( x - inputs.maxWidth / 2.0 ) / ( 0.5 * ZOOM * inputs.maxWidth ) + MOVE_X,
							  ( y - inputs.maxHeight / 2.0 ) / ( 0.5 * ZOOM * inputs.maxHeight ) + MOVE_Y
					);
					float i = Utils.divergenceIndex( MAX_ITERATIONS, z, c );

					int color = Color.HSBtoRGB((float)i/MAX_ITERATIONS, 0.7f, 0.7f);

					int finalX = x;
					int finalY = y;
					final int finalColor = color;
					pw.setArgb( finalX, finalY, finalColor );
				}
			}
		}

	}

}
