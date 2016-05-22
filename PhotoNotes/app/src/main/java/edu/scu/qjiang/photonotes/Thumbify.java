package edu.scu.qjiang.photonotes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.widget.ImageView;

import java.io.FileOutputStream;

/**
 * Created by clover on 5/11/16.
 */
public class Thumbify {
    static public void generateThumbnail(String imgFile, String thumbFile) {
        try {
            Bitmap picture = BitmapFactory.decodeFile(imgFile);
            Bitmap resized = ThumbnailUtils.extractThumbnail(picture, 120, 120);
            FileOutputStream fos = new FileOutputStream(thumbFile);
            resized.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanImageView(ImageView imageView) {
        if (imageView.getDrawable() instanceof BitmapDrawable) {
            return;
        }

        //Clean up the view's image for the sake of memory
        BitmapDrawable b = (BitmapDrawable)imageView.getDrawable();
        b.getBitmap().recycle();
        imageView.setImageDrawable(null);
    }
}
