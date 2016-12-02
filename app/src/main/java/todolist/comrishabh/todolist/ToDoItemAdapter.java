package todolist.comrishabh.todolist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by RISHABH on 12/1/2016.
 */

public class ToDoItemAdapter extends ArrayAdapter<ToDOItem> {

    int resource;
    private static String TAG=ToDoItemAdapter.class.getSimpleName();

    public ToDoItemAdapter(Context context, int resource, List<ToDOItem> objects) {
        super(context, resource, objects);
        Log.i(TAG, "ToDoItemAdapter: fired");
        this.resource=resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView: fired");
        LinearLayout todoView;
        ToDOItem currentItem=getItem(position);
        String date;
        String text=currentItem.getText();

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM");
        date=simpleDateFormat.format(currentItem.getDate());

        if(convertView==null){
            Log.i(TAG, "getView: convertview is null");
            todoView= new LinearLayout(getContext());
            String inflater=Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater=(LayoutInflater) getContext().getSystemService(inflater);
            layoutInflater.inflate(resource,todoView,Boolean.TRUE);

        } else {
            Log.i(TAG, "getView: convertView not null");
            todoView=(LinearLayout) convertView;
        }

        TextView dateview=(TextView) todoView.findViewById(R.id.rowDate);
        dateview.setText(date);

        MyTextView myTextView= (MyTextView) todoView.findViewById(R.id.row);
        myTextView.setText(text);

        return todoView;
    }

}
