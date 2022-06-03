# Jenkis

## What is jenkins and contineos devoplement/divivery/intergation  ?

- In software engineering, CI/CD or CICD is the combined practices of continuous integration and continuous delivery or continuous deployment. CI/CD bridges the gaps between development and operation activities and teams by enforcing automation in building, testing and deployment of applications

- Contineous intigration 
    - all the phase are automated plan code 
    - (Developer) devop code --> then we devloy on cloud (Deploy) 
    - Git (code face) --> Integration(Git/gitlab---> testing -->deploy)
-stages
    - Build     #we are autmoating all build test deploy features
    - test
    - deploy

## what is Jenkins (Java based application)
- Jenkins is an open source automation server. It helps automate the parts of software development related to building, testing, and deploying, facilitating continuous integration and continuous delivery. It is a server-based system that runs in servlet containers such as Apache Tomcat.

- Benifits
    - plugins #addon feature
        - Plugins are the primary means of enhancing the functionality of a Jenkins environment to suit organization- or user-specific needs. There are over a thousand different plugins which can be installed on a Jenkins controller and to integrate various build tools, cloud providers, analysis tools, and much more.
        - extension 
- Why to use jenkins 
    - Provides lot of feature like plugins also CI/CD 
## lets see how we use jenkins now 
### Creating a Jobs
- navigate -> New items -> 

- people 
    - all people
- build histroy
    - execcutions is noting but build 
    - we can build history
- Manage Jenkins 
    - We can see the path 
    - no of exicutors
        - how many jobs we can exicute paralley or in cup how many job we can perfom parallely
        - no core vs no of exicutors
    - labels 
        - labels are nothing but tags 
        - labels are used when we want to exicute it on perticular node or master
    - usage
        - use master as much you can 
        - need lable to exicute job on master
    - global tool ocnfig
        - we will use application here 
    - manage plugis 
    - manage node 
        - manage the slaves/node/agents
    -secuity
## In Jenkins we follow Master-Slave concept
- wher we have one master and multiple slaves
- we can also make multiple master as well for backup puorpose
- Jenkins is a single server (now we need to exicute our job)
- so we will exicute all jobs in different pc so that we get max spped for master

### Now lets create a job printing hello world
- now we are writing free style project
- accoring to project we will need plugin 
    - creating job 
        - Nevigate -> newiteam-> enter project name -> frestyle -> ok -> build(Exicute shell) -> echo "hello world" -> save
    - Now build job 
        - nevigate -> Build now -> select build histroy -> console o/p

### Why we need to create a job
    - when devopler push their code to repo we need to test and delpoy it using the job

### create a new job for pull the code form repo 
    - we will need git to be install on master server hence we are running all cmd on master```yum install git```
    - we will also need plugin 
        - nevigate -> manage jenkins -> manage plugins ->avilable -> git -> install without restart
        - we have many plugins 100 
    - we will need authenticate for cloning repo
    - so we need to keep key in jenkins 
    - workspace where we work we can see our repository is cloned
    - we can check the pulled repo on master server check```var/lib/jenkins/workspace/codepull```

### lets create the Master-Slave concept
    - so we can distriue the build to node1 
    - we can also use maven on different node2 
    - for sonarqube we will use node3
    - we can alos create the more than one master for backup perpose
    - launch the new instance on ec2 which will work as slave/node1
        - yum install git
        - yum install java-11-openjdk
    - here ssh will communicate between servre and node
    - nevigate to manage jneknis -> manage nodes and cloud 
    - install java-11-openjdk in node system for connection or keeping up the node
    - create a ssh-key pair on node side so we can connect through ssh 

### what is build trigger
    - we can set trigeer for below
        - trigger builds remotely (e.g., from scripts)
        - Build after other projects are built
        - Build periodically
        - Poll SCM

# Now we will create a pipeline in jenkins 
    - what is pipeline is nothing but no of task exicuted in sequence/parellel
    - Cloth washing example.
    - we will create diff stage 
        - PULL -> Build -> TEST -> DEPLOY 
        - postbuild action after build stage we will sent o/p to test.
## Now we will try to run our pipeline through gitlab repo
    - select the pipeline script form scm 
    - script path (pieplines/piepline.jdp)
    - jpd is only for declerative pipeline
- pipeline 
    - we perform ci/cd using th pipeline
    - written in GRROVY language also called as dsl(domain specific pipeline) langauge 
    - we use two types of pipeline
        - Declarative pipeline(new)
        ---

            pipeline {
            agent { docker { image 'maven:3.8.4-openjdk-11-slim' } }
            stages {
                stage('build') {
                    steps {
                        sh 'mvn --version'
                    }
                }
            }
        ...
        - scripted pipeline (old) 
            - here we will pull repo from our gitlab
        ---
            pipeline {
            agent any 
            stages {
                stage('pull') { 
                    steps {
                        git branch: 'main', credentialsId: 'gitlab_cread', url: 'https://gitlab.com/abhalkar/jenkis-ci-cd.git' 
                    }
                }
            stage('build') { 
                steps {
                    echo "Build Successul"
                    }
                }
            }
        ...

# What is SDLC 
    - it is the process of working in IT industry that how s/w should develop delopy diliver and for that process is set hence called as SDLC lifecycle police.

# what is webhook 
    - it is used to integrate the jenkins with git/gitlab.
    - webhook apply trigger to perfom push operation
    - nevigate -> build trigger -> []github hook trigger for gitscm polling
    - go in repo enable or add webhook 
    - also need to install new gitHub plugins  
    - when developer push the code to git/github will trigger the webhook so the next automated process will start exicuting.

# what is seed jobs
    - seed job are used to create a newjob 
    - select the process job dsl in Build
    - used to take backup of your jobs
    - operation as code 
        - error we can face ```script not approved for use```
        - to resolve this install new plugin 
            - plugin name :- Authorized projects

# how to take backup of our jobs
    - we taken backup we use maven fo pipeline to seedjob 
    - XML jobs to dsl github search on google
    - Jenkins XML to Job DSL converter repo```https://github.com/abhalkar/jenkinsxml2jobdsl```
    - yum install maven 
    - Clone the above repo in jenkins machine 
    - ./gradlew build
    - cd /root/jenkinsxml2jobdsl/build/libs
    - java -jar jenkinsxml2jobdsl.jar -u admin -a admin -j 13.233.64.230 -p 8080 pipeline-job2
    - Writing jobs/pipeline-job2.groovy
    - so now to use the backuped file to launch same job adjust the script file(pipeline-job2.groovy) 
    - create a new file seedjobs.groovy and copy the data form jobs/pipeline-job2.groovy
    - now create a new job -> frestyle -> source code -> provide gitlab url -> rrocess job DLS -> look on filesystem -> seed-jobs/seedjob.groovy
        - while crrating a new job from backuped file 
            - remember to change the name of now job 
            - url 
            - cread 
            - path 
# What is ci/cd ?
    - build: artifact 
        - android (apk)
        - java (war,jar)
        - linux (.rpm .deb)

    - Build tools for java 
        - jenkins
        - gradel
        - maven (2004, morden version apache ant, support more language(C#,ruby,java), depends on xml)
        - apache ant

# what is maven ?
    - maven in five minutes min (https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
    - dev(unit test) -> tets(testing) -> prod(final)
    - perquest is java 
## Installation maven 
    - download maven 
    - curl -O https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz
    - tar -zxf apache-maven-3.8.5-bin.tar.gz
    - cd apache-maven-3.8.5
    - $set env variable
    - vi /etc/profile
        - export PATH=$PATH:'/root/apache-maven-3.8.5'
        - source /etc/profile
    - mvn --version 

## project creation
    - to create dir hirearchy 
    - proper systematic structure is define (img,file,src,com)
    - pom.xml imp file
        - mvn archetype:generate -DgroupId=com.cloudblitz.aap -DartifactId=cloudblitz-aap -DarchetypeArtifactId=maven-archetype-quickstart     -DarchetypeVersion=1.4 -DinteractiveMode=false
        - yum install tree -y
        - tree cloudblitz-app
## Maven build life cycle 

    - EG:-# mvn archetype:generate -DgroupId=com.cloudblitz.aap -DartifactId=cloudblitz-aap -DarchetypeArtifactId=maven-archetype-quickstart     -DarchetypeVersion=1.4 -DinteractiveMode=false

    - each filed life cylce contains 3 faces 
        - Default
            - fases 
        - clean
            - for cleaning the pervioius aap build
        - site
            - docaumentation
    - build life cycle
        - valid
        - compaile
        - test 
        - package
        - intergartion-test
        - verify
        - install 
        - deploy
    - what is pom.xml
        - cat pom.xml
        - all the dependices are mentioned in pom.xml file required for project
        - we can taken dependices on google like selinum config we add in above pom.xml(eg: junit) file (accroding to project)
        - issue need to fixed in pom.xml

## Lets make an appln and make a built
    - pull the application from git repo
    - # tree studententaap-ui
    - # cd studentaap-ui

    - for to create buil make sure you in current where pom.xml is avilable 
        - # mvn clean   -> used to clean previos build
        - # mvn packages or # mvn build   -> all plugines will get download for project
        - # cd /target
        - studentapp-2.2-SNAPSHOT.war     -> artifact now we will deploy on tomcat 

    - install tomcat 
        - # curl -O https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.79/bin/apache-tomcat-8.5.79.zip
        - # unzip apache-tomcat-8.5.79
        - # chmod 700 catalina.sh
        - #  /apache-maven-3.8.5/bin./catalina.sh start

    - place the application to /webapps diir
        - #  cp /root/studentapp-ui/target/studentapp-2.2-SNAPSHOT.war /root/apache-tomcat-8.5.79/webapps/
        - allow the 8080 port 
        - url:- ip:8080:app-name/   -> to verify 

## lets setup a pipeline for all pull,build,test, deploy  for application 
    - node 1
        - lauch a new instace(node1) 
        - # curl -O https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.79/bin/apache-tomcat-8.5.79.zip
        - # unzip apache-tomcat-8.5.79.zip
        - # yum install unzip
        - # unzip apache-tomcat-8.5.79.zip
        - # cd apache-tomcat-8.5.79
        - # cd bin/
        - # chmod 700 catalina.sh
        - # ./catalina.sh start
        
    - need to do some config on node1
        - #  cd conf/
        - # vim tomcat-users.xml                --> local host config file
            - <role rolename="manager-gui"/>
            - <role rolename="manager-script"/>
            - <role rolename="manager-jmx"/> 
            - <role rolename="manager-status"/>
            - <role rolename="admin-gui"/>
            - <user username="linux" password="readhat" roles="manager-gui,manager-script,manager-jmx,manager-status,admin-gui"/>

        - # cd ../webapps/
        - # cd host-manager/META-INF/
        - # vim context.xml                  --> for host config file
            - <!--  <Valve className="org.apache.catalina.valves.RemoteAddrValve"
                allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" /> --> 
        - # cd webaaps/manager/META-INF
        - # vim context.xml
            - <!--  <Valve className="org.apache.catalina.valves.RemoteAddrValve"
         allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" />  -->

        - # ./catalina.sh stop 
        - # ./catalina.sh start

    - jenkins master
        - aad a new iteam 
        - create a pipeline project
        - pull it form repo 
        - provide the script path 

    - create a syntex for deploy stage
        - install plugin deploy to container
        - refer pipelinemaven.jdp 

# implementing the sonarqube for testong

    - used for tesing the application 
    - sonarqube is java based application 
    - run no of tesing on sonarqube
    - launch an aws instance for sonarqube
    - prequesties is JAVA 
    - installing sonarqube on instance 
        - # yum install epel-release unzip vim wget -y
        - need Belsoft java 
        - # wget https://download.bell-sw.com/java/11.0.4/bellsoft-jdk11.0.4-linux-amd64.rpm
        - # yim install java -y
        - # rpm -ivh bellsoft-jdk11.0.4-linux-amd64.rpm
        - # alternatives java        --> to check the default java select belsoft
        - sonarqube data is stored on database servre
            - # rpm -ivh http://repo.mysql.com/mysql57-community-release-el7.rpm
        - # rpm --import https://repo.mysql.com/RPM-GPG-KEY-mysql-2022
        - # yum install mysql-server -y
        - # systemctl start mysqld
        - # systemctl enable mysqld
        - # echo 'vm.max_map_count=262144' >/etc/sysctl.conf
        - # sysctl -p
        - # echo '* - nofile 80000' >>/etc/security/limits.conf
        - # sed -i -e '/query_cache_size/ d' -e '$ a query_cache_size = 15M' /etc/my.cnf
        - # systemctl restart mysqld
        - # grep 'password' /var/log/mysqld.log   -> pass is stored eg- (YFTlurLi3pf
        - # mysql_secure_installation    -> make all yes
        - # mysql -p -u root
            - show databases    -> passw stored
            - show tables;       -> usr infor have file
            - show databases;
        - now create a db 
            - create database sonarqube;
            - create user 'sonarqube'@'localhost' identified by 'Redhat@123';
            - grant all privileges on sonarqube.* to 'sonarqube'@'localhost';
            - flush privileges;
        - now lwts install sonarqube
        - # wget https://binaries.sonarsource.com/Distribution/sonarqube/sonarqube-7.9.1.zip
        - # unzip sonarqube-7.9.1.zip
        - # mv sonarqube-7.9.1 /opt/sonarqube
        - # cd /opt/
        - # mv sonarqube sonar
        - # sed -i -e '/^sonar.jdbc.username/ d' -e '/^sonar.jdbc.password/ d' -e '/^sonar.jdbc.url/ d' -e '/^sonar.web.host/ d' -e '/^sonar.web.port/ d' /opt/sonar/conf/sonar.properties
        - # sed -i -e '/#sonar.jdbc.username/ a sonar.jdbc.username=sonarqube' -e '/#sonar.jdbc.password/ a sonar.jdbc.password=Cloudblitz@123' -e '/InnoDB/ a sonar.jdbc.url=jdbc.mysql://localhost:3306/sonarqube?useUnicode=true&characterEncoding=utf&rewriteBatchedStatements=true&useConfigs=maxPerformance' -e '/#sonar.web.host/ a sonar.web.host=0.0.0.0' /opt/sonar/conf/sonar.properties         ->> we will provide the databse name and sonar qube password and webhost port no is 9000
        - sonar config file /conf/sonar.properties
        - # useradd sonar
        - # chown sonar:sonar /opt/sonar/ -R
        - # sed -i -e '/^#RUN_AS_USER/ c RUN_AS_USER=sonar' /opt/sonar/bin/linux-x86-64/sonar.sh  --> can only run by sonar user
        - # vim /opt/sonar/bin/linux-x86-64/sonar.sh
        - # vim /opt/sonar/conf/wrapper.conf
        - # alternatives --config java
        - # vim /opt/sonar/conf/wrapper.conf
        - now to start the sonrqube
        - # /opt/sonar/bin/linux-x86-64/sonar.sh start
        - to show status
        - # /opt/sonar/bin/linux-x86-64/sonar.sh status
        - to get logs 
        - # less logs/sonar.log
    - check the sonar qube by hitting the url:9000 
        - Login -> admin -> admin 

    - now lets create new project in sonar qube
        - add a new projecct ->(project key) studentaap -> (token) studentaap -> copy the token (one time visible) 83f673adc59d3592c980a9114b3d3f5a6a0f4b79
        - select java -> maven -> copy scanner -> exicute it on Jenkins server 
        - select the studentaap-ui (jenkins sevre) -> run the scanner cmd where the pon.xml file is present
        - will build here and sent it to sonar qube
        - sonarque is used for quality check of aap 
        - vunlarablity vs code smell 
        - bugs,vulnerablities,code smell,coverage,duplication here quality gate check will pass.
        - Quality check -> we put ruls for our application

# Intgrate sonarqube with jenkins
    - will need plugin sonaqube scanner
        - lets create a new pipeline for sonar 
        - use the pipeline syntext and create a shell script
    - need to configure on jnknis 
        - nevigate to configure system -> sonarqube server -> add sonar -> name: sonar -> url: http://3.109.203.173:9000 -> authenthentication: add cred -> manager jenkis -> manage cred -> add txt and token in it -> now add cred -. save
    - copy mvn cmd for sonar and make a sheel script form pipeline syntex
    - then past in pipeline refer pipelinesonar
    - now add pipeline in jenkins job pull throug scm -> provide path 
## how to avoid the if test fail then should not deploy to test 
    - we will need quality gate








    


