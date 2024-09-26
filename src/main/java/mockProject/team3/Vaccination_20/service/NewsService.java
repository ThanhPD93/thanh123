package mockProject.team3.Vaccination_20.service;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsDeleteDto;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsRequestDto1;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsResponseDto;
import org.springframework.data.domain.Page;

public interface NewsService {
    NewsResponseDto addNews(NewsRequestDto1 newsAddRequestDto);

    Page<NewsResponseDto> findByTittleOrContent(String searchInput, int page, int size);

    NewsResponseDto findById(String id);

    boolean deleteByIds(NewsDeleteDto ids);
}