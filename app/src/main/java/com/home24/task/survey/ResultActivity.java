package com.home24.task.survey;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.home24.task.survey.adapters.SurveyListAdapter;
import com.home24.task.survey.api.ApiManager;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.neberox.app.survey.R;

public class ResultActivity extends BaseActivity
{
    private UltimateRecyclerView listView;
    private SurveyListAdapter adapter;

    private ImageView galleryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listView = (UltimateRecyclerView) findViewById(R.id.listView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);

        listView.setEmptyView(R.layout.listview_footer, R.id.progressBar);

        adapter = new SurveyListAdapter(this, ApiManager.getInstance().surveys, false);
        listView.setAdapter(adapter);

        galleryBtn = (ImageView) findViewById(R.id.galleryBtn);
        galleryBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!adapter.isGrid)
                {
                    galleryBtn.setImageResource(R.drawable.ic_tab);
                    GridLayoutManager layoutManager = new GridLayoutManager(ResultActivity.this, 3);
                    listView.setLayoutManager(layoutManager);
                    adapter = new SurveyListAdapter(ResultActivity.this, ApiManager.getInstance().surveys, true);
                    listView.setAdapter(adapter);
                }
                else
                {
                    galleryBtn.setImageResource(R.drawable.ic_grid);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ResultActivity.this);
                    listView.setLayoutManager(layoutManager);
                    adapter = new SurveyListAdapter(ResultActivity.this, ApiManager.getInstance().surveys, false);
                    listView.setAdapter(adapter);
                }
            }
        });
    }
}
