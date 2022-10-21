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

public final class Vector implements Cloneable {

	/*  Vector.java
	 * 
	 * Represents a 3D vector
	 * 
	 */
	
	public float x;
	public float y;
	public float z;

	public Vector() {
		this(0, 0, 0);
	}

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}
	
	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(Vector v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}

	public void setTo(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setTo(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void setTo(Vector v) {
		this.x = v.getX();
		this.y = v.getY();
		this.z = v.getZ();
	}
	
	public void zero() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public void negate() {
		this.x = -x;
		this.y = -y;
		this.z = -z;
	}
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + Float.floatToIntBits(this.x);
		hash = 59 * hash + Float.floatToIntBits(this.y);
		hash = 59 * hash + Float.floatToIntBits(this.z);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Vector other = (Vector) obj;
		return Float.floatToIntBits(this.x) == Float.floatToIntBits(other.x) && Float.floatToIntBits(this.y) == Float.floatToIntBits(other.y) && Float.floatToIntBits(this.z) == Float.floatToIntBits(other.z);
	}

	@Override
	public Vector clone() {
		return new Vector(x, y, z);
	}

	public void print() {
		System.out.println(this.toString());
	}
	
	@Override
	public String toString() {
		return String.format("%.4f, %.4f, %.4f", x, y, z);
	}
	
}
