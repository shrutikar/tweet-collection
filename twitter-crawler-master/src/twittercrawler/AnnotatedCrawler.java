/* We use this class to get the working annotated tweets
 * using the files that have ONLY the user ID and the tweet ID
 */
package twittercrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

class AnnotatedCrawler {
    
    final String CONSUMER_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxx";
    final String CONSUMER_KEY_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    final String ACCESS_TOKEN = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    final String ACCESS_TOKEN_SECRET = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    Status status;
    
    void run() throws FileNotFoundException, IOException {
        Twitter twitter = new TwitterFactory().getInstance();;
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
        AccessToken accessToken = new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
        twitter.setOAuthAccessToken(accessToken);
        
        FileInputStream fInstream = new FileInputStream("annotated.txt");
        DataInputStream in = new DataInputStream(fInstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        FileWriter fOutstream = new FileWriter("annotatedOut.txt");
        BufferedWriter out = new BufferedWriter(fOutstream);
        
        String strLine;
        int i = 0;
        while ((strLine = br.readLine()) != null) {
            i++;
            int index = strLine.indexOf("\t");
            String userId = strLine.substring(0, index);
            String str2 = strLine.substring(index + "\t".length());
            
            index = str2.indexOf("\t");
            String tweetId = str2.substring(0, index);
            String annotation = str2.substring(index + "\t".length());
            try {
                status = twitter.showStatus(Long.parseLong(tweetId));
                String result = userId + "\t" + tweetId + "\t" + status.getText() + " \t" + annotation + "\n";
                out.write(result);
            } catch (TwitterException ex) {
                System.out.println("Twitter error at " + i);
                ex.printStackTrace();
            }
            
            // due to rate limit of twitter, we stop at 180 messages and continue
            // after 15 minutes
            if (i == 180) {
                break; }
        }
        in.close();
        out.close();
    } 
}
