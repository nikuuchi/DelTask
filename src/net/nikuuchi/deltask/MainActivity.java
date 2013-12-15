package net.nikuuchi.deltask;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Activity mActivity = this;
		
		List<TaskModel> list = new ArrayList<TaskModel>();
		for (int i = 0; i < 10; i++) {
			list.add(new TaskModel("hoge"+i, i, i, i, i));
		}
		
		ListView listView = (ListView) findViewById(R.id.TaskListView);
		TaskListItemAdapter adapter = new TaskListItemAdapter(mActivity, list);
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

}
