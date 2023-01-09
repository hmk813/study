package com.example.demo.vo;

import lombok.Getter;

public class ResultData<DT> {

  @Getter
    private String resultCode;//결과코드

  @Getter
  private String msg;//메시지

  @Getter
  private DT data1;//데이터

  @Getter
  private String data1Name;

  private ResultData(){ //생성자

  }

  public static ResultData from(String resultCode, String msg){
    return from(resultCode, msg, null, null);
  }

  public static <DT> ResultData<DT> from (String resultCode, String msg, String data1Name, DT data1){
    ResultData<DT> rd = new ResultData<DT>();
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

  public static <DT> ResultData<DT> newData(ResultData oldRd, String data1Name, DT data1){
    return from(oldRd.getResultCode(), oldRd.getMsg(), data1Name, data1);
  }
}
