package edu.moria.moriaale;

import edu.moria.moriaale.fractals.Fractal;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.io.File;

public class MainClass {

	public static void usage() {
		System.out.println(
				  "Usage : ./gradlew run --args=\" your parameters here \"\n" +
				  "Possible parameters:\n" +
				  "   -h, --help\n" +
				  "          Show this text.\n" +
				  "   -draw\n" +
				  "          Draw a fractal with default values.\n" +
				  "   --type\n" +
				  "          Use a specified fractal type.\n" +
				  "          Default: julia\n" +
				  "          Exemple : --type mandelbrot\n" +
				  "   --height\n" +
				  "          Use a specified height.\n" +
				  "          Default: 500 pixels\n" +
				  "          Exemple : --height 1000\n" +
				  "   --width\n" +
				  "          Use a specified width.\n" +
				  "          Default: 500\n" +
				  "          Exemple : --width 1000\n" +
				  "   --real\n" +
				  "          Set the used real part of the constant in a Julia Set\n" +
				  "          Default: 0.285\n" +
				  "          Exemple : --real -0.70176\n" +
				  "   --imaginary\n" +
				  "          Set the used imaginary part of the constant in a Julia Set.\n" +
				  "          Default: 0.01\n" +
				  "          Exemple : --imaginary -0.3842\n" +
				  "   --zoom\n" +
				  "          Change the zoom multiplier.\n" +
				  "          Default: 1.0\n" +
				  "          Exemple : --zoom 1.5 \n" +
				  "   --moveX\n" +
				  "          Move the fractal image on the horizontal line.\n" +
				  "          Default: 0\n" +
				  "          Exemple : --moveX 0.5  \n" +
				  "   --moveY\n" +
				  "          Move the fractal image on the vertical line.\n" +
				  "          Default: 0\n" +
				  "          Exemple : --moveY 0.5 \n" +
				  "   --iterations\n" +
				  "          Change the amount of iterations used for each pixel.\n" +
				  "          Default: 1200\n" +
				  "          Exemple : --iterations 200 \n" +
				  "   --path\n" +
				  "          Use the specified path where save the result png.\n" +
				  "          Default: result.png\n" +
				  "          Exemple : --path directory/test.png\n" +
				  "Exemples :\n" +
				  "   ./gradlew run --args=\" --type julia --path tests" + File.separator + "result.png \"\n" +
				  "   ./gradlew run --args=\" --type mandelbrot --path tests" + File.separator + "result.png \"\n" +
				  "   ./gradlew run --args=\" --path tests" + File.separator + "result.png \"\n" +
				  "   ./gradlew run --args=\" --height 1000 --width 1000 \"\n" +
				  "   ./gradlew run --args=\" --zoom 1.5 --moveX 0.4 --moveY 0.4 \""
						  );
	}

	public static void main( String[] args ) {
		if( args.length == 0 )
			App.main( args );
		else {
			Fractal fractalType = Fractal.JULIA;
			InputMenu.Inputs inputs = new InputMenu.Inputs( 0.285, 0.01, 500, 500, 1.0, 1200 );
			double MOVE_Y = 0;
			double MOVE_X = 0;
			String savePath = "result.png";

			int readIndex = 0;
			try {
				while( readIndex < args.length && ( args[readIndex].equals( "-h" ) || args[readIndex].equals( "--help" )
													|| readIndex < args.length - 1 ) ) {
					switch( args[readIndex] ) {
						case "-h":
						case "--help": {
							usage();
							return;
						}
						case "--type": {
							try {
								fractalType = Fractal.valueOf( args[readIndex + 1].toUpperCase() );
							} catch( IllegalArgumentException e ) {
								System.out.println( "Fractal type \"" + args[readIndex + 1] + "\" don't exist." );
								System.exit( 1 );
							}
							break;
						}
						case "--height": {
							inputs.maxHeight = Integer.parseInt( args[readIndex + 1] );
							if( inputs.maxHeight < 0 )
								numberFormatExceptionHandler();
							break;
						}
						case "--width": {
							inputs.maxWidth = Integer.parseInt( args[readIndex + 1] );
							if( inputs.maxWidth < 0 )
								numberFormatExceptionHandler();
						}
						break;
						case "--real": {
							inputs.CReal = Double.parseDouble( args[readIndex + 1] );
							break;
						}
						case "--imaginary": {
							inputs.CImaginary = Double.parseDouble( args[readIndex + 1] );
							break;
						}
						case "--zoom": {
							inputs.zoom = Double.parseDouble( args[readIndex + 1] );
							if( inputs.zoom < 0 )
								numberFormatExceptionHandler();
							break;
						}
						case "--moveX": {
							MOVE_X = Double.parseDouble( args[readIndex + 1] );
						}
						break;
						case "--moveY": {
							MOVE_Y = Double.parseDouble( args[readIndex + 1] );
						}
						case "--iterations": {
							inputs.maxIterations = Integer.parseInt( args[readIndex + 1] );
						}
						break;
						case "--path": {
							savePath = args[readIndex + 1];
							readIndex++;
							while( readIndex + 1 < args.length && !args[readIndex + 1].startsWith( "-" ) ) {
								savePath += " " + args[readIndex + 1];
								readIndex++;
							}
							break;
						}
						default:
							break;
					}
					readIndex++;
				}
			} catch( NumberFormatException e ) {
				numberFormatExceptionHandler();
			}

			WritableImage writableImage = new WritableImage( inputs.maxWidth, inputs.maxHeight );
			PixelWriter pw = writableImage.getPixelWriter();

			int nbBlock = 4;
			int blockSize = ( inputs.maxHeight / nbBlock );
			for( int y = 0; y < nbBlock; y++ ) {
				int startY = y * blockSize;

				try {
					Runnable generator = (Runnable) fractalType.constructors.newInstance( pw, startY, startY + blockSize, inputs, MOVE_X, MOVE_Y );
					Thread drawerThread = new Thread( generator );
					drawerThread.start();
					drawerThread.join();
				} catch( Exception e ) {
					e.printStackTrace();
				}
			}
			String separator = File.separator.replace( "\\", "\\\\" );
			savePath = savePath.replaceAll( "\\\\", separator );
			savePath = savePath.replaceAll( "/", separator );
			if( savePath.endsWith( separator ) )
				savePath += "result.png";
			else if( !savePath.toLowerCase().endsWith( ".png" ) )
				savePath += ".png";
			File file = new File( savePath );
			if( file.exists() )
				file.delete();
			if( file.mkdirs() ) {
				Utils.saveWritableImage( writableImage, file );
			} else {
				System.out.println( "An error occured while creating directories." );
				System.exit( 1 );
			}
		}
	}

	private static void numberFormatExceptionHandler() {
		System.out.println( "One of the arguments is an invalid number" );
		System.exit( 1 );
	}

}
