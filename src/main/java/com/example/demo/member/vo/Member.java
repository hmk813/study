package com.example.demo.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  private int id;
  private String regDate;
  private String updateDate;
  private String loginId;
  private String loginPw;
  private int authLevel;
  private String name;
  private String nickname;
  private String cellphoneNo;
  private String email;
  private boolean delStatus;//delStatus는 boolean으로 해야된다
  private String delDate;

}
