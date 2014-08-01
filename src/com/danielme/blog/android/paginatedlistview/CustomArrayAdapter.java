/* 
 * @author Shwetang R.D 
 * Visual Sistemas Electronicos Ltda.
 * Belo Horizonte-Brazil -2014
 */
package com.danielme.blog.android.paginatedlistview;

import java.util.List;

import com.danielme.blog.android.paginatedlistview.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Custom adapter with "View Holder Pattern".
 * 
 * @author danielme.com
 * 
 */
public class CustomArrayAdapter extends ArrayAdapter<String>
{
	private LayoutInflater layoutInflater;

	public CustomArrayAdapter(Context context, List<String> objects)
	{
		super(context, 0, objects);
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// holder pattern
		Holder holder = null;
		if (convertView == null)
		{
			holder = new Holder();

			convertView = layoutInflater.inflate(R.layout.listview, null);
			holder.setTextView1((TextView) convertView.findViewById(R.id.textView1));
			holder.setTextView2((TextView) convertView.findViewById(R.id.textView2));
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder) convertView.getTag();
		}

		holder.getTextView1().setText(getItem(position));
		holder.getTextView2().setText(getItem(position));
		return convertView;
	}

}