package com.skyhope.materialtagview.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skyhope.materialtagview.R;
import com.skyhope.materialtagview.interfaces.TagClickListener;

import java.util.ArrayList;
import java.util.List;
/*
 *  ****************************************************************************
 *  * Created by : Md Tariqul Islam on 3/19/2019 at 11:23 AM.
 *  * Email : tariqul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md Tariqul Islam on 3/19/2019.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */


public class TagViewAdapter extends RecyclerView.Adapter<TagViewAdapter.TagViewHolder> implements Filterable {

    private List<String> mTagItemList;

    private List<String> mBackUpList;

    private int mTagTextColor;
    private int mTagBackgroundColor;

    private TagClickListener mClickListener;

    public TagViewAdapter(int mTagTextColor, int mTagBackgroundColor) {
        this.mTagTextColor = mTagTextColor;
        this.mTagBackgroundColor = mTagBackgroundColor;

        mTagItemList = new ArrayList<>();
        mBackUpList = new ArrayList<>();
    }

    @NonNull
    @Override
    public TagViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tag, viewGroup, false);
        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagViewHolder tagViewHolder, int i) {
        String tag = mTagItemList.get(i);

        tagViewHolder.textViewTag.setTextColor(mTagTextColor);

        GradientDrawable drawable = (GradientDrawable) tagViewHolder.tagContainer.getBackground();
        drawable.setColor(mTagBackgroundColor);

        tagViewHolder.textViewTag.setText(tag);
    }

    @Override
    public int getItemCount() {
        return mTagItemList.size();
    }

    /**
     * Add Tag item to show.
     *
     * @param tagList List of Tag
     */
    public void addItems(List<String> tagList) {
        if (mTagItemList != null) {
            mTagItemList.clear();
            mTagItemList = tagList;
        }
        if (mBackUpList != null) {
            mBackUpList.clear();
            mBackUpList.addAll(tagList);
        }

        notifyDataSetChanged();
    }

    public void addItem(String text) {
        mTagItemList.add(text);
        mBackUpList.add(text);

        notifyItemInserted(mTagItemList.size() - 1);
    }

    public void addTagTextColor(int mTagTextColor) {
        this.mTagTextColor = mTagTextColor;
    }

    public void addTagBackgroundColor(int mTagBackgroundColor) {
        this.mTagBackgroundColor = mTagBackgroundColor;
    }

    public void setTagClickListener(TagClickListener listener) {
        mClickListener = listener;
    }

    /**
     * Remove item When user select a Tag from list
     *
     * @param position Item Position
     */
    public void removeTagItem(int position, String tag) {
        if (mTagItemList != null && mTagItemList.size() >= position) {
            mTagItemList.remove(tag);
            notifyItemRemoved(position);
        }

        if (mBackUpList != null && mBackUpList.size() >= position) {
            mBackUpList.remove(tag);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if (charSequence.toString().isEmpty()) {
                    mTagItemList.clear();
                    mTagItemList.addAll(mBackUpList);

                } else {
                    mTagItemList.clear();

                    for (String text : mBackUpList) {
                        if (text.toLowerCase().contains(charSequence) || text.toUpperCase().contains(charSequence)) {
                            mTagItemList.add(text);
                        }
                    }
                }
                final FilterResults results = new FilterResults();
                results.values = mTagItemList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }

    class TagViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTag;
        LinearLayout tagContainer;

        TagViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTag = itemView.findViewById(R.id.text_view_tag);
            tagContainer = itemView.findViewById(R.id.tag_container);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onGetSelectTag(getAdapterPosition(), mTagItemList.get(getAdapterPosition()));
            }
        }
    }
}
