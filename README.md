##盗墓笔记
##还未进行push，amend进行追加，no-edit不修改备注

git commit --amend --no-edit

##资料
[Spring 文档](https://spring.io/guides)
[Spring Web](https://spring.io/guides/gs/serving-web-content/)
[es](https://elasticsearch.cn/explore)
[GitHub deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[Bootstrap](https://v3.bootcss.com/getting-started/)
[GitHub Oauth](https://developer.github.com/apps/building-aouth-apps/creating-an-oauth-app/)


##工具
[Git](https://git-scm.com/download)
[VP](https://www.visual-paradigm.com)


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