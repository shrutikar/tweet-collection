/* The main method
 * we get authentication and we use the other classes as needed
 * to extract the information on the tweets and the user
 */
package twittercrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterCrawlerMain {
     
    // to get own consumer key, follow tutorial found HERE:
    // http://consultingblogs.emc.com/nileeshabojjawar/archive/2010/03/18/twitter4j-oauth-generating-the-access-token.aspx
    
    private final static String CONSUMER_KEY = "********";
    private final static String CONSUMER_KEY_SECRET = "****************";
 
    public void start() throws TwitterException, IOException {
        Twitter twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
        RequestToken requestToken = twitter.getOAuthRequestToken();
        System.out.println("Authorization URL: \n" + requestToken.getAuthorizationURL());
        AccessToken accessToken = null;
 
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            try {
                System.out.print("Input PIN here: ");
                String pin = br.readLine();
                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            } catch (TwitterException te) {
                System.out.println("Failed to get access token, caused by: " + te.getMessage());
                System.out.println("Retry input PIN");
            }
        }
        
        
        // Save these!!!!
        System.out.println("Access Token: " + accessToken.getToken());
        System.out.println("Access Token Secret: " + accessToken.getTokenSecret());
    }
 
    public static void main(String[] args) throws TwitterException, IOException {
     
    /* We comment - uncomment each class to use as necessary
     * first we get the authentication
     * then we create 2 files with the working tweets from the ones given
     * and finally we take all the info asked
     */
       
        
        
    /* Get authentication (1st step!!!) */
        TwitterCrawlerMain t = new TwitterCrawlerMain();
        t.start();
    
    
    /* To crawl the not annotated tweets */
        //NotAnnotatedCrawler c = new NotAnnotatedCrawler();
        //c.run();
       
        
    /* To crawl the annotated tweets */
        //AnnotatedCrawler c = new AnnotatedCrawler();
        //c.run();
        
        
    /* To get the list of most used words - parser */
        //TweetParser parser = new TweetParser();
        //parser.run();
        
        
    /* To get info from the not annotated tweets */
        //NotAnnotatedInfo c = new NotAnnotatedInfo();
        //c.run();
        
        
    /* To get info from the not annotated tweets */
        //AnnotatedInfo c = new AnnotatedInfo();
        //c.run();
    }
}
