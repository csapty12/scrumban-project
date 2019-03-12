[![CircleCI](https://circleci.com/gh/csapty12/scrumban-project.svg?style=svg)](https://circleci.com/gh/csapty12/scrumban-project])

# The Idea
To create my own custom version of trello, for personal projects. 
To view the current live state -> https://trellban.herokuapp.com

# The Purpose

To learn about using Java Spring Boot, with React.js and Redux.js.
I will be deploying to AWS or Heroku once the project has been compelte. 

# The Idea

The idea is to create similar CRUD functionalities to what applications such as JIRA or TRELLO use, such that I can create my own scrum / kanban board.

It will consist of features including:

- creating a user
- logging in as a user
- Create new project
- Read the project contents (which would consist of ticket)
- Update current project
- Delete a project

The front end will have a similar look to Jira, created using React.js component, and Redux.js to store application state such as JWT's.

# How To Use Locally:
## Dependencies:
- Docker
- yarn
- Java
- Maven 

- To use the frontend:
  - use yarn install to get all node dependencies
  - use yarn start to start up the front end. This will automatically open localhost:3000
  
- To use the backend:
  - use mvn clean install spring-boot:run to start the backend service. 
  - currently, you can fire queries to the backend, is a dockerised MySQL db soon.
- please ensure that you start both if you want to have both backend and frontend functionality! :)
