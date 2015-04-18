package com.example.graphviewsample.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class InternetConnectivity {
	static Context context;
	NetworkInfo activeNetwork;

	public InternetConnectivity(Context myContext) {
		this.context = myContext;
	}

	private boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null
				&& activeNetwork.isConnectedOrConnecting();
		return isConnected;
	}

	public boolean isConnectedActiveNetwork() {

		
		boolean isConnected = false;
		if (isConnected()) {
			boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
			boolean isMobileData = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
			if (isWiFi) {
				// wifi is available and active
				isConnected = true;
			} else if (isMobileData) {
				// wifi is available and active

				isConnected = true;
			} 
		}
		else {
			isConnected = false;
			//showNoConnectionDialog();
		}

		return isConnected;

	}
	 public static void showNoConnectionDialog() {
	
	        AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        builder.setCancelable(true);
	        builder.setMessage("Open Internet Settings");
	        builder.setTitle("No Internet Connections");
	        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
	            }
	        });
	        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                return;
	            }
	        });
	        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            public void onCancel(DialogInterface dialog) {
	                return;
	            }
	        });

	        builder.show();
	    }

}
