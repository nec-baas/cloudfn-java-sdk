CloudFunctions Java テストフレームワーク
====================================

モバイルバックエンド基盤 Cloud Functions Javaロジック
のテストフレームワークです。

Cloud Functions の Mock を作成し、ダミー Context を作成して
Functions を呼び出してテストを行うことが可能です。

プロジェクトへの組み込み手順
----------------------------

プロジェクトへのテストフレームワークの組み込み手順は以下の通りです。

プロジェクトの pom.xml の dependencies に以下の行を追加してください。
(バージョンは適宜変更してください。)

       <dependency>
            <groupId>com.nec.baas.cloudfn.sdk</groupId>
            <artifactId>cloudfn-test-framework</artifactId>
            <version>7.0.0</version>
            <scope>test</scope>
       </dependency>

使用方法
--------

サンプルが src/test/java/com/nec/baas/cloudfn/sdk/test/CloudfnMockTest.java にあります。
対象となる Function のサンプルは同ディレクトリの TestFunction.java です。

最初に CloudFunctions クラスインスタンを生成します。

        CloudFnMock cloudFnMock = new CloudFnMock();

NebulaContext のダミーである TestNebulaContext を生成して mock に設定します。
実サーバと通信する必要がある場合は、適切なパラメータを設定してください。

        TestNebulaContext nebulaContext = new TestNebulaContext()
                .tenant("tenant1")
                .appId("app1")
                .appKey("appkey1")
                .baseUri("https://baas.example.com/api");
        cloudFnMock.setNebulaContext(nebulaContext);

テストを実行するときは call を呼び出します。
第1引数に対象 Function の class、第2引数に handler名、第3引数に event を
指定します。event には以下のいずれかの型を指定できます。

* String : 文字列
* Map<String, Object> : JSON を Map に変ししたもの
* byte[] : バイナリ
* java.nio.Buffer : バイナリ
* ByteArrayInputStream : バイナリ

実行結果は TestContext として返り値に返却されます。

    TestContext ctx = cloudFnMock.call(TestFunction.class, "sayHello", "dummy");

Function の終了を待つ場合は ctx.waitFor() を呼び出します。
Function が成功したかどうかは ctx.isSucceeded(), ctx.isFailed() で調べられます。

