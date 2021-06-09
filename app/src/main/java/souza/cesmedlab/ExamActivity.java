package souza.cesmedlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
    }

    public void onConfirmation(View view){
        EditText doctorName = (EditText) findViewById(R.id.doctor_name);
        EditText doctorCRM = (EditText) findViewById(R.id.doctor_crm);
        EditText patientName = (EditText) findViewById(R.id.patient_name);

    }
}