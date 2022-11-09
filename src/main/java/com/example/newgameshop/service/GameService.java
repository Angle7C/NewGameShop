package com.example.newgameshop.service;


import com.example.newgameshop.entity.Game;

import java.util.List;

public interface GameService {
    default List<Game> findAll(){return null;}
    default Game findGame(Integer id){return null;}
    default List<Game> seekName(String name){return null;}
    default Boolean addGame(Game game){return false;}
    default Boolean updateGame(Game game){return false;}
    default Boolean deleteGame(Game game){return false;}
    default List<Game> findPageSize(String type,String name){return null;}
    default List<Game> findGameTypePage( int size , int page,String gameType,String name){return null;}
    default List<Game> findGameTypePageZero( int size , int page,String gameType,Boolean bool){return null;}
    default List<Game> findAllZero(){return null;}
    default List<Game> seekNameZero(String Name){return null;}
    default List<Game> orderBy(int size, int page, String gameType,Boolean bool){return null;}
    default List<Game> findGameByType(String type){return null;}
    default List<Game> findGameByWord(String word){return null;}
}
