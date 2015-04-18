package com.example.graphviewsample.testcase;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

public class TwitterValidation extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		new myAsynchTask().execute();

	}

	class myAsynchTask extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			main();
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}

	public static void main() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("HMtvp2bzPXcQjImR545Yvwb8U")
				.setOAuthConsumerSecret(
						"E9vNDzV6jTncQkBjxYyLFwfEAgbM8kHGvYj4JE2UOqKzwbxIF7")
				.setOAuthAccessToken(
						"1734421237-8QmEOTUgrshXFqR6EFy1RAruGMDfdgEwbbHphCt")
				.setOAuthAccessTokenSecret(
						"W7G1Zjghr7oW9X7kXpltbrPE40A25MOvBE1j7i1tWUK64");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		try {
			Query query = new Query("limetray");
			QueryResult result;
			result = twitter.search(query);
			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets) {
				System.out.println("@" + tweet.getUser().getScreenName()
						+ " - " + tweet.getText() + "  date = "
						+ tweet.getCreatedAt());
			}

			System.exit(0);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
	}
}
