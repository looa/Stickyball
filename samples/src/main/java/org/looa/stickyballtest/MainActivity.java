package org.looa.stickyballtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.looa.stickyballview.widget.DotIndicatorView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DotIndicatorView dv;
    private Button button1, button2, button3, button4, button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dv = (DotIndicatorView) findViewById(R.id.dv_sample);
        dv.setSelectedView(DotIndicatorView.STICKY_BALL);
        dv.setOnClickListener(this);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);

        button1.setTag(1);
        button2.setTag(2);
        button3.setTag(3);
        button4.setTag(4);
        button5.setTag(5);


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dv.setCurrentItem((Integer) v.getTag() - 1);
    }
}
