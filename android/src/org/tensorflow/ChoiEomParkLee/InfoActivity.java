package org.tensorflow.ChoiEomParkLee;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.tensorflow.demo.R;

public class InfoActivity extends Activity {
    private FragmentManager fm;
    private Fragment_One fragment1;
    private Fragment_Two fragment2;
    private Fragment_Three fragment3;
    private FragmentTransaction fts;
    String name;
    Bundle bundle;
    Context ctx;
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.infoactivity);
        //인식값이 넘어옴
        ctx=getApplicationContext();
        Intent intent = getIntent();
        //값받아오지
        name = intent.getExtras().getString("title");

        fm=getFragmentManager();
        //인식값을 keyword로 넘겨줌
        //일단은 "상상파크"
        bundle= new Bundle(1);
        bundle.putString("keyword",name);
        fragment1 = new Fragment_One();
        fragment1.setArguments(bundle);

        fragment2 = new Fragment_Two();
        fragment2.setArguments(bundle);
        fts= fm.beginTransaction();
        fts.replace(R.id.fragment,fragment1).commitAllowingStateLoss();

    }
    public void clickHandler(View view){
        fts=fm.beginTransaction();
        switch (view.getId()){
            case R.id.fragment1:
                fragment1 = new Fragment_One();
                fragment1.setArguments(bundle);
                fts.replace(R.id.fragment,fragment1).commitAllowingStateLoss();
                break;
            case R.id.fragment2:
                fragment2 = new Fragment_Two();
                fragment2.setArguments(bundle);
                fts.replace(R.id.fragment,fragment2).commitAllowingStateLoss();
                break;
            case R.id.fragment3:
                fragment3 = new Fragment_Three();
                fragment3.setArguments(bundle);
                fts.replace(R.id.fragment,fragment3).commitAllowingStateLoss();
                break;
            case R.id.fragment4:
                Intent intent = new Intent(ctx, DetectorActivity.class);
                startActivity(intent);
                break;
        }
    }
}
