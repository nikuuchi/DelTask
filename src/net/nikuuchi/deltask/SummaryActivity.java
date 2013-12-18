package net.nikuuchi.deltask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SummaryActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.summary, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case R.id.action_settings:
			intent = new Intent(this, SettingsActivity.class);
			this.startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_summary_dummy,
					container, false);

			int section = getArguments().getInt(ARG_SECTION_NUMBER);

			long start_time = 0;
			long end_time = 0;
			Calendar calender = Calendar.getInstance();
			int year = calender.get(Calendar.YEAR);
			int month = calender.get(Calendar.MONTH) + 1; // 0 - 11
			int day = calender.get(Calendar.DAY_OF_MONTH);
			Date today = null;
			try {
				today = DateFormat.getDateInstance().parse(String.format("%d/%d/%d", year,month,day));
			} catch (ParseException e) {
				e.printStackTrace();
				today = new Date();
			}

			switch (section) {
			case 1:
				start_time = today.getTime();
				end_time = today.getTime() + (60*60*24*1000);
				break;
			case 2:
				start_time = today.getTime() - (60*60*24*1000);
				end_time = today.getTime();
				break;
			default:
				Log.d("TaskDel", "default in SummaryActivity.onCreateView");
				break;
			}
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	        String start_time_formatted = sdf.format(new Date(start_time));

			TextView sectionLabelView = (TextView) rootView.findViewById(R.id.section_label);
			sectionLabelView.setText(start_time_formatted);

			TextView allTaskCountView = (TextView) rootView.findViewById(R.id.all_task_count);
			allTaskCountView.setText(String.format("%d", 3));

			TextView completedTaskView = (TextView) rootView.findViewById(R.id.completed_task_count);
			completedTaskView.setText(String.format("%d", 2));

			TextView TaskTimeView = (TextView) rootView.findViewById(R.id.task_time_count);
			TaskTimeView.setText(String.format("%02d:%02d:%02d", 2,4,5));

			return rootView;
		}
	}

}
