package com.example.androidu.noteplacer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private RectanglePlacerView npv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        npv = (RectanglePlacerView)findViewById(R.id.note_placer_view);
        npv.setNumRows(5);
        npv.setNumColumns(5);
        npv.setDragRectColor(0xFFFF0000);
        npv.setNoteDragListener(new RectanglePlacerView.RectangleDragListener(){
            @Override
            public void onRectangleDrag(int rect1X, int rect1Y, int rect2X, int rect2Y){
                Log.d("MainActivity", "Rectangle Dragged: " + rect1X + ", " + rect1Y + ", " + rect2X + ", " + rect2Y);
            }
        });
    }
}
