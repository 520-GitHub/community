##盗墓笔记
##还未进行push，amend进行追加，no-edit不修改备注

git commit --amend --no-edit

##资料
https://spring.io/guides
https://spring.io/guides/gs/serving-web-content/
https://elasticsearch.cn/explore
https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys

##工具
https://git-scm.com/download


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