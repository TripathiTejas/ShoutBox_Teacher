package org.kotakeducation.shoutbox.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.kotakeducation.shoutbox.Activities.ProjectDisplayActivity;
import org.kotakeducation.shoutbox.Activities.ProjectFeedActivity;
import org.kotakeducation.shoutbox.Models.ModelForProjectFeed;
import org.kotakeducation.shoutbox.R;
import java.util.List;

public class AdapterProjectFeed extends RecyclerView.Adapter<AdapterProjectFeed.MyViewHolder> {

    private ProjectFeedActivity activity;
    private List<ModelForProjectFeed> mList;

    public AdapterProjectFeed(ProjectFeedActivity activity, List<ModelForProjectFeed> mList){
        this.activity=activity;
        this.mList=mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(activity).inflate(R.layout.feed_display,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ProjectTitle.setText(mList.get(position).getTitle());
        holder.UserName.setText(mList.get(position).getUsername());
        holder.ProjectDate.setText(mList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView ProjectTitle,UserName,ProjectDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProjectTitle=itemView.findViewById(R.id.ProjectTitle);
            UserName=itemView.findViewById(R.id.UserName);
            ProjectDate=itemView.findViewById(R.id.ProjectDate);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position =getAdapterPosition();
            Intent intent =new Intent(activity, ProjectDisplayActivity.class);
            intent.putExtra("User ID",mList.get(position).getUserId());
            intent.putExtra("Project Id",mList.get(position).getProjectID());
            activity.startActivity(intent);
        }
    }

}
