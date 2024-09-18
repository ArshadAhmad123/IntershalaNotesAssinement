package com.example.intershalanotesassinement.fragment;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.intershalanotesassinement.R;
import com.example.intershalanotesassinement.database.NoteDbHelper;


public class AddNoteFragment extends Fragment {
       EditText topic, summary;
       Button save;
       private NoteDbHelper noteDbHelper;
       private String summaryPassArg;
       private String topicPassArg;
       private int flag;
       private int id;
       public AddNoteFragment() {
        // Required empty public constructor
       }


    // TODO: Rename and change types and number of parameters
    public static AddNoteFragment newInstance(int id,String summary, String topic,int flag) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putString("summary", summary);
        args.putString("topic", topic);
        args.putInt("id",id);
        args.putInt("flag",flag);
        fragment.setArguments(args);
        return fragment;
    }
    public static AddNoteFragment newInstance1(int flag) {
        AddNoteFragment fragment = new AddNoteFragment();
        Bundle args = new Bundle();
        args.putInt("flag",flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            summaryPassArg = getArguments().getString("summary");
            topicPassArg = getArguments().getString("topic");
            flag=getArguments().getInt("flag");
            id=getArguments().getInt("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_note, container, false);
       topic =  v.findViewById(R.id.topic);
       summary =  v.findViewById(R.id.summary);
       save =  v.findViewById(R.id.save);
       if(flag==0){
           summary.setText(summaryPassArg);
           topic.setText(topicPassArg);
       }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1) {
                    noteDbHelper = new NoteDbHelper(getContext());
                    noteDbHelper.addNotes(summary.getText().toString(), topic.getText().toString());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main, new MainNoteFragment())
                            .commit();
                }
                if (flag==0){
                    Toast.makeText(getContext(), "hereeeeeeee  " + String.valueOf(id), Toast.LENGTH_LONG).show();

                    noteDbHelper = new NoteDbHelper(getContext());
                    noteDbHelper.updateTable(id,summary.getText().toString(), topic.getText().toString());
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main, new MainNoteFragment())
                            .commit();
                }
            }
        });



        return v;
    }

}