package souza.cesmedlab;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.IpSecManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onConfirmation(View view){

        String textWrite = "Doctor Name: " + doctorName + "\n\nDoctor CRM: " + doctorCRM + "\n\nPatient Name: " +
                patientName + "\n\nRequired Exams : ";
        for(int i = 0; i < exams.size(); i++){
            textWrite += exams.get(i);
        }

        PdfDocument examsPdf = new PdfDocument();
        Paint title = new Paint();
        Paint text = new Paint();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page myPage = examsPdf.startPage(pageInfo);
        Canvas canvas = myPage.getCanvas();

        title.setTextAlign(Paint.Align.CENTER);
        title.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
        title.setTextSize(70);
        canvas.drawText("Exams",(int)1200/2,270, title);

        canvas.drawText(textWrite, 200, 350, text);

        examsPdf.finishPage(myPage);

        File file = new File(getFilePath());

        try {
            examsPdf.writeTo(new FileOutputStream(file));
            Toast toast = Toast.makeText(this, "Saved to " + getFilePath(), Toast.LENGTH_LONG);
            toast.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        examsPdf.close();
        
    }
    public void onBack(View view){
        Intent intent = new Intent(this, ExamActivity.class);
        startActivity(intent);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void open(View view){
        File file = new File(getFilePath());
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getFilePath(){
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File directory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(directory, "Exams.pdf");
        return file.getPath();
    }
}