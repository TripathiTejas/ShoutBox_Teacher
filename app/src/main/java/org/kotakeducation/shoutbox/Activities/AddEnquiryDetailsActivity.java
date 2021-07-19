package org.kotakeducation.shoutbox.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.kotakeducation.shoutbox.Adapters.FormAdapter;
import org.kotakeducation.shoutbox.Models.EnquiryProjectModel;
import org.kotakeducation.shoutbox.Models.FormModel;
import org.kotakeducation.shoutbox.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddEnquiryDetailsActivity extends AppCompatActivity {

    RecyclerView formRV;

    List<FormModel> formModelList;
    FormAdapter adapter;

    TextView tempTV;
    ExtendedFloatingActionButton confirmBtn;

    HashMap<String, Object> bodyTextHashmap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_enquiry_details);

        init();

        formModelList.add(new FormModel("Question", "Use curiosity, wonder, need or interest to ask rich questions"));
        formModelList.add(new FormModel("Predict", "Think about what will happen"));
        formModelList.add(new FormModel("Plan", "Identify methods and materials. Seek information"));
        formModelList.add(new FormModel("Investigate", "Observe objects, places, events. Sort, classify, compare, contrast, test. "));
        formModelList.add(new FormModel("Record", "Document observations and data from investigation"));
        formModelList.add(new FormModel("Analyze & Interpret", "Make meaning, Explain patterns in data"));
        formModelList.add(new FormModel("Connect", "Connect prior knowledge and new knowledge. Reflect on learning."));

        adapter.notifyDataSetChanged();

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                bodyTextHashmap = adapter.getBodyTextHashmap();
                tempTV.setText(adapter.getBodyTextHashmap().toString());

                try {
                    EnquiryProjectModel enquiryProjectModel = new EnquiryProjectModel();
                    enquiryProjectModel.setQuestion(adapter.getBodyTextHashmap().get("Question").toString());
                    enquiryProjectModel.setPredict(adapter.getBodyTextHashmap().get("Predict").toString());
                    enquiryProjectModel.setPlan(adapter.getBodyTextHashmap().get("Plan").toString());
                    enquiryProjectModel.setInvestigate(adapter.getBodyTextHashmap().get("Investigate").toString());
                    enquiryProjectModel.setRecord(adapter.getBodyTextHashmap().get("Record").toString());
                    enquiryProjectModel.setAnalyze(adapter.getBodyTextHashmap().get("Analyze & Interpret").toString());
                    enquiryProjectModel.setConnect(adapter.getBodyTextHashmap().get("Connect").toString());
                    tempTV.setText(adapter.getBodyTextHashmap().get("Connect").toString());

                    new MaterialAlertDialogBuilder(AddEnquiryDetailsActivity.this)
                            .setTitle("Confirm?")
                            .setMessage("You will no be able to edit this section later")
                            .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent mIntent = new Intent(AddEnquiryDetailsActivity.this, AddProjectActivity.class);
                                    mIntent.putExtra("activity", "enquiry");
                                    mIntent.putExtra("enquiryModel", enquiryProjectModel);
                                    startActivity(mIntent);
                                    finish();
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .show();

                } catch (Exception e) {
                    Toast.makeText(AddEnquiryDetailsActivity.this, "Empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onBackPressed() {
        new MaterialAlertDialogBuilder(AddEnquiryDetailsActivity.this)
                .setTitle("Go Back?")
                .setMessage("All changes will be lost")
                .setPositiveButton("Yes Go back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(AddEnquiryDetailsActivity.this, AddProjectActivity.class);
                        intent.putExtra("activity", "feed");
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void init() {
        formRV = findViewById(R.id.formRV);
        formModelList = new ArrayList<>();
        adapter = new FormAdapter(formModelList, AddEnquiryDetailsActivity.this);
        formRV.setLayoutManager(new LinearLayoutManager(this));
        formRV.setAdapter(adapter);
        tempTV = findViewById(R.id.tempTv);
        confirmBtn = findViewById(R.id.confirmBtn);

    }
}