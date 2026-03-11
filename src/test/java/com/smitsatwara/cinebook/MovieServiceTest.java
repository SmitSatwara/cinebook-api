package com.smitsatwara.cinebook;

import com.smitsatwara.cinebook.dto.MovieRequest;
import com.smitsatwara.cinebook.model.Language;
import com.smitsatwara.cinebook.model.Movie;
import com.smitsatwara.cinebook.repository.MovieRepository;
import com.smitsatwara.cinebook.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void getMovieById_WhenMovieExists_ShouldReturnMovie() {
        // Arrange
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitle("Inception");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        // Act
        Movie result = movieService.getMovieById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
        verify(movieRepository, times(1)).findById(1L);
    }

    @Test
    void getMovieById_WhenMovieNotFound_ShouldThrowException() {
        // Arrange
        when(movieRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> movieService.getMovieById(99L));
        verify(movieRepository, times(1)).findById(99L);
    }

    @Test
    void addMovie_WhenMovieDoesNotExist_ShouldSaveAndReturnMovie(){
        //Arrange
        MovieRequest movieRequest =  new MovieRequest("Pushpa","Action", Language.HINDI,120,8.5, LocalDate.of(2025,04,20),"Pushpa mvie descriotn");
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitle(movieRequest.getTitle());
        movie.setGenre(movieRequest.getGenre());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setRating(movieRequest.getRating());
        movie.setDuration(movieRequest.getDuration());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        when(movieRepository.findByTitleAndLanguage(movieRequest.getTitle(),movieRequest.getLanguage())).thenReturn(Optional.empty());
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        //Act
        Movie  result = movieService.addMovie(movieRequest);

        //Assert
        assertEquals(movie,result);
        verify(movieRepository,times(1)).save(any(Movie.class));

    }

    @Test
    void addMovie_WhenMovieAlreadyExists_ShouldThrowException(){
        //Arrange Act
        MovieRequest movieRequest =  new MovieRequest("Pushpa","Action", Language.HINDI,120,8.5, LocalDate.of(2025,04,20),"Pushpa mvie descriotn");

        Movie movie = new Movie();
        movie.setTitle("Pushpa");
        movie.setLanguage(Language.HINDI);
        when(movieRepository.findByTitleAndLanguage(movie.getTitle(),movie.getLanguage())).thenReturn(Optional.of(movie));
        //Assert
        assertThrows(RuntimeException.class,()->movieService.addMovie(movieRequest));
        verify(movieRepository,times(1)).findByTitleAndLanguage(movie.getTitle(),movie.getLanguage());

    }
    @Test
    void deleteMovie_WhenMovieExists_ShouldDeleteSuccessfully(){
        //Arrange
        Movie movie = new Movie();
        movie.setMovieId(1L);
        movie.setTitle("Pushpa");
        when(movieRepository.existsById(1L)).thenReturn(true);
        //Act
        movieService.deleteMovie(1L);

        //Assert
        verify(movieRepository,times(1)).deleteById(1L);
    }

    @Test
    void deleteMovie_WhenMovieNotExist_ShouldThrowException(){
        //Arrange
        //Act
        when(movieRepository.existsById(1L)).thenReturn(false);
        //Assert
        assertThrows(RuntimeException.class,()->movieService.deleteMovie(1L));
        verify(movieRepository,never()).deleteById(anyLong());
    }

}