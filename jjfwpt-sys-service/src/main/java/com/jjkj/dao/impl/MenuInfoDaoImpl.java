package com.jjkj.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jjkj.dao.MenuInfoDao;
import com.jjkj.dao.mapper.TMenuInfoMapper;
import com.jjkj.exception.BusinessException;
import com.jjkj.model.TMenuInfo;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MenuInfoDaoImpl extends ServiceImpl<TMenuInfoMapper, TMenuInfo> implements MenuInfoDao {
    @Override
    public TMenuInfo findMenuByContion(Map condition) {
        QueryWrapper queryWrapper=new QueryWrapper();
        condition.forEach((f,v)->queryWrapper.eq(f,v));
        TMenuInfo menuInfo=this.getOne(queryWrapper,true);
        return menuInfo;
    }

    @Override
    public List<TMenuInfo> findMenuListByContion(Map condition) {
        QueryWrapper queryWrapper=new QueryWrapper();
        condition.forEach((f,v)->queryWrapper.eq(f,v));
        List<TMenuInfo> tMenuInfos=this.list(queryWrapper);
        return tMenuInfos;
    }
    //只有三级菜单
    @Override
    public List<TMenuInfo> findMenuTreeSortList(Map condition){
        QueryWrapper queryWrapper=new QueryWrapper();
        condition.forEach((f,v)->queryWrapper.eq(f,v));
        queryWrapper.last("order by order_num");
        List<TMenuInfo> tMenuInfos=this.list(queryWrapper);
        List <TMenuInfo> root =new ArrayList<>();
        for(TMenuInfo tMenuInfo:tMenuInfos){
            if(tMenuInfo.getType().equals("1")){
                root.add(tMenuInfo);
            }
        }
        root= treeMenu(root,tMenuInfos);
        Collections.sort(root,new Comparator<TMenuInfo>(){
            @Override
            public int compare(TMenuInfo o1, TMenuInfo o2) {
                try{
                    if(o1.getOrderNum()>o2.getOrderNum()){
                        return 1;
                    }else{
                        return -1;
                    }
                }catch (Exception e){
                    return 0;
                }
            }
        });
        List<TMenuInfo> newList=new ArrayList<>();
       // newList=treeMenuToList(newList,root);
        return root;
    }

    //相当于按照层级排序
    public List<TMenuInfo> treeMenuToList(List<TMenuInfo> newList,List<TMenuInfo> list){
        list.forEach(item->{
            newList.add(item);
            if(item.getChildren()!=null){
                treeMenuToList(newList,item.getChildren());
            }
        });
        return newList;
    }

    public List<TMenuInfo> treeMenu(List<TMenuInfo> root,List<TMenuInfo> menuList){
        root.forEach((item)->{
            menuList.forEach(item_->{
                if(item.getId().equals(item_.getPid())){
                    item.getChildren().add(item_);
                }
            });
            if(item.getChildren()!=null){
                treeMenu(item.getChildren(),menuList);
            }
        });
        return root;
    }
    @Override
    public Boolean addMenu(TMenuInfo menuInfo) {
        return this.save(menuInfo);
    }

    @Override
    public List<TMenuInfo> findMenuTreeView(Long userId){
        List<TMenuInfo> tMenuInfos=baseMapper.selectRoleMenus(userId);
        List <TMenuInfo> root =new ArrayList<>();
        for(TMenuInfo tMenuInfo:tMenuInfos){
            if(tMenuInfo.getType().equals("1")){
                root.add(tMenuInfo);
            }
        }
        root= treeMenu(root,tMenuInfos);
        Collections.sort(root,new Comparator<TMenuInfo>(){
            @Override
            public int compare(TMenuInfo o1, TMenuInfo o2) {
                try{
                    if(o1.getOrderNum()>o2.getOrderNum()){
                        return 1;
                    }else{
                        return -1;
                    }
                }catch (Exception e){
                    return 0;
                }
            }
        });
        return root;
    }

    @Override
    public List<TMenuInfo> findAllMenuTreeView() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("state","0");
        List<TMenuInfo> tMenuInfos = this.list(queryWrapper);

        List <TMenuInfo> root =new ArrayList<>();
        for(TMenuInfo tMenuInfo:tMenuInfos){
            if(tMenuInfo.getType().equals("1")){
                root.add(tMenuInfo);
            }
        }
        root= treeMenu(root,tMenuInfos);
        Collections.sort(root,new Comparator<TMenuInfo>(){
            @Override
            public int compare(TMenuInfo o1, TMenuInfo o2) {
                try{
                    if(o1.getOrderNum()>o2.getOrderNum()){
                        return 1;
                    }else{
                        return -1;
                    }
                }catch (Exception e){
                    return 0;
                }
            }
        });
        return root;
    }

    @Override
    public Boolean updateMenu(TMenuInfo menuInfo) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",menuInfo.getId());
        return this.update(menuInfo,queryWrapper);
    }

    @Override
    public Boolean delMenu(String[] ids) {
        return  this.removeByIds(Arrays.asList(ids));
    }
}
