package org.tensorflow.ChoiEomParkLee;

import android.app.Fragment;
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

import java.util.Map;

import static android.content.ContentValues.TAG;

public class Fragment_Three extends Fragment {
    View view;
    String keyword;
    StorageReference storageRef;
    Map<String,Object> map;
    ValueEventListener postListener;
    DatabaseReference ref;
    TextView titleTextView;
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment3, container, false);
        keyword=getArguments().getString("keyword");
        ref= FirebaseDatabase.getInstance().getReference();
        titleTextView =view.findViewById(R.id.fragment3_title);
        imageView=view.findViewById(R.id.fragment3_image);
        Log.e(TAG,"dododo"+keyword+"map.jpg");
        storageRef = FirebaseStorage.getInstance().getReference().child("mapimages").child(keyword+"map.jpg");
        Glide.with(view.getContext()).load(storageRef).into(imageView);
        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //여기서 뷰에 값을 줘야함
                for(DataSnapshot a:dataSnapshot.getChildren()){
                    //keyword="tamgu"
                    Log.e(TAG,"dododo"+a.getKey());
                    if(a.getKey().equals("mapping")){
                        map = (Map<String, Object>)a.getValue();

                    }
                    if(a.getKey().equals(keyword)){
                        LocationDataObject lo= a.getValue(LocationDataObject.class);
                        titleTextView.setText(lo.name);
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
