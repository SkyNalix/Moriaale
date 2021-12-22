package edu.moria.moriaale.fractals;

import edu.moria.moriaale.Complexe;
import edu.moria.moriaale.InputMenu;
import edu.moria.moriaale.Utils;
import javafx.scene.image.PixelWriter;

import java.awt.*;

public class JuliaRunnable implements Runnable {
	private final PixelWriter pw;
	private final int minY;
	private final int maxY;
	private final InputMenu.Inputs inputs;
	private final double MOVE_X;
	private final double MOVE_Y;

	public JuliaRunnable( PixelWriter pw, int minY, int maxY, InputMenu.Inputs inputs, double MOVE_X,double MOVE_Y ) {
		this.pw = pw;
		this.minY = minY;
		this.maxY = maxY;
		this.inputs = inputs;
		this.MOVE_X = MOVE_X;
		this.MOVE_Y = MOVE_Y;
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

				int color = Color.HSBtoRGB( i / inputs.maxIterations, 0.7f, 0.7f );

				pw.setArgb( x, y, color );
			}
		}
	}
}
