export const fetchAllProjects = () =>{
    console.log("fetching");
    return fetch("http://localhost:8080/api/project")
    .then((response => response.json()))
    .catch(error => console.log("error has occured: " + error));
}


