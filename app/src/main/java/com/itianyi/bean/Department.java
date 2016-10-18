package com.itianyi.bean;

import android.database.sqlite.SQLiteDatabase;

import com.itianyi.app.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 沫 on 2015/8/10.
 */
public class Department {

    private int mId;
    private String name;
    private int parent;

    public static void saveList(String departmentJson, SQLiteDatabase db) throws JSONException {

        JSONArray lrmJSONArray = null;
        lrmJSONArray = new JSONArray(departmentJson);

        List<Department> departmentList = JsonArrayToList(lrmJSONArray);
        SQLiteHelper.InsertDepartment(db, departmentList);
    }

    public static List<Department> JsonArrayToList(JSONArray departmentJson) throws JSONException {
        List<Department> departmentList = new ArrayList<>();
        int length = departmentJson.length();
        for(int i = 0 ;i < length;i++) {
            JSONObject object = departmentJson.getJSONObject(i);
            String dwName = object.getString("dw");
            Department department = new Department();
            int mId = departmentList.size();
            department.setmId(mId);
            department.setName(dwName);
            department.setParent(-1);
            departmentList.add(department);

            String deptNames = object.getString("dept");
            String[] deptList = deptNames.split(",");
            for(int j = 0;j<deptList.length;j++) {
                Department department1 = new Department();
                department1.setmId(departmentList.size());
                department1.setName(deptList[j]);
                department1.setParent(mId);
                departmentList.add(department1);
            }
        }
        return departmentList;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
}
