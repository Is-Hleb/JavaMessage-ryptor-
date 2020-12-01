package hleb;

import java.io.IOException;
import java.lang.invoke.SwitchPoint;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Main {

    static class CipheredMessage {
        public String message;
        public Vector<Integer> keys;

        CipheredMessage(String message, Vector<Integer> keys) {
            this.message = message;
            this.keys = keys;
        }

        public String keysToString() {
            StringBuilder sb = new StringBuilder();
            for (Integer key : keys) {
                sb.append(key).append(' ');
            }
            return sb.toString();
        }
    }

    static class Pair {
        public int first;
        public String second;

        public Pair(int first, String second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second='" + second + '\'' +
                    '}';
        }
    }

    public static CipheredMessage cipherMessage(String input) {
        String output = input;
        Vector<Integer> keys = new Vector<>();

        for (int count = (int) (Math.random() * 10); count >= 0; count--) {
            Pair cur = cipherString(output);
            output = cur.second;
            keys.add(cur.first);
        }

        return new CipheredMessage(output, keys);
    }

    public static Pair cipherString(String input) {
        int key = 0;
        StringBuilder out = new StringBuilder();
        while (key == 0)
            key = (int) (Math.random() * 1000);

        for (int i = 0; i < input.length(); i++) {
            char cur = input.charAt(i);
            int kode = (int) cur ^ key;
            out.append((char) kode);
        }
        System.out.println(out.toString());
        return new Pair(key, out.toString());
    }

    public static String reCipher(String input, Vector<Integer> keys) {
        StringBuilder cur_re_ciphered_string = new StringBuilder();
        String out = input;

        for (Integer key : keys) {
            cur_re_ciphered_string.delete(0, cur_re_ciphered_string.length());
            for (char sim : out.toCharArray()) {
                cur_re_ciphered_string.append((char) ((int) sim ^ key));
            }
            out = cur_re_ciphered_string.toString();
        }
        return out;
    }

    protected static String getData(String message) {
        Scanner in = new Scanner(System.in);

        System.out.println(message);

        String out = in.nextLine();

        while (out.length() == 0) {
            System.out.println("Please, write something...");
            out = in.nextLine();
        }

        return out;
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String com = "";

        String[][] help = {
                {"cipherMessage", "This command help you to cipher some message..."},
                {"reCipherMessage", "this command will help you to re cipher some message, if you have cipher key..."},
                {"cipherMessageUseKey", "this command help if you you want to cipher message with you own key"},
        };

        while (com != "exit") {
            com = in.nextLine();

            if (com.contains("exit"))
                break;

            switch (com) {
                case "cipherMessage":
                    String input = getData("Write message");

                    CipheredMessage out = cipherMessage(input);
                    System.out.println("Ready message : " + out.message + '\n' + "Code to recipher : " + out.keysToString());
                    break;

                case "reCipherMessage":
                    String message = getData("Write message");

                    String keys = getData("Write keys");

                    if (keys.charAt(keys.length() - 1) != ' ')
                        keys = keys + ' ';

                    StringBuilder sb = new StringBuilder();
                    Vector<Integer> in_re_cipher = new Vector<>();

                    for (char sim : keys.toCharArray()) {
                        if (sim == ' ') {
                            in_re_cipher.add(Integer.parseInt(sb.toString()));
                            sb.delete(0, sb.toString().length());
                        } else {
                            sb.append(sim);
                        }
                    }
                    System.out.println("Re ciphered message : " + reCipher(message, in_re_cipher));
                    break;

                case "cipherMessageUseKey":
                    message = getData("Enter message");

                    keys = getData("Write keys");

                    if (keys.charAt(keys.length() - 1) != ' ')
                        keys = keys + ' ';

                    in_re_cipher = new Vector<>();
                    sb = new StringBuilder();

                    for (char sim : keys.toCharArray()) {
                        if (sim == ' ') {
                            in_re_cipher.add(Integer.parseInt(sb.toString()));
                            sb.delete(0, sb.toString().length());
                        } else {
                            sb.append(sim);
                        }
                    }
                    System.out.println("Re ciphered message : " + reCipher(message, in_re_cipher));
                    break;

                case "help":
                    for (String[] arr : help) {
                        System.out.println('\t' + arr[0] + '\t' + "| \t" + arr[1]);
                    }
                    break;

                default:
                    System.out.println("This is un correct command, write 'help', or try again...");
                    break;
            }
        }
    }
}
