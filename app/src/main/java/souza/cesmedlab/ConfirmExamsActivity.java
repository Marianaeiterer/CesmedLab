package souza.cesmedlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ConfirmExamsActivity extends AppCompatActivity {
    String doctorName;
    String doctorCRM;
    String patientName;
    ArrayList<String> exams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_exams);
        Intent intent = getIntent();
        if (intent != null) { // If nothing was sent, make sure we don't try to read it
            doctorName = intent.getStringExtra("Doctor Name");     // If param not there, then it will be null
            doctorCRM = intent.getStringExtra("Doctor CRM");  // If param not there, then set it to 0
            patientName = intent.getStringExtra("Patient Name");
            Bundle bundle = getIntent().getExtras();
            exams = bundle.getStringArrayList("exams");
            ListView listView = findViewById(R.id.exams);
            ArrayAdapter<String> items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exams);
            listView.setAdapter(items);

            // Capture the layout's TextView and set the string as its text
            TextView textView = findViewById(R.id.confirmation);
            textView.setText("Doctor Name: " + doctorName + "\n\nDoctor CRM: " + doctorCRM + "\n\nPatient Name: " + patientName + "\n\nRequired Exams : ");
        } else {
            TextView textView = findViewById(R.id.confirmation);
            textView.setText("Oh, don't you have a favorite scripture? Try Ether 12:27");
        }
    }

    public void onBack(View view){
        Intent intent = new Intent(this, ExamActivity.class);
        startActivity(intent);
    }
}