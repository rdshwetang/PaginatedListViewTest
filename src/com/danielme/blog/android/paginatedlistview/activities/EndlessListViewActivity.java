/* 
 * @author Shwetang R.D 
 * Visual Sistemas Electronicos Ltda.
 * Belo Horizonte-Brazil -2014
 */

package com.danielme.blog.android.paginatedlistview.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.danielme.blog.android.paginatedlistview.CustomArrayAdapter;
import com.danielme.blog.android.paginatedlistview.Datasource;
import com.danielme.blog.android.paginatedlistview.R;

public class EndlessListViewActivity extends AbstractListViewActivity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.endless);
		datasource = Datasource.getInstance();
		footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
		getListView().addFooterView(footerView, null, false);
		setListAdapter(new CustomArrayAdapter(this, datasource.getData(0, PAGESIZE)));
		getListView().removeFooterView(footerView);

		getListView().setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1)
			{
				// nothing here
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				if (load(firstVisibleItem, visibleItemCount, totalItemCount))
				{
					loading = true;
					getListView().addFooterView(footerView, null, false);
					(new LoadNextPage()).execute("");
				}
			}
		});
		updateDisplayingTextView();
	}
}