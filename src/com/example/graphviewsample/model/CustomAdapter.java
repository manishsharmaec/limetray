package com.example.graphviewsample.model;

import java.util.List;

import twitter4j.Status;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.graphviewsample.R;

public class CustomAdapter extends BaseAdapter {
	List<Status> tweeterStatusMdel;
	Context context;
	List<TweetModel> tweetModel;

	public CustomAdapter(List<Status> statusmodel, Context mcontext,
			List<TweetModel> tweetmodel) {
		if (statusmodel != null) {
			tweeterStatusMdel = statusmodel;
		}
		if (tweetmodel != null) {
			tweetModel = tweetmodel;

		}
		context = mcontext;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int size = 0;
		if (tweeterStatusMdel != null) {
			size = tweeterStatusMdel.size();
		} else if (tweetModel != null) {
			size = tweetModel.size();

		}
		return size;

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Object object = null;
		if (tweeterStatusMdel != null) {
			object = tweeterStatusMdel.get(position);
		} else if (tweetModel != null) {
			object = tweetModel.get(position);

		}

		return object;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.custom_view, null);
		TextView text, datetime;
		text = (TextView) v.findViewById(R.id.tweetText);
		datetime = (TextView) v.findViewById(R.id.datetime);
		if (tweeterStatusMdel != null) {
			text.setText(tweeterStatusMdel.get(position).getText());
			datetime.setText(tweeterStatusMdel.get(position).getCreatedAt().toLocaleString());
		} else if (tweetModel != null) {
			text.setText(tweetModel.get(position).getTweetMessage());
			datetime.setText(tweetModel.get(position).getTweetDateTime());

		}

		return v;
	}

}
