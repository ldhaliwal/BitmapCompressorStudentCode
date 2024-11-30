/******************************************************************************
 *  Compilation:  javac BitmapCompressor.java
 *  Execution:    java BitmapCompressor - < input.bin   (compress)
 *  Execution:    java BitmapCompressor + < input.bin   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   q32x48.bin
 *                q64x96.bin
 *                mystery.bin
 *
 *  Compress or expand binary input from standard input.
 *
 *  % java DumpBinary 0 < mystery.bin
 *  8000 bits
 *
 *  % java BitmapCompressor - < mystery.bin | java DumpBinary 0
 *  1240 bits
 ******************************************************************************/

import java.util.ArrayList;

/**
 *  The {@code BitmapCompressor} class provides static methods for compressing
 *  and expanding a binary bitmap input.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @author Zach Blick
 *  @author Liliana Dhaliwal
 */
public class BitmapCompressor {

    /**
     * Reads a sequence of bits from standard input, compresses them,
     * and writes the results to standard output.
     */
    public static void compress() {
        int count = 0;
        int bit, currentBit = 0;

        // Cycles through each number in the input file
        while(!BinaryStdIn.isEmpty()){
            bit = BinaryStdIn.readInt(1);

            if(bit == currentBit){
                // Checks if we've found a run longer than our allotted amount of space
                if (count == 255) {
                    // If so, write out the count, write that there is 0 of the next bit, and continue to count
                    BinaryStdOut.write(count, 8);
                    count = 0;
                    BinaryStdOut.write(count, 8);
                }
            }
            // Writes out the count of a given digit, and update the current bit
            else {
                BinaryStdOut.write(count, 8);
                count = 0;
                // Switches the value of the current bit (all credit to Kate Little for this super quick way of doing it!)
                currentBit = 1 - currentBit;
            }
            count++;
        }

        // Writes out the final count
        BinaryStdOut.write(count, 8);
        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {
        int bit = 0;

        // Reads through the input file
        while (!BinaryStdIn.isEmpty()) {
            int length = BinaryStdIn.readInt(8);

            // Cycles through a given count and writes out that many of a bit
            for (int i = 0; i < length; i++) {
                BinaryStdOut.write(bit, 1);
            }

            // Updates the value of the bit that is being written out
            bit = 1 - bit;
        }
        BinaryStdOut.close();
    }

    /**
     * When executed at the command-line, run {@code compress()} if the command-line
     * argument is "-" and {@code expand()} if it is "+".
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}