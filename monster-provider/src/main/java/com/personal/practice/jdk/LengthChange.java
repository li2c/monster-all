package com.personal.practice.jdk;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LengthChange {

    public static void test(Object... a){
        for (Object i:a){
            System.out.println((Integer) i);
        }
    }

//    public static void main(String[] args) {
//        List<Integer> ls=new ArrayList<>();
//        ls.add(1);
//        ls.add(2);
//        test(ls);
//        Integer[] s=new Integer[]{1,2};
//        test(s);
//    }

    public static void main(String[] args) {

        AppMomentDto appMomentDto=new AppMomentDto();
        appMomentDto.setTxt("test");
        AppMomentDto.Image image=new AppMomentDto.Image();
        image.setCheck(1);
        image.setHeight(1);
        image.setImgUrl("test");
        image.setWidth(12);
        List<AppMomentDto.Image> imgList=new ArrayList();
        imgList.add(image);
        appMomentDto.setImgList(imgList);
        System.out.println(JSONObject.toJSONString(appMomentDto));
    }
}
