package com.elkanah.gads.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elkanah.gads.R;
import com.elkanah.gads.models.LearningLeaders;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LearningLeadersAdapter extends RecyclerView.Adapter<LearningLeadersAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<LearningLeaders> learningLeaders;

    public LearningLeadersAdapter(Context context, List<LearningLeaders> learningLeaders) {
        this.context = context;
        this.learningLeaders = learningLeaders;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LearningLeadersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.learning_leaders_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LearningLeadersAdapter.ViewHolder holder, int position) {
        try {
            GotoUpdateData(holder, position);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void GotoUpdateData(ViewHolder holder, int position) {
        try {
            holder.tvName.setText(learningLeaders.get(position).name);
            holder.tvTitle.setText(String.valueOf(learningLeaders.get(position).hours + context.getString(R.string.learn) + learningLeaders.get(position).country));
            String uri = learningLeaders.get(position).badgeUrl;
            try {
                Picasso.get().load(uri).fit().into(holder.imgBadge);
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return learningLeaders == null ? 0 : learningLeaders.size();
    }

    public void setNewLeaders(List<LearningLeaders> learningLeaders) {
        this.learningLeaders = learningLeaders;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvTitle;
        private ImageView imgBadge;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_branch_title);
            tvTitle = itemView.findViewById(R.id.textView);
            imgBadge = itemView.findViewById(R.id.product_prefix_iconn);

        }
    }
}
