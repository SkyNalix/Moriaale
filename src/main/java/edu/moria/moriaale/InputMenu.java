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
		entries.add( new TextField( String.valueOf( 0.285 ) ) );
		entries.add( new Label( "Constant's imaginary" ) );
		entries.add( new TextField( String.valueOf( 0.01 ) ) );
		entries.add( new Label( "Max Width" ) );
		entries.add( new TextField( String.valueOf( 500 ) ) );
		entries.add( new Label( "Max Height" ) );
		entries.add( new TextField( String.valueOf( 500 ) ) );

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
							  Integer.parseInt( ( (TextField) entries.get( 7 ) ).getText() )
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

	public static ArrayList<InputsCouleur> showDialogCouleur() {
		ArrayList<InputsCouleur> listeInputCouleur = new ArrayList<>(); 

		Dialog<ArrayList<InputsCouleur>> dialog = new Dialog<>();
		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.getButtonTypes().addAll( ButtonType.OK, ButtonType.CANCEL );
		List<Node> entries = new ArrayList<>();
		entries.add( new Label( "Couleur Rouge du Fond" ) );
		entries.add( new TextField( String.valueOf( 0 ) ) );
		entries.add( new Label( "Couleur Verte du Fond" ) );
		entries.add( new TextField( String.valueOf( 0 ) ) );
		entries.add( new Label( "Couleur Bleu du Fond" ) );
		entries.add( new TextField( String.valueOf( 0 ) ) );
		entries.add( new Label( "Couleur Rouge de la Fractale" ) );
		entries.add( new TextField( String.valueOf( 100 ) ) );
		entries.add( new Label( "Couleur Verte de la Fractale" ) );
		entries.add( new TextField( String.valueOf( 100 ) ) );
		entries.add( new Label( "Couleur Bleu de la Fractale" ) );
		entries.add( new TextField( String.valueOf( 180 ) ) );

		VBox vbox = new VBox();
		for( Node entry : entries )
			vbox.getChildren().add( entry );
		dialogPane.setContent( vbox );

		dialog.setResultConverter( ( ButtonType button ) -> {
			if( button == ButtonType.OK ) {
				try {
					listeInputCouleur.add(new InputsCouleur(
							  Integer.parseInt( ( (TextField) entries.get( 1 ) ).getText() ),
							  Integer.parseInt( ( (TextField) entries.get( 3 ) ).getText() ),
							  Integer.parseInt( ( (TextField) entries.get( 5 ) ).getText() )
					) );
					listeInputCouleur.add(new InputsCouleur(
						Integer.parseInt( ( (TextField) entries.get( 7 ) ).getText() ),
						Integer.parseInt( ( (TextField) entries.get( 9 ) ).getText() ),
						Integer.parseInt( ( (TextField) entries.get( 11 ) ).getText() )
			  ) );
			  return listeInputCouleur;
				} catch( Exception e ) {
					return null;
				}
			}
			return null;
		} );
		Optional<ArrayList<InputsCouleur>> optionalResult = dialog.showAndWait();
		
		return optionalResult.orElse( null );
	}

	public static class InputsCouleur {
		public int r;
		public int v;
		public int b;

		public InputsCouleur(int r,int v,int b){
			this.r =r ;
			this.v = v;
			this.b = b;
			if(this.r < 0){this.r = 0;} else if(this.r > 255){this.r = 255;} 
			if(this.b < 0){this.b = 0;}else if (this.b > 255){this.b  = 255;}
			if(this.v < 0){this.v = 0;}else if(this.v > 255){this.v = 255;}
		}

	}

	public static class Inputs {

		public double CReal;
		public double CImaginary;
		public int maxWidth;
		public int maxHeight;

		public Inputs( double CReal, double CImaginary, int maxWidth, int maxHeight ) {
			this.CReal = CReal;
			this.CImaginary = CImaginary;
			this.maxWidth = maxWidth;
			this.maxHeight = maxHeight;
		}

	}

}
