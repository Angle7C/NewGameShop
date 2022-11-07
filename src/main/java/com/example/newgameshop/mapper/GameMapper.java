package com.example.newgameshop.mapper;



import com.example.newgameshop.entity.Game;

import java.util.List;

public interface GameMapper {
    public List<Game> findAll();
    public List<Game> findAllZero();
    public Game findGame(Integer id);
    public List<Game> seekName(String name);
    public List<Game> seekNameZero(String name);
    public void addGame(Game game);
    public void updateGame(Game game);
    public void deleteGame(Game game);
    public List<Game> findPageSize( String bool,String name);
    public List<Game> findGameTypePage( int size ,int page,String gameType,String name);
    public List<Game> findGameTypePageZero(int size, int page, String gameType,Boolean bool);
    public List<Game> orderBy(int size, int page, String gameType,Boolean bool);

}
