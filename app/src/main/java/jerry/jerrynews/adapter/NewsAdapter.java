package jerry.jerrynews.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import jerry.jerrynews.bean.NewsBean;
import jerry.jerrynews.R;
import jerry.jerrynews.activity.DetailActivity;

/**
 * Created by Administrator on 2017/5/22.
 */


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<NewsBean.ResultBean.DataBean> mNewsList;
    private Context context;

    private OnItemClickListener mOnItemClickListener;


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView item_image_id;
        TextView item_title_id, item_date_id, item_authorName_id;

        public ViewHolder(View itemView) {
            super(itemView);
            item_image_id = (ImageView) itemView.findViewById(R.id.item_image_id);
            item_title_id = (TextView) itemView.findViewById(R.id.item_title_id);
            item_date_id = (TextView) itemView.findViewById(R.id.item_date_id);
            item_authorName_id = (TextView) itemView.findViewById(R.id.item_authorName_id);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }

    public NewsAdapter(List<NewsBean.ResultBean.DataBean> newsList) {
        this.mNewsList = newsList;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        NewsAdapter.ViewHolder holder = new NewsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NewsBean.ResultBean.DataBean news = mNewsList.get(position);
        // TODO: 2017/5/22 加一个placeholder，表示正在加载中 
        Picasso.with(holder.item_image_id.getContext()).load(news.getThumbnail_pic_s())
                .transform(new CropSquareTransformation())
                .placeholder(R.mipmap.loading)
                .into(holder.item_image_id);
        holder.item_title_id.setText(news.getTitle());
        holder.item_date_id.setText(news.getDate());
        holder.item_authorName_id.setText(news.getAuthor_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = news.getUrl();
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                /*intent.putExtra(DetailActivity.URL_EXTRA, url);
                intent.putExtra(DetailActivity.KEY_EXTRA, news.getUniquekey());
                intent.putExtra(DetailActivity.TITLE_EXTRA, news.getTitle());*/
                intent.putExtra("news", news);

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    class CropSquareTransformation implements Transformation {

        //截取从宽度和高度最小作为bitmap的宽度和高度
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();//释放bitmap
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }
    }

    public void changData(List<NewsBean.ResultBean.DataBean> newsList) {
        this.mNewsList = newsList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public NewsBean.ResultBean.DataBean getItem(int position) {
        return mNewsList == null ? null : mNewsList.get(position);
    }
}
