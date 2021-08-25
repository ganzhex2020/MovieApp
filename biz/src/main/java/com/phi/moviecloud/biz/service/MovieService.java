package com.phi.moviecloud.biz.service;


import com.phi.moviecloud.biz.mapper.MovieMapper;
import com.phi.moviecloud.biz.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    MovieMapper movieMapper;

    public List<Movie> findMoviesByUserId(Long userId) {
        List<Movie> movies = movieMapper.findByUserId(userId);
        return movies;
    }

    @Transactional
    public void saveMovie(Movie movie) {
        movieMapper.saveMovie(movie);
    }
}
