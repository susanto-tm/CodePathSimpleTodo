package com.example.simpletodo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EditActivity extends AppCompatActivity {

    EditText editItem;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editItem = findViewById(R.id.editItemText);
        saveButton = findViewById(R.id.saveButton);

        getSupportActionBar().setTitle("Edit Item");

        editItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Create intent that contains results of modified data
                Intent intent = new Intent();

                // Pass data (results of editing)
                intent.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString());
                intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                // Set the result of the intent
                setResult(RESULT_OK, intent);
                // Finish activity, close screen and go back
                finish();
            }
        });
    }
}
