package com.example.newgameshop.mapper;



import com.example.newgameshop.entity.Indent;

import java.util.List;

public interface IndentMapper {
    public Indent findOrder(Integer id);
    public List<Indent> findUserId(Integer id);
    public void addIndent(Indent indent);
    public void deleteIndent(Indent indent);
    public void updateIndent(Indent indent);
    public List<Indent> indentPage(Integer size,Integer page,Integer userId);

}

