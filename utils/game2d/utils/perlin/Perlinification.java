package game2d.utils.perlin;

import game2d.utils.perlin.PerlinNoise.PerlinOctaveChunkData;
import game2d.utils.util.Util;
import game2d.utils.util.Vector2f;
import game2d.utils.util.Vector2v;
import game2d.utils.util.Vectornf;

/**
 * Class of static methods to be used by the {@link PerlinNoise} algorithm
 * @author Gareth Kmet
 *
 */
public final class Perlinification {
	/**
	 * No initializing
	 */
	private Perlinification() {}
	
	/**
	 * Generates and stores the pixel masks for the chunk
	 * @author Gareth Kmet
	 *
	 */
	static final class Masks{
		final Vectornf[][]
				TL, TR,
				BL, BR;
		
		final Vectornf[][][] m = new Vectornf[PerlinNoise.MASKS][][];
		
		/**
		 * Generates empty masks
		 * @param size - {@link PerlinChunk#pixelSize}
		 */
		private Masks(int size){
			TL = new Vectornf[size][size]; m[PerlinNoise.TL]=TL;
			BL = new Vectornf[size][size]; m[PerlinNoise.BL]=BL;
			TR = new Vectornf[size][size]; m[PerlinNoise.TR]=TR;
			BR = new Vectornf[size][size]; m[PerlinNoise.BR]=BR;
		}
		
	}
	
	/**
	 * Runs the perlin algorithm on the chunk
	 * 
	 * <p>This algorithm first dot-products the individual pixels' distance vector and influence vector for each {@link PerlinNoise#MASKS}. 
	 * Then it horizontally lerps the top and bottom masks respectively and then vertically lerps those two new masks together
	 * @param chunk - {@link PerlinChunk}
	 * @return <b><code>Vectornf[][]</code></b> - the float array of the final pixel values
	 */
	static Vectornf[][] perlinAChunk(Vector2v[] invecs, PerlinOctave oct) {
		Masks chunkMask = new Masks(oct.psize());
		for(int i=0; i<PerlinNoise.MASKS; i++) {
			perlinAMask(i, chunkMask, oct, invecs);
		}
		
		Vectornf[][] mT = lerpMs(chunkMask.TL, chunkMask.TR, oct.psize(), true);
		Vectornf[][] mB = lerpMs(chunkMask.BL, chunkMask.BR, oct.psize(), true);
		Vectornf[][] mask = lerpMs(mT, mB, oct.psize(), false);
		
		return mask;
		
	}
	
	/**
	 * Lerps two <code>Vectornf[][]</code> masks together
	 * @param m1 - the first mask
	 * @param m2 - the second mask
	 * @param size - the width of the mask, {@link PerlinChunk#pixelSize}
	 * @param lr - the directionality of the lerp (<code>true</code> for horizontal and <code>false</code> for vertical)
	 * @return <code><b>Vectornf[][]</code></b> - the resulting mask
	 */
	private static Vectornf[][] lerpMs(Vectornf[][] m1, Vectornf[][] m2, int size, boolean lr){
		float psize = 1f/size;
		
		Vectornf[][] mask = new Vectornf[size][size];
		
		for(int x=0;x<size;x++) {
			for(int y=0;y<size;y++) {
				float aProp = (lr?x:y)*psize;
				mask[x][y] = Util.lerps(m1[x][y],m2[x][y], aProp);
			}
		}
		
		return mask;
	}
	
	/**
	 * Runs the dot-product of the pixel distance vector and the influence vector of the chunk respective of a given mask
	 * @param mask - {@link PerlinNoise#MASKS}
	 * @param masks - the chunk's pixel masks
	 * @param chunk - the chunk
	 */
	private static void perlinAMask(int mask, Masks masks, PerlinOctave oct, Vector2v[] invecs) {
		for(int x=0; x<oct.psize(); x++) {
			for(int y=0; y<oct.psize(); y++) {
				Vector2f pixelMaskVector = oct.pixelDistanceVectors()[mask][x][y];
				masks.m[mask][x][y] = Vector2v.dot(invecs[mask], pixelMaskVector);
			}
		}
	}
	
	/**
	 * Overrides the default methods to find an influence vector at a given location
	 * <p>Methods can return <code>null</code> to use the default methods for a chunk
	 * <br>Methods should return the same value for equal spiral indices
	 * @author Gareth Kmet
	 *
	 */
	public interface PerlinInfluenceGenerator{
		/**
		 * Returns an influence for a given chunk corner on the first octave
		 * 
		 * @param seed - the seed that would be used to generate the random index
		 * @param spiralIndex - the unique index of the corner
		 * @param cx - the x position of the chunk
		 * @param cy - the y position of a chunk
		 * @param mask - the {@link PerlinNoise#MASKS}
		 * @return the influence vector to be used for this corner or <code>null</code> to use the default methods
		 */
		public Vectornf perlinMainInfluenceVector(long seed, int spiralIndex, int cx, int cy, int mask);
		
		/**
		 * Returns an influence vector for a given chunk corner on the subsequent octaves
		 * 
		 * @param seed - the seed that would be used to generate the random index
		 * @param spiralIndex - the unique index of the corner
		 * @param mask - the {@link PerlinNoise#MASKS}
		 * @param octData - the {@link PerlinOctaveChunkData} of the octave chunk 
		 * @return the influence vector to be used for this corner or <code>null</code> to use the default methods
		 */
		public default Vectornf perlinOctInfluenceVector(long seed, int spiralIndex, int mask, PerlinNoise.PerlinOctaveChunkData octData) {return null;}
	}
	
}
