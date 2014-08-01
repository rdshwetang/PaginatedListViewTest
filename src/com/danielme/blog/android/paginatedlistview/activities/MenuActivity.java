/* 
 * @author Shwetang R.D 
 * Visual Sistemas Electronicos Ltda.
 * Belo Horizonte-Brazil -2014
 */
package com.danielme.blog.android.paginatedlistview.activities;

import com.danielme.blog.android.paginatedlistview.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}

	public void endless(View button)
	{
		startActivity(new Intent(this, EndlessListViewActivity.class));
	}

	public void loadmore(View button)
	{
		startActivity(new Intent(this, LoadMoreListViewActivity.class));
	}

	public void buttons(View button)
	{
		startActivity(new Intent(this, PagingButtonsListViewActivity.class));
	}
}