package com.itianyi.bean;

import android.database.sqlite.SQLiteDatabase;

import com.itianyi.app.CadresApplication;
import com.itianyi.app.CharacterParser;
import com.itianyi.app.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by 沫 on 2015/7/20.
 */
public class Roster {
    private int xh;
    private String xm;
    private String szm;
    private String xb;
    private String mz;
    private String jg;
    private Date csrq;
    private int NL;
    private Date cjgzsj;
    private int GL;
    private String zzmm;
    private String xl;
    private String zy;
    private String yyyx;
    private String xzj;
    private Date xrzjsj;
    private int ZL;
    private String dw;
    private String szbm;
    private String xzw;
    private String xrzwsj;
    private String xjx;
    private String zwlb;

    public int getXh() {
        return xh;
    }

    public void setXh(int xh) {
        this.xh = xh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }
    public void setSZM(String szm) {
        this.szm= szm;
    }
    public void setSZMByXM(String xm) {
        String mXM=xm.replace(" ","");
        StringBuilder szm = new StringBuilder();
        CharacterParser characterParser = CharacterParser.getInstance();
        for(int i = 0;i<mXM.length();i++) {
            String pinyin = characterParser.getSelling(mXM.charAt(i)+"");
            szm.append(pinyin.substring(0, 1).toUpperCase());
        }
        this.szm = szm.toString();
    }
    public String getszm() {
        return szm;
    }
    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg;
    }

    public Date getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq,Date reference_time) throws ParseException {
        if(!"".equals(csrq)&&csrq!=null){
            try{
                SimpleDateFormat sdf=null;
                if(csrq.length()>7){
                    sdf= new SimpleDateFormat("yyyy.MM.dd");
                    if(csrq.contains("/")) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    }
                }else {
                    sdf= new SimpleDateFormat("yyyy.MM");
                    if(csrq.contains("/")) {
                        sdf = new SimpleDateFormat("yyyy/MM");
                    }
                }

                Date date = sdf.parse(csrq);
                this.csrq = date;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(reference_time);
                int now_year = calendar.get(Calendar.YEAR);
                int now_month = calendar.get(Calendar.MONTH);

                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                this.NL = now_year - year;
                if(now_month<month) {
                    this.NL--;
                }
            }catch(Exception e){

            }
        }

    }

    public Date getCjgzsj() {
        return cjgzsj;
    }

    public void setCjgzsj(String cjgzsj,Date reference_time) throws ParseException {
        if(!"".equals(cjgzsj)&&cjgzsj!=null){
            try {
                SimpleDateFormat sdf = null;
                if(cjgzsj.length()>7){
                    sdf=new SimpleDateFormat("yyyy.MM.dd");
                    if(cjgzsj.contains("/")) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    }
                }else {
                    sdf=new SimpleDateFormat("yyyy.MM");
                    if(cjgzsj.contains("/")) {
                        sdf = new SimpleDateFormat("yyyy/MM");
                    }
                }

                Date date = sdf.parse(cjgzsj);
                this.cjgzsj = date;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(reference_time);
                int now_year = calendar.get(Calendar.YEAR);
                int now_month = calendar.get(Calendar.MONTH);

                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                this.GL = now_year - year;
                if(now_month<month) {
                    this.GL--;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public String getZzmm() {
        return zzmm;
    }

    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }

    public String getXl() {
        return xl;
    }

    public void setXl(String xl) {
        this.xl = xl;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy;
    }

    public String getYyyx() {
        return yyyx;
    }

    public void setYyyx(String yyyx) {
        this.yyyx = yyyx;
    }

    public String getXzj() {
        return xzj;
    }

    public void setXzj(String xzj) {
        this.xzj = xzj;
    }

    public Date getXrzjsj() {
        return xrzjsj;
    }

    public void setXrzjsj(String xrzjsj,Date reference_time) throws ParseException {
        if(!"".equals(xrzjsj)&&xrzjsj!=null){
            try{
                SimpleDateFormat sdf =null;
                if(xrzjsj.length()>7){
                     sdf = new SimpleDateFormat("yyyy.MM.dd");
                    if(xrzjsj.contains("/")) {
                        sdf = new SimpleDateFormat("yyyy/MM/dd");
                    }
                }else{
                     sdf = new SimpleDateFormat("yyyy.MM");
                    if(xrzjsj.contains("/")) {
                        sdf = new SimpleDateFormat("yyyy/MM");
                    }

                }

                Date date = sdf.parse(xrzjsj);
                this.xrzjsj = date;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(reference_time);
                int now_year = calendar.get(Calendar.YEAR);
                int now_month = calendar.get(Calendar.MONTH);

                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);

                this.ZL = now_year - year;
                if(now_month<month) {
                    this.ZL--;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public String getSzbm() {
        return szbm;
    }

    public void setSzbm(String szbm) {
        this.szbm = szbm;
    }

    public String getXzw() {
        return xzw;
    }

    public void setXzw(String xzw) {
        this.xzw = xzw;
    }

    public String getXrzwsj() {
        return xrzwsj;
    }

    public void setXrzwsj(String xrzwsj) {
        this.xrzwsj = xrzwsj;
    }

    public static Roster JsonToDTO(JSONObject object,Date reference_time) throws Exception {
        Roster roster = new Roster();
        roster.setXh(object.getInt("序号"));
        roster.setXm(ObjectItemToString(object,"姓名").replace(" ",""));
        roster.setSZMByXM(ObjectItemToString(object,"姓名"));
        roster.setXb(ObjectItemToString(object,"性别"));
        roster.setMz(ObjectItemToString(object,"民族"));
        roster.setJg(ObjectItemToString(object,"籍贯"));
        roster.setCsrq(ObjectItemToString(object,"出生日期"),reference_time);
        roster.setCjgzsj(ObjectItemToString(object,"参加工作时间"),reference_time);
        roster.setZzmm(ObjectItemToString(object,"政治面貌"));
        roster.setXl(ObjectItemToString(object,"学历"));
        roster.setZy(ObjectItemToString(object,"专业"));
        roster.setYyyx(ObjectItemToString(object,"毕业院校"));
        roster.setXzj(ObjectItemToString(object,"现职级"));
        roster.setXrzjsj(ObjectItemToString(object,"任现职级时间"),reference_time);
        roster.setSzbm(ObjectItemToString(object, "所在部门"));
            roster.setDw(ObjectItemToString(object, "单位"));
            roster.setXrzwsj(ObjectItemToString(object,"任现职务时间"));
            roster.setXzw(ObjectItemToString(object, "现职务"));

            roster.setXjx(ObjectItemToString(object, "警衔"));
            roster.setZwlb(ObjectItemToString(object,"职务类别"));
        return roster;
    }

    public static String ObjectItemToString(JSONObject object, String Name) {
        try {
            String str = object.getString(Name);
            return str;
        } catch (JSONException e) {
            return "";
        }
    }

    public static void saveList(String strJSON,SQLiteDatabase db,Date reference_time) throws Exception {

        JSONArray jsonArray = null;
        jsonArray = new JSONArray(strJSON);
        List<Roster> objects = new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++) {
            int count = ((JSONObject)jsonArray.get(i)).getJSONArray("list").length();
            for(int j = 0 ;j<count;j++) {
                JSONObject object = (JSONObject) ((JSONObject) jsonArray.get(i)).getJSONArray("list").get(j);

                objects.add(Roster.JsonToDTO(object,reference_time));
            }
        }
        SQLiteHelper.insertRoster(db, objects);
    }

    public int getNL() {
        return NL;
    }

    public int getGL() {
        return GL;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getXjx() {
        return xjx;
    }

    public void setXjx(String xjx) {
        this.xjx = xjx;
    }

    public String getZwlb() {
        return zwlb;
    }

    public void setZwlb(String zwlb) {
        this.zwlb = zwlb;
    }

    public int getZL() {
        return ZL;
    }

    public void setZL(int ZL) {
        this.ZL = ZL;
    }
}
