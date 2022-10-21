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

import java.util.ArrayList;
import java.util.List;

public final class UnfinishedModel {
	
	/*  UnfinishedModel.java
	 * 
	 * Represents a collection of vertices and triangles that have yet to be
	 * converted to a model with indexed geometry
	 * 
	 */
	
	public List<Vector> vertices;
	public List<Triangle> triangles;

	public UnfinishedModel() {
		vertices = new ArrayList<Vector>();
		triangles = new ArrayList<Triangle>();
	}

	public Model toModel() {
		Model model = new Model();
		model.setVertexCount(vertices.size());
		model.setTriangleCount(triangles.size());

		for (int v = 0; v < model.vertexCount; v++) {
			model.setVertex(v,
					(int) (vertices.get(v).getX()),
					(int) (vertices.get(v).getY()),
					(int) (vertices.get(v).getZ()));
		}

		for (int t = 0; t < model.triangleCount; t++) {
			model.setTriangle(t,
					vertices.indexOf(triangles.get(t).v1),
					vertices.indexOf(triangles.get(t).v2),
					vertices.indexOf(triangles.get(t).v3));
		}
		
		return model;
	}

}
