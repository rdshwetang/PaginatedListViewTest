/* 
 * @author Shwetang R.D 
 * Visual Sistemas Electronicos Ltda.
 * Belo Horizonte-Brazil -2014
 */
package com.danielme.blog.android.paginatedlistview.activities;

import java.util.List;

import com.danielme.blog.android.paginatedlistview.CustomArrayAdapter;
import com.danielme.blog.android.paginatedlistview.Datasource;
import com.danielme.blog.android.paginatedlistview.R;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public abstract class AbstractListViewActivity extends ListActivity
{

	protected Datasource datasource;

	protected static final int PAGESIZE = 10;

	protected TextView textViewDisplaying;

	protected View footerView;

	protected boolean loading = false;

	protected class LoadNextPage extends AsyncTask<String, Void, String>
	{
		private List<String> newData = null;

		@Override
		protected String doInBackground(String... arg0)
		{
			// para que de tiempo a ver el footer ;)
			try
			{
				Thread.sleep(1500);
			}
			catch (InterruptedException e)
			{
				Log.e("AbstractListActivity", e.getMessage());
			}
			newData = datasource.getData(getListAdapter().getCount(), PAGESIZE);
			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			CustomArrayAdapter customArrayAdapter = ((CustomArrayAdapter) getListAdapter());
			for (String value : newData)
			{
				customArrayAdapter.add(value);
			}
			customArrayAdapter.notifyDataSetChanged();

			getListView().removeFooterView(footerView);
			updateDisplayingTextView();
			loading = false;
		}

	}

	protected void updateDisplayingTextView()
	{
		textViewDisplaying = (TextView) findViewById(R.id.displaying);
		String text = getString(R.string.display);
		text = String.format(text, getListAdapter().getCount(), datasource.getSize());
		textViewDisplaying.setText(text);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Toast.makeText(this, getListAdapter().getItem(position) + " " + getString(R.string.selected), Toast.LENGTH_SHORT).show();
	}
	
	protected boolean load(int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		boolean lastItem = firstVisibleItem + visibleItemCount == totalItemCount && getListView().getChildAt(visibleItemCount -1) != null && getListView().getChildAt(visibleItemCount-1).getBottom() <= getListView().getHeight();
		boolean moreRows = getListAdapter().getCount() < datasource.getSize();
		return moreRows && lastItem && !loading;
		
	}
}