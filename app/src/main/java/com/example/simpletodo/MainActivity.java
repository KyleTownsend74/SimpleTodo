package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final String KEY_ITEM_DATE = "item_date";
    public static final String KEY_ITEM_MONTH = "item_month";
    public static final String KEY_ITEM_DAY = "item_day";
    public static final String KEY_ITEM_YEAR = "item_year";
    public static final int EDIT_TEXT_CODE = 20;

    List<ListItem> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                items.remove(position);
                // Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("MainActivity", "Single click at position " + position);
                // create the new activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                // pass the data being edited
                i.putExtra(KEY_ITEM_TEXT, items.get(position).getText());
                i.putExtra(KEY_ITEM_POSITION, position);
                i.putExtra(KEY_ITEM_DATE, items.get(position).getUseDate());
                i.putExtra(KEY_ITEM_MONTH, items.get(position).getMonth());
                i.putExtra(KEY_ITEM_DAY, items.get(position).getDay());
                i.putExtra(KEY_ITEM_YEAR, items.get(position).getYear());
                // display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItemText = etItem.getText().toString();
                ListItem todoItem = new ListItem(todoItemText);
                // Add item to the model
                items.add(todoItem);
                // Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() - 1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    // handle the result of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            // Retrieve the updated values
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            boolean itemUseDate = data.getBooleanExtra(KEY_ITEM_DATE, false);
            String itemDateText = "";
            String itemMonth = data.getStringExtra(KEY_ITEM_MONTH);
            int itemDay = data.getIntExtra(KEY_ITEM_DAY, 1);
            int itemYear = data.getIntExtra(KEY_ITEM_YEAR, 2021);

            if(itemUseDate) {
                itemDateText = itemMonth + " " + itemDay + ", " + itemYear;
            }

            ListItem newItem = new ListItem(itemText, itemUseDate, itemDateText, itemMonth, itemDay, itemYear);

            // extract the original position of the edited item from the position key
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            // update the model at the right position with the new item
            items.set(position, newItem);
            // notify the adapter
            itemsAdapter.notifyItemChanged(position);
            // persist the changes
            saveItems();
            Toast.makeText(getApplicationContext(), "Item updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    // This function will load items by reading every line of the data file
    private void loadItems() {
        items = new ArrayList<>();

        try {
            List<String> readItems = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));

            for(int i = 0; i < readItems.size(); i++) {
                Scanner scanner = new Scanner(readItems.get(i));
                scanner.useDelimiter("~");
                ListItem newItem = new ListItem(scanner.next(), Boolean.valueOf(scanner.next()), scanner.next(),
                        scanner.next(), Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()));
                items.add(newItem);
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        } catch(NumberFormatException e) {
            Log.e("MainActivity", "Error loading items. Try deleting data.txt", e);
            items = new ArrayList<>();
        }
    }

    // This function saves items by writing them into the data file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}