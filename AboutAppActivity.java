/*********************************************************************
 FILE:    AboutAppActivity
 PROGRAMMER: Bryan Butz
 LOGON ID:   z1836033
 DUE DATE:   3/06/2020

 FUNCTION:   This informs the user in the usage of the app as well as
             who programmed the app.
 *********************************************************************/

package com.example.triviajson;


import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class AboutAppActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }//end onCreate


    //Back Button
    public void goBack( View view )
    { finish(); }//end goBack

}//end AboutAppActivity
