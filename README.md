# ChatRoom_front
## 操作
- 使用jar檔開啟時，請先開啟chatAPP.jar
- 確認有連上後，再執行ChatRoom.jar
`java -jar [filename.jar]`
- 請使用java17
- 預設為不會自動更新，避免資源消耗過多(AWS要錢)
- 若要啟用自動更新功能，建議透過code更改PageController中的
  - `update_immediate = false`
  - 改為
  - `update_immediate = true`
  - 再編譯
- 或者使用按鈕開啟該功能也行
