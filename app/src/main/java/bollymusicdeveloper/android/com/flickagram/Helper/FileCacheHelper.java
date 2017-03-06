package bollymusicdeveloper.android.com.flickagram.Helper;

/**
 * Created by amitagarwal3 on 3/6/2017.
 */

import java.io.File;
import android.content.Context;

public class FileCacheHelper {

    private File cacheDir;

    public FileCacheHelper(Context context){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"Flickagram_Cache");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }

    // look for the file by hashcode
    public File getFile(String url){
        String filename=String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;

    }

    // delete all the files from the cache
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }

}
