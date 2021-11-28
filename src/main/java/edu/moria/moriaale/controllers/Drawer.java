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


public class Drawer {

	public static Drawer instance;
	public Thread thread;

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
			thread.interrupt();
			drawingPane = null;
		}
		drawingPane = new BorderPane();
		gamePane.getChildren().add( drawingPane );
		WritableImage buffer = new WritableImage( inputs.maxWidth, inputs.maxHeight );
		PixelWriter pw = buffer.getPixelWriter();
		DrawerThread generator = new DrawerThread( pw );

		thread = new Thread( generator );
		thread.setDaemon( true );
		thread.start();

		Drawer.instance.drawingPane.getChildren().add( new ImageView( buffer ) );
	}

	@FXML
	private void onExitPressed() {
		if( thread != null )
			thread.interrupt();
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

		public DrawerThread( PixelWriter pw ) {
			this.pw = pw;
		}

		@Override
		public void run() {
			for( int y = 0; y < inputs.maxHeight; y++ ) {
				for( int x = 0; x < inputs.maxWidth; x++ ) {
					int MAX_ITERATIONS = 1200;
					Complexe c = new Complexe( inputs.CReal, inputs.CImaginary );
					Complexe z = new Complexe(
							  1.5 * ( x - inputs.maxWidth / 2.0 ) / ( 0.5 * ZOOM * inputs.maxWidth ) + MOVE_X,
							  ( y - inputs.maxHeight / 2.0 ) / ( 0.5 * ZOOM * inputs.maxHeight ) + MOVE_Y
					);
					float i = Utils.divergenceIndex( MAX_ITERATIONS, z, c );

					int finalX = x;
					int finalY = y;
					int color = Color.WHITE.getRGB();
					if( i < MAX_ITERATIONS )
						color = Color.HSBtoRGB( ( MAX_ITERATIONS / i ) % 1, 1, 1 );

					final int finalColor = color;
					Platform.runLater( () -> {
						pw.setArgb( finalX, finalY, finalColor );
					} );
				}
			}
		}

	}

}
