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

public class Main {
	
	/*  Main.java
	 * 
	 * The entry point of the program,
	 * interprets command line arguments
	 * 
	 */
	
	private static void usage() {
		System.out.println("usage:");
		System.out.println("obj2dprj <objfile> [options]");
		System.out.println("============================");
		System.out.println("possible options:");
		System.out.println("-s [INT]\t\tspecify scale of model (default " + ModelLoader.scale + ")");
		System.out.println("-t [INT] [INT] [INT]\ttranslate model");
		System.out.println("-u\t\t\tflip model upside-down");
		System.out.println("-b\t\t\tflip model backwards");
		System.out.println("-r\t\t\treverse triangle winding");
		System.out.println("");
		System.out.println("example: obj2dprj model.obj -s 12 -r -t 20 50 100 -b");
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			usage();
			return;
		}
		
		boolean translate = false;
		int tx = 0, ty = 0, tz = 0;
		boolean flipback = false;
		boolean flipupdn = false;
		boolean winding = true;
		
		for (int i = 1; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-s")) {
				if (args.length > (i + 1)) {
					ModelLoader.scale = Integer.parseInt(args[i + 1]);
					i++;
				} else {
					System.err.println("-s needs one integer argument after it!");
				}
			} else if (args[i].equalsIgnoreCase("-u")) {
				flipupdn = true;
			} else if (args[i].equalsIgnoreCase("-b")) {
				flipback = true;
			} else if (args[i].equalsIgnoreCase("-r")) {
				winding = false;
			} else if (args[i].equalsIgnoreCase("-t")) {
				translate = true;
				if (args.length > (i + 1)) {
					tx = Integer.parseInt(args[i + 1]);
					i++;
				} else {
					System.err.println("-t needs three integer arguments after it!");
				}
				if (args.length > (i + 1)) {
					ty = Integer.parseInt(args[i + 1]);
					i++;
				} else {
					System.err.println("-t needs three integer arguments after it!");
				}
				if (args.length > (i + 1)) {
					tz = Integer.parseInt(args[i + 1]);
					i++;
				} else {
					System.err.println("-t needs three integer arguments after it!");
				}
			}
		}
		
		System.out.println("reading: " + args[0]);
		System.out.println("with a scale of " + ModelLoader.scale);

		Model m = ModelLoader.loadObject(args[0]);
		
		if (translate) {
			m.translate(tx, ty, tz);
			System.out.println("translated to " + tx + " " + ty + " " + tz);
		}
		
		if (flipupdn) {
			m.flipUpsideDown();
			System.out.println("flipped upside down");
		}
		
		if (flipback) {
			m.flipBackwards();
			System.out.println("flipped backwards");
		}
		
		m.export(winding);
	}
	
}
