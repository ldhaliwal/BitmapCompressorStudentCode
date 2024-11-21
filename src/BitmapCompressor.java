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
        ArrayList<Integer> data = new ArrayList<>();

        while(!BinaryStdIn.isEmpty()){
            data.add(BinaryStdIn.readInt(1));
        }

        int currentBit = data.get(0);
        int count = 0;

        for (int bit : data) {
            if (bit == currentBit) {
                count++;
            }
            else {
                BinaryStdOut.write(currentBit, 1);
                BinaryStdOut.write(count, 8);
                currentBit = bit;
                count = 1;
            }
        }

        BinaryStdOut.close();
    }

    /**
     * Reads a sequence of bits from standard input, decodes it,
     * and writes the results to standard output.
     */
    public static void expand() {
        while (!BinaryStdIn.isEmpty()) {
            int bit = BinaryStdIn.readInt(1);
            int length = BinaryStdIn.readInt(8);

            for (int i = 0; i < length; i++) {
                BinaryStdOut.write(bit, 1);
            }
        }

        BinaryStdOut.close();

//        ArrayList<Integer> data = new ArrayList<>();
//
//        while(!BinaryStdIn.isEmpty()){
//            data.add(BinaryStdIn.readInt(1));
//        }
//
//        for(int i = 0; i < data.size(); i++){
//            int bit = data.get(i);
//            int bitCount = data.get(i + 1);
//
//            // Write the bit `count` times
//            for (int j = 0; j < bitCount; j++) {
//                BinaryStdOut.write(bit, 1);
//            }
//            i++;
//        }
//
//        BinaryStdOut.close();
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