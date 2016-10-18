package com.itianyi.cadres;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.itianyi.adapter.TreeViewAdapter;
import com.itianyi.app.CadresApplication;
import com.itianyi.app.SQLiteHelper;
import com.itianyi.bean.Department;
import com.itianyi.bean.TreeElement;
import com.itianyi.view.TreeView;

import java.util.ArrayList;
import java.util.List;


public class SelectDeptActivity extends BaseActivity {

    private List<TreeElement> treeElementList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_dept);
        TreeView treeView = (TreeView) findViewById(R.id.treeview);
        TreeView.onItemClickListener itemClickCallBack = new TreeView.onItemClickListener() {
            // 创建节点点击事件监听
            @Override
            public void onItemClick(int position,
                                             TreeViewAdapter adapter) {
                TreeElement element = (TreeElement) adapter.getItem(position);

                Intent intent = new Intent();
                String unitName = null,departmentName = null;

                if(element.getParentId()!=null&&!element.getParentId().equals("-1")) {
                    for(int i = 0;i<treeElementList.size();i++) {
                        if(treeElementList.get(i).getId().equals(element.getParentId())) {
                            unitName = treeElementList.get(i).getTitle();
                            departmentName = element.getTitle();
                            break;
                        }
                    }
                } else {
                    unitName = element.getTitle();
                }
                intent.putExtra("com.itianyi.cadres.selectdept.unit",unitName);
                intent.putExtra("com.itianyi.cadres.selectdept.department",departmentName);
                //返回回去
                setResult(-1,intent);
                finish();
            }
        };

        //初始化数据
        initData();
        treeView.initData(this, treeElementList);// 初始化数据
        treeView.setLastLevelItemClickCallBack(itemClickCallBack);// 设置节点点击事件监听

    }
    private void initData() {
        CadresApplication app = (CadresApplication) this.getApplication();
        List<Department> departmentList = SQLiteHelper.getDepartmentList(app);
        TreeElement root = new TreeElement();
        root.setId("-1");
        root.setLevel(1);
        root.setTitle("全部");
        root.setFold(false);
        root.setHasChild(true);
        root.setHasParent(false);
        root.setParentId(null);
        treeElementList.add(root);
        for(Department department : departmentList) {
            TreeElement treeElement = new TreeElement();
            treeElement.setId(department.getmId()+"");
            treeElement.setTitle(department.getName());
            treeElement.setHasParent(true);
            if(department.getParent() == -1) {
                treeElement.setFold(false);
                treeElement.setHasChild(true);
                treeElement.setLevel(2);
                treeElement.setParentId("-1");
            } else {
                treeElement.setFold(false);
                treeElement.setHasChild(false);
                treeElement.setLevel(3);
                treeElement.setParentId(department.getParent()+"");
            }
            treeElementList.add(treeElement);
        }
    }

    @Override
    public void onBackPressed()
    {
        setResult(-2);
        super.onBackPressed();
    }
}
