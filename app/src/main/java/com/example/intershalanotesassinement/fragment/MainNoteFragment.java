package com.example.intershalanotesassinement.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.intershalanotesassinement.R;
import com.example.intershalanotesassinement.adapter.ListOfNotesAdapter;
import com.example.intershalanotesassinement.database.NoteDbHelper;
import com.example.intershalanotesassinement.model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainNoteFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListOfNotesAdapter adapter;
    private List<Notes> dataList;
    private NoteDbHelper noteDbHelper;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainNoteFragment() {

        // Required empty public constructor
    }


    public static MainNoteFragment newInstance(String param1, String param2) {
        MainNoteFragment fragment = new MainNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_note, container,false);
        recyclerView = view.findViewById(R.id.recycler_view);
        noteDbHelper = new NoteDbHelper(getContext());
        dataList= new ArrayList<>();
       // Toast.makeText(getContext(),dataList.toString(),Toast.LENGTH_LONG).show();
        dataList = noteDbHelper.fetchData();
       // Toast.makeText(getContext(),"hhhhhhhhhhh "+dataList.get(0).getId(), Toast.LENGTH_LONG).show();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListOfNotesAdapter(dataList,getContext());

        recyclerView.setAdapter(adapter);
        FloatingActionButton add = view.findViewById(R.id.add);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {

                    add.animate().translationY(-200).setDuration(500).start();
                } else {
                    add.animate().translationY(0).setDuration(500).start();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteFragment fragment = AddNoteFragment.newInstance1(1);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(R.id.main, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });



        return view;
    }

}