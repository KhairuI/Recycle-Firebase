package com.example.recyclefirebase;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewNodeActivity extends AppCompatActivity {

    private EditText editTexttitle,editTextDescription;
    private NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_node);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add note ");

        editTexttitle= findViewById(R.id.edit_text_title);
        editTextDescription= findViewById(R.id.edit_text_description);
        numberPicker= findViewById(R.id.number_picker_Priority);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.save_note){
            saveNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveNote() {

        String title= editTexttitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPicker.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(NewNodeActivity.this,"Please insert title and description",Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference notebookRef= FirebaseFirestore.getInstance().collection("notebook");

        notebookRef.add(new Note(title,description,priority));
        Toast.makeText(NewNodeActivity.this,"note added",Toast.LENGTH_SHORT).show();
        finish();
    }
}
