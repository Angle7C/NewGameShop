package com.example.newgameshop.service;



import com.example.newgameshop.entity.Indent;

import java.util.List;

public interface IndentService {
    default Indent findOrder(Integer id){return null;}
    default List<Indent> findUserId(Integer id){return null;}
    default Boolean addIndent(Indent indent){return false;}
    default Boolean deleteIndent(Indent indent){return false;}
    default Boolean updateIndent(Indent indent){return false;}
    default List<Indent> identPage(Integer size,Integer page,Integer userId){return null;}
}
