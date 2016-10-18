package com.itianyi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itianyi.adapter.TreeViewAdapter;
import com.itianyi.bean.TreeElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 类名：TreeView.java
 * 类描述：实现树形结构的view
 * 创建时间：2012-11-23
 */
public class TreeView extends ListView implements AdapterView.OnItemClickListener {

    private static String TAG = "TreeView";

    List<TreeElement> treeElements = null;// 所有节点集合
    List<TreeElement> currentElements = null;// 当前显示的节点集合
    List<TreeElement> tempElements = null;// 用于临时存储

    HashMap<String, List<TreeElement>> deleteMap = new HashMap<String, List<TreeElement>>();
    //用于存储要删除的子节点，以及间接子节点。
    TreeViewAdapter adapter = null;// 用于数据填充
    onItemClickListener itemClickCallBack;// 用户点击事件回调

    public TreeView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        treeElements = new ArrayList<>();
        currentElements = new ArrayList<>();

        adapter = new TreeViewAdapter(context, currentElements);
        this.setAdapter(adapter);
        itemClickCallBack = new onItemClickListener() {
            @Override
            public void onItemClick(int position,TreeViewAdapter adapter) {
            }
        };
        this.setOnItemClickListener(this);
    }

    public void initData(Context context, List<TreeElement> treeElements) {
        this.treeElements = treeElements;
        getFirstLevelElements(context);
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置点击事件回调接口
     *
     * @param itemClickCallBack
     */

    public void setLastLevelItemClickCallBack(onItemClickListener itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }

    /**
     * 初始化树形结构列表数据,把第一层级的数据添加到currentElements中
     */
    public void getFirstLevelElements(Context context) {

        int size = treeElements.size();
        if (currentElements == null) {
            currentElements = new ArrayList<TreeElement>();
        }
        currentElements.clear();
        for (int i = 0; i < size; i++) {
            if (treeElements.get(i).getLevel() == 1) {
                currentElements.add(treeElements.get(i));

            }
        }
    }

    /**
     * 从所有节点集合中获取某父节点的子节点集合
     *
     * @param parentId
     * @return
     */
    private List<TreeElement> getChildElementsFromAllById(String parentId) {
        tempElements = new ArrayList<TreeElement>();
        int size = treeElements.size();

        for (int i = 0; i < size; i++) {
            String temptParentId = treeElements.get(i).getParentId() ;
            if(temptParentId != null){
                if (temptParentId.equalsIgnoreCase(parentId)) {
                    tempElements.add(treeElements.get(i));
                }
            }
        }
        return tempElements;
    }

    /**
     * 从当前显示的节点集合中    , 获取某父节点的子节点集合
     * @param parentId
     * @return
     */
    private List<TreeElement> getChildElementsFromCurrentById(String parentId) {
        if (tempElements == null) {
            tempElements = new ArrayList<TreeElement>();
        } else {
            tempElements.clear();
        }

        int size = currentElements.size();
        if(size > 0){
            for(TreeElement treeElement : currentElements){
                String temp = treeElement.getParentId();
                if(temp != null){
                    if (temp.equalsIgnoreCase(parentId)) {
                        tempElements.add(treeElement);
                    }

                }
            }
        }
        return tempElements;
    }

    /**
     * 删除某父节点的所有子节点集合(包括直接节点和间接节点)
     * @param parentId
     * @return
     */
    private synchronized boolean delAllChildElementsByParentId(String parentId) {

        List<TreeElement> childElments = getChildElementsFromCurrentById(parentId);
        List<TreeElement> treeElementsToDel = deleteMap.get(parentId);
        if (treeElementsToDel == null) {
            treeElementsToDel = new ArrayList<TreeElement>();
            deleteMap.put(parentId, treeElementsToDel);
        } else {
            treeElementsToDel.clear();
        }
        int size = childElments.size();

        TreeElement childElement = null;
        for (int i = 0; i < size; i++) {
            childElement = childElments.get(i);
            //寻找间接节点
            if (childElement.isHasChild() && childElement.isFold()) {
                treeElementsToDel.add(childElement);
            }
        }
        size = treeElementsToDel.size();

        if(size > 0){
            //删除间接节点
            List<TreeElement> deleteTreeElement = treeElementsToDel;
            for(TreeElement element : deleteTreeElement){
                delAllChildElementsByParentId(element.getId());
            }
        }
        //删除直接节点
        delDirectChildElementsByParentId(parentId);
        return true;
    }

    /**
     * 删除某父节点的直接子节点集合
     *
     * @param parentId
     * @return
     */
    private synchronized boolean delDirectChildElementsByParentId(
            String parentId) {
        boolean success = false;
        if (currentElements == null || currentElements.size() == 0) {
            return success;
        }
        synchronized (currentElements) {
            int size = currentElements.size();
            for (int i = size - 1; i >= 0; i--) {
                String temp = currentElements.get(i).getParentId();
                if(temp != null && temp.equalsIgnoreCase(parentId)){
                    currentElements.get(i).setFold(false);// 记得隐藏子节点时把展开状态设为false
                    currentElements.remove(i);
                }
            }
        }
        success = true;
        return success;
    }

    /**
     * 根据id查下标
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unused")
    private int getElementIndexById(String id) {
        int num = currentElements.size();
        for (int i = 0; i < num; i++) {
            if (currentElements.get(i).getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unused")
    private TreeElement getElementById(String id){
        int num = currentElements.size();
        if( num > 0){
            for (int i = 0; i < num; i++) {
                if (currentElements.get(i).getId().equalsIgnoreCase(id)) {
                    return currentElements.get(i);
                }
            }
        }
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View convertView,
                            int position, long id) {
        if(itemClickCallBack!=null)
            itemClickCallBack.onItemClick(position, adapter);
    }

    public void onChangeFolder(int position) {
        TreeElement element = currentElements.get(position);
        if (element.isHasChild()) {// 当前节点有子节点时只进行数据显示或隐藏等操作
            if (!element.isFold()) {// 当前父节点为未展开状态
                currentElements.addAll(position + 1,this.getChildElementsFromAllById(element.getId()));
            } else if (element.isFold()) {// 当前父节点为展开状态
                boolean success = this.delAllChildElementsByParentId(element.getId());
                if (!success) {
                    return;
                }
            }
            element.setFold(!element.isFold());// 设置反状态
            adapter.notifyDataSetChanged();// 刷新数据显示
        }
        else {
            if(itemClickCallBack!=null)
                itemClickCallBack.onItemClick(position,adapter);
        }
    }

    /**
     * 自定义内部接口，用于用户点击最终节点时的事件回调
     */
    public interface onItemClickListener {
        public void onItemClick(int position,TreeViewAdapter adapter);
    }
}
