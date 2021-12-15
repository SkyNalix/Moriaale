package edu.moria.moriaale.controllers;

import edu.moria.moriaale.App;
import edu.moria.moriaale.Complexe;
import edu.moria.moriaale.InputMenu;
import edu.moria.moriaale.Utils;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Drawer {

	public static Drawer instance;
	public ArrayList<Thread> threads = new ArrayList<>();

	public Pane gamePane;
	public BorderPane drawingPane;
	public ButtonBar buttonBar;

	public double ZOOM = 1.0;
	public double MOVE_X = 0.1;
	public double MOVE_Y = 0.1;
	public InputMenu.Inputs inputs;
	private WritableImage buffer;


	public void initialize() {
		Drawer.instance = this;
		inputs = InputMenu.showDialog();
		if( inputs == null ) Platform.runLater( () -> App.transferTo( Utils.GUI.MAIN_MENU ) );
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

		if( inputs == null ) {
			Platform.runLater( () -> App.transferTo( Utils.GUI.MAIN_MENU ) );
			return;
		}
		buffer = new WritableImage( inputs.maxWidth, inputs.maxHeight );
		PixelWriter pw = buffer.getPixelWriter();

		int nbBlock = 4;
		int blockSize = ( inputs.maxHeight / nbBlock );
		for( int y = 0; y < nbBlock; y++ ) {
			int startY = y * blockSize;
			DrawerThread generator = new DrawerThread( pw, startY, startY + blockSize );
			Platform.runLater( generator );
		}
		Drawer.instance.drawingPane.getChildren().add( new ImageView( buffer ) );

	}

	@FXML
	private void onToImagePressed() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter( "PNG", "*.png" ) );

		File file = fileChooser.showSaveDialog( App.mainInstance.primaryStage );

		if( file != null ) {
			String fileName = file.getName();

			if( !fileName.toUpperCase().endsWith( ".PNG" ) ) {
				file = new File( file.getAbsolutePath() + ".png" );
			}
			try {
				ImageIO.write( SwingFXUtils.fromFXImage( buffer, null ),
							   "png", file );
			} catch( IOException e ) {
				e.printStackTrace();
				System.out.println( "An error occurred while trying to save the image" );
			}
		}

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

	@FXML
	private void onMoveUpPressed() {
		MOVE_Y -= 0.2 / ZOOM;
		draw();
	}

	@FXML
	private void onMoveDownPressed() {
		MOVE_Y += 0.2 / ZOOM;
		draw();
	}

	@FXML
	private void onMoveLeftPressed() {
		MOVE_X -= 0.2 / ZOOM;
		draw();
	}

	@FXML
	private void onMoveRightPressed() {
		MOVE_X += 0.2 / ZOOM;
		draw();
	}

	public void refreshButtonsPosition() {
		buttonBar.setLayoutX( ( App.mainInstance.primaryStage.getWidth() - buttonBar.getWidth() ) / 2 - 195 );
		buttonBar.setLayoutY( App.mainInstance.primaryStage.getHeight() - ( 2 * buttonBar.getHeight() ) - 110 );
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

					int color = Color.HSBtoRGB( i / MAX_ITERATIONS, 0.7f, 0.7f );

					pw.setArgb( x, y, color );
				}
			}
		}

	}

}
