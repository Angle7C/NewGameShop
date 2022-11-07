package com.example.newgameshop.service;

import com.gameshop.entity.Indent;

import java.util.List;

public interface IndentService {
    public Indent findOrder(Integer id);
    public List<Indent> findUserId(Integer id);
    public void addIndent(Indent indent);
    public void deleteIndent(Indent indent);
    public void updateIndent(Indent indent);
    public List<Indent> identPage(Integer size,Integer page,Integer userId);
}
