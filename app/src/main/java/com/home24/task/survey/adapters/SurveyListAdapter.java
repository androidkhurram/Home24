package com.home24.task.survey.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.home24.task.survey.models.SurveyModel;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.home24.task.survey.BaseActivity;
import com.neberox.app.survey.R;

import java.util.ArrayList;

public class SurveyListAdapter extends UltimateViewAdapter<SurveyListAdapter.ViewHolder>
{
	private BaseActivity mContext;
	private ArrayList<SurveyModel> cafeList = new ArrayList<>();
	public boolean isGrid = false;

	public SurveyListAdapter(BaseActivity mContext, ArrayList<SurveyModel> feedItems, boolean isGrid)
	{
		this.isGrid = isGrid;
		this.mContext = mContext;
		this.cafeList = feedItems;
	}

	@Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup)
    {
        return null;
    }

	@Override
	public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i)
	{}


	@Override
	public int getItemCount()
	{
		return cafeList.size();
	}

	@Override
	public int getAdapterItemCount()
	{
		return cafeList.size();
	}

	@Override
	public long generateHeaderId(int i)
	{
		return 0;
	}

	@Override
	public ViewHolder newFooterHolder(View view)
	{
		return new ViewHolder(view);
	}

	@Override
	public ViewHolder newHeaderHolder(View view)
	{
		return new ViewHolder(view);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent)
	{
		if (!isGrid)
		{
			View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_row, parent, false);

			ViewHolder holder = new ViewHolder(vi);

			holder.title = (TextView) vi.findViewById(R.id.title);
			holder.image = (SimpleDraweeView) vi.findViewById(R.id.image);
			holder.likeImg = (ImageView) vi.findViewById(R.id.likeImg);

			holder.mainLayout = (RelativeLayout) vi.findViewById(R.id.mainLayout);
			return holder;
		}
		else
		{
			View vi = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_grid, parent, false);

			ViewHolder holder = new ViewHolder(vi);

			holder.image = (SimpleDraweeView) vi.findViewById(R.id.image);
			holder.likeImg = (ImageView) vi.findViewById(R.id.likeImg);
			holder.mainLayout = (RelativeLayout) vi.findViewById(R.id.mainLayout);

			return holder;
		}
	}

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position)
	{
		final SurveyModel store = cafeList.get(position);

		holder.mainLayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//ApiManager.getInstance().selectedCafe = store;
				//mContext.startActivity(new Intent(mContext, CafeActivity.class));
			}
		});

		if (store.media.size() > 0)
			holder.image.setImageURI(store.media.get(0).uri);

		if (!isGrid)
			holder.title.setText(store.title);

		if (store.isLiked)
		{
			holder.likeImg.setRotation(0);
			holder.likeImg.setImageResource(R.drawable.about_like_icon_clicked);
		}
		else
		{
			holder.likeImg.setRotation(180);
			holder.likeImg.setImageResource(R.drawable.about_like_icon_clicked);
		}

	}

	public void updateValues(ArrayList<SurveyModel> historyItemsList, boolean isGrid)
	{
		this.isGrid = isGrid;
		this.cafeList = historyItemsList;
		notifyDataSetChanged();
	}

	public static class ViewHolder extends UltimateRecyclerviewViewHolder
	{
		public TextView title;
		public SimpleDraweeView image;
		public ImageView likeImg;

		public RelativeLayout mainLayout;

		ViewHolder(View itemView)
		{
			super(itemView);
		}


		@Override
		public void onItemSelected()
		{
			//itemView.setBackgroundColor(Color.LTGRAY);
		}

		@Override
		public void onItemClear()
		{
			//itemView.setBackgroundColor(0);
		}
	}
}