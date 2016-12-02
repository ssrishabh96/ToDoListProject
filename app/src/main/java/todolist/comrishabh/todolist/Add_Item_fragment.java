package todolist.comrishabh.todolist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by RISHABH on 12/1/2016.
 */

public class Add_Item_fragment extends Fragment {


    private static final String TAG=Add_Item_fragment.class.getSimpleName();
    private  OnNewItemAddListener onNewItemAddListener;



    public interface OnNewItemAddListener{
        public void onNewItemAdded(String newItem);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach: fired");

        try{
            onNewItemAddListener= (OnNewItemAddListener) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+" activity must implement the OnNewItemAdded Listener");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: fired");
        View view= inflater.inflate(R.layout.additem_fragment, container, false);

        final EditText editText=(EditText) view.findViewById(R.id.fragment_editText);
        editText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                Log.i(TAG, "onKey: fired" +keyEvent.getKeyCode());

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_ENTER) {
                        Log.i(TAG, "onKey: adding the data");
                        String newItem = editText.getText().toString();
                        onNewItemAddListener.onNewItemAdded(newItem);
                        editText.setText("");
                        return true;
                    }
                }

                Log.i(TAG, "onKey: Failed!! adding the data");
                return false;
            }

        });


        return view;
    }
}
