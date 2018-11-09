# ScrumBan

This is a small project, to learn about using Java Spring Boot, with React.js and Redux.js.

# How To Use:

- To use the frontend:
  - first use npm install to get all node dependencies
  - use npm start to start up the front end. This will automatically open localhost:3000
  
- To use the backend:
  - use mvn spring-boot:run to start the backend service. 
  - currently, you can fire queries to the backend, whcih are saved to an H2 db, however this will beacome a dockerised MySQL db soon.

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
