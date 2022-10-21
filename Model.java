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

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class Model {

	/*  Model.java
	 * 
	 * Represents a set of vertices and triangles that reference them
	 * 
	 */
	
	public int vertexCount;

	public int[] vertexX;
	public int[] vertexY;
	public int[] vertexZ;

	public int triangleCount;

	public int[] triangleVertexA;
	public int[] triangleVertexB;
	public int[] triangleVertexC;

	public Model() {

	}

	public void setVertexCount(int n) {
		this.vertexCount = n;
		this.vertexX = new int[n];
		this.vertexY = new int[n];
		this.vertexZ = new int[n];
	}

	public void setTriangleCount(int n) {
		this.triangleCount = n;
		this.triangleVertexA = new int[n];
		this.triangleVertexB = new int[n];
		this.triangleVertexC = new int[n];
	}

	public int setVertex(int index, int x, int y, int z) {
		vertexX[index] = x;
		vertexY[index] = y;
		vertexZ[index] = z;
		return index;
	}

	public int setTriangle(int index, int a, int b, int c) {
		triangleVertexA[index] = a;
		triangleVertexB[index] = b;
		triangleVertexC[index] = c;
		return index;
	}

	public void translate(int x, int y, int z) {
		for (int v = 0; v < this.vertexCount; v++) {
			this.vertexX[v] += x;
			this.vertexY[v] += y;
			this.vertexZ[v] += z;
		}
	}

	public void flipBackwards() {
		for (int v = 0; v < vertexCount; v++) {
			vertexX[v] = -vertexX[v];
			vertexZ[v] = -vertexZ[v];
		}
	}

	public void flipUpsideDown() {
		for (int v = 0; v < vertexCount; v++) {
			vertexX[v] = -vertexX[v];
			vertexY[v] = -vertexY[v];
		}
	}

	private static final int MAX_TRIANGLES = 2048;
	private static final int MAX_GROUP = 64;

	public void export(boolean normal_winding) {
		String out = "";
		int tcount = triangleCount;
		for (int i = 0; i < triangleCount; i++) {
			int a, b, c;
			int x, y, z;

			out += "0 1 1 16777215 0\n";
			a = triangleVertexA[i];
			b = triangleVertexB[i];
			c = triangleVertexC[i];
			if (!normal_winding) {
				int t = a;
				a = c;
				c = t;
			}
			x = vertexX[c];
			y = vertexY[c];
			z = vertexZ[c];
			out += x + " " + y + " " + z + "\n";
			x = vertexX[b];
			y = vertexY[b];
			z = vertexZ[b];
			out += x + " " + y + " " + z + "\n";
			x = vertexX[a];
			y = vertexY[a];
			z = vertexZ[a];
			out += x + " " + y + " " + z + "\n";
		}
		for (int i = 0; i < MAX_TRIANGLES - tcount; i++) {
			out += "0 0 0 0 0\n";
			out += "0 0 0\n";
			out += "0 0 0\n";
			out += "0 0 0\n";
		}
		out += tcount + " " + vertexCount + " 0 0 1\n";
		for (int i = 0; i < MAX_GROUP; i++) {
			out += "0 0 0\n";
		}
		out += "16777215 0\n";

		String title = "Save file as:";
		FileDialog fd = new FileDialog((Frame) null, title, FileDialog.SAVE);
		fd.setVisible(true);
		if (fd.getFile() == null) {
			return;
		}
		
		try {
			FileOutputStream fout;
			PrintStream pout;
			
			String filePath = fd.getDirectory();
			String fileName = fd.getFile();
			String fileDesc = filePath + fileName;
			
			fout = new FileOutputStream(new File(fileDesc));
			pout = new PrintStream(new BufferedOutputStream(fout), true);
			pout.print(out);
			pout.flush();
			pout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
