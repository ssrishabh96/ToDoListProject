package todolist.comrishabh.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by RISHABH on 12/1/2016.
 */

public class ToDOItem {
    String text;
    Date date;

    public ToDOItem(String text) {
        this(text, new Date(System.currentTimeMillis()));
    }

    public ToDOItem(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public Date getDate() {

        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/mm/yyyy hh:mm");
        String datestr=simpleDateFormat.format(date);
        return date;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/mm/yyyy hh:mm");
        String datestr=simpleDateFormat.format(date);
        return "(" + datestr +") "+text;
    }
}
