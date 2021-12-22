package edu.moria.moriaale.fractals;

import edu.moria.moriaale.Complexe;
import edu.moria.moriaale.InputMenu;
import javafx.scene.image.PixelWriter;

import java.awt.*;

public class MandelbrotRunnable implements Runnable{

	private final PixelWriter pw;
	private final int minY;
	private final int maxY;
	private final InputMenu.Inputs inputs;
	private final double MOVE_X;
	private final double MOVE_Y;

	public MandelbrotRunnable( PixelWriter pw, int minY, int maxY, InputMenu.Inputs inputs, double MOVE_X, double MOVE_Y ) {
		this.pw = pw;
		this.minY = minY;
		this.maxY = maxY;
		this.inputs = inputs;
		this.MOVE_X = MOVE_X;
		this.MOVE_Y = MOVE_Y;
	}

	public void run() {

		double x1 = -1.5;
		double y1 = -1.2;

		if( inputs.zoom == 1 ) {
			inputs.zoom = 100;
		}

		Complexe z = new Complexe( 0, 0 );
		Complexe c = new Complexe( 0, 0 );
		for( int y = minY; y < maxY; y++ ) {
			for( int x = 0; x < inputs.maxWidth; x++ ) {
				z.real = 0;
				z.imaginary = 0;
				c.real = (double) x / inputs.zoom + x1 + MOVE_X;
				c.imaginary = (double) y / inputs.zoom + y1 + MOVE_Y;
				int i = 0;
				do {
					double tmp = z.real;
					z.real = z.real * z.real - z.imaginary * z.imaginary + c.real;
					z.imaginary = 2 * z.imaginary * tmp + c.imaginary;
					i = i + 1;
				} while( z.real * z.real + z.imaginary * z.imaginary < 4 && i < inputs.maxIterations );

				if( i == inputs.maxIterations ) { //dessine le pixel de la couleur de la fractale
					pw.setArgb( x, y, Color.HSBtoRGB( (float) i / inputs.maxIterations, 0.7f, 0.7f ) );
				} else { //dessine le pixel de la couleur de remplissage
					pw.setArgb( x, y, Color.HSBtoRGB( (float) i / inputs.maxIterations, 0.3f, 0.3f ) );
				}
			}
		}
	}

}
