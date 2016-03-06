package bft.fishtagsapp.editlinkage;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ImageView(getApplicationContext()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        TagInfo[] existingLinkages = { // in the real app, this would be loaded from app's internal memory on startup.
                new TagInfo("Photo1","Tag1","Descr1"),
                new TagInfo("Photo2","Tag2","Descr2"),
        };

        ArrayAdapter<TagInfo> adapter = new MyAdapter(this, existingLinkages);

        ListView listview = (ListView)findViewById(R.id.linkageList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
