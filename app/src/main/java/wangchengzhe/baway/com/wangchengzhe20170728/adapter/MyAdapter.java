package wangchengzhe.baway.com.wangchengzhe20170728.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import wangchengzhe.baway.com.wangchengzhe20170728.bean.NewsBean;
import wangchengzhe.baway.com.wangchengzhe20170728.R;

/**
 * 功能描述  :
 * 创建时间 : 2017/7/28 15:04
 * 编写人  :  王成哲
 */
public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder> implements  View.OnClickListener{
//    private OnItemClickListener onItemClickListener;
    private List<NewsBean.DataBean>  mList=new ArrayList<>();
    private Context context;
    private OnItemClickListener mOnItemClickListener = null;
    public MyAdapter(Context context){
        this.context=context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item, null);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.tv.setText(mList.get(position).getTitle());
        Glide.with(context).load(mList.get(position).getUserImg()).into(holder.iv);
        holder.itemView.setTag(position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<NewsBean.DataBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            iv= (ImageView) itemView.findViewById(R.id.iv);
            tv=(TextView)itemView.findViewById(R.id.tv);
        }
    }


    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

}