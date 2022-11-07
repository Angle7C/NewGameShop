package com.example.newgameshop.service.impl;

import com.example.newgameshop.entity.Game;
import com.example.newgameshop.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {
    @Override
    public List<Game> findAll() {
        return null;
    }

    @Override
    public Game findGame(Integer id) {
        return null;
    }

    @Override
    public List<Game> seekName(String name) {
        return null;
    }

    @Override
    public void addGame(Game game) {

    }

    @Override
    public void updateGame(Game game) {

    }

    @Override
    public void deleteGame(Game game) {

    }

    @Override
    public List<Game> findPageSize(String type, String name) {
        return null;
    }

    @Override
    public List<Game> findGameTypePage(int size, int page, String gameType, String name) {
        return null;
    }

    @Override
    public List<Game> findGameTypePageZero(int size, int page, String gameType) {
        return null;
    }

    @Override
    public List<Game> findAllZero() {
        return null;
    }

    @Override
    public List<Game> seekNameZero(String Name) {
        return null;
    }

    @Override
    public List<Game> orderBy(int size, int page, String gameType, Boolean bool) {
        return null;
    }
}
