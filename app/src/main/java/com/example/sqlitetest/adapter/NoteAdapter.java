package com.example.sqlitetest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitetest.R;
import com.example.sqlitetest.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> mListNote;
    private Context mContext;

    public NoteAdapter(List<Note> list, Context context) {
        mListNote = list;
        mContext = context;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layput_note_line, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = mListNote.get(position);
        String noteContent = note.getContent();
        if (noteContent.contains("\n")) {
            int index = noteContent.indexOf("\n");
            noteContent = noteContent.substring(0, index);
        }
        holder.mTVTitle.setText(note.getTitle());
        holder.mTVContent.setText(noteContent);
    }

    @Override
    public int getItemCount() {
        return mListNote.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView mTVTitle;
        TextView mTVContent;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            mTVTitle = itemView.findViewById(R.id.tv_title);
            mTVContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
