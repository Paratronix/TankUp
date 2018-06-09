package tankup.tankup;





import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.util.ArrayList;

        /**
  * Created by User on 2/28/2017.
 */

        public class ListDataActivity extends AppCompatActivity {


            private static final String TAG = "ListDataActivity";

            DatabaseHelper mDatabaseHelper;                                                         //declare variables
            private ListView mListView;                                                             //declare variables

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.list_layout);                                               //get view from list_layout
                mListView = (ListView) findViewById(R.id.listView);                                 //reference to mListView
                mDatabaseHelper = new DatabaseHelper(this);

                populateListView();
            }

            private void populateListView() {
                Log.d(TAG, "populateListView: Displaying data in the ListView.");              // Debug


                Cursor cr = mDatabaseHelper.getData();
                ArrayList<String> listData = new ArrayList<>();                                     //create new ArrayList listData
                while (cr.moveToNext()) {
                                                                                                    //get the value from the database in column 1
                                                                                                    //then add it to the ArrayList
                    listData.add(cr.getString(0));
                }

                ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);           //create the list adapter and set the adapter
                mListView.setAdapter(adapter);

            }

        }

