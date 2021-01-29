package mn.edu.num.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Signup extends AppCompatActivity {

    DatabaseHelper myDb;
    String username, password, confirmpass, age, gender, number, birthdate;
    EditText etage, etgender, etnumber, etrepass;
    DatePicker dob;
    Button btSave, btCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        etage = findViewById(R.id.etage);
        etgender = findViewById(R.id.etgender);
        etnumber = findViewById(R.id.etnumber);
        dob = findViewById(R.id.dob);
        etrepass = findViewById(R.id.etrepass);

        myDb = new DatabaseHelper(this);

        Intent intent=getIntent();
        username = intent.getStringExtra("username");
        password = intent.getStringExtra("password");

        btSave = findViewById(R.id.btSave);
        btCancel = findViewById(R.id.btCancel);

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void addUser() {
        age = etage.getText().toString();
        gender = etgender.getText().toString();
        number = etnumber.getText().toString();
        confirmpass = etrepass.getText().toString();


        long dateTime = dob.getCalendarView().getDate();
        Date date = new Date(dateTime);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", java.util.Locale.getDefault());
        birthdate = dateFormat.format(date);

        Log.i("Puujedb", "username " + username + " password " + password + " age " + age);
        Log.i("Puujedb", "gender " + gender + " number " + number + " birthdate " + birthdate);

        if(age.length() == 0 || gender.length() == 0 || number.length() == 0 ) {
            Toast.makeText(this, "Please insert values", Toast.LENGTH_SHORT).show();
        }
        else {
            if (password.equals(confirmpass)) {
                boolean result = myDb.addData(username, password, age, gender, number, birthdate);
                if (result) {
                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                    etrepass.setText("");
                    etage.setText("");
                    etgender.setText("");
                    etnumber.setText("");
                    onBackPressed();
                }
                else {
                    Toast.makeText(this, "Not added", Toast.LENGTH_SHORT).show();
                    etrepass.setText("");
                    etage.setText("");
                    etgender.setText("");
                    etnumber.setText("");
                }
            }
            else {
                Toast.makeText(this, "Password confirmation is incorrect.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
