import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ProjectTile from './ProjectTile';
import style from './projectList.css';

class ProjectList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeTile: null,
    };
  }

  setActiveTile = id => e => {
    e.preventDefault();
    this.setState(state => ({
      ...state,
      activeTile: id !== state.activeTile ? id : null,
    }));
  };

  render() {
    const { data } = this.props;
    const { activeTile } = this.state;
    
    return (
      <div>
        <h1 className={style.projectHeader}>All Projects</h1>
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
      </div>
    );
  }
}

ProjectList.propTypes = {
  data: PropTypes.arrayOf(
    PropTypes.shape({
      projectName: PropTypes.string,
      createdAt: PropTypes.string,
      projectIdentifier: PropTypes.string,
    })
  ).isRequired,
};

export default ProjectList;
