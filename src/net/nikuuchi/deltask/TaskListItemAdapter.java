package net.nikuuchi.deltask;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskListItemAdapter extends ArrayAdapter<TaskModel> {
	private LayoutInflater mLayoutInflater;

	public TaskListItemAdapter(Context context, List<TaskModel> objects) {
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

        TaskModel item = getItem(position);

        TextView text1 = (TextView) view.findViewById(R.id.TitleText);
        text1.setText("Title:" + item.getTitle());
        TextView text2 = (TextView) view.findViewById(R.id.CreatedTime);
        text2.setText("SubTitle:" + item.getCreatedAt());

        return view;
	}
	
	public LayoutInflater getmLayoutInflater() {
		return mLayoutInflater;
	}

	private void setmLayoutInflater(LayoutInflater mLayoutInflater) {
		this.mLayoutInflater = mLayoutInflater;
	}

}