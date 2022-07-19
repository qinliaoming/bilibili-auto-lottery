package org.bilibili.lottery.api;


import lombok.Data;
import org.bilibili.lottery.common.utils.HttpUtils;
import org.bilibili.lottery.common.utils.JacksonUtils;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ArticleApi {

    private static final String LIST_ARTICLES_URL_FORMATTED = "https://api.bilibili.com/x/space/article?mid=%d&sort=publish_time";
    private static final String READ_ARTICLE_URL_FORMATTED = "https://www.bilibili.com/read/cv%s";
    private static final Pattern patten = Pattern.compile("\\d{17,}+");

    private static int getArticleMetaByUid(int uid) throws IOException {
        String listArticlesUrl = String.format(LIST_ARTICLES_URL_FORMATTED, uid);

        String entity = HttpUtils.doGetEntity(listArticlesUrl);
        ArticlesResponse articlesResponse = JacksonUtils.readValue(entity, ArticlesResponse.class);
        List<ArticleMeta> articles = articlesResponse.getArticles();
        if (articles != null && !articles.isEmpty()) {
            return articles.get(0).getId();
        }
        return -1;
    }


    public static List<String> getDynamicIdsInArticle(int uid) throws IOException {
        int articleId = getArticleMetaByUid(uid);
        if (articleId == -1) return Collections.emptyList();
        String readArticleUrl = String.format(READ_ARTICLE_URL_FORMATTED, articleId);
        return Jsoup
                .connect(readArticleUrl)
                .get()
                .body()
                .getElementsByClass("article-link")
                .stream()
                .map(element -> element.attr("href"))
                .map(link -> {
                    Matcher matcher = patten.matcher(link);
                    if (matcher.find()) return matcher.group();
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static class ArticlesResponse {
        private ArticleData data;

        public List<ArticleMeta> getArticles() {
            return this.data.getArticles();
        }

    }

    private static class ArticleData {
        private List<ArticleMeta> articles;

        public List<ArticleMeta> getArticles() {
            return this.articles;
        }
    }

    @Data
    private static class ArticleMeta {
        private int id;
        private String title;
    }

}
