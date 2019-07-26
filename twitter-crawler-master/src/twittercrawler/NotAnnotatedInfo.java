/* We take all the asked info from not annotated tweets */

package twittercrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.StringTokenizer;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class NotAnnotatedInfo {
    
    final String CONSUMER_KEY = "xxxxxxxxxxxxxxxxxxxxx";
    final String CONSUMER_KEY_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    final String ACCESS_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    final String ACCESS_TOKEN_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    private Status status;
    
    private int exclamationMarks;
    private int questionMarks;
    private int quotationMarks;
    private int posEmoticons;
    private int negEmoticons;
    private int uppercase;
    private int lowercase;
    private int posWords;
    private int vPosWords;
    private int negWords;
    private int vNegWords;
    private int veryPosEmoticons;
    private int veryNegEmoticons;
    private int linkNum;
    private int negations;
    private int letterRepeat;
    private int posExp;
    private int negExp;
    private double posRatio;
    private double negRatio;
    private double vPosRatio;
    private double vNegRatio;
    
    
    void run() throws FileNotFoundException, IOException {
        
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
        AccessToken accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
        twitter.setOAuthAccessToken(accessToken);
        
        FileInputStream fInstream = new FileInputStream("notAnnotatedOut.txt");
        DataInputStream in = new DataInputStream(fInstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        FileWriter fOutstream = new FileWriter("notAnnotatedWeka.txt");
        BufferedWriter out = new BufferedWriter(fOutstream);
        
        
        String strLine;
        String resultTweet;
        WordSets sets = new WordSets();
        
        int i = 0; 
        while ((strLine = br.readLine()) != null) {
            i++;
            
            int index = strLine.indexOf("\t");
            String userId = strLine.substring(0, index);
            String str2 = strLine.substring(index + "\t".length());
            
            index = str2.indexOf("\t");
            String tweetId = str2.substring(0, index);
            String tweet = str2.substring(index + "\t".length());
            
            resultTweet = getTwitterInfo(i, tweetId, twitter);
            getStatusInfo(tweet);
            findWords(tweet, sets.getPosWords(), sets.getVPosWords(), sets.getNegWords(), sets.getVNegWords(), sets.getNegations(), sets.getPosExp(), sets.getNegExp());
            
            String result = tweetId + "," + userId + "," + resultTweet + ","
                    + Integer.toString(linkNum) + ","
                    + Integer.toString(posWords) + ","
                    + Integer.toString(negWords) + ","
                    + Integer.toString(vPosWords) + ","
                    + Integer.toString(vNegWords) + ","
                    + Integer.toString(posExp) + ","
                    + Integer.toString(negExp) + ","
                    + Double.toString(posRatio) + ","
                    + Double.toString(negRatio) + ","
                    + Double.toString(vPosRatio) + ","
                    + Double.toString(vNegRatio) + ","
                    + Integer.toString(negations) + ","
                    + Integer.toString(letterRepeat) + ","
                    + Integer.toString(exclamationMarks) + ","
                    + Integer.toString(questionMarks) + ","
                    + Integer.toString(quotationMarks) + ","
                    + Integer.toString(uppercase) + ","
                    + Integer.toString(lowercase) + ","
                    + Integer.toString(posEmoticons) + ","
                    + Integer.toString(negEmoticons) + ","
                    + Integer.toString(veryPosEmoticons) + ","
                    + Integer.toString(veryNegEmoticons) + ","
                    + "?" + "\n";
            
            // the info is being presented like this:
            // tweetid, userid, followers, friends, timestamp, retweets, favorited
            // links, posWords, negWords, negations, letterRepeat 
            //exclamationMarks, questionMarks, quotationMarks, uppercase, lowercase,
            //posEmoticons, negEmoticons, veryPosEmoticons, veryNegEmoticons
            out.write(result);
            if (i == 180) {
                break; }
        }
        in.close();
        out.close();
    } 

   private void getStatusInfo(String tweet) {
        exclamationMarks = 0;
        questionMarks = 0;
        quotationMarks = 0;
        posEmoticons = 0;
        negEmoticons = 0;
        veryPosEmoticons = 0;
        veryNegEmoticons = 0;
        uppercase = 0;
        lowercase = 0;
        letterRepeat = 0;
        
        // find exclamation, quotation, question marks, uppercase, lowercase
        char[] tweetChars = tweet.toCharArray();
        for (char i: tweetChars) {
            if (i == '!') {
                exclamationMarks ++; }
            if (i == '?') {
                questionMarks ++; }
            if (i == '\'' || i == '\"') {
                quotationMarks ++; }
            if (Character.isUpperCase(i)) {
                uppercase ++; }
            else if (Character.isLowerCase(i)) {
                lowercase ++; }
        }
        
        for (int i = 0; i < tweet.length() - 1; i++) {
            if (tweetChars[i] == ':' && tweetChars[i+1] == ')') {
                posEmoticons ++; }
            if (tweetChars[i] == ':' && tweetChars[i+1] == 'D') {
                veryPosEmoticons ++; }
            if (tweetChars[i] == ':' && (tweetChars[i+1] == 'P' || tweetChars[i+1] == 'p')) {
                posEmoticons ++; }
            if (tweetChars[i] == 'C' && tweetChars[i+1] == ':') {
                veryPosEmoticons ++; }
            if (tweetChars[i] == ';' && tweetChars[i+1] == ')') {
                posEmoticons ++; }
            if (tweetChars[i] == '(' && tweetChars[i+1] == ';') {
                posEmoticons ++; }
            if (tweetChars[i] == '<' && tweetChars[i+1] == '3') {
                veryPosEmoticons ++; }
            if ((tweetChars[i] == 'X' || tweetChars[i] == 'x') && tweetChars[i+1] == 'D') {
                veryPosEmoticons ++; }
            if (tweetChars[i] == ':' && (tweetChars[i+1] == '(' || tweetChars[i+1] == '@' || tweetChars[i+1] == '/')) {
                negEmoticons ++; }
            if (tweetChars[i] == 'D' && tweetChars[i+1] == ':') {
                veryNegEmoticons ++; }
            if ((tweetChars[i] == '/' || tweetChars[i] == '\\') && tweetChars[i+1] == ':') {
                veryNegEmoticons ++; }
            if(tweetChars[i] == '♥') {
                veryPosEmoticons ++; }
            if (tweetChars[i] == ':' && tweetChars[i+1] == '*') {
                veryPosEmoticons ++; }
            
            if (tweetChars[i] == 't' && tweetChars[i+1] == 'p') {
                i += 4; }
        }
        
        for (int i = 0; i < tweet.length() - 2; i++) {
            if (tweetChars[i] == ':' && tweetChars[i+1] == '-' && tweetChars[i+2] == ')') {
                posEmoticons ++; }
            if (tweetChars[i] == ':' && tweetChars[i+1] == '-' && (tweetChars[i+2] == 'P' || tweetChars[i+2] == 'p')) {
                posEmoticons ++; }
            if (tweetChars[i] == ';' && tweetChars[i+1] == '-' && tweetChars[i+2] == ')') {
                posEmoticons ++; }
            if (tweetChars[i] == '\\' && tweetChars[i+1] == 'm' && tweetChars[i+2] == '/') {
                posEmoticons ++; }
            if (tweetChars[i] == ':' && tweetChars[i+1] == '-' && tweetChars[i+2] == '(') {
                negEmoticons ++; }
            if (tweetChars[i] == '>' && tweetChars[i+1] == '.' && tweetChars[i+2] == '<') {
                veryNegEmoticons ++; }
            if (tweetChars[i] == '-' && tweetChars[i+1] == '_' && tweetChars[i+2] == '-') {
                negEmoticons ++; }
            if (tweetChars[i] == 'o' && tweetChars[i+1] == '_' && tweetChars[i+2] == 'o') {
                negEmoticons ++; }
            if (tweetChars[i] == ':' && tweetChars[i+1] == '-' && tweetChars[i+1] == '*') {
                veryPosEmoticons ++; }
            
            if (tweetChars[i] == 't' && tweetChars[i+1] == 't' && tweetChars[i+1] == 'p') {
                i += 4; }
        }
        
        for (int i = 0; i < tweet.length() - 2; i++) {
            if (tweetChars[i] == tweetChars[i+1] && tweetChars[i] == tweetChars[i+2]) {
                letterRepeat ++;
                i = i + 2;
            }
        }
    }
    
    
    private String getTwitterInfo(int i, String tweetId, Twitter twitter) {
        String result = null;
        
        //followers, friends, timestamp, retweets, favorited
        try {
            status = twitter.showStatus(Long.parseLong(tweetId));
            result = Long.toString(status.getUser().getFollowersCount()) + ","
                    + Long.toString(status.getUser().getFriendsCount()) + ",\""
                    + status.getCreatedAt() + "\","
                    + Long.toString(status.getRetweetCount()) + ","
                    + Long.toString((status.isFavorited()) ? 1:0);
        
        } catch (TwitterException ex) { ex.printStackTrace(); System.out.println(i);}
        return result;
    }

    
    /* http://wiki.answers.com/Q/Java_program_to_find_frequency_of_occurence_of_each_word_in_text_file */
    private void findWords(String tweet, Set posSet, Set vPosSet, Set negSet,
            Set vNegSet, Set negationsSet, Set posExpSet, Set negExpSet) {
        int words = 0;
        boolean flag = false;
        String lastWord = "";
        
        linkNum = 0;
        posWords = 0;
        vPosWords = 0;
        posExp = 0;
        negWords = 0; 
        vNegWords = 0;
        negExp = 0;
        negations = 0;
        posRatio = 0.0;
        negRatio = 0.0;
        vPosRatio = 0.0;
        vNegRatio = 0.0;
        
        tweet = tweet.toLowerCase();
        StringTokenizer parser = new StringTokenizer(tweet, " \t\n\r\f.,;:!?\"#"); 
        while (parser.hasMoreTokens()) {
            String currentWord = clearWord(parser.nextToken());
            
            words ++;
            flag = false;
            if (lastWord.compareTo("") != 0) {
                if (posExpSet.contains(lastWord + " " + currentWord)) {
                    posExp ++;
                    flag = true;
                }
                else if (negExpSet.contains(lastWord + " " + currentWord)) {
                    negExp ++;
                    flag = true;
                }
                else if (posSet.contains(lastWord)) {
                    posWords ++; }
                else if (vPosSet.contains(lastWord)) {
                    vPosWords ++; }
                else if (negSet.contains(lastWord)) {
                    negWords ++; }
                else if (vNegSet.contains(lastWord)) {
                    vNegWords ++; }
                else if (negationsSet.contains(lastWord)) {
                    negations ++; }
                else if (lastWord.startsWith("http")) {
                    linkNum ++; }
            }
            if (flag) {
                lastWord = ""; }
            else {
                lastWord = currentWord; }
        }
        if (!flag) {
            if (posSet.contains(lastWord)) {
                posWords ++; }
            if (vPosSet.contains(lastWord)) {
                vPosWords ++; }
            if (negSet.contains(lastWord)) {
                negWords ++; }
            if (vNegSet.contains(lastWord)) {
                vNegWords ++; }
            if (negationsSet.contains(lastWord)) {
                negations ++; }
            if (lastWord.startsWith("http")) {
                linkNum ++; }
        }
        posRatio = (double) posWords/words;
        negRatio = (double) negWords/words;
        vPosRatio = (double) vPosWords/words;
        vNegRatio = (double) vNegWords/words;
    }
    
    private String clearWord(String word){
        String clearWord;
        if (word.charAt(0) == '\'') {
            clearWord = word.substring(1);
        }
        else if (word.charAt(word.length() - 1) == '\'') {
            clearWord = word.substring(0, word.length() - 1);
        }
        else {
            clearWord = word;
        }
        return clearWord;
    }
}
