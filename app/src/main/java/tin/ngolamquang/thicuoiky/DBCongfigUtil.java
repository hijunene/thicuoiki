package tin.ngolamquang.thicuoiky;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBCongfigUtil {
    final  static String DATABASE_NAME = "dbMonan.sqlite";
    final  static String DB_PATH_SUFFIX = "/databases/";

    static SQLiteDatabase database = null;

    public static SQLiteDatabase getDatabase(){
        if(database == null){
            Context context = GlobalApplication.getAppContext();
            copyDatabaseFromAssets(context, DB_PATH_SUFFIX, DATABASE_NAME);
            database = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        }

        return database;
    }

    private static void copyDatabaseFromAssets(Context context, String dbPathSuffix, String databaseName) {
        File dbFile = context.getDatabasePath(databaseName);
        if(!dbFile.exists()){
            File dbDir = new File(context.getApplicationInfo().dataDir + dbPathSuffix);

            if(!dbDir.exists()){
                dbDir.mkdir();
            }

            InputStream is = null;
            OutputStream os = null;

            try{
                is = context.getAssets().open(databaseName);
                String outputFilePath = context.getApplicationInfo().dataDir + dbPathSuffix + databaseName;
                os = new FileOutputStream(outputFilePath);

                byte [] buffer = new byte[1024];
                int length;

                while ((length = is.read(buffer)) > 0){
                    os.write(buffer, 0, length);
                }

                os.flush();
                Toast.makeText(context, "Đã chép xong", Toast.LENGTH_SHORT).show();

            }catch (IOException e) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }finally {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
