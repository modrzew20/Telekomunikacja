package sample;

public class m {

    public static void main(String[] args) {
        String l = "aaaa";

        byte[][] binary = functions.wordtobinary(l);
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
        connect[1][0] = 1;
        connect[2][0] = 1;
        connect[2][1] = 0;
        connect[3][0] = 1;
        connect[3][1] = 0;
        connect[3][2] = 0;
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

    }
}
