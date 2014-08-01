/* 
 * @author Shwetang R.D 
 * Visual Sistemas Electronicos Ltda.
 * Belo Horizonte-Brazil -2014
 */
package com.danielme.blog.android.paginatedlistview.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.danielme.blog.android.paginatedlistview.CustomArrayAdapter;
import com.danielme.blog.android.paginatedlistview.Datasource;
import com.danielme.blog.android.paginatedlistview.R;

/**
 * 
 * @author http://danielme.com
 * 
 */
public class PagingButtonsListViewActivity extends AbstractListViewActivity
{

	private int offset = 0;

	private ProgressBar progressBar;

	private Button first;

	private Button last;

	private Button prev;

	private Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buttons);

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		textViewDisplaying = (TextView) findViewById(R.id.displaying);
		first = (Button) findViewById(R.id.buttonfirst);
		prev = (Button) findViewById(R.id.buttonprev);
		next = (Button) findViewById(R.id.buttonnext);
		last = (Button) findViewById(R.id.buttonlast);
		datasource = Datasource.getInstance();

		setListAdapter(new CustomArrayAdapter(this, new ArrayList<String>()));

		(new LoadNextPage()).execute();
	}

	private class LoadNextPage extends AsyncTask<String, Void, String>
	{
		private List<String> newData = null;

		@Override
		protected void onPreExecute()
		{
			progressBar.setVisibility(View.VISIBLE);
			loading = true;
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... arg0)
		{
			// para que de tiempo a ver la animaci??n
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				Log.e("PagingButtons", e.getMessage());
			}
			newData = datasource.getData(offset, PAGESIZE);
			return null;
		}

		@Override
		protected void onPostExecute(String result)
		{
			CustomArrayAdapter customArrayAdapter = ((CustomArrayAdapter) getListAdapter());
			customArrayAdapter.clear();
			for (String value : newData)
			{
				customArrayAdapter.add(value);
			}
			customArrayAdapter.notifyDataSetChanged();

			updateDisplayingTextView();

			loading = false;
		}

	}

	@Override
	protected void updateDisplayingTextView()
	{
		String text = getString(R.string.displayshort);
		text = String.format(text, Math.min(datasource.getSize(), offset + 1), Math.min(offset + PAGESIZE, datasource.getSize()), datasource.getSize());
		textViewDisplaying.setText(text);
		updateButtons();
		progressBar.setVisibility(View.INVISIBLE);
	}

	private void updateButtons()
	{
		if (getCurrentPage() > 1)
		{
			first.setEnabled(true);
			prev.setEnabled(true);
		}
		else
		{
			first.setEnabled(false);
			prev.setEnabled(false);
		}
		if (getCurrentPage() < getLastPage())
		{
			next.setEnabled(true);
			last.setEnabled(true);
		}
		else
		{
			next.setEnabled(false);
			last.setEnabled(false);
		}

	}

	private int getLastPage()
	{
		return (int) (Math.ceil((float) datasource.getSize() / PAGESIZE));
	}

	private int getCurrentPage()
	{
		return (int) (Math.ceil((float) (offset + 1) / PAGESIZE));
	}

	/******************** BUTTONS *************************/

	public void first(View v)
	{
		if (!loading)
		{
			offset = 0;
			(new LoadNextPage()).execute();
		}
	}

	public void next(View v)
	{
		if (!loading)
		{
			offset = getCurrentPage() * PAGESIZE;
			(new LoadNextPage()).execute();
		}
	}

	public void previous(View v)
	{
		if (!loading)
		{
			offset = (getCurrentPage() - 2) * PAGESIZE;
			(new LoadNextPage()).execute();
		}
	}

	public void last(View v)
	{
		if (!loading)
		{
			offset = (getLastPage() - 1) * PAGESIZE;
			(new LoadNextPage()).execute();
		}
	}

}
