package mn.edu.num.login;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;

    DatabaseHelper myDb;
    private Button btLogin, btSignup;
    private EditText etusername, etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        etpassword = findViewById(R.id.etpassword);
        etusername = findViewById(R.id.etusername);
        btLogin = findViewById(R.id.btLogin);
        myDb = new DatabaseHelper(this);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        String name = mPreferences.getString(getString(R.string.name), "");
        etusername.setText(name);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    public void Login() {
        String username = etusername.getText().toString();
        String password = etpassword.getText().toString();

        Log.i("Puujedb", username + " \t " +password);
        if (username.length() > 0 && password.length() > 0) {
            if (myDb.checkUser(username, password)) {
                String name = etusername.getText().toString();
                mEditor.putString(getString(R.string.name), name);
                mEditor.commit();


                Intent intent = new Intent(MainActivity.this, UserInfo.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Username or password is incorrect.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Please insert username and password", Toast.LENGTH_SHORT).show();
        }
    }

    public void SignUp(View v) {
        if (etusername.getText().toString().length() > 0 && etpassword.getText().toString().length() > 0) {
            Intent intent = new Intent(MainActivity.this, Signup.class);
            intent.putExtra("username", etusername.getText().toString());
            intent.putExtra("password", etpassword.getText().toString());
            startActivity(intent);
        }
    }
}
