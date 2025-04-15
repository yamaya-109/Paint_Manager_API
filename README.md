# Paint_Manager_api

#前書き
  プラモの塗料を買いに行くもどの塗料だったか忘れたり、新たにプラモ買っても家に塗料あったかなぁになったりする事が多く、面倒だったので、APIを作成しました。

##  概要
塗料の名前、種類、色（RGB）、カテゴリ、所持量をユーザーごとに管理するAPIです。  
同じ塗料の自動統合、インポート/エクスポート、類似色検索などに対応しています。

## 技術スタック
- Java 17
- Spring Boot
- Spring Security（JWT認証）
- PostgreSQL
- Jackson（JSON変換）

## 環境設定
spring.datasource.url=jdbc:postgresql://localhost:5432/paintdb

spring.datasource.username=your_user

spring.datasource.password=your_password

jwt.secret=your_jwt_secret

##
テスト用コマンド一覧(DOS窓で叩いてね)

✅ ログイン
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"username\":\"ユーザーネーム\",\"password\":\"パスワード\"}"

✅ 全件取得
curl http://localhost:8080/api/paint/all -H "Authorization: Bearer YOUR_TOKEN_HERE"

✅ 塗料の追加
curl -X POST http://localhost:8080/api/paint/add -H "Authorization: Bearer YOUR_TOKEN_HERE" -H "Content-Type: application/json" -d "{\"name\":\"ホワイト\",\"type\":\"アクリル\",\"category\":\"白系\",\"red\":255,\"green\":255,\"blue\":255,\"amount\":10}"

✅ 名前で検索（完全一致）
curl --get "http://localhost:8080/api/paint/search/name" -H "Authorization: Bearer YOUR_TOKEN_HERE" --data-urlencode "name=ホワイト"

✅ 名前で検索（部分一致）
curl --get "http://localhost:8080/api/paint/search/name_c" -H "Authorization: Bearer YOUR_TOKEN_HERE" --data-urlencode "name=ホワ"

✅ カテゴリで検索
curl --get "http://localhost:8080/api/paint/search/category" -H "Authorization: Bearer YOUR_TOKEN_HERE" --data-urlencode "category=白系"

✅ 類似色検索
curl --get "http://localhost:8080/api/paint/similar" -H "Authorization: Bearer YOUR_TOKEN_HERE" --data-urlencode "r=255" --data-urlencode "g=255" --data-urlencode "b=255" --data-urlencode "margin=10"

✅ 名前指定で削除
curl -X DELETE "http://localhost:8080/api/paint/delete/name" -H "Authorization: Bearer YOUR_TOKEN_HERE" --data-urlencode "name=ホワイト"

✅ 全件削除
curl -X DELETE http://localhost:8080/api/paint/delete/all -H "Authorization: Bearer YOUR_TOKEN_HERE"

✅ 使用量を ID で減らす（例：ID=1、-3ml 減らす）
curl -X PUT "http://localhost:8080/api/paint/decrease/id" -H "Authorization: Bearer YOUR_TOKEN_HERE" --data-urlencode "id=1" --data-urlencode "amount=-3"


##今後の展望

  ・このAPIを使うWebアプリ開発
  
    ・ログイン機能はあるけどトークン毎回ペーストするのが面倒なのでアプリ側で吸収したい
    
    ・1個1個集塗料のAPIを叩くのではなく、アプリ側でまとめて叩くような感じにしてまとめて表示したい
    
    ・例えばタミヤエナメルの「X-12 ゴールドリーフ」「XF-6 コッパー」はエナメルじゃなくてラッカー系だったり塗料名と系統が異なるするのでそこをアプリ側で吸収したい
    
    ・旧版水性ホビーカラー(確かAQUEOUSと大きい文字で書かれてない奴)の上にアクリジョン塗ると割れる問題についてまとめて要求したり追加したりしている場合は、確認ウィンドウを表示してアプリ側で吸収したい
    
    ・混ぜられる塗料、混ぜられない塗料をわかりやすくアプリ側で吸収したい
