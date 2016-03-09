package bft.fishtagsapp.editlinkage;

import android.net.Uri;

/**
 * Created by jamiecho on 3/6/16.
 */
public class TagInfo {
    Uri photo; //photo file path, if it exists.
    Uri tag; //tag info file path, if it exists
    String summary; //concise, one-liner description of this info

    public TagInfo(Uri photo, Uri tag, String summary){
        this.photo=photo;
        this.tag=tag;
        this.summary=summary;
    }
}
