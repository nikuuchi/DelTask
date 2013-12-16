package net.nikuuchi.deltask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
	TaskDBHelper helper;

	private List<Task> loadTaskList() {
		if(helper == null) {
			helper = new TaskDBHelper(this);
		}

		return TaskDBUtils.select(helper);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Activity mActivity = this;
		

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
				} else {
					item.setEndAt(new Date().getTime());
					TaskDBUtils.update_startTime(helper, item);
		            Toast.makeText(mActivity, "End:"+ sdf.format(d), Toast.LENGTH_SHORT).show();
				}
	            adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_task:
			this.newTask();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();
		//TODO
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
