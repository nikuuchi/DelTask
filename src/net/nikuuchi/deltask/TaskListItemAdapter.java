package net.nikuuchi.deltask;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskListItemAdapter extends ArrayAdapter<Task> {
	private LayoutInflater mLayoutInflater;

	public TaskListItemAdapter(Context context, List<Task> objects) {
		super(context, 0/*Custom List Item*/, objects);
		this.setmLayoutInflater((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;

		if (convertView == null) {
            view = mLayoutInflater.inflate(R.layout.task_item, parent, false);
        } else {
            view = convertView;
        }

        Task item = getItem(position);

        TextView text1 = (TextView) view.findViewById(R.id.TitleText);
        text1.setText("Task: " + item.getTitle());
        TextView text2 = (TextView) view.findViewById(R.id.CreatedTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd h:mm a");
        String str = sdf.format(new Date(item.getCreatedAt()));
        text2.setText(str);

        return view;
	}
	
	public LayoutInflater getmLayoutInflater() {
		return mLayoutInflater;
	}

	private void setmLayoutInflater(LayoutInflater mLayoutInflater) {
		this.mLayoutInflater = mLayoutInflater;
	}

}
