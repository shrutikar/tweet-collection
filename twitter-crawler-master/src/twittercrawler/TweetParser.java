/* * * * * * * * * *
* We parse every word from the tweets and move it in a hash map
* then we show in the stdout the most used words
* that way we can have a general view of words that MAYBE can help
* in the sentiment analysis of large numbers of tweets.
*/
package twittercrawler;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class TweetParser {
    
    private HashMap frequencyMap;
    private String tweet;
    
    public void run() throws FileNotFoundException, IOException {
        FileInputStream fInstream = new FileInputStream("notAnnotatedOut.txt");
        DataInputStream in = new DataInputStream(fInstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        frequencyMap = new HashMap();
        String strLine;
        
        // We use the not annotated tweets first
        while ((strLine = br.readLine()) != null) {
            int index = strLine.indexOf("\t");
            String str2 = strLine.substring(index + "\t".length());
            index = str2.indexOf("\t");
            
            tweet = str2.substring(index + "\t".length());
            getWords(tweet);
        }
        
        fInstream = new FileInputStream("annotatedOut.txt");
        in = new DataInputStream(fInstream);
        br = new BufferedReader(new InputStreamReader(in));
        
        // the annotated afterwards
        while ((strLine = br.readLine()) != null) {
            int index = strLine.lastIndexOf("\t");
            String str2 = strLine.substring(0, index);
            int index2 = str2.lastIndexOf("\t");
            
            tweet = strLine.substring(index2, index - 1);
            getWords(tweet);
        }
        printMap(frequencyMap);
    }

    
    public static void printMap(Map<String, String> map){
        int value;
        
        for (Map.Entry entry : map.entrySet()) {
            value = (int) entry.getValue();
            
            // choose the frequency of words, as most are found only once - twice
            // a value of 5-10 is good enough
            if (value > 10)
                System.out.println("Key : " + entry.getKey() + "\t\t\tValue : " + value);
        }
    }
    
    // Code found HERE:
    // http://wiki.answers.com/Q/Java_program_to_find_frequency_of_occurence_of_each_word_in_text_file    
    public void getWords(String strLine) {
        strLine = strLine.toLowerCase();
        
        StringTokenizer parser = new StringTokenizer(strLine, " \t\n\r\f.,;:!?\""); 
        while (parser.hasMoreTokens()) { 
            String currentWord = parser.nextToken(); 
            Integer frequency = (Integer) frequencyMap.get(currentWord); 
            if (frequency == null) {
                frequency = 0; }  
            frequencyMap.put(currentWord, frequency + 1); 
        }
    }
}
