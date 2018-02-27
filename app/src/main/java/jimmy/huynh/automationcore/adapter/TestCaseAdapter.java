package jimmy.huynh.automationcore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import jimmy.huynh.automationcore.R;
import jimmy.huynh.automationcore.TestCase;

import static jimmy.huynh.automationcore.utils.Constant.TEST_RESULT_FAILED;
import static jimmy.huynh.automationcore.utils.Constant.TEST_RESULT_PASSED;

/**
 * Created by huutam.huynh on 2/23/18.
 */

public class TestCaseAdapter extends RecyclerView.Adapter<TestCaseAdapter.TestCaseViewHolder> {
    Context mContext;
    List<TestCase> testCases;
    TestCaseListener testCaseListener;
    int mPositionExpand = -1;

    public interface TestCaseListener {
        void onSelect(View v, int position);
    }


    public TestCaseAdapter(Context context, TestCaseListener listener) {
        mContext = context;
        testCases = new ArrayList<>();
        testCaseListener = listener;
    }

    public void addData(List<TestCase> testCaseList) {
        testCases.addAll(testCaseList);
        notifyDataSetChanged();
    }

    public void addData(TestCase testCase) {
        testCases.add(testCase);
        notifyDataSetChanged();
    }

    @Override
    public TestCaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View testCaseView = inflater.inflate(R.layout.item_test_case_layout, parent, false);
        return new TestCaseViewHolder(testCaseView);
    }

    @Override
    public void onBindViewHolder(final TestCaseViewHolder holder, int position) {
        TestCase data = testCases.get(position);
        holder.tvTestID.setText(data.getTestid());
        holder.tvClassName.setText(data.getClassName());
        holder.tvTestIdDetail.setText(data.getTestid());
        holder.tvDescription.setText(data.getDescription());
        holder.tvReturnCode.setText(data.getActualReturnValue());
        holder.tvResult.setText(data.getResult().equals(TEST_RESULT_PASSED) ?
                TEST_RESULT_PASSED :
                TEST_RESULT_FAILED);


        if (data.getDuration() < 1000) {
            holder.tvTime.setText(data.getDuration() + " ms");
        } else {
            long second = TimeUnit.MILLISECONDS.toSeconds(data.getDuration()) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(data.getDuration()));
            long min = TimeUnit.MILLISECONDS.toMinutes(data.getDuration());
            if (min > 0) {
                if (second != 0) {
                    holder.tvTime.setText(String.format("%01d min %01d s", min, second));
                } else {
                    holder.tvTime.setText(String.format("%01d min", min));
                }
            } else {
                holder.tvTime.setText(String.format("%01d s", second));
            }
        }


        if (data.getResult().equals(TEST_RESULT_PASSED)) {
            holder.llTitle.setBackgroundColor(mContext.getResources().getColor(R.color.colorPink));
            holder.ivResult.setImageResource(R.drawable.ic_pass);

        } else {
            holder.llTitle.setBackgroundColor(mContext.getResources().getColor(R.color.colorRed));
            holder.ivResult.setImageResource(R.drawable.ic_delete);

        }

        if (mPositionExpand == position) {

            holder.llDetail.setVisibility(View.VISIBLE);
        } else {
            holder.llDetail.setVisibility(View.GONE);
        }
        holder.llTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPositionExpand == holder.getAdapterPosition()){
                    mPositionExpand =-1;
                }else{
                    mPositionExpand = holder.getAdapterPosition();
                }
                notifyDataSetChanged();
                if(mPositionExpand == getItemCount()-1){
                    testCaseListener.onSelect(view, holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return testCases.size();
    }

    public class TestCaseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_test_id)
        TextView tvTestID;
        @BindView(R.id.tv_class_name)
        TextView tvClassName;
        @BindView(R.id.tv_test_id_detail)
        TextView tvTestIdDetail;
        @BindView(R.id.tv_description)
        TextView tvDescription;
        @BindView(R.id.tv_return_code)
        TextView tvReturnCode;
        @BindView(R.id.tv_result)
        TextView tvResult;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_detail)
        LinearLayout llDetail;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.iv_result)
        ImageView ivResult;

        public TestCaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public TestCase getTestCaseByPosition(int position) {
        return testCases.get(position);
    }
}
