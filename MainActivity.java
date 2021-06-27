/*********************************************************************
 FILE:    Trivia Quiz
 PROGRAMMER: Bryan Butz
 LAST UPDATE: 7/8/2020

 FUNCTION:   This app retrieves data from the openTDB API and stores it
             as an array of Question objects. It then displays the
             questions and sets the button texts as possible answers. If
             the user answers correctly, they gain a point. They ans-
             wer questions until all questions are attempted and at that
             point the user can either retrieve new questions through
             the SETTINGS option or hit START again to try with the same
             subject.
 *********************************************************************/

package com.example.triviajson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    public List<Question> questionList = new ArrayList<>();
    private QuestionArrayAdapter questionArrayAdapter;
    private Button answerButton1, answerButton2, answerButton3, answerButton4;
    private TextView questionTV, scoreTV;
    private Button b;
    private int score = 0;
    private int questNum = 0;
    final int min = 0;
    final int boolMax = 1;
    final int max = 3;
    public static boolean initial = true;
    int random = 0;
    static public String urlString = "https://opentdb.com/api.php?amount=2";



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        questionArrayAdapter = new QuestionArrayAdapter(this,questionList);

        //Connect text views and buttons
        questionTV = findViewById(R.id.questionTextView);
        answerButton1 = findViewById(R.id.ansButton1);
        answerButton2 = findViewById(R.id.ansButton2);
        answerButton3 = findViewById(R.id.ansButton3);
        answerButton4 = findViewById(R.id.ansButton4);
        scoreTV = findViewById(R.id.scoreTextView);

        // Deactivate buttons until JSON is downloaded
        answerButton1.setEnabled(false);
        answerButton2.setEnabled(false);
        answerButton3.setEnabled(false);
        answerButton4.setEnabled(false);



        //Go to the SettingsActivity
        if( initial )
        {
            //Go to the SettingsActivity
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            initial = false;
        }

    }//end onCreate



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {

            Intent insertIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(insertIntent);

            return true;
        }
        else if (id == R.id.action_about)
        {

            Intent deleteIntent = new Intent(MainActivity.this, AboutAppActivity.class);
            startActivity(deleteIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void testAnswer( View view )
    {
        b = (Button)view;
        String testButton = b.getText().toString();
        String correctAns = Html.fromHtml(questionList.get(questNum).correct_answer).toString();

        Log.d("BUTTON TEST: ", testButton);
        Log.d("CORRECT BUTTON ANS: ", correctAns );

        if( testButton.equals(correctAns)  )
        {
            score++;
            scoreTV.setText(Integer.toString(score));

            b.setBackgroundColor(Color.GREEN);

            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(100); //To manage the blinking time
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(4);
            b.startAnimation(anim);


            b.setBackgroundColor(Color.WHITE);

        }


        questNum++;


        //Set next question or finish by displaying final score
        if( questNum < questionList.size() )
            setQuestion(questNum);
        else
         {
            Toast.makeText(MainActivity.this, "Final Score: " + score, Toast.LENGTH_SHORT).show();

            //disable buttons after last question
             answerButton1.setEnabled(false);
             answerButton2.setEnabled(false);
             answerButton3.setEnabled(false);
             answerButton4.setEnabled(false);
         }

    }//end testAnswer




    //After randomizing a number this then choosing one of four
    //scenarios for choosing which answer goes to which button
    public void setQuestion( int sub )
    {
        String incorAnswer1 = "", incorAnswer2 = "",  incorAnswer3 = "";
        String type = Html.fromHtml(questionList.get(sub).type).toString();

        //Convert HTML JSON data to standard strings
        String corrAnswer = Html.fromHtml(questionList.get(sub).correct_answer).toString();


        try
        {
            if( type.equals("multiple"))
            {
                incorAnswer1 = Html.fromHtml(questionList.get(sub).incorrect_answers.getString(0)).toString();
                incorAnswer2 = Html.fromHtml(questionList.get(sub).incorrect_answers.getString(1)).toString();
                incorAnswer3 = Html.fromHtml(questionList.get(sub).incorrect_answers.getString(2)).toString();
            }
            else if (type.equals("boolean"))
            {
                incorAnswer1 = Html.fromHtml(questionList.get(sub).incorrect_answers.getString(0)).toString();
            }
        }
        catch( JSONException e )
        {
            e.printStackTrace();
        }


        //Set question TV
        questionTV.setText(Html.fromHtml(questionList.get(sub).QuestionTxt));


        if( type.equals("multiple") )
        {
            //Generate random number between 0 to 3
            random = new Random().nextInt((max - min) + 1) + min;


            //Set buttons at random placement
            switch (random) {
                case 0:
                    answerButton1.setText(corrAnswer);

                    answerButton2.setText(incorAnswer1);
                    answerButton3.setText(incorAnswer2);
                    answerButton4.setText(incorAnswer3);
                    break;


                case 1:
                    answerButton2.setText(corrAnswer);

                    answerButton1.setText(incorAnswer1);
                    answerButton3.setText(incorAnswer2);
                    answerButton4.setText(incorAnswer3);
                    break;

                case 2:
                    answerButton3.setText(corrAnswer);

                    answerButton2.setText(incorAnswer1);
                    answerButton1.setText(incorAnswer2);
                    answerButton4.setText(incorAnswer3);
                    break;

                case 3:
                    answerButton4.setText(corrAnswer);

                    answerButton2.setText(incorAnswer1);
                    answerButton3.setText(incorAnswer2);
                    answerButton1.setText(incorAnswer3);
                    break;
            }
        }
        else if( type.equals("boolean"))
        {


            //Generate random number between 0 to 3
            random = new Random().nextInt((boolMax - min) + 1) + min;


            //Set buttons at random placement
            switch (random)
            {
                case 0:
                    answerButton1.setText(corrAnswer);
                    answerButton2.setText(incorAnswer1);

                    answerButton3.setText(incorAnswer2);
                    answerButton4.setText(incorAnswer3);
                    break;


                case 1:
                    answerButton1.setText(incorAnswer1);
                    answerButton2.setText(corrAnswer);

                    answerButton3.setText(incorAnswer2);
                    answerButton4.setText(incorAnswer3);
                    break;
            }
        }

    }//end SetQuestion



    public void getData(View view)
    {
        try
        {
            URL url = new URL(urlString);

            //Create asyncTask
            QuestionTask questionTask = new QuestionTask();

            //start the asyncTask
            questionTask.execute(url);
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }

    }//end getData



    //method to convert JSON object into arrayList
    private void convertJSON ( JSONObject questions )
    {
        //clear the list
        questionList.clear();

        try
        {
            JSONArray list = questions.getJSONArray("results");

            //loop to parse the JSON array into individual states
            for(int i = 0; i < list.length(); i++)
            {
                //retrieve a single state object from the array
                JSONObject questionObj = list.getJSONObject(i);

                //add the state to the list
                questionList.add( new Question( questionObj.getString("question"), questionObj.getString("difficulty"),
                        questionObj.getString("type"),questionObj.getString("correct_answer") , questionObj.getJSONArray("incorrect_answers")) );



                Log.d("QUESTION: ", questionObj.getString("question"));
                Log.d("DIFFICULTY: ", questionObj.getString("difficulty"));
                Log.d("TYPE: ", questionObj.getString("type"));
                Log.d("CORRECT_ANS: ", questionObj.getString("correct_answer"));
                Log.d("INCORRECT_ANS: ", questionObj.getJSONArray("incorrect_answers").toString());

            }//end for loop
        }
        catch( JSONException jse )
        {
            jse.printStackTrace();
        }

    }//end convertJSON




    //inner asyncTask class
    public class QuestionTask extends AsyncTask<URL, String, JSONObject>
    {

        @Override
        protected JSONObject doInBackground(URL... urls)
        {
            Log.d("URL: ", urls[0].toString());

            HttpURLConnection connection = null;

            try
            {
                //try to connect to the specified url
                connection = (HttpURLConnection)urls[0].openConnection();

                //connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");

                Log.d("CONNECTION: ", connection.toString() );

                //check if the connection was successful
                int responseCode = connection.getResponseCode();

                Log.d("RESPONSE CODE:", Integer.toString(responseCode) );

                if( responseCode == HttpURLConnection.HTTP_OK )
                {
                    //Read the information form the web page - line by line
                    StringBuilder builder = new StringBuilder();

                    try
                    {
                        BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()));

                        String line;
                        while( (line = reader.readLine()) != null )
                        {
                            builder.append(line);
                        }
                    }
                    catch( IOException ioe)
                    {
                        publishProgress("Connection Error: input");
                        ioe.printStackTrace();
                    }


                    //create and return the JSON object
                    return new JSONObject(builder.toString());
                }
                else
                {
                    publishProgress("Connection Error: bad URL");
                }
            }
            catch( Exception e )
            {
                publishProgress("Connection Error: bad URL 2");
                e.printStackTrace();
            }
            finally
            {
                connection.disconnect();
            }


            return null;
        }//end doInBackground


        @Override
        protected void onProgressUpdate(String... values)
        {
            Toast.makeText(MainActivity.this, values[0], Toast.LENGTH_SHORT).show();
        }//end onProgressUpdate


        @Override
        protected void onPostExecute(JSONObject jsonObject)
        {
            convertJSON( jsonObject );


            //Reactivate/activate Buttons
            answerButton1.setEnabled(true);
            answerButton2.setEnabled(true);
            answerButton3.setEnabled(true);
            answerButton4.setEnabled(true);


            //reset counters
            questNum = 0;
            score = 0;
            scoreTV.setText(Integer.toString(score));

            // Set up first question
            setQuestion(0);


            //set up the array adapter
            questionArrayAdapter.notifyDataSetChanged();


        }//end onPostExecute


    }//end StateTask



    @Override
    protected void onResume()
    {
        super.onResume();
    }//end onResume



}//end MainActivity


