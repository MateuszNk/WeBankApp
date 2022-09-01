package io.github.mateusznk.webankapp.domain.api;

import io.github.mateusznk.webankapp.domain.news.News;
import io.github.mateusznk.webankapp.domain.news.NewsDao;

import java.util.List;
import java.util.stream.Collectors;

public class NewsService {
    private final NewsDao newsDao = new NewsDao();

    public List<NewsBasicInfo> findAll() {
        return newsDao.findAllNews()
                .stream().map(NewsMapper::map)
                .collect(Collectors.toList());
    }

    private static class NewsMapper {
        static NewsBasicInfo map(News news) {
            return new NewsBasicInfo(
                    news.getTitle(),
                    news.getUrl(),
                    news.getDescription(),
                    news.getDateAdded()
            );
        }
    }
}
