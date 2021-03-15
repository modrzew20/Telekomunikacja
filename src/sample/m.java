package sample;

public class m {

    public static void main(String[] args) {
        String l = "abwadae3";

        byte[][] binary = functions.stringToBinary(l);
        for(int i=0; i<l.length(); i++) {
            for(int j=0;j<8;j++) {
                System.out.print(binary[i][j]);
            }
            System.out.println("");
        }

        System.out.println("BITY PARZYSTOSCI");


        byte[][] parityBits=functions.addParityBit(binary);

        for(int i=0; i<l.length(); i++) {
            for(int j=0;j<8;j++) {
                System.out.print(parityBits[i][j]);

            }
            System.out.println("");
        }

        byte[][] connect = functions.connect(binary,parityBits);
        for(int i=0; i<l.length(); i++) {
            for(int j=0;j<16;j++) {
                System.out.print(connect[i][j]);
            }
            System.out.println("");
        }
        System.out.println("Zmiana");
//        connect[0][13] = 0;
//        connect[0][0] = 1;
        for(int i=0; i<l.length(); i++) {
            for(int j=0;j<16;j++) {
                System.out.print(connect[i][j]);
            }
            System.out.println("");
        }
        System.out.println("MNOZENIE MACIERZY");
        byte[][] checked = functions.addParityBit(connect);

        for(int i=0; i<l.length(); i++) {
            for(int j=0;j<8;j++) {
                System.out.print(checked[i][j]);
            }
            System.out.println("");
        }
        System.out.println(functions.findError(checked, connect));
        byte[][] result = functions.disconnect(connect);
        for(int i=0; i<l.length(); i++) {
            for(int j=0;j<8;j++) {
                System.out.print(result[i][j]);
            }
            System.out.println("");
        }
        System.out.println(functions.binaryToString(result));

    }
}
