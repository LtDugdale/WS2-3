package predictive;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 3 More efficiency (25%)
 *
 * @author Laurie Dugdale
 */
public class MapDictionary implements Dictionary {

    private Map <String, Set<String>> mapDictionary; // Stores all the contents of the dictionary file

    /**
     * Answer 1.2)
     *
     * I've chosen TreeMap after a lot of testing. after testing this class TreeMap has better efficiency.
     * whilst it was overall around 0.1s slower this was down to adding the dictionary to the Map. during lookup
     * TreeMap consistently outperformed HashMap. The documentation states that with HashMap the get method is O(1)
     * where as with TreeMap get is O(log n). This could mean that HashMap scales better. However I've yet to run a test
     * that proves this.
     * ---------------------------------------------------------------------------------
     * Constructor for the ListDictionary class.
     *
     * This constructor accepts a String representing the path to the dictionary file. When the class is instantiated
     * all the valid words ( words only containing letters of the alphabet ) in the file are added to a Map.
     *
     * @param path A string representing the path to the required dictionary file.
     */
    public MapDictionary(String path){

        mapDictionary = new TreeMap<>();
        String word;

        // read in the file -- scanner is inside "try with resources" as it explicitly closes
        try( Scanner in = new Scanner( new File(path) ) ){

            // while there is a next line
            while ( in.hasNextLine() ) {

                // check if current word is valid before adding
                if (PredictivePrototype.isValidWord(word = in.nextLine())){
                    word = word.toLowerCase();
                    mapDictionary.computeIfAbsent(PredictivePrototype.wordToSignature(word),k -> new HashSet<>()).add(word);
                }
            }

        } catch(IOException e){

            System.err.println("Got an IOException: " + e.getMessage());
        }
    }

    @Override
    /**
     * signatureToWords accepts a String containing numbers as a parameter representing a signature to be matched to
     * a word in the dictionary LinkedList ( e.g. if a number in the string is 2 it will be matched to "abc" ).
     * A set of all the Strings that are matched to the signature are returned.
     *
     * @param signature String of numbers representing the numbers on a keypad used for a t9 texting system
     * @return Set of Strings containing the matched words from the "words" file in the directory.
     */
    public Set<String> signatureToWords(String signature) {

        // in keeping with the other classes if the signature doesnt exist return and empty list instead of null, this is done for consistency
        return (mapDictionary.get(signature) == null) ? new HashSet<>() : mapDictionary.get(signature);
    }


    @Override
    /**
     * To string method
     * Created this to help with testing.
     *
     * @return The toString method of the mapDictionary object.
     */
    public String toString() {
        return mapDictionary.toString();
    }

}
