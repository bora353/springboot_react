import React from "react";
import "./style.css";
import { BoardListItem } from "types/interface";
import { useNavigate } from "react-router-dom";
import defaultProfileImage from "assets/image/gaeun.jpg";

interface Props {
  boardListItem: BoardListItem;
}

//            component: Board List Item 컴포넌트              //
export default function BoardItem({ boardListItem }: Props) {
  //             properties                    //
  const { boardNumber, title, content, boardTitleImage } = boardListItem;
  const { favoriteCount, commentCount, viewCount } = boardListItem;
  const { writeDatetime, writerNickname, writerProfileImage } = boardListItem;

  //            function: 네비게이트 함수                //
  //const navigator = useNavigate();

  //            event handler: 게시물 아이템 클리기 이벤트 처리 함수                //
  const onClickHandler = () => {
    //navigator(boardNumber);
  };

  //            render: Board List Item 컴포넌트 렌더링             //
  return (
    <div className="board-list-item" onClick={onClickHandler}>
      <div className="board-list-item-main-box">
        <div className="board-list-item-top">
          <div className="board-list-item-profile-box">
            <div
              className="board-list-item-profile-image"
              style={{
                // backgroundImage: `url(https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9aeHFWXHfDhJ_xzPneJlEsKVdu-tZ5vGoow&usqp=CAU)`,
                backgroundImage: `url(${
                  writerProfileImage ? writerProfileImage : defaultProfileImage
                })`,
              }}
            ></div>
          </div>
          <div className="board-list-item-write-box">
            <div className="board-list-item-nickname">{"보라도리"}</div>
            <div className="board-list-item-write-date">{"2023.10.04"}</div>
          </div>
        </div>
        <div className="board-list-item-middle">
          <div className="board-list-item-title">{title}</div>
          <div className="board-list-item-content">{content}</div>
        </div>
        <div className="board-list-item-bottom">
          <div className="board-list-item-counts">{`댓글 ${favoriteCount}  좋아요 ${commentCount}  조회수 ${viewCount}`}</div>
        </div>
      </div>

      {boardTitleImage !== null && (
        <div className="board-list-item-image-box">
          <div
            className="board-list-item-image"
            style={{
              // backgroundImage: `url(data:image/png;base64,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)`,
              backgroundImage: `url(${boardTitleImage})`,
            }}
          ></div>
        </div>
      )}
    </div>
  );
}
