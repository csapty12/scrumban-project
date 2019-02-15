export default class Project {
  constructor(
    id = "",
    projectName = "",
    projectIdentifier = "",
    description = "",
    createdAt = ""
  ) {
    this.id = id;
    this.projectName = projectName;
    this.projectIdentifier = projectIdentifier;
    this.description = description;
    this.createdAt = createdAt;
  }
}