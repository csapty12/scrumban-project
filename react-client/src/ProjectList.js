import React, { Component } from 'react';
import PropTypes from 'prop-types';
import ProjectTile from './ProjectTile';
import style from './projectList.css';
import {fetchAllProjects} from "./api/CallProjectListAPI"
class ProjectList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      activeTile: null,
      showModal:false,
      data: []

    };
  }

  // componentDidMount(){
  //   const allProjectData = fetchAllProjects().then(data =>
  //     this.setState({ 
  //       ...this.state,
  //       data: data 
  //       })
  //   );
  // }

  async componentDidMount(){
    try{
    const allProjectData = await fetchAllProjects();
    const json = await allProjectData.json();
      this.setState({ 
        ...this.state,
        data: json
        })
    }catch (error) {
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

  toggleModal = (e)=>{
    this.setState({showModal: !this.state.showModal})

  }
  render() {
    const { data } = this.state;
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
              toggleModal={this.toggleModal}
              isDialogOpen={item.id === activeTile && this.state.showModal}

            />
          ))}
        </div>
      </div>
    );
  }
}

export default ProjectList;
