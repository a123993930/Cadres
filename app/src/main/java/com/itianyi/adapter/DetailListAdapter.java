package com.itianyi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.itianyi.bean.Roster;
import com.itianyi.cadres.R;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 沫 on 2015/7/14.
 */
public class DetailListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater = null;
    private List<Roster> rosterList;

    public DetailListAdapter(Context context, List<Roster> array) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        rosterList = array;
    }
    public void setRosterList(List<Roster> array) {
        rosterList = array;
    }
    @Override
    public int getCount() {
        return rosterList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if (convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.list_cell, null);
            holder.xh = (TextView) convertView.findViewById(R.id.xh);
            holder.xm = (TextView) convertView.findViewById(R.id.xm);
            holder.xb = (TextView) convertView.findViewById(R.id.xb);
            holder.mz = (TextView) convertView.findViewById(R.id.mz);
            holder.jg = (TextView) convertView.findViewById(R.id.jg);
            holder.csrq = (TextView) convertView.findViewById(R.id.csrq);
            holder.cjgzsj = (TextView) convertView.findViewById(R.id.cjgzsj);
            holder.xl = (TextView) convertView.findViewById(R.id.xl);
            holder.xzj = (TextView) convertView.findViewById(R.id.xzj);
            holder.rxzjsj = (TextView) convertView.findViewById(R.id.rxzjsj);
            holder.dw = (TextView) convertView.findViewById(R.id.dw);
            holder.szbm = (TextView) convertView.findViewById(R.id.szbm);
            holder.xzw = (TextView) convertView.findViewById(R.id.xzw);
            holder.xjx = (TextView) convertView.findViewById(R.id.xjx);
            holder.zwlb = (TextView) convertView.findViewById(R.id.zwlb);
            holder.ll_background = (LinearLayout) convertView.findViewById(R.id.ll_background);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Roster roster = rosterList.get(position);
        holder.xh.setText((position+1) + "");
        holder.xm.setText(roster.getXm());
        holder.xb.setText(roster.getXb());
        holder.mz.setText(roster.getMz());
        holder.jg.setText(roster.getJg());
        SimpleDateFormat DateToStr = new SimpleDateFormat("yyyy.MM");

        holder.csrq.setText(DateToStr.format(roster.getCsrq()) +"\n("+roster.getNL()+")");
        holder.cjgzsj.setText(DateToStr.format(roster.getCjgzsj()) +"\n("+roster.getGL()+")");
        holder.xl.setText(roster.getXl());
        holder.xzj.setText(roster.getXzj());
        holder.rxzjsj.setText(DateToStr.format(roster.getXrzjsj()) +"\n("+roster.getZL()+")");
        holder.dw.setText(roster.getDw());
        holder.szbm.setText(roster.getSzbm());
        holder.xzw.setText(roster.getXzw());
        holder.xjx.setText(roster.getXjx());
        if(roster.getZwlb().equals("领导职务")) {
            holder.zwlb.setText("★");
        } else {
            holder.zwlb.setText("☆");
        }
        if(roster.getNL()>=60) {
            holder.ll_background.setBackgroundResource(R.drawable.list_redsel);
        } else {
            holder.ll_background.setBackgroundResource(R.drawable.listsel);
        }

        return convertView;
    }

    //ViewHolder静态类
    static class ViewHolder
    {
        public TextView xh;
        public TextView xm;
        public TextView xb;
        public TextView mz;
        public TextView jg;
        public TextView csrq;
        public TextView cjgzsj;
        public TextView xl;
        public TextView xzj;
        public TextView rxzjsj;
        public TextView dw;
        public TextView szbm;
        public TextView xzw;
        public TextView xjx;
        public TextView zwlb;

        public LinearLayout ll_background;
    }
}
