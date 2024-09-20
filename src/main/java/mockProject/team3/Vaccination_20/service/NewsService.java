package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.newsDto.NewsAddRequestDto;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsResponseDto;
import org.springframework.data.domain.Page;

public interface NewsService {
    NewsResponseDto addNews(NewsAddRequestDto newsAddRequestDto);

    public Page<NewsResponseDto> findByTittleOrContent(String searchInput, int page, int size);
}
