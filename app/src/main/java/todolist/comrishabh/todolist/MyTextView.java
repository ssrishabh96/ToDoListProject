package todolist.comrishabh.todolist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

/**
 * Created by RISHABH on 12/1/2016.
 */

public class MyTextView extends TextView {

    private static String TAG=MyTextView.class.getSimpleName();
    private Paint marginPaint;
    private Paint linePaint;
    private int papercolor;
    private float margin;

    public MyTextView(Context context) {
        super(context);
        Log.i(TAG, "MyTextView: fired");
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "MyTextView: fired with context and AttribiteSet");
        init();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "MyTextView: fires with all 3 arguements");
        init();
    }

    private void init(){
        Log.i(TAG, "init: fired");

        Resources resources=getResources();

        marginPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(resources.getColor(R.color.notepad_margin));

        linePaint= new Paint(marginPaint);
        linePaint.setColor(resources.getColor(R.color.notepad_lined));

        papercolor= resources.getColor(R.color.notepad_paper);
        margin=resources.getDimension(R.dimen.notepad_margin);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw: fired");

        // set paper background
        canvas.drawColor(papercolor);

        // draw ruled line
        canvas.drawLine(0,0,0, getMeasuredHeight(), linePaint);
        canvas.drawLine(0,getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(),linePaint);

        // draw Margin
        canvas.drawLine(margin,0,margin,getMeasuredHeight(), marginPaint);

        canvas.save();
        canvas.translate(margin,0);

     // Draw things on canvas under the text
        super.onDraw(canvas);
        canvas.restore();
     // Draw things on the canvas over the text
        }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG, "onKeyDown: fired");

        return super.onKeyDown(keyCode, event);
    }
}
