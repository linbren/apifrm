git remote rm origin
git remote add origin git@github.com:linbren/apifrm.git
git pull origin master --allow-unrelated-histories
git status
git add filename
git commit -m ""
git push -u origin master
git push origin master
git log a.txt
git reset -hard HEAD a.txt
git pull

  git config --global user.email "you@example.com"
  git config --global user.name "Your Name"

1、jstl版本冲突会导致tomcat插件启不来。
创建项目时默认的glassfish，改为javax.servlet jstl 1.2
2、jetty启动时报annotations超时，改运行命令如下：
jetty:run -Dorg.eclipse.jetty.annotations.maxWait=120


http://localhost:8091/api/login.do?getToken&userCode=admin&userPass=E10ADC3949BA59ABBE56E057F20F883E

