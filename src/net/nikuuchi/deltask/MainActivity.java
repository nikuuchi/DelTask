package net.nikuuchi.deltask;

import java.util.ArrayList;
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

	private List<TaskModel> loadTaskList() {
		List<TaskModel> list = new ArrayList<TaskModel>();
		return list;
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
				TaskModel item = (TaskModel) parent.getItemAtPosition(position);
				String str = item.toString();
				try {
					str = item.toJson().toString(4);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
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
			//Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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
				adapter.add(new TaskModel(editText.getText().toString(),11,new Date().getTime(), 0, 0));
			}
		}).show().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

}
