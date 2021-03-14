package sample;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class functions {
    static byte[][] H = {
            {1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0},
            {1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1}};


    static byte[][] wordtobinary(String word) {

        byte[][] binary = new byte[word.length()][8];
        char letter, n;
        String binarytext;

        for ( int i = 0; i < word.length(); i++ ) {
            letter = word.charAt(i);
            binarytext = Integer.toBinaryString(letter);
            if (binarytext.length() != 8) {
                do {
                    binarytext = "0" + binarytext;
                } while (binarytext.length() != 8);
            }

            for ( int j = 0; j < 8; j++ ) {
                n = binarytext.charAt(j);
                if (n == 48) binary[i][j] = 0;
                else binary[i][j] = 1;
            }

        }
        return binary;
    }

    static byte[][] addParityBit(byte[][] binary) {
        byte[][] parityBits = new byte[binary.length][8];
        int result = 0;
        for ( int letter = 0; letter < binary.length; letter++ ) {
            for ( int i = 0; i < 8; i++ ) {
                for ( int j = 0; j < binary[0].length; j++ ) {
                    result += binary[letter][j] * H[i][j];

                }
                if (result % 2 == 0) parityBits[letter][i] = 0;
                else parityBits[letter][i] = 1;
                result = 0;
            }
        }

        return parityBits;
    }


    static byte[][] connect(byte[][] binary, byte[][] parityBits) {
        byte[][] connected = new byte[binary.length][16];
        for ( int i = 0; i < binary.length; i++ ) {
            for ( int j = 0; j < 16; j++ ) {
                if (j < 8) connected[i][j] = binary[i][j];
                else connected[i][j] = parityBits[i][j - 8];
            }
        }
        return connected;
    }

    static String findError(byte[][] binary) {
        byte[][] connected = new byte[binary.length][16];

        String result = "", tmp;
        for ( int letter = 0; letter < binary.length; letter++ ) {
            tmp = findErrorInOneLetter(binary[letter]);
            if (!tmp.equals("")) {
                result = "Letter " + letter + ": " + tmp + "\n";
                tmp = "";
            }
        }


        return result;
    }

    /**
     * @param binary - single letter in binary
     *               Returns string with information where was the error found
     *               returns empty string if there are no errors
     */
    static private String findErrorInOneLetter(byte[] binary) {
        //Comparing result with columns, if column matches binary then the
        // error is on the place which is equivalent to the column number
        boolean isCorrect = false;
        boolean isRightColumn = false;
        for ( byte b : binary ) {
            isCorrect = true;
            if (b != 0) {
                isCorrect = false;
                break;
            }
        }
        if (!isCorrect) {
            for ( int i = 0; i < H[0].length; i++ ) {
                // kazda kolumna
                for ( int j = 0; j < binary.length; j++ ) {
                    isRightColumn = true;
                    if (binary[j] != H[j][i]) {
                        isRightColumn = false;
                        break;
                    }
                }
                if (isRightColumn) {
                    return "Single bit error found in bit " + i;
                }
            }
            // if nothing was returned it means that there is more than 1 error
            // Checking the sums of columns to see if 2 sums of columns equal our binary array
            for ( int i = 0; i < H[0].length; i++ ) {
                for ( int j = i + 1; j < H[0].length; j++ ) {
                    for ( int k = 0; k < binary.length; k++ ) {
                        isRightColumn = true;
                        if (binary[k] != ((H[k][j] + H[k][i]) % 2)) {
                            isRightColumn = false;
                            break;
                        }
                    }
                    if (isRightColumn) {
                        return "Double bit error found in bits " + i + " and " + j;
                    }

                }
            }
            // We can only detect triple bit errors but we cannot fix them
            return "Triple bit error found, cannot be fixed";
        }

        return "";
    }
}
