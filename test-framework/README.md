CloudFunctions Java テストフレームワーク
====================================

モバイルバックエンド基盤 Cloud Functions Javaロジック
のテストフレームワークです。

Cloud Functions の Mock を作成し、ダミー Context を作成して
Functions を呼び出してテストを行うことが可能です。

プロジェクトへの組み込み手順
----------------------------

プロジェクトへのテストフレームワークの組み込み手順は以下の通りです。

まず、以下の手順でビルドを行ってください。

    $ mvn package

注: Eclipse でビルドする場合は lombok plugin の組み込みが必要です(後述)

target ディレクトリに cloudfn-test-framework-x.x.x.jar
が生成されますので、これをプロジェクトに組み込んでください。

具体的には、プロジェクトの libs ディレクトリに上記 JAR ファイルを追加し、
pom.xml の dependencies に以下の行を追加してください。
(バージョンは適宜変更してください。)

       <dependency>
            <!-- モバイルバックエンド基盤クラウドファンクションのSDK(必須) -->
            <groupId>com.nec.baas.cloudfn.sdk</groupId>
            <artifactId>cloudfn-test-framework</artifactId>
            <version>7.0.0</version>
            <scope>test</scope>
            <systemPath>${project.basedir}/libs/cloudfn-test-framework-7.0.0.jar</systemPath>
       </dependency>

もしくは、src/main/java 以下にある java ファイルをそのままプロジェクトの
テストソースに組み込んでいただいても構いません。

また、以下の dependency の追加が必要です。記述方法は本テストフレームワークの pom.xml
を参照してください。

* okhttp3

IDE でビルドする場合の注意事項
-------------------------------

IDE でビルドする場合は、IDE 側に Lombok プラグインを導入する必要があります。

Eclipse の場合は https://projectlombok.org/setup/eclipse を参照してインストールを
行ってください。lombok.jar をダブルクリックし、インストールする IDE を選択して
「Install/Update」をクリックすることでインストールされます。

Eclipse 以外の IDE の場合は、https://projectlombok.org/setup/overview の手順を
参照ください。

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

