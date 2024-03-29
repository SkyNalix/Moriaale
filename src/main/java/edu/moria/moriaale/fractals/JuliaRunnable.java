package edu.moria.moriaale.fractals;

import edu.moria.moriaale.Complexe;
import edu.moria.moriaale.InputMenu;
import edu.moria.moriaale.Utils;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


public class JuliaRunnable implements Runnable {

	private final PixelWriter pw;
	private final int minY;
	private final int maxY;
	private final InputMenu.Inputs inputs;
	private final double MOVE_X;
	private final double MOVE_Y;
	private final InputMenu.InputsCouleur couleurFond;
	private final InputMenu.InputsCouleur couleurFractal;

	public JuliaRunnable( PixelWriter pw, int minY, int maxY, InputMenu.Inputs inputs, double MOVE_X, double MOVE_Y, InputMenu.InputsCouleur couleurFond, InputMenu.InputsCouleur couleurFractal ) {
		this.pw = pw;
		this.minY = minY;
		this.maxY = maxY;
		this.inputs = inputs;
		this.MOVE_X = MOVE_X;
		this.MOVE_Y = MOVE_Y;
		this.couleurFond = couleurFond;
		this.couleurFractal = couleurFractal;
	}

	@Override
	public void run() {
		for( int y = minY; y < maxY; y++ ) {
			for( int x = 0; x < inputs.maxWidth; x++ ) {
				Complexe c = new Complexe( inputs.CReal, inputs.CImaginary );
				Complexe z = new Complexe(
						  1.5 * ( x - inputs.maxWidth / 2.0 ) / ( 0.5 * inputs.zoom * inputs.maxWidth ) + MOVE_X,
						  ( y - inputs.maxHeight / 2.0 ) / ( 0.5 * inputs.zoom * inputs.maxHeight ) + MOVE_Y
				);
				float i = Utils.divergenceIndex( inputs.maxIterations, z, c );

				Color couleur = Color.hsb( i, 0.7, 0.7 );
				if( couleurFond != null ) {
					if( couleur.getRed() > couleur.getGreen() || couleur.getBlue() > couleur.getGreen() ) {
						//fond d'ecran
						float r = (float) this.couleurFond.r / 255.f;
						float v = (float) this.couleurFond.v / 255.f;
						float b = (float) this.couleurFond.b / 255.f;
						Color couleur2 = new Color( r, v, b, 1.0 );
						pw.setColor( x, y, couleur2 );
					} else {
						float r = (float) this.couleurFractal.r / 255.f;
						float v = (float) this.couleurFractal.v / 255.f;
						float b = (float) this.couleurFractal.b / 255.f;
						Color couleur2 = new Color( r, v, b, 1.0 );
						pw.setColor( x, y, couleur2 );
					}
				} else { //version de base
					int color = java.awt.Color.HSBtoRGB( i / inputs.maxIterations, 0.7f, 0.7f );
					pw.setArgb( x, y, color );
				}
			}
		}
	}

}
