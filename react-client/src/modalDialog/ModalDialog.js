import React, { Component } from 'react'
import style from './Modal.css';
import Project from "../model/Project"

export default class ModalDialog extends Component {
  constructor(props){
    super(props);
    this.state={
      project: new Project()
    }
  }

  handleChange=event=>{
    console.log("event: " + event.target.value);
    const projectState = { ...this.state.project };
    projectState[event.target.name] = event.target.value;
    this.setState({
      project: projectState
    });
   
  }
   handleSubmit = event => {
      event.preventDefault();
      console.log("handling submit");
      this.props.handleSubmit(this.state.project);

    }
  render() {
    const { toggleDialog, handleCloseDialog, type, onKeyDown} = this.props;
    let displayFlex = 'none';
    if (toggleDialog) {
      displayFlex = 'flex';
    }
    return (
    <div className={style.overlay}>
      <div
        id="myModal"
        className={style.modal}
        style={{ display: displayFlex }}
        role="dialog"
      >
        <div className={style.modalContent} >
          <div className={style.modalHeader} >
            <span
              className={style.close}
              onClick={handleCloseDialog}
              role="presentation">
              &times;
            </span>

            <h2>{type} Project</h2>
          </div>
          <div className={style.modalBody}>
            <form onSubmit={this.handleSubmit}>
              <div className={style.row}>
                <div className={style.col25}>
                  <label htmlFor="pname" name="projectName">Project Name</label>
                </div>
                <div className={style.col75}>
                  <input type="text" id="projectName" name="projectName" onChange={this.handleChange}/>
                </div>
              </div>
              <div className="row">
                <div className={style.col25}>
                  <label htmlFor="subject">Description</label>
                </div>
                <div className={style.col75}>
                  <textarea
                    id="description"
                    name="description"
                    placeholder="This problem aims to solve..."
                    style={{ height: '200px' }}
                    onChange={this.handleChange}
                  />
                </div>
              </div>
              <div className={style.row}>
                <button type="submit" className={style.projectButton} data-test="button__create">{type}</button>
                <button onClick={handleCloseDialog} className={style.projectButton}  data-test="button__cancel">Cancel</button>
              </div>
            </form>
            <br />
          </div>
        </div>
      </div>
    </div>
  );
  }
}