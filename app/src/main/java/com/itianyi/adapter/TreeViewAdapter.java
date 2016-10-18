package com.itianyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itianyi.bean.TreeElement;
import com.itianyi.cadres.R;
import com.itianyi.view.TreeView;

import java.util.List;

/**
 * 类名：TreeViewAdapter.java
 * 类描述：用于填充数据的类
 * 创建时间：2012-11-23 16:32
 */
public class TreeViewAdapter extends BaseAdapter {

    class ViewHolder {
        LinearLayout layout;
        ImageView icon;
        TextView title;
    }

    Context context;
    ViewHolder holder;
    LayoutInflater inflater;

    List<TreeElement> elements;

    public TreeViewAdapter(Context context, List<TreeElement> elements) {
        this.context = context;
        this.elements = elements;
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        /**
         * ---------------------- get holder------------------------
         */
        if (convertView == null) {
            if (inflater == null) {
                inflater = LayoutInflater.from(context);
            }
            holder = new ViewHolder();
            convertView = inflater
                    .inflate(R.layout.tree_view_item, null);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.tree_view_item_content);
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TreeView view = (TreeView)parent;
                    view.onChangeFolder(position);
                }
            });
            holder.icon = (ImageView) convertView
                    .findViewById(R.id.tree_view_item_icon);
            holder.title = (TextView) convertView
                    .findViewById(R.id.tree_view_item_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         * ---------------------- set holder--------------------------
         */
        if (elements.get(position).isHasChild()) {// 有子节点，要显示图标
            if (elements.get(position).isFold()) {
                holder.icon.setImageResource(R.drawable.treeview_list_expand);
            } else if (!elements.get(position).isFold()) {
                holder.icon.setImageResource(R.drawable.treeview_list_collapse);
            }
            holder.icon.setVisibility(View.VISIBLE);
        } else {// 没有子节点，要隐藏图标
            holder.icon.setImageResource(R.drawable.treeview_list_expand);
            holder.icon.setVisibility(View.INVISIBLE);
        }
        holder.icon.setPadding(25 * (elements.get(position).getLevel()), 0, 0, 0);// 根据层级设置缩进
        holder.title.setText(elements.get(position).getTitle());
        holder.title.setTextSize(35 - elements.get(position).getLevel() * 5); // 根据层级设置字体大小

        return convertView;
    }
}

