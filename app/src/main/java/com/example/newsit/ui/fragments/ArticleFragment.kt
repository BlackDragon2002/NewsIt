package com.example.newsit.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsit.R
import com.example.newsit.databinding.FragmentArticleBinding
import com.example.newsit.databinding.FragmentBreakingNewsBinding
import com.example.newsit.ui.NewsActivity
import com.example.newsit.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment :Fragment(R.layout.fragment_article){

    lateinit var viewModel:NewsViewModel

    private lateinit var binding: FragmentArticleBinding

    val args :ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as NewsActivity).viewModel

        val article=args.article
        binding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener {
            viewModel.savedArticle(article)
            Snackbar.make(view,"Article Saved Successfully",Snackbar.LENGTH_SHORT).show()
        }
    }
}