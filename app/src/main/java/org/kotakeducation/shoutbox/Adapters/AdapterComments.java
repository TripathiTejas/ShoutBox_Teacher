package org.kotakeducation.shoutbox.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.kotakeducation.shoutbox.Activities.Comments;
import org.kotakeducation.shoutbox.Activities.ProjectDisplayActivity;
import org.kotakeducation.shoutbox.Activities.ProjectFeedActivity;
import org.kotakeducation.shoutbox.Models.ModelForComments;
import org.kotakeducation.shoutbox.Models.ModelForProjectFeed;
import org.kotakeducation.shoutbox.R;

import java.util.List;

public class AdapterComments extends RecyclerView.Adapter<AdapterComments.MyViewHolder>{
    private Comments activity;
    private List<ModelForComments> mList;

    public AdapterComments(Comments activity, List<ModelForComments> mList){
        this.activity=activity;
        this.mList=mList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(activity).inflate(R.layout.comment_display,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.CommentUsername.setText(mList.get(position).getUsername());
        holder.CommentText.setText(mList.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView CommentUsername, CommentText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            CommentUsername = itemView.findViewById(R.id.UserNameComment);
            CommentText = itemView.findViewById(R.id.CommentText);
        }

    }
}
