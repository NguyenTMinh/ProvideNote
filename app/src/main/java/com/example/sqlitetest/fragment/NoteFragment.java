package com.example.sqlitetest.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.sqlitetest.R;
import com.example.sqlitetest.iface.ISendData;

public class NoteFragment extends DialogFragment {
    private EditText mETTitle;
    private EditText mETContent;
    private Button mBSave;
    private Button mBCancel;
    private ISendData mISendData;
    private int mDialogWidth;
    private int mDialogHeight;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ISendData) {
            mISendData = (ISendData) context;
        }
        mDialogWidth = (int) context.getResources().getDimension(R.dimen.dialog_width);
        mDialogHeight = (int) context.getResources().getDimension(R.dimen.dialog_height);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setLayout(mDialogWidth, mDialogHeight);
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       mETTitle = view.findViewById(R.id.et_title);
       mETContent = view.findViewById(R.id.et_content);
       mBSave = view.findViewById(R.id.bt_save);
       mBCancel = view.findViewById(R.id.bt_cancel);

       // Set action click event button
        mBSave.setOnClickListener(v -> onSaveButton());
        mBCancel.setOnClickListener(v -> onCancelButton());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private void onCancelButton() {
        this.dismiss();
    }

    private void onSaveButton() {
        String title = mETTitle.getText().toString().trim();
        String content = mETContent.getText().toString().trim();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            if (TextUtils.isEmpty(title)) {
                mETTitle.setError("Please enter title");
            }
            if (TextUtils.isEmpty(content)) {
                mETContent.setError("Please enter content");
            }
        } else {
            mISendData.saveNote(title, content);
            this.dismiss();
        }
    }
}
