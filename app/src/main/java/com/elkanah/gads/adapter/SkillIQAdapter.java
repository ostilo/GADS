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
import com.elkanah.gads.models.SkillIQLeaders;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SkillIQAdapter extends RecyclerView.Adapter<SkillIQAdapter.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private List<SkillIQLeaders> skillIQLeaders;

    public SkillIQAdapter(Context context, List<SkillIQLeaders> skillIQLeaders) {
        this.context = context;
        this.skillIQLeaders = skillIQLeaders;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public SkillIQAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.learning_leaders_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SkillIQAdapter.ViewHolder holder, int position) {
        try {
            GotoUpdateData(holder, position);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void GotoUpdateData(SkillIQAdapter.ViewHolder holder, int position) {
        try {
            holder.tvName.setText(skillIQLeaders.get(position).name);
            holder.tvTitle.setText(String.valueOf(skillIQLeaders.get(position).score + " skill IQ Score. " + skillIQLeaders.get(position).country));
            String uri = skillIQLeaders.get(position).badgeUrl;
            try {
                Picasso.get().load(uri).fit().into(holder.imgBadge);
            } catch (Exception e) {
                //holder.imgBadge.setImageResource(R.drawable.gadsplash);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return skillIQLeaders == null ? 0 : skillIQLeaders.size();
    }

    public void setNewLeaders(List<SkillIQLeaders> skillIQLeaders) {
       this.skillIQLeaders = skillIQLeaders;
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
