import java.math.BigInteger;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Random;

public class Bob {
    // Defining and Initialising Bob's variables
    private BigInteger privateKey;
    public ArrayList<BigInteger> publicKey = new ArrayList<BigInteger>();
    private ArrayList<BigInteger> memoryCipherText = new ArrayList<BigInteger>();
    private ArrayList<BigInteger> finalMessage = new ArrayList<BigInteger>();
    private ArrayList<Character> finalString = new ArrayList<Character>();
    private BigInteger pVal;
    private BigInteger qVal;
    private BigInteger nVal;
    private BigInteger rVal;
    private BigInteger eVal;
    private BigInteger dVal;

    // Bob constructor; keyGeneration() is run to generate keys on initialisation of program
    public Bob(){
        this.memoryCipherText = memoryCipherText;
        keyGeneration();
    }
    // Method to automatically generate private key
    public void keyGeneration(){
        publicKey.clear();
        pVal = BigInteger.valueOf(0);
        qVal = BigInteger.valueOf(0);
        nVal = BigInteger.valueOf(0);
        rVal = BigInteger.valueOf(0);
        eVal = BigInteger.valueOf(0);
        dVal = BigInteger.valueOf(0);
        generatePQ();
        // pVal = BigInteger.valueOf(733);
        // qVal = BigInteger.valueOf(601);
        generateNR();
        generateE();
        //eVal = BigInteger.valueOf(907);
        generateD();
        publicKey.add(eVal);
        // publicKey.add(BigInteger.valueOf(13));
        publicKey.add(nVal);
        // publicKey.add(BigInteger.valueOf(77));
        this.privateKey = dVal;
        // this.privateKey = BigInteger.valueOf(37);
    }
    //Method to generate private key using custom values for primes P and Q
    public void customGeneration(BigInteger P, BigInteger Q)
    {
        publicKey.clear();
        pVal = BigInteger.valueOf(0);
        qVal = BigInteger.valueOf(0);
        nVal = BigInteger.valueOf(0);
        rVal = BigInteger.valueOf(0);
        eVal = BigInteger.valueOf(0);
        dVal = BigInteger.valueOf(0);
        pVal = P;
        qVal = Q;
        generateNR();
        generateCustomE();
        generateD();
        publicKey.add(eVal);
        publicKey.add(nVal);
        this.privateKey = dVal;
    }

    //getters and setters
    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    public ArrayList<BigInteger> getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(ArrayList<BigInteger> publicKey) {
        this.publicKey = publicKey;
    }

    //Method to reassign the ciphertext from alice
    public void setCipherText(ArrayList<BigInteger> ct){
        memoryCipherText.clear();
        //System.out.println("ct:" + ct);
        //System.out.println("Bob's cipher text before reassigning: " + memoryCipherText);
        memoryCipherText.addAll(ct);
        //System.out.println("Bob's cipher text after reassigning: " + memoryCipherText);
    }
    //Method to generate a massive Prime number that is most likely a prime
    private BigInteger probablePrime(int val){
        Random randomNum = new Random();
        return BigInteger.probablePrime(val, randomNum);
    }
    //Method to generate values P and Q
    private void generatePQ(){
        this.pVal = probablePrime(330);
        this.qVal = probablePrime(330);
        while (pVal.equals(qVal))
        {
            qVal = probablePrime(330);
        }
        // System.out.println("P: " + pVal + "\nQ: " + qVal);

    }
    //Method to generate values N and R when automatically generating private key
    private void generateNR(){
        this.nVal = this.pVal.multiply(this.qVal);
        // System.out.println("N: " + nVal);

        BigInteger tempP = pVal.subtract(BigInteger.valueOf(1));
        BigInteger tempQ = qVal.subtract(BigInteger.valueOf(1));
        this.rVal = tempP.multiply(tempQ);
        // System.out.println("R: " + rVal);
        // System.out.println("R Length: " + rVal.toString().length());
    }
    //Method to generate value E when automatically generating private key
    private void generateE(){
        this.eVal = probablePrime(364);
        while (eVal.compareTo(rVal) != -1)
        {
            eVal = probablePrime(364);
        }
        // System.out.println("E: " + eVal);
        // System.out.println("E Length: " + eVal.toString().length());
        // System.out.println(rVal.subtract(eVal));
    }
    //Method to generate value E when using custom values for P and Q
    private void generateCustomE(){
        int tempVal1 = rVal.bitLength();
        tempVal1 = tempVal1 - 1;
        this.eVal = probablePrime(tempVal1);
        while (eVal.compareTo(rVal) != -1)
        {
            eVal = probablePrime(tempVal1);
        }
        // System.out.println("E: " + eVal);
        // System.out.println("E Length: " + eVal.toString().length());
        // System.out.println(rVal.subtract(eVal));
    }
    //Method to generate value D
    private void generateD(){
       dVal = eVal.modInverse(rVal);
    }
    //Method to decrypt Alice's encrypted message
    public String decryptMessage() {
        //Clear ArrayLists
        finalMessage.clear();
        finalString.clear();
        //For every character, run decrypting algorithm to get plaintext character ASCII value at index i
        for(int i = 0; i < memoryCipherText.size(); i++)
        {
            BigInteger tempVal1 =  memoryCipherText.get(i).modPow(privateKey, publicKey.get(1));
            // System.out.println("Stage 1: Cipher Text to the power of Private Key : " + tempVal1);
            //tempVal1 = tempVal1.mod(publicKey.get(1));
            // System.out.println("Stage 2: Taking the mod of  the temp val now : " + tempVal1);
            finalMessage.add(tempVal1);
        }
        // System.out.println("Final Message in Binary Text: \n" + finalMessage);
        //Convert every ASCII character value to character data type to get letter
        for(int i = 0; i < finalMessage.size(); i++){
            finalString.add((char)finalMessage.get(i).intValue());
        }
        // System.out.println("Decrypted Text: \n" + finalString);
        //Construct a string using converted character ArrayList
        StringBuilder result = new StringBuilder(finalString.size());
        for (Character c: finalString){
            result.append(c);
        }
        //Return decrypted string
        String output = result.toString();
        // System.out.println("Decrypted Text In String: \n" + output);
        return output;
    }
}
