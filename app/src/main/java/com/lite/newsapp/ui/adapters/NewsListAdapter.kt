package com.lite.newsapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lite.newsapp.R
import com.lite.newsapp.models.Articles

class NewsListAdapter(private val context: Context) :
    RecyclerView.Adapter<NewsListAdapter.NewsItemResponseHolder>() {

    private val articles = ArrayList<Articles>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemResponseHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item_response, parent, false)
        return NewsItemResponseHolder(view)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: NewsItemResponseHolder, position: Int) {
        holder.textView.text = articles[position].title
        Glide.with(context)
            .load(articles[position].urlToImage)
            .placeholder(context.getDrawable(R.drawable.white_background))
            .into(holder.imageView)

    }

    fun setArticlesResponse(articles: List<Articles>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    class NewsItemResponseHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageView: ImageView = view.findViewById(R.id.news_image)
        var textView: TextView = view.findViewById(R.id.news_response)
    }
}