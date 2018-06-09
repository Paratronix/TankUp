package tankup.tankup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EintragActivity extends AppCompatActivity {


    ;

    DatabaseHelper mDatabaseHelper;                                                                 //declare variables
    private Button btnViewData;                                                                     //declare variables
    private Button btnAdd;                                                                          //declare variables
    private EditText editText1;                                                                     //declare variables
    private EditText editText2;                                                                     //declare variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eintrag_activity);                                                  //Get view from eintrag_activity.xml

        mDatabaseHelper = new DatabaseHelper(this);
        editText1 = (EditText) findViewById(R.id.name);                                             //reference to editText1
        editText2 = (EditText) findViewById(R.id.bestrafung);                                       //reference to editText2
        btnAdd = (Button) findViewById(R.id.butnAdd);                                               //reference to btnAdd
        btnViewData = (Button) findViewById(R.id.butnViewData);                                     //reference to btnViewData



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry1 = editText1.getText().toString();                                  //entered text in editText1 get stored in a string variable
                String newEntry2 = editText2.getText().toString();                                  //entered text in editText2 get stored in a string variable
                if (newEntry1.isEmpty() || newEntry2.isEmpty()) {                                   //if at least  one  text field is empty ,
                    toastMessage("You must put something in the text field!");                      // error message
                } else {
                    AddData(newEntry1, newEntry2);                                                  //otherwise method AddData is called
                    editText1.setText("");                                                          //and text fields are emptied again
                    editText2.setText("");                                                          //and text fields are emptied again
                }
            }

        });


        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                               //capture button clicks
                Intent intent = new Intent(EintragActivity.this, ListDataActivity.class);      //and start ListDataActivity.class
                startActivity(intent);
            }
        });


    }

    public void AddData(String newEntry1, String newEntry2){

            boolean insertData = mDatabaseHelper.addData(newEntry1, newEntry2);

            if (insertData) {                                                                       //if  insertData is true, data got successfully inserted into database
                toastMessage("Data Successfully Inserted!");

            } else {                                                                                //else insertData is false, data didnt get  inserted into Database
                toastMessage("Something went wrong");

            }
    }




    /**
     * * customizable toast
     * @param message
     */
    private void toastMessage (String message){                                                     //Method  to pass a notification in the form of a string
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}