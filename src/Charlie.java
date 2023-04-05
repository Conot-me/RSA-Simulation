import java.math.BigInteger;
import java.util.ArrayList;

public class Charlie {
    //assigning variables
    private ArrayList<BigInteger> publicKey = new ArrayList<BigInteger>();
    private ArrayList<BigInteger> cipherText = new ArrayList<>();
    private ArrayList<Character> plainText = new ArrayList<>();
    private ArrayList<BigInteger> finalMessage = new ArrayList<BigInteger>();
    private BigInteger foundP;
    private BigInteger foundQ;
    private BigInteger foundR;
    private BigInteger foundD;

    //constructor not needed as charlie only reacts to inputs.
    public Charlie() {

    }

    //getters and setters
    public ArrayList<BigInteger> getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(ArrayList<BigInteger> publicKey) {
        this.publicKey = publicKey;
    }

    public ArrayList<BigInteger> getCipherText() {
        return cipherText;
    }

    public void setCipherText(ArrayList<BigInteger> cipherText) {
        this.cipherText = cipherText;
    }

    //used to determine whether the value of the public key N is too computationally heavy in order to decrypt.
    public boolean isThisDecryptable()
    {
        //we check if the value of N is withing the boundaries of the int type.
        if (getPublicKey().get(0).compareTo(BigInteger.valueOf(2147483647)) == 1|| getPublicKey().get(1).compareTo(BigInteger.valueOf(2147483647)) == 1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //check if decryption is possible. if true then attempts to decrypt.
    public String decryptionVerification()
    {
        //decryptable check
        if (isThisDecryptable())
        {
            //Work out factors of n
            for(int i = 1; i < publicKey.get(1).intValue(); i++)
            {
                //fermats little theorem
                if(publicKey.get(1).intValue() % i == 0)
                {
                    BigInteger factorP = BigInteger.valueOf(i);
                    BigInteger factorQ = BigInteger.valueOf(publicKey.get(1).intValue() / factorP.intValue());

                    //determines if  P and Q are both probable primes.
                    if (isProbablyPrime(factorP) && isProbablyPrime(factorQ))
                    {
                        BigInteger factorR = calculateR(factorP, factorQ);
                        BigInteger factorD = calculateD(factorR);
                        boolean isPrivateKeyValid = testD(factorD, factorR);

                        //determines if D is correct
                        if(isPrivateKeyValid)
                        {
                            //If correct, assigns and saves the current value, and returns them
                            this.foundP = factorP;
                            this.foundQ = factorQ;
                            this.foundR = factorR;
                            this.foundD = factorD;
                            return ("Private Key Decrypted!" + '\n' + decryptMessage());
                        }
                    }
                }
            }
            //this should never run; Charlie checks the key sizes beforehand, this only outputs if you force Charlie to decrypt regardless of key size
            return("Memory Error! This is due to the numbers being too big to crack and not enough computational power to do so!");
        }
        else
        {
            // If the keys are too large to be computed, then he does not run it
            return("The keys are too big to be decrypted by me!");
        }
    }

    //decrypts the message with the keys found/generated
    public String decryptMessage() {
        //decryption method for each character.
        for(int i = 0; i < cipherText.size(); i++)
        {
            BigInteger tempVal1 =  cipherText.get(i).modPow(foundD, publicKey.get(1));
            // System.out.println("Stage 1: Cipher Text to the power of Private Key : " + tempVal1);
            //tempVal1 = tempVal1.mod(publicKey.get(1));
            // System.out.println("Stage 2: Taking the mod of  the temp val now : " + tempVal1);
            finalMessage.add(tempVal1);
        }
        // System.out.println("Final Message in Binary Text: \n" + finalMessage);

        //converts integers to characters
        for(int i = 0; i < finalMessage.size(); i++){
            plainText.add((char)finalMessage.get(i).intValue());
        }
        //System.out.println("Binary Text found by Charlie: \n" + plainText);
        StringBuilder result = new StringBuilder(plainText.size());

        //converts characters to string
        for (Character c: plainText){
            result.append(c);
        }
        String output = result.toString();
        // System.out.println("String found by Charlie: \n " + output);

        return output;
    }

    //checks if the passed variable is a probable prime.
    public boolean isProbablyPrime(BigInteger temp)
    {
        return temp.isProbablePrime(5);
    }

    //calculate R from p-1 * q-1.
    public BigInteger calculateR(BigInteger factorP, BigInteger factorQ)
    {
        BigInteger tempA = factorP.subtract(BigInteger.valueOf(1));
        BigInteger tempB = factorQ.subtract(BigInteger.valueOf(1));
        BigInteger factorR = tempA.multiply(tempB);
        return factorR;
    }

    //Calculate the private key using the inverse modulo of E and R
    public BigInteger calculateD(BigInteger factorR)
    {
        // System.out.println("public key = " + publicKey.get(0) + "\n" + "factorR = "+factorR);
        BigInteger factorD = publicKey.get(0).modInverse(factorR);
        return factorD;
    }

    //verifies if the D calculated is the correct one. E*D MOD R = 1
    public boolean testD(BigInteger factorD, BigInteger factorR)
    {
        BigInteger checkSum = publicKey.get(0).multiply(factorD).mod(factorR);

        if (checkSum.intValue() == 1)
        {
            return true;
        }
        return false;
    }


}
