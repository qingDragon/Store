package com.big.yanzhuang.store;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PhoneActivity extends AppCompatActivity {
    private List<Person> personList = new ArrayList<>();
    private ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initPersons();
        PersonAdapter adapter = new PersonAdapter(PhoneActivity.this,R.layout.add_phone,personList);
        listView1 =(ListView) findViewById(R.id.lv_phone);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = personList.get(position);
//                if (ContextCompat.checkSelfPermission(PhoneActivity.this,
//                        Manifest.permission.CALL_PHONE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(PhoneActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
//
//                } else {
                    callPhone(person);
//                }
            }
        });
    }
    private  void initPersons(){
        Person p1 = new Person("顾阳","12312341234");
        Person p2 = new Person("张朔","12312341234");
        Person p3 = new Person("颜壮","13813322995");
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                break;
//
//        }
//    }

    public void callPhone(Person person) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + person.getNum());
            intent.setData(data);
            startActivity(intent);
        }catch(SecurityException e ){
            e.printStackTrace();

        }

    }
}
