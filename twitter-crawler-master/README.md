TwitterCrawler
==============

A simple twitter crawler. Uses tweets/tweetIDs and mines information. Sample .txt files are included, and it is shown how from the id's or raw text we mine sentiment analysis information. The format of the .txt files created are for use in Weka.

Using the twitter4j library for the twitter interaction with the program. http://twitter4j.org/en/index.html



How to Use
===============

<h3>Phase 1:</h3>
We have athenticate our application through Twitter, so that we can use the API.<br>
Classes:<b>TwitterCrawlerMain.java</b>

<h3>Phase 2:</h3>
We have two text files containing id's and tweet id's, of which one is annotated and the other is not. We use the tweet id's  to get the text which we will later analyze.<br>
Classes: <b>AnnotatedCrawler.java</b>, <b>NotAnnotatedCrawler.java</b>

<h3>Phase 3:</h3>
We perform the analysis depending on 2 types of info: person id and tweet text.
<ul>
<li>We grab all the relevant info from the Twitter API such as followers, following, date, etc.</li>
<li>Using a series of emoticons, positive/negatives words, expressions, exclamation/question marks, etc we try to determine the sentiment of the tweet.</li>
</ul>
Classes: <b>AnnotatedInfo.java</b>, <b>NotAnnotatedInfo.java</b>

<h3>Phase 0 (Optional):</h3>
We can use the class <b>TweetParser.java</b> to determine the most commonly found words in the text file containing the tweets.


Weka Template
===============

The following is the template to be used with the text files, to create a valid .arff file.<br><br>


@RELATION tweets<br><br>

@ATTRIBUTE tweet-id NUMERIC<br>
@ATTRIBUTE user-id NUMERIC<br>
@ATTRIBUTE followers NUMERIC<br>
@ATTRIBUTE following NUMERIC<br>
@ATTRIBUTE timestamp STRING<br>
@ATTRIBUTE retweets NUMERIC<br>
@ATTRIBUTE favorited NUMERIC<br>
@ATTRIBUTE hyperlinks NUMERIC<br>
@ATTRIBUTE positive-words NUMERIC<br>
@ATTRIBUTE negative-words NUMERIC<br>
@ATTRIBUTE very-positive-words NUMERIC<br>
@ATTRIBUTE very-negative-words NUMERIC<br>
@ATTRIBUTE positive-exp NUMERIC<br>
@ATTRIBUTE negative-exp NUMERIC<br>
@ATTRIBUTE positive-ratio REAL<br>
@ATTRIBUTE negative-ratio REAL<br>
@ATTRIBUTE very-positive-ratio REAL<br>
@ATTRIBUTE very-negative-ratio REAL<br>
@ATTRIBUTE negations NUMERIC<br>
@ATTRIBUTE letter-repeat NUMERIC<br>
@ATTRIBUTE exclamation-marks NUMERIC<br>
@ATTRIBUTE question-marks NUMERIC<br>
@ATTRIBUTE quotation-marks NUMERIC<br>
@ATTRIBUTE uppercase NUMERIC<br>
@ATTRIBUTE lowercase NUMERIC<br>
@ATTRIBUTE positive-emoticons NUMERIC<br>
@ATTRIBUTE negative-emoticons NUMERIC<br>
@ATTRIBUTE very-positive-emoticons NUMERIC<br>
@ATTRIBUTE very-negative-emoticons NUMERIC<br>
@ATTRIBUTE class {1,0,-1}<br><br>

@DATA<br>
/* the text files included */


