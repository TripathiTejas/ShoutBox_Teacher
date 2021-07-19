package org.kotakeducation.shoutbox.Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import org.kotakeducation.shoutbox.Models.FormModel;
import org.kotakeducation.shoutbox.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.FormViewHolder> {

    List<FormModel> formModelList;
    Context context;
    HashMap<String, Object> bodyTextHashmap;

    public HashMap<String, Object> getBodyTextHashmap() {
        return bodyTextHashmap;
    }

    public void setBodyTextHashmap(HashMap<String, Object> bodyTextHashmap) {
        this.bodyTextHashmap = bodyTextHashmap;
    }

    public FormAdapter(List<FormModel> formModelList, Context context) {
        this.formModelList = formModelList;
        this.context = context;
        bodyTextHashmap = new HashMap<>();
    }


    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public FormAdapter.FormViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_form,parent,false);
        return new FormAdapter.FormViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull FormAdapter.FormViewHolder holder, int position) {

        FormModel formModel = formModelList.get(position);

        holder.headingTV.setText(formModel.getHeading());
        holder.enquiryInfoTV.setText(formModel.getEnquiryInfo());

        holder.headingLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.isCollapsed = ! holder.isCollapsed;
                holder.collapseLL.setVisibility(holder.isCollapsed ? View.VISIBLE : View.GONE);
                if (!holder.isCollapsed){
                    holder.downIcon.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
                else {
                    holder.downIcon.setImageResource(R.drawable.ic_baseline_arrow_right_24);
                }
            }
        });

        holder.bodyET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = "empty";
                if (!TextUtils.isEmpty(holder.bodyET.getText().toString())){
                    s = String.valueOf(charSequence);
                }
                bodyTextHashmap.put(holder.headingTV.getText().toString(), s);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String s = "empty";
                if (!TextUtils.isEmpty(holder.bodyET.getText().toString())){
                    s = String.valueOf(charSequence);
                }
                bodyTextHashmap.put(holder.headingTV.getText().toString(), s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return formModelList.size();
    }

    public class FormViewHolder extends RecyclerView.ViewHolder {

        TextView headingTV, enquiryInfoTV;
        EditText bodyET;

        LinearLayout collapseLL, headingLL;
        boolean isCollapsed = false;

        Button saveBtn;

        ImageView downIcon;

        public FormViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            headingTV = itemView.findViewById(R.id.headingTV);
            enquiryInfoTV = itemView.findViewById(R.id.enquiryInfoTV);
            bodyET = itemView.findViewById(R.id.bodyET);
            collapseLL = itemView.findViewById(R.id.collapseLL);
            headingLL = itemView.findViewById(R.id.headingLL);
            saveBtn = itemView.findViewById(R.id.saveBtn);
            downIcon = itemView.findViewById(R.id.downIcon);
        }
    }
}
