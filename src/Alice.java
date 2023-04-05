import java.math.BigInteger;
import java.util.ArrayList;

public class Alice {
    //Defining and Initialising Alice's variables
    private String plainText = ("");
    private ArrayList<BigInteger> binaryText = new ArrayList<BigInteger>();
    public ArrayList<BigInteger> bobMemoryKey = new ArrayList<BigInteger>();
    private ArrayList<BigInteger> cipherText = new ArrayList<BigInteger>();

    //Constructor
    public Alice() {
        this.plainText = plainText;
        //System.out.println("Plain Text: \n" + plainText);
    }
    
    //Sets the saved variable to be bob's public key
    public void setBobKey(ArrayList<BigInteger> bobKey){
        bobMemoryKey = bobKey;
    }

    //Function to convert text to ASCII Binary
    public void binaryTextConversion(){
        binaryText.clear();
        cipherText.clear();
        // System.out.println("Binary Text is: " + binaryText);
        for(int i = 0; i < plainText.length(); i++)
        {
            BigInteger tempValue = BigInteger.valueOf(plainText.charAt(i));
            // System.out.println(tempValue);
            binaryText.add(tempValue);
        }
          // System.out.println("ASCII Text: \n" + binaryText);
    }

    // Encrypts the message using Bob's public key
    public ArrayList<BigInteger> encryptMessage() {
        // System.out.println("Binary Text is: " + binaryText);
        for(int i = 0; i < binaryText.size(); i++)
        {
            // Math for encrypting the public key
            BigInteger tempVal1 = binaryText.get(i).modPow(bobMemoryKey.get(0), bobMemoryKey.get((1)));
            cipherText.add(tempVal1);
        }
        // System.out.println("Cipher Text: \n" + cipherText);
        // Returns the cipher text which has been encrypted
        return cipherText;
    }

    //Getter to retrieve the Cipher Text (Used by Bob to recieve it)
    public ArrayList<BigInteger> getCipherText() {
        return(cipherText);
    }

    //Setter to change the plain text (used by user to input plaintext)
    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }
}
