package souza.cesmedlab;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {
    public final static String doctorName = "Doctor's Name";
    public final static String doctorCRM = "Doctor's CRM";
    public final static String patientName = "Patient's Name";
    public final static ArrayList<String> items = new ArrayList<>();
    private Button showList;
    ListView listHematologias, listQumicaS;
    ArrayAdapter<String> adapter;
    String[] Hematologia = {"Biometria Hemática completa","Hcto/Hb",
            "Plaquetas","Sdimentación(VSG)","Hematozoario","Retriculocitos",
            "Grupo y factor Flh"};

    String[] QuimicaSanguinea = {"Glucosa Basal","Glucosa Post. prandial","Curva de Glucosa -- Horas","Urea",
            "Cretanina", "Acido úrico","Colesterol","HDL colesterol", "LDL colesterol", "Triglicéridos",
            "Bilirrubina", "Proteinas totales","Albumina","Globulinas", "Calcio"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        showList = findViewById(R.id.ShowList);
        listHematologias = findViewById(R.id.listView_hematologia);
        listQumicaS = findViewById(R.id.listView_qumicaS);
        listCreation();
    }

    public void onConfirmation(View view){

        EditText editText = (EditText) findViewById(R.id.doctor_name);
        String dName = editText.getText().toString();

        editText = (EditText) findViewById(R.id.doctor_crm);
        String dCRM = editText.getText().toString();

        editText = (EditText) findViewById(R.id.patient_name);
        String pName = editText.getText().toString();

        //Log message
        String message = "About to create intent with " + doctorName + " " + doctorCRM + ":" + patientName;
        Log.d("Intent Creation ", message);
        //Create intent
        Intent intent = new Intent(this, ConfirmExamsActivity.class);
        intent.putExtra(doctorName, dName);
        intent.putExtra(doctorCRM,dCRM);
        intent.putExtra(patientName, pName);

        String itemSelected= "";
        for ( int i = 0 ; i < listHematologias.getCount(); i++){
            if (listHematologias.isItemChecked(i)){
                String item = (String) listHematologias.getItemAtPosition(i);
                items.add(item);
            }
        }

        intent.putExtra("exams", itemSelected);
        for ( int i = 0 ; i < listQumicaS.getCount(); i++){
            if (listQumicaS.isItemChecked(i)){
                String item = (String) listQumicaS.getItemAtPosition(i);
                items.add(item);
            }
        }
        intent.putExtra("exams", items);
        startActivity(intent);

    }
    public void listCreation(){
        
        String itemSelected = "Selected Items: \n";
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,Hematologia);
        listHematologias.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,QuimicaSanguinea);
        listQumicaS.setAdapter(adapter);

    }
}