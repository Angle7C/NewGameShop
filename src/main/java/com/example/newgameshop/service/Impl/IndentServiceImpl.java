package com.example.newgameshop.service.Impl;

import com.example.newgameshop.entity.Indent;
import com.example.newgameshop.mapper.IndentMapper;
import com.example.newgameshop.service.IndentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IndentServiceImpl implements IndentService {
    @Resource
    private IndentMapper indentMapper;

    public Indent findOrder(Integer id){
        Indent indent=findOrder(id);
        return indent;}

    public List<Indent> findUserId(Integer id){
        List<Indent> indentList=findUserId(id);
        return indentList;}

    public Boolean addIndent(Indent indent){
        indentMapper.addIndent(indent);
        return false;}

    public Boolean deleteIndent(Indent indent){
        indentMapper.deleteIndent(indent);
        return false;}

    public Boolean updateIndent(Indent indent){
        indentMapper.updateIndent(indent);
        return false;}

    public List<Indent> identPage(Integer size,Integer page,Integer userId){
        List<Indent> indentList=indentMapper.indentPage(size, page, userId);
        return indentList;}
}
