package jimmy.huynh.automationcore.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jimmy.huynh.automationcore.TestCase;

import static android.content.ContentValues.TAG;
import static jimmy.huynh.automationcore.utils.Constant.COMMA;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_ARRAY_LIST_OBJECT;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_ARRAY_LIST_STRING;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_BOOLEAN;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_BYTE_ARRAY;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_DISTINGUISHED_NAME;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_INTEGER;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_STRING;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_STRING_ARRAY;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_TOKEN_TYPE;
import static jimmy.huynh.automationcore.utils.Constant.INPUT_TYPE_VOID;
import static jimmy.huynh.automationcore.utils.Constant.NULL_VALUE_INPUT;
import static jimmy.huynh.automationcore.utils.Constant.SEMICOLON;

/**
 * Created by huutam.huynh on 2/22/18.
 */

public class Utils {
    public static Class<?>[] getParamsType(TestCase testcase) {
        String[] splitInput = testcase.getInput().split(COMMA);
        String[] splitInputParamTypes = testcase.getInputType().split(COMMA);
        Class<?> params[] = new Class[splitInput.length];
        if (splitInputParamTypes.length == 1) {
            Constant.ParamType paramType = Utils.getParamType(splitInputParamTypes[0]);
            switch (paramType) {
                case INT_TYPE:
                    for (int i = 0; i < splitInput.length; i++) {
                        params[i] = int.class;
                    }
                    break;
                case STRING_TYPE:
                    for (int i = 0; i < splitInput.length; i++) {
                        params[i] = String.class;
                    }
                    break;
                case BOOLEAN_TYPE:
                    for (int i = 0; i < splitInput.length; i++) {
                        params[i] = boolean.class;
                    }
                    break;
                case ARRAY_LIST_STRING_TYPE:
                case ARRAY_LIST_OBJECT_TYPE:
                    for (int i = 0; i < splitInput.length; i++) {
                        params[i] = ArrayList.class;
                    }
                    break;
                case TYPE_BYTE_ARRAY:
                    for (int i = 0; i < splitInput.length; i++) {
                        params[i] = byte[].class;
                    }
                    break;
                default:
                    for (int i = 0; i < splitInput.length; i++) {
                        params[i] = String.class;
                    }
                    break;
            }
        } else {
            for (int i = 0; i < splitInputParamTypes.length; i++) {
                Constant.ParamType paramType = Utils.getParamType(splitInputParamTypes[i]);
                switch (paramType) {
                    case INT_TYPE:
                        params[i] = int.class;
                        break;
                    case STRING_TYPE:
                        params[i] = String.class;
                        break;
                    case BOOLEAN_TYPE:
                        params[i] = boolean.class;
                        break;
                    case ARRAY_LIST_STRING_TYPE:
                    case ARRAY_LIST_OBJECT_TYPE:
                        params[i] = ArrayList.class;
                        break;
                    case TYPE_BYTE_ARRAY:
                        params[i] = byte[].class;
                        break;
                    default:
                        params[i] = String.class;
                        break;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < params.length; i++) {
            String str = params[i].toString();
            stringBuilder.append(i == 0 ? "" : COMMA).append(str);

        }
        Log.d(TAG, " Automation Get getParamsType type: " + stringBuilder.toString());
        return params;
    }

    public static Object[] getParamsValues(TestCase testcase) {
        String[] splitInput = testcase.getInput().split(COMMA);
        Object[] argument = new Object[splitInput.length];
        String[] splitInputParamTypes = testcase.getInputType().split(COMMA);
        if (splitInputParamTypes.length == 1) {
            Constant.ParamType paramType = Utils.getParamType(splitInputParamTypes[0]);
            switch (paramType) {
                case INT_TYPE:
                    for (int i = 0; i < splitInput.length; i++) {
                        argument[i] = Integer.parseInt(splitInput[i]);
                    }
                    break;
                case STRING_TYPE:
                    for (int i = 0; i < splitInput.length; i++) {
                        argument[i] = splitInput[i];
                    }
                    break;
                case BOOLEAN_TYPE:
                    for (int i = 0; i < splitInput.length; i++) {
                        argument[i] = "true".equals(splitInput[i]);
                    }
                    break;
                case ARRAY_LIST_STRING_TYPE:
                case ARRAY_LIST_OBJECT_TYPE:
                    for (int i = 0; i < splitInput.length; i++) {
                        argument[i] = Utils.getListStringFromStringArray(splitInput[i]);
                    }
                    break;

                default:
                    for (int i = 0; i < splitInput.length; i++) {
                        argument[i] = splitInput[i];
                    }
                    break;
            }
        } else {
            for (int i = 0; i < splitInputParamTypes.length; i++) {
                Constant.ParamType paramType = Utils.getParamType(splitInputParamTypes[i]);
                switch (paramType) {
                    case INT_TYPE:
                        argument[i] = Integer.parseInt(splitInput[i]);
                        ;
                        break;
                    case STRING_TYPE:
                        argument[i] = splitInput[i];
                        break;
                    case BOOLEAN_TYPE:
                        argument[i] = "true".equals(splitInput[i]);
//                        argument[i] = "true" == splitInput[i];
                        break;
                    case ARRAY_LIST_STRING_TYPE:
                    case ARRAY_LIST_OBJECT_TYPE:
                        argument[i] = Utils.getListStringFromStringArray(splitInput[i]);
                        break;

                    default:
                        argument[i] = splitInput[i];
                        break;
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < argument.length; i++) {
            if (argument[i] != null) {
                String str = argument[i].toString();
                stringBuilder.append(i == 0 ? "" : COMMA).append(str);
            }

        }
        Log.d(TAG, "Automation Get getParamsValues input: " + stringBuilder.toString());
        return argument;
    }

    public static Constant.ParamType getParamType(String paramTypeStr) {
        switch (paramTypeStr) {
            case INPUT_TYPE_STRING:
                return Constant.ParamType.STRING_TYPE;
            case INPUT_TYPE_BOOLEAN:
                return Constant.ParamType.BOOLEAN_TYPE;
            case INPUT_TYPE_INTEGER:
                return Constant.ParamType.INT_TYPE;
            case INPUT_TYPE_VOID:
                return Constant.ParamType.VOID_TYPE;
            case INPUT_TYPE_STRING_ARRAY:
                return Constant.ParamType.STRING_ARRAY_TYPE;
            case INPUT_TYPE_TOKEN_TYPE:
                return Constant.ParamType.TOKEN_TYPE;
            case INPUT_TYPE_ARRAY_LIST_STRING:
                return Constant.ParamType.ARRAY_LIST_STRING_TYPE;
            case INPUT_TYPE_ARRAY_LIST_OBJECT:
                return Constant.ParamType.ARRAY_LIST_OBJECT_TYPE;
            case INPUT_TYPE_DISTINGUISHED_NAME:
                return Constant.ParamType.DISTINGUISHED_NAME_TYPE;
            case INPUT_TYPE_BYTE_ARRAY:
                return Constant.ParamType.TYPE_BYTE_ARRAY;
            default:
                return Constant.ParamType.ANY;
        }
    }

    public static List<String> getListStringFromStringArray(String arrayStrFormat) {
        String newStringWithoutArray = arrayStrFormat.substring(Constant.ARRAY.length() + 1, arrayStrFormat.length() - 1);
        String[] array = newStringWithoutArray.split(SEMICOLON);
        ArrayList<String> result = new ArrayList<>();
        if (array.length == 0) {
            result.add(newStringWithoutArray);
        } else {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(NULL_VALUE_INPUT)) {
                    continue;
                } else {
                    String curentInput = array[i];
                    String finalString = curentInput;
                    result.add(finalString);
                }
            }
        }
        return result;
    }

    public static boolean isSuccessCode(String result, String expectedResult, Constant.ReturnType returnType) {
        Log.d(TAG, "result: " + result);
        if (result == null) {
            if (returnType == Constant.ReturnType.STRING_TYPE) {
                return true;
            } else {
                return false;
            }
        }

        switch (returnType) {
            case INT_TYPE:
                try {
                    int resultCode = Integer.parseInt(result);
                    String[] expectedCodeArr = expectedResult.split(",");
                    for (int i = 0; i < expectedCodeArr.length; i++) {
                        if (resultCode == Integer.parseInt(expectedCodeArr[i])) {
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                break;
        }
        return false;
    }

    public static void showAlert(Context context, String title, String msg, final DialogConfirmInterface dialogInterface) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, AlertDialog.THEME_HOLO_LIGHT);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialogInterface != null) {
                            dialogInterface.confirm(true);
                        }

                    }
                });
        alertDialogBuilder.create().show();
    }

    public interface DialogConfirmInterface {
        void confirm(boolean value);
    }
}
