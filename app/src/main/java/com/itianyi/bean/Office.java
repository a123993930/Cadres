package com.itianyi.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.itianyi.app.SQLiteHelper;
import com.itianyi.app.CadresApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 沫 on 2015/7/10.
 */
public class Office {
    private Context context;


    public static void saveList(String lrmJSON,SQLiteDatabase db) throws JSONException {

        JSONArray lrmJSONArray = null;
        lrmJSONArray = new JSONArray(lrmJSON);

        List<Office> officeList = JsonArrayToList(lrmJSONArray);
        SQLiteHelper.InsertLRM(db, officeList);
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static class Member{
        private String Chengwei = "";
        private String Name = "";
        private String Age = "";
        private String Zzmm = "";
        private String Work = "";

        public String getChengwei() {
            return Chengwei;
        }

        public void setChengwei(String chengwei) {
            Chengwei = chengwei;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            Age = age;
        }

        public String getZzmm() {
            return Zzmm;
        }

        public void setZzmm(String zzmm) {
            Zzmm = zzmm;
        }

        public String getWork() {
            return Work;
        }

        public void setWork(String work) {
            Work = work;
        }

    }
    private String Name = "";
    private String Sex = "";
    private String Birth = "";
    private String Minzu = "";
    private String Jiguan = "";
    private String BirthAddr = "";
    private String RudangDate = "";
    private String GognzuoDate = "";
    private String JiankangStatus = "";
    private String Zhicheng = "";
    private String Techang = "";
    private String Qrzjy = "";
    private String QrzSchool = "";
    private String Zzjy = "";
    private String ZzSchool = "";
    private String Xrzw = "";
    private String Nrzw = "";
    private String Nmzw = "";
    private String Jianli = "";
    private String Jiangcheng = "";
    private String Kaohe = "";
    private String RmReason = "";
    private String Cbdwyj = "";
    private String AddDate = "";
    private List<Member> memberList = new ArrayList<>();
    private byte[] Photo;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getBirth() {
        return Birth;
    }

    public void setBirth(String birth) {
        Birth = birth;
    }

    public String getMinzu() {
        return Minzu;
    }

    public void setMinzu(String minzu) {
        Minzu = minzu;
    }

    public String getJiguan() {
        return Jiguan;
    }

    public void setJiguan(String jiguan) {
        Jiguan = jiguan;
    }

    public String getBirthAddr() {
        return BirthAddr;
    }

    public void setBirthAddr(String birthAddr) {
        BirthAddr = birthAddr;
    }

    public String getRudangDate() {
        return RudangDate;
    }

    public void setRudangDate(String rudangDate) {
        RudangDate = rudangDate;
    }

    public String getGognzuoDate() {
        return GognzuoDate;
    }

    public void setGognzuoDate(String gognzuoDate) {
        GognzuoDate = gognzuoDate;
    }

    public String getJiankangStatus() {
        return JiankangStatus;
    }

    public void setJiankangStatus(String jiankangStatus) {
        JiankangStatus = jiankangStatus;
    }

    public String getZhicheng() {
        return Zhicheng;
    }

    public void setZhicheng(String zhicheng) {
        Zhicheng = zhicheng;
    }

    public String getTechang() {
        return Techang;
    }

    public void setTechang(String techang) {
        Techang = techang;
    }

    public String getQrzjy() {
        return Qrzjy;
    }

    public void setQrzjy(String qrzjy) {
        Qrzjy = qrzjy;
    }

    public String getQrzSchool() {
        return QrzSchool;
    }

    public void setQrzSchool(String qrzSchool) {
        QrzSchool = qrzSchool;
    }

    public String getZzjy() {
        return Zzjy;
    }

    public void setZzjy(String zzjy) {
        Zzjy = zzjy;
    }

    public String getZzSchool() {
        return ZzSchool;
    }

    public void setZzSchool(String zzSchool) {
        ZzSchool = zzSchool;
    }

    public String getXrzw() {
        return Xrzw;
    }

    public void setXrzw(String xrzw) {
        Xrzw = xrzw;
    }

    public String getNrzw() {
        return Nrzw;
    }

    public void setNrzw(String nrzw) {
        Nrzw = nrzw;
    }

    public String getNmzw() {
        return Nmzw;
    }

    public void setNmzw(String nmzw) {
        Nmzw = nmzw;
    }

    public String getJianli() {
        return Jianli;
    }

    public void setJianli(String jianli) {
        Jianli = jianli;
    }

    public String getJiangcheng() {
        return Jiangcheng;
    }

    public void setJiangcheng(String jiangcheng) {
        Jiangcheng = jiangcheng;
    }

    public String getKaohe() {
        return Kaohe;
    }

    public void setKaohe(String kaohe) {
        Kaohe = kaohe;
    }

    public String getRmReason() {
        return RmReason;
    }

    public void setRmReason(String rmReason) {
        RmReason = rmReason;
    }

    public String getCbdwyj() {
        return Cbdwyj;
    }

    public void setCbdwyj(String cbdwyj) {
        Cbdwyj = cbdwyj;
    }

    public String getAddDate() {
        return AddDate;
    }

    public void setAddDate(String addDate) {
        AddDate = addDate;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public static List<Office> JsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<Office> officeList = new ArrayList<>();
        int count = jsonArray.length();
        try {
        for(int i = 0;i<count;i++) {
            Office office = new Office();
            JSONObject object = (JSONObject) jsonArray.get(i);
            office.setName(object.getString("Name"));
            office.setSex(object.getString("Sex"));
            office.setBirth(object.getString("Birth"));
            office.setMinzu(object.getString("Minzu"));
            office.setJiguan(object.getString("Jiguan"));
            office.setBirthAddr(object.getString("BirthAddr"));
            office.setRudangDate(object.getString("RudangDate"));
            office.setGognzuoDate(object.getString("GognzuoDate"));
            office.setJiankangStatus(object.getString("JiankangStatus"));
            office.setZhicheng(object.getString("Zhicheng"));
            office.setTechang(object.getString("Techang"));
            office.setQrzjy(object.getString("Qrzjy"));
            office.setQrzSchool(object.getString("QrzSchool"));
            office.setZzjy(object.getString("Zzjy"));
            office.setZzSchool(object.getString("ZzSchool"));
            office.setXrzw(object.getString("Xrzw"));
            office.setNrzw(object.getString("Nrzw"));
            office.setNmzw(object.getString("Nmzw"));
            office.setJianli(object.getString("Jianli"));
            office.setJiangcheng(object.getString("Jiangcheng"));
            office.setKaohe(object.getString("Kaohe"));
            office.setRmReason(object.getString("RmReason"));
            JSONArray members = object.getJSONArray("Members");
            List<Member> memeberList = new ArrayList<>();
            int memberLength = members.length();
            for (int j = 0; j < memberLength; j++) {
                JSONObject memberObject = (JSONObject) members.get(j);
                Member member = new Member();
                if (memberObject.getString("Chengwei").isEmpty()) {
                    continue;
                }
                member.setChengwei(memberObject.getString("Chengwei"));
                member.setName(memberObject.getString("Name"));
                member.setAge(memberObject.getString("Age"));
                member.setZzmm(memberObject.getString("Zzmm"));
                member.setWork(memberObject.getString("Work"));
                memeberList.add(member);
            }
            office.setMemberList(memeberList);
            office.setCbdwyj(object.getString("Cbdwyj"));
            office.setAddDate(object.getString("AddDate"));

            Bitmap imageBitmap = BitmapFactory.decodeFile(CadresApplication.mFilePath+ File.separator+"images"+File.separator+office.getName()+".pic");
            if(imageBitmap == null) {
                office.setPhoto(new byte[0]);
            } else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                office.setPhoto(baos.toByteArray());
            }
            officeList.add(office);
        }

        } catch (Exception e) {
            String a =e.getMessage();
            a = a.trim();
        }
        return officeList;
    }

}
