package com.example.newgameshop.serviceImpl;

import com.gameshop.entity.Indent;
import com.gameshop.mapper.IndentMapper;
import com.gameshop.service.IndentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IndentServiceImpl implements IndentService {
   private IndentMapper indentMapper;
   public IndentMapper getIndentMapper(){ return indentMapper; }
   @Autowired
   public void setIndentMapper(IndentMapper indentMapper){ this.indentMapper=indentMapper; }
    public Indent findOrder(Integer id) { return indentMapper.findOrder(id); }
    public List<Indent> findUserId(Integer id) { return indentMapper.findUserId(id); }
    public void addIndent(Indent indent) { indentMapper.addIndent(indent); }
    public void deleteIndent(Indent indent) { indentMapper.deleteIndent(indent); }
    public void updateIndent(Indent indent) { indentMapper.updateIndent(indent); }

    @Override
    public List<Indent> identPage(Integer size, Integer page, Integer userId) {
        return indentMapper.indentPage(size,(page-1)*size,userId);
    }
}
