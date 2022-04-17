package org.carcinus.tools.bean.response.article;

import org.carcinus.tools.bean.response.Response;

import java.util.List;

public class ArticlesResponse extends Response {
    private ArticleData data;

    public List<ArticleMeta> getArticles() {
        return this.data.getArticles();
    }
    private class ArticleData {
        private List<ArticleMeta> articles;

        public List<ArticleMeta> getArticles() {
            return articles;
        }
    }
}
