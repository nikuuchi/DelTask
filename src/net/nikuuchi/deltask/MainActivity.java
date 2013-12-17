package net.nikuuchi.deltask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TaskListItemAdapter adapter;
	Handler handler;
	TaskDBHelper helper;
	Timer timer;
	TimerTask timerTask;

	private List<Task> loadTaskList() {
		if(helper == null) {
			helper = new TaskDBHelper(this);
		}

		return TaskDBUtils.select(helper);
	}

	private void initTimer() {
		timer = new Timer();
		handler = new Handler();
		timerTask = new TimerTask() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
					}
				});
			}
		};
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Activity mActivity = this;
		initTimer();

		ListView listView = (ListView) findViewById(R.id.TaskListView);
		adapter = new TaskListItemAdapter(mActivity, loadTaskList());
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Task item = (Task) parent.getItemAtPosition(position);
				Date d = new Date();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:mm a");

		        if(item.getStartAt() == 0) {
					item.setStartAt(d.getTime());
					TaskDBUtils.update_startTime(helper, item);
		            Toast.makeText(mActivity, "Start:"+ sdf.format(d), Toast.LENGTH_SHORT).show();

				} else if(item.getEndAt() == 0) {
					item.setEndAt(new Date().getTime());
					TaskDBUtils.update_endTime(helper, item);
		            Toast.makeText(mActivity, "End:"+ sdf.format(d), Toast.LENGTH_SHORT).show();
				} else {
					TaskDBUtils.delete_logical(helper, item);
					adapter.remove(item);
		            Toast.makeText(mActivity, "Remove Task:"+ sdf.format(d), Toast.LENGTH_SHORT).show();
				}
	            adapter.notifyDataSetChanged();
			}
		});

		timer.schedule(timerTask, 1000, 1000);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initTimer();
		timer.schedule(timerTask, 1000, 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.action_new_task:
			this.newTask();
			return true;
		case R.id.action_settings:
			intent = new Intent(this, SettingsActivity.class);
			this.startActivity(intent);
			break;
		case R.id.action_summary:
			intent = new Intent(this, SummaryActivity.class);
			this.startActivity(intent);
            break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();
		timer.cancel();
		timer = null;
	}

	private void newTask() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View view = inflater.inflate(R.layout.input_dialog, null);
		final EditText editText = (EditText)view.findViewById(R.id.editText1);
		new AlertDialog.Builder(this)
			.setTitle(R.string.dialog_text)
			.setView(view)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Task t = TaskDBUtils.insert(helper, editText.getText().toString());
				adapter.add(t);
			}
		}).show().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

}
