package jimmy.huynh.automationcore.utils;

/**
 * Created by huutam.huynh on 2/22/18.
 */

public class Constant {

    public static final String INPUT_TYPE_BYTE_ARRAY = "vkey_byte_array";
    public static final String INPUT_TYPE_DISTINGUISHED_NAME = "DistinguishedName";
    public static final String INPUT_TYPE_STRING = "string";
    public static final String INPUT_TYPE_BOOLEAN = "boolean";
    public static final String INPUT_TYPE_INTEGER = "integer";
    public static final String INPUT_TYPE_VOID = "void";
    public static final String INPUT_TYPE_STRING_ARRAY = "string_array";
    public static final String INPUT_TYPE_TOKEN_TYPE = "token_type";
    public static final String INPUT_TYPE_ARRAY_LIST_STRING = "array_list_string";
    public static final String INPUT_TYPE_ARRAY_LIST_OBJECT = "array_list_object";

    public static final String NULL_VALUE_INPUT = "NULL";
    public static final String COMMA = ",";
    public static final String ARRAY = "array";
    public static final String SEMICOLON = ";";

    public static final String EXPECTED_OUTPUT_STRING = "string";
    public static final String EXPECTED_OUTPUT_BOOLEAN = "boolean";
    public static final String EXPECTED_OUTPUT_INTEGER = "integer";
    public static final String EXPECTED_OUTPUT_VOID = "void";
    public static final String EXPECTED_OUTPUT_STRING_ARRAY = "string_array";
    public static final String EXPECTED_OUTPUT_ARRAY_LIST_STRING = "array_list_string";
    public static final String EXPECTED_OUTPUT_ARRAY_LIST_OBJECT = "array_list_object";
    public static final String EXPECTED_OUTPUT_TOKEN_TYPE = "VKEY_TOKEN_TYPE";
    public static final String EXPECTED_OUTPUT_ARRAY_BYTE = "byte[]";

    public static final String TEST_CASE_BROADCAST = "test_case_broadcast";

    public static final String TEST_RESULT_PASSED = "passed";
    public static final String TEST_RESULT_FAILED = "failed";

    public enum ReturnType {
        INT_TYPE,
        STRING_TYPE,
        BOOLEAN_TYPE,
        VOID_TYPE,
        STRING_ARRAY_TYPE,
        TOKEN_TYPE,
        ARRAY_LIST_STRING_TYPE,
        ARRAY_LIST_OBJECT_TYPE,
        OSP_PAGE_ACTION,
        ARRAY_BYTE,
        ANY
    }

    public enum ParamType {
        INT_TYPE,
        STRING_TYPE,
        BOOLEAN_TYPE,
        VOID_TYPE,
        STRING_ARRAY_TYPE,
        TOKEN_TYPE,
        ARRAY_LIST_STRING_TYPE,
        ARRAY_LIST_OBJECT_TYPE,
        DISTINGUISHED_NAME_TYPE,
        TYPE_BYTE_ARRAY,
        ARRAY_BYTE,
        ANY
    }
}
