/*********************************************************************
 FILE:    SettingsActivity
 PROGRAMMER: Bryan Butz
 LOGON ID:   z1836033
 DUE DATE:   3/06/2020

 FUNCTION:   This holds the functions to generate the URL needed for the
             openTDB API. It gives the user the option to chose the
             difficulty level and the topic for their set of questions.
             After hitting the button, the URL is generated and awaits
             the user to press START in the main screen.
 *********************************************************************/

package com.example.triviajson;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class SettingsActivity extends MainActivity
{
    private RadioGroup difficultyRG, typeRG;
    private Spinner topicSpinner;
    public String baseString = "https://opentdb.com/api.php?amount=10";
    public String diffString = "";
    public String topicString = "";
    public String typeString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        difficultyRG = findViewById(R.id.difficultyRG);
        difficultyRG.setOnCheckedChangeListener( difListener );

        typeRG = findViewById(R.id.typeRG);
        typeRG.setOnCheckedChangeListener(typeListener);


        //Spinner populated by data from strings.xml file
        topicSpinner = findViewById(R.id.infoSpinner);

        //create the array adapter with the information
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.spinnerArray, R.layout.spinner_view );


        topicSpinner.setAdapter(adapter1);

        topicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selection1;

                selection1 = parent.getItemAtPosition( position ).toString();

                topicString = "&category=" + convertTopic(selection1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });



    }//end onCreate



    public void buildURL(View view)
    {
        urlString = baseString + typeString + topicString + diffString;


        Log.d("NEW_URL_STRING: ", urlString);

        finish();
    }



    private int convertTopic( String topic )
    {
        switch ( topic )
        {
            case "General Knowledge" : return 9;
            case "Books" : return 10;
            case "Film" : return 11;
            case "Music" : return 12;
            case "Anime/Manga" : return 31;
            case "Television" : return 14;
            case "Video Games" : return 15;
            case "Board Games" : return 16;
            case "Science and Nature" : return 17;
            case "Computers" : return 18;
            case "Mathematics" : return 19;
            case "Mythology" : return 20;
            case "Sports" : return 21;
            case "Geography" : return 22;
            case "History" : return 23;
            case "Politics" : return 24;
            case "Art" : return 25;
            case "Celebrities" : return 26;
            case "Animals" : return 27;
            case "Vehicles" : return 28;
            case "Comics" : return 29;
            case "Gadgets" : return 30;
            case "Theatre" : return 13;
            case "Animation" : return 32;

            default : return 0;
        }

    }


    private RadioGroup.OnCheckedChangeListener difListener = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            switch( checkedId )
            {
                case R.id.difButton1: diffString = "&difficulty=easy";
                    break;

                case R.id.difButton2: diffString = "&difficulty=medium";
                    break;

                case R.id.difButton3: diffString = "&difficulty=hard";
                    break;
            }

        }
    };




    private RadioGroup.OnCheckedChangeListener typeListener = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            switch( checkedId )
            {
                case R.id.typeButton1: typeString = "&type=multiple";
                    break;

                case R.id.typeButton2: typeString = "&type=boolean";
                    break;
            }

        }
    };



    //Back Button
    public void goBack( View view )
    { finish(); }//end goBack


}//end SettingsActivity
