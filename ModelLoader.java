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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public final class ModelLoader {
	
	/*  ModelLoader.java
	 * 
	 * Loads the geometry from a Wavefront OBJ file into an UnfinishedModel,
	 * then converts it to a Model
	 * 
	 */
	
	public static int scale = 100;

	private static List<Vector> vertices;
	private static UnfinishedModel object;
	
	/* number of vertices that didn't end up being part of a group */
	private static int ungrouped = 0;
	
	public static Model loadObject(String filename) {
		object = new UnfinishedModel();

		vertices = new ArrayList<Vector>();

		ungrouped = 0;
		parseFile(filename);
		/* add the ungrouped vertices */
		if (ungrouped != 0) {
			object.vertices.addAll(vertices);
		}
		System.out.println("num verts: " + object.vertices.size());
		System.out.println("num tris: " + object.triangles.size());
		
		return object.toModel();
	}

	private static Vector getVertex(int index) {
		if (index < 0) {
			index = vertices.size() + index + 1;
		}
		return vertices.get(index - 1);
	}

	private static Vector getVertex(String indexString) {
		return getVertex(Integer.parseInt(indexString));
	}

	private static void parseFile(String filename) {
		try {
			BufferedReader reader = null;
			if (!filename.startsWith("/")) {
				filename = "/" + filename;
			}
			reader = new BufferedReader(new InputStreamReader(ModelLoader.class.getResourceAsStream(filename)));

			while (true) {
				String line = reader.readLine();
				if (line == null) {
					reader.close();
					return;
				}

				line = line.trim();
				/* skip comments and empty lines */
				if (line.length() > 0 && !line.startsWith("#")) {
					parseLine(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void parseLine(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		String cmd = tokenizer.nextToken();
		if (cmd.equals("v")) {
			vertices.add(new Vector(
					Float.parseFloat(tokenizer.nextToken()) * scale,
					Float.parseFloat(tokenizer.nextToken()) * scale,
					Float.parseFloat(tokenizer.nextToken()) * scale));
			ungrouped++;
		} else if (cmd.equals("f")) {
			Vector[] curverts = new Vector[12];
			int index = 0;
			while (tokenizer.hasMoreTokens()) {
				String indexString = tokenizer.nextToken();

				int endIndex = indexString.indexOf('/');
				if (endIndex != -1) {
					indexString = indexString.substring(0, endIndex);
				}
				curverts[index] = getVertex(indexString);
				index++;
			}
			/* triangulate convex polygon */
			for (int i = 2; i < index; i++) {
				int v1i = i - 1;
				int v2i = i;
				object.triangles.add(new Triangle(curverts[0], curverts[v1i], curverts[v2i]));
			}
		} else if (cmd.equals("g")) {
			object.vertices.addAll(vertices);
			ungrouped = 0;
		} else {
			// unsupported command
		}
	}

}
