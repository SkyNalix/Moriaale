package edu.moria.moriaale;


import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InputMenu {

	public static Inputs showDialog() {
		Dialog<Inputs> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll( ButtonType.OK, ButtonType.CANCEL );
		List<Node> entries = new ArrayList<>();
		entries.add( new Label( "Constant's real" ) );
		entries.add( new TextField("0.285") );
		entries.add( new Label( "Constant's imaginary" ) );
		entries.add( new TextField( "0.01" ) );
		entries.add( new Label( "Max Width" ) );
		entries.add( new TextField( "500" ) );
		entries.add( new Label( "Max Height" ) );
		entries.add( new TextField( "500"  ) );
		entries.add( new Label( "Zoom multiplier" ) );
		entries.add( new TextField( "1.0" ) );
		entries.add( new Label( "Max iterations" ) );
		entries.add( new TextField( "1200" ) );

		VBox vbox = new VBox();
		for( Node entry : entries )
			vbox.getChildren().add( entry );
		dialogPane.setContent( vbox );

		dialog.setResultConverter( ( ButtonType button ) -> {
			if( button == ButtonType.OK ) {
				try {
					return new Inputs(
							  Float.parseFloat( ( (TextField) entries.get( 1 ) ).getText() ),
							  Float.parseFloat( ( (TextField) entries.get( 3 ) ).getText() ),
							  Integer.parseInt( ( (TextField) entries.get( 5 ) ).getText() ),
							  Integer.parseInt( ( (TextField) entries.get( 7 ) ).getText() ),
							  Double.parseDouble( ( (TextField) entries.get( 9 ) ).getText() ),
							  Integer.parseInt( ( (TextField) entries.get( 11 ) ).getText() )
					);
				} catch( Exception e ) {
					return null;
				}
			}
			return null;
		} );
		Optional<Inputs> optionalResult = dialog.showAndWait();
		return optionalResult.orElse( null );
	}

	public static class Inputs {

		public double CReal;
		public double CImaginary;
		public int maxWidth;
		public int maxHeight;
		public double zoom;
		public int maxIterations;

		public Inputs( double CReal, double CImaginary, int maxWidth, int maxHeight, double zoom, int maxIterations) {
			this.CReal = CReal;
			this.CImaginary = CImaginary;
			this.maxWidth = maxWidth;
			this.maxHeight = maxHeight;
			this.zoom = zoom;
			this.maxIterations = maxIterations;
		}

	}

}
