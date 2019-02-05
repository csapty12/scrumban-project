export default class Project {
  id = "";
  projectName = "";
  projectIdentifier = "";
  description = "";
  createdAt = "";

  constructor(id, projectName, projectIdentifier, description, createdAt) {
    this.id = id;
    this.projectName = projectName;
    this.projectIdentifier = projectIdentifier;
    this.description = description;
    this.createdAt = createdAt;
  }
}
