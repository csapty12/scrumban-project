import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ProjectTile from './ProjectTile';
import style from './projectList.css';
import { fetchAllProjects } from './api/CallProjectListAPI';
import ModalDialog from './modalDialog/ModalDialog';
class ProjectList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeTile: null,
      toggleDialog: false,
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

  handleIsProjectDialogActive = item => {
    this.setState({
      ...this.state,
      toggleDialog: !this.state.toggleDialog,
    });
  };

  render() {
    const { data } = this.state;
    const { activeTile } = this.state;

    return (
      <div>
        <h1 className={style.projectHeader}>All Projects</h1>
        <span
          className={style.newProject}
          onClick={this.handleIsProjectDialogActive}
        >
          NEW PROJECT +
        </span>
        {this.state.toggleDialog && (
          <ModalDialog
            type="Create"
            handleCloseDialog={this.handleIsProjectDialogActive}
            toggleDialog={this.state.toggleDialog}
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
              />
            ))}
          </div>
        }
      </div>
    );
  }
}

export default ProjectList;
