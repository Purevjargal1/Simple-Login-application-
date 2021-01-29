package mn.edu.num.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ChangePassword extends AppCompatActivity {

    Button btSave;
    EditText etoldpass, etnewpass, etrepass;
    String username, oldpass, newpass;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent i = getIntent();
        username = i.getStringExtra("username");

        myDb = new DatabaseHelper(this);

        btSave = findViewById(R.id.btSave);
        etoldpass = findViewById(R.id.etoldpass);
        etnewpass = findViewById(R.id.etnewpass);
        etrepass = findViewById(R.id.etrepass);

//        ArrayList arrayList = myDb.getAllUser();

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();
            }
        });
    }

    public void changePass() {
        oldpass = etoldpass.getText().toString();
        newpass = etnewpass.getText().toString();

        Cursor checkPass = myDb.checkPwd(username);
        checkPass.moveToFirst();

        String pwd = checkPass.getString(checkPass.getColumnIndex(DatabaseHelper.COL3));

        if (!checkPass.isClosed()) {
            checkPass.close();
        }

        if (pwd.equals(oldpass) && newpass.length() > 0) {
            if (newpass.equals(etrepass.getText().toString())) {
                myDb.updatePass(username, newpass);
                Toast.makeText(this, "Password changed successfully.", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Password confirmation is incorrect.", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show();
    }
}
