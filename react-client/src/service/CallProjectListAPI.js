export const fetchAllProjects = () => {
  console.log('fetching');
  return fetch('http://localhost:8080/api/project');
};

const createSlug = newProject => {
  return newProject.projectName.split(' ').join('-');
};
export const createNewProject = async newProject => {
  newProject.projectIdentifier = createSlug(newProject);
  return fetch('http://localhost:8080/api/project', {
    method: 'POST',
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(newProject),
  });
};
