package service;

import af.sql.c3p0.AfSimpleDB;
import data.Admin;
import data.Borrower;
import data.DataUtil;
import data.Reservation;
import utility.database.DateFormat;
import utility.database.SqlUpdate;

import java.util.ArrayList;
import java.util.List;

public class AdminService {
    public static Admin createAdmin(){
        return  new Admin();
    }
    public static boolean isExistent(int id){
        String query="select admin_id from admin where admin_id="+id;
        try {
            String [] result=AfSimpleDB.get(query);
            if(result==null) return  false;
            else return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean add(Admin admin)
    {
        try {
            AfSimpleDB.insert(admin);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void update(Admin admin){
        SqlUpdate asu=new SqlUpdate("admin");
        asu.add("name",admin.getName());
        asu.add("adr",admin.getAdr());
        asu.add3("sex",admin.getSex());
        asu.add2("birth", DataUtil.isNull(admin.getBirth()) ? null:DateFormat.DBDate(admin.getBirth()));
        asu.add2("tel",admin.getTel());
        asu.add2("workTime",admin.getWorkTime());
        asu.add2("salary",admin.getSalary());
        asu.add2("level",admin.getLevel());
        asu.add2("pw",admin.getPw());
        String s1 = asu + " where work_id=" + admin.getWork_id();
        try {
            AfSimpleDB.execute(s1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Admin getAdmin(int id){
        String query="select * from admin where work_id="+id;
        try {
            Admin admin= (Admin)AfSimpleDB.get(query,Admin.class);
            return admin;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<Borrower> getAllBorrowers(){
        List<Borrower> borrowers = new ArrayList<>();
        int lineID = 0;
        while(true){
            String query = "select * from borrower order by borrower_id asc limit "+lineID+","+(lineID+1);
            lineID += 1;
            Borrower borrower = null;
            try{
                borrower = (Borrower) AfSimpleDB.get(query,Borrower.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(borrower != null){
                borrowers.add(borrower);
            }else{
                break;
            }
        }
        return borrowers;
    }
}
