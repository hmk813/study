package com.example.demo.service;

import com.example.demo.repository.BoardRepository;
import com.example.demo.vo.Board;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  private BoardRepository boardRepository;

  public BoardService(BoardRepository boardRepository){

    this.boardRepository = boardRepository;
  }

  public Board getBoardById(int id) {
    return boardRepository.getBoardById(id);
  }
}
