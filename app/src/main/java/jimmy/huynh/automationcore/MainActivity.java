package jimmy.huynh.automationcore;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jimmy.huynh.automationcore.adapter.TestCaseAdapter;
import jimmy.huynh.automationcore.utils.Constant;
import jimmy.huynh.automationcore.utils.CountDownAnimation;
import jimmy.huynh.automationcore.utils.Utils;

import static jimmy.huynh.automationcore.utils.Constant.TEST_CASE_BROADCAST;
import static jimmy.huynh.automationcore.utils.Constant.TEST_RESULT_FAILED;
import static jimmy.huynh.automationcore.utils.Constant.TEST_RESULT_PASSED;

public class MainActivity extends AppCompatActivity {

    //    int mSuccessCount = 0;
    protected ProgressDialog mProgress;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.iv_gif_loading)
    ImageView mIvGifLoading;

    @BindView(R.id.rc_test_case)
    RecyclerView rcTestCase;
    @BindView(R.id.dp_test_case)
    DonutProgress dpTestCase;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;

    @BindView(R.id.pb_number)
    ProgressBar pbNumberTestCase;
    @BindView(R.id.ll_result)
    LinearLayout llResult;

    @BindView(R.id.tv_test_id)
    TextView tvTestID;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;
    @BindView(R.id.tv_loading_status)
    TextView tvLoadingStatus;
    @BindView(R.id.tv_failed_numer)
    TextView tvFaileNumber;
    LinearLayoutManager layoutManager;
    TestCaseAdapter testCaseAdapter;
    List<TestCase> mListTestCase = new ArrayList<>();
    public static final String TAG = "Jimmy.Huynh";
    public static final long SECONDS = 1000;
    private int mSuccessCount = 0;
    private TestCaseAsyncTask testCaseAsyncTask;
    private CountDownAnimation mCountDownAnimation;
    private boolean isTestDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbar.setTitle("V-Guard SDK Test");
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        //progress dialog
        mProgress = new ProgressDialog(this);
        mProgress.setCancelable(false);
        mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //set up left panel
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        //Set animation for loading icon
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(mIvGifLoading);
        Glide.with(this).load(R.drawable.ic_loading_transparent).into(imageViewTarget);

        setUpUI();
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(MainActivity.this);
//setting animation for loading text
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(350);
        mCountDownAnimation = new CountDownAnimation(tvLoadingStatus, 1000, "", false);
        mCountDownAnimation.setAnimation(animation);
        mCountDownAnimation.setCountDownListener(new CountDownAnimation.CountDownListener() {
            @Override
            public void onCountDownEnd(CountDownAnimation animation) {
                //todo
            }
        });

        call();
    }

    private void call() {
        //register broadcast for permance testcase
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(mTestCaseReceiverRcvr, new IntentFilter(TEST_CASE_BROADCAST));
        tvLoadingStatus.setVisibility(View.VISIBLE);
        TestCasesManager testCasesManager = new TestCasesManager(MainActivity.this);
        JSonObject testCase = testCasesManager.parseJSON();

        List<TestCase> lst = testCase.getTestcase();
        mListTestCase = testCase.getTestcase();

//                mListTestCase = intent.getParcelableExtra(RetrieveDataService.RESPONSE_TESTCASE_DATA_EXTRA);
        if (mListTestCase == null) {

            Utils.showAlert(MainActivity.this, getString(R.string.title_error),
                    "",
                    new Utils.DialogConfirmInterface() {
                        @Override
                        public void confirm(boolean value) {
                            mCountDownAnimation.endAnimation();
                            mCountDownAnimation.cancel();
                            tvLoadingStatus.setText(R.string.empty_test_case);
                            rlProgress.setVisibility(View.GONE);
                            rlLoading.setVisibility(View.INVISIBLE);
//                                    stopRunTesting();

                        }
                    });

            return;

        }
        Log.d(TAG, "AutomationTest:mTestCaseData size: " + mListTestCase.size() + "_" + mListTestCase.toString());
        tvTestID.setVisibility(View.VISIBLE);
        rlProgress.setVisibility(View.VISIBLE);
        tvLoadingStatus.setText("Now testing...");
        mCountDownAnimation.setStatusText("Now testing...");
        mCountDownAnimation.start();
        updateCircleLoading(false, 100);
        pbNumberTestCase.setMax(mListTestCase.size());
        pbNumberTestCase.setProgress(0);
        dpTestCase.setProgress(0);
        tvNumber.setText(0 + "/" + mListTestCase.size() + "");
        updateCircleLoading(false, 100);

        Intent testIntent = new Intent(TEST_CASE_BROADCAST);
        isTestDone = false;
        testIntent.putExtra("TestCaseCounter", 0);
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(testIntent);
    }

    private BroadcastReceiver mTestCaseReceiverRcvr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (TEST_CASE_BROADCAST.equalsIgnoreCase(intent.getAction())) {
                int currentTestCaseCounter = intent.getExtras().getInt("TestCaseCounter");
                testCaseAsyncTask = new TestCaseAsyncTask(currentTestCaseCounter);
                testCaseAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //new TestCaseAsyncTask(currentTestCaseCounter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    };
    private BroadcastReceiver mRetrieveDataRcvr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            if (intent != null) {

            }
        }
    };

    private void executeTestCase() {
        TestCasesManager testCasesManager = new TestCasesManager(this);
        JSonObject testCase = testCasesManager.parseJSON();

        List<TestCase> lst = testCase.getTestcase();
        mListTestCase = testCase.getTestcase();

        ArrayList<Object> inVolkClasses = new ArrayList<>();
        CallDemostration callDemostration = new CallDemostration();
        inVolkClasses.add(callDemostration);
        AutomationManager automationManager = AutomationManager.getInstance();
        int index = 0;
        int duration;
        long timeStart;
        boolean isSuccess = false;
        for (TestCase testCaseDetail : lst) {
            //setlayout
            index++;
            tvTestID.setText(testCaseDetail.getTestid());
            dpTestCase.setProgress(((float) (index) / mListTestCase.size() * 100));
            tvNumber.setText((index + "/" + mListTestCase.size()));
            pbNumberTestCase.setProgress(index + 1);


            Object invokeObject = AutomationManager.getInvokeClass(testCaseDetail, inVolkClasses);
            Constant.ReturnType returnType = testCaseDetail.getOutput();
            String mResultString = "";
            switch (returnType) {
                case INT_TYPE:
                    mResultString = automationManager.performTestCaseArrayByte(invokeObject, testCaseDetail);
                    break;
                case VOID_TYPE:
                    mResultString = automationManager.performTestCaseVoid(invokeObject, testCaseDetail);
                    break;
                case BOOLEAN_TYPE:
                    mResultString = automationManager.performTestCaseBoolean(invokeObject, testCaseDetail);
                    break;
                case STRING_TYPE:
                    mResultString = automationManager.performTestCaseString(invokeObject, testCaseDetail);
                    break;
                case ARRAY_LIST_OBJECT_TYPE:
                    automationManager.performTestCaseArrayListObject(invokeObject, testCaseDetail);
                    break;
                case ARRAY_BYTE:
                    mResultString = automationManager.performTestCaseArrayByte(invokeObject, testCaseDetail);
                    break;
            }

            int delaySec = Integer.parseInt(testCaseDetail.getDelaySec());
            long timeEnd = System.currentTimeMillis();

            timeStart = System.currentTimeMillis();
            duration = (int) (timeEnd - timeStart) + (int) (delaySec * SECONDS);
            testCaseDetail.setDuration(duration);

            isSuccess = Utils.isSuccessCode(mResultString, testCaseDetail.getExpectedOutput(), testCaseDetail.getOutput());

            String resultString = isSuccess ? TEST_RESULT_PASSED : TEST_RESULT_FAILED;
            if (isSuccess) {
                mSuccessCount++;
            }

            testCaseDetail.setResult(resultString);
            testCaseDetail.setActualReturnValue(mResultString == null ? "" : mResultString);

            if (delaySec > 0) {
                Log.d(TAG, "Automation Delay with: " + delaySec + "s");
                try {
                    Thread.sleep(delaySec * SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            testCaseAdapter.addData(testCaseDetail);
//            String result = automationManager.performTestCaseArrayByte(invokeObject, testCaseDetail);
//            Log.d("Jimmy", "Called: " + result);
        }
        float successPercent = ((float) mSuccessCount / (float) mListTestCase.size()) * 100f;

        updateCircleLoading(true, Math.round(successPercent * 10.0) / 10.0f);
//                isTestDone = true;
//                mCountDownAnimation.endAnimation();
//                mCountDownAnimation.cancel();
        String doneStatusSuccessStr = "PASSED: " + mSuccessCount;
        String doneStatusFailedStr = "FAILED: " + (mListTestCase.size() - mSuccessCount);
        tvLoadingStatus.setText(doneStatusSuccessStr);
        tvFaileNumber.setVisibility(View.VISIBLE);
        tvFaileNumber.setText(doneStatusFailedStr);
        tvTestID.setText("TEST DONE");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem settingItem = menu.findItem(R.id.action_settings);
        settingItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                //todo
                return true;
            }
        });

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        EditText searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        //Set text color
        searchPlate.setTextColor(getResources().getColor(R.color.colorWhite)); // set the text color
        searchPlate.setHintTextColor(getResources().getColor(R.color.colorWhite)); // set the hint color
        //Set icon close button
        ImageView searchClose = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.ic_close);
        //Set icon search button
        ImageView icon = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        icon.setImageResource(R.drawable.ic_search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Todo
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //todo
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        // Handle navigation view item clicks here.
                        int id = menuItem.getItemId();
//                        if (id == R.id.nav_grammar) {
//                            Intent intent = new Intent(MainActivity.this, GrammarActivity.class);
//                            startActivity(intent);
//                        }
//                        if (id == R.id.nav_vocabulary) {
//                            Intent intent = new Intent(MainActivity.this, VocabularyActivity.class);
//                            startActivity(intent);
//                        }
//                        if (id == R.id.nav_story) {
//                            Intent intent = new Intent(MainActivity.this, StoryActivity.class);
//                            startActivity(intent);
//                        }
//                        if (id == R.id.nav_pronunciation) {
//                            Intent intent = new Intent(MainActivity.this, PronunciationActivity.class);
//                            startActivity(intent);
//                        }
//                        if(id == R.id.nav_easy){
//                            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                            startActivity(intent);
//                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    protected void showLoading() {
        if (!mProgress.isShowing()) {
            mProgress.show();
        }
    }

    protected void hideLoading() {
        if (mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }

    private void setUpUI() {
        testCaseAdapter = new TestCaseAdapter(getBaseContext(), new TestCaseAdapter.TestCaseListener() {
            @Override
            public void onSelect(View v, int position) {
                rcTestCase.smoothScrollToPosition(testCaseAdapter.getItemCount() - 1);
            }
        });
        layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcTestCase.getContext(),
                layoutManager.getOrientation());
        rcTestCase.addItemDecoration(dividerItemDecoration);
        rcTestCase.setLayoutManager(layoutManager);
        rcTestCase.setItemAnimator(new DefaultItemAnimator());
        rcTestCase.setAdapter(testCaseAdapter);
    }


    private class TestCaseAsyncTask extends AsyncTask<String, Void, Boolean> {
        int currentTestCaseCounter = 0;
        TestCase testCase;
        int duration;

        public TestCaseAsyncTask(int currentTestCaseCounter) {
            this.currentTestCaseCounter = currentTestCaseCounter;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            testCase = mListTestCase.get(this.currentTestCaseCounter);

            tvTestID.setText(testCase.getTestid());
            dpTestCase.setProgress(((float) (this.currentTestCaseCounter + 1) / mListTestCase.size() * 100));
            tvNumber.setText((this.currentTestCaseCounter) + 1 + "/" + mListTestCase.size());
            pbNumberTestCase.setProgress(this.currentTestCaseCounter + 1);

        }

        @Override
        protected Boolean doInBackground(String... params) {

            String mResultString = "null";
            //perform TestCase
            long timeStart = System.currentTimeMillis();
            AutomationManager automationManager = AutomationManager.getInstance();
            Constant.ReturnType returnType = testCase.getOutput();

            Log.d(TAG, "==================Automationtest============================");
            Log.d(TAG, "Automationtest_" + testCase.getTestid() + "_" + testCase.getMethod() + " Expected output: " + testCase.getExpectedOutput() + " returnType: " + String.valueOf(returnType));
            Log.d(TAG, "Automationtest_" + " input number: " + testCase.getInputNums());
            ArrayList<Object> inVolkClasses = new ArrayList<>();

            CallDemostration callDemostration = new CallDemostration();
            inVolkClasses.add(callDemostration);

            Object invokeObject = AutomationManager.getInvokeClass(testCase, inVolkClasses);

            switch (returnType) {
                case INT_TYPE:
                    mResultString = automationManager.performTestCaseInt(invokeObject, testCase);
                    break;
                case VOID_TYPE:
                    mResultString = automationManager.performTestCaseVoid(invokeObject, testCase);
                    break;
                case BOOLEAN_TYPE:
                    mResultString = automationManager.performTestCaseBoolean(invokeObject, testCase);
                    break;
                case STRING_TYPE:
                    mResultString = automationManager.performTestCaseString(invokeObject, testCase);
                    break;
                case ARRAY_LIST_OBJECT_TYPE:
                    automationManager.performTestCaseArrayListObject(invokeObject, testCase);
                    break;
                case ARRAY_BYTE:
                    mResultString = automationManager.performTestCaseArrayByte(invokeObject, testCase);
                    break;
            }
            int delaySec = Integer.parseInt(testCase.getDelaySec());
            long timeEnd = System.currentTimeMillis();
            duration = (int) (timeEnd - timeStart) + (int) (delaySec * SECONDS);
            testCase.setDuration(duration);

            boolean isSuccess = Utils.isSuccessCode(mResultString, testCase.getExpectedOutput(), testCase.getOutput());
            String resultString = isSuccess ? TEST_RESULT_PASSED : TEST_RESULT_FAILED;
            if (isSuccess) {
                mSuccessCount++;
            }

            testCase.setResult(resultString);
            testCase.setActualReturnValue(mResultString == null ? "" : mResultString);

            if (delaySec > 0) {
                Log.d(TAG, "Automation Delay with: " + delaySec + "s");
                try {
                    Thread.sleep(delaySec * SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "Automation Return result: " + isSuccess);
            return isSuccess;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            currentTestCaseCounter++;

//            testCaseAdapter.setDataSource(mListTestCase);
            testCaseAdapter.addData(testCase);

            if (currentTestCaseCounter < mListTestCase.size()) {
                tvFaileNumber.setVisibility(View.INVISIBLE);
                sendPerformTestcaseBroadcast(currentTestCaseCounter);
            } else {

                float successPercent = ((float) mSuccessCount / (float) mListTestCase.size()) * 100f;

                updateCircleLoading(true, Math.round(successPercent * 10.0) / 10.0f);
                isTestDone = true;
                mCountDownAnimation.endAnimation();
                mCountDownAnimation.cancel();
                String doneStatusSuccessStr = "PASSED: " + mSuccessCount;
                String doneStatusFailedStr = "FAILED: " + (mListTestCase.size() - mSuccessCount);
                tvLoadingStatus.setText(doneStatusSuccessStr);
                tvFaileNumber.setVisibility(View.VISIBLE);
                tvFaileNumber.setText(doneStatusFailedStr);
                tvTestID.setText("TEST DONE");
//                testCaseAdapter.setListTestCaseOrigin(mListTestCase);
            }

        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public void updateCircleLoading(boolean isLoadingDone, float percent) {
        if (isLoadingDone) {
            dpTestCase.setVisibility(View.VISIBLE);
            dpTestCase.setProgress(percent);
            rlLoading.setVisibility(View.GONE);
        } else {
            dpTestCase.setVisibility(View.GONE);
            rlLoading.setVisibility(View.VISIBLE);
        }
    }

    synchronized private void sendPerformTestcaseBroadcast(int counter) {
        final Intent testIntent = new Intent(TEST_CASE_BROADCAST);
        testIntent.putExtra("TestCaseCounter", counter);
        LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(testIntent);
    }


}
