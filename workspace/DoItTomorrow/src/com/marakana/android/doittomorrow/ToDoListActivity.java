package com.marakana.android.doittomorrow;

import com.marakana.android.doittomorrow.provider.ToDoDatabaseOpenHandler;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleCursorAdapter;

public class ToDoListActivity extends ListActivity {

	private SimpleCursorAdapter mAdapter;
	private Cursor mCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mCursor = ToDoDatabaseOpenHandler.getInstance(this).getReadableDatabase()
			.query(ToDoDatabaseOpenHandler.TODO_TABLE,
				   new String[] { ToDoDatabaseOpenHandler.KEY_ID, ToDoDatabaseOpenHandler.KEY_CONTENT },
				   null, null, null, null,
				   ToDoDatabaseOpenHandler.KEY_CREATED_AT + " DESC");
		startManagingCursor(mCursor);
		
		mAdapter = new SimpleCursorAdapter(this,
					android.R.layout.simple_list_item_1,
					mCursor,
					new String[] {ToDoDatabaseOpenHandler.KEY_CONTENT},
					new int[] {android.R.id.text1});
		setListAdapter(mAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_todo_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int id = item.getItemId();
		Intent intent;
		if (R.id.action_new_todo == id) {
			// Create new to do item
			intent = new Intent(this, NewToDoActivity.class);
			startActivity(intent);
			return true;
		}
		if (R.id.action_clear_todos == id) {
			// Delete all to do items
			clearToDos();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void clearToDos() {
		ToDoDatabaseOpenHandler.getInstance(this).getReadableDatabase()
			.delete(ToDoDatabaseOpenHandler.TODO_TABLE, null, null);
		mCursor.requery();
		mAdapter.notifyDataSetChanged();
	}

}
