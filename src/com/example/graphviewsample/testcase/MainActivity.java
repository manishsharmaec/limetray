package com.example.graphviewsample.testcase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.SideNavigationView;
import com.example.graphviewsample.R;
import com.example.graphviewsample.root.Dashboard;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries.Shape;

public class MainActivity extends Activity implements ISideNavigationCallback{
	SideNavigationView sideNavigationView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initSideNavigationView();
		GraphView graph = (GraphView) findViewById(R.id.graph);

		double[] xaxis = new double[] { 0, 1, 2, 3, 4, 5 };
		double[] yaxis = new double[] { 1, 5, 3, 2, 6 };
		DataPoint[] datapoint = new DataPoint[5];
		for (int i = 0; i < datapoint.length; i++) {

			double x = xaxis[i];
			double y = yaxis[i];

			datapoint[i] = new DataPoint(x, y);
		}

		BarGraphSeries<DataPoint> series2 = new BarGraphSeries<DataPoint>(
				datapoint);
		series2.setSpacing(10);
		LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(
				new DataPoint[] { new DataPoint(0, 1), new DataPoint(1, 5),
						new DataPoint(2, 3), new DataPoint(3, 2),
						new DataPoint(4, 6) });
		series.setThickness(5);

		PointsGraphSeries<DataPoint> series1 = new PointsGraphSeries<DataPoint>(
				new DataPoint[] { new DataPoint(0, 1), new DataPoint(1, 5),
						new DataPoint(2, 3), new DataPoint(3, 2),
						new DataPoint(4, 6) });

		series1.setShape(Shape.POINT);

		// series.setColor(Color.RED);
		// graph.setBackgroundColor(Color.BLUE);
		// series.setValuesOnTopSize(50);
		// graph.setBackgroundResource(R.drawable.ic_launcher);

		graph.addSeries(series);
		graph.addSeries(series1);
		graph.addSeries(series2);
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

			Intent in = new Intent(this, MainActivity.class);
			startActivity(in);
			finish();

			break;

		case R.id.home_item:
			Intent home = new Intent(this, Dashboard.class);
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
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								// Stop the activity
								finish();
							}

						}).setNegativeButton("Cancel", null).show();

	}

}
