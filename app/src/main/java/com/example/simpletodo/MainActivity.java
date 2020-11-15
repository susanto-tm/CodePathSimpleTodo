package com.example.simpletodo;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> todoItems;
    Button addButton;
    EditText editText;
    RecyclerView recyclerView;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        editText = findViewById(R.id.editText);
        recyclerView = findViewById(R.id.recyclerView);

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Delete item from model
                todoItems.remove(position);
                // Notify adapter the position we performed the delete
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at position " + position);
                // Create new activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                // Pass data being edited
                i.putExtra(KEY_ITEM_TEXT, todoItems.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                // Display activity
                startActivityForResult(i, EDIT_TEXT_CODE);

            }
        };

        itemsAdapter = new ItemsAdapter(todoItems, onLongClickListener, onClickListener);
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = editText.getText().toString();
                // Add Item to model
                todoItems.add(todoItem);
                // Notify adapter an item is inserted
                itemsAdapter.notifyItemInserted(todoItems.size() - 1);
                editText.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 20 && resultCode == RESULT_OK) {
            // Retrieve the updated text value
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            // Extract original position of edited item from the position key
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            // Update the model at the right position with the new text
            todoItems.set(position, itemText);

            // Notify the adapter
            itemsAdapter.notifyItemChanged(position);

            // Persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(), "Item updated successfully", Toast.LENGTH_SHORT).show();

        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // Load items by reading every line of the data file
    private void loadItems() {
        try {
            todoItems = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            todoItems = new ArrayList<>();
        }
    }
    // Saves items by writing them into the data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), todoItems);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}
