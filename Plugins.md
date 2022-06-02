# Plugins we are going to use here 
- git 
    - for cloning the gitlab repo 
- ssh build agents 
    - for configuring the master slave concept
- email extension 
    - Used when ever our build failed sends notification 
- pipeline 
    - we perform ci/cd using th pipeline
    - written in GRROVY language also called as dsl(domain specific pipeline) langauge 
    - we use two types of pipeline
        - Declarative pipeline(new) . groovy
        - scripted pipeline(old) with .jdp
- pipeline stage view
    - to see graphical view of our ci/cd pipeline
- gitHub
    - to apply webhook with github 
- seed Jenkins
    - use for seed job to exicute 
    - we can cerate a job using the seed job 
    - seed job is used to create the new job
- Authorized projects
    - when we face the error
        - ```script not approved for use``
        - to remove this error install above plugin
        - then configure the project 
            - nevigate -> manage jenkins -> Configure Global Security -> Access Control for Builds ->Project default Build Authorization -> run as user aho triggred build
- maven intergation 
- deploy to container
    - artifact uploder 


            
        

