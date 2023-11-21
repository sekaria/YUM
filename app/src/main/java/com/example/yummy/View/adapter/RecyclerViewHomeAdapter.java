package com.example.yummy.View.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yummy.Model.Categories;
import com.example.yummy.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewHomeAdapter extends RecyclerView.Adapter<RecyclerViewHomeAdapter.RecyclerViewHolder> {

    private List<Categories.Category> categories;
    private Context context;
    private ClickListener clickListener;

    public RecyclerViewHomeAdapter(List<Categories.Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_category,
                viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder viewHolder, int i) {

        String strCategoryThumb = categories.get(i).getStrCategoryThumb();
        Picasso.get().load(strCategoryThumb).placeholder(R.drawable.ic_circle).into(viewHolder.categoryThumb);

        String strCategoryName = categories.get(i).getStrCategory();
        viewHolder.categoryName.setText(strCategoryName);
    }

//    @Override
//    public int getItemCount() {
//        return categories.size();
//    }

    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }


    class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoryThumb;
        TextView categoryName;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryThumb = itemView.findViewById(R.id.categoryThumb);
            categoryName = itemView.findViewById(R.id.categoryName);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(v, getAdapterPosition());
            }
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}