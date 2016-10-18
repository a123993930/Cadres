package com.itianyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

/**
 * Created by 沫 on 2015/6/8.
 */
public class FileListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<File> mFileList;

    public FileListAdapter(Context context, List<File> fileList) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mFileList = fileList;
    }
    public void setFileList(List<File> fileList) {
        mFileList = fileList;
    }
    @Override
    public int getCount() {
        return mFileList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        File file = (File)getItem(position);
        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView =mInflater.inflate(android.R.layout.simple_list_item_1, null);
            holder.fileName = (TextView)convertView.findViewById(android.R.id.text1);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.fileName.setText(file.getName());
        return convertView;
    }

    //ViewHolder静态类
    static class ViewHolder
    {
        public TextView fileName;
    }
}
