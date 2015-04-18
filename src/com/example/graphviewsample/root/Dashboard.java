package com.example.graphviewsample.root;

import java.util.List;

import twitter4j.Status;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.example.graphviewsample.R;
import com.example.graphviewsample.controller.ProcessQuery;
import com.example.graphviewsample.interfaces.QueryResponse;
import com.example.graphviewsample.model.CustomAdapter;
import com.example.graphviewsample.model.TweetDataSource;
import com.example.graphviewsample.model.TweetModel;
import com.example.graphviewsample.testcase.MainActivity;
import com.example.graphviewsample.utilities.InternetConnectivity;
import com.jjoe64.graphview.GraphView;


public class Dashboard extends Activity implements OnClickListener, QueryResponse, ISideNavigationCallback {
	EditText searchForString;
	Button okSearh;
	TweetDataSource datasource;
	ListView listView;
	SideNavigationView sideNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		initSideNavigationView();
		searchForString = (EditText) findViewById(R.id.edit_search);
		okSearh = (Button) findViewById(R.id.ok_search);
		listView = (ListView)findViewById(R.id.tweeterList);
		
		okSearh.setOnClickListener(this);
		datasource = new TweetDataSource(Dashboard.this);
		datasource.open();
		InternetConnectivity connectivity = new InternetConnectivity(Dashboard.this);
		if(!connectivity.isConnectedActiveNetwork()){
			List<TweetModel> values = datasource.getAllComments();

			CustomAdapter adapter = new CustomAdapter(null, Dashboard.this,values);
			    listView.setAdapter(adapter);
			
		}
		

	}

	private void initSideNavigationView() {
		// TODO Auto-generated method stub
		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		sideNavigationView.setMenuItems(R.menu.menu);

		sideNavigationView.setMenuClickCallback(this);
		sideNavigationView.setMode(SideNavigationView.Mode.LEFT);
		if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.GINGERBREAD_MR1) {
			// only for gingerbread and newer versions
			getActionBar().setIcon(R.drawable.menu);
			getActionBar().setDisplayHomeAsUpEnabled(true);
		} else {
			Toast.makeText(this, "action bar not supported", Toast.LENGTH_LONG)
					.show();
		}

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			sideNavigationView.toggleMenu();
			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onSideNavigationItemClick(int itemId) {

		switch (itemId) {

		case R.id.graph_view_item:

			Intent in = new Intent(Dashboard.this, MainActivity.class);
			startActivity(in);
			finish();

			break;

		case R.id.home_item:
			Intent home = new Intent(Dashboard.this, Dashboard.class);
			startActivity(home);
			finish();
			break;
		
		case R.id.exit_item:
			ShowExitDialog();
			
			break;
		default:
			break;
		}
	}
	private void ShowExitDialog() {
		// TODO Auto-generated method stub
		 new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Exit Dialog")
	        .setMessage("Do You Really want To Exit")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

	            @Override
	            public void onClick(DialogInterface dialog, int which) {

	                //Stop the activity
	               finish();    
	            }

	        })
	        .setNegativeButton("Cancel", null)
	        .show();
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.edit_search:

			break;
		case R.id.ok_search:
			String searchFor = searchForString.getText().toString();
			new ProcessQuery(Dashboard.this, this).execute(searchFor);

			break;
		default:
			break;
		}

	}

	@Override
	public void onError(String errorResponse) {
		// TODO Auto-generated method stub
		System.out.println(errorResponse);
	}

	@Override
	public void onResponse(List<Status> list) {
		

		for (int i = 0; i < list.size(); i++) {
			
			
			datasource.createTweetModel(list.get(i).getText(), list.get(i).getCreatedAt().toString());
			
		}
		 
		 
		CustomAdapter adapter = new CustomAdapter(list, Dashboard.this,null);
		    listView.setAdapter(adapter);
		
		
	}

}
