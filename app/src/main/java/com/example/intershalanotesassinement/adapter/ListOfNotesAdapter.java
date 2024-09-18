package com.example.intershalanotesassinement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.intershalanotesassinement.MainActivity;
import com.example.intershalanotesassinement.R;
import com.example.intershalanotesassinement.fragment.AddNoteFragment;
import com.example.intershalanotesassinement.model.Notes;
import java.util.List;

public class ListOfNotesAdapter extends RecyclerView.Adapter<ListOfNotesAdapter.ViewHolder> {
    private List<Notes> dataList; // Replace with your data model
    private Context context;

    public ListOfNotesAdapter(List<Notes> dataList, Context context) {
        this.dataList = dataList;
        this.context= context;
        // Add data to the list
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_for_notes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notes note = dataList.get(position);
        holder.summary.setText(dataList.get(position).getSummary());
        holder.topic.setText(String.valueOf(dataList.get(position).getTopic()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteFragment fragment = AddNoteFragment.newInstance(note.getId(), note.getSummary(), note.getTopic(),0);
                ((MainActivity)context).getSupportFragmentManager().beginTransaction()
                        .add(R.id.main, fragment)
                        .addToBackStack(null)
                        .commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView summary;
        public  TextView topic;
        public ViewHolder(View itemView) {
            super(itemView);
            summary = itemView.findViewById(R.id.summary);
            topic = itemView.findViewById(R.id.topic);
            // textView = itemView.findViewById(R.id.text_view);
        }
    }
    public void updateList(List<Notes> dataList){
        this.dataList=dataList;
        notifyDataSetChanged();
    }
}
