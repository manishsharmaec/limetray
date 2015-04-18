package com.example.graphviewsample.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.example.graphviewsample.constants.Constants;
import com.example.graphviewsample.interfaces.QueryResponse;

public class ProcessQuery extends AsyncTask<String, String, List<Status>> {
	QueryResponse qReponse;

	Context context;
	ProgressDialog dialog;
	final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
	final static String TwitterStreamURL = "https://api.twitter.com/1.1/search/tweets.json?&since=2014-05-01&q=";

	public ProcessQuery(Context mContext, QueryResponse response) {
		qReponse = response;

		context = mContext;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog = new ProgressDialog(context);
		dialog.setTitle("Tweets Fetching");
		dialog.setMessage("Fetching tweets please wait...");
		dialog.setCancelable(true);
		dialog.show();
	}

	@Override
	protected List<twitter4j.Status> doInBackground(String... params) {
		// TODO Auto-generated method stub
		List<twitter4j.Status> list = null;
		list = getTweetList(params[0]);
//		 list = getAllTwitter(params[0]);
		return list;
	}

	@Override
	protected void onPostExecute(List<twitter4j.Status> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result != null) {
			dialog.cancel();
			qReponse.onResponse(result);
		}

	}

	public List<twitter4j.Status> getTweetList(String searchForValue) {
		List<twitter4j.Status> tweets = null;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(Constants.ConsumerKey)
				.setOAuthConsumerSecret(Constants.ConsumerSecretKey)
				.setOAuthAccessToken(Constants.AccessToken)
				.setOAuthAccessTokenSecret(Constants.AccessTokenSecret);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		Paging paging = new Paging(2, 100);

		try {
			Query query = new Query(searchForValue);
			QueryResult result;

			result = twitter.search(query);
			tweets = result.getTweets();

		} catch (Exception te) {
			te.printStackTrace();
			// System.out.println("Failed to search tweets: " +
			// te.getMessage());
			System.exit(-1);
			qReponse.onError(te.getMessage());
			dialog.cancel();
		}
		return tweets;
	}

	private List<twitter4j.Status> getAllTwitter(String searchForQuery) {
		String results = null;
		List<twitter4j.Status> tweets = null;

		// Step 1: Encode consumer key and secret
		try {
			// URL encode the consumer key and secret
			String urlApiKey = URLEncoder
					.encode(Constants.ConsumerKey, "UTF-8");
			String urlApiSecret = URLEncoder.encode(
					Constants.ConsumerSecretKey, "UTF-8");

			// Concatenate the encoded consumer key, a colon character, and the
			// encoded consumer secret
			String combined = urlApiKey + ":" + urlApiSecret;

			// Base64 encode the string
			String base64Encoded = Base64.encodeToString(combined.getBytes(),
					Base64.NO_WRAP);

			// Step 2: Obtain a bearer token
			HttpPost httpPost = new HttpPost(TwitterTokenURL);
			httpPost.setHeader("Authorization", "Basic " + base64Encoded);
			httpPost.setHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
			HttpClient client = new DefaultHttpClient();
			HttpResponse response = client.execute(httpPost);

			String rawAuthorization = EntityUtils
					.toString(response.getEntity());
			Log.e("raw authoriazation", rawAuthorization);
			String[] rawFetched = parseRawAuth(rawAuthorization);

			if (rawFetched[0].equals("bearer")) {

				// Step 3: Authenticate API requests with bearer token
				HttpGet httpGet = new HttpGet(TwitterStreamURL + searchForQuery);

				// construct a normal HTTPS request and include an Authorization
				// header with the value of Bearer <>
				httpGet.setHeader("Authorization", "Bearer " + rawFetched[1]);
				httpGet.setHeader("Content-Type", "application/json");
				// update the results with the body of the response
				response = client.execute(httpGet);

				String allTweets = EntityUtils.toString(response.getEntity());
				Log.e("searched query", allTweets);

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tweets;
	}

	public String[] parseRawAuth(String json) {
		String token_type = null, access_token = null;
		try {
			JSONObject object = new JSONObject(json);
			token_type = object.getString("token_type");
			access_token = object.getString("access_token");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new String[] { token_type, access_token };

	}

}
