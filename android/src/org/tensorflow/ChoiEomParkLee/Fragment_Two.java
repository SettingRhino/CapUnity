package org.tensorflow.ChoiEomParkLee;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.tensorflow.demo.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Fragment_Two extends Fragment {
    TextView titleTextView;
    TextView commentTextView;
    TextView tellTextView;
    ArrayList<TextView> facilityTextViews=new ArrayList<TextView>();
    ValueEventListener postListener;
    DatabaseReference ref;
    View.OnClickListener tellClick;
    View view;
    String keyword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment2, container, false);
        //Bundle값 받아오고
        keyword=getArguments().getString("keyword");
        Log.e(TAG,"dododo"+keyword);
        ref= FirebaseDatabase.getInstance().getReference();
        //뷰들 초기화.
        titleTextView = (TextView)view.findViewById(R.id.fragment2_title);
        tellTextView = (TextView)view.findViewById(R.id.fragment2_tell);
        commentTextView=view.findViewById(R.id.fragment2_comment);
        facilityTextViews.add((TextView) view.findViewById(R.id.fragment2_facility1));
        facilityTextViews.add((TextView) view.findViewById(R.id.fragment2_facility2));
        facilityTextViews.add((TextView) view.findViewById(R.id.fragment2_facility3));
        facilityTextViews.add((TextView) view.findViewById(R.id.fragment2_facility4));
        facilityTextViews.add((TextView) view.findViewById(R.id.fragment2_facility5));
        facilityTextViews.add((TextView) view.findViewById(R.id.fragment2_facility6));
        tellClick=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("전화하시겠습니까?").setMessage(tellTextView.getText().toString());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String telnumber=tellTextView.getText().toString();
                        Intent tt = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telnumber));
                        startActivity(tt);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        };
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot a:dataSnapshot.getChildren()){
                    if(a.getKey().equals(keyword)){
                        //여기서 뷰에 값을 줘야함
                        LocationDataObject lo= a.getValue(LocationDataObject.class);
                        titleTextView.setText(lo.name);
                        commentTextView.setText(lo.comment);
                        tellTextView.setText(lo.tel);
                        if(!lo.tel.equals(""))
                        {
                            tellTextView.setOnClickListener(tellClick);
                        }
                        for(int i=0;i< lo.facilities.size();i++){
                            facilityTextViews.get(i).setText(lo.facilities.get(i));
                            //facilityTextViews.get(i).setOnClickListener(facilityClick);
                        }
                    }
                }
                //  Log.e(TAG,"dododo"+lo.toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //   Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        ref.addListenerForSingleValueEvent(postListener);
        return view;
    }

}
