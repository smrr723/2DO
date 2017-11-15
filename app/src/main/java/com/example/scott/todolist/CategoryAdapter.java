package com.example.scott.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by scott on 15/11/2017.
 */

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    private List<Category> categoies;
    private DBHelper dbHelper;

    public CategoryAdapter(Context context, List<Category> categoies, DBHelper dbHelper) {
        this.context = context;
        this.categoies = categoies;
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return categoies.size();
    }

    @Override
    public Object getItem(int position) {
        return categoies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.category_item, viewGroup, false);
        }
        TextView categoryName = v.findViewById(R.id.categoryName);
        TextView categoryCount = v.findViewById(R.id.qty);

        Category category = categoies.get(position);
        categoryName.setText(category.getName());
        Long count = dbHelper.getCountByCategory(category.getId());
        if (count != null)
            categoryCount.setText(count.toString());
        return v;
    }
}

