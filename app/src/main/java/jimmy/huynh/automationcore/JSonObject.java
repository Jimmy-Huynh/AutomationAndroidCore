package jimmy.huynh.automationcore;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huutam.huynh on 2/21/18.
 */

public class JSonObject {

    @SerializedName("testcase")
    private List<TestCase> testcase = new ArrayList<>();

    public List<TestCase> getTestcase() {
        return testcase;
    }

    public void setTestcase(List<TestCase> testcase) {
        this.testcase = testcase;
    }
}
