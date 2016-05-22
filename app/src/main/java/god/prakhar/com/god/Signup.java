package god.prakhar.com.god;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by Prakhar Gupta on 22/05/2016.
 */
public class Signup extends Activity {

    EditText ed1,ed2,ed3,ed4,ed5,ed6;
    String username,email,password,cpassword;
    int pstatus = 0;
    Button b1;

    private String urlJsonObj = "http://hindi.pythonanywhere.com/signup";
    private ProgressDialog pDialog;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        b1 = (Button) findViewById(R.id.button1);

        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);
        ed4 = (EditText) findViewById(R.id.editText4);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                assignValues();

                checkPassword();

                if(pstatus == 2) {
                    Toast.makeText(getApplication(),"Password cannot be blank :/",Toast.LENGTH_LONG).show();
                    return;
                }else if (pstatus == 0)
                {
                    Toast.makeText(getApplication(), "Passwords do not match :/", Toast.LENGTH_LONG).show();
                    return;
                }else if (pstatus == 1)
                {
                    makeJsonObjectRequest();
                }
            }
        });
    }

    private void assignValues()
    {
        username = ed1.getText().toString();
        email = ed4.getText().toString();
        password = ed5.getText().toString();
        cpassword = ed6.getText().toString();
    }


    private void checkPassword()
    {
        pstatus = 0;
        if (password.equals(""))
        {
            pstatus = 2;
        }
        else if (password != null && cpassword != null)
        {
            if (password.equals(cpassword))
            {
                pstatus = 1;
            }
        }
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void makeJsonObjectRequest()
    {
        showpDialog();

        StringRequest sr = new StringRequest(Request.Method.POST, urlJsonObj, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if (status.equals("success"))
                    {
                        Toast.makeText(getApplication(),"Account Successfully Created :D",Toast.LENGTH_SHORT).show();
                        intent = new Intent(Signup.this,Login_Page_User.class);
                        startActivity(intent);
                    }else if (status.equals("error"))
                    {
                        Toast.makeText(getApplication(),"Email or Username already exists :/",Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getApplication(),"wtf server",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e)
                {
                    Toast.makeText(getApplication(), "Sign Up Failed :(", Toast.LENGTH_SHORT).show();
                    System.out.println(e);
                }

                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(),"No internet connection",LENGTH_SHORT).show();
                System.out.println(error);
                hidepDialog();
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(sr);
    }
}
