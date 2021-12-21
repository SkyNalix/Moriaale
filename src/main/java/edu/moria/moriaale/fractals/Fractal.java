package edu.moria.moriaale.fractals;

import java.lang.reflect.Constructor;

public enum Fractal {

	JULIA( "Julia", JuliaRunnable.class.getConstructors()[0]),
	MANDELBROT( "Mandelbrot", MandelbrotRunnable.class.getConstructors()[0] );

	public final String name;
	public final Constructor<?> constructors;

	Fractal( String name, Constructor<?> constructors ) {
		this.name = name;
		this.constructors = constructors;
	}

}
