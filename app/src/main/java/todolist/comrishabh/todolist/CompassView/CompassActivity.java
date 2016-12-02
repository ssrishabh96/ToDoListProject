package todolist.comrishabh.todolist.CompassView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import todolist.comrishabh.todolist.R;

public class CompassActivity extends AppCompatActivity {
    MyCompassView myCompassView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        myCompassView=(MyCompassView) this.findViewById(R.id.myCompass);
        myCompassView.setBearing(45);
    }

    public void rotateMore(View view) {
        int bearing= 15 +(int) myCompassView.getBearing();
        myCompassView.setBearing((float) bearing);

    }
}
