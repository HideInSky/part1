package com.example.part1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Note> notes;

    Adapter (Context context, List<Note> notes){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_view_note, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("onbind in adapter", "start");
        String title = notes.get(position).getQuestionTitle();
        String date = notes.get(position).getDateOfCreate();
        String times = notes.get(position).getDoneTimes();
        holder.title.setText(title);
        holder.date.setText(date);
        holder.times.setText(times);
        Log.d("onbind in adapter", "end");

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, times;
        ViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.txt_question_title);
            date = itemView.findViewById(R.id.txt_date);
            times = itemView.findViewById(R.id.txt_times);

            // click any item to get a response
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),
                            "Get this item", Toast.LENGTH_SHORT).show();
                    Intent to_edit_note = new Intent(v.getContext(), EditNote.class);
                    to_edit_note.putExtra("ID", notes.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(to_edit_note);
                }
            });



        }


    }
}
