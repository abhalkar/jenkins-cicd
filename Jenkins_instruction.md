# Steps to install Jenkins 
- prequiests for jenkins updated version is best
- Jenkins repo need to download
    - wget -O /etc/yum.repos.d/jenkins.repo     https://pkg.jenkins.io/redhat/jenkins.repo    - mv jenkins.repo /etc/yum.repos.d/
    - rpm --import https://pkg.jenkins.io/redhat/jenkins.io.key 
    - yum update -y
    - yum install jenkins -y 
    - yum install java-11-openjdk
    - systemctl enable jenkins
    - systemctl start jenkins 
    - systemctl status jenkins 

- now allow the 8080 port for instance in security greoup
- ping ip:8080
- Now we need to unlock jenkins
- Jenkins root dir is ```/var/lib/jenkins/secrets/initialAdminPassword```
    - cat /var/lib/jenkins/secrets/initialAdminPassword 
    - copy past the password for admin user
    - [] install plugins
    - [] Select plugins to install   -> best practice install plugin manually  
- now install Plugins
- create first admin user (put admin in all field)
- now we will get the usrl for jenkins : /var/lib/jenkins/secrets/initialAdminPassword
- Start jenknis

