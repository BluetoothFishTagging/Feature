package bft.fishtagsapp.editlinkage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by jamiecho on 3/6/16.
 */

public class MyAdapter extends ArrayAdapter<TagInfo> {

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    ViewHolder viewHolder;
    LayoutInflater inflater;

    public MyAdapter(Context context, TagInfo[] values) {
        super(context, R.layout.row_layout,values);
        inflater = LayoutInflater.from(getContext());
    }
    private Bitmap decodeFile(File f) { // not my code; http://stackoverflow.com/questions/477572/strange-out-of-memory-issue-while-loading-an-image-to-a-bitmap-object#823966
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.descriptionText);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TagInfo s = getItem(position);

        viewHolder.textView.setText(s.description);

        String photoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera/20160211_051742.jpg"; //photo directory.

        Bitmap bm = decodeFile(new File(photoPath)); //or something else, retrieved from app's internal memory.

        viewHolder.imageView.setImageBitmap(bm); //cannot set directly to URI, outofmemoryerror.

        //viewHolder.imageView.setImageResource(R.drawable.placeholder);

        return  convertView;
    }
}
