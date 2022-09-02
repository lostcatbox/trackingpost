package com.lostcatbox.trackingpost.service;

import com.lostcatbox.trackingpost.domain.PostDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CUPostProvider implements PostProvider{
    public PostDto get(String postNumber){
        try {
            Document doc = Jsoup.connect("https://www.doortodoor.co.kr/jsp/cmn/TrackingCUpost.jsp?pTdNo="+postNumber).get();
            Elements elements = doc.select("table[class=tepTb mt10]"); //해당 class의 이름 테이블 검색
            Elements table = elements.get(0).select("tbody").select("tr");
            Elements rows = table.get(table.size() - 1).select("td");

            Elements elements2 = doc.select("table[class=tepTb mt20]"); //해당 class의 이름 테이블 검색
            Elements table2 = elements2.get(0).select("tr");
            Elements rows2 = table2.get(table2.size() - 1).select("td"); //updated_date, message, 담당집배점, 상대집배점

            PostDto postDto = PostDto.builder()
                    .postNumber(rows.get(0).text())
                    .sender(rows.get(1).text())
                    .receiver(rows.get(2).text())
                    .contentType(rows.get(3).text())
                    .statusData(rows2.get(0).text())
                    .message(rows2.get(1).text())
                    .location(getlocationinfo(rows2)) //location이동중인지, 정착인지 구별후 맞는 location반환
                    .build();

            return postDto;
        } catch (IOException e){
            e.printStackTrace(); //?? 네트워크 오류시 처리방법 정리하기
            return null; //null로 처리?
        }

    }
    public boolean isSupport(PostCompanyEnum postcompany) {
        if (postcompany == PostCompanyEnum.CU){
            return true;
        }else{
            return false;
        }
    }

    private String getlocationinfo(Elements rows) {
        String location;
        if(rows.get(3).text().isEmpty()){
            location = rows.get(2).text();
        } else {
            location = rows.get(2).text()+ "->" + rows.get(3).text();
        }
        return location;
    }
}
