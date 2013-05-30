package com.marakana.android.doittomorrow;

import com.marakana.android.doittomorrow.provider.ToDoDatabaseOpenHandler;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class NewToDoActivity extends Activity implements OnClickListener {

	private CheckBox mCheckboxCompleted;
	private Spinner mSpinnerPriority;
	private EditText mEditToDoContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_todo);
		
		mCheckboxCompleted = (CheckBox) findViewById(R.id.checkbox_completed);
		mSpinnerPriority = (Spinner) findViewById(R.id.spinner_priority);
		mEditToDoContent = (EditText) findViewById(R.id.edit_todo_content);
		
		Button buttonCreate = (Button) findViewById(R.id.button_create_todo_item);
		buttonCreate.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		// Get all to do item properties
		final int maxPriority = getResources().getStringArray(R.array.priority_entries).length;
		final int itemPriority
				= Math.min(maxPriority, Math.max(0, mSpinnerPriority.getSelectedItemPosition()));
		final String itemContent = mEditToDoContent.getText().toString();
		final int itemCompleted = mCheckboxCompleted.isChecked() ? 1 : 0;
		final long itemCreatedAt = System.currentTimeMillis();
		
		// Insert new to do item
		
		ContentValues values = new ContentValues();
		values.put(ToDoDatabaseOpenHandler.KEY_CONTENT, itemContent);
		values.put(ToDoDatabaseOpenHandler.KEY_PRIORITY, itemPriority);
		values.put(ToDoDatabaseOpenHandler.KEY_COMPLETED, itemCompleted);
		values.put(ToDoDatabaseOpenHandler.KEY_CREATED_AT, itemCreatedAt);
		
		ToDoDatabaseOpenHandler.getInstance(this).getWritableDatabase()
				.insert(ToDoDatabaseOpenHandler.TODO_TABLE, null, values);
		
		// Close activity
		finish();
	}

}
