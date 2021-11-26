package edu.moria.moriaale;

public class Complexe {

	public double real;
	public double imaginary;

	public Complexe( double real, double imaginary ) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public Complexe product( Complexe c2 ) {
		double newReal = real * c2.real - imaginary * c2.imaginary;
		double newImaginary = real * c2.imaginary + imaginary * c2.real;
		return new Complexe( newReal, newImaginary );
	}

	public Complexe add( Complexe number ) {
		return new Complexe( this.real + number.real, this.imaginary + number.imaginary );
	}

	public double module() {
		return Math.sqrt( Math.pow( this.real, 2 ) + Math.pow( this.imaginary, 2 ) );
	}

}
