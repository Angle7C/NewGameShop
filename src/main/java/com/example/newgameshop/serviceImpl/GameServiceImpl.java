package com.example.newgameshop.serviceImpl;

import com.gameshop.entity.Game;
import com.gameshop.entity.Page;
import com.gameshop.mapper.GameMapper;
import com.gameshop.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameServiceImpl implements GameService {

    private GameMapper gameMapper;

    public GameMapper getGameMapper(){ return gameMapper; }
    @Autowired
    public void setGameMapper(GameMapper gameMapper){ this.gameMapper=gameMapper; }
    public List<Game> findAll() { return  gameMapper.findAll(); }
    public Game findGame(Integer id) { return gameMapper.findGame(id); }
    public List<Game> seekName(String name) { return gameMapper.seekName("%"+name+"%"); }
    public void addGame(Game game) { gameMapper.addGame(game); }
    public void updateGame(Game game) { gameMapper.updateGame(game); }
    public void deleteGame(Game game) { gameMapper.deleteGame(game); }
    public List<Game> findPageSize(String bool,String name) {
        return gameMapper.findPageSize("".equals(bool)?null:bool,"".equals(name)?null:"%"+name+"%");

    }
    public List<Game> findGameTypePage(int size, int page, String gameType,String name) {
        return gameMapper.findGameTypePage(size,(page-1)*size,gameType, name!=null?"%"+name+"%":null );
    }

    @Override
    public List<Game> findGameTypePageZero(int size, int page, String gameType) {
        return gameMapper.findGameTypePageZero(size,(page-1)*size,null,false);
    }

    public List<Game> findAllZero(){
        return gameMapper.findAllZero();
    }

    @Override
    public List<Game> seekNameZero(String name) {
        return gameMapper.seekNameZero("%"+name+"%");
    }

    @Override
    public List<Game> orderBy(int size, int page, String gameType, Boolean bool) {
        return gameMapper.orderBy(size,(page-1)*size,null,true);
    }
}
