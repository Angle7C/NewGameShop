package com.example.newgameshop.service.Impl;

import com.example.newgameshop.entity.Game;
import com.example.newgameshop.mapper.GameMapper;
import com.example.newgameshop.service.GameService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Resource
    private GameMapper gameMapper;

    public List<Game> findAll(){
        List<Game> gameList=gameMapper.findAll();
        return gameList;}

    public Game findGame(Integer id){
        Game game=gameMapper.findGame(id);
        return game;}

    public List<Game> seekName(String name){
        List<Game> gameList=gameMapper.seekName(name);
        return gameList;}

    public Boolean addGame(Game game){
        gameMapper.addGame(game);
        return true;}

    public Boolean updateGame(Game game){
        gameMapper.updateGame(game);
        return true;}

    public Boolean deleteGame(Game game){
        gameMapper.deleteGame(game);
        return true;}

    public List<Game> findPageSize(String type,String name){
        List<Game> gameList=gameMapper.findPageSize(type, name);
        return gameList;}

    public List<Game> findGameTypePage( int size , int page,String gameType,String name){
        List<Game> gameList=gameMapper.findGameTypePage(size, page, gameType, name);
        return gameList;}

    public List<Game> findGameTypePageZero( int size , int page,String gameType,Boolean bool){
        List<Game> gameList=gameMapper.findGameTypePageZero(size, page, gameType,bool);
        return gameList;}

    public List<Game> findAllZero(){
        List<Game> gameList=gameMapper.findAllZero();
        return gameList;}

    public List<Game> seekNameZero(String Name){
        List<Game> gameList=gameMapper.seekNameZero(Name);
        return gameList;}

    public List<Game> orderBy(int size, int page, String gameType,Boolean bool){
        List<Game> gameList=gameMapper.orderBy(size, page, gameType, bool);
        return gameList;}
}
