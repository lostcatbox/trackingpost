package com.example.externalpost.service.provider;

import com.example.trackingpostcore.domain.PostDto;
import com.example.trackingpostcore.domain.PostCompanyEnum;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class CUPostProvider implements PostProvider {
    public PostDto get(String postNumber) {
        try {
            Document doc = Jsoup.connect("https://www.doortodoor.co.kr/jsp/cmn/TrackingCUpost.jsp?pTdNo=" + postNumber).get();
            Elements test = doc.select("table[class=tepTb mt20]").get(0).select("tr").get(1).select("td");
            ; //해당 class의 이름 테이블 검색
            if (test.text().equals("검색된 결과가 없습니다.")) { //정말없거나, 아니면 CU편의점끼리 택배임
                try {
                    PostDto cuToCUInfo = getCUToCUInfo(postNumber);
                    return cuToCUInfo;
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e) {//크롤링 중 get범위벗어남 실패처리
                    e.printStackTrace();
                    return null;
                }
            } else{
                PostDto postDto = getCommonPostInfo(doc); //기존 크롤링 이어서 진행
                return postDto;
                }
        } catch (IOException e) {
            e.printStackTrace();
            return null; //null로 처리?
        } catch (IndexOutOfBoundsException e) { //크롤링 중 get범위벗어남 실패처리
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private PostDto getCommonPostInfo(Document doc) {
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
                .status("CU") //CU는 택배 상태 정보없음
                .message(rows2.get(1).text())
                .location(getlocationinfo(rows2)) //location이동중인지, 정착인지 구별후 맞는 location반환
                .build();
        return postDto;
    }

    private PostDto getCUToCUInfo(String postNumber) throws IOException,IndexOutOfBoundsException {
        Document doc = Jsoup.connect("https://www.cupost.co.kr/postbox/delivery/localResult.cupost")
                .requestBody("invoice_no=" + postNumber)
                .post();
        String s = doc.toString();
        Elements elements = doc.getElementById("gotoMainContents").select("table[class=tableType1]");
        Elements td = elements.get(0).select("tr").select("td");//운송장번호
        String contentType = td.get(1).text(); //상품정보: 의류
        String sender = td.get(4).text() + " 보내는점포: " + td.get(6).text(); //보내는분
        String receiver = td.get(5).text() + " 받는점포: " + td.get(7).text(); //받는분
        Elements recentPath = elements.get(2).select("tr").get(elements.get(2).select("tr").size() - 1).select("td");//운송장번호 처리일시	현재위치 배송상태
        String statusData = recentPath.get(0).text();//처리일시
        String location = recentPath.get(1).text();//현재위치
        String message = recentPath.get(2).text();//배송상태
        PostDto postDto = PostDto.builder()
                .postNumber(postNumber)
                .sender(sender)
                .receiver(receiver)
                .contentType(contentType)
                .statusData(statusData)
                .status("CU") //CU는 택배 상태 정보없음
                .message(message)
                .location(location) //location이동중인지, 정착인지 구별후 맞는 location반환
                .build();
        return postDto;
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
