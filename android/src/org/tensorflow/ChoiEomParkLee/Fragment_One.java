package org.tensorflow.ChoiEomParkLee;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.tensorflow.demo.R;

import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Fragment_One extends Fragment {
    TextView titleTextView;
    ArrayList<TextView> hashTagTextViews=new ArrayList<TextView>();
    ImageView imageView;
    StorageReference storageRef;
    ValueEventListener postListener;
    DatabaseReference ref;
    View.OnClickListener hashClick;
    View view;
    String keyword;
    Map<String,Object> map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment1, container, false);
        //Bundle값 받아오고
        keyword=getArguments().getString("keyword");
        //
        ref= FirebaseDatabase.getInstance().getReference();//.child(keyword);
        //뷰들 초기화.
        titleTextView =view.findViewById(R.id.fragment1_title);
        hashTagTextViews.add((TextView) view.findViewById(R.id.fragment1_hashtag1));
        hashTagTextViews.add((TextView) view.findViewById(R.id.fragment1_hashtag2));
        hashTagTextViews.add((TextView) view.findViewById(R.id.fragment1_hashtag3));
        hashTagTextViews.add((TextView) view.findViewById(R.id.fragment1_hashtag4));
        hashTagTextViews.add((TextView) view.findViewById(R.id.fragment1_hashtag5));
        hashTagTextViews.add((TextView) view.findViewById(R.id.fragment1_hashtag6));
        imageView = view.findViewById(R.id.fragment1_image);
        //리스너 달아주고
        hashClick=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(TextView tx:hashTagTextViews){
                    if(tx.getId()== v.getId()){
                        String tr=tx.getText().toString().replace("#","");
                        Intent intent = new Intent(view.getContext(), InfoActivity.class);
                        intent.putExtra("title",(String) map.get(tr));
                        startActivity(intent);
                    }
                }
            }
        };
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //여기서 뷰에 값을 줘야함
                for(DataSnapshot a:dataSnapshot.getChildren()){
                    //keyword="tamgu"
                    Log.e(TAG,"dododo"+a.getKey());
                    if(a.getKey().equals("mapping")){
                        map = (Map<String, Object>)a.getValue();
                        Log.e(TAG,"dododo"+a.getKey());
                    }
                    if(a.getKey().equals(keyword)){
                        LocationDataObject lo= a.getValue(LocationDataObject.class);
                        titleTextView.setText(lo.name);
                        for(int i=0;i< lo.hashtag.size();i++){
                            hashTagTextViews.get(i).setText("#"+lo.hashtag.get(i));
                            hashTagTextViews.get(i).setOnClickListener(hashClick);
                        }
                        storageRef = FirebaseStorage.getInstance().getReference().child("images").child(lo.image);
                        Glide.with(view.getContext()).load(storageRef).into(imageView);
                    }
                }
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
