package mmt.source.com.schoolproject.Dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mmt.source.com.schoolproject.Util.DbHelper;
import mmt.source.com.schoolproject.model.SvTrack;

public class DaoUtil {

    SQLiteDatabase db;

    public DaoUtil() {
        if(DbHelper.getInstance() != null){
            db = DbHelper.getInstance().getWritableDatabase();

            db.execSQL("CREATE TABLE IF NOT EXISTS UserDetails(usrId VARCHAR(20), usrName VARCHAR(100), usrType VARCHAR(50), mobNum VARCHAR(50), emailId VARCHAR(100), usrAddr VARCHAR(250), bloodG VARCHAR(100), studentId VARCHAR(100), status VARCHAR(100), password VARCHAR(100))");
            db.execSQL("CREATE TABLE IF NOT EXISTS StuDetails(stuId VARCHAR(20), stuName VARCHAR(100), stuGen VARCHAR(50), stuStandard VARCHAR(50), stuDob VARCHAR(100), stuAddr VARCHAR(250), stuPincode VARCHAR(100), stuArea VARCHAR(100), routeId VARCHAR(100), stuPickUpTime VARCHAR(100), stuDropTime VARCHAR(100))");

        }else {
            throw new RuntimeException("DBHelper not initialized");
        }
    }

    public void addUserDetails() {
        SvTrack svt_inst = SvTrack.getInstance();

        db.execSQL("INSERT INTO UserDetails VALUES('"+svt_inst.getUserDetails().getUsrId()+"','"+svt_inst.getUserDetails().getUsrName()+"','"+svt_inst.getUserDetails().getUsrType()+"', '"+svt_inst.getUserDetails().getMobNum()+"','"+svt_inst.getUserDetails().getEmailId()+"', '"+svt_inst.getUserDetails().getUsrAddr()+"', '"+svt_inst.getUserDetails().getBloodG()+"', '"+svt_inst.getUserDetails().getStudentId()+"', '"+svt_inst.getUserDetails().getStatus()+"', '"+svt_inst.getUserDetails().getPassword()+"')");
    }

    public void addStuDetails() {
        SvTrack svt_inst = SvTrack.getInstance();

        db.execSQL("INSERT INTO StuDetails VALUES('"+svt_inst.getStudentDetails().getStuId()+"','"+svt_inst.getStudentDetails().getStuName()+"','"+svt_inst.getStudentDetails().getStuGen()+"', '"+svt_inst.getStudentDetails().getStuStandard()+"','"+svt_inst.getStudentDetails().getStuDob()+"', '"+svt_inst.getStudentDetails().getStuAddr()+"', '"+svt_inst.getStudentDetails().getStuPincode()+"', '"+svt_inst.getStudentDetails().getStuArea()+"', '"+svt_inst.getStudentDetails().getRouteId()+"', '"+svt_inst.getStudentDetails().getStuPickUpTime()+"', '"+svt_inst.getStudentDetails().getStuDropTime()+"')");
    }
    public void deleteUserDetails() {
            db.delete("StuDetails", null, null);
            db.delete("UserDetails", null, null);
    }


    public void getUserDetails() {
        Cursor cursor = db.rawQuery("Select * from UserDetails", new String[]{});
        SvTrack svt_inst = SvTrack.getInstance();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            svt_inst.getUserDetails().setUsrId(cursor.getString(0));
            svt_inst.getUserDetails().setUsrName(cursor.getString(1));
            svt_inst.getUserDetails().setUsrType(cursor.getString(2));
            svt_inst.getUserDetails().setMobNum(cursor.getString(3));
            svt_inst.getUserDetails().setEmailId(cursor.getString(4));
            svt_inst.getUserDetails().setUsrAddr(cursor.getString(5));
            svt_inst.getUserDetails().setBloodG(cursor.getString(6));
            svt_inst.getUserDetails().setStudentId(cursor.getString(7));
            svt_inst.getUserDetails().setStatus(cursor.getString(8));

        }
        else {

            svt_inst.getUserDetails().setUsrId("-1");
        }
    }


    public void getStuDetails() {
        Cursor cursor = db.rawQuery("Select * from StuDetails", new String[]{});
        SvTrack svt_inst = SvTrack.getInstance();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            svt_inst.getStudentDetails().setStuId(cursor.getString(0));
            svt_inst.getStudentDetails().setStuName(cursor.getString(1));
            svt_inst.getStudentDetails().setStuGen(cursor.getString(2));
            svt_inst.getStudentDetails().setStuStandard(cursor.getString(3));
            svt_inst.getStudentDetails().setStuDob(cursor.getString(4));
            svt_inst.getStudentDetails().setStuAddr(cursor.getString(5));
            svt_inst.getStudentDetails().setStuPincode(cursor.getString(6));
            svt_inst.getStudentDetails().setStuArea(cursor.getString(7));
            svt_inst.getStudentDetails().setRouteId(cursor.getString(8));
            svt_inst.getStudentDetails().setStuPickUpTime(cursor.getString(9));
            svt_inst.getStudentDetails().setStuDropTime(cursor.getString(10));

        }
        else {

            svt_inst.getStudentDetails().setStuId("-1");
        }
    }
}
