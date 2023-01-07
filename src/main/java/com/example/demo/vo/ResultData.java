package com.example.demo.vo;

import lombok.Getter;

public class ResultData {

  @Getter
    private String resultCode;//결과코드

  @Getter
  private String msg;//메시지

  @Getter
  private Object data1;//데이터

  private ResultData(){ //생성자

  }

  public static ResultData from(String resultCode, String msg){
    return from(resultCode, msg, null);
  }

  public static ResultData from (String resultCode, String msg, Object data1){
    ResultData rd = new ResultData();
    rd.resultCode = resultCode;
    rd.msg = msg;
    rd.data1 = data1;

    return rd;
  }



  public boolean isSuccess() {
    return resultCode.startsWith("S-");

  }


  public boolean isFail(){
    return isSuccess() == false; //성공이 아니라면~!!

  }

  public static ResultData newData(ResultData joinRd, Object newData){
    return from(joinRd.getResultCode(), joinRd.getMsg(), newData);
  }
}
