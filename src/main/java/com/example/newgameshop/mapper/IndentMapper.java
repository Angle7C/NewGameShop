package com.example.newgameshop.mapper;

import com.gameshop.entity.Indent;
import org.springframework.stereotype.Component;

import java.util.List;

public interface IndentMapper {
    public Indent findOrder(Integer id);
    public List<Indent> findUserId(Integer id);
    public void addIndent(Indent indent);
    public void deleteIndent(Indent indent);
    public void updateIndent(Indent indent);
    public List<Indent> indentPage(Integer size,Integer page,Integer userId);

}

