# ChatRoom_front
## 操作
- 使用jar檔開啟時，請先開啟chatAPP.jar
- 確認有連上後，再執行ChatRoom.jar  
- 參考指令
  - `java -jar [filename.jar]`
  - 請使用java17
- 預設為不會自動更新，避免資源消耗過多(AWS要錢)
- 若要啟用自動更新功能，建議透過code更改PageController中的
  - `update_immediate = false`
  - 改為
  - `update_immediate = true`
  - 再編譯
- 或者使用按鈕開啟該功能也行
## ChatGPT功能
- 請記得申請API key，並綁定信用卡後才可以使用ChatGPT api
  - 沒有綁定信用卡的話無法使用chat recommand reply功能是正常的!
- 這邊不免費提供該API key避免信用卡大爆炸
- 使用model: `gpt-3.5-turbo-0613`
