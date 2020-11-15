package com.example.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EditActivity extends AppCompatActivity {

    private String editItemState;
    private String editDescState;

    EditText editItem;
    EditText editDesc;
    Button saveButtonItem;
    Button saveButtonDesc;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editItem = findViewById(R.id.editItemText);
        editDesc = findViewById(R.id.editDesc);
        saveButtonItem = findViewById(R.id.saveButton);
        saveButtonDesc = findViewById(R.id.saveButtonDesc);
        doneButton = findViewById(R.id.doneButton);

        getSupportActionBar().setTitle("Edit Item");

        // Initialize edit screen
        editItemState = getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT);
        editDescState = getIntent().getStringExtra(MainActivity.KEY_ITEM_DESC);
        editItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));
        editDesc.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_DESC));

        saveButtonItem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Override value if changed
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                editItemState = editItem.getText().toString();
                Toast.makeText(getApplicationContext(), "Todo Item Updated", Toast.LENGTH_SHORT).show();
            }
        });

        saveButtonDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Override value if changed
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                editItemState = editItem.getText().toString();
                editDescState = editDesc.getText().toString();
                Toast.makeText(getApplicationContext(), "Todo Description Updated", Toast.LENGTH_SHORT).show();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent that contains results of modified data
                Intent intent = new Intent();

                // Pass data (results of editing)
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editItemState);
                intent.putExtra(MainActivity.KEY_ITEM_DESC, editDescState);
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                // Set the result of the intent
                setResult(RESULT_OK, intent);

                // Finish activity, close screen and go back
                finish();
            }
        });

    }
}
