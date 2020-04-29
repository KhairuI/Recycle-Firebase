package com.example.recyclefirebase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteHolder> {

    private OnItemClickListener listener;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {

        holder.textViewTitle.setText(model.getTitle());
        holder.textViewDescprition.setText(model.getDescprition());
        holder.textViewPriority.setText(String.valueOf(model.getPriority()));

    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item,viewGroup,false);

        return new NoteHolder(v);
    }

    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class NoteHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView textViewDescprition;
        TextView textViewPriority;


        public NoteHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle= itemView.findViewById(R.id.text_view_title);
            textViewDescprition= itemView.findViewById(R.id.text_view_descprition);
            textViewPriority= itemView.findViewById(R.id.text_view_priority);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position= getAdapterPosition();
                   if(position!= RecyclerView.NO_POSITION && listener != null){

                       listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });


        }
    }

    public  interface OnItemClickListener{

        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }

    public  void setOnItemClickListener(OnItemClickListener listener){

        this.listener= listener;
    }
}
