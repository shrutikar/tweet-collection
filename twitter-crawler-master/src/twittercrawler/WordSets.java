/* The word sets used for annotation */

package twittercrawler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WordSets {
    
    Set<String> posWords;
    Set<String> vPosWords;
    Set<String> negWords;
    Set<String> vNegWords;
    Set<String> negations;
    Set<String> posExp;
    Set<String> negExp;
    
    
    public WordSets() {
        //The decision on whether to put a word in Positive/Negative Words or Very Positive/Negative Words
        //category is made by taking into account both the intensity of the emotion it describes and the
        //possibility of showing up in a sentence without having the expected meaning
        
        posWords = new HashSet<>(Arrays.asList("ha", "happy", "happier", "happily",
                "like", "likes", "thank", "thanks", "nice", "lol", "lucky", "luck",
                "luckily", "fortunately", "fun", "funny", "inspiration",
                "inspirational", "inspired", "inspire", "inspires", "inspiring",
                "better", "beautiful", "handsome", "hi", "hello", "glad", "appreciate",
                "appriciation", "appreciates", "appreciated","fantasy", "good",
                "luck", "welcome", "yes", "awesome", "success","great", "greater",
                "laugh", "laughing", "laughter", "laughed", "laughs", "keen",
                "glad", "hot", "hottest","successful", "successfully", "cute",
                "enjoyed", "enjoy", "positive","enjoying", "omg", "omfg", "perfect",
                "rule", "dream", "party", "heaven", "well", "wonderful"));
        
        vPosWords = new HashSet<>(Arrays.asList("haha", "hahaha", "happiest","yeah",
                "love", "loves", "loved", "loving", "beloved", "amazing", "amazed",
                "birthday", "luv", "ultimate", "best", "gorgeous", "wow", "fantastic",
                "incredible", "favorite", "lmao", "ily", "proud",  "luckiest", "cheer",
                "cheered", "cheering", "congrats", "congratulations", "rolf", "rolling",
                "rollin", "fucken", "greatest", "excellent", "excited", "exciting", "excite"));
        
        negWords = new HashSet<>(Arrays.asList("broken", "bad", "lost", "lose",
                "worse", "stuck", "stop", "stops", "annoyed", "annoying", "annoy",
                "noisy", "noisiest", "noisier", "hater", "hard", "harder", "shit",
                "poor", "ugh", "ughh", "ughhh", "tired", "sorry", "unlucky",
                "unfortunately", "pain", "pains", "negative", "miss", "missed",
                "missing", "ass", "disappointed", "fail", "damn"));
        
        vNegWords = new HashSet<>(Arrays.asList("liar", "bored", "boredom", "worst", 
                "sucks", "hurt", "painfull", "hate", "upset", "skank", "skanks", "bitch", 
                "mofo", "rip", "cry", "cries", "crying", "unfollow", "moody", "sick",
                "gtfo", "piss", "pissed", "unfollowing", "pissing", "crap", "crappy",
                "sad", "sadness", "motherfucker", "lying", "lies", "lie", "fuck",
                "hardest", "asshole", "angry", "dick", "hell", "hatin", "tear", "tears"));
        
        negations = new HashSet<>(Arrays.asList("not", "don't", "dont", "can't",
                "cant", "won't", "wont", "shouldn't", "no", "ain't", "aint",
                "cannot", "wasn't", "weren't", "wasnt", "werent"));
        
        posExp = new HashSet<>(Arrays.asList("happy birthday", "good morning", 
                "can't wait", "cant wait", "looking forward", "look forward", 
                "fingers crossed", "let's go", "lets go", "hell yeah", "thank you",
                "thank god", "good luck", "fuck yeah", "get along", "getting along",
                "well done"));
        
        negExp = new HashSet<>(Arrays.asList("can't stand", "fuck you", "fuck off", 
                "miss you", "missed you", "missing you", "too much", "cant stand",
                "middle finger", "get over", "getting over"));
    }
    
    public Set getPosWords() {
        return posWords;  
    }
    
    public Set getNegWords() {
        return negWords;  
    }
    
    public Set getNegations() {
        return negations;  
    }
    
    public Set getVPosWords() {
        return vPosWords;  
    }
    
    public Set getVNegWords() {
        return vNegWords;  
    }
    
    public Set getPosExp() {
        return posExp;  
    }
    
    public Set getNegExp() {
        return negExp;  
    }
}
