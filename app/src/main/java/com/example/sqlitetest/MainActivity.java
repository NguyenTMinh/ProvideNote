package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.example.sqlitetest.adapter.NoteAdapter;
import com.example.sqlitetest.fragment.NoteFragment;
import com.example.sqlitetest.iface.ISendData;
import com.example.sqlitetest.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ISendData {
    private NoteAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFABAdd;
    private List<Note> mNoteList;
    private MyDBHelper mMyDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init database
        mMyDBHelper = new MyDBHelper(this);

        //  list of note
        mNoteList = mMyDBHelper.getNotes();

        // Init recycler view for displaying the list
        mAdapter = new NoteAdapter(mNoteList, this);
        mRecyclerView = findViewById(R.id.rv_list_note);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        // Init the button
        mFABAdd = findViewById(R.id.fab_add_note);
        mFABAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addNote() {
        NoteFragment noteFragment = new NoteFragment();
        noteFragment.show(getSupportFragmentManager(), null);
    }

    @Override
    public void saveNote(String title, String content) {
        int last_id = (mNoteList.size() > 0)? mNoteList.get(mNoteList.size()-1).getID() + 1: 0;
        Note note = new Note(last_id, title, content);
        mNoteList.add(note);
        mAdapter.notifyDataSetChanged();
        mMyDBHelper.insertNote(note);
    }
}