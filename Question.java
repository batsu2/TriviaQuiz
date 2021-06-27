/*********************************************************************
 FILE:    Question
 PROGRAMMER: Bryan Butz
 LOGON ID:   z1836033
 DUE DATE:   3/06/2020

 FUNCTION:   This class functions as a Question object for the JSON
             data to be inserted into. It holds the question text,
             difficulty, and correct/incorrect answers.
 *********************************************************************/

package com.example.triviajson;

import org.json.JSONArray;

public class Question
{
    public final String QuestionTxt;
    public final String difficulty;
    public final String type;
    public final String correct_answer;
    public final JSONArray incorrect_answers;



    public Question( String initQuest, String initdif, String initType, String initCorrect, JSONArray initIncorrect )
    {
        QuestionTxt = initQuest;
        difficulty = initdif;
        type = initType;
        correct_answer = initCorrect;
        incorrect_answers = initIncorrect;

    }//end constructor

}//end Question class
