package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.newsDto.NewsRequestDto1;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsResponseDto1;
import org.springframework.data.domain.Page;

public interface NewsService {
    NewsResponseDto1 addNews(NewsRequestDto1 newsRequestDto1);

    public Page<NewsResponseDto1> findByTittleOrContent(String searchInput, int page, int size);
}
