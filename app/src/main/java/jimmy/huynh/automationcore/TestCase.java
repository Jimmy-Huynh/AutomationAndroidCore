package jimmy.huynh.automationcore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import jimmy.huynh.automationcore.utils.Constant;

import static jimmy.huynh.automationcore.utils.Constant.EXPECTED_OUTPUT_ARRAY_LIST_OBJECT;
import static jimmy.huynh.automationcore.utils.Constant.EXPECTED_OUTPUT_ARRAY_LIST_STRING;
import static jimmy.huynh.automationcore.utils.Constant.EXPECTED_OUTPUT_BOOLEAN;
import static jimmy.huynh.automationcore.utils.Constant.EXPECTED_OUTPUT_INTEGER;
import static jimmy.huynh.automationcore.utils.Constant.EXPECTED_OUTPUT_STRING;
import static jimmy.huynh.automationcore.utils.Constant.EXPECTED_OUTPUT_STRING_ARRAY;
import static jimmy.huynh.automationcore.utils.Constant.EXPECTED_OUTPUT_TOKEN_TYPE;
import static jimmy.huynh.automationcore.utils.Constant.EXPECTED_OUTPUT_VOID;

/**
 * Created by huutam.huynh on 2/21/18.
 */

public class TestCase {


    @SerializedName("testid")
    @Expose
    private String testid;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("platform")
    @Expose
    private String platform;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("class_name")
    @Expose
    private String className;

    @SerializedName("method_android")
    @Expose
    private String method;

    @SerializedName("input_type")
    @Expose
    private String inputType;

    @SerializedName("input_number")
    @Expose
    private String inputNums;

    @SerializedName("delay_sec")
    @Expose
    private String delaySec = "0";

    @SerializedName("input")
    @Expose
    private String input;

    @SerializedName("expected_output")
    @Expose
    private String expectedOutput;

    @SerializedName("expected_output_type")
    @Expose
    private String expectedOutputType;

    private String result;
    private String actualReturnValue = "";
    private int duration;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getActualReturnValue() {
        return actualReturnValue;
    }

    public void setActualReturnValue(String actualReturnValue) {
        this.actualReturnValue = actualReturnValue;
    }

    public String getResult() {
        return result == null ? "" : result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public TestCase() {
    }

    public TestCase(String testid, String description, String platform, String type, String className, String method, String inputType, String inputNums, String delaySec, String input, String expectedOutput, String expectedOutputType) {
        this.testid = testid;
        this.description = description;
        this.platform = platform;
        this.type = type;
        this.className = className;
        this.method = method;
        this.inputType = inputType;
        this.inputNums = inputNums;
        this.delaySec = delaySec;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.expectedOutputType = expectedOutputType;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInputNums() {
        return inputNums == null ? "0" : inputNums;
    }

    public void setInputNums(String inputNums) {
        this.inputNums = inputNums;
    }

    public String getDelaySec() {
        return delaySec;
    }

    public void setDelaySec(String delaySec) {
        this.delaySec = delaySec;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getExpectedOutputType() {
        return expectedOutputType;
    }

    public void setExpectedOutputType(String expectedOutputType) {
        this.expectedOutputType = expectedOutputType;
    }

    public Constant.ReturnType getOutput() {
        switch (expectedOutputType) {
            case EXPECTED_OUTPUT_STRING:
                return Constant.ReturnType.STRING_TYPE;
            case EXPECTED_OUTPUT_BOOLEAN:
                return Constant.ReturnType.BOOLEAN_TYPE;
            case EXPECTED_OUTPUT_INTEGER:
                return Constant.ReturnType.INT_TYPE;
            case EXPECTED_OUTPUT_VOID:
                return Constant.ReturnType.VOID_TYPE;
            case EXPECTED_OUTPUT_STRING_ARRAY:
                return Constant.ReturnType.STRING_ARRAY_TYPE;
            case EXPECTED_OUTPUT_TOKEN_TYPE:
                return Constant.ReturnType.TOKEN_TYPE;
            case EXPECTED_OUTPUT_ARRAY_LIST_STRING:
                return Constant.ReturnType.ARRAY_LIST_STRING_TYPE;
            case EXPECTED_OUTPUT_ARRAY_LIST_OBJECT:
                return Constant.ReturnType.ARRAY_LIST_OBJECT_TYPE;
            default:
                return Constant.ReturnType.VOID_TYPE;
        }
    }
}
