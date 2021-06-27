/*********************************************************************
 FILE:    QuestionArrayAdapter
 PROGRAMMER: Bryan Butz
 LAST UPDATE: 3/6/2020

 FUNCTION:   This functions as an array adapter for an array of question
             objects.
 *********************************************************************/

package com.example.triviajson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;

public class QuestionArrayAdapter extends ArrayAdapter<Question>
{

    //context is the activity where the ListView is displayed
    //states is the list of data to be displayed


    public QuestionArrayAdapter(Context context, List<Question> states)
    {
        //call the super class constructor. The -1 indicates that
        //a custom layout is being used
        super(context, -1, states);

    }//end constructor


    //The getView method is called to get the View that displays
    //a ListView item's data. Must be overridden for the custom layout
    //
    //position is the ListView item's position
    //convertView is the View representing the ListView item
    //parent is the parent of the ListView item


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //get the question for the specified ListView position
        Question question = getItem(position);

        //Reference to the list item's views
        ViewHolder viewHolder;


        //If there is not a reusable ViewHolder, create one
        if( convertView == null )
        {
            //Create a new ViewHolder object
            viewHolder = new ViewHolder();

            //Get a layout inflater so the custom layout can be attached
            //LayoutInflater inflater = LayoutInflater.from(getContext());

            //Attach the custom layout. false indicates
            //convertView = inflater.inflate(R.layout.list_item, parent, false);


            //Set up the connections in the new ViewHolder
            viewHolder.questionTV =
                    (TextView)convertView.findViewById(R.id.questionTextView);
            viewHolder.ansButton1 =
                    (Button)convertView.findViewById(R.id.ansButton1);
            viewHolder.ansButton2 =
                    (Button)convertView.findViewById(R.id.ansButton2);
            viewHolder.ansButton3 =
                    (Button)convertView.findViewById(R.id.ansButton3);
            viewHolder.ansButton4 =
                    (Button)convertView.findViewById(R.id.ansButton4);





            //store the ViewHolder with the ListView item
            convertView.setTag(viewHolder);
        }
        //There is a reusable ViewHolder
        else
        {
            //get the ViewHolder that was stored with the ListView item
            viewHolder = (ViewHolder)convertView.getTag();
        }


        //Return the completed ListView item to be displayed
        return convertView;
    }


    //Class for re-using views as list items scroll off and on the screen
    private static class ViewHolder
    {
        TextView questionTV;
        Button ansButton1, ansButton2, ansButton3, ansButton4;
    }//end inner ViewHolder class

} //end QuestionArrayAdapter
