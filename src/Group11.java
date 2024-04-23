//todo add the crack function
// make sure that the alphabet characters are being cast to lowercase
// implement decrypt function into the menu
// add comments and javadoc
import java.util.*;
import java.io.*;

public class Group11 {
    private static boolean isCracking = false;
    public static void main(final String args[]) throws FileNotFoundException {
        boolean validKey = true;
        // This is to ensure that the decryption only prints out if the key is being bruteforced
        String filename = "";
        File dicFile = new File("dictionary.txt");
        Scanner dicScan = new Scanner(dicFile);
        //Arraylist that will store the values returned from encrypt and decrypt methods
        ArrayList<Character> fileContents;
        //array of characters of the alphabet
        char[] Alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
                'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};


        System.out.println("Start of program");
        System.out.println("# of arguments: " + args.length);

        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i] + i);
            filename = args[1];
            //System.out.println(filename);
            File file = new File(filename);

            try {
                Scanner scan = new Scanner(file);
                char[] fileContent = writeFromFile(scan);
                if (args[i].equals("-e")) {
                    File testfile = new File(filename);
                    filename = args[1];
                    // the key will be the third argument and the second index and the scond index in the arry
                    String key = args[2];
                    // the key is being passed from the argument into a double for the rest of the methods
                    Double keyspace = Double.parseDouble(key);
                    try {
                        // if this test fails then the program will end, however the incorrect keyspace format will still be assigned to the variable for function purposes
                        Double testKeyspace = Double.parseDouble(key);
                        // this ensures that the keyspace providd is a numeric value
                    } catch (NumberFormatException e) {
                        validKey = false;
                    }       //the program will call an exception if the key isn't a number
                    if (validKey) {
                        //System.out.println("keyspace is " + key);
                        fileContents = encrypt(keyspace, fileContent, Alphabet);
                        writeToFile("EncryptedText.txt", fileContents);

                    } else {
                        validKey = false;
                        System.out.println("Key must be a number, stupid. ");
                        break;
                    }
                    //the first index of args is the first command pushed ot the terminal
                    //System.out.println("encrypt");

                    // System.out.println("keyspace is " + key);

                }
                if (args[i].equals("-d")) {
                    File testfile = new File(filename);

                    filename = args[1];
                    // the key will be the third argument and the second index and the scond index in the arry

                    String key = args[2];
                    Double keyspace = Double.parseDouble(key);

                    System.out.println(new String(fileContent));


                    try {

                        // if this test fails then the program will end, however the incorrect keyspace format will still be assigned to the variable for function purposes

                        Double testKeyspace = Double.parseDouble(key);

                        // this ensures that the keyspace providd is a numeric value
                    } catch (NumberFormatException e) {
                        validKey = false;
                    }       //the program will call an exception if the key isn't a number
                    if (validKey) {
                        System.out.println("keyspace is " + key);
                        fileContents = decrypt(keyspace, fileContent, Alphabet);
                        writeToFile("DecryptedText.txt", fileContents);
                    } else {
                        validKey = false;
                        System.out.println("Key must be a number, stupid. ");
                        break;
                    }
                    //the first index of args is the first command pushed ot the terminal

                    //System.out.println("keyspace is " + key);
                    decrypt(keyspace, fileContent, Alphabet);
                    System.out.println("Decrypting");
                }
                if (args[i].equals("-c")) {
                    filename = args[1];
                    isCracking=true;
                    //System.out.println("filename is " + filename);
                    System.out.println("Cracking");
                    crack(fileContent, "dictionary.txt", Alphabet, 0);
                }
            } catch (FileNotFoundException fnf) {
                System.
                        out.println("File not found");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        if (validKey) {
            File file = new File(filename);
            Scanner scan = new Scanner(file);
            char[] fileContent = writeFromFile(scan);
            //System.out.println("In this fucking file is: " + new String(fileContent));


            // dince the encrypt function uses an int this doublemust be cast to an integer


            System.out.println("End of program");
        }


    }

    // This method creates an empty string and adds the contents of a specified file to it and returns the text as an aray of characters. It raises an error if the file does't exist.
    public static char[] writeFromFile(final Scanner scan) throws FileNotFoundException {
// crreates an empty string for thr the file contents to be added to
        String string = "";
        //While the file still has lines, the scannerwill continue to read it
        while (scan.hasNextLine()) {
            string = string.toLowerCase();
            string = string + "\n" + scan.nextLine();
        }
        //The string containing the text from the file will be added to this string of characters
        final char[] fileContentString = string.toCharArray();
        return fileContentString;
    }

    public static void writeToFile(String fileName, ArrayList<Character> fileContents) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        for (int i = 0; i < fileContents.size();i++){
            if(fileContents.get(i) == ' '){
                writer.write("\n");
            }
            writer.write(fileContents.get(i));
        }
        writer.close();
    }



    public static ArrayList<Character> encrypt(double key, char[] plainText, char[] alphabet) {
        ArrayList<Character> encryptedText = new ArrayList<Character>();
        //variable to store a character
        char tempChar = ' ';
        //variable to store the current value in the  plaintext
        int currentVal = 0;
        //first variable to store character pulled from an array to be compared
        char compare1= ' ';
        //second variable to store character pulled from an array to be compared
        char compare2 = ' ';
        //while loop to loop through the plaintext list
        while ( currentVal < plainText.length) {
            //if the plaintext value is nothing, add to the array
            if(compare1 == ' '){
                encryptedText.add(compare1);
            }
            //loops through the alphebet array
            for (int j = 0; j < alphabet.length; j++) {
                //takes the current value from the plaintext array, makes it lowercase and stores it to a variable
                compare1 = Character.toLowerCase(plainText[currentVal]);
                //takes the current value from the alphabet array and stores it to a variable
                compare2 = alphabet[j];
                //compares the two values
                if (compare1 == compare2) {
                    //shifts the value by the key number and modulates it
                    tempChar = alphabet[(j+(int)key)%26];
                    //adds to a new arraylist
                    encryptedText.add(tempChar);
                }
            }
            //increments the while loop by one
            currentVal  += 1;

        }
        //returns the arraylist of encrypted plainText
        return encryptedText;
    }

    public static ArrayList<Character> decrypt(double key, char[] cipherText, char[] alphabet) {
        ArrayList<Character> decryptedText = new ArrayList<Character>();
        //variable to store a character
        char tempChar = ' ';
        //variable to store the current value in the  plaintext
        int currentVal = 0;
        //first variable to store character pulled from an array to be compared
        char compare1= ' ';
        //second variable to store character pulled from an array to be compared
        char compare2 = ' ';
        //while loop to loop through the plaintext list
        while (currentVal < cipherText.length) {
            if (key > 26) {
                key = key % 26;
            }
            //if the plaintext value is nothing, add to the array
            if(compare1 == ' '){
                decryptedText.add(compare1);
            }
            for (int j = 0; j < alphabet.length; j++) {
                //takes the current value from the plaintext array, makes it lowercase and stores it to a variable
                compare1 = Character.toLowerCase(cipherText[currentVal]);
                //takes the current value from the alphabet array and stores it to a variable
                compare2 = alphabet[j];
                //compares the two values
                if (compare1 == compare2) {
                    //shifts the value by the key number, 26 and modulates it
                    //adds 26 to the value since Java can't deal with negative array values
                    tempChar = alphabet[(j - (int) key + 26) % 26];
                    //adds to a new arraylist
                    decryptedText.add(tempChar);
                }
            }
            //increments the while loop by one
            currentVal += 1;
            //checks for if the crack function is called
            if (isCracking) {
                System.out.println("DECRYPTED: " + tempChar);
            }
        }
        //returns the arraylist of decrypted text
        return decryptedText;
    }

    public static ArrayList<Character> crack(char[] ciphertext, String dictionary, char[] alphabet, int threshold) {

        ArrayList<Character> decryptedText = new ArrayList<Character>();
        int matches = 0;
        // Split the dictionary into an array of words at a spaces
        String[] words = dictionary.split("\\s+");
        // Iterate over each possible key
        //System.out.println("Matched word: " + word);
        int key = 0;
        while(key < alphabet.length) {
            matches = 0;
            System.out.printf("\n" + String.valueOf(matches));
            // Decrypt the ciphertext using the current key
            ArrayList<Character> plaintext = decrypt(key, ciphertext, alphabet);
            System.out.println("Ayo, the key being used is " +key);
            String candidate = "";
            for (Character word : plaintext) {
                candidate += word;
            }

            // Iterate over each word in the dictionary
            for (String word : words) {
                if (candidate.toLowerCase().contains(word.toLowerCase())) {
                    matches++;
                }
                System.out.println("Comparing decrypted text: " + candidate.toLowerCase() + " with dictionary word: " + word.toLowerCase());

            }

            // If the number of matching words meets the threshold, return the decrypted text
            if ((double) matches / (double) words.length >= (double) threshold ) {
                System.out.println("The threshold has reached " + threshold +"%, so " + key + " is the key.");
                decryptedText = plaintext;
                break;
            }
            key += 1;
        }
        isCracking = false;
        // Return an empty ArrayList if no match is found
        return decryptedText;
    }
}