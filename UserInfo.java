package mn.edu.num.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfo extends AppCompatActivity {

    Button btSave, btCancel, btChange;
    DatePicker dob;
    EditText etage, etgender, etnumber;
    DatabaseHelper myDb;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        etage = findViewById(R.id.etage);
        etgender = findViewById(R.id.etgender);
        etnumber = findViewById(R.id.etnumber);
        dob = findViewById(R.id.dob);
        btSave = findViewById(R.id.btSave);
        btCancel = findViewById(R.id.btCancel);
        btChange = findViewById(R.id.btChange);
        myDb = new DatabaseHelper(this);

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent i = getIntent();
        username = i.getStringExtra("username");

        Cursor userInfo = myDb.getData(username);
        userInfo.moveToFirst();

        String birthdate = userInfo.getString(userInfo.getColumnIndex(DatabaseHelper.COL7));
        String[] arr = birthdate.split("\\/");
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);
        dob.updateDate(year, month-1, day);

        etage.setText(userInfo.getString(userInfo.getColumnIndex(DatabaseHelper.COL4)));
        etgender.setText(userInfo.getString(userInfo.getColumnIndex(DatabaseHelper.COL5)));
        etnumber.setText(userInfo.getString(userInfo.getColumnIndex(DatabaseHelper.COL6)));


        if (!userInfo.isClosed())
            userInfo.close();

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfo.this, ChangePassword.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    public void updateData() {
        String age = etage.getText().toString();
        String gender = etgender.getText().toString();
        String number = etnumber.getText().toString();
        long dateTime = dob.getCalendarView().getDate();
        Date date = new Date(dateTime);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", java.util.Locale.getDefault());
        String birthdate = dateFormat.format(date);

        boolean result = myDb.updateData(username, age, gender, number, birthdate);
        if (result) {
            Toast.makeText(this, "Data updated successfully.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Data not updated.", Toast.LENGTH_SHORT).show();
        }
    }
}
