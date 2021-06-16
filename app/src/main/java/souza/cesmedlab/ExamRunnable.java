package souza.cesmedlab;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExamRunnable implements Runnable{

    String doctorName;
    String doctorCRM;
    String patientName;
    ArrayList<String> exams;
    Activity activity;

    public ExamRunnable(String doctorName, String doctorCRM, String patientName, ArrayList<String> exams, Activity activity) {
        this.doctorName = doctorName;
        this.doctorCRM = doctorCRM;
        this.patientName = patientName;
        this.exams = new ArrayList<>(exams);
        this.activity = activity;
    }
    @Override
    public void run() {

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // This is the code that will run on the UI thread.
                Toast toast = Toast.makeText(activity, "PDF generated and sent ", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

}
