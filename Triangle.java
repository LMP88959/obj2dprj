/*****************************************************************************/
/*
 * obj2dprj - Converting Wavefront OBJ file (.obj) geometry
 * 				to a 'Dungeon Project' (.dprj)
 * 				for use in the King's Crook model editor
 * 
 *   by EMMIR 2018-2022
 *   
 *   YouTube: https://www.youtube.com/c/LMP88
 *   
 * This software is released into the public domain.
 */
/*****************************************************************************/

public final class Triangle {
	
	/*  Triangle.java
	 * 
	 * Represents three vertices
	 * 
	 */
	
	public Vector v1, v2, v3;
	
	public Triangle(Vector a, Vector b, Vector c) {
		v1 = a;
		v2 = b;
		v3 = c;
	}

}
