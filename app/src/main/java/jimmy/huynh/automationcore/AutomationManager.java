package jimmy.huynh.automationcore;

import android.support.annotation.NonNull;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import jimmy.huynh.automationcore.utils.Constant;

import static jimmy.huynh.automationcore.utils.Constant.TEST_RESULT_FAILED;
import static jimmy.huynh.automationcore.utils.Constant.TEST_RESULT_PASSED;
import static jimmy.huynh.automationcore.utils.Utils.getParamsType;
import static jimmy.huynh.automationcore.utils.Utils.getParamsValues;

public class AutomationManager {

    private final String TAG = AutomationManager.class.getSimpleName();

    private static AutomationManager _automationManager;

    public static AutomationManager getInstance() {
        if (_automationManager == null) {
            _automationManager = new AutomationManager();
        }
        return _automationManager;
    }

    public String performTestCaseArrayByte(Object oInterface, @NonNull TestCase testcase) {
        Method method;
        int resultCode = 0;
        try {
            int numInputs = Integer.parseInt(testcase.getInputNums());
            if (numInputs == 0) {
                method = oInterface.getClass().getMethod(testcase.getMethod(), null);
                resultCode = (int) method.invoke(oInterface);
            } else if (testcase.getInput() != null) {
                Class<?> params[] = getParamsType(testcase);
                Object[] argument = getParamsValues(testcase);
                // with parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), params);
                resultCode = ((int) method.invoke(oInterface, argument));
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return String.valueOf(resultCode);
    }

    /**
     * Performance test case with output type int
     *
     * @param oInterface
     * @param testcase
     * @return int:result code
     */
    public String performTestCaseInt(Object oInterface, @NonNull TestCase testcase) {
        Method method;
        String resultCode = "";
        try {

            int numInputs = Integer.parseInt(testcase.getInputNums());

            if (numInputs == 0) {
                method = oInterface.getClass().getMethod(testcase.getMethod(), null);
                resultCode = String.valueOf(method.invoke(oInterface));
            } else if (testcase.getInput() != null) {
                Class<?> params[] = getParamsType(testcase);
                Object[] argument = getParamsValues(testcase);
                // with parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), params);
                resultCode = String.valueOf(method.invoke(oInterface, argument));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "_performTestCaseInt_result: " + resultCode);
        return resultCode;
    }

    /**
     * Performance testcase return to void
     *
     * @param oInterface
     * @param testcase
     */
    public String performTestCaseVoid(Object oInterface, @NonNull TestCase testcase) {
        Method method;
        String result = "";
        try {

            if (testcase.getInputNums().contains(",")) {
                return TEST_RESULT_FAILED;
            }
            int numInputs = Integer.parseInt(testcase.getInputNums());
            if (numInputs == 0) {
                //no parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), null);
                method.invoke(oInterface);
                result = TEST_RESULT_PASSED;
            } else if (testcase.getInput() != null) {
                Class<?> params[] = getParamsType(testcase);
                Object[] argument = getParamsValues(testcase);
                // with parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), params);
                method.invoke(oInterface, argument);
                result = TEST_RESULT_PASSED;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return TEST_RESULT_FAILED;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return TEST_RESULT_FAILED;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return TEST_RESULT_FAILED;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return TEST_RESULT_FAILED;
        }
        Log.d(TAG, "_performTestCaseVoid_result: " + result);
        return result;
    }

    public String performTestCaseString(Object oInterface, @NonNull TestCase testcase) {
        Method method;
        String result = "";
        try {

            int numInputs = Integer.parseInt(testcase.getInputNums());

            if (numInputs == 0) {
                //no parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), null);
                result = method.invoke(oInterface) + "";
            } else if (testcase.getInput() != null) {
                Class<?> params[] = getParamsType(testcase);
                Object[] argument = getParamsValues(testcase);
                // with parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), params);
                result = method.invoke(oInterface, argument) + "";
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "_performTestCaseString_result: " + result);
        return result;
    }

    /**
     * Performance testcase return boolean
     *
     * @param oInterface
     * @param testcase
     * @return
     */
    public String performTestCaseBoolean(Object oInterface, @NonNull TestCase testcase) {
        Method method;
        boolean result = false;
        try {

            int numInputs = Integer.parseInt(testcase.getInputNums());

            if (numInputs == 0) {
                //no parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), null);
                Constant.ReturnType returnType = testcase.getOutput();
                result = (boolean) method.invoke(oInterface);
            } else if (testcase.getInput() != null) {
                Class<?> params[] = getParamsType(testcase);
                Object[] argument = getParamsValues(testcase);
                // with parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), params);
                result = (boolean) method.invoke(oInterface, argument);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "_performTestCaseBoolean_result: " + result);
        return String.valueOf(result);
    }

    /**
     * Performance testcase return String[]
     *
     * @param oInterface
     * @param testcase
     * @return
     */
    public String[] performTestCaseStringArray(Object oInterface, @NonNull TestCase testcase) {
        Method method;
        String[] result = new String[0];
        try {

            int numInputs = Integer.parseInt(testcase.getInputNums());

            if (numInputs == 0) {
                //no parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), null);
                Constant.ReturnType returnType = testcase.getOutput();
                result = (String[]) method.invoke(oInterface);
            } else if (testcase.getInput() != null) {
                Class<?> params[] = getParamsType(testcase);
                Object[] argument = getParamsValues(testcase);
                // with parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), params);
                result = (String[]) method.invoke(oInterface, argument);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Log.d(TAG, testcase.getTestid() + "_performTestCaseStringArray_result: " + result);
        return result;
    }

    /**
     *
     * @param oInterface
     * @param testcase
     * @return
     */
    public ArrayList<Object> performTestCaseArrayListObject(Object oInterface, @NonNull TestCase testcase) {
        Method method;
        ArrayList<Object> result = new ArrayList<>();
        try {

            int numInputs = Integer.parseInt(testcase.getInputNums());

            if (numInputs == 0) {
                //no parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), null);
                Constant.ReturnType returnType = testcase.getOutput();
                result = (ArrayList<Object>) method.invoke(oInterface);
            } else if (testcase.getInput() != null) {
                Class<?> params[] = getParamsType(testcase);
                Object[] argument = getParamsValues(testcase);
                // with parrams
                method = oInterface.getClass().getMethod(testcase.getMethod(), params);
                result = (ArrayList<Object>) method.invoke(oInterface, argument);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "_performTestCaseArrayListObject_result: " + result);
        return result;
    }

    public static Object getInvokeClass(TestCase testCase, ArrayList<Object> inVolkeCLasses) {
        for (Object obj : inVolkeCLasses) {
            if (testCase.getClassName().equals(obj.getClass().getSimpleName())) {
                return obj;
            }
        }
        return null;
    }
}
