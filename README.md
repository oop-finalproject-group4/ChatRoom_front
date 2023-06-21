# ChatRoom_front
## 專案管理
- 使用`maven`作為專案的package以及依賴管理工具
- 要自己編譯成jar檔案的話需要使用maven build
- 環境使用
  - java 17
  - java sdk 17
  - javafx 16
  - javafx sdk 16
  - 皆寫於pom.xml之中
## 操作
- jar檔案：存於google drive
  - https://drive.google.com/drive/folders/1BBqQE1fB3WuUw2usb5OK_pjGB2nwXPox?usp=drive_link
  - 使用jar檔開啟時，請先開啟chatAPP.jar
  - 確認有連上後，再執行ChatRoom.jar  
  - 參考指令
    - `java -jar [filename.jar]`
    - 請使用java17

- 預設為不會自動更新，避免資源消耗過多(AWS要錢)
- 若要啟用自動更新功能，建議透過code更改PageController中的
  - @`line 29`
```
update_immediate = false
```
  - 改為
```
update_immediate = true
```
  - 再編譯
- 或者使用按鈕開啟該功能也行
## ChatGPT功能
- 請記得申請API key，並綁定信用卡後才可以使用ChatGPT api
  - 沒有綁定信用卡的話無法使用chat recommand reply功能是正常的!
- 這邊不免費提供該API key避免信用卡大爆炸
- 使用model: `gpt-3.5-turbo-0613`
