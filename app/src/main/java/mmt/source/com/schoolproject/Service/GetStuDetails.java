package mmt.source.com.schoolproject.Service;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mmt.source.com.schoolproject.model.SvTrack;

public class GetStuDetails extends AsyncTask<String, Void, Integer> {

    SvTrack svt_inst = SvTrack.getInstance();

    @Override
    protected Integer doInBackground(String... params) {
        Integer result = 0;
        BufferedReader reader = null;
        try {
            // Create Apache HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost("http://mindmapstechnologies.com:9000/SvnTracking/mmt/User/GetStudentById");

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

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            result = statusCode;
            if (statusCode == 200) {

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

            svt_inst.getStudentDetails().setStuId(item.getString("stuId"));
            svt_inst.getStudentDetails().setStuName(item.getString("stuName"));
            svt_inst.getStudentDetails().setStuGen(item.getString("stuGen"));
            svt_inst.getStudentDetails().setStuStandard(item.getString("stuStandard"));
            svt_inst.getStudentDetails().setStuDob(item.getString("stuDob"));
            svt_inst.getStudentDetails().setStuAddr(item.getString("stuAddr"));
            svt_inst.getStudentDetails().setStuPincode(item.getString("stuPincode"));
            svt_inst.getStudentDetails().setStuArea(item.getString("stuArea"));
            svt_inst.getStudentDetails().setRouteId(item.getString("routeId"));
            svt_inst.getStudentDetails().setStuPickUpTime(item.getString("stuPickUpTime"));
            svt_inst.getStudentDetails().setStuDropTime(item.getString("stuDropTime"));
            svt_inst.getStudentDetails().setUsrId(item.getString("usrId"));

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
