package com.example.newgameshop.service;

import com.gameshop.entity.Game;

import java.util.List;

public interface GameService {
    public List<Game> findAll();
    public Game findGame(Integer id);
    public List<Game> seekName(String name);
    public void addGame(Game game);
    public void updateGame(Game game);
    public void deleteGame(Game game);
    public List<Game> findPageSize(String type,String name);
    public List<Game> findGameTypePage( int size , int page,String gameType,String name);
    public List<Game> findGameTypePageZero( int size , int page,String gameType);
    public List<Game> findAllZero();
    public List<Game> seekNameZero(String Name);
    public List<Game> orderBy(int size, int page, String gameType,Boolean bool);

}
