ビルド手順
==========

本テストフレームワークのビルド手順は以下の通りです。

ビルド手順
----------

    $ mvn package

注: Eclipse でビルドする場合は lombok plugin の組み込みが必要です(後述)

target ディレクトリに cloudfn-test-framework-x.x.x.jar が生成されます。

IDE でビルドする場合の注意事項
-------------------------------

IDE でビルドする場合は、IDE 側に Lombok プラグインを導入する必要があります。

Eclipse の場合は https://projectlombok.org/setup/eclipse を参照してインストールを
行ってください。lombok.jar をダブルクリックし、インストールする IDE を選択して
「Install/Update」をクリックすることでインストールされます。

Eclipse 以外の IDE の場合は、https://projectlombok.org/setup/overview の手順を
参照ください。

JARファイルの使用方法
---------------------

本テストフレームワークの JAR ファイルを使用する方法は以下の通りです。

まずプロジェクトの libs ディレクトリに上記 JAR ファイルを追加してください。

次に、pom.xml の dependencies に以下の行を追加してください。
(バージョンは適宜変更してください。)

       <dependency>
            <!-- モバイルバックエンド基盤クラウドファンクションのSDK(必須) -->
            <groupId>com.nec.baas.cloudfn.sdk</groupId>
            <artifactId>cloudfn-test-framework</artifactId>
            <version>7.0.0</version>
            <scope>test</scope>
            <systemPath>${project.basedir}/libs/cloudfn-test-framework-7.0.0.jar</systemPath>
       </dependency>

また、以下の dependency の追加が必要です。記述方法は本テストフレームワークの pom.xml
を参照してください。

* okhttp3
