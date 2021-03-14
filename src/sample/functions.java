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
        String binarytext = "";

        for (int i = 0; i < word.length(); i++) {
            letter = word.charAt(i);
            binarytext = Integer.toBinaryString(letter);
            if (binarytext.length() != 8) {
                do {
                    binarytext = "0" + binarytext;
                } while (binarytext.length() != 8);
            }

            for (int j = 0; j < 8; j++) {
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
        for (int letter = 0; letter < binary.length; letter++) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    //  System.out.println("binarnie: " +binary[letter][j] +" tablica: "+ H[i][j]);
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
        for (int i = 0; i < binary.length; i++) {
            for (int j = 0; j < 16; j++) {
                if (j < 8) connected[i][j] = binary[i][j];
                else connected[i][j] = parityBits[i][j - 8];
            }
        }
        return connected;
    }

    static byte[][] check(byte[][] binary) {
          byte[][] results ;
//        int x = 0;
//        for (int i = 0; i < code.length; i++) {
//            for (int j = 0; j < 16; j++) {
//                results[i][j] += code[i][j] * H[i][j];
//            }
////        }
//        int result=0;
//
//        for (int letter = 0; letter < binary.length; letter++) {
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    System.out.println("binarnie: " +binary[letter][j] +" tablica: "+ H[i][j]);
//                    result += binary[letter][j] * H[i][j];
//
//                }
//                results[letter][i] = (byte)result;
//                result = 0;
//                System.out.println("end");
//            }
//        }

        results=addParityBit(binary);
        results=addParityBit(results);
        int count=0;
        for (int i=0; i< 8; i++) {
            for (int j=0; j< results.length; j++) {
                //if(H[j][i]==results[j][i]) count++;
                System.out.println(H[j][i]);
            }
            if(count==8) System.out.println("blad");
            count=0;
        }

        return results;
    }
}
