package jimmy.huynh.automationcore;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huutam.huynh on 2/21/18.
 */

public class TestCasesManager {
    private Activity _activity;


    public TestCasesManager(Activity activity) {
        _activity = activity;
    }

    public JSonObject parseJSON() {
        Gson gson = new GsonBuilder().create();
        String json = loadJsonFile();

        JSonObject listTestCases = gson.fromJson(json, JSonObject.class);
        return listTestCases;
    }

    private String loadJsonFile() {
        String json = null;
        try {
            InputStream inputStream = _activity.getAssets().open("jimmy.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

}
