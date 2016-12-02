package todolist.comrishabh.todolist;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import todolist.comrishabh.todolist.CompassView.CompassActivity;

public class MainActivity extends AppCompatActivity implements Add_Item_fragment.OnNewItemAddListener {

    private static final String TAG=MainActivity.class.getSimpleName();
    ArrayList<ToDOItem> myListItems;
    private ToDoItemAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: fired");

        FragmentManager fm = getSupportFragmentManager();
        MyListViewfragment myListViewfragment=(MyListViewfragment) fm.findFragmentById(R.id.MainActivitymyListViewFragment);
        myListItems = new ArrayList<ToDOItem>();
        populateMyItems();
        myListAdapter = new ToDoItemAdapter(this, R.layout.todo_listitem, myListItems);

        myListViewfragment.setListAdapter(myListAdapter);



    }

    private void populateMyItems() {
        Log.i(TAG, "populateMyItems: fired");
        for (int i = 0; i < 10; i++) {
            myListItems.add(0, new ToDOItem("Hola :) "+i ));
        }
    }



    @Override
    public void onNewItemAdded(String newItem) {
        Log.i(TAG, "onNewItemAdded: fired");
        ToDOItem temp=new ToDOItem(newItem);
        myListItems.add(0,temp);
        myListAdapter.notifyDataSetChanged();

    }

    public void nextActivity(View view) {
        Intent intent= new Intent(this, CompassActivity.class);
        startActivity(intent);
    }

}
