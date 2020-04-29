package com.example.recyclefirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db= FirebaseFirestore.getInstance();

    private CollectionReference notebookRef= db.collection("notebook");

    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAdd= findViewById(R.id.floatingActionButtonId);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,NewNodeActivity.class);
                startActivity(intent);

            }
        });

        setUpRecycleView();
    }

    private void setUpRecycleView() {

        Query query= notebookRef.orderBy("priority",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Note> options= new FirestoreRecyclerOptions.Builder<Note>().setQuery(query,Note.class).build();

        adapter= new NoteAdapter(options);

        RecyclerView recyclerView= findViewById(R.id.recycleViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

     new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
         @Override
         public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
             return false;
         }

         @Override
         public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

             adapter.deleteItem(viewHolder.getAdapterPosition());
         }
     }).attachToRecyclerView(recyclerView);

     adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
         @Override
         public void onItemClick(DocumentSnapshot documentSnapshot, int position) {

             Note note= documentSnapshot.toObject(Note.class);
             String id= documentSnapshot.getId();
             String path= documentSnapshot.getReference().getPath();

             Toast.makeText(MainActivity.this, "position : "+position+"Id : "+id, Toast.LENGTH_SHORT).show();
         }
     });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
