import React, { Component } from 'react';
import ProjectTile from './ProjectTile';
import style from './projectList.css';
import {
  fetchAllProjects,
  createNewProject,
} from './service/CallProjectListAPI';
import ModalDialog from './modalDialog/ModalDialog';
import Project from './model/Project';

class ProjectList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeTile: null,
      toggleDialog: false,
      project: null,
      data: [],
    };
  }

  async componentDidMount() {
    try {
      const allProjectData = await fetchAllProjects();
      const json = await allProjectData.json();
      this.setState({
        ...this.state,
        data: json,
      });
    } catch (error) {
      console.log(error);
    }
  }

  setActiveTile = id => e => {
    e.preventDefault();
    this.setState(state => ({
      ...state,
      activeTile: id !== state.activeTile ? id : null,
    }));
  };

  handleIsProjectDialogActive = event => {
    this.setState({
      ...this.state,
      toggleDialog: !this.state.toggleDialog,
    });
  };

  handleSubmit = project => {
    this.handleIsProjectDialogActive(null);
    const response = createNewProject(project).then(response =>
      response.json().then(data =>
        this.setState({
          ...this.state,
          data: [...this.state.data, data],
        })
      )
    );
  };

  render() {
    const { data } = this.state;
    const { activeTile } = this.state;

    return (
      <div className={style.newProjectContainer}>
        <h1 className={style.projectHeader}>All Projects</h1>
        <div>
          <span
            className={style.newProject}
            onClick={this.handleIsProjectDialogActive}
          >
            NEW PROJECT +
          </span>
        </div>
        {this.state.toggleDialog && (
          <ModalDialog
            type="Create"
            handleCloseDialog={this.handleIsProjectDialogActive}
            toggleDialog={this.state.toggleDialog}
            handleSubmit={this.handleSubmit.bind(this)}
            project={new Project()}
          />
        )}
        <br />

        {
          <div className={style.flexContainer}>
            {data.map(item => (
              <ProjectTile
                name={item.projectName}
                date={new Date(item.createdAt)}
                key={`tile-${item.id}-${item.projectIdentifier}`}
                isMenuOpen={item.id === activeTile}
                toggleMenu={this.setActiveTile(item.id)}
                projectIdentifier={item.projectIdentifier}
              />
            ))}
          </div>
        }
      </div>
    );
  }
}

export default ProjectList;
