package mmt.source.com.schoolproject.Service;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mmt.source.com.schoolproject.model.SvTrack;
import mmt.source.com.schoolproject.model.User;

public class UserRegister extends AsyncTask<String, Void, Integer> {

    SvTrack svt_inst = SvTrack.getInstance();

    @Override
    protected Integer doInBackground(String... params) {
        Integer result = 0;
        BufferedReader reader = null;
        try {
            // Create Apache HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://mindmapstechnologies.com:9000/SvnTracking/mmt/User/AddUser");

            System.out.println("shiva get login called ");

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            //postRequest.setEntity(new StringEntity(json, "UTF-8"));
            StringEntity stringEntity = new StringEntity(gson.toJson(svt_inst.getUserDetails()));
            postRequest.setEntity(stringEntity);
            postRequest.setHeader("Content-Type", "application/json");

            HttpResponse httpResponse = httpclient.execute(postRequest);


            String line;
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            if((line = reader.readLine())!=null){
                System.out.println("shiva get register response "+line);
            }
            System.out.println("Status code "+httpResponse.getStatusLine().getStatusCode());

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            result = statusCode;
            if (statusCode == 200) {
                System.out.println("shiva successful login ");
                parseResult(line);

            } else {
                System.out.println("shiva failure response received");
            }

        } catch (Exception e) {
            System.out.println("shiva"+e.getCause());
            e.printStackTrace();
            System.out.println("shiva exception occurred ");
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }




    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    private void parseResult(String result) {
        try {

            SvTrack svt_inst = SvTrack.getInstance();

            JSONObject item = new JSONObject(result);

            svt_inst.getUserDetails().setUsrId(item.getString("usrId"));
            svt_inst.getUserDetails().setUsrName(item.getString("usrName"));
            svt_inst.getUserDetails().setUsrType(item.getString("usrType"));
            svt_inst.getUserDetails().setMobNum(item.getString("mobNum"));
            svt_inst.getUserDetails().setEmailId(item.getString("emailId"));
            svt_inst.getUserDetails().setUsrAddr(item.getString("usrAddr"));
            svt_inst.getUserDetails().setBloodG(item.getString("bloodG"));
            svt_inst.getUserDetails().setStudentId(item.getString("studentId"));
            svt_inst.getUserDetails().setStatus(item.getString("status"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        // Download complete. Lets update UI

        if (result == 1) {

            //mGridAdapter.setGridData(mGridData);
        } else {
            // Toast.makeText(LocationViewActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
