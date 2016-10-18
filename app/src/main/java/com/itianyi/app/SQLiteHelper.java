package com.itianyi.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.cardemulation.CardEmulation;

import com.itianyi.bean.Department;
import com.itianyi.bean.Office;
import com.itianyi.bean.Roster;

import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 沫 on 2015/7/16.
 */
public class SQLiteHelper {
    //花名册表
    public static void insertRoster(SQLiteDatabase db,List<Roster> objectList) {

        createRoster(db);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        for(Roster roster:objectList) {
                db.execSQL("insert into roster(xh,xm,szm,xb,mz,jg,csrq,cjgzsj,zzmm,xl,zy ,yyyx ,xzj,xrzjsj,dw,szbm,xzw,xrzwsj,xjx,zwlb) values" +
                        "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{
                        roster.getXh(),roster.getXm(),roster.getszm(),roster.getXb(),roster.getMz(),roster.getJg(),
                        sdf.format(roster.getCsrq()),sdf.format(roster.getCjgzsj()),roster.getZzmm(),roster.getXl(),roster.getZy(),roster.getYyyx(),
                        roster.getXzj(),sdf.format(roster.getXrzjsj()),roster.getDw(),roster.getSzbm(),roster.getXzw(),roster.getXrzwsj(),
                        roster.getXjx(),roster.getZwlb()
                });
        }
    }

    public static List<Roster> getListFromRoster(CadresApplication app,String units,String dept,String xzj,String key,String police,String category) {

        Date reference_time = app.getReference_time();
        if(reference_time == null) {
            reference_time = new Date();
        }
        String sql = "Select * from roster where (xm is NULL OR xm like ? OR szm is NULL OR szm like ?) AND " +
                "(DW IS NULL OR DW like ?) AND (SZBM IS NULL OR SZBM like ?)" +
                " AND (xzj is NULL OR xzj like ?) And" +
                "(xjx is NULL OR xjx like ?) AND (zwlb is NULL OR zwlb like ?) Order By ID";
        SQLiteDatabase db = createRoster(app);

        Cursor cursor = db.rawQuery(sql, new String[]{key + "%",key + "%", units,dept,xzj,police,category});
        List<Roster> result = new ArrayList();
        for(int i=0;i<cursor.getCount();i++){
            try {
                if(cursor.moveToFirst()) {//判断游标是否为空

                    cursor.move(i);//移动到指定记录
                    Roster roster = new Roster();
                    roster.setXh(cursor.getInt(cursor.getColumnIndex("xh")));
                    roster.setXm(cursor.getString(cursor.getColumnIndex("xm")));
                    roster.setSZM(cursor.getString(cursor.getColumnIndex("szm")));
                    roster.setXb(cursor.getString(cursor.getColumnIndex("xb")));
                    roster.setMz(cursor.getString(cursor.getColumnIndex("mz")));
                    roster.setJg(cursor.getString(cursor.getColumnIndex("jg")));
                    roster.setCsrq(cursor.getString(cursor.getColumnIndex("csrq")),reference_time);
                    roster.setCjgzsj(cursor.getString(cursor.getColumnIndex("cjgzsj")),reference_time);
                    roster.setZzmm(cursor.getString(cursor.getColumnIndex("zzmm")));
                    roster.setXl(cursor.getString(cursor.getColumnIndex("xl")));
                    roster.setZy(cursor.getString(cursor.getColumnIndex("zy")));
                    roster.setYyyx(cursor.getString(cursor.getColumnIndex("yyyx")));
                    roster.setXzj(cursor.getString(cursor.getColumnIndex("xzj")));
                    roster.setXrzjsj(cursor.getString(cursor.getColumnIndex("xrzjsj")),reference_time);
                    roster.setDw(cursor.getString(cursor.getColumnIndex("dw")));
                    roster.setSzbm(cursor.getString(cursor.getColumnIndex("szbm")));
                    roster.setXzw(cursor.getString(cursor.getColumnIndex("xzw")));
                    roster.setXrzwsj(cursor.getString(cursor.getColumnIndex("xrzwsj")));
                    roster.setXjx(cursor.getString(cursor.getColumnIndex("xjx")));
                    roster.setZwlb(cursor.getString(cursor.getColumnIndex("zwlb")));

                    result.add(roster);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        cursor.close();
        return result;

    }

    private static void createRoster(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS roster (ID INTEGER PRIMARY KEY AUTOINCREMENT,xh INTEGER,xm VARCHAR(20),szm VARCHAR(20),xb VARCHAR(20)," +
                "mz VARCHAR(20),jg VARCHAR(20),csrq VARCHAR(20),cjgzsj VARCHAR(20),zzmm VARCHAR(20),xl VARCHAR(20)," +
                "zy VARCHAR(20),yyyx VARCHAR(20),xzj VARCHAR(20),xrzjsj VARCHAR(20),dw VARCHAR(20),szbm VARCHAR(20),xzw VARCHAR(20)," +
                "xrzwsj VARCHAR(20),xjx VARCHAR(20),zwlb VARCHAR(20))";
        db.execSQL(sql);
    }

    private static SQLiteDatabase createRoster(CadresApplication app) {
        SQLiteDatabase db = app.getDateBase();
        createRoster(db);
        return db;
    }

    //部门表
    public static void InsertDepartment( SQLiteDatabase db,List<Department> departmentList) {

        createDepartment(db);
        for(Department department :departmentList) {
            db.execSQL("insert into department(id,name,parent) values (?,?,?)",new Object[]{department.getmId(),department.getName(),department.getParent()});
        }
    }

    public static List<Department> getDepartmentList(CadresApplication app) {

        String sql = "Select * from department";
        SQLiteDatabase db = createDepartment(app);

        Cursor cursor = db.rawQuery(sql, null);
        List<Department> result = new ArrayList();
        if(cursor.moveToFirst()){//判断游标是否为空
            for(int i=0;i<cursor.getCount();i++){
                try {
                    Department department = new Department();
                    department.setmId(cursor.getInt(cursor.getColumnIndex("ID")));
                    department.setName(cursor.getString(cursor.getColumnIndex("name")));
                    department.setParent(cursor.getInt(cursor.getColumnIndex("parent")));
                    result.add(department);

                    cursor.moveToNext();//移动到指定记录
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        cursor.close();
        return result;
    }
    private static void createDepartment(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS department (ID INTEGER PRIMARY KEY,name VARCHAR(20),parent INTEGER)";
        db.execSQL(sql);
    }

    private static SQLiteDatabase createDepartment(CadresApplication app) {
        SQLiteDatabase db = app.getDateBase();
        createDepartment(db);
        return db;
    }



    public static void InsertLRM(SQLiteDatabase db,List<Office> officeList) {
        createLRM(db);
        try {
        for(Office office :officeList) {
            if(office.getName().startsWith("何")) {
                String Name =office.getName();
                Name.trim();
            }
            db.execSQL("insert into rlmDetail (Name,Sex,Birth,Minzu,Jiguan,BirthAddr,RudangDate,GognzuoDate," +
                            "JiankangStatus,Zhicheng,Techang,Qrzjy,QrzSchool,Zzjy,ZzSchool,Xrzw,Nrzw,Nmzw,Jianli," +
                            "Jiangcheng,Kaohe,RmReason,Cbdwyj,AddDate,photo) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                    new Object[]{office.getName(), office.getSex(), office.getBirth(), office.getMinzu(), office.getJiguan(), office.getBirthAddr(),
                            office.getRudangDate(), office.getGognzuoDate(), office.getJiankangStatus(), office.getZhicheng(), office.getTechang(),
                            office.getQrzjy(), office.getQrzSchool(), office.getZzjy(), office.getZzSchool(), office.getXrzw(), office.getNrzw(),
                            office.getNmzw(), office.getJianli(), office.getJiangcheng(), office.getKaohe(), office.getRmReason(), office.getCbdwyj(),
                            office.getAddDate(), office.getPhoto()});
            Cursor cursor = db.rawQuery("select last_insert_rowid()", null);
            int Id = 0;
            if (cursor.moveToFirst()) {
                Id = cursor.getInt(0);
            }
            for (Office.Member member : office.getMemberList()) {
                db.execSQL("insert into Members (DetailID,Chengwei,Name,Age,Zzmm ,Work) values (?,?,?,?,?,?)",
                        new Object[]{Id, member.getChengwei(), member.getName(), member.getAge(), member.getZzmm(), member.getWork()});
            }
            cursor.close();
        }
        }catch(Exception e) {
            String a = e.getMessage();
            a.trim();
        }
    }

    public static Office getOffice(CadresApplication app,String name) throws ParseException {

        String sql = "Select * from rlmDetail where Name like ?";
        SQLiteDatabase db = createDepartment(app);

        Cursor cursor = db.rawQuery(sql, new String[]{"%"+name+"%"});
        Office office = new Office();
        if(cursor.moveToFirst()){//判断游标是否为空
            office.setName(cursor.getString(cursor.getColumnIndex("Name")));
            office.setSex(cursor.getString(cursor.getColumnIndex("Sex")));
            office.setBirth(cursor.getString(cursor.getColumnIndex("Birth")));
            office.setMinzu(cursor.getString(cursor.getColumnIndex("Minzu")));
            office.setJiguan(cursor.getString(cursor.getColumnIndex("Jiguan")));
            office.setBirthAddr(cursor.getString(cursor.getColumnIndex("BirthAddr")));
            office.setRudangDate(cursor.getString(cursor.getColumnIndex("RudangDate")));
            office.setGognzuoDate(cursor.getString(cursor.getColumnIndex("GognzuoDate")));
            office.setJiankangStatus(cursor.getString(cursor.getColumnIndex("JiankangStatus")));
            office.setZhicheng(cursor.getString(cursor.getColumnIndex("Zhicheng")));
            office.setTechang(cursor.getString(cursor.getColumnIndex("Techang")));
            office.setQrzjy(cursor.getString(cursor.getColumnIndex("Qrzjy")));
            office.setQrzSchool(cursor.getString(cursor.getColumnIndex("QrzSchool")));
            office.setZzjy(cursor.getString(cursor.getColumnIndex("Zzjy")));
            office.setZzSchool(cursor.getString(cursor.getColumnIndex("ZzSchool")));
            office.setXrzw(cursor.getString(cursor.getColumnIndex("Xrzw")));
            office.setNrzw(cursor.getString(cursor.getColumnIndex("Nrzw")));
            office.setNmzw(cursor.getString(cursor.getColumnIndex("Nmzw")));
            office.setJianli(cursor.getString(cursor.getColumnIndex("Jianli")));
            office.setJiangcheng(cursor.getString(cursor.getColumnIndex("Jiangcheng")));
            office.setKaohe(cursor.getString(cursor.getColumnIndex("Kaohe")));
            office.setRmReason(cursor.getString(cursor.getColumnIndex("RmReason")));
            byte[] photos = cursor.getBlob(cursor.getColumnIndex("photo"));
            office.setPhoto(photos);

            String membersSQL = "Select * from Members where DetailID=?";

            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            Cursor membersCursor = db.rawQuery(membersSQL, new String[]{id + ""});

            List<Office.Member> memberList = new ArrayList<>();
            if(membersCursor.moveToFirst()){//判断游标是否为空
                int cursorCount = membersCursor.getCount();
                for(int i=0;i<cursorCount;i++){
                    try {
                        Office.Member member =new Office.Member();
                        member.setChengwei(membersCursor.getString(membersCursor.getColumnIndex("Chengwei")));
                        member.setName(membersCursor.getString(membersCursor.getColumnIndex("Name")));
                        member.setAge(membersCursor.getString(membersCursor.getColumnIndex("Age")));
                        member.setZzmm(membersCursor.getString(membersCursor.getColumnIndex("Zzmm")));
                        member.setWork(membersCursor.getString(membersCursor.getColumnIndex("Work")));

                        memberList.add(member);

                        membersCursor.moveToNext();//移动到指定记录
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            office.setMemberList(memberList);
            office.setCbdwyj(cursor.getString(cursor.getColumnIndex("Cbdwyj")));
            office.setAddDate(cursor.getString(cursor.getColumnIndex("AddDate")));
        }
        cursor.close();
        return office;
    }

    //详细信息表
    private static void createLRM(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS rlmDetail (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name VARCHAR(20)," +
                "Sex VARCHAR(20),Birth VARCHAR(20),Minzu VARCHAR(20),Jiguan VARCHAR(20),BirthAddr VARCHAR(20),RudangDate VARCHAR(20)," +
                "GognzuoDate VARCHAR(20),JiankangStatus VARCHAR(20),Zhicheng VARCHAR(20),Techang VARCHAR(20),Qrzjy VARCHAR(20)," +
                "QrzSchool VARCHAR(20),Zzjy VARCHAR(20),ZzSchool VARCHAR(20),Xrzw VARCHAR(20),Nrzw VARCHAR(20),Nmzw VARCHAR(20),Jianli TEXT," +
                "Jiangcheng VARCHAR(20),Kaohe VARCHAR(20),RmReason VARCHAR(20),Cbdwyj VARCHAR(20),AddDate VARCHAR(20),photo BLOB)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Members (ID INTEGER PRIMARY KEY AUTOINCREMENT,DetailID INTEGER,Chengwei VARCHAR(20)," +
                "Name VARCHAR(20),Age VARCHAR(20),Zzmm VARCHAR(20),Work VARCHAR(20))");
    }
    //详细信息表
    private static SQLiteDatabase createLRM(CadresApplication app) {
        SQLiteDatabase db = app.getDateBase();
        db.beginTransaction();  //手动设置开始事务
        db.execSQL("CREATE TABLE IF NOT EXISTS rlmDetail (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name VARCHAR(20)," +
                "Sex VARCHAR(20),Birth VARCHAR(20),Minzu VARCHAR(20),Jiguan VARCHAR(20),BirthAddr VARCHAR(20),RudangDate VARCHAR(20)," +
                "GognzuoDate VARCHAR(20),JiankangStatus VARCHAR(20),Zhicheng VARCHAR(20),Techang VARCHAR(20),Qrzjy VARCHAR(20)," +
                "QrzSchool VARCHAR(20),Zzjy VARCHAR(20),ZzSchool VARCHAR(20),Xrzw VARCHAR(20),Nrzw VARCHAR(20),Nmzw VARCHAR(20),Jianli TEXT," +
                "Jiangcheng VARCHAR(20),Kaohe VARCHAR(20),RmReason VARCHAR(20),Cbdwyj VARCHAR(20),AddDate VARCHAR(20),photo BLOB)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Members (ID INTEGER PRIMARY KEY AUTOINCREMENT,DetailID INTEGER,Chengwei VARCHAR(20)," +
                "Name VARCHAR(20),Age VARCHAR(20),Zzmm VARCHAR(20),Work VARCHAR(20))");
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        db.endTransaction(); //处理完成
        return db;
    }

    public static void DeleteAllData(CadresApplication app) {
        SQLiteDatabase db = app.getDateBase();
        db.beginTransaction();  //手动设置开始事务
        db.execSQL("DROP TABLE IF EXISTS roster");
        db.execSQL("DROP TABLE IF EXISTS department");
        db.execSQL("DROP TABLE IF EXISTS rlmDetail");
        db.execSQL("DROP TABLE IF EXISTS Members");
        db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
        db.endTransaction(); //处理完成
    }

    public static void DeleteAllFile(String path) {
        File file  = new File(path);
        File[] fileList = file.listFiles();
        for(File child : fileList) {
            if(child.isDirectory()) {
                DeleteAllFile(child.getPath());
            }
            child.delete();
        }
    }
}
