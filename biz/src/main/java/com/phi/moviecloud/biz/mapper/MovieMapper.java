package com.phi.moviecloud.biz.mapper;


import com.phi.moviecloud.biz.model.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {
    List<Movie> findByUserId(Long userId);

    int saveMovie(@Param("movie")Movie movie);

    // for testing
    boolean deleteAll();
}
