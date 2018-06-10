package tankup.tankup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

        /**
  * Created by User on 2/28/2017.
  */


        public class DatabaseHelper extends SQLiteOpenHelper {

            //Database name
            private static final String DATABASE_NAME = "tankup.db";

            //fields  for table 1 (name)
            private static final String TABLE_NAME = "people_table";
            private static final String COL_ID = "ID";
            private static final String COL_NAME = "name";
            private static final String COL_PUNISH = "punish";

            //fields for table 2 (task)
            private static final String TABLE_TASK = "challenge_table";
            private static final String Task_ID =   "T_ID";
            private static final String COL_TASK = "task";

            //fields for table 3 (question)
            private static final String Table_Quiz = "quiz_table";
            private static final String Quiz_ID =  "Q_ID";
            private static final String COL_Question ="question";
            private static final String COL_Opt1 = "option1";
            private static final String COL_Opt2 = "option2";
            private static final String COL_Opt3 = "option3";
            private static final String COL_Opt4 = "option4";



           private SQLiteDatabase db;

            public DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, 2);
            }


            //create table 1 (Name), table 2 (Task) an table 3 (Quiz)
            @Override
            public void onCreate(SQLiteDatabase db) {

                this.db=db;

                String createTable1 = "CREATE TABLE " + TABLE_NAME + "("
                        + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COL_NAME +" TEXT,"
                        + COL_PUNISH + " TEXT" + ")";


                String createTable2 = "CREATE TABLE " + TABLE_TASK + "("
                        + Task_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COL_TASK + " TEXT" + ")";



                String createTable3 = "CREATE TABLE " + Table_Quiz + "("
                        + Quiz_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COL_Question +" TEXT,"
                        + COL_Opt1 +" TEXT,"
                        + COL_Opt2 +" TEXT,"
                        + COL_Opt3 +" TEXT,"
                        + COL_Opt4 +" TEXT" + ")";



                db.execSQL(createTable1);
                db.execSQL(createTable2);
                db.execSQL(createTable3);
                fillQuizTable();
                fillTaskTable();

            }


            //when the database is updated, table get dropped and recreated
            @Override
            public void onUpgrade(SQLiteDatabase db, int i, int i1) {
                db.execSQL("drop table if exists " + TABLE_NAME);
                db.execSQL("drop table if exists " + TABLE_TASK);
                db.execSQL("drop table if exists " + Table_Quiz);
                onCreate(db);
            }






            public boolean addData(String newEntry1, String newEntry2) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();                                  //creates instance of ContentValues class to store  values
                contentValues.put(COL_NAME, newEntry1);                                             // put(column,value)
                contentValues.put(COL_PUNISH, newEntry2);                                           // put(column,value)

                long result= db.insert(TABLE_NAME, null, contentValues);

                //if date as inserted incorrectly it will return -1
               if (result == -1) {
                   return false;
               }else{
                   return true;
               }
            }


            // get Data for ListView
            public Cursor getData(){
                SQLiteDatabase db = this.getWritableDatabase();
                String query = "SELECT " + COL_NAME + " FROM " + TABLE_NAME;
                Cursor cr = db.rawQuery(query, null);                                  // create Cursor in order to parse our  results
                 return cr;
             }






             //get names for Quiz
             public ArrayList<String> GetNames()
            {
                ArrayList<String> list = new ArrayList<>();
                SQLiteDatabase db = this.getWritableDatabase();
                String query = "SELECT " + COL_NAME + " FROM " + TABLE_NAME;
                Cursor cursor = db.rawQuery(query, null);

                if (cursor.moveToFirst())
                {
                    do
                    {
                        list.add(cursor.getString(cursor.getColumnIndex(COL_NAME)));
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
                return list;
            }





            //get punishments for quiz
            public ArrayList<String> GetBestrafungen()
            {
                ArrayList<String> list = new ArrayList<>();
                SQLiteDatabase db = this.getWritableDatabase();
                String query = "SELECT " + COL_PUNISH + " FROM " + TABLE_NAME;
                Cursor cursor = db.rawQuery(query, null);

                if (cursor.moveToFirst())
                {
                    do
                    {
                        list.add(cursor.getString(cursor.getColumnIndex(COL_PUNISH)));
                    }
                    while (cursor.moveToNext());
                }
                cursor.close();
                return list;
            }








            private void fillTaskTable(){
                Task t1 = new Task("Mache 12 Liegestützen");
                addTask(t1);
                Task t2 = new Task("Zähle 5 verschiede Pokemons auf");
                addTask(t2);
                Task t3 = new Task("Stelle einen Beruf deiner Wahl in Pantomime da");
                addTask(t3);

            }

            private void addTask (Task task){
                ContentValues cv = new ContentValues();
                cv.put(COL_TASK,task.getTask());
                db.insert(TABLE_TASK,null,cv);
            }


            public List<Task> getAllTasks() {
                List<Task> taskList = new ArrayList<>();
                db = getReadableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TASK, null);

                if (c.moveToFirst()) {
                    do {
                        Task task = new Task();
                        task.setTask(c.getString(c.getColumnIndex(COL_TASK)));
                       taskList.add(task);
                    } while (c.moveToNext());
                }

                c.close();
                return taskList;
            }








            private void fillQuizTable(){
               Question q1 = new Question("Wo ist die WM 2018", "Russland", "Thailand", "Argentinien", "Ukraine");
               addQuestion(q1);
               Question q2 = new Question("Was ist die Hauptstadt von Litauen", "Riga", "Minsk", "Belgrad", "Talinn");
               addQuestion(q2);
               Question q3 = new Question("Wie viele Bundesländer hat die BRD", "16", "15", "17", "13");
               addQuestion(q3);
               Question q4 = new Question("Wer ist kein Formel 1 Fahrer", "Glock", "Ocon", "Vettel", "Bottas");
               addQuestion(q4);
               Question q5 = new Question("was ist kein NFL Team", "Unicorns", "Raiders", "Steelers", "Eagels");
               addQuestion(q5);
               Question q6 = new Question("Wer hat die meisten Abos auf Youtube", "PewDiePie","Gronkh","Ninja","TutiEgG");
               addQuestion(q6);
            }

            private void addQuestion(Question question) {
                ContentValues cv = new ContentValues();
                cv.put(COL_Question,question.getQuestion());
                cv.put(COL_Opt1,question.getOption1());
                cv.put(COL_Opt2,question.getOption2());
                cv.put(COL_Opt3,question.getOption3());
                cv.put(COL_Opt4,question.getOption4());
                 db.insert(Table_Quiz,null,cv);

            }


            public List<Question> getAllQuestions() {
                List<Question> questionList = new ArrayList<>();
                 db = getReadableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM " + Table_Quiz, null);

                if (c.moveToFirst()) {
                    do {
                        Question question = new Question();
                        question.setQuestion(c.getString(c.getColumnIndex(COL_Question)));
                        question.setOption1(c.getString(c.getColumnIndex(COL_Opt1)));
                        question.setOption2(c.getString(c.getColumnIndex(COL_Opt2)));
                        question.setOption3(c.getString(c.getColumnIndex(COL_Opt3)));
                        question.setOption4(c.getString(c.getColumnIndex(COL_Opt4)));
                        questionList.add(question);
                    } while (c.moveToNext());
                }

                c.close();
                return questionList;
            }
        }








