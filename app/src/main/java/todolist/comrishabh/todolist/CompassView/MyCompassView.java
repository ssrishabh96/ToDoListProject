package todolist.comrishabh.todolist.CompassView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Switch;

import todolist.comrishabh.todolist.R;

/**
 * Created by RISHABH on 12/1/2016.
 */

public class MyCompassView extends View {

    private static String TAG=MyCompassView.class.getSimpleName();
    private Paint markerPaint;
    private Paint textPaint;
    private Paint circlePaint;
    private String north_String;
    private String south_String;
    private String west_String;
    private String east_String;
    private int textHeight;
    private float bearing;

    public MyCompassView(Context context) {
        super(context);
        Log.i(TAG, "MyCompassView: fired");
        initCompassView();
    }

    public MyCompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "MyCompassView: fired");
        initCompassView();
    }

    public MyCompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "MyCompassView: fired");
        initCompassView();
    }

    protected void initCompassView(){
        Log.i(TAG, "initCompassView: fired");
        Resources resources =getResources();

        markerPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        markerPaint.setColor(resources.getColor(R.color.compass_marker_color));

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(resources.getColor(R.color.text_color));

        circlePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(resources.getColor(R.color.compass_background));
        circlePaint.setStrokeWidth(1);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        north_String=resources.getString(R.string.cardinal_north);
        south_String=resources.getString(R.string.cardinal_south);
        east_String=resources.getString(R.string.cardinal_east);
        west_String=resources.getString(R.string.cardinal_west);

        textHeight=(int) textPaint.measureText("xX");


        setFocusable(Boolean.TRUE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: fired");

        int measuredheight=measure(heightMeasureSpec);
        int measuredwidth=measure(widthMeasureSpec);

        int result=Math.min(measuredwidth,measuredheight);

        setMeasuredDimension(result,result);
    }

    private int measure(int measureSpec){
        Log.i(TAG, "measure: fired");
        int result=0;

        int specMode=MeasureSpec.getMode(measureSpec);
        int specSize=MeasureSpec.getSize(measureSpec);

        if(specMode==MeasureSpec.UNSPECIFIED)
        {
            result=200;
        }
        else{
            result=specSize;
        }

        return result;
    }

    public void setBearing(float bearing) {
        Log.i(TAG, "setBearing: fired");
        this.bearing = bearing;
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {

        super.dispatchPopulateAccessibilityEvent(event);
        Log.i(TAG, "dispatchPopulateAccessibilityEvent: fired");

        if(isShown()){

            String bearingStr=String.valueOf(bearing);

            if(bearingStr.length()>AccessibilityEvent.MAX_TEXT_LENGTH)
                bearingStr=bearingStr.substring(0, AccessibilityEvent.MAX_TEXT_LENGTH);

            event.getText().add(bearingStr);
            return true;
        }


        return false;
    }

    public float getBearing() {
        Log.i(TAG, "getBearing: fired");
        return bearing;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Log.i(TAG, "onDraw: fired");

        int mMeasuredHeight=getMeasuredHeight();
        int mMeasuredWidth=getMeasuredWidth();


        int px=mMeasuredWidth/2;
        int py=mMeasuredHeight/2;

        int radius=Math.min(px,py);

        canvas.drawCircle(px,py,radius,circlePaint);

        canvas.save();
        canvas.rotate(-bearing,px,py);

        int textWIDTH= (int) textPaint.measureText("W");
        int cardinalX=px- textWIDTH/2;
        int cardinalY= py- radius+ textHeight;

        for(int i=0; i<24; i++){

            canvas.drawLine(px,py-radius,px,py-radius+10,markerPaint);
            canvas.save();
            canvas.translate(0,textHeight);

            if(i%6==0){

                String directionStr="";
                switch(i){
                    case 0:
                        directionStr=north_String;
                        int arrowY=2*textHeight;
                        canvas.drawLine(px,arrowY,px-5,3*textHeight,markerPaint);

                        canvas.drawLine(px,arrowY, px+5, 3*textHeight,markerPaint);
                        break;
                    case 6:
                        directionStr=east_String; break;
                    case 12: directionStr= south_String; break;

                    case 18: directionStr=west_String; break;

                }
                canvas.drawText(directionStr,cardinalX,cardinalY,textPaint);
            }
            else if(i%3==0){

                String angle= String.valueOf(i*15);
                float angletextWidth= textPaint.measureText(angle);
                int angleTextX= (int) ( px-angletextWidth/2);
                int angleTextY= py-radius+textHeight;

                canvas.drawText(angle,angleTextX,angleTextY,textPaint);

            }
            canvas.restore();
            canvas.rotate(15,px,py);
        }

        canvas.restore();

    }
}
