node {  
     stage('Pull') { 
        git branch: 'main', url: 'https://github.com/abhalkar/studentapp-ui.git'
    }
    stage('Build') { 
        echo "Build test" 
    }
    stage('Test') { 
        echo "Test success"
    }
    stage('Deploy') { 
        echo "webhook added success"
    }
}