##盗墓笔记
##还未进行push，amend进行追加，no-edit不修改备注

git commit --amend --no-edit

##资料
[Spring 文档](https://spring.io/guides)<br>
[Spring Web](https://spring.io/guides/gs/serving-web-content/)<br>
[es](https://elasticsearch.cn/explore)<br>
[GitHub deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)<br>
[Bootstrap](https://v3.bootcss.com/getting-started/)<br>
[GitHub OAuth](https://developer.github.com/apps/building-aouth-apps/creating-an-oauth-app/)
[Spring](https://docs.spring.io)<br>
[菜鸟教程](https://www.runoob.com/musql/mysql-insert-query.html)<br>
[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)<br>

##工具
[Git](https://git-scm.com/download)<br>
[Visual Paradigm](https://www.visual-paradigm.com)<br>
[Flyway](https://flywaydb.org/getstarted/firststeps/maven)<br>
[lombok](https://www.projectlombok.org)<br>
[octotree](https://www.octotree.io/)<br>


##代码：
##在命令行上创建新的存储库
echo "# community" >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin git@github.com:520-GitHub/community.git
git push -u origin master
##从命令行推送现有存储库
git remote add origin git@github.com:520-GitHub/community.git
git push -u origin master
##从其他存储库导入代码
You can initialize this repository with code from a Subversion, Mercurial, or TFS project.

```bash
删除数据库: rm ~/community.*
创建数据库：mvn flyway:migrate
将generatorConfig.xml中的配置进行覆盖执行创建脚本信息：
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```

```IDEA
自动补充返回值：ctrl+alt+v
:ctrl+alt+L

```