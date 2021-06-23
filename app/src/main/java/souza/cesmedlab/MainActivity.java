package souza.cesmedlab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onExamsForm(View view){
        Intent intent = new Intent(this, ExamActivity.class);
        startActivity(intent);
    }
    public void onResultForm(View view){
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
    }
}