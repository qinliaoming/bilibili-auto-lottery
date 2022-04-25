package org.carcinus.tools.api;

import carcinus.code.common.utils.JsonUtils;
import org.carcinus.tools.bean.response.article.ArticleMeta;
import org.carcinus.tools.bean.response.article.ArticlesResponse;
import org.carcinus.tools.utils.HttpUtils;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleApi {

    private static final String LIST_ARTICLES_URL_FORMATTED = "https://api.bilibili.com/x/space/article?mid=%d&sort=publish_time";
    private static final String READ_ARTICLE_URL_FORMATTED = "https://www.bilibili.com/read/cv%s";

    public static List<ArticleMeta> getArticleMetaByUid(int uid) throws IOException {
        String listArticlesUrl = String.format(LIST_ARTICLES_URL_FORMATTED, uid);

        String entity = HttpUtils.doGetEntity(listArticlesUrl);
        ArticlesResponse articlesResponse = JsonUtils.readValue(entity, ArticlesResponse.class);
        return articlesResponse.getArticles();
    }


    public static List<String> getDynamicIdInArticle(int articleId) throws IOException {
        String readArticleUrl = String.format(READ_ARTICLE_URL_FORMATTED, articleId);
        return Jsoup
                .connect(readArticleUrl)
                .get()
                .body()
                .getElementsByClass("article-link")
                .stream()
                .map(element -> element.attr("href"))
                .map(link -> link.substring(link.lastIndexOf("/"), link.lastIndexOf("\\?")))
                .collect(Collectors.toList());
    }

}
